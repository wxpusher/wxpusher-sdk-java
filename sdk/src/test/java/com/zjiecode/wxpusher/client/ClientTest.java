package com.zjiecode.wxpusher.client;

import com.alibaba.fastjson.JSONObject;
import com.zjiecode.wxpusher.client.bean.*;

import org.junit.Test;

import java.util.List;

/**
 * 说明：接口测试
 * 作者：zjiecode
 * 时间：2019-05-03
 */
public class ClientTest {


    @Test
    public void sendMsg() {
        Message message = new Message();
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setContent("接口测试接口测试接口测试");
        message.setSummary("消息摘要");
        message.setAppToken("AT_xxx");
        message.setUid("UID_xx");
        Result<List<MessageResult>> result = WxPusher.send(message);
        System.out.println(JSONObject.toJSONString(result));
    }

    @Test
    public void testQuery() {
        Result<Page<WxUser>> users = WxPusher.queryWxUser("AT_xxxxx", "UID_xxxxx");
        System.out.println(JSONObject.toJSONString(users));
    }
}
