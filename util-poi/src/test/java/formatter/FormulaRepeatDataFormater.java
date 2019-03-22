package formatter;

import cn.irving.zhao.util.poi.config.CellConfig;
import cn.irving.zhao.util.poi.formatter.CellDataFormatter;

/**
 * Created by irving on 2017/8/7.
 */
public class FormulaRepeatDataFormater implements CellDataFormatter {

    @Override
    public Object format(Object source, CellConfig cellConfig, int rowIv, int colIv) {
        return "SUM(" + getColCharIndex((cellConfig.getCellIndex() + colIv - 2)) + (cellConfig.getRowIndex() + rowIv + 1) + ":"
                + getColCharIndex((cellConfig.getCellIndex() + colIv - 1)) + (cellConfig.getRowIndex() + rowIv + 1) + ")";
    }
}
