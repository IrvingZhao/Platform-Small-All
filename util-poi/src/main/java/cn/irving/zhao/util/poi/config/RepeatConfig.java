package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.Repeatable;
import cn.irving.zhao.util.poi.formatter.FormatterFactory;
import cn.irving.zhao.util.poi.formatter.RepeatCheck;
import cn.irving.zhao.util.poi.formatter.RepeatIVFormatter;
import lombok.Getter;

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

    private int rowIv;//行循环追加量
    private int colIv;//列循环追加量

    private int max;//最大循环次数

    private Class<? extends Collection> collectionType;//集合具体类型

    private Class<?> itemType;//集合中单个元素的类型

    private RepeatIVFormatter formatter;//循环行、列追加量计算器

    private RepeatCheck check;//循环结束检查器

    /**
     * 获取行、列叠加量
     *
     * @return [rowIv, colIv]
     */
    public int[] getNextIv(int loop, Object source) {
        if (this.formatter == null) {
            return new int[]{rowIv, colIv};
        } else {
            return this.formatter.getRowColIv(loop, source);
        }
    }

}
