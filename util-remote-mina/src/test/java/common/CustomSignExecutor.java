package common;

import cn.irving.zhao.util.base.security.CryptoUtils;
import cn.irving.zhao.util.remote.mina.core.sign.MinaMessageSignExecutor;

public class CustomSignExecutor implements MinaMessageSignExecutor {
    @Override
    public String getMessageSign(String data, String salt) {
        return CryptoUtils.getHash(data, salt);
    }

    @Override
    public boolean validMessage(String data, String sign, String salt) {
        return CryptoUtils.verify(sign, data, salt);
    }
}
