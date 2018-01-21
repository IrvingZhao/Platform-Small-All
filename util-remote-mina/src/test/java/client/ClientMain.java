package client;

import cn.irving.zhao.util.remote.mina.client.MinaClient;
import common.CustomMethodFactory;
import common.CustomSerialExecutor;
import common.CustomSignExecutor;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        CustomMethodFactory methodFactory = new CustomMethodFactory();

        methodFactory.registerMethod("method", new ClientMethod());
        methodFactory.registerMethod("method1", new ClientMethod1());
        methodFactory.registerMethod("errorMethod", new ClientExceptionMethod());

        CustomSerialExecutor serialExecutor = new CustomSerialExecutor();

        CustomSignExecutor signExecutor = new CustomSignExecutor();

        MinaClient client = new MinaClient("192.168.4.129", 8899);
        client.setClientId("ef5ea89d-f80d-4846-b387-cbfe22142310");
        client.setSalt("EWuqCA0zjEka4qrVujAnJvWlScTjGqeFfGqQMDEYsFM=");

        client.setMethodFactory(methodFactory);

        client.setSerialExecutor(serialExecutor);

        client.setSignExecutor(signExecutor);

        client.setAutoRegisterClient(true);

        client.init();

//        client.setSalt("111231231");
//        client.sendMessage("111", new ClientModel("我是客户端", "客户端sign异常消息"));

//        new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                try {
        Thread.sleep(3000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
        client.sendMessage("method", new ClientModel("我是客户端", "第" + 0 + "次发送消息"));
//            }
//
//        }).start();

        System.out.println("=================== send paired message :::" + System.currentTimeMillis() + "  =========================");
        ClientModel result = client.sendPairedMessage("method1", new ClientModel("我是客户端", "发送客户端成对消息"), ClientModel.class);
        System.out.println(result.getData1());
        System.out.println(result.getData2());
        System.out.println("=================== send paired message :::" + System.currentTimeMillis() + "  =========================");


        System.out.println("========================== send exception message ==========================");
        try {
            client.sendPairedMessage("errorMethod", new ClientModel("我是客户端，发送异常测试", "第" + 0 + "次发送消息"), ClientModel.class);
        } catch (Exception e) {
            System.out.println("======================= 异常捕获 =========================");
            e.printStackTrace();
        }
        System.out.println("========================== send exception message ==========================");
        System.out.println("========================== send normal after exception ======================");
        client.sendMessage("method", new ClientModel("我是客户端", "第" + 1 + "次发送消息"));
        System.out.println("========================== send normal after exception ======================");

    }
}
