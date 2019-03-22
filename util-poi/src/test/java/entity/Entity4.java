package entity;

import cn.irving.zhao.util.poi.annonation.Cell;
import cn.irving.zhao.util.poi.annonation.MergedFormat;
import cn.irving.zhao.util.poi.annonation.Repeatable;
import cn.irving.zhao.util.poi.annonation.Sheet;
import cn.irving.zhao.util.poi.enums.CellDataType;
import cn.irving.zhao.util.poi.enums.SheetType;
import formatter.Entity4Check;
import formatter.MergedFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Entity4 {
    @MergedFormat(MergedFormatter.class)
    @Cell(rowIndex = 0, colIndex = 0, dataType = CellDataType.STRING)
    private String merge;

    @Sheet(type = SheetType.INNER)
//    @Repeatable(direction = Direction.VERTICALLY)
//    @Repeatable(rowIdentity = 1,itemType = Entity5.class)
    @Repeatable(rowIdentity = 1, itemType = Entity5.class, check = Entity4Check.class)
    private List<Entity5> entity5;
}
