package cn.irving.zhao.util.poi.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 单元格合并配置信息
 */
@Getter
@Setter
public class MergedConfig {

    public MergedConfig(int startRowIndex, int startColIndex, int endRowIndex, int endColIndex) {
        this.startRowIndex = startRowIndex;
        this.startColIndex = startColIndex;
        this.endRowIndex = endRowIndex;
        this.endColIndex = endColIndex;
    }

    private final int startRowIndex;
    private final int startColIndex;

    private final int endRowIndex;
    private final int endColIndex;

}