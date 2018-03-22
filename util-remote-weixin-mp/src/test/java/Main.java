//import cn.irving.zhao.platform.weixin.base.message.send.MessageSender;

import cn.irving.zhao.platform.weixin.mp.WeChartMpClient;
import cn.irving.zhao.platform.weixin.mp.message.send.material.temporary.AddTempMaterialInputMessage;
import cn.irving.zhao.platform.weixin.mp.message.send.material.temporary.AddTempMaterialOutputMessage;

import java.io.File;

/**
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        AccessTokenOutputMessage tokenOutputMessage = new AccessTokenOutputMessage("wx6b7a73e6b97446d3", "4675c2185a0840fc08342b1490526b2a");
//        AccessTokenInputMessage tokenInputMessage = new MessageSender().sendMessage(tokenOutputMessage);
//        System.out.println(tokenInputMessage);


        String token = "7_ZAhMfdqCcdsi2H_5vkezZjBQIJmM6H67WGfMbrNG3DZ3d8WgiqCd9gzuz3PLhIiHdL1EXmMFWQhS7hYLCcf9Y-OVajrYxfuywASddL-Q5x9lvh-YC2LSC37cCL_krkGZgXnow4JCy8fg6lm4WVFjAAAKJT";

        AddTempMaterialOutputMessage outputMessage = new AddTempMaterialOutputMessage(token);

        outputMessage.setType("image");
        outputMessage.setMedia(new File("f:/pdfBg.jpg"));

        AddTempMaterialInputMessage inputMessage = WeChartMpClient.sendMessage(outputMessage);

        System.out.println(inputMessage);


//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("d:/basepath/1.txt"));
//        bos.write("abc".getBytes());
//        bos.close();

    }
}
