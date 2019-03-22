package cn.irving.zhao.util.poi.formatter;

import org.apache.commons.collections4.map.ReferenceMap;

import java.lang.reflect.InvocationTargetException;

/**
 * 各种格式化工具工厂类
 */
public class FormatterFactory<T> {
    private FormatterFactory() {
    }

    private final ReferenceMap FORMATTER_CACHE = new ReferenceMap();

    private static final FormatterFactory me = new FormatterFactory();

    public static <T> FormatterFactory<T> getFormatterFactory(Class<T> type) {
        return me;
    }

    public T getFormatter(Class<? extends T> clazz) {
        T result = null;
        try {
            result = (T) me.FORMATTER_CACHE.get(clazz.getName());
        } catch (Exception e) {//类型转换异常
            e.printStackTrace();
        }
        if (result == null) {
            synchronized (me) {
                try {
                    result = clazz.getDeclaredConstructor().newInstance();
                    me.FORMATTER_CACHE.put(clazz.getName(), result);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

}
