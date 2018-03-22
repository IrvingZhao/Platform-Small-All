package cn.irving.zhao.platform.weixin.mp.message.send.menu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 菜单信息
 */
public class Menu {
    @JsonProperty("menuid")
    private String menuId;
    private List<Button> button;
    private MatchRule matchrule;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public List<Button> getButton() {
        return button;
    }

    public void setButton(List<Button> button) {
        this.button = button;
    }

    public MatchRule getMatchrule() {
        return matchrule;
    }

    public void setMatchrule(MatchRule matchrule) {
        this.matchrule = matchrule;
    }
}
