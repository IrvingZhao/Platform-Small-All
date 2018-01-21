package cn.irving.zhao.util.base.security;

import cn.irving.zhao.util.base.serial.StringByteSerialUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息签名加密，默认提供 MD5 和 SHA256
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public class MessageDigestSecurity {
    public static MessageDigestSecurity MD5 = new MessageDigestSecurity("MD5");
    public static MessageDigestSecurity SHA256 = new MessageDigestSecurity("SHA-256");
    public static MessageDigestSecurity SHA512 = new MessageDigestSecurity("SHA-512");

    public static MessageDigestSecurity newInstances(String securityType) {
        return new MessageDigestSecurity(securityType);
    }

    private MessageDigest messageDigest;

    private MessageDigestSecurity(String securityType) {
        try {
            this.messageDigest = MessageDigest.getInstance(securityType);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得content签名
     *
     * @param content 明文
     * @return 密文
     */
    public String encrypt(String content) {
        return encrypt(content.getBytes());
    }

    /**
     * 获得content签名
     *
     * @param content 明文
     * @return 密文
     */
    public String encrypt(InputStream content) {
        try {
            DigestInputStream din = new DigestInputStream(content, messageDigest);
            int bufferSize = 0x4000000 < content.available() ? 0x4000000 : content.available();
            byte[] buffer = new byte[bufferSize];
            int tmp = 0;
            while (din.read(buffer, tmp * bufferSize, bufferSize) > -1) ;
            din.close();
            byte[] digest = messageDigest.digest();
            return StringByteSerialUtil.byteToHex(digest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得content签名
     *
     * @param content 明文
     * @return 密文
     */
    public String encrypt(byte[] content) {
        return StringByteSerialUtil.byteToHex(encryptByte(content));
    }

    private byte[] encryptByte(byte[] content) {
        return messageDigest.digest(content);
    }

    /**
     * 校验签名是否正确
     *
     * @param content  明文
     * @param security 签名
     * @return true-正确 false-错误
     */
    public boolean validate(String content, String security) {
        return validate(content.getBytes(), security);
    }

    /**
     * 校验签名是否正确
     *
     * @param content  明文
     * @param security 签名
     * @return true-正确 false-错误
     */
    public boolean validate(byte[] content, String security) {
        byte[] securityByte = encryptByte(content);
        return security.equals(StringByteSerialUtil.byteToHex(securityByte)) || security.equals(hackHexString(securityByte));
    }

    /**
     * 校验签名是否正确
     *
     * @param content  明文
     * @param security 签名
     * @return true-正确 false-错误
     */
    public boolean validate(InputStream content, String security) {
        return security.equals(encrypt(content));
    }

    private String hackHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(Integer.toHexString(aByte & 0xFF));
        }
        return result.toString();
    }
}
