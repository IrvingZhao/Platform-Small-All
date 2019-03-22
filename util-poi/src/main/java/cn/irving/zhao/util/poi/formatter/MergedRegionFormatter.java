package cn.irving.zhao.util.poi.formatter;

@FunctionalInterface
public interface MergedRegionFormatter {

    /**
     * 返回合并单元格结束坐标
     *
     * @param source 正在写入单元格的数据对象的所属对象
     * @return 单元格坐标，第0个 行坐标，第1个 列坐标
     */
    int[] endIndex(Object source);

}
