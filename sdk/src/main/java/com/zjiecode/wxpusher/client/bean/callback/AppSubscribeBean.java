package com.zjiecode.wxpusher.client.bean.callback;

/**
 * 说明：二维码被扫描的时候的回调数据结构
 * 作者：zjiecode
 * 时间：2019-10-05
 */
public class AppSubscribeBean {
    public static final String SOURCE_SCAN = "scan";
    public static final String SOURCE_LINK = "link";
    public static final String SOURCE_COMMAND = "command";
    public static final String SOURCE_ORDER = "order";

    private String uid;
    private Long appId;
    private String appName;
    private String userName;
    private String userHeadImg;
    private Long time;
    //来源：scan:扫码订阅，link：通过链接订阅
    private String source;
    //附加信息
    private String extra;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }
}
