package cn.irving.zhao.util.poi.formatter;

import org.apache.commons.collections4.map.ReferenceMap;

/**
 * 工作表名称格式化对象工厂
 */
public class FormatterFactory<T> {
    private FormatterFactory() {
    }

    private final ReferenceMap FORMATTERCACHE = new ReferenceMap();

    private static final FormatterFactory me = new FormatterFactory();

    public static <T> FormatterFactory<T> getFormatterFactory(Class<T> type) {
        return me;
    }

    public T getFormatter(Class<? extends T> clazz) {
        T result = null;
        try {
            result = (T) me.FORMATTERCACHE.get(clazz.getName());
        } catch (Exception e) {//类型转换异常
            e.printStackTrace();
        }
        if (result == null) {
            synchronized (me) {
                try {
                    result = clazz.newInstance();
                    me.FORMATTERCACHE.put(clazz.getName(), result);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

}
