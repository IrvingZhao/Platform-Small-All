package cn.irving.zhao.util.remote.mina.core.message;

public interface MinaMessageData {

    String getClientId();

    String getMethod();

    Object getData();

}
