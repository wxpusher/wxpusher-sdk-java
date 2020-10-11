package com.zjiecode.wxpusher.demo.result;

import lombok.Getter;

/**
 * 返回http错误，对外暴露http code
 * 主要用于网关健康检查等
 */
@Getter
public class HttpException extends RuntimeException {
    private int httpStatus;//http返回密

    public HttpException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
