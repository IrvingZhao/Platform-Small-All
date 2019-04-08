package cn.irving.zhao.util.poi;

import cn.irving.zhao.util.poi.config.*;
import cn.irving.zhao.util.poi.enums.CellDataType;
import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.enums.WorkbookType;
import cn.irving.zhao.util.poi.exception.ExportException;
import cn.irving.zhao.util.poi.inter.IWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.*;

/**
 * POI 工具包
 */
public final class ExcelExporter {

    private ExcelExporter() {

    }

    private static final ExcelExporter me = new ExcelExporter();

    public static void export(IWorkbook data, OutputStream outputStream, String template) throws ExportException {
        try {
            export(data, outputStream, me.getTemplateStreamByPath(template));
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
    public static void export(IWorkbook data, String output, InputStream template) throws ExportException {
        try {
            File file = me.createOutputFile(output);
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
    public static void export(IWorkbook data, String output, String template) throws ExportException {
        try {
            File file = me.createOutputFile(output);
            FileOutputStream outputStream = new FileOutputStream(file);
            export(data, outputStream, me.getTemplateStreamByPath(template));
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
                templateStream = ExcelExporter.class.getResourceAsStream(path);
            }
        }
        return templateStream;
    }

    private File createOutputFile(String output) throws IOException {
        File file = new File(output);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new ExportException("文件：" + output + "创建失败");
            }
        }
        return file;
    }


    /**
     * 导出对象
     *
     * @param data     数据
     * @param output   输出流位置
     * @param template 模板输入流
     */
    public static void export(IWorkbook data, OutputStream output, InputStream template) throws ExportException {
        try {
            WorkBookConfig workBookConfig = WorkBookConfigFactory.getWorkBookConfig(data);

            Workbook workbook = export(workBookConfig, data, template);

            workbook.setForceFormulaRecalculation(true);
            workbook.write(output);

            output.flush();
        } catch (IOException e) {
            throw new ExportException("流异常", e);
        }
    }

    /**
     * 获取WorkBook方法
     *
     * @param workBookConfig 导出配置信息
     * @param data           被导出的数据
     * @param template       模板数据流
     * @return 工作簿对象
     */
    public static Workbook export(WorkBookConfig workBookConfig, Object data, InputStream template) {
        WorkbookType type = workBookConfig.getWorkbookType();
        Workbook result = type.getWorkbook(template);

        //获得引入sheet配置信息
        List<SheetConfig> sheetConfigs = workBookConfig.getSheetConfigs();
        if (sheetConfigs != null) {
            sheetConfigs.forEach((itemSheetConfig) -> {
                try {
                    //循环写入引入sheet
                    me.writeSheet(result, null, itemSheetConfig, data, 0, 0);
                } catch (ExportException e) {
                    throw new RuntimeException("单元表" + itemSheetConfig.getName() + "导出失败", e);
                }
            });
        }

        return result;
    }

    /**
     * 写入工作表
     *
     * @param workbook    工作簿
     * @param sheet       当前配置所对应的工作表
     * @param sheetConfig 当前工作表配置信息
     * @param source      当前待写入对象
     * @param rowIv       行修正量
     * @param colIv       列修正量
     */
    private void writeSheet(Workbook workbook, Sheet sheet, SheetConfig sheetConfig, Object source, int rowIv, int colIv) {
        //设置表格位移空间
        if (sheetConfig.getSheetType() == SheetType.INNER) {//内部引入表格，位移叠加
            //如果是内嵌工作表，添加行、列修正量
            rowIv += sheetConfig.getBaseRow();
            colIv += sheetConfig.getBaseCol();
        } else if (sheetConfig.getSheetType() == SheetType.OUTER) {//外部引入，位置重置
            //设置 表格修正量
            rowIv = sheetConfig.getBaseRow();
            colIv = sheetConfig.getBaseCol();
        }
        RepeatConfig repeatConfig = sheetConfig.getRepeatConfig();
        if (repeatConfig == null) {
            //非循环写入工作表
            Object data = sheetConfig.getData(source);
            Sheet writeDataSheet = null;
            //根据工作表配置创建具体写入工作表对象
            if (sheetConfig.getSheetType() == SheetType.INNER) {
                writeDataSheet = sheet;
            } else if (sheetConfig.getSheetType() == SheetType.OUTER) {
                writeDataSheet = this.getWriteSheet(workbook, sheetConfig, data, 0);
            }
            //将数据写入工作表
            this.writeSheetData(workbook, writeDataSheet, sheetConfig, data, rowIv, colIv);
        } else {
            int max = repeatConfig.getMax();
            int currentIndex = 0;
            Object data = sheetConfig.getData(source);
            Sheet writeDataSheet = null;
            //检查数据是否为一个集合类型
            if (data instanceof Iterable) {
                //迭代循环次数
                for (Object itemData : ((Iterable) data)) {
                    if (max > 0 && currentIndex >= max) {//最大循环次数判断
                        break;
                    }
                    //根据配置配置信息，创建对应工作表
                    if (sheetConfig.getSheetType() == SheetType.INNER) {
                        writeDataSheet = sheet;
                    } else if (sheetConfig.getSheetType() == SheetType.OUTER) {
                        writeDataSheet = this.getWriteSheet(workbook, sheetConfig, itemData, currentIndex);
                    }
                    //写入数据
                    this.writeSheetData(workbook, writeDataSheet, sheetConfig, itemData, rowIv, colIv);

                    //计算循环位移量
                    int[] ivs = repeatConfig.getNextIv(currentIndex, source);
                    rowIv += ivs[0];
                    colIv += ivs[1];

                    currentIndex++;
                }
            }
        }
    }

    private void writeSheetData(Workbook workbook, Sheet sheet, SheetConfig sheetConfig, Object data, int rowIv, int colIv) {

        //写入单元格
        sheetConfig.getCellConfigs().forEach((item) -> this.writeCell(workbook, sheet, item, data, rowIv, colIv));

        //写入引用工作表
        sheetConfig.getRefSheetConfigs().forEach((item) -> this.writeSheet(workbook, sheet, item, data, rowIv, colIv));
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
                this.writeCellData(workbook, sheet, cellConfig, cellData, rowIv, colIv, data);//单一数据写入
            } catch (ExportException e) {
                throw new ExportException("写入第" + cellConfig.getRowIndex() + "行，第" + cellConfig.getCellIndex() + "列数据失败", e);
            }
        } else {
            int currentIndex = 0;
            int max = repeatConfig.getMax();
            if (cellData instanceof Iterable) {//检查循环写入的数据类型是否可迭代
                try {
                    for (Object dataItem : ((Iterable) cellData)) {//迭代数据
                        if (max > 0 && currentIndex >= max) {//最大循环次数判断
                            break;
                        }
                        this.writeCellData(workbook, sheet, cellConfig, dataItem, rowIv, colIv, data);//写入单元格数据

                        //设置循环位移量
                        int[] ivs = repeatConfig.getNextIv(currentIndex, data);
                        rowIv += ivs[0];
                        colIv += ivs[1];

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
    private void writeCellData(Workbook workbook, Sheet sheet, CellConfig cellConfig, Object cellData, int rowIv, int colIv, Object cellDataSource) throws ExportException {//写入cell 数据
        MergedConfig mergedConfig = cellConfig.getMergedConfig();
        Cell cell;
        if (mergedConfig == null) {//检查是否需要合并单元格
            //根据行坐标、列坐标、行权重、列权重获取单元格
            cell = this.getCell(getRow(sheet, cellConfig.getRowIndex() + rowIv), cellConfig.getCellIndex() + colIv);
        } else {
            //根据合并单元格配置，行权重，列权重获取单元格
            int[] mergePosition = mergedConfig.getMergedPosition(cellDataSource);
            cell = this.getMergedCell(sheet,
                    mergePosition[0] + rowIv,
                    mergePosition[1] + colIv,
                    mergePosition[2] + rowIv,
                    mergePosition[3] + colIv);
        }
        CellDataType dataType = cellConfig.getDataType();//数据类型
        FormatterConfig formatterConfig = cellConfig.getFormatterConfig();//数据格式化配置
        if (formatterConfig != null) {//写入格式化配置
            String formatterPattern = formatterConfig.getFormatString();
            if (formatterPattern != null && !"".equals(formatterPattern)) {//设置excel中数据格式化的格式
                this.getCellStyle(cell, workbook)
                        .setDataFormat(workbook.createDataFormat().getFormat(formatterPattern));
            }
            if (formatterConfig.getCellDataFormatter() != null) {//如果包含数据格式化方法，执行格式化
                cellData = formatterConfig.getCellDataFormatter().format(cellData, cellConfig, rowIv, colIv);
            }
        }
        if (dataType == CellDataType.AUTO) {//根据数据类型，执行不同的数据写入逻辑
            this.writeAutoTypeCell(cell, cellData);
        } else {
            this.writeCustomTypeCell(cell, dataType, cellData);
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
            if (cellData instanceof Calendar) {
                cell.setCellValue((Calendar) cellData);
            } else if (cellData instanceof Date) {
                cell.setCellValue((Date) cellData);
            } else {
                throw new ExportException(cellData.getClass() + "不是一个Date或Calendar对象");
            }
        } else if (dataType == CellDataType.NUMERIC) {
            if (cellData instanceof Number) {
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
        if (cellData instanceof Calendar) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((Calendar) cellData);
        } else if (cellData instanceof Date) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue((Date) cellData);
        } else if (cellData instanceof Number) {//如果是数字类型，调用doubleValue 方法
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((Number) cellData).doubleValue());
        } else if (cellData instanceof RichTextString) {
            cell.setCellValue((RichTextString) cellData);
        } else {//其他类型，调用String.valueOf 转换成字符串后输出
            cell.setCellType(CellType.STRING);
            cell.setCellValue(String.valueOf(cellData));
        }
    }

    /**
     * 获取写入配置信息
     *
     * @param workbook    工作簿对象
     * @param sheetConfig 工作表配置
     * @param data        工作表对应数据
     * @param loopIndex   循环索引
     */
    private Sheet getWriteSheet(Workbook workbook, SheetConfig sheetConfig, Object data, Integer loopIndex) {
        String sheetName;
        if (sheetConfig.getSheetNameFormatter() != null) {//如果有工作表对象格式化，则使用工作表格式化对象进行工作表名称格式化
            sheetName = sheetConfig.getSheetNameFormatter().getSheetName(data, sheetConfig.getName(), loopIndex);
        } else {
            sheetName = sheetConfig.getName();
        }
        return getSheet(workbook, sheetName);
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
