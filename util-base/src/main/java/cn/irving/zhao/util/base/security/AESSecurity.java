package cn.irving.zhao.util.base.security;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES对称加密
 * <p>默认编码规范：UTF-8</p>
 *
 * @author Irving.Zhao
 * @version 1.0
 * @since 1.0
 */
public final class AESSecurity {

    /**
     * AES 加密
     */
    public static final AESSecurity AES = new AESSecurity(false, "AES");
    /**
     * AES 使用key生成器生成key 加密
     */
    public static final AESSecurity AES_KEY_GENERATOR = new AESSecurity(true, "AES");

    /**
     * 创建新的AES加密器
     *
     * @param needKeyGenerator 是否需要生成秘钥
     * @param cipherType       加密方式
     * @param charset          内容编码
     * @return AES加密工具类
     */
    public static AESSecurity newInstances(Boolean needKeyGenerator, String cipherType, Charset charset) {
        return new AESSecurity(needKeyGenerator, cipherType, charset);
    }

    private int keyGeneratorLength = 128;
    private Boolean keyGenerator;
    private String cipherType;
    private Charset charset = Charset.forName("UTF-8");

    private AESSecurity(Boolean needKeyGenerator, String cipherType) {
        this.keyGenerator = needKeyGenerator;
        this.cipherType = cipherType;
    }

    private AESSecurity(Boolean needKeyGenerator, String cipherType, Charset charset) {
        this(needKeyGenerator, cipherType);
        this.charset = charset;
    }

    /**
     * 加密content内容
     * <p>iv值只有CBC加密模式才可用</p>
     *
     * @param content  原文
     * @param password 密钥
     * @param iv       iv向量
     * @return 密文
     */
    public String encrypt(String content, String password, String iv) {
        return encrypt(content, password, iv.getBytes());
    }

    /**
     * 加密content内容，将密文输出到输出流中
     * <p>iv值只有CBC加密模式才可用</p>
     *
     * @param content  原文
     * @param password 密钥
     * @param iv       iv向量
     * @param output   输出流
     */
    public void encrypt(InputStream content, String password, String iv, OutputStream output) {
        encrypt(content, password, iv.getBytes(), output);
    }

    /**
     * 加密content内容
     * <p>iv值只有CBC加密模式才可用</p>
     *
     * @param content  原文
     * @param password 密钥
     * @param iv       iv向量
     * @return 密文
     */
    public String encrypt(String content, String password, byte[] iv) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes(charset));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        encrypt(byteArrayInputStream, password, iv, outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    /**
     * 加密content内容，将密文输出到输出流中
     * <p>iv值只有CBC加密模式才可用</p>
     *
     * @param content  原文
     * @param password 密钥
     * @param iv       iv向量
     * @param output   输出流
     */
    public void encrypt(InputStream content, String password, byte[] iv, OutputStream output) {
        try {
            Cipher cipher = generateCipher(password, Cipher.ENCRYPT_MODE, iv);
            CipherInputStream cipherInputStream = new CipherInputStream(content, cipher);
            int bufferSize = 0x4000000 < content.available() ? 0x4000000 : content.available();
            int i = 0;
            byte[] buffered = new byte[bufferSize];
            while ((i = cipherInputStream.read(buffered)) > -1) {
                output.write(buffered, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException e) {
            }
        }

    }

    /**
     * 加密content内容
     *
     * @param content  原文
     * @param password 密钥
     * @return 密文
     */
    public String encrypt(String content, String password) {
        return encrypt(content, password, "");
    }

    /**
     * 加密content内容，将密文输出到输出流中
     *
     * @param content  原文
     * @param password 密钥
     * @param output   输出流
     */
    public void encrypt(InputStream content, String password, OutputStream output) {
        encrypt(content, password, "", output);
    }


    /**
     * 解密content内容
     *
     * @param content  密文
     * @param password 密钥
     * @param iv       向量
     * @return 原文
     */
    public String decrypt(String content, String password, String iv) {
        return decrypt(content, password, iv.getBytes());
    }

    /**
     * 解密content内容，将原文输出到输出流中
     *
     * @param content  密文
     * @param password 密钥
     * @param iv       向量
     * @param output   输出流
     */
    public void decrypt(InputStream content, String password, String iv, OutputStream output) {
        decrypt(content, password, iv.getBytes(), output);
    }

    /**
     * 解密content内容
     *
     * @param content  密文
     * @param password 密钥
     * @param iv       向量
     * @return 原文
     */
    public String decrypt(String content, String password, byte[] iv) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(content));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        decrypt(byteArrayInputStream, password, iv, outputStream);
        return new String(outputStream.toByteArray(), charset);
    }

    /**
     * 解密content内容，将原文输出到输出流中
     *
     * @param content  密文
     * @param password 密钥
     * @param iv       向量
     * @param output   输出流
     */
    public void decrypt(InputStream content, String password, byte[] iv, OutputStream output) {

        try {
            Cipher cipher = generateCipher(password, Cipher.DECRYPT_MODE, iv);
            int bufferSize = 0x4000000 < content.available() ? 0x4000000 : content.available();
            byte[] buffered = new byte[bufferSize];
            CipherOutputStream cipherOutputStream = new CipherOutputStream(output, cipher);
            while (content.read(buffered) > -1) {
                cipherOutputStream.write(buffered);
            }
            cipherOutputStream.flush();
            cipherOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException e) {
            }
        }

    }

    /**
     * 解密content内容
     *
     * @param content  密文
     * @param password 密钥
     * @return 原文
     */
    public String decrypt(String content, String password) {
        return decrypt(content, password, "");
    }

    /**
     * 解密content内容，将原文输出到输出流中
     *
     * @param content  密文
     * @param password 密钥
     * @param output   输出流
     */
    public void decrypt(InputStream content, String password, OutputStream output) {
        decrypt(content, password, "", output);
    }

    /**
     * 生成加密器
     *
     * @param password    密钥
     * @param encryptMode 加密或解密
     * @param ivs         IV 向量
     * @return 返回java加密器
     */
    public Cipher generateCipher(String password, int encryptMode, byte[] ivs) {
        try {
            byte[] keyBytes;
            if (keyGenerator) {
                KeyGenerator generator = KeyGenerator.getInstance("AES");
                generator.init(keyGeneratorLength, new SecureRandom(password.getBytes()));
                SecretKey secretKey = generator.generateKey();
                keyBytes = secretKey.getEncoded();
            } else {
                keyBytes = password.getBytes();
            }
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher result = Cipher.getInstance(cipherType);
            if (ivs == null || ivs.length != 16) {
                result.init(encryptMode, keySpec);
            } else {
                result.init(encryptMode, keySpec, new IvParameterSpec(ivs));
            }
            return result;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getKeyGeneratorLength() {
        return keyGeneratorLength;
    }

    public void setKeyGeneratorLength(int keyGeneratorLength) {
        this.keyGeneratorLength = keyGeneratorLength;
    }
}
