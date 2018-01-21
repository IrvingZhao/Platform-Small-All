package common;

import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethod;
import cn.irving.zhao.util.remote.mina.core.method.MinaMessageMethodFactory;

import java.util.HashMap;
import java.util.Map;

public class CustomMethodFactory implements MinaMessageMethodFactory {

    private Map<String, MinaMessageMethod> methods = new HashMap<>();

    @Override
    public MinaMessageMethod getMethod(String methodName) {
        return methods.get(methodName);
    }

    @Override
    public void registerMethod(String methodName, MinaMessageMethod method) {
        methods.put(methodName, method);
    }
}
