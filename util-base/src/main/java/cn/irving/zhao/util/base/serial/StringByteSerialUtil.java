package cn.irving.zhao.util.base.serial;

/**
 * byte数据与字符串互相转换工具类
 */
public final class StringByteSerialUtil {

    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for (int j = 0; i < l; ++i) {
            out[j++] = DIGITS_LOWER[(240 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[15 & data[i]];
        }
        return out;
    }


    /**
     * <p>将byte数组进行Hex编码</p>
     *
     * @param bytes 带编码数组
     * @return 编码后结果
     */
    public static String byteToHex(byte[] bytes) {
        return new String(encodeHex(bytes));
    }

    /**
     * <p>将hex编码字符串转换为byte数组</p>
     *
     * @param hex hex编码字符串
     * @return 转换后byte数据
     */
    public static byte[] hexToByte(String hex) {
        char[] data = hex.toCharArray();
        int len = data.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for (int j = 0; j < len; ++i) {
                int f = toDigit(data[j], j) << 4;
                ++j;
                f |= toDigit(data[j], j);
                ++j;
                out[i] = (byte) (f & 255);
            }

            return out;
        }
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        } else {
            return digit;
        }
    }
}
