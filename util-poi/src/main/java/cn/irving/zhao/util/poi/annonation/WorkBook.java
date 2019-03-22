package cn.irving.zhao.util.poi.annonation;

import cn.irving.zhao.util.poi.enums.WorkbookType;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WorkBook {

    /**
     * 默认工作表名称，即当前当前类中的 {@link Cell} 标记的属性所对应的工作表名
     *
     * @return 工作表名称，默认为 Sheet
     */
    String defaultSheetName() default "Sheet";

    /**
     * 工作簿类型
     *
     * @return 默认为 {@link WorkbookType#XLSX}
     */
    WorkbookType type() default WorkbookType.XLSX;

    /**
     * 默认工作表格式化类
     *
     * @return 默认工作表格式化类
     * @see #defaultSheetName()
     */
    Class<? extends SheetNameFormatter> defaultSheetNameFormatter() default SheetNameFormatter.class;

}
