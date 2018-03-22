package cn.irving.zhao.platform.weixin.mp.message.send.menu.entity;

/**
 * 菜单类型
 */
public enum MenuType {
    /**
     * 推送点击事件
     */
    click,
    /**
     * 跳转指定地址
     */
    view,
    /**
     * 调用扫一扫，并将扫码结果推送服务器
     */
    scancode_push,
    /**
     * 调用扫一扫，并将扫码结果推送服务器，同时提示消息接收中
     */
    scancode_waitmsg,
    /**
     * 调用相机，拍照后将图片传输服务器
     */
    pic_sysphoto,
    /**
     * 调用本地图库或相机
     */
    pic_photo_or_album,
    /**
     * 调用微信相册
     */
    pic_weixin,
    /**
     * 调用位置选择工具
     */
    location_select,
    /**
     * 根据素材id，返回永久素材
     */
    media_id,
    /**
     * 根据素材id，返回素材对应的图文消息
     */
    view_limited,
    /**
     * 小程序
     */
    miniprogram;
}
