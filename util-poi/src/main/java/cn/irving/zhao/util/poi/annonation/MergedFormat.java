package cn.irving.zhao.util.poi.annonation;

import cn.irving.zhao.util.poi.formatter.MergedRegionFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 合并单元格动态配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MergedFormat {

    /**
     * 单元格动态计算类
     * @return 合并单元格计算类
     */
    Class<? extends MergedRegionFormatter> value();

}
