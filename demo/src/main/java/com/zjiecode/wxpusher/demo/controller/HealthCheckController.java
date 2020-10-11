package com.zjiecode.wxpusher.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.zjiecode.wxpusher.client.WxPusher;
import com.zjiecode.wxpusher.demo.result.HttpException;
import com.zjiecode.wxpusher.demo.result.Result;
import com.zjiecode.wxpusher.demo.result.ResultCode;
import com.zjiecode.wxpusher.demo.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/status")
public class HealthCheckController {
    private final long startTime = System.currentTimeMillis();

    /**
     * 返回服务状态
     */
    @GetMapping("/health")
    public Result alive() {
        long now = System.currentTimeMillis();
        Map<String, Object> data = new HashMap<>();
        data.put("系统启动时间", DateUtil.formatTimeLong(now - startTime));
        com.zjiecode.wxpusher.client.bean.Result result = WxPusher.queryMessageStatus(1L);
        boolean connectWxPusher = (result.getCode() == ResultCode.SUCCESS.getCode()) ||
                (result.getCode() == ResultCode.BIZ_FAIL.getCode());
        data.put("到WxPuhser的链接状态", connectWxPusher);
        data.put("响应时间", DateUtil.formatTimeLong(System.currentTimeMillis() - now));
        if (connectWxPusher) {
            return Result.getSuccess(data);
        }
        throw new HttpException(JSONObject.toJSONString(data), 500);
    }
}
