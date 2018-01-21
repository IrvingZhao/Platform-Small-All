package client;

import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;

public class ClientMethod implements MinaMessageMethod<ClientModel, Object> {
    @Override
    public Object execute(ClientModel data) {
        System.out.println("=============================clientMethodExecute================================");
        System.out.println(data);
        return null;
    }

    @Override
    public Class<ClientModel> getDataType() {
        return ClientModel.class;
    }
}
