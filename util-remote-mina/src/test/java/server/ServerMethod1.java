package server;

import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;

public class ServerMethod1 implements MinaMessageMethod<ServerModel, ServerModel> {

    @Override
    public ServerModel execute(ServerModel data) {
        System.out.println("=============================clientMethodExecute================================");
        System.out.println(data);
        System.out.println("============= 执行 等待 ===============");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ServerModel("我是服务器", "服务器返回");
    }

    @Override
    public Class<ServerModel> getDataType() {
        return ServerModel.class;
    }
}
