package demo;

import cn.irving.zhao.util.poi.formatter.CellDataFormatter;

/**
 * Created by irving on 2017/8/7.
 */
public class FormulaRepeatDataFormater implements CellDataFormatter {
    @Override
    public Object format(Object source, int rowIndex, int colIndex) {
        return "SUM(" + getColCharIndex((colIndex - 2)) + (rowIndex + 1) + ":" + getColCharIndex((colIndex - 1)) + (rowIndex + 1) + ")";
    }
}
