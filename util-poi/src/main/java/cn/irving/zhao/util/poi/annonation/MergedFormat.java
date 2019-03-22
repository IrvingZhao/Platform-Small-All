package cn.irving.zhao.util.poi.annonation;

import cn.irving.zhao.util.poi.formatter.MergedRegionFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MergedFormat {

    Class<? extends MergedRegionFormatter> value();

}
