package cn.irving.zhao.util.poi.annonation;

import cn.irving.zhao.util.poi.enums.Direction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

/**
 * 循环配置信息
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Repeatable {

    /**
     * 循环方向
     *
     * @return 循环方向
     */
    Direction direction() default Direction.VERTICALLY;

    /**
     * 每次递增数值
     *
     * @return 自增值
     */
    int identity() default 1;

    /**
     * 循环最大次数
     * <p>
     * -1不限制
     * </p>
     *
     * @return 最大循环次数
     */
    int max() default -1;

    /**
     * 集合具体实现类，读取时使用
     *
     * @return 集合实现类
     */
    Class<? extends Collection> collectionType() default Collection.class;

    /**
     * 单个元素的具体实现类，读取时使用
     *
     * @return 集合元素类
     */
    Class<?> itemType() default Object.class;

}
