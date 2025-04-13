package com.smjcco.wxpusher.client.sdk.demo.data;

import com.smjcco.wxpusher.client.sdk.bean.callback.UpCommandBean;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：上行的用户数据
 * 作者：zjiecode
 * 时间：2020-10-17
 */
@Slf4j
public class UpCommandDataRepo {

    private static final long TimeOut = 1000 * 60 * 10;//1小时

    private static final List<UpCommandBean> data = new ArrayList<>();

    public static synchronized void add(UpCommandBean commandBean) {
        data.add(commandBean);
    }

    public static synchronized List<UpCommandBean> getData() {
        return data;
    }

    /**
     * 删除超时的数据
     */
    public static synchronized void clearTimeout() {
        data.removeIf(commandBean -> (System.currentTimeMillis() - commandBean.getTime()) > TimeOut);
    }
}
