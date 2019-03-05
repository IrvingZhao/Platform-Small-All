import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.methods.CloseableHttpResponse;
import org.apache.hc.client5.http.methods.HttpPost;
import org.apache.hc.core5.http.entity.ContentType;
import org.apache.hc.core5.http.entity.FileEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author zhaojn1
 * @version Demo2.java, v 0.1 2018/3/19 zhaojn1
 * code>
 */
public class Demo2 {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient client = HttpClients.custom().build();
        String token = "7_ZAhMfdqCcdsi2H_5vkezZjBQIJmM6H67WGfMbrNG3DZ3d8WgiqCd9gzuz3PLhIiHdL1EXmMFWQhS7hYLCcf9Y-OVajrYxfuywASddL-Q5x9lvh-YC2LSC37cCL_krkGZgXnow4JCy8fg6lm4WVFjAAAKJT";
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=image";
        HttpPost post = new HttpPost(String.format(url, token));

        FileEntity fileEntity = new FileEntity(new File("f:/pdfBg.jpg"),
                ContentType.create("image/jpg"));

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addBinaryBody("media", new File("f:/pdfBg.jpg"),
                ContentType.get(fileEntity), "demo.jpg");

        post.setEntity(entityBuilder.build());

        CloseableHttpResponse response = client.execute(post);
        InputStream inputStream = response.getEntity().getContent();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String temp;
        while ((temp = reader.readLine()) != null) {
            System.out.println(temp);
        }

    }
}
