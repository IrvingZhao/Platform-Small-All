package cn.irving.zhao.util.poi.formatter;

/**
 * 单元格数据格式化
 */
@FunctionalInterface
public interface CellDataFormatter {

    /**
     * 单元格数据格式化方法
     * <ul>
     * <li>列坐标可使用{@link #getColCharIndex}进行转换</li>
     * <li>excel行坐标=rowIndex+1</li>
     * </ul>
     *
     * @param source   源数据
     * @param rowIndex 当前单元格行坐标
     * @param colIndex 当前单元格列坐标
     * @return 格式化后数据
     */
    Object format(Object source, int rowIndex, int colIndex);

    /**
     * 获得列字母坐标
     *
     * @param col 列数字坐标，从0开始
     * @return 列字母坐标
     */
    default String getColCharIndex(int col) {
        char[] code = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int temp = col;
        StringBuilder result = new StringBuilder();
        while (temp >= 0) {
            result.append(code[(temp % 26)]);
            temp = (temp / 26) - 1;
        }
        result.reverse();
        return result.toString();
    }
}
