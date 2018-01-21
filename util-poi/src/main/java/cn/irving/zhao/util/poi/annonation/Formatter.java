package cn.irving.zhao.util.poi.annonation;

import cn.irving.zhao.util.poi.formatter.CellDataFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单元格数据格式化配置
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Formatter {

    /**
     * 单元格内数据格式化方法
     */
    Class<? extends CellDataFormatter> cellDataFormatter() default CellDataFormatter.class;

    /**
     * excel中单元格格式化字符串
     */
    String cellFormatPattern() default "";

}
