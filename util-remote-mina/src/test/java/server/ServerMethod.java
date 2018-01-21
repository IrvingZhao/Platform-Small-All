package server;


import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;

public class ServerMethod implements MinaMessageMethod<ServerModel, ServerModel> {

    @Override
    public ServerModel execute(ServerModel data) {
        System.out.println("=============================serverMethodExecute================================");
        System.out.println(data);
        return new ServerModel("我是服务器", "服务器返回消息");
    }

    @Override
    public Class<ServerModel> getDataType() {
        return ServerModel.class;
    }
}
