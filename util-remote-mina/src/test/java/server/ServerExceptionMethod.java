package server;

import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;

public class ServerExceptionMethod implements MinaMessageMethod<ServerModel, ServerModel> {
    @Override
    public ServerModel execute(ServerModel data) {
        throw new RuntimeException("服务器异常信息");
    }

    @Override
    public Class<ServerModel> getDataType() {
        return ServerModel.class;
    }
}
