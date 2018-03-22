import cn.irving.zhao.util.remote.http.HttpClient;
import cn.irving.zhao.util.remote.http.enums.HttpMethod;
import cn.irving.zhao.util.remote.http.enums.RequestType;
import cn.irving.zhao.util.remote.http.message.HttpMessage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojn1
 * @version TestMain.java, v 0.1 2018/3/19 zhaojn1
 * @project userProfile
 */
public class TestMain {
    public static void main(String[] args) {
        HttpClient client = new HttpClient();
        String token = "7_ZAhMfdqCcdsi2H_5vkezZjBQIJmM6H67WGfMbrNG3DZ3d8WgiqCd9gzuz3PLhIiHdL1EXmMFWQhS7hYLCcf9Y-OVajrYxfuywASddL-Q5x9lvh-YC2LSC37cCL_krkGZgXnow4JCy8fg6lm4WVFjAAAKJT";
        HttpMessage demoMessage = new DemoMessage(token);
        client.sendMessage(demoMessage);
    }

    public static class DemoMessage implements HttpMessage {

        public DemoMessage(String token) {
            this.token = token;
        }

        private String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

        private String token;
        private String type = "image";

        @Override
        public String getRequestUrl() {
            return String.format(url, token, type);
        }

        @Override
        public HttpMethod getRequestMethod() {
            return HttpMethod.POST;
        }

        @Override
        public RequestType getRequestType() {
            return RequestType.MULTIPART;
        }

        @Override
        public Map<String, Object> getRequestParams() {
            Map<String, Object> result = new HashMap<>();
            result.put("media", new File("F:/pdfBg.jpg"));
            return result;
        }

        @Override
        public void setResponseStream(InputStream inputStream) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String temp;
                while ((temp = reader.readLine()) != null) {
                    stringBuilder.append(temp);
                }
                System.out.println(stringBuilder.toString());
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
