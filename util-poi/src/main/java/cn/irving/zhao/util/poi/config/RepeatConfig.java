package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.Repeatable;
import cn.irving.zhao.util.poi.enums.Direction;

import java.io.Serializable;
import java.util.Collection;

/**
 * 循环配置信息
 */
public class RepeatConfig implements Serializable {

    RepeatConfig(Repeatable repeatable) {
        this.identity = repeatable.identity();
        this.direction = repeatable.direction();
        this.max = repeatable.max();
        this.collectionType = repeatable.collectionType();
        this.itemType = repeatable.itemType();
    }

    public RepeatConfig(int identity, Direction direction, Class<? extends Collection> collectionType, Class<?> itemType) {
        this(identity, direction, -1, collectionType, itemType);
    }

    public RepeatConfig(int identity, Direction direction, int max, Class<? extends Collection> collectionType, Class<?> itemType) {
        this.identity = identity;
        this.direction = direction;
        this.max = max;
        this.collectionType = collectionType;
        this.itemType = itemType;
    }

    private final int identity;//递增值

    private final Direction direction;//方向

    private final int max;

    private final Class<? extends Collection> collectionType;

    private final Class<?> itemType;

    public int getIdentity() {
        return identity;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getMax() {
        return max;
    }

    public Class<? extends Collection> getCollectionType() {
        return collectionType;
    }

    public Class<?> getItemType() {
        return itemType;
    }
}
