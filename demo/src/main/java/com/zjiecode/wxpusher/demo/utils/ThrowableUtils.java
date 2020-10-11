package com.zjiecode.wxpusher.demo.utils;


import org.springframework.lang.Nullable;

public class ThrowableUtils {
    /**
     * 获取根错误
     */
    public static @Nullable Throwable getRootThrowable(Throwable throwable){
        if(throwable==null){
            return null;
        }
        while (throwable.getCause()!=null){
            throwable = throwable.getCause();
        }
        return throwable;
    }
}
