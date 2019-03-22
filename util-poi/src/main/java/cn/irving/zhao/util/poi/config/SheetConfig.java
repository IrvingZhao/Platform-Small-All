package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.Sheet;
import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.exception.ExportException;
import cn.irving.zhao.util.poi.formatter.RepeatCheck;
import cn.irving.zhao.util.poi.formatter.RepeatIVFormatter;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;
import cn.irving.zhao.util.poi.formatter.FormatterFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 工作表配置信息
 */
@Getter
@Setter
public class SheetConfig {

    private static FormatterFactory<SheetNameFormatter> factory = FormatterFactory.getFormatterFactory(SheetNameFormatter.class);

    private SheetConfig() {
    }

    static SheetConfig createSheetConfig(Sheet sheet, Function<Object, Object> dataGetter, BiConsumer<Object, Object> dataSetter, Class<?> dataType) {
        var result = new SheetConfig();
        result.sheetType = sheet.type();

        result.dataGetter = dataGetter;
        result.dataSetter = dataSetter;
        result.dataType = dataType;

        result.baseRow = sheet.baseRow();
        result.baseCol = sheet.baseCol();

        if (sheet.type() == SheetType.OUTER) {
            result.name = sheet.name();
            if (sheet.nameFormatter() != SheetNameFormatter.class) {
                result.sheetNameFormatter = factory.getFormatter(sheet.nameFormatter());
            }
            if ("".equals(result.name) && result.sheetNameFormatter == null) {
                throw new ExportException("类型为OUTER的Sheet需制定name或SheetNameFormat");
            }
        }

        return result;
    }

    static SheetConfig createOuterSheet(String name, SheetNameFormatter sheetNameFormatter, Function<Object, Object> dataGetter) {
        var result = new SheetConfig();

        result.sheetType = SheetType.OUTER;
        result.name = name;
        result.sheetNameFormatter = sheetNameFormatter;
        result.dataGetter = dataGetter;
        result.baseRow = 0;
        result.baseCol = 0;

        return result;
    }

    /**
     * 创建内联工作表配置，工作表原点坐标为0,0，可通过{@link #setStartIndex(int, int)} 进行设置
     *
     * @param dataGetter 数据获取器
     * @param dataSetter 数据设置器，读取excel时使用
     * @param dataType   数据类型，读取excel时使用
     */
    public static SheetConfig createInnerSheet(Function<Object, Object> dataGetter, BiConsumer<Object, Object> dataSetter, Class<?> dataType) {
        SheetConfig result = new SheetConfig();

        result.baseRow = 0;
        result.baseCol = 0;
        result.sheetType = SheetType.INNER;
        result.dataGetter = dataGetter;
        result.dataSetter = dataSetter;
        result.dataType = dataType;

        return result;
    }

    /**
     * 创建外部引入工作表，name或nameFormatter只能有一个为空
     *
     * @param name          工作表名称
     * @param nameFormatter 工作表名称格式化对象
     * @param dataGetter    数据获取器
     * @param dataSetter    数据设置器，读取excel时使用
     * @param dataType      数据类型，读取excel时使用
     */
    public static SheetConfig createOuterSheet(String name, SheetNameFormatter nameFormatter, Function<Object, Object> dataGetter, BiConsumer<Object, Object> dataSetter, Class<?> dataType) {
        SheetConfig result = new SheetConfig();

        if ((name == null || "".equals(name)) && nameFormatter == null) {
            throw new ExportException("类型为OUTER的Sheet需制定name或SheetNameFormat");
        }

        result.sheetType = SheetType.OUTER;
        result.name = name;
        result.sheetNameFormatter = nameFormatter;
        result.dataGetter = dataGetter;
        result.dataSetter = dataSetter;
        result.dataType = dataType;

        return result;
    }

    /**
     * 工作表循环配置
     *
     * @param rowIv          每次循环行叠加量
     * @param colIv          每次循环列叠加量
     * @param max            最大循环次数，-1为不限制
     * @param collectionType 数据类型，读取excel时使用
     * @param itemType       单个数据类型，读取excel时使用
     * @param check          循环结束检查器，读取excel时使用
     */
    public SheetConfig setRepeatConfig(int rowIv, int colIv, int max, Class<? extends Collection> collectionType, Class<?> itemType, RepeatCheck check) {
        this.repeatConfig = new RepeatConfig(rowIv, colIv, max, collectionType, itemType, check);
        return this;
    }

    /**
     * 工作表循环配置
     *
     * @param formatter      行列叠加计算器
     * @param max            最大循环次数，-1为不限制
     * @param collectionType 数据类型，读取excel时使用
     * @param itemType       单个数据类型，读取excel时使用
     * @param check          循环结束检查器，读取excel时使用
     */
    public SheetConfig setRepeatConfig(RepeatIVFormatter formatter, int max, Class<? extends Collection> collectionType, Class<?> itemType, RepeatCheck check) {
        this.repeatConfig = new RepeatConfig(formatter, max, collectionType, itemType, check);
        return this;
    }

    /**
     * 设置工作表原点坐标
     *
     * @param row 行坐标
     * @param col 列坐标
     */
    public SheetConfig setStartIndex(int row, int col) {
        this.baseRow = row;
        this.baseCol = col;
        return this;
    }

    /**
     * 添加单元格配置信息
     *
     * @param cellConfig 单元格配置信息
     */
    public SheetConfig addCellConfig(CellConfig cellConfig) {
        cellConfigs.add(cellConfig);
        return this;
    }

    /**
     * 添加 工作表配置信息
     *
     * @param sheetConfig 工作表配置信息
     */
    public SheetConfig addSheetConfig(SheetConfig sheetConfig) {
        refSheetConfigs.add(sheetConfig);
        return this;
    }

    public Object getData(Object source) {
        return dataGetter.apply(source);
    }

    public void setData(Object source, Object data) {
        dataSetter.accept(source, data);
    }

    void setCellAndRefSheet(SheetConfig config){
        this.cellConfigs.addAll(config.cellConfigs);
        this.refSheetConfigs.addAll(config.refSheetConfigs);
    }

    private SheetType sheetType = SheetType.OUTER;//工作表类型

    private String name;//工作表名称

    private SheetNameFormatter sheetNameFormatter;//工作表名称格式化对象

    private int baseRow;//工作表位移横坐标
    private int baseCol;//工作表位移列坐标

    private RepeatConfig repeatConfig;//循环配置项

    private Function<Object, Object> dataGetter;

    private BiConsumer<Object, Object> dataSetter;

    private Class<?> dataType;

    private final List<CellConfig> cellConfigs = new ArrayList<>();//单元格配置信息

    private final List<SheetConfig> refSheetConfigs = new ArrayList<>();//关联单元表配置信息

}


