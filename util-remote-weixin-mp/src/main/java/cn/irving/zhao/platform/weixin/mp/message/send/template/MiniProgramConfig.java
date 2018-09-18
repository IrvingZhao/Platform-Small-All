package cn.irving.zhao.platform.weixin.mp.message.send.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 模板消息跳转至小程序
 * */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MiniProgramConfig {

    @JsonProperty("appid")
    private String appId;
    @JsonProperty("pagepath")
    private String pagePath;

}
