package cn.irving.zhao.util.poi.config;

/**
 * 单元格合并配置信息
 */
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

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public int getStartColIndex() {
        return startColIndex;
    }

    public int getEndRowIndex() {
        return endRowIndex;
    }

    public int getEndColIndex() {
        return endColIndex;
    }

}