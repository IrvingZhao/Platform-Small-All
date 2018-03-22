package cn.irving.zhao.platform.weixin.mp.message.send.menu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 个性化菜单匹配规则类
 */
public class MatchRule {

    @JsonProperty("tag_id")
    private String tagId;

    private Sex sex;

    @JsonProperty("client_platform_type")
    private PlatformType platform;

    private String country;

    private String province;

    private String city;

    private String language;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public PlatformType getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformType platform) {
        this.platform = platform;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
