package cn.irving.zhao.util.poi.inter;

import cn.irving.zhao.util.poi.enums.WorkbookType;

/**
 * workbook 需继承的接口
 */
public interface IWorkbook {

    default WorkbookType getWorkbookType() {
        return WorkbookType.XLSX;
    }

    default String getSheetName() {
        return String.valueOf(System.currentTimeMillis());
    }

}
