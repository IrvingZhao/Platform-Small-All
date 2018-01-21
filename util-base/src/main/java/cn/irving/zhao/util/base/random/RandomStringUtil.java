package cn.irving.zhao.util.base.random;

import java.util.Random;

/**
 * 随机数接口
 */
public final class RandomStringUtil {

    public static final RandomStringUtil ALL_CHAR = new RandomStringUtil("23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ");
    public static final RandomStringUtil LETTER = new RandomStringUtil("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public static final RandomStringUtil LOWER_LETTER = new RandomStringUtil("abcdefghijklmnopqrstuvwxyz");
    public static final RandomStringUtil NUMBER = new RandomStringUtil("0123456789");
    public static final RandomStringUtil UPPER_LETTER = new RandomStringUtil("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    private String sourceStr;

    public RandomStringUtil(String sourceStr) {
        this.sourceStr = sourceStr;
    }

    public String generateString(int length) {
        final StringBuffer sb = new StringBuffer();
        final Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(sourceStr.charAt(random.nextInt(sourceStr.length())));
        }
        return sb.toString();
    }
}
