package server;

import cn.irving.zhao.util.remote.mina.server.MinaServer;
import common.CustomMethodFactory;
import common.CustomSerialExecutor;
import common.CustomServerSaltHolder;
import common.CustomSignExecutor;

public class ServerMain {
    public static void main(String[] args) throws Exception {

        CustomMethodFactory methodFactory = new CustomMethodFactory();

        methodFactory.registerMethod("method", new ServerMethod());
        methodFactory.registerMethod("method1", new ServerMethod1());
        methodFactory.registerMethod("errorMethod", new ServerExceptionMethod());

        CustomSerialExecutor serialExecutor = new CustomSerialExecutor();

        CustomSignExecutor signExecutor = new CustomSignExecutor();

        CustomServerSaltHolder saltHolder = new CustomServerSaltHolder();

        MinaServer server = new MinaServer("192.168.4.129", 8899);

        server.setSerialExecutor(serialExecutor);
        server.setSignExecutor(signExecutor);
        server.setSaltHolder(saltHolder);
        server.setMethodFactory(methodFactory);

        server.init();
        System.out.println("123123");

        Thread.sleep(15000L);

        System.out.println("=========================start=============================");
        server.sendMessage("ef5ea89d-f80d-4846-b387-cbfe22142310", "method", new ServerModel("我是server", "第" + 0 + "次发送消息"));
        System.out.println("===============================456456======================================");
        System.out.println("========================== send paired message :: " + System.currentTimeMillis() + " =============================");
        ServerModel result = server.sendPairedMessage("ef5ea89d-f80d-4846-b387-cbfe22142310", "method1", new ServerModel("我是服务器", "发送成对消息"), ServerModel.class);
        System.out.println(result.getData1());
        System.out.println(result.getData2());
        System.out.println("========================== send paired message :: " + System.currentTimeMillis() + " =============================");
        System.out.println("========================== send exception message ==========================");
        try {
            ServerModel result1 = server.sendPairedMessage("ef5ea89d-f80d-4846-b387-cbfe22142310", "errorMethod", new ServerModel("我是server，发送异常测试", "第" + 0 + "次发送消息"), ServerModel.class);
        } catch (Exception e) {
            System.out.println("======================= 异常捕获 =========================");
            e.printStackTrace();
        }
        System.out.println("========================== send exception message ==========================");
        System.out.println("========================== send normal after exception ======================");
        server.sendMessage("ef5ea89d-f80d-4846-b387-cbfe22142310", "method", new ServerModel("我是server", "第" + 1 + "次发送消息"));
        System.out.println("========================== send normal after exception ======================");
    }
}
