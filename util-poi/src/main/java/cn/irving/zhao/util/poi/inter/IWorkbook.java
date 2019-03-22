package cn.irving.zhao.util.poi.inter;

import cn.irving.zhao.util.poi.enums.WorkbookType;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;

/**
 * workbook 需继承的接口
 */
public interface IWorkbook {

    default WorkbookType getWorkbookType() {
        return WorkbookType.XLSX;
    }

    default String getSheetName() {
        return "Sheet 1";
    }

    default SheetNameFormatter getDefaultSheetNameFormatter() {
        return null;
    }

}
