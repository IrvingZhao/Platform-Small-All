package cn.irving.zhao.util.poi.annonation;

import cn.irving.zhao.util.poi.enums.Direction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 循环配置信息
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Repeatable {

    /**
     * 循环方向
     */
    Direction direction() default Direction.VERTICALLY;

    /**
     * 每次递增数值
     */
    int identity() default 1;

    /**
     * 循环最大次数
     * <p>
     * -1不限制
     * </p>
     */
    int max() default -1;

}
