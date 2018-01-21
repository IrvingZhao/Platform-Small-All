package client;

import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;

public class ClientMethod1 implements MinaMessageMethod<ClientModel, ClientModel> {

    @Override
    public ClientModel execute(ClientModel data) {
        System.out.println("=============================clientMethodExecute================================");
        System.out.println(data);
        System.out.println("============= 执行 等待 ===============");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ClientModel("我是客户端", "客户端返回消息");
    }

    @Override
    public Class<ClientModel> getDataType() {
        return ClientModel.class;
    }
}
