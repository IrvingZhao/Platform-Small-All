package cn.irving.zhao.platform.weixin.mp.message.send.menu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 按钮配置信息
 */
@Getter
@Setter
public final class Button {

    /**
     * 创建单击事件菜单
     *
     * @param name 菜单名称
     * @param key  单击事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generateClickButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.CLICK;
        return result;
    }

    /**
     * 创建打开链接菜单
     *
     * @param name 菜单名称
     * @param url  网页链接
     * @return 菜单按钮实例
     */
    public static Button generateViewButton(String name, String url) {
        Button result = new Button();
        result.name = name;
        result.url = url;
        result.type = MenuType.VIEW;
        return result;
    }

    /**
     * 创建扫码事件菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generateScanCodePushButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.SCAN_CODE_PUSH;
        return result;
    }

    /**
     * 创建扫码事件，并提示消息接受中菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generateScanCodeWaitMsgButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.SCAN_CODE_WAIT_MSG;
        return result;
    }

    /**
     * 创建调用相机拍照菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generatePicSysPhotoButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.PIC_SYS_PHOTO;
        return result;
    }

    /**
     * 创建调用相机或相册发图菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generatePicPhotoOrAlbumButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.PIC_PHOTO_OR_ALBUM;
        return result;
    }

    /**
     * 创建调用微信相册菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generatePicWeixinButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.PIC_WEIXIN;
        return result;
    }

    /**
     * 创建地理位置发送菜单
     *
     * @param name 菜单名称
     * @param key  事件下发参数
     * @return 菜单按钮实例
     */
    public static Button generateLocationSelectButton(String name, String key) {
        Button result = new Button();
        result.name = name;
        result.key = key;
        result.type = MenuType.LOCATION_SELECT;
        return result;
    }

    /**
     * 创建素材发送菜单
     *
     * @param name    菜单名称
     * @param mediaId 素材id
     * @return 菜单按钮实例
     */
    public static Button generateMediaIdButton(String name, String mediaId) {
        Button result = new Button();
        result.name = name;
        result.mediaId = mediaId;
        result.type = MenuType.MEDIA_ID;
        return result;
    }

    /**
     * 创建图文素材消息发送菜单
     *
     * @param name    菜单名称
     * @param mediaId 素材id
     * @return 菜单按钮实例
     */
    public static Button generateViewLimitedButton(String name, String mediaId) {
        Button result = new Button();
        result.name = name;
        result.mediaId = mediaId;
        result.type = MenuType.VIEW_LIMITED;
        return result;
    }

    /**
     * 创建打开小程序菜单
     *
     * @param name     菜单名称
     * @param url      不支持小程序的客户端打开的地址
     * @param appId    小程序appId
     * @param pagePath 小程序地址
     */
    public static Button generateMiniProgramButton(String name, String url, String appId, String pagePath) {
        Button result = new Button();
        result.name = name;
        result.url = url;
        result.appId = appId;
        result.pagePath = pagePath;
        result.type = MenuType.MINI_PROGRAM;
        return result;
    }

    /**
     * 添加子菜单
     *
     * @param button 子菜单对象
     */
    public Button addSubButton(Button button) {
        if (subButton == null) {
            synchronized (this) {
                if (subButton == null) {
                    subButton = new ArrayList<>();
                }
            }
        }
        subButton.add(button);
        return this;
    }


    private Button() {
    }

    private String name;//菜单标题
    private MenuType type;//菜单类型
    private String key;//菜单key
    private String url;//网页链接地址
    @JsonProperty("media_id")
    private String mediaId;//素材id
    @JsonProperty("appid")
    private String appId;//小程序appid
    @JsonProperty("pagepath")
    private String pagePath;//小程序页面路径
    @JsonProperty("sub_button")
    private List<Button> subButton;//子菜单

}
