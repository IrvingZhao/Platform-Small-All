package cn.irving.zhao.platform.weixin.mp.entity.message;

import cn.irving.zhao.platform.weixin.base.message.send.BaseSendOutputMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserInfoOutputMessage extends BaseSendOutputMessage<UserInfoInputMessage> {
    private final static String URL = "https://api.weixin.qq.com/sns/userinfo";

    private final String accessToken;

    private final String openId;

    private final String lang = "zh_CN";

    @Override
    public String getUrl() {
        return URL;
    }
}
