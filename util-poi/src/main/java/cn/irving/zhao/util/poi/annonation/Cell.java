package cn.irving.zhao.util.poi.annonation;

import cn.irving.zhao.util.poi.enums.CellDataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单元格配置信息
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cell {
    /**
     * 行坐标
     *
     * @return 行坐标
     */
    int rowIndex();

    /**
     * 列坐标
     *
     * @return 列坐标
     */
    int colIndex();

    /**
     * 单元格数据类型，默认为字符串
     *
     * @return 单元格数据类型
     */
    CellDataType dataType() default CellDataType.AUTO;
}
