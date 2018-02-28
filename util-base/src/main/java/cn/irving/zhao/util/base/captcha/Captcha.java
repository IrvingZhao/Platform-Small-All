package cn.irving.zhao.util.base.captcha;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片验证码对象
 */
public class Captcha {

    public Captcha(String code, BufferedImage image) {
        this.code = code;
        this.image = image;
    }

    private final String code;

    private final BufferedImage image;

    public void write(OutputStream outputStream, String format) throws IOException {
        ImageIO.write(image, format, outputStream);
    }

    public String getCode() {
        return code;
    }

    public BufferedImage getImage() {
        return image;
    }
}
