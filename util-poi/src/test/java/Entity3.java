import cn.irving.zhao.util.poi.annonation.Cell;
import cn.irving.zhao.util.poi.annonation.Formatter;
import cn.irving.zhao.util.poi.enums.CellDataType;
import demo.FormulaRepeatDataFormater;

/**
 * Created by irving on 2017/8/7.
 */
public class Entity3 {

    public Entity3(int intA, int intB, String sum) {
        this.intA = intA;
        this.intB = intB;
        this.sum = sum;
    }

    @Cell(rowIndex = 1, colIndex = 1, dataType = CellDataType.NUMERIC)
    private int intA;

    @Cell(rowIndex = 1, colIndex = 2, dataType = CellDataType.NUMERIC)
    private int intB;

    @Cell(rowIndex = 1, colIndex = 3, dataType = CellDataType.FORMULA)
    @Formatter(cellDataFormatter = FormulaRepeatDataFormater.class)
    private String sum;

    public int getIntA() {
        return intA;
    }

    public void setIntA(int intA) {
        this.intA = intA;
    }

    public int getIntB() {
        return intB;
    }

    public void setIntB(int intB) {
        this.intB = intB;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
