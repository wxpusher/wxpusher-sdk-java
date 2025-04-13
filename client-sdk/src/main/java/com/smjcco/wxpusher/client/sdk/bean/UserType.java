package com.smjcco.wxpusher.client.sdk.bean;

public enum UserType {
    APP(0),
    TOPIC(1),
    ;
    private final Integer type;

    UserType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}