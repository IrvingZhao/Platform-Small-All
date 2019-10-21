package cn.irving.zhao.util.poi.formatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

public interface CellStyleSetter {

    void setCellStyle(CellStyle style, Cell cell,Object cellData);

}
