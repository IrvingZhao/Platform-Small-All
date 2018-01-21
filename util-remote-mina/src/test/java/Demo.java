import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

public class Demo {
    public static void main(String[] args) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("我是第一段");
        builder.append('\uffff');
        builder.append("我是第二段");
        System.out.println(builder.toString());

        System.out.println(Arrays.toString(builder.toString().split(new String(new char[]{'\uffff'}))));

//        Logger logger = LoggerFactory.getLogger(Demo.class);
//        logger.error("测试-{}-{}", UUID.randomUUID().toString(), "异常信息", new NullPointerException("异常"));
//
//        Exception exception = new NullPointerException("测试异常");
//        System.out.println(exception.toString());
//        logger.info("测试");
//        String s1=ObjectStringSerialUtil.getSerialUtil().serial(null, ObjectStringSerialUtil.SerialType.JSON);
//        System.out.println(s1.equals("null"));
//
//        System.out.println(ObjectStringSerialUtil.getSerialUtil().parse("null", ObjectStringSerialUtil.SerialType.JSON)==null);

//        Object object = new Object();
//        System.out.println(System.currentTimeMillis());
//        synchronized (object) {
//            object.wait(3000L);
//        }
//        System.out.println(System.currentTimeMillis());

//        Test test = new Test("1", null);
//        test.setDemo(test);
//
//        System.out.println(ObjectStringSerialUtil.getSerialUtil().serial(test, ObjectStringSerialUtil.SerialType.JSON));;
//        System.out.println(ObjectStringSerialUtil.getSerialUtil().parse("123123", String.class, ObjectStringSerialUtil.SerialType.JSON));

//        Method method = Demo.class.getMethod("test");
//        System.out.println(method.getReturnType().getClass().equals(Void.class));
//
//
//        System.out.println(method.getReturnType() == Void.class);
//        System.out.println(method.getReturnType() == void.class);
//        System.out.println(method.getReturnType().equals(Void.class));
//        System.out.println(method.getReturnType().equals(void.class));


    }

    public void test() {

    }

    public static class Test {
        public Test() {
        }

        public Test(String s1, Object demo) {
            this.s1 = s1;
            this.demo = demo;
        }

        private String s1;
        private Object demo;

        public String getS1() {
            return s1;
        }

        public void setS1(String s1) {
            this.s1 = s1;
        }

        public Object getDemo() {
            return demo;
        }

        public void setDemo(Object demo) {
            this.demo = demo;
        }
    }

}
