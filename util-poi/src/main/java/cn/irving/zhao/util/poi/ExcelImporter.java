package cn.irving.zhao.util.poi;

import cn.irving.zhao.util.poi.config.*;
import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.exception.ExportException;
import cn.irving.zhao.util.poi.exception.ImportException;
import cn.irving.zhao.util.poi.formatter.CellDataFormatter;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ExcelImporter {

    private ExcelImporter() {
    }

    private static final ExcelImporter me = new ExcelImporter();

    public static <T> T readExcel(Class<T> type, InputStream stream) {
        try {
            Workbook workbook = WorkbookFactory.create(stream);
            return readExcel(type, workbook);
        } catch (IOException e) {
            throw new ImportException("文件读取错误", e);
        }
    }

    public static <T> T readExcel(Class<T> type, Workbook workbook) {
        try {
            var result = type.getConstructor().newInstance();
            var config = WorkBookConfigFactory.getWorkBookConfig(result);

            config.getSheetConfigs().forEach((item) -> {
                me.readOneSheet(workbook, null, item, result, 0, 0);
            });

            return result;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ImportException("待写入对象【" + type.getName() + "】创建失败", e);
        }
    }

    private boolean readOneSheet(Workbook workbook, Sheet sheet, SheetConfig sheetConfig, Object source, int rowIv, int colIv) {
        if (sheetConfig.getSheetType() == SheetType.INNER) {
            rowIv += sheetConfig.getBaseRow();
            colIv += sheetConfig.getBaseCol();
        } else {
            rowIv = sheetConfig.getBaseRow();
            colIv = sheetConfig.getBaseCol();
        }
        var repeatConfig = sheetConfig.getRepeatConfig();
        if (repeatConfig == null) {

            Sheet readSheet;
            if (sheetConfig.getSheetType() == SheetType.INNER) {
                readSheet = sheet;
            } else {
                readSheet = getReadSheet(workbook, source, sheetConfig, 0);
            }
            if (readSheet == null) {
                return false;
            }

            var readObject = this.getWriteObj(sheetConfig, source);

            this.readSheetData(workbook, readSheet, sheetConfig, readObject, rowIv, colIv);//检查单元格读取结果

        } else {
            var max = repeatConfig.getMax();
            var currentIndex = 0;
            var collectionData = this.getCollectionInstance(repeatConfig.getCollectionType(), sheetConfig.getDataType());

            sheetConfig.setData(source, collectionData);

            var itemDataType = repeatConfig.getItemType();

            while (true) {
                if (max > 0 && currentIndex >= max) {
                    break;
                }

                Sheet dataSheet = null;
                //设置工作簿数据源
                if (sheetConfig.getSheetType() == SheetType.INNER) {
                    dataSheet = sheet;
                } else if (sheetConfig.getSheetType() == SheetType.OUTER) {
                    dataSheet = getReadSheet(workbook, source, sheetConfig, currentIndex);
                }
                if (dataSheet == null) {
                    break;
                }

                Object itemData = null;
                try {
                    itemData = itemDataType.getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }

                var readSheetResult = this.readSheetData(workbook, dataSheet, sheetConfig, itemData, rowIv, colIv);

                if (!readSheetResult) {
                    break;
                }

                collectionData.add(itemData);

                if (repeatConfig.getCheck() != null) {
                    if (!repeatConfig.getCheck().checkRepeat(sheet, currentIndex, rowIv, colIv)) {
                        break;
                    }
                }

                int[] ivs = repeatConfig.getNextIv(currentIndex, source);

                rowIv += ivs[0];
                colIv += ivs[1];

                currentIndex++;

            }

        }

        return false;
    }

    private boolean readSheetData(Workbook workbook, Sheet sheet, SheetConfig sheetConfig, Object source, int rowIv, int colIv) {
        List<Boolean> results = new ArrayList<>();

        sheetConfig.getCellConfigs().stream().map((item) -> {
            return this.readCell(sheet, item, source, rowIv, colIv);
        }).forEach(results::add);

        sheetConfig.getRefSheetConfigs().stream().map((item) -> {
            return this.readOneSheet(workbook, sheet, item, source, rowIv, colIv);
        }).forEach(results::add);

        return results.parallelStream().anyMatch(Boolean::booleanValue);
    }


    private Object getWriteObj(SheetConfig sheetConfig, Object source) {
        if (sheetConfig.getDataType() == null) {
            return source;
        } else {
            try {
                Object result = sheetConfig.getDataType().getConstructor().newInstance();
                sheetConfig.setData(source, result);
                return result;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new ExportException("构建导出对象失败", e);
            }
        }
    }

    /**
     * 根据配置信息获取读取的工作簿对象
     */
    private Sheet getReadSheet(Workbook workbook, Object source, SheetConfig sheetConfig, int loopIndex) {
        if (sheetConfig.getSheetNameFormatter() == null) {
            return workbook.getSheet(sheetConfig.getName());
        } else {
            Sheet result;
            for (int i = 0, length = workbook.getNumberOfSheets(); i < length; i++) {
                result = workbook.getSheetAt(i);
                if (sheetConfig.getSheetNameFormatter().checkSheet(source, sheetConfig, result.getSheetName(), loopIndex)) {
                    return result;
                }
            }
        }
        return null;
    }

    private boolean readCell(Sheet sheet, CellConfig cellConfig, Object source, int rowIv, int colIv) {
        RepeatConfig repeatConfig = cellConfig.getRepeatConfig();
        if (repeatConfig == null) {
            Cell readCell = this.getReadCell(sheet, cellConfig, rowIv, colIv);
            if (readCell == null) {
                return false;
            }

            Object data = this.formatCellData(readCell, cellConfig, rowIv, colIv);
            if (data == null) {
                return false;
            } else {
                cellConfig.setData(source, data);
                return true;
            }
        } else {
            var collectionType = repeatConfig.getCollectionType();
            var collectionData = getCollectionInstance(collectionType, cellConfig.getType());
            var currentIndex = 0;
            var max = repeatConfig.getMax();
            while (true) {
                if (max > 0 && currentIndex >= max) {//最大循环次数判断
                    break;
                }
                //获得读取单元格
                Cell readCell = this.getReadCell(sheet, cellConfig, rowIv, colIv);
                if (readCell == null) {
                    break;
                }
                Object data = this.formatCellData(readCell, cellConfig, rowIv, colIv);
                if (data == null) {
                    break;
                }
                collectionData.add(data);

                var nextIv = repeatConfig.getNextIv(currentIndex, source);
                rowIv += nextIv[0];
                colIv += nextIv[1];
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

    private Cell getReadCell(Sheet sheet, CellConfig cellConfig, int rowIv, int colIv) {
        //获得读取单元格
        var readRow = sheet.getRow(cellConfig.getRowIndex() + rowIv);
        if (readRow == null) {
            return null;
        }
        return readRow.getCell(cellConfig.getCellIndex() + colIv);
    }

    private Object formatCellData(Cell readCell, CellConfig cellConfig, int rowIv, int colIv) {
        //获得数据
        Object data = getCellData(readCell, cellConfig.getType());
        if (cellConfig.getFormatterConfig() != null) {
            CellDataFormatter dataFormatter = cellConfig.getFormatterConfig().getCellDataFormatter();
            if (dataFormatter != null) {
                data = dataFormatter.parse(data, cellConfig, rowIv, colIv);
            }
        }
        return data;
    }

    /**
     * 读取单个单元格内的数据
     *
     * @param cell     单元格对象
     * @param dataType 数据类型
     */
    private Object getCellData(Cell cell, Class<?> dataType) {
        if (cell.getCellType() == CellType.FORMULA) {//如果单元格时表达式时，计算表达式，并写入数据
            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            cell.setCellType(cellValue.getCellType());
            if (cellValue.getCellType() == CellType.STRING) {
                cell.setCellValue(cellValue.getStringValue());
            } else if (cellValue.getCellType() == CellType.NUMERIC) {
                cell.setCellValue(cellValue.getNumberValue());
            } else if (cellValue.getCellType() == CellType.BOOLEAN) {
                cell.setCellValue(cellValue.getBooleanValue());
            } else if (cellValue.getCellType() == CellType.ERROR) {
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
                return collectionType.getConstructor().newInstance();
            } else {
                if (dataType.isAssignableFrom(ArrayList.class)) {
                    return new ArrayList<>();
                } else if (dataType.isAssignableFrom(HashSet.class)) {
                    return new HashSet<>();
                } else {
                    throw new ExportException("未匹配到" + dataType.getName() + "的Collection实例");
                }
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ExportException("Collection初始化失败", e);
        }
    }


}
