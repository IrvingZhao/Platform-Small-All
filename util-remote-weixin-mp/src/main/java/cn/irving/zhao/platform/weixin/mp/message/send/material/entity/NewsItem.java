package cn.irving.zhao.platform.weixin.mp.message.send.material.entity;

import cn.irving.zhao.platform.weixin.mp.message.entity.BoolType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 单个图文素材
 */
@Setter
@Getter
public class NewsItem {

    private String title;//图文消息标题
    @JsonProperty("thumb_media_id")
    private String thumbMediaId;//封面图片
    @JsonProperty("show_cover_pic")
    private BoolType showCoverPic;//是否展示封面图，0，不展示，1展示
    private String author;//作者
    private String digest;//图文摘要，仅限单图文消息
    private String content;//图文消息内容，小于2W字符，小于1M，无js
    private String url;//图文页地址
    @JsonProperty("content_source_url")
    private String contentSourceUrl;//阅读原文地址

    public void setShowCoverPic(Boolean showCoverPic) {
        this.showCoverPic = showCoverPic ? BoolType.TRUE : BoolType.FALSE;
    }

}
