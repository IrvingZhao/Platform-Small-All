package cn.irving.zhao.util.poi.annonation;

import cn.irving.zhao.util.poi.enums.SheetType;
import cn.irving.zhao.util.poi.formatter.SheetNameFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 工作簿配置
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sheet {

    /**
     * 工作簿类型，默认值为{@link SheetType#OUTER}
     */
    SheetType type() default SheetType.OUTER;

    /**
     * 工作簿名称，只在{@link Sheet#type()} 为 {@link SheetType#OUTER} 时有效
     *
     * @return 默认值："Sheet"
     */
    String name() default "Sheet";

    /**
     * 工作表名称
     */
    Class<? extends SheetNameFormatter> nameFormatter() default SheetNameFormatter.class;

    /**
     * <p>内嵌表格基准行。</p>
     * <p>内嵌表格类中的所有行坐标均增加基准行值</p>
     * <p>只在{@link Sheet#type()} 为 {@link SheetType#INNER} 时有效</p>
     *
     * @return 默认值为0
     */
    int baseRow() default 0;

    /**
     * <p>内嵌表格基准列</p>
     * <p>内嵌表格中所有列坐标均增加基准列值</p>
     * <p>只在{@link Sheet#type()} 为 {@link SheetType#INNER} 时有效</p>
     *
     * @return 默认值为0
     */
    int baseCol() default 0;


}
