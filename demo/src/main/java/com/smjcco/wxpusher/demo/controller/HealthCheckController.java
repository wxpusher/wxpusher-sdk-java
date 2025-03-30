package com.smjcco.wxpusher.demo.controller;

import com.alibaba.fastjson2.JSON;
import com.smjcco.wxpusher.demo.result.HttpException;
import com.smjcco.wxpusher.demo.result.Result;
import com.smjcco.wxpusher.demo.result.ResultCode;
import com.smjcco.wxpusher.demo.utils.DateUtil;
import com.smjcco.wxpusher.sdk.WxPusher;
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
        com.smjcco.wxpusher.sdk.bean.Result<Integer> result = WxPusher.getDefaultWxPusher().queryMessageStatus(1L);
        boolean connectWxPusher = (result.getCode() == ResultCode.SUCCESS.getCode()) ||
                (result.getCode() == ResultCode.BIZ_FAIL.getCode());
        data.put("到WxPuhser的链接状态", connectWxPusher);
        data.put("响应时间", DateUtil.formatTimeLong(System.currentTimeMillis() - now));
        if (connectWxPusher) {
            return Result.getSuccess(data);
        }
        throw new HttpException(JSON.toJSONString(data), 500);
    }
}
