package common;

import cn.irving.zhao.util.remote.mina.core.sign.ClientHashSaltHolder;

import java.util.HashMap;
import java.util.Map;

public class CustomServerSaltHolder implements ClientHashSaltHolder {

    private Map<String, String> saltHolder = new HashMap<>();

    public CustomServerSaltHolder() {
        saltHolder.put("ef5ea89d-f80d-4846-b387-cbfe22142310", "EWuqCA0zjEka4qrVujAnJvWlScTjGqeFfGqQMDEYsFM=");
    }

    @Override
    public String getSalt(String clientKey) {
        return saltHolder.get(clientKey);
    }
}
