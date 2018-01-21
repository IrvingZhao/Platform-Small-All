package cn.irving.zhao.util.poi;

import cn.irving.zhao.util.poi.config.*;
import cn.irving.zhao.util.poi.enums.CellDataType;
import cn.irving.zhao.util.poi.enums.Direction;
import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.enums.WorkbookType;
import cn.irving.zhao.util.poi.exception.ExportException;
import cn.irving.zhao.util.poi.formatter.CellDataFormatter;
import cn.irving.zhao.util.poi.inter.IWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * POI 工具包
 */
public class POIUtil {

    public void export(IWorkbook data, OutputStream outputStream, String template) throws ExportException {
        try {
            export(data, outputStream, getTemplateStreamByPath(template));
        } catch (IOException e) {
            throw new ExportException("流异常", e);
        }
    }

    /**
     * 导出excel
     *
     * @param data     数据
     * @param output   输出位置，物理磁盘路径
     * @param template excel模板文件流
     */
    public void export(IWorkbook data, String output, InputStream template) throws ExportException {
        try {
            File file = new File(output);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new ExportException("文件：" + output + "创建失败");
                }
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            export(data, outputStream, template);
        } catch (IOException e) {
            throw new ExportException("流异常", e);
        }
    }

    /**
     * excel导出
     *
     * @param data     数据
     * @param output   输出磁盘位置，物理磁盘位置
     * @param template 模板位置，磁盘物理路径或classpath路径，优先查找磁盘物理路径
     */
    public void export(IWorkbook data, String output, String template) throws ExportException {
        try {
            File file = new File(output);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new ExportException("文件：" + output + "创建失败");
                }
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            export(data, outputStream, getTemplateStreamByPath(template));
        } catch (IOException e) {
            throw new ExportException("流异常", e);
        }

    }

    /**
     * 根据路径创建查找文件
     */
    private InputStream getTemplateStreamByPath(String path) throws FileNotFoundException {
        InputStream templateStream = null;
        if (path != null && !"".equals(path)) {
            File templateFile = new File(path);// 检查文件，如果物理磁盘中存在，使用物理磁盘中的文件，不存在，使用class获得resource
            if (templateFile.exists()) {
                templateStream = new FileInputStream(templateFile);
            } else {
                templateStream = POIUtil.class.getResourceAsStream(path);
            }
        }
        return templateStream;
    }


    /**
     * 导出对象
     *
     * @param data     数据
     * @param output   输出流位置
     * @param template 模板输入流
     */
    public void export(IWorkbook data, OutputStream output, InputStream template) throws ExportException {
        try {
            WorkBookConfig workBookConfig = WorkBookConfigFactory.getWorkbookConfig(data.getClass());
            Workbook workbook = export(data.getWorkbookType(), workBookConfig, data, template);
            workbook.write(output);
            output.flush();
        } catch (IOException e) {
            throw new ExportException("流异常", e);
        }
    }

    /**
     * 获取WorkBook方法
     *
     * @param type           导出类型
     * @param workBookConfig 导出配置信息
     * @param data           被导出的数据
     * @return 工作簿对象
     */
    public Workbook export(WorkbookType type, WorkBookConfig workBookConfig, Object data, InputStream template) {
        Workbook result = type.getWorkbook(template);

        SheetConfig defaultSheetConfig = workBookConfig.getDefaultSheetConfig();
        if (defaultSheetConfig != null) {//默认单元表导出
            String sheetName;
            if (defaultSheetConfig.getSheetNameFormatter() == null) {
                sheetName = defaultSheetConfig.getName();
            } else {
                sheetName = defaultSheetConfig.getSheetNameFormatter().getSheetName(defaultSheetConfig.getName(), 0);
            }
            if (sheetName == null || sheetName.equals("")) {
                sheetName = String.valueOf(System.currentTimeMillis());
            }
            defaultSheetConfig.setName(sheetName);
            try {
                writeSheetData(result, null, defaultSheetConfig, data, 0, 0, 0);
            } catch (ExportException e) {
                throw new RuntimeException(data.getClass() + "中的默认单元表导出失败", e);
            }

        }
        List<SheetConfig> sheetConfigs = workBookConfig.getSheetConfigs();
        if (sheetConfigs != null) {
            sheetConfigs.forEach((itemSheetConfig) -> {
                Object itemSheetData = itemSheetConfig.getData(data);
                if (itemSheetData != null) {//数据为空是，不写入工作表
                    try {
                        writeSheetData(result, null, itemSheetConfig, itemSheetData, 0, 0, 0);
                    } catch (ExportException e) {
                        throw new RuntimeException("单元表" + itemSheetConfig.getName() + "导出失败", e);
                    }
                }
            });
        }

        return result;
    }

    /**
     * 写入工作表数据
     *
     * @param sheet       工作表对象
     * @param sheetConfig 工作表配置信息
     * @param data        工作表对应的数据对象
     * @param loopIndex   循环调用时的索引
     */
    private void writeSheetData(Workbook workbook, Sheet sheet, SheetConfig sheetConfig, Object data, int rowIv, int colIv, int loopIndex) throws ExportException {
        Sheet writeDataSheet = null;
        //判断单元表写入方式，并获取工作表写入对象
        if (sheetConfig.getSheetType() == SheetType.INNER) {
            writeDataSheet = sheet;//内嵌工作表的写入对象为当前工作表
        } else if (sheetConfig.getSheetType() == SheetType.OUTER) {//外联工作表需新建工作表对象
            String sheetName;
            if (sheetConfig.getSheetNameFormatter() != null) {//如果有工作表对象格式化，则使用工作表格式化对象进行工作表名称格式化
                sheetName = sheetConfig.getSheetNameFormatter().getSheetName(sheetConfig.getName(), loopIndex);
            } else {
                sheetName = sheetConfig.getName();
            }
            writeDataSheet = getSheet(workbook, sheetName);
            // 新创建 sheet 对象时，重置 rowIv 和 colIv
            rowIv = colIv = 0;
        }
        if (writeDataSheet == null) {
            throw new ExportException("数据写入工作表创建异常");
        }

        SheetCellConfig sheetCellConfig = sheetConfig.getSheetCellConfig(); // 获得 工作表 配置信息
        List<CellConfig> cellConfigs = sheetCellConfig.getCellConfigs();// 获得单元格配置
        try {
            for (CellConfig item : cellConfigs) {
                writeCell(workbook, writeDataSheet, item, data, rowIv, colIv);//写入单元格数据
            }
        } catch (ExportException e) {
            throw new ExportException(writeDataSheet.getSheetName() + "数据写入异常", e);
        }
        List<SheetConfig> sheetConfigs = sheetCellConfig.getRefSheetConfigs();//获得关联工作表配置信息
        if (sheetConfigs == null) {//没有工作表配置信息是，跳出
            return;
        }
        for (SheetConfig itemSheetConfig : sheetConfigs) {
            // 在一个单元表 中 的 多个单元表配置信息，每次进入新的单元表配置时，该单元表的 rowIv、colIv 应与最初rowIv、colIv 一致
            int itemSheetRowIv = rowIv;
            int itemSheetColIv = colIv;
            if (itemSheetConfig.getSheetType() == SheetType.INNER) {//如果为内嵌工作表，追加 baseRow  baseCol
                itemSheetRowIv += itemSheetConfig.getBaseRow();
                itemSheetColIv += itemSheetConfig.getBaseCol();
            }
            //判断是否为循环引入
            RepeatConfig repeatConfig = itemSheetConfig.getRepeatConfig();
            Object itemSheetData = itemSheetConfig.getData(data);
            if (itemSheetData != null) {//数据为空时，不写入
                if (repeatConfig == null) {
                    //写入工作表
                    writeSheetData(workbook, writeDataSheet, itemSheetConfig, itemSheetData, itemSheetConfig.getBaseRow() + itemSheetRowIv, itemSheetConfig.getBaseCol() + itemSheetColIv, 0);
                } else {
                    Direction direction = repeatConfig.getDirection();
                    int identity = repeatConfig.getIdentity();
                    int max = repeatConfig.getMax();
                    int currentIndex = 0;
                    if (Iterable.class.isInstance(itemSheetData)) {//检查循环写入数据是否为可迭代
                        for (Object itemSheetDataItem : ((Iterable) itemSheetData)) {
                            if (max > 0 && currentIndex >= max) {//最大循环次数判断
                                break;
                            }
                            //写入单个工作表
                            writeSheetData(workbook, writeDataSheet, itemSheetConfig, itemSheetDataItem, itemSheetRowIv, itemSheetColIv, currentIndex);
                            //设置循环行列权重
                            if (direction == Direction.HERIZONTAL) {
                                itemSheetColIv += identity;
                            } else if (direction == Direction.VERTICALLY) {
                                itemSheetRowIv += identity;
                            } else if (direction == Direction.BOTH) {
                                itemSheetRowIv += identity;
                                itemSheetColIv += identity;
                            }
                            currentIndex++;
                        }
                    } else {
                        throw new RuntimeException(itemSheetData.getClass().getName() + "不是一个有效的 Iterable 对象");
                    }

                }
            }
        }
    }

    /**
     * 写入单元格数据信息，判断是否为Repeat
     *
     * @param sheet      工作表对象
     * @param cellConfig 单元格配置信息
     * @param data       单元格数据所属对象
     */
    private void writeCell(Workbook workbook, Sheet sheet, CellConfig cellConfig, Object data, int rowIv, int colIv) throws ExportException {
        RepeatConfig repeatConfig = cellConfig.getRepeatConfig();
        Object cellData = cellConfig.getData(data);//获取单元格数据
        if (cellData == null) {//数据为空时，跳出
            return;
        }
        if (repeatConfig == null) {//检查单元格是否为循环写入
            try {
                writeCellData(workbook, sheet, cellConfig, cellData, rowIv, colIv);//单一数据写入
            } catch (ExportException e) {
                throw new ExportException("写入第" + cellConfig.getRowIndex() + "行，第" + cellConfig.getCellIndex() + "列数据失败", e);
            }
        } else {
            int currentIndex = 0;
            int max = repeatConfig.getMax();
            int identity = repeatConfig.getIdentity();
            Direction dir = repeatConfig.getDirection();
            if (Iterable.class.isInstance(cellData)) {//检查循环写入的数据类型是否可迭代
                try {
                    for (Object dataItem : ((Iterable) cellData)) {//迭代数据
                        if (max > 0 && currentIndex >= max) {//最大循环次数判断
                            break;
                        }
                        writeCellData(workbook, sheet, cellConfig, dataItem, rowIv, colIv);//写入单元格数据

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
                } catch (ExportException e) {
                    throw new ExportException("第" + currentIndex + "次写入第" + cellConfig.getRowIndex() + "行，第" + cellConfig.getCellIndex() + "列数据异常", e);
                }
            } else {
                throw new ExportException(cellData.getClass().getName() + "不是一个有效的 Iterable 对象");
            }
        }
    }


    /**
     * 写入单元格内容
     *
     * @param sheet      数据所在单元表
     * @param cellConfig 单元格配置信息
     * @param cellData   单元格对象
     * @param rowIv      行坐标修正值
     * @param colIv      列坐标修正值
     */
    private void writeCellData(Workbook workbook, Sheet sheet, CellConfig cellConfig, Object cellData, int rowIv, int colIv) throws ExportException {//写入cell 数据
        MergedConfig mergedConfig = cellConfig.getMergedConfig();
        Cell cell;
        if (mergedConfig == null) {//检查是否需要合并单元格
            //根据行坐标、列坐标、行权重、列权重获取单元格
            cell = getCell(getRow(sheet, cellConfig.getRowIndex() + rowIv), cellConfig.getCellIndex() + colIv);
        } else {
            //根据合并单元格配置，行权重，列权重获取单元格
            cell = getMergedCell(sheet,
                    mergedConfig.getStartRowIndex() + rowIv,
                    mergedConfig.getStartColIndex() + colIv,
                    mergedConfig.getEndRowIndex() + rowIv,
                    mergedConfig.getEndColIndex() + colIv);
        }
        CellDataType dataType = cellConfig.getDataType();//数据类型
        FormatterConfig formatterConfig = cellConfig.getFormatterConfig();//数据格式化配置
        if (formatterConfig != null) {
            String formatterPattern = formatterConfig.getFormatString();
            if (formatterPattern != null && !"".equals(formatterPattern)) {//设置excel中数据格式化的格式
                getCellStyle(cell, workbook).setDataFormat(workbook.createDataFormat().getFormat(formatterPattern));
            }
            if (formatterConfig.getCellDataFormatter() != null) {//如果包含数据格式化方法，执行格式化
                cellData = formatterConfig.getCellDataFormatter().format(cellData, cell.getRowIndex(), cell.getColumnIndex());
            }
        }
        if (dataType == CellDataType.AUTO) {//根据数据类型，执行不同的数据写入逻辑
            writeAutoTypeCell(cell, cellData);
        } else {
            writeCustomTypeCell(cell, dataType, cellData);
        }
    }

    /**
     * 自定义对象类型写入
     *
     * @param cell     单元格
     * @param dataType 数据类型
     * @param cellData 数据对象
     */
    private void writeCustomTypeCell(Cell cell, CellDataType dataType, Object cellData) throws ExportException {
        cell.setCellType(dataType.getCellType());//设置类型
        if (dataType == CellDataType.DATE) {
            if (Calendar.class.isInstance(cellData)) {
                cell.setCellValue((Calendar) cellData);
            } else if (Date.class.isInstance(cellData)) {
                cell.setCellValue((Date) cellData);
            } else {
                throw new ExportException(cellData.getClass() + "不是一个Date或Calendar对象");
            }
        } else if (dataType == CellDataType.NUMERIC) {
            if (int.class.isInstance(cellData) || long.class.isInstance(cellData) || double.class.isInstance(cellData)) {
                cell.setCellValue((double) cellData);
            } else if (Number.class.isInstance(cellData)) {
                cell.setCellValue(((Number) cellData).doubleValue());
            } else {
                throw new ExportException(cellData.getClass() + "不是一个有效的数字类型");
            }
        } else if (dataType == CellDataType.FORMULA) {
            cell.setCellFormula(String.valueOf(cellData));
        } else if (dataType == CellDataType.STRING) {
            cell.setCellValue(String.valueOf(cellData));
        }
    }

    /**
     * 自动类型检测写入
     *
     * @param cell     单元格对象
     * @param cellData 被写入数据
     */
    private void writeAutoTypeCell(Cell cell, Object cellData) {
        if (Calendar.class.isInstance(cellData)) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((Calendar) cellData);
        } else if (Date.class.isInstance(cellData)) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((Date) cellData);
        } else if (double.class.isInstance(cellData)
                || int.class.isInstance(cellData)
                || long.class.isInstance(cellData)) { //基础数据类型，强制转换double后输出
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((double) cellData);
        } else if (Number.class.isInstance(cellData)) {//如果是数字类型，调用doubleValue 方法
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((Number) cellData).doubleValue());
        } else if (RichTextString.class.isInstance(cellData)) {
            cell.setCellValue((RichTextString) cellData);
        } else {//其他类型，调用String.valueOf 转换成字符串后输出
            cell.setCellType(CellType.STRING);
            cell.setCellValue(String.valueOf(cellData));
        }
    }

    /**
     * 获取单元格样式，单元格样式找不到时，自动创建新样式，并添加至单元格中
     *
     * @param cell     单元对象
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    private CellStyle getCellStyle(Cell cell, Workbook workbook) {
        if (cell.getCellStyle() == null) {
            cell.setCellStyle(workbook.createCellStyle());
        }
        return cell.getCellStyle();
    }


    /**
     * 获得合并单元格
     *
     * @param sheet    工作表对象
     * @param startRow 合并开始行
     * @param startCol 合并开始列
     * @param endRow   合并结束行
     * @param endCol   合并结束列
     * @return 单元格对象
     */
    private Cell getMergedCell(Sheet sheet, int startRow, int startCol, int endRow, int endCol) {
        sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCol, endCol));
        return getCell(getRow(sheet, startRow), startCol);
    }

    /**
     * 获取工作簿中的工作表
     *
     * @param workbook  工作簿对象
     * @param sheetName 工作表名称
     * @return 工作表对象
     */
    private Sheet getSheet(Workbook workbook, String sheetName) {
        return workbook.getSheet(sheetName) == null ? workbook.createSheet(sheetName) : workbook.getSheet(sheetName);
    }

    /**
     * 获取工作表中的行
     *
     * @param sheet    工作表
     * @param rowIndex 行坐标
     * @return 行对象
     */
    private Row getRow(Sheet sheet, int rowIndex) {
        return sheet.getRow(rowIndex) == null ? sheet.createRow(rowIndex) : sheet.getRow(rowIndex);
    }

    /**
     * 获取单元格对象
     *
     * @param row       单元格所在行
     * @param cellIndex 单元格纵坐标
     * @return 单元格对象
     */
    private Cell getCell(Row row, int cellIndex) {
        return row.getCell(cellIndex) == null ? row.createCell(cellIndex) : row.getCell(cellIndex);
    }


}
