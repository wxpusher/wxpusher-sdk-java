package com.zjiecode.wxpusher.client.bean;

/**
 * 说明：微信用户数据
 * 作者：zjiecode
 * 时间：2019-10-28
 */
public class WxUser {

    //id，如果调用删除或者拉黑接口，需要这个id
    private long id;
    //UID，用户标志
    private String uid;
    /**
     * 用户是否打开接收消息
     */
    private boolean enable;
    //用户关注的应用或者主题id，根据type来区分
    private Long appOrTopicId;
    // 关注的应用或者主题名字
    private String target;
    /**
     * 昵称
     *
     * @deprecated 微信已经不再返回这个字段
     */
    private String nickName;

    /**
     * 微信头像
     *
     * @deprecated 微信已经不再返回这个字段
     */
    private String headImg;
    /**
     * 是否拉黑用户
     */
    private boolean reject;
    //关注类型，0：关注应用，1：关注topic
    private int type;
    //关注的应用或者主题名字
    private String name;

    /**
     * /0表示用户不是付费用户，大于0表示用户付费订阅到期时间，毫秒级时间戳
     */
    private long payEndTime;
    //用户关注应用的时间
    private long createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Long getAppOrTopicId() {
        return appOrTopicId;
    }

    public void setAppOrTopicId(Long appOrTopicId) {
        this.appOrTopicId = appOrTopicId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public boolean isReject() {
        return reject;
    }

    public void setReject(boolean reject) {
        this.reject = reject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(long payEndTime) {
        this.payEndTime = payEndTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
