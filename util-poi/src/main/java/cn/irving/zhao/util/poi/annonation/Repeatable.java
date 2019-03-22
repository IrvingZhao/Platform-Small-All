package cn.irving.zhao.util.poi.annonation;

import cn.irving.zhao.util.poi.formatter.RepeatCheck;
import cn.irving.zhao.util.poi.formatter.RepeatIVFormatter;

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
     * 循环每次行移动量
     *
     * @return 行移动量，默认0
     */
    int rowIdentity() default 0;

    /**
     * 循环每次列移动量
     *
     * @return 列移动量，默认0
     */
    int colIdentity() default 0;

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
     * 重复向量格式化类
     *
     * @return 格式化类
     */
    Class<? extends RepeatIVFormatter> formatter() default RepeatIVFormatter.class;

    /**
     * 循环检查类
     *
     * @return 循环检查类
     */
    Class<? extends RepeatCheck> check() default RepeatCheck.class;

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
