package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.enums.WorkbookType;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作簿配置信息
 */
public class WorkBookConfig {
    @Getter
    private final List<SheetConfig> sheetConfigs = new ArrayList<>();//工作簿内的工作表配置信息

    private SheetConfig defaultSheetConfig;//默认工作表配置缓存位置

    private String defaultSheetName;//默认工作表名称

    private SheetNameFormatter defaultSheetNameFormatter;//默认工作表名称格式化对象

    @Getter
    private WorkbookType workbookType;//工作簿类型

    private WorkBookConfig() {
    }

    /**
     * 创建工作簿配置信息，工作表名称使用位置为：根对象中包含的Inner工作表配置和Cell配置项
     *
     * @param workbookType              工作簿类型
     * @param defaultSheetName          工作簿默认工作表名称
     * @param defaultSheetNameFormatter 工作簿默认工作表名称格式化对象
     */
    public static WorkBookConfig createWorkBookConfig(WorkbookType workbookType, String defaultSheetName, SheetNameFormatter defaultSheetNameFormatter) {
        WorkBookConfig result = new WorkBookConfig();
        result.defaultSheetName = defaultSheetName;
        result.workbookType = workbookType;
        result.defaultSheetNameFormatter = defaultSheetNameFormatter;
        return result;
    }

    /**
     * 添加单元格配置，写入 工作簿默认工作表中
     *
     * @param cellConfig 单元格配置
     * @return 当前对象
     */
    public WorkBookConfig addCellConfig(CellConfig cellConfig) {
        this.createDefaultSheet();
        defaultSheetConfig.addCellConfig(cellConfig);
        return this;
    }

    /**
     * 添加工作表配置，如果工作表的类型为{@link SheetType#INNER}，则写入工作簿默认工作表中
     *
     * @param sheetConfig 工作表配置信息
     * @return 当前对象
     */
    public WorkBookConfig addSheetConfig(SheetConfig sheetConfig) {
        if (sheetConfig.getSheetType() == SheetType.INNER) {
            this.createDefaultSheet();
            defaultSheetConfig.addSheetConfig(sheetConfig);
        } else {
            sheetConfigs.add(sheetConfig);
        }
        return this;
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
