package cn.irving.zhao.util.poi.formatter;

import cn.irving.zhao.util.poi.config.CellConfig;
import org.apache.poi.ss.usermodel.Cell;

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
     * @param source     源数据
     * @param cellConfig 单元格配置
     * @param rowIv      行偏移量
     * @param colIv      列偏移量
     * @return 格式化后数据
     */
    Object format(Object source, CellConfig cellConfig, int rowIv, int colIv);

    /**
     * 单元格数据解析方法
     *
     * @param source     存放对象
     * @param cellConfig 单元格配置信息
     * @param rowIv      行偏移量
     * @param colIv      列偏移量
     * @return 存放数据对象
     */
    default Object parse(Object source, CellConfig cellConfig, int rowIv, int colIv) {
        return source;
    }

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
