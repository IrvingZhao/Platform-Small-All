import cn.irving.zhao.util.poi.annonation.Cell;
import cn.irving.zhao.util.poi.annonation.MergedRegion;
import cn.irving.zhao.util.poi.annonation.Repeatable;
import cn.irving.zhao.util.poi.annonation.Sheet;
import cn.irving.zhao.util.poi.enums.Direction;
import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.inter.IWorkbook;
import demo.DemoNameFormatter;

import java.awt.*;
import java.util.List;

/**
 * Created by irving on 2017/8/3.
 */
public class WorkbookContainer implements IWorkbook {

    @Sheet(type = SheetType.OUTER, name = "workbook-outer-sheet1")
    private Entity1 entity1;

    @Cell(rowIndex = 7, colIndex = 1)
    private String s1;

    @Cell(rowIndex = 8, colIndex = 2)
    @Repeatable(direction = Direction.HERIZONTAL, identity = 1)
    private List<String> s2;

    @Cell(rowIndex = 9, colIndex = 3)
    @Repeatable(direction = Direction.VERTICALLY, identity = 2, max = 2)
    @MergedRegion(endRowIndex = 10, endColIndex = 4)
    private List<String> s3;

    @Sheet(type = SheetType.INNER)
    private Entity2 entity2;

    @Sheet(type = SheetType.OUTER, name = "引入外部A", nameFormatter = DemoNameFormatter.class)
    @Repeatable(itemType = Entity2.class)
    private List<Entity2> entity2s;

    @Sheet(type = SheetType.INNER, baseRow = 20, baseCol = 0)
    @Repeatable(direction = Direction.VERTICALLY, identity = 2, itemType = Entity3.class)
    private List<Entity3> entity3s;


    public Entity1 getEntity1() {
        return entity1;
    }

    public void setEntity1(Entity1 entity1) {
        this.entity1 = entity1;
    }

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

    public Entity2 getEntity2() {
        return entity2;
    }

    public void setEntity2(Entity2 entity2) {
        this.entity2 = entity2;
    }

    public List<Entity2> getEntity2s() {
        return entity2s;
    }

    public WorkbookContainer setEntity2s(List<Entity2> entity2s) {
        this.entity2s = entity2s;
        return this;
    }

    public List<Entity3> getEntity3s() {
        return entity3s;
    }

    public WorkbookContainer setEntity3s(List<Entity3> entity3s) {
        this.entity3s = entity3s;
        return this;
    }
}
