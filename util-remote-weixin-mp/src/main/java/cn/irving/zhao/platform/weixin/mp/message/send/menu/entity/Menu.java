package cn.irving.zhao.platform.weixin.mp.message.send.menu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 菜单信息
 */
@Getter
@Setter
public class Menu {
    @JsonProperty("menuid")
    private String menuId;
    private List<Button> button;
    private MatchRule matchrule;

}
