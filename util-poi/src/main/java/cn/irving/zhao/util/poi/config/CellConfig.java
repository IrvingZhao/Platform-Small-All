package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.*;
import cn.irving.zhao.util.poi.enums.CellDataType;
import cn.irving.zhao.util.poi.formatter.CellDataFormatter;
import cn.irving.zhao.util.poi.formatter.MergedRegionFormatter;
import cn.irving.zhao.util.poi.formatter.RepeatCheck;
import cn.irving.zhao.util.poi.formatter.RepeatIVFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 单元格配置信息
 */
@Getter
public class CellConfig {

    CellConfig(Cell cell, Repeatable repeatable, MergedFormat mergedFormat, MergedPosition mergedPosition, Formatter formatter,
               Function<Object, Object> dataGetter, BiConsumer<Object, Object> dataSetter, Class<?> type) {
        this.rowIndex = cell.rowIndex();
        this.cellIndex = cell.colIndex();
        this.dataType = cell.dataType();
        if (repeatable != null) {
            this.repeatConfig = new RepeatConfig(repeatable);
        }
        if (mergedFormat != null) {
            this.mergedConfig = new MergedConfig(cell, mergedFormat);
        } else if (mergedPosition != null) {
            this.mergedConfig = new MergedConfig(cell, mergedPosition);
        }
        if (formatter != null) {
            this.formatterConfig = new FormatterConfig(formatter);
        }
        this.dataGetter = dataGetter;
        this.dataSetter = dataSetter;
        this.type = type;
    }

    /**
     * @param rowIndex     行坐标，从0开始
     * @param colIndex     列坐标，从0开始
     * @param cellDataType 单元格数据类型
     * @param dataGetter   数据获取器
     * @param dataSetter   数据设置器，调用时传入两个参数，第一个为数据所属对象，第二个为数据
     * @param type         数据类型，需具备空构造函数用于初始化
     */
    public CellConfig(int rowIndex, int colIndex, CellDataType cellDataType, Function<Object, Object> dataGetter, BiConsumer<Object, Object> dataSetter, Class<?> type) {
        this.rowIndex = rowIndex;
        this.cellIndex = colIndex;
        this.dataType = cellDataType;
        this.dataGetter = dataGetter;
        this.dataSetter = dataSetter;
        this.type = type;
    }

    /**
     * 单元格循环配置
     *
     * @param rowIv          每次循环行叠加量
     * @param colIv          每次循环列叠加量
     * @param max            最大循环次数，-1为不限制
     * @param collectionType 数据类型，读取excel时使用
     * @param itemType       单个数据类型，读取excel时使用
     * @param check          循环结束检查器，读取excel时使用
     */
    public CellConfig setRepeatConfig(int rowIv, int colIv, int max, Class<? extends Collection> collectionType, Class<?> itemType, RepeatCheck check) {
        this.repeatConfig = new RepeatConfig(rowIv, colIv, max, collectionType, itemType, check);
        return this;
    }

    /**
     * 单元格循环配置
     *
     * @param formatter      行列叠加计算器
     * @param max            最大循环次数，-1为不限制
     * @param collectionType 数据类型，读取excel时使用
     * @param itemType       单个数据类型，读取excel时使用
     * @param check          循环结束检查器，读取excel时使用
     */
    public CellConfig setRepeatConfig(RepeatIVFormatter formatter, int max, Class<? extends Collection> collectionType, Class<?> itemType, RepeatCheck check) {
        this.repeatConfig = new RepeatConfig(formatter, max, collectionType, itemType, check);
        return this;
    }

    /**
     * 单元格合并配置信息
     *
     * @param endRowIndex 合并结束行坐标
     * @param endColIndex 合并结束列坐标
     */
    public CellConfig setMergedConfig(int endRowIndex, int endColIndex) {
        this.mergedConfig = new MergedConfig(this.rowIndex, this.cellIndex, endRowIndex, endColIndex);
        return this;
    }

    /**
     * 单元格合并配置信息
     *
     * @param format 单元格合并坐标计算器
     */
    public CellConfig setMeredConfig(MergedRegionFormatter format) {
        this.mergedConfig = new MergedConfig(this.rowIndex, this.cellIndex, format);
        return this;
    }

    /**
     * 单元格数据格式化配置信息
     *
     * @param formatString      单元格格式化字符串
     * @param cellDataFormatter 数据格式化对象
     */
    public CellConfig setFormatterConfig(String formatString, CellDataFormatter cellDataFormatter) {
        this.formatterConfig = new FormatterConfig(formatString, cellDataFormatter);
        return this;
    }

    /**
     * 单元格数据格式化配置信息
     *
     * @param formatString 单元格格式化字符串
     */
    public CellConfig setFormatterConfig(String formatString) {
        this.formatterConfig = new FormatterConfig(formatString);
        return this;
    }

    /**
     * 单元格数据格式化配置信息
     *
     * @param cellDataFormatter 数据格式化对象
     */
    public CellConfig setFormatterConfig(CellDataFormatter cellDataFormatter) {
        this.formatterConfig = new FormatterConfig(cellDataFormatter);
        return this;
    }

    /**
     * 获取数据
     */
    public Object getData(Object source) {
        return dataGetter.apply(source);
    }

    /**
     * 设置数据
     */
    public void setData(Object source, Object data) {
        dataSetter.accept(source, data);
    }

    private int rowIndex;//行坐标
    private int cellIndex;//列坐标

    private CellDataType dataType;//单元格数据类型

    private RepeatConfig repeatConfig;//循环配置信息

    private MergedConfig mergedConfig;//合并单元格配置

    private FormatterConfig formatterConfig;//单元格格式化信息

    private Function<Object, Object> dataGetter;//数据获取器

    private BiConsumer<Object, Object> dataSetter;//数据设置器

    private Class<?> type;//单元格对应属性类型，读取数据时使用

}
