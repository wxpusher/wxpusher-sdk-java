package com.smjcco.wxpusher.client.sdk.demo.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回结果数据结构
 */
@Setter
@Getter
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result(ResultCode code, String msg, T data) {
        this.code = code.getCode();
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultCode code, String msg) {
        this.code = code.getCode();
        this.msg = msg;
    }
    public static <T> Result<T> getSuccess(T data) {
        return new Result<>(ResultCode.SUCCESS, "处理成功", data);
    }

    public static <T> Result<T> getSuccess() {
        return new Result<>(ResultCode.SUCCESS, "处理成功");
    }

    public static Result<?> getBizFail(String msg) {
        return new Result<>(ResultCode.BIZ_FAIL, msg);
    }

    public  boolean isSuccess() {
        return code == ResultCode.SUCCESS.getCode();
    }

    @Override
    public String toString() {
        return "[" + getCode() + "]" + getMsg();
    }
}
