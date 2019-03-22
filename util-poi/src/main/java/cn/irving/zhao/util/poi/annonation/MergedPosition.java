package cn.irving.zhao.util.poi.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 合并单元格配置信息
 * <p>合并单元格开始位置坐标为{@link Cell}坐标</p>
 * <p>结束坐标在注解中配置</p>
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MergedPosition {

    /**
     * 目标单元格行坐标
     *
     * @return 目标行坐标
     */
    int endRowIndex();

    /**
     * 目标单元格列坐标
     *
     * @return 目标列坐标
     */
    int endColIndex();

}
