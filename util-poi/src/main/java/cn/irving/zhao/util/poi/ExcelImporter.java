package cn.irving.zhao.util.poi;

import cn.irving.zhao.util.poi.config.*;
import cn.irving.zhao.util.poi.enums.Direction;
import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.exception.ExportException;
import cn.irving.zhao.util.poi.formatter.CellDataFormatter;
import cn.irving.zhao.util.poi.inter.IWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.util.*;

/**
 * @author Irving
 * @version ExcelImporter.java, v 0.1 2018/3/2
 */
public class ExcelImporter extends ExcelOperator {

    private static final ExcelImporter me = new ExcelImporter();

    public static <T extends IWorkbook> T imports(Class<T> type, Workbook workbook) {
        try {
            T result = type.newInstance();//构建返回结果
            WorkBookConfig workBookConfig = WorkBookConfigFactory.getWorkbookConfig(type);
            SheetConfig defaultSheetConfig = workBookConfig.getDefaultSheetConfig();// 默认 Sheet
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return result;
            }
            //读取默认sheet
            if (defaultSheetConfig != null) {
                me.readSheet(workbook, null, defaultSheetConfig, result, 0, 0);
            }
            //读取额外sheet信息
            workBookConfig.getSheetConfigs().forEach((itemSheetConfig) -> {
                try {
                    me.readSheet(workbook, null, itemSheetConfig, result, 0, 0);
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            });

            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ExportException("构建导出对象失败", e);
        }
    }

    /**
     * 读取数据
     *
     * @param workbook    工作表对象
     * @param sheet       工作簿读取源
     * @param sheetConfig 工作簿配置信息
     * @param source      数据写入的对象
     * @param rowIv       行修正量
     * @param colIv       列修正量
     */
    private boolean readSheet(Workbook workbook, Sheet sheet, SheetConfig sheetConfig, Object source, int rowIv, int colIv) throws IllegalAccessException, InstantiationException {
        //内嵌工作簿时，添加行、列修正量
        if (sheetConfig.getSheetType() == SheetType.INNER) {
            rowIv += sheetConfig.getBaseRow();
            colIv += sheetConfig.getBaseCol();
        }
        RepeatConfig repeatConfig = sheetConfig.getRepeatConfig();
        if (repeatConfig == null) {//单一对象读取
            Class<?> dataType = sheetConfig.getDataType();
            Object data;
            //构建数据对象
            if (dataType == null) {
                data = source;
            } else {
                data = dataType.newInstance();
                sheetConfig.setData(source, data);
            }
            Sheet readDataSheet = null;
            //获取数据源工作簿
            if (sheetConfig.getSheetType() == SheetType.INNER) {
                readDataSheet = sheet;
            } else if (sheetConfig.getSheetType() == SheetType.OUTER) {
                readDataSheet = getReadSheet(workbook, data, sheetConfig, 0);
            }
            //写入工作簿
            return readSheet(workbook, readDataSheet, sheetConfig.getSheetCellConfig(), data, rowIv, colIv);
        } else {
            //循环读取数据
            Class<?> itemDataType = repeatConfig.getItemType();
            Collection collectionData = getCollectionInstance(repeatConfig.getCollectionType(), sheetConfig.getDataType());
            Object itemData = itemDataType.newInstance();
            Sheet dataSheet = null;
            int currentIndex = 0;
            int max = repeatConfig.getMax();
            Direction dir = repeatConfig.getDirection();
            int identity = repeatConfig.getIdentity();
            while (true) {
                if (max > 0 && currentIndex >= max) {//最大循环次数判断
                    break;
                }
                //设置工作簿数据源
                if (sheetConfig.getSheetType() == SheetType.INNER) {
                    dataSheet = sheet;
                } else if (sheetConfig.getSheetType() == SheetType.OUTER) {
                    dataSheet = getReadSheet(workbook, itemData, sheetConfig, currentIndex);
                    rowIv = colIv = 0;//外联工作簿时，重置行、列修正量
                }
                if (dataSheet == null) {
                    break;
                }
                //读取工作簿
                //工作簿内所有配置信息均为null时，返回false
                boolean readResult = readSheet(workbook, dataSheet, sheetConfig.getSheetCellConfig(), itemData, rowIv, colIv);
                if (!readResult) {
                    break;
                }
                collectionData.add(itemData);
                itemData = itemDataType.newInstance();
                if (sheetConfig.getSheetType() == SheetType.INNER) {
                    if (dir == Direction.HERIZONTAL) {
                        colIv += identity;
                    } else if (dir == Direction.VERTICALLY) {
                        rowIv += identity;
                    } else if (dir == Direction.BOTH) {
                        rowIv += identity;
                        colIv += identity;
                    }
                }
                currentIndex++;
            }
            if (collectionData.isEmpty()) {
                return false;
            } else {
                sheetConfig.setData(source, collectionData);
                return true;
            }
        }
    }

