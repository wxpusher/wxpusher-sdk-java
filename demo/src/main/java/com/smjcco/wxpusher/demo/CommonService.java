package com.smjcco.wxpusher.demo;

import com.smjcco.wxpusher.sdk.WxPusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonService {
    @Value("${spring.profiles.active}")
    String activeEnv;
    @Value("${wxpusher.biz.apptoken}")
    private String appToken;

    @PostConstruct
    private void init() {
        log.info("运行环境：" + activeEnv);
        //初始化默认的WxPusher
        WxPusher.initDefaultWxPusher(appToken);

    }
}
