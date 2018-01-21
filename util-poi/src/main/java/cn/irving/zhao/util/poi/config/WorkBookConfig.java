package cn.irving.zhao.util.poi.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作簿配置信息
 */
public class WorkBookConfig {

    private List<SheetConfig> sheetConfigs;//工作簿内的工作表配置信息

    private SheetConfig defaultSheetConfig;//默认工作表配置信息

    public void addSheetConfig(SheetConfig sheetConfig) {
        if (sheetConfigs == null) {
            synchronized (this) {
                if (sheetConfigs == null) {
                    sheetConfigs = new ArrayList<>();
                }
            }
        }
        sheetConfigs.add(sheetConfig);
    }

    public void addDefaultSheetCellConfig(CellConfig cellConfig) {
        if (defaultSheetConfig == null) {
            synchronized (this) {
                if (defaultSheetConfig == null) {
                    defaultSheetConfig = new SheetConfig();
                }
            }
        }
        defaultSheetConfig.addCellConfig(cellConfig);
    }

    public void addDefaultInnerSheetConfig(SheetConfig sheetConfig) {
        if (defaultSheetConfig == null) {
            synchronized (this) {
                if (defaultSheetConfig == null) {
                    defaultSheetConfig = new SheetConfig();
                }
            }
        }
        defaultSheetConfig.addSheetConfig(sheetConfig);
    }

    public List<SheetConfig> getSheetConfigs() {
        return sheetConfigs;
    }

    public void setSheetConfigs(List<SheetConfig> sheetConfigs) {
        this.sheetConfigs = sheetConfigs;
    }

    public SheetConfig getDefaultSheetConfig() {
        return defaultSheetConfig;
    }

    public void setDefaultSheetConfig(SheetConfig defaultSheetConfig) {
        this.defaultSheetConfig = defaultSheetConfig;
    }
}
