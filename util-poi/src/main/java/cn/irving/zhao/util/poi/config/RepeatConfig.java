package cn.irving.zhao.util.poi.config;

import cn.irving.zhao.util.poi.annonation.Repeatable;
import cn.irving.zhao.util.poi.enums.Direction;

import java.io.Serializable;

/**
 * 循环配置信息
 */
public class RepeatConfig implements Serializable {

    RepeatConfig(Repeatable repeatable) {
        this.identity = repeatable.identity();
        this.direction = repeatable.direction();
        this.max = repeatable.max();
    }

    public RepeatConfig(int identity, Direction direction) {
        this(identity, direction, -1);
    }

    public RepeatConfig(int identity, Direction direction, int max) {
        this.identity = identity;
        this.direction = direction;
        this.max = max;
    }

    private final int identity;//递增值

    private final Direction direction;//方向

    private final int max;

    public int getIdentity() {
        return identity;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getMax() {
        return max;
    }

}
