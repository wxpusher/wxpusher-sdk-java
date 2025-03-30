package com.smjcco.wxpusher.sdk.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * 说明：
 * 作者：zjiecode
 * 时间：2019-09-05
 */
public class Message {
    /**
     * 1:text，可以直接显示在卡片里面
     * 2:html，点击以后查看，支持html
     * 3:md，markdown格式，和html类似
     */
    public static final int CONTENT_TYPE_TEXT = 1;
    public static final int CONTENT_TYPE_HTML = 2;
    public static final int CONTENT_TYPE_MD = 3;

    /**
     * verifyPayType=0，表示本条消息，不验证付费状态，发送给所有用户
     * verifyPayType=1，表示本条消息，只发送给付费订阅期内的用户
     * verifyPayType=2，表示本条消息，只发送给未订阅或者付费订阅过期的用户
     */
    public static final int VERIFY_PAY_TYPE_IGNORE = 0;
    public static final int VERIFY_PAY_TYPE_IN_PAY = 1;
    public static final int VERIFY_PAY_TYPE_OUT_PAY = 2;

    private String appToken;

    //发送的目标
    private Set<String> uids;
    private Set<Long> topicIds;

    private Integer contentType;

    private String content;

    private String summary;

    private Integer verifyPayType;

    private String url;

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public Set<String> getUids() {
        return uids;
    }

    public void setUid(String uid) {
        this.uids = new HashSet<>(1);
        this.uids.add(uid);
    }

    public void setTopicId(Long topicId) {
        this.topicIds = new HashSet<>(1);
        this.topicIds.add(topicId);
    }

    public void setUids(Set<String> uids) {
        this.uids = uids;
    }

    public Set<Long> getTopicIds() {
        return topicIds;
    }

    public void setTopicIds(Set<Long> topicIds) {
        this.topicIds = topicIds;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 只需要 body 标签内部的内容。
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setVerifyPayType(Integer verifyPayType) {
        this.verifyPayType = verifyPayType;
    }

    public Integer getVerifyPayType() {
        return verifyPayType;
    }
}
