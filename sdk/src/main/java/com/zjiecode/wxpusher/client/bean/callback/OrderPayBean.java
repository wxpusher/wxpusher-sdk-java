package com.zjiecode.wxpusher.client.bean.callback;

public class OrderPayBean {
    //付费增加的时间，毫秒。退款是负数，表示减少的订阅时间。
    private long addTime;
    //金额，单位分，退款是负数
    private long amount;
    //发生的应用id
    private long appId;
    //发生时间，毫秒级时间戳
    private long createTime;
    //产品id
    private int prodId;
    //支付或者退款的交易号，和用户微信账单中的商户号对应
    private String tradeNo;
    //1表示付款，2表示退款
    private int type;
    //发生用户的uid
    private String uid;

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
