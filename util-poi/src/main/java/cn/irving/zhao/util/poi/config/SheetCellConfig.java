package cn.irving.zhao.util.poi.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作表内单元格配置信息
 */
public class SheetCellConfig {
    private final List<CellConfig> cellConfigs = new ArrayList<>();//单元格配置信息

    private final List<SheetConfig> refSheetConfigs = new ArrayList<>();//关联单元表配置信息

    public SheetCellConfig() {
    }

    public SheetCellConfig addCellConfig(CellConfig cellConfig) {
        cellConfigs.add(cellConfig);
        return this;
    }

    public SheetCellConfig addSheetConfig(SheetConfig sheetConfig) {
        refSheetConfigs.add(sheetConfig);
        return this;
    }

    public List<CellConfig> getCellConfigs() {
        return cellConfigs;
    }

    public List<SheetConfig> getRefSheetConfigs() {
        return refSheetConfigs;
    }

}