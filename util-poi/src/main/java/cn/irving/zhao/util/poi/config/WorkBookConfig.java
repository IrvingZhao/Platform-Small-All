package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.enums.WorkbookType;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作簿配置信息
 */
public class WorkBookConfig {
    @Getter
    private final List<SheetConfig> sheetConfigs = new ArrayList<>();//工作簿内的工作表配置信息

    private SheetConfig defaultSheetConfig;

    private String defaultSheetName;

    private SheetNameFormatter defaultSheetNameFormatter;

    @Getter
    private WorkbookType workbookType;

    private WorkBookConfig() {
    }

    public static WorkBookConfig createWorkBookConfig(String defaultSheetName, WorkbookType workbookType, SheetNameFormatter defaultSheetNameFormatter) {
        var result = new WorkBookConfig();
        result.defaultSheetName = defaultSheetName;
        result.workbookType = workbookType;
        result.defaultSheetNameFormatter = defaultSheetNameFormatter;
        return result;
    }

    void addCellConfig(CellConfig cellConfig) {
        this.createDefaultSheet();
        defaultSheetConfig.addCellConfig(cellConfig);
    }

    void addSheetConfig(SheetConfig sheetConfig) {
        if (sheetConfig.getSheetType() == SheetType.INNER) {
            this.createDefaultSheet();
            defaultSheetConfig.addSheetConfig(sheetConfig);
        } else {
            sheetConfigs.add(sheetConfig);
        }
    }

    private void createDefaultSheet() {
        if (defaultSheetConfig == null) {
            synchronized (this) {
                if (defaultSheetConfig == null) {
                    defaultSheetConfig = SheetConfig.createOuterSheet(defaultSheetName, defaultSheetNameFormatter, (item) -> item);
                    sheetConfigs.add(0, defaultSheetConfig);//添加至第一个
                }
            }
        }
    }

}
