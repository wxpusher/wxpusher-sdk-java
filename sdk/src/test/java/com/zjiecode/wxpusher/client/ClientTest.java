package com.zjiecode.wxpusher.client;

import com.alibaba.fastjson.JSONObject;
import com.zjiecode.wxpusher.client.bean.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 说明：接口测试
 * 作者：zjiecode
 * 时间：2019-05-03
 */
public class ClientTest {


    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger();
        AtomicLong time = new AtomicLong(System.currentTimeMillis());
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                while (true) {
                    Message message = new Message();
                    message.setContentType(Message.CONTENT_TYPE_HTML);
                    message.setContent("接口测试<b>接口测</b>试接口测试");
                    message.setSummary(Thread.currentThread() + "-测试消息摘要:" + System.currentTimeMillis() + "<br />");
                    message.setAppToken("AT_a2zb61nhS33h54NOLvSYxgrMO4KcJ2vG");
                    message.setUid("UID_LffjrW6nh4vpNWGC85QMzX82OURf");
                    Result<List<MessageResult>> result = WxPusher.send(message);
                    count.incrementAndGet();
                    if (!result.isSuccess()) {
                        System.out.println(JSONObject.toJSONString(result));
                    }
                    if (count.get() % 100 == 0) {
                        long during = System.currentTimeMillis() - time.get();
                        time.set(System.currentTimeMillis());
                        System.out.println(count.get() + "-" + (100F / during * 1000F));
                    }
                }
            }).start();
        }

    }

    @Test
    public void testQuery() {
        Result<Page<WxUser>> users = WxPusher.queryWxUser("AT_xxxxx", "UID_xxxxx");
        System.out.println(JSONObject.toJSONString(users));
    }
}
