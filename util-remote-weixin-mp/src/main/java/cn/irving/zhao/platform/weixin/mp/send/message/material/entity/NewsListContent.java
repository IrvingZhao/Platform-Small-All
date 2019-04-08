package cn.irving.zhao.platform.weixin.mp.send.message.material.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsListContent {

    @JsonProperty("news_item")
    private List<NewsItem> newsItem;

}
