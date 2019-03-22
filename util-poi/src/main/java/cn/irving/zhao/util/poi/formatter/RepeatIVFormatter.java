package cn.irving.zhao.util.poi.formatter;

/**
 * 循环的工作表或单元格，自定义行列叠加方式
 */
@FunctionalInterface
public interface RepeatIVFormatter {

    /**
     * 获得 循环中行列 修正量
     *
     * @param loop   循环次数
     * @param source 被写入的对象的父对象
     * @return 当前循环需要追加的 行 列 修正量  第0个 行，第1个 列
     */
    int[] getRowColIv(int loop, Object source);

}
