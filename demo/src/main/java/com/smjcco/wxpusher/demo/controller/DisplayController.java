package com.smjcco.wxpusher.demo.controller;

import com.smjcco.wxpusher.demo.data.ScanQrocodeDataRepo;
import com.smjcco.wxpusher.demo.data.UpCommandDataRepo;
import com.smjcco.wxpusher.demo.result.BizException;
import com.smjcco.wxpusher.demo.utils.RandomUtil;
import com.smjcco.wxpusher.sdk.WxPusher;
import com.smjcco.wxpusher.sdk.bean.CreateQrcodeReq;
import com.smjcco.wxpusher.sdk.bean.CreateQrcodeResp;
import com.smjcco.wxpusher.sdk.bean.Result;
import com.smjcco.wxpusher.sdk.bean.ResultCode;
import com.smjcco.wxpusher.sdk.bean.callback.AppSubscribeBean;
import com.smjcco.wxpusher.sdk.bean.callback.UpCommandBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明：演示发送消息
 * 作者：zjiecode
 * 时间：2019-10-05
 */
@RestController
@RequestMapping("/demo")
@Slf4j
public class DisplayController {

    /**
     * 发送普通文本
     */
    @GetMapping("")
    public ModelAndView display() {
        //生成一个随机字符串来当作二维码标志，实际使用的时候，可以使用用户ID，避免重复。最大64位
        String qrcodeId = RandomUtil.getRandomStr(32);
        //创建一个参数二维码
        CreateQrcodeReq createQrcodeReq = new CreateQrcodeReq();
        createQrcodeReq.setValidTime(3600);//二维码有效时间
        createQrcodeReq.setExtra(qrcodeId);
        Result<CreateQrcodeResp> tempQrcode = WxPusher.getDefaultWxPusher().createAppTempQrcode(createQrcodeReq);
        if (!tempQrcode.isSuccess()) {
            throw new BizException(tempQrcode.getMsg());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("qrcodeUrl", tempQrcode.getData().getUrl());
        data.put("qrcodeId", qrcodeId);
        return new ModelAndView("display", data);
    }

    @GetMapping("/getuid/{qrcodeId}")
    public Result<Map<String, Object>> getUidByQrcodeId(@PathVariable("qrcodeId") String qrcodeId) {
        if (StringUtils.isEmpty(qrcodeId)) {
            return new Result<>(ResultCode.BIZ_FAIL, "二维码错误");
        }
        AppSubscribeBean appSubscribeBean = ScanQrocodeDataRepo.get(qrcodeId);
        List<UpCommandBean> commandBeanList = UpCommandDataRepo.getData();
        Map<String, Object> data = new HashMap<>();
        data.put("scan", appSubscribeBean);
        data.put("upCommand", commandBeanList);
        Result<Map<String, Object>> result = new Result<>(ResultCode.SUCCESS, "处理成功");
        result.setData(data);
        ScanQrocodeDataRepo.remove(qrcodeId);
        return result;
    }


}
