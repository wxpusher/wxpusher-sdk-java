package com.zjiecode.wxpusher.demo.cron;

import com.zjiecode.wxpusher.demo.data.ScanQrocodeDataRepo;
import com.zjiecode.wxpusher.demo.data.UpCommandDataRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 说明：删除登陆的时候的二维码
 * 作者：zhangjie33
 * 时间：2020/5/5
 */
@Slf4j
@Configuration
@EnableScheduling
public class ClearTimeoutDataScheduleTask {


    //每天凌晨3点运行
    @Scheduled(cron = "0 */10 * * * ?")
    private void clearTimeOutData() {
        log.info("清理数据-开始");
        ScanQrocodeDataRepo.clearTimeout();
        UpCommandDataRepo.clearTimeout();
        log.info("清理数据-完成");
    }
}
