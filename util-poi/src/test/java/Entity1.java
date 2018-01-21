import cn.irving.zhao.util.poi.annonation.Cell;
import cn.irving.zhao.util.poi.annonation.MergedRegion;
import cn.irving.zhao.util.poi.annonation.Repeatable;
import cn.irving.zhao.util.poi.annonation.Sheet;
import cn.irving.zhao.util.poi.enums.Direction;
import cn.irving.zhao.util.poi.enums.SheetType;
import demo.DemoNameFormatter;

import java.util.List;

/**
 * Created by irving on 2017/8/3.
 */
public class Entity1 {

    @Cell(rowIndex = 1, colIndex = 1)
    private String s1;

    @Cell(rowIndex = 2, colIndex = 2)
    @Repeatable(direction = Direction.HERIZONTAL, identity = 1)
    private List<String> s2;

    @Cell(rowIndex = 3, colIndex = 3)
    @Repeatable(direction = Direction.VERTICALLY, identity = 2, max = 2)
    @MergedRegion(endRowIndex = 4, endColIndex = 4)
    private List<String> s3;

    @Sheet(type = SheetType.INNER, baseCol = 6, baseRow = 0)
    @Repeatable(direction = Direction.VERTICALLY, identity = 8)
    private List<Entity2> entity2List;

    @Sheet(type = SheetType.OUTER, name = "引入外部", nameFormatter = DemoNameFormatter.class)
    @Repeatable
    private List<Entity2> entity2;

    @Sheet(type = SheetType.INNER, baseRow = 20, baseCol = 0)
    @Repeatable(direction = Direction.VERTICALLY, identity = 2)
    private List<Entity3> entity3s;


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

    public List<Entity2> getEntity2List() {
        return entity2List;
    }

    public void setEntity2List(List<Entity2> entity2List) {
        this.entity2List = entity2List;
    }

    public List<Entity2> getEntity2() {
        return entity2;
    }

    public void setEntity2(List<Entity2> entity2) {
        this.entity2 = entity2;
    }

    public List<Entity3> getEntity3s() {
        return entity3s;
    }

    public void setEntity3s(List<Entity3> entity3s) {
        this.entity3s = entity3s;
    }
}
