package cn.irving.zhao.util.base.serial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 对象转字节序列化
 */
public final class ObjectByteSerialUtil {
    private static Logger logger = LoggerFactory.getLogger(ObjectByteSerialUtil.class);

    private ObjectByteSerialUtil() {
    }

    public static byte[] serialize(Object object) {
        if (object == null) {
            return new byte[0];
        }
        if (!Serializable.class.isAssignableFrom(object.getClass())) {
            throw new IllegalArgumentException(ObjectByteSerialUtil.class.getSimpleName() + " requires a Serializable payload " +
                    "but received an object of type [" + object.getClass().getName() + "]");
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error("序列化异常", e);
            throw new RuntimeException("序列化异常", e);
        }
    }

    public static Serializable deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Serializable) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("对象反序列化异常", e);
            throw new RuntimeException("反序列化异常", e);
        }
    }

}
