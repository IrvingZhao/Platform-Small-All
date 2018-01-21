package cn.irving.zhao.util.base.clone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 对象克隆
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public final class CloneUtil {

    private static Logger logger = LoggerFactory.getLogger(CloneUtil.class);
    private static Method CloneMethod;

    static {
        Method[] methods = Object.class.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method item = methods[i];
            if (item.getName().equals("clone")) {
                CloneMethod = item;
                break;
            }
        }
    }

    /**
     * 通过字节码形式克隆对象
     * <p>克隆失败后，会返回原有对象</p>
     *
     * @param source 需要被克隆的对象
     * @return 克隆后对象
     */
    public <T extends Serializable> T clone(T source) {
        T result = null;

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(source);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            result = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("clone异常", e);
            result = source;
        }

        return result;
    }

    /**
     * 通过clone方法进行对象克隆
     * <p>克隆失败后，会返回原有对象</p>
     *
     * @param source 需要被克隆的对象
     * @return 克隆后对象
     */
    public <T extends Cloneable> T clone(T source) {
        try {
            return (T) CloneMethod.invoke(source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("clone异常", e);
        }
        return source;
    }

}
