package cn.irving.zhao.util.poi.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作簿配置信息
 */
@Getter
@Setter
public class WorkBookConfig {

    private final List<SheetConfig> sheetConfigs = new ArrayList<>();//工作簿内的工作表配置信息

    private SheetConfig defaultSheetConfig;//默认工作表配置信息

    public void addSheetConfig(SheetConfig sheetConfig) {
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

}
