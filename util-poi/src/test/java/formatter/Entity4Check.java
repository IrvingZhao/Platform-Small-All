package formatter;

import cn.irving.zhao.util.poi.formatter.RepeatCheck;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Entity4Check implements RepeatCheck {

    @Override
    public boolean checkRepeat(Sheet sheet, int loop, int rowIv, int colIv) {
        Row row = sheet.getRow(rowIv + 1);
        if (row == null) {
            return false;
        }
        return row.getCell(colIv) == null;
    }
}
