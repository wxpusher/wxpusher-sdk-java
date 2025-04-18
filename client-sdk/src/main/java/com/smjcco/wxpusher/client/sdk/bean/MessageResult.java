package com.smjcco.wxpusher.client.sdk.bean;

/**
 * 说明：
 * 作者：zjiecode
 * 时间：2019-09-23
 */
public class MessageResult {
    private String uid;
    private String status;
    private Integer code;
    private Long messageId;

    /**
     * 请求服务端是否成功，这个判断成功 以后，再判断业务状态
     * @return 结果
     */
    public boolean isSuccess() {
        return code == ResultCode.SUCCESS.getCode();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
