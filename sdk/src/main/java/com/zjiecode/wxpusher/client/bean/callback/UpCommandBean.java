package com.zjiecode.wxpusher.client.bean.callback;

/**
 * 说明：上行消息内容
 * 作者：zjiecode
 * 时间：2020-10-17
 */
public class UpCommandBean {
    private String uid;
    private String appKey;
    private String appName;
    private Long time;
    //附加信息
    private String content;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
