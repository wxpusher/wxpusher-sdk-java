package com.zjiecode.wxpusher.client;

import com.alibaba.fastjson.JSONObject;
import com.zjiecode.wxpusher.client.bean.Message;
import com.zjiecode.wxpusher.client.bean.Page;
import com.zjiecode.wxpusher.client.bean.Result;
import com.zjiecode.wxpusher.client.bean.WxUser;

import org.junit.Test;

/**
 * 说明：接口测试
 * 作者：zjiecode
 * 时间：2019-05-03
 */
public class ClientTest {


    @Test
    public void testQuery() {
        Result<Page<WxUser>> users = WxPusher.queryWxUser("AT_xxxxx", "UID_xxxxx");
        System.out.println(JSONObject.toJSONString(users));
    }
}
