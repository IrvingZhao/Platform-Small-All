package entity;

import cn.irving.zhao.util.poi.annonation.Cell;
import cn.irving.zhao.util.poi.annonation.MergedPosition;
import cn.irving.zhao.util.poi.annonation.Repeatable;
import cn.irving.zhao.util.poi.annonation.Sheet;
import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.inter.IWorkbook;
import formatter.DemoNameFormatter;
import formatter.RepeatIvFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by irving on 2017/8/3.
 */
@Getter
@Setter
public class WorkbookContainer implements IWorkbook {

    @Sheet(type = SheetType.OUTER, name = "workbook-outer-sheet1")
    private Entity1 entity1;

    @Cell(rowIndex = 7, colIndex = 1)
    private String s1;

    @Cell(rowIndex = 8, colIndex = 2)
//    @Repeatable(direction = Direction.HERIZONTAL, identity = 1)
    @Repeatable(colIdentity = 1)
    private List<String> s2;

    @Cell(rowIndex = 9, colIndex = 3)
//    @Repeatable(direction = Direction.VERTICALLY, identity = 2, max = 2)
    @Repeatable(rowIdentity = 2, max = 2)
    @MergedPosition(endRowIndex = 10, endColIndex = 4)
    private List<String> s3;

    @Sheet(type = SheetType.INNER)
    private Entity2 entity2;

    @Sheet(type = SheetType.OUTER, name = "引入外部A", nameFormatter = DemoNameFormatter.class)
    @Repeatable(itemType = Entity2.class)
    private List<Entity2> entity2s;

    @Sheet(type = SheetType.INNER, baseRow = 20, baseCol = 0)
//    @Repeatable(direction = Direction.VERTICALLY, identity = 2, itemType = Entity3.class)
    @Repeatable(rowIdentity = 2, itemType = Entity3.class)
    private List<Entity3> entity3s;

    @Sheet(type = SheetType.OUTER, name = "MERGE TEST")
    private Entity4 entity4;

    @Sheet(type = SheetType.OUTER, name = "MERGE TEST REPEAT", baseRow = 2, baseCol = 2)
    @Repeatable(formatter = RepeatIvFormatter.class,itemType = Entity4.class)
//    @Sheet(type = SheetType.OUTER, name = "MERGE TEST REPEAT", baseRow = 2, baseCol = 2, nameFormatter = DemoNameFormatter.class)
//    @Repeatable
    private List<Entity4> entity4List;
}
