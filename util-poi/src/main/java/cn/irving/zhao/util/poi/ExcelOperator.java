package cn.irving.zhao.util.poi;

import cn.irving.zhao.util.poi.config.RepeatConfig;
import cn.irving.zhao.util.poi.enums.Direction;
import cn.irving.zhao.util.poi.exception.ExportException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author Irving
 * @version ExcelOperator.java, v 0.1 2018/3/2
 */
public class ExcelOperator {

    /**
     * 获取单元格样式，单元格样式找不到时，自动创建新样式，并添加至单元格中
     *
     * @param cell     单元对象
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    protected CellStyle getCellStyle(Cell cell, Workbook workbook) {
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
    protected Cell getMergedCell(Sheet sheet, int startRow, int startCol, int endRow, int endCol) {
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
    protected Sheet getSheet(Workbook workbook, String sheetName) {
        return workbook.getSheet(sheetName) == null ? workbook.createSheet(sheetName) : workbook.getSheet(sheetName);
    }

    /**
     * 获取工作表中的行
     *
     * @param sheet    工作表
     * @param rowIndex 行坐标
     * @return 行对象
     */
    protected Row getRow(Sheet sheet, int rowIndex) {
        return sheet.getRow(rowIndex) == null ? sheet.createRow(rowIndex) : sheet.getRow(rowIndex);
    }

    /**
     * 获取单元格对象
     *
     * @param row       单元格所在行
     * @param cellIndex 单元格纵坐标
     * @return 单元格对象
     */
    protected Cell getCell(Row row, int cellIndex) {
        return row.getCell(cellIndex) == null ? row.createCell(cellIndex) : row.getCell(cellIndex);
    }

}
