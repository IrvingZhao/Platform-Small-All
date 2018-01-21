package cn.irving.zhao.util.poi.formatter;

/**
 * 单元表名字格式化
 */
@FunctionalInterface
public interface SheetNameFormatter {

    /**
     * 格式化工作表名称
     *
     * @param sheetName 工作表原有名称
     * @param loopIndex 循环第几次
     * @return 格式化后名称
     */
    String getSheetName(String sheetName, int loopIndex);

}
