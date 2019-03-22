package cn.irving.zhao.util.poi.formatter;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 自定义循环检查
 */
@FunctionalInterface
public interface RepeatCheck {

    /**
     * 检查是否还可继续读取
     *
     * @param sheet 工作表对象
     * @param loop  循环索引，从0开始
     * @param rowIv 循环中 行 偏移量
     * @param colIv 循环中 列 偏移量
     * @return true 可继续读取，false 不可继续读取
     */
    boolean checkRepeat(Sheet sheet, int loop, int rowIv, int colIv);

}