    /**
     * 读取工作簿中的数据，当工作簿中所有的属性读取结果均为null时，返回false
     *
     * @param workbook        工作表对象
     * @param sheet           工作簿对象
     * @param sheetCellConfig 工作簿配置信息
     * @param source          数据源
     * @param rowIv           行修正量
     * @param colIv           列修正量
     */
    private boolean readSheet(Workbook workbook, Sheet sheet, SheetCellConfig sheetCellConfig, Object source, int rowIv, int colIv) {
        boolean cellResult = sheetCellConfig.getCellConfigs().stream().map((cellConfig) -> {
            return readCell(sheet, cellConfig, source, rowIv, colIv);
        }).filter(match -> match).count() > 0;
        boolean sheetResult = sheetCellConfig.getRefSheetConfigs().stream().map((itemConfig) -> {
            try {
                return readSheet(workbook, sheet, itemConfig, source, rowIv, colIv);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            return false;
        }).filter(match -> match).count() > 0;
        return cellResult || sheetResult;
    }

    /**
     * 读取单个单元格数据
     *
     * @param sheet      工作簿数据源
     * @param cellConfig 单元格配置信息
     * @param source     写入对象
     * @param rowIv      行修正量
     * @param colIv      列修正量
     * @return 读取结果为null时，返回false
     */
    private boolean readCell(Sheet sheet, CellConfig cellConfig, Object source, int rowIv, int colIv) {
        RepeatConfig repeatConfig = cellConfig.getRepeatConfig();
        if (repeatConfig == null) {
            //获得读取单元格
            Row readRow = sheet.getRow(cellConfig.getRowIndex() + rowIv);
            if (readRow == null) {
                return false;
            }
            Cell readCell = readRow.getCell(cellConfig.getCellIndex() + colIv);
            if (readCell == null) {
                return false;
            }
            //获得读取数据
            Object data = getCellData(readCell, cellConfig.getType());
            //检查是否有单元格格式化配置
            if (cellConfig.getFormatterConfig() != null) {
                CellDataFormatter dataFormatter = cellConfig.getFormatterConfig().getCellDataFormatter();
                if (dataFormatter != null) {
                    data = dataFormatter.parse(data, cellConfig, rowIv, colIv);
                }
            }
            //检查数据是否为null
            if (data == null) {
                return false;
            } else {
                cellConfig.setData(source, data);
                return true;
            }
        } else {
            //构建配置信息
            Class<? extends Collection> collectionType = repeatConfig.getCollectionType();
            Collection collectionData = getCollectionInstance(collectionType, cellConfig.getType());
            int currentIndex = 0;
            int max = repeatConfig.getMax();
            int identity = repeatConfig.getIdentity();
            Direction dir = repeatConfig.getDirection();
            //循环读取
            while (true) {
                if (max > 0 && currentIndex >= max) {//最大循环次数判断
                    break;
                }
                //获得读取单元格
                Row readRow = sheet.getRow(cellConfig.getRowIndex() + rowIv);
                if (readRow == null) {
                    break;
                }
                Cell readCell = readRow.getCell(cellConfig.getCellIndex() + colIv);
                if (readCell == null) {
                    break;
                }
                //获得数据
                Object data = getCellData(readCell, cellConfig.getType());
                if (cellConfig.getFormatterConfig() != null) {
                    CellDataFormatter dataFormatter = cellConfig.getFormatterConfig().getCellDataFormatter();
                    if (dataFormatter != null) {
                        data = dataFormatter.parse(data, cellConfig, rowIv, colIv);
                    }
                }
                if (data == null) {//当数据为null时，跳出
                    break;
                }
                collectionData.add(data);
                //计算行、列权重
                if (dir == Direction.HERIZONTAL) {
                    colIv += identity;
                } else if (dir == Direction.VERTICALLY) {
                    rowIv += identity;
                } else if (dir == Direction.BOTH) {
                    rowIv += identity;
                    colIv += identity;
                }
                currentIndex++;
            }
            if (collectionData.isEmpty()) {
                return false;
            } else {
                cellConfig.setData(source, collectionData);
                return true;
            }
        }
    }

    /**
     * 读取单个单元格内的数据
     *
     * @param cell     单元格对象
     * @param dataType 数据类型
     */
    private Object getCellData(Cell cell, Class<?> dataType) {
        if (cell.getCellTypeEnum() == CellType.FORMULA) {//如果单元格时表达式时，计算表达式，并写入数据
            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            cell.setCellType(cellValue.getCellTypeEnum());
            if (cellValue.getCellTypeEnum() == CellType.STRING) {
                cell.setCellValue(cellValue.getStringValue());
            } else if (cellValue.getCellTypeEnum() == CellType.NUMERIC) {
                cell.setCellValue(cellValue.getNumberValue());
            } else if (cellValue.getCellTypeEnum() == CellType.BOOLEAN) {
                cell.setCellValue(cellValue.getBooleanValue());
            } else if (cellValue.getCellTypeEnum() == CellType.ERROR) {
                cell.setCellValue(cellValue.getErrorValue());
            }
        }
        //根据不同类型读取相关数据
        if (dataType == Long.class || dataType == long.class) {
            cell.setCellType(CellType.NUMERIC);
            return Double.valueOf(cell.getNumericCellValue()).longValue();
        } else if (dataType == Integer.class || dataType == int.class) {
            cell.setCellType(CellType.NUMERIC);
            return Double.valueOf(cell.getNumericCellValue()).intValue();
        } else if (dataType == Short.class || dataType == short.class) {
            cell.setCellType(CellType.NUMERIC);
            return Double.valueOf(cell.getNumericCellValue()).shortValue();
        } else if (dataType == Double.class || dataType == double.class) {
            cell.setCellType(CellType.NUMERIC);
            return cell.getNumericCellValue();
        } else if (dataType == Float.class || dataType == float.class) {
            cell.setCellType(CellType.NUMERIC);
            return Double.valueOf(cell.getNumericCellValue()).floatValue();
        } else if (dataType == String.class) {
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue();
        } else if (RichTextString.class.isAssignableFrom(dataType)) {
            return cell.getRichStringCellValue();
        } else if (Date.class.isAssignableFrom(dataType)) {
            cell.setCellType(CellType.NUMERIC);
            return cell.getDateCellValue();
        } else if (Calendar.class.isAssignableFrom(dataType)) {
            cell.setCellType(CellType.NUMERIC);
            Calendar result = Calendar.getInstance();
            result.setTime(cell.getDateCellValue());
            return result;
        } else {
            return cell.getStringCellValue();
        }
    }

    /**
     * 获得Collection类型
     *
     * @param collectionType 注解中的配置类型
     * @param dataType       //属性对应的配置类型
     */
    private Collection getCollectionInstance(Class<? extends Collection> collectionType, Class<?> dataType) {
        try {
            if (collectionType != Collection.class) {//注解中包含配置，直接使用注解中配置的类型
                return collectionType.newInstance();
            } else {
                if (dataType.isAssignableFrom(ArrayList.class)) {
                    return new ArrayList<>();
                } else if (dataType.isAssignableFrom(HashSet.class)) {
                    return new HashSet<>();
                } else {
                    throw new ExportException("未匹配到" + dataType.getName() + "的Collection实例");
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ExportException("Collection初始化失败", e);
        }
    }

    /**
     * 根据配置信息获取读取的工作簿对象
     */
    private Sheet getReadSheet(Workbook workbook, Object source, SheetConfig sheetConfig, int loopIndex) {
        Sheet result;
        if (sheetConfig.getSheetNameFormatter() == null) {
            return workbook.getSheetAt(0);
        }
        for (int i = 0, length = workbook.getNumberOfSheets(); i < length; i++) {
            result = workbook.getSheetAt(i);
            if (sheetConfig.getSheetNameFormatter().checkSheet(source, sheetConfig, result.getSheetName(), loopIndex)) {
                return result;
            }
        }
        return null;
    }

}
