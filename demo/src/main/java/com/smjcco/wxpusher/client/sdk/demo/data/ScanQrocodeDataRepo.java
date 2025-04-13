package com.smjcco.wxpusher.client.sdk.demo.data;

import java.util.*;

import com.smjcco.wxpusher.client.sdk.bean.callback.AppSubscribeBean;
import lombok.extern.slf4j.Slf4j;

/**
 * 说明：缓存一下数据，demo就不用数据库了，正式产品的时候，可以使用db存储
 * 作者：zjiecode
 * 时间：2019-10-05
 */
@Slf4j
public class ScanQrocodeDataRepo {

    private static final long TimeOut = 1000 * 60 * 60;//1小时

    private static final Map<String, AppSubscribeBean> data = new HashMap<>();

    public static synchronized void put(String key, AppSubscribeBean value) {
        data.put(key, value);
    }

    public static synchronized AppSubscribeBean get(String key) {
        return data.get(key);
    }

    public static synchronized void remove(String key) {
        data.remove(key);
    }

    /**
     * 删除超时的数据
     */
    public static synchronized void clearTimeout() {
        Set<Map.Entry<String, AppSubscribeBean>> entries = data.entrySet();
        Iterator<Map.Entry<String, AppSubscribeBean>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, AppSubscribeBean> entry = iterator.next();
            if (System.currentTimeMillis() - entry.getValue().getTime() > TimeOut) {
                data.remove(entry.getKey());
            }
        }
    }
}
