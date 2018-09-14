package cn.irving.zhao.platform.weixin.base.config.message;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestHead {
    String key();

    String value();
}
