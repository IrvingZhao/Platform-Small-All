package formatter;

import cn.irving.zhao.util.poi.config.SheetConfig;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;

/**
 * Created by irving on 2017/8/4.
 */
public class DemoNameFormatter implements SheetNameFormatter {

    @Override
    public String getSheetName(Object data, String sheetName, int loopIndex) {
        return sheetName + "__" + (loopIndex + 1);
    }

    @Override
    public boolean checkSheet(Object obj, SheetConfig sheetConfig, String sheetName, int loopIndex) {
        return (sheetConfig.getName() + "__" + (loopIndex + 1)).equals(sheetName);
    }
}
