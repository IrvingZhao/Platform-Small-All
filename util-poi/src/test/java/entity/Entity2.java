package entity;

import cn.irving.zhao.util.poi.annonation.Cell;
import cn.irving.zhao.util.poi.annonation.MergedPosition;
import cn.irving.zhao.util.poi.annonation.Repeatable;

import java.util.List;

/**
 * Created by irving on 2017/8/3.
 */
public class Entity2 {
    @Cell(rowIndex = 1, colIndex = 1)
    private String s1;

    @Cell(rowIndex = 2, colIndex = 2)
//    @Repeatable(direction = Direction.HERIZONTAL, identity = 1)
    @Repeatable(colIdentity = 1)
    private List<String> s2;

    @Cell(rowIndex = 3, colIndex = 3)
//    @Repeatable(direction = Direction.VERTICALLY, identity = 2, max = 2)
    @Repeatable(rowIdentity = 2, max = 2)
    @MergedPosition(endRowIndex = 4, endColIndex = 4)
    private List<String> s3;

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public List<String> getS2() {
        return s2;
    }

    public void setS2(List<String> s2) {
        this.s2 = s2;
    }

    public List<String> getS3() {
        return s3;
    }

    public void setS3(List<String> s3) {
        this.s3 = s3;
    }
}
