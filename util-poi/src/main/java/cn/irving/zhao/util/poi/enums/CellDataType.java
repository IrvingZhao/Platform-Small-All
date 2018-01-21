package cn.irving.zhao.util.poi.enums;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * 单元格数据类型
 */
public enum CellDataType {
    /**
     * 自动获取
     */
    AUTO(CellType._NONE),
    /**
     * 数字
     */
    NUMERIC(CellType.NUMERIC),
    /**
     * 时间
     */
    DATE(CellType.NUMERIC),
    /**
     * 字符串
     */
    STRING(CellType.STRING),
    /**
     * 表达式
     */
    FORMULA(CellType.FORMULA);

    CellDataType(CellType cellType) {
        this.cellType = cellType;
    }

    private final CellType cellType;

    public CellType getCellType() {
        return cellType;
    }
}
