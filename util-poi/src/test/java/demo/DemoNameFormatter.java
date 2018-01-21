package demo;

import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;

/**
 * Created by irving on 2017/8/4.
 */
public class DemoNameFormatter implements SheetNameFormatter {
    @Override
    public String getSheetName(String sheetName, int loopIndex) {
        return sheetName + "__" + (loopIndex + 1);
    }
}
