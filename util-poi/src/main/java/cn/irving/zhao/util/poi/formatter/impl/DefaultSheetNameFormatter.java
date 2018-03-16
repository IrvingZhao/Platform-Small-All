package cn.irving.zhao.util.poi.formatter.impl;

import cn.irving.zhao.util.poi.config.SheetConfig;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;

/**
 * 默认Sheet名称格式化对象
 *
 * @author Irving Zhao
 */
public class DefaultSheetNameFormatter implements SheetNameFormatter {
    @Override
    public String getSheetName(Object data, String sheetName, int loopIndex) {
        return sheetName;
    }

    @Override
    public boolean checkSheet(Object obj, SheetConfig sheetConfig, String sheetName, int loopIndex) {
        return sheetConfig.getName().equals(sheetName);
    }
}
