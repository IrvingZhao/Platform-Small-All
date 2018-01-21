package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.Cell;
import cn.irving.zhao.util.poi.annonation.Formatter;
import cn.irving.zhao.util.poi.annonation.MergedRegion;
import cn.irving.zhao.util.poi.annonation.Repeatable;
import cn.irving.zhao.util.poi.enums.CellDataType;

import java.util.function.Function;

/**
 * 单元格配置信息
 */
public class CellConfig {

    CellConfig(Cell cell, Repeatable repeatable, MergedRegion mergedRegion, Formatter formatter, Function<Object, Object> dataGetter) {
        this(cell.rowIndex(),
                cell.colIndex(),
                cell.dataType(),
                repeatable == null ? null : new RepeatConfig(repeatable),
                mergedRegion == null ? null : new MergedConfig(cell.rowIndex(), cell.colIndex(), mergedRegion.endRowIndex(), mergedRegion.endColIndex()),
                formatter == null ? null : new FormatterConfig(formatter),
                dataGetter);
    }

    public CellConfig(int rowIndex, int cellIndex, CellDataType dataType, RepeatConfig repeatConfig, MergedConfig mergedConfig, FormatterConfig formatterConfig, Function<Object, Object> dataGetter) {
        this.rowIndex = rowIndex;
        this.cellIndex = cellIndex;
        this.dataType = dataType;
        this.repeatConfig = repeatConfig;
        this.mergedConfig = mergedConfig;
        this.formatterConfig = formatterConfig;
        this.dataGetter = dataGetter;
    }

    private final int rowIndex;//行坐标
    private final int cellIndex;//列坐标

    private final CellDataType dataType;//单元格数据类型

    private final RepeatConfig repeatConfig;//循环配置信息

    private final MergedConfig mergedConfig;//合并单元格配置

    private final FormatterConfig formatterConfig;//单元格格式化信息

    private final Function<Object, Object> dataGetter;//数据获取器

    public Object getData(Object source) {
        return dataGetter.apply(source);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getCellIndex() {
        return cellIndex;
    }

    public RepeatConfig getRepeatConfig() {
        return repeatConfig;
    }

    public MergedConfig getMergedConfig() {
        return mergedConfig;
    }

    public CellDataType getDataType() {
        return dataType;
    }

    public FormatterConfig getFormatterConfig() {
        return formatterConfig;
    }
}
