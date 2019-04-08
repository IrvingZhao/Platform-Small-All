package cn.irving.zhao.platform.weixin.mp.receive.entity;

import cn.irving.zhao.platform.weixin.mp.config.enums.SendMessageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ReplayNewsMessage extends BaseReplayMessage {
    public ReplayNewsMessage(String toUserName, String fromUserName) {
        super(toUserName, fromUserName, SendMessageType.NEWS);
    }

    @Getter
    @JsonProperty("ArticleCount")
    private int articleCount;

    @Getter
    @JsonProperty("articles")
    @JacksonXmlElementWrapper(namespace = "Item")
    private final List<ReplayNews> articles = new ArrayList<>();

    /**
     * 添加一片图文
     *
     * @param title       图文标题
     * @param description 图文描述
     * @param picUrl      图文图片地址，支持JPG、PNG，大图360x200，小图200x200
     * @param url         打开地址
     */
    public ReplayNewsMessage addNewArticle(String title, String description, String picUrl, String url) {
        this.articles.add(new ReplayNews(title, description, picUrl, url));
        this.articleCount = this.articles.size();
        return this;
    }
}
