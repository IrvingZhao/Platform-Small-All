package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.Repeatable;
import cn.irving.zhao.util.poi.formatter.FormatterFactory;
import cn.irving.zhao.util.poi.formatter.RepeatCheck;
import cn.irving.zhao.util.poi.formatter.RepeatIVFormatter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

/**
 * 循环配置信息
 */
@Getter
public class RepeatConfig implements Serializable {

    private static FormatterFactory<RepeatIVFormatter> factory = FormatterFactory.getFormatterFactory(RepeatIVFormatter.class);
    private static FormatterFactory<RepeatCheck> checkFactory = FormatterFactory.getFormatterFactory(RepeatCheck.class);

    RepeatConfig(Repeatable repeatable) {
        if (repeatable.formatter() != RepeatIVFormatter.class) {
            this.formatter = factory.getFormatter(repeatable.formatter());
            this.max = repeatable.max();
            this.collectionType = repeatable.collectionType();
            this.itemType = repeatable.itemType();
        } else {
            this.rowIv = repeatable.rowIdentity();
            this.colIv = repeatable.colIdentity();
            this.max = repeatable.max();
            this.collectionType = repeatable.collectionType();
            this.itemType = repeatable.itemType();
        }
        if (repeatable.check() != RepeatCheck.class) {
            this.check = checkFactory.getFormatter(repeatable.check());
        }
    }

    RepeatConfig(int rowIv, int colIv, int max, Class<? extends Collection> collectionType, Class<?> itemType, RepeatCheck check) {
        this.rowIv = rowIv;
        this.colIv = colIv;
        this.max = max;
        this.collectionType = collectionType;
        this.itemType = itemType;
        this.check = check;
    }

    RepeatConfig(RepeatIVFormatter formatter, int max, Class<? extends Collection> collectionType, Class<?> itemType, RepeatCheck check) {
        this.formatter = formatter;
        this.max = max;
        this.collectionType = collectionType;
        this.itemType = itemType;
        this.check = check;
    }

    private int rowIv;
    private int colIv;

    private int max;

    private Class<? extends Collection> collectionType;

    private Class<?> itemType;

    private RepeatIVFormatter formatter;

    private RepeatCheck check;

    public int[] getNextIv(int loop, Object source) {
        if (this.formatter == null) {
            return new int[]{rowIv, colIv};
        } else {
            return this.formatter.getRowColIv(loop, source);
        }
    }

}
