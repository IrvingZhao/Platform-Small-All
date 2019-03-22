package entity;

import cn.irving.zhao.util.poi.annonation.Cell;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entity5 {

    @Cell(rowIndex = 0, colIndex = 1)
    private String s1;
    @Cell(rowIndex = 0, colIndex = 2)
    private String s2;
    @Cell(rowIndex = 0, colIndex = 3)
    private String s3;
    @Cell(rowIndex = 0, colIndex = 4)
    private String s4;

}
