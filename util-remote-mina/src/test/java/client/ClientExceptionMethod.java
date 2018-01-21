package client;

import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;

public class ClientExceptionMethod implements MinaMessageMethod<ClientModel, ClientModel> {
    @Override
    public ClientModel execute(ClientModel data) {
        throw new RuntimeException("客户端异常信息");
    }

    @Override
    public Class<ClientModel> getDataType() {
        return ClientModel.class;
    }
}
