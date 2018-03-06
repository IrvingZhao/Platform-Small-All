package cn.irving.zhao.util.poi.formatter.impl;

import cn.irving.zhao.util.poi.config.SheetConfig;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;

/**
 * @author zhaojn1
 * @version DefaultSheetNameFormatter.java, v 0.1 2018/3/6 zhaojn1
 * @project userProfile
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
