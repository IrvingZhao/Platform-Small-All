package cn.irving.zhao.platform.weixin.base.config.message;

import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageFormat;
import cn.irving.zhao.platform.weixin.base.config.enums.WeChartMessageRequestMethod;

import java.lang.annotation.*;

/**
 * 微信消息配置
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface WeChartMessage {
    boolean isSecurity() default false;

    WeChartMessageRequestMethod requestMethod() default WeChartMessageRequestMethod.GET;

    WeChartMessageFormat requestType() default WeChartMessageFormat.FORM;

    WeChartMessageFormat responseType() default WeChartMessageFormat.JSON;

    RequestHead[] requestHead() default {};

}
