package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.Cell;
import cn.irving.zhao.util.poi.annonation.MergedFormat;
import cn.irving.zhao.util.poi.annonation.MergedPosition;
import cn.irving.zhao.util.poi.formatter.FormatterFactory;
import cn.irving.zhao.util.poi.formatter.MergedRegionFormatter;

/**
 * 单元格合并配置信息
 */
public class MergedConfig {

    private static FormatterFactory<MergedRegionFormatter> factory = FormatterFactory.getFormatterFactory(MergedRegionFormatter.class);

    MergedConfig(Cell cell, MergedPosition position) {
        this.startRowIndex = cell.rowIndex();
        this.startColIndex = cell.colIndex();
        this.endRowIndex = position.endRowIndex();
        this.endColIndex = position.endColIndex();
    }

    MergedConfig(Cell cell, MergedFormat format) {
        this.startRowIndex = cell.rowIndex();
        this.startColIndex = cell.colIndex();
        this.formatter = factory.getFormatter(format.value());
    }

    MergedConfig(int startRowIndex, int startColIndex, int endRowIndex, int endColIndex) {
        this.startRowIndex = startRowIndex;
        this.startColIndex = startColIndex;
        this.endRowIndex = endRowIndex;
        this.endColIndex = endColIndex;
    }

    MergedConfig(int startRowIndex, int startColIndex, MergedRegionFormatter formatter) {
        this.startRowIndex = startRowIndex;
        this.startColIndex = startColIndex;
        this.formatter = formatter;
    }

    private int startRowIndex;
    private int startColIndex;

    private int endRowIndex;
    private int endColIndex;

    private MergedRegionFormatter formatter;

    /**
     * 获得合并坐标
     *
     * @return 合并坐标  [开始行，开始列，结束行，结束列]
     */
    public int[] getMergedPosition(Object source) {
        if (this.formatter == null) {
            return new int[]{startRowIndex, startColIndex, endRowIndex, endColIndex};
        } else {
            var endPosition = this.formatter.endIndex(source);
            return new int[]{startRowIndex, startColIndex, endPosition[0], endPosition[1]};
        }
    }

}