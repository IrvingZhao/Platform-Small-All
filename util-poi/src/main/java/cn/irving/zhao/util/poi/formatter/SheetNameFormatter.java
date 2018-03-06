package cn.irving.zhao.util.poi.formatter;

import cn.irving.zhao.util.poi.config.SheetConfig;

/**
 * 单元表名字格式化
 */
public interface SheetNameFormatter {

    /**
     * 格式化工作表名称
     *
     * @param data      工作簿对应的数据
     * @param sheetName 工作表原有名称
     * @param loopIndex 循环第几次
     * @return 格式化后名称
     */
    String getSheetName(Object data, String sheetName, int loopIndex);

    /**
     * 检查sheet是否为可读取sheet，并可设置读取对象中的内容
     *
     * @param obj         读取对象
     * @param sheetConfig 工作簿配置信息
     * @param sheetName   工作簿名称
     * @param loopIndex   循环第几次
     */
    boolean checkSheet(Object obj, SheetConfig sheetConfig, String sheetName, int loopIndex);

}
