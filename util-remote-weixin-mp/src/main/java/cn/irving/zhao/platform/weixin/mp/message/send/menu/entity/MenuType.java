package cn.irving.zhao.platform.weixin.mp.message.send.menu.entity;

import cn.irving.zhao.util.base.serial.custom.CustomEnumValue;

/**
 * 菜单类型
 */
public enum MenuType implements CustomEnumValue<MenuType, String> {
    /**
     * 推送点击事件
     */
    CLICK("click"),
    /**
     * 跳转指定地址
     */
    VIEW("view"),
    /**
     * 调用扫一扫，并将扫码结果推送服务器
     */
    SCAN_CODE_PUSH("scancode_push"),
    /**
     * 调用扫一扫，并将扫码结果推送服务器，同时提示消息接收中
     */
    SCAN_CODE_WAIT_MSG("scancode_waitmsg"),
    /**
     * 调用相机，拍照后将图片传输服务器
     */
    PIC_SYS_PHOTO("pic_sysphoto"),
    /**
     * 调用本地图库或相机
     */
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),
    /**
     * 调用微信相册
     */
    PIC_WEIXIN("pic_weixin"),
    /**
     * 调用位置选择工具
     */
    LOCATION_SELECT("location_select"),
    /**
     * 根据素材id，返回永久素材
     */
    MEDIA_ID("media_id"),
    /**
     * 根据素材id，返回素材对应的图文消息
     */
    VIEW_LIMITED("view_limited"),
    /**
     * 小程序
     */
    MINI_PROGRAM("miniprogram");

    MenuType(String code) {
        this.code = code;
    }

    private String code;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public MenuType[] getValues() {
        return MenuType.values();
    }
}
