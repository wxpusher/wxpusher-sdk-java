package com.smjcco.wxpusher.demo.controller;

import com.alibaba.fastjson2.JSON;
import com.smjcco.wxpusher.demo.data.ScanQrocodeDataRepo;
import com.smjcco.wxpusher.demo.data.UpCommandDataRepo;
import com.smjcco.wxpusher.sdk.WxPusher;
import com.smjcco.wxpusher.sdk.bean.Message;
import com.smjcco.wxpusher.sdk.bean.callback.AppSubscribeBean;
import com.smjcco.wxpusher.sdk.bean.callback.BaseCallBackReq;
import com.smjcco.wxpusher.sdk.bean.callback.UpCommandBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 说明：接收来自WxPusher服务的回调
 * 当用户扫码关注的时候会触发
 * 作者：zjiecode
 * 时间：2019-10-05
 */
@RestController
@RequestMapping("/demo")
@Slf4j
public class CallBackController {
    @Value("${wxpusher.biz.apptoken}")
    private String appToken;

    @PostMapping("/callback")
    public String callback(@RequestBody BaseCallBackReq callBackReq) {
        log.info("收到wxpusher回调:{}", JSON.toJSONString(callBackReq));
        if (BaseCallBackReq.ACTION_APP_SUBSCRIBE.equalsIgnoreCase(callBackReq.getAction())) {
            AppSubscribeBean appSubscribeBean = JSON.parseObject(JSON.toJSONString(callBackReq.getData()), AppSubscribeBean.class);
            if (!StringUtils.isEmpty(appSubscribeBean.getExtra())) {
                //这里的extra 就是创建二维码的时候，携带的数据，也就是qrcodeId，
                ScanQrocodeDataRepo.put(appSubscribeBean.getExtra(), appSubscribeBean);
                log.info("存储回调数据:{}", JSON.toJSONString(appSubscribeBean));
                //扫码以后，发送一条消息给用户
                Message message = new Message();
                message.setContent("扫描成功，你可以使用demo演示程序发送消息");
                message.setContentType(Message.CONTENT_TYPE_TEXT);
                message.setUid(appSubscribeBean.getUid());
                WxPusher.getDefaultWxPusher().send(message);
            } else {
                //无参数二维码（默认二维码），不需要发送提醒，会自动发送后台设置的
            }
            return "";
        }
        //上行命令消息
        if (BaseCallBackReq.ACTION_SEND_UP_CMD.equalsIgnoreCase(callBackReq.getAction())) {
            UpCommandBean upCommandBean = JSON.parseObject(JSON.toJSONString(callBackReq.getData()), UpCommandBean.class);
            UpCommandDataRepo.add(upCommandBean);
            //收到上行消息后，发送一条消息给用户
            Message message = new Message();
            message.setContent("Demo程序收到上行消息:" + upCommandBean.getContent());
            message.setContentType(Message.CONTENT_TYPE_TEXT);
            message.setUid(upCommandBean.getUid());
            message.setAppToken(appToken);
            WxPusher.getDefaultWxPusher().send(message);
            return "";
        }

        //直接返回 空串 即可
        return "";
    }
}
