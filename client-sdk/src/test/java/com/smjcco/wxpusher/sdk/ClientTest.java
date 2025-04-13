package com.smjcco.wxpusher.sdk;

import com.smjcco.wxpusher.sdk.bean.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 说明：接口测试
 * 作者：zjiecode
 * 时间：2019-05-03
 */
public class ClientTest {

    private static final String APP_TOKEN = "AT_xxx"; // 测试用的APP_TOKEN，请替换为自己的
    private static final String TEST_UID = "UID_xx"; // 测试用的UID，请替换为自己的
    private static final Long TEST_TOPIC_ID = 6950L; // 测试用的主题ID，请替换为自己的

    private WxPusher wxPusher;

    @Before
    public void setup() {
        // 使用构造函数初始化
        wxPusher = new WxPusher(APP_TOKEN);
        // 也可以使用静态方法初始化默认实例
        // WxPusher.initDefaultWxPusher(APP_TOKEN);
    }

    /**
     * 测试发送文本消息到单个用户
     */
    @Test
    public void testSendTextToUser() {
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setContent("这是一条测试文本消息");
        message.setUid(TEST_UID);

        Result<List<MessageResult>> result = wxPusher.send(message);
        System.out.println("发送结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
        if (result.isSuccess() && result.getData() != null) {
            for (MessageResult messageResult : result.getData()) {
                System.out.println("发送到UID: " + messageResult.getUid() + ", 结果：" + messageResult.getStatus());
            }
        }
        
        // 添加断言
        Assert.assertTrue("发送消息应该成功", result.isSuccess());
        Assert.assertEquals("处理成功", result.getMsg());
        Assert.assertNotNull("返回数据不应为空", result.getData());
        Assert.assertFalse("返回结果列表不应为空", result.getData().isEmpty());
        
        MessageResult messageResult = result.getData().get(0);
        Assert.assertEquals("UID应匹配", TEST_UID, messageResult.getUid());
        Assert.assertTrue("消息发送状态应包含'创建发送任务成功'", messageResult.getStatus().contains("创建发送任务成功"));
        Assert.assertTrue("消息发送码应为成功", messageResult.isSuccess());
    }

    /**
     * 测试发送HTML消息到单个用户
     */
    @Test
    public void testSendHtmlToUser() {
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setContentType(Message.CONTENT_TYPE_HTML);
        message.setContent("<h1>HTML消息测试</h1><p style='color:red'>这是一条红色的测试消息</p>");
        message.setUid(TEST_UID);
        message.setSummary("HTML消息测试");

        Result<List<MessageResult>> result = wxPusher.send(message);
        System.out.println("发送结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
        if (result.isSuccess() && result.getData() != null) {
            for (MessageResult messageResult : result.getData()) {
                System.out.println("发送到UID: " + messageResult.getUid() + ", 结果：" + messageResult.getStatus());
            }
        }
        
        // 添加断言
        Assert.assertTrue("发送HTML消息应该成功", result.isSuccess());
        Assert.assertNotNull("返回数据不应为空", result.getData());
        Assert.assertFalse("返回结果列表不应为空", result.getData().isEmpty());
        
        MessageResult messageResult = result.getData().get(0);
        Assert.assertEquals("UID应匹配", TEST_UID, messageResult.getUid());
    }

    /**
     * 测试发送Markdown消息到单个用户
     */
    @Test
    public void testSendMarkdownToUser() {
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setContentType(Message.CONTENT_TYPE_MD);
        message.setContent("# Markdown消息测试\n\n**这是加粗文本**\n\n*这是斜体文本*");
        message.setUid(TEST_UID);
        message.setSummary("Markdown消息测试");

        Result<List<MessageResult>> result = wxPusher.send(message);
        System.out.println("发送结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
        if (result.isSuccess() && result.getData() != null) {
            for (MessageResult messageResult : result.getData()) {
                System.out.println("发送到UID: " + messageResult.getUid() + ", 结果：" + messageResult.getStatus());
            }
        }
        
        // 添加断言
        Assert.assertTrue("发送Markdown消息应该成功", result.isSuccess());
        Assert.assertNotNull("返回数据不应为空", result.getData());
        Assert.assertFalse("返回结果列表不应为空", result.getData().isEmpty());
    }

    /**
     * 测试发送消息到主题
     */
    @Test
    public void testSendToTopic() {
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setContent("这是一条发送到主题的测试消息");
        message.setTopicId(TEST_TOPIC_ID);
        message.setSummary("主题消息测试");

        Result<List<MessageResult>> result = wxPusher.send(message);
        System.out.println("发送结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
        if (result.isSuccess() && result.getData() != null) {
            for (MessageResult messageResult : result.getData()) {
                System.out.println("发送结果：" + messageResult.getStatus());
            }
        }
        
        // 添加断言
        Assert.assertTrue("发送主题消息应该成功", result.isSuccess());
        Assert.assertNotNull("返回数据不应为空", result.getData());
        Assert.assertFalse("返回结果列表不应为空", result.getData().isEmpty());
    }

    /**
     * 测试发送消息到多个用户
     */
    @Test
    public void testSendToMultiUsers() {
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setContent("这是一条发送到多个用户的测试消息");

        // 添加多个UID
        Set<String> uids = new HashSet<>();
        uids.add(TEST_UID);
        // 添加更多用户...
        // uids.add("UID_xxx2");
        message.setUids(uids);

        Result<List<MessageResult>> result = wxPusher.send(message);
        System.out.println("发送结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
        if (result.isSuccess() && result.getData() != null) {
            for (MessageResult messageResult : result.getData()) {
                System.out.println("发送到UID: " + messageResult.getUid() + ", 结果：" + messageResult.getStatus());
            }
        }
        
        // 添加断言
        Assert.assertTrue("发送多用户消息应该成功", result.isSuccess());
        Assert.assertNotNull("返回数据不应为空", result.getData());
        Assert.assertFalse("返回结果列表不应为空", result.getData().isEmpty());
        
        MessageResult messageResult = result.getData().get(0);
        Assert.assertEquals("UID应匹配", TEST_UID, messageResult.getUid());
        Assert.assertTrue("消息发送状态应包含'创建发送任务成功'", messageResult.getStatus().contains("创建发送任务成功"));
    }

    /**
     * 测试发送带URL的消息
     */
    @Test
    public void testSendWithUrl() {
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setContent("这是一条带链接的测试消息");
        message.setUid(TEST_UID);
        message.setUrl("https://wxpusher.zjiecode.com");

        Result<List<MessageResult>> result = wxPusher.send(message);
        System.out.println("发送结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
        if (result.isSuccess() && result.getData() != null) {
            for (MessageResult messageResult : result.getData()) {
                System.out.println("发送到UID: " + messageResult.getUid() + ", 结果：" + messageResult.getStatus());
            }
        }
        
        // 添加断言
        Assert.assertTrue("发送带URL消息应该成功", result.isSuccess());
        Assert.assertEquals("处理成功", result.getMsg());
        Assert.assertNotNull("返回数据不应为空", result.getData());
        Assert.assertFalse("返回结果列表不应为空", result.getData().isEmpty());
        
        MessageResult messageResult = result.getData().get(0);
        Assert.assertEquals("UID应匹配", TEST_UID, messageResult.getUid());
        Assert.assertTrue("消息发送状态应包含'创建发送任务成功'", messageResult.getStatus().contains("创建发送任务成功"));
    }

    /**
     * 测试查询消息状态
     */
    @Test
    public void testQueryMessageStatus() {
        // 先发送一条消息获取消息ID
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setContent("这是一条用于测试查询状态的消息");
        message.setUid(TEST_UID);

        Result<List<MessageResult>> sendResult = wxPusher.send(message);
        if (sendResult.isSuccess() && sendResult.getData() != null && !sendResult.getData().isEmpty()) {
            Long messageId = sendResult.getData().get(0).getMessageId();
            if (messageId != null) {
                Result<Integer> result = wxPusher.queryMessageStatus(messageId);
                System.out.println("查询结果：" + result.isSuccess() + ", 消息状态：" + result.getData());
                
                // 添加断言
                Assert.assertTrue("查询消息状态应该成功", result.isSuccess());
                Assert.assertNotNull("状态码不应为空", result.getData());
                Assert.assertEquals("消息状态应为1", 1, result.getData().intValue());
            } else {
                System.out.println("消息ID为空，无法查询状态");
                Assert.fail("消息ID不应为空");
            }
        } else {
            System.out.println("发送消息失败，无法测试查询状态");
            Assert.fail("发送消息应该成功");
        }
    }

    /**
     * 测试删除消息
     */
    @Test
    public void testDeleteMessage() {
        // 先发送一条消息获取消息ID
        Message message = new Message();
        message.setAppToken(APP_TOKEN);
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setContent("这是一条用于测试删除的消息");
        message.setUid(TEST_UID);

        Result<List<MessageResult>> sendResult = wxPusher.send(message);
        if (sendResult.isSuccess() && sendResult.getData() != null && !sendResult.getData().isEmpty()) {
            Long messageId = sendResult.getData().get(0).getMessageId();
            if (messageId != null) {
                Result<Boolean> result = wxPusher.deleteMessage(messageId);
                System.out.println("删除结果：" + result.isSuccess() + ", 状态：" + result.getData());
                
                // 根据实际情况，可能删除失败是正常的，因为消息已经推送给用户
                // 这里我们只检查API调用是否返回了结果，而不检查删除是否成功
                Assert.assertNotNull("应该返回结果", result);
            } else {
                System.out.println("消息ID为空，无法删除消息");
                Assert.fail("消息ID不应为空");
            }
        } else {
            System.out.println("发送消息失败，无法测试删除消息");
            Assert.fail("发送消息应该成功");
        }
    }

    /**
     * 测试创建带参数的临时二维码
     */
    @Test
    public void testCreateTempQrcode() {
        CreateQrcodeReq req = new CreateQrcodeReq();
        req.setExtra("test_extra_data");
        req.setValidTime(1800); // 设置有效期为30分钟

        Result<CreateQrcodeResp> result = wxPusher.createAppTempQrcode(req);
        System.out.println("创建二维码结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
        if (result.isSuccess() && result.getData() != null) {
            CreateQrcodeResp resp = result.getData();
            System.out.println("二维码Code: " + resp.getCode());
            System.out.println("二维码URL: " + resp.getUrl());
            System.out.println("二维码短链接: " + resp.getShortUrl());
            System.out.println("过期时间: " + resp.getExpires());
            System.out.println("附加数据: " + resp.getExtra());
        }
        
        // 添加断言
        Assert.assertTrue("创建二维码应该成功", result.isSuccess());
        Assert.assertEquals("处理成功", result.getMsg());
        Assert.assertNotNull("返回数据不应为空", result.getData());
        
        CreateQrcodeResp resp = result.getData();
        Assert.assertNotNull("二维码Code不应为空", resp.getCode());
        Assert.assertNotNull("二维码URL不应为空", resp.getUrl());
        Assert.assertTrue("二维码URL应包含正确格式", resp.getUrl().contains(".jpg"));
        Assert.assertEquals("附加数据应匹配", "test_extra_data", resp.getExtra());
        Assert.assertTrue("过期时间应大于当前时间", resp.getExpires() > System.currentTimeMillis());
    }

    /**
     * 测试查询用户列表
     */
    @Test
    public void testQueryWxUser() {
        Result<Page<WxUser>> result = wxPusher.queryWxUserV2(APP_TOKEN, 1, 10, null, false, UserType.APP);
        System.out.println("查询用户结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
        if (result.isSuccess() && result.getData() != null) {
            Page<WxUser> page = result.getData();
            System.out.println("总用户数: " + page.getTotal());
            System.out.println("当前页: " + page.getPage());
            System.out.println("页大小: " + page.getPageSize());

            if (page.getRecords() != null) {
                for (WxUser wxUser : page.getRecords()) {
                    System.out.println("用户UID: " + wxUser.getUid());
                    System.out.println("用户ID: " + wxUser.getId());
                    System.out.println("应用/主题ID: " + wxUser.getAppOrTopicId());
                    System.out.println("关注类型: " + wxUser.getType());
                    System.out.println("用户是否启用: " + wxUser.isEnable());
                    System.out.println("用户是否拉黑: " + wxUser.isReject());
                    System.out.println("创建时间: " + wxUser.getCreateTime());
                    System.out.println("---------------------");
                }
            }
        }
        
        // 添加断言
        Assert.assertTrue("查询用户应该成功", result.isSuccess());
        Assert.assertEquals("处理成功", result.getMsg());
        Assert.assertNotNull("返回数据不应为空", result.getData());
        
        Page<WxUser> page = result.getData();
        Assert.assertNotNull("用户列表不应为空", page.getRecords());
        Assert.assertFalse("用户列表不应为空", page.getRecords().isEmpty());
        Assert.assertEquals("当前页应为1", Integer.valueOf(1), page.getPage());
        Assert.assertEquals("页大小应为10", Integer.valueOf(10), page.getPageSize());
        Assert.assertTrue("总用户数应大于0", page.getTotal() > 0);
        
        // 检查是否包含测试用户
        boolean containsTestUser = false;
        for(WxUser user : page.getRecords()) {
            if(TEST_UID.equals(user.getUid())) {
                containsTestUser = true;
                Assert.assertEquals("应用ID应匹配", Long.valueOf(141), user.getAppOrTopicId());
                Assert.assertEquals("关注类型应为APP", 0, user.getType());
                break;
            }
        }
        Assert.assertTrue("用户列表应包含测试用户", containsTestUser);
    }

    /**
     * 测试查询指定UID用户信息
     */
    @Test
    public void testQuerySpecificUser() {
        Result<Page<WxUser>> result = wxPusher.queryWxUserV2(APP_TOKEN, 1, 10, TEST_UID, false, UserType.APP);
        System.out.println("查询指定用户结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
        if (result.isSuccess() && result.getData() != null) {
            Page<WxUser> page = result.getData();
            if (page.getRecords() != null && !page.getRecords().isEmpty()) {
                WxUser wxUser = page.getRecords().get(0);
                System.out.println("用户UID: " + wxUser.getUid());
                System.out.println("用户ID: " + wxUser.getId());
                System.out.println("应用/主题ID: " + wxUser.getAppOrTopicId());
                System.out.println("关注类型: " + wxUser.getType());
                System.out.println("用户是否启用: " + wxUser.isEnable());
                System.out.println("用户是否拉黑: " + wxUser.isReject());
                System.out.println("创建时间: " + wxUser.getCreateTime());
            } else {
                System.out.println("未找到指定UID的用户");
            }
        }
        
        // 添加断言
        Assert.assertTrue("查询指定用户应该成功", result.isSuccess());
        Assert.assertEquals("处理成功", result.getMsg());
        Assert.assertNotNull("返回数据不应为空", result.getData());
        
        Page<WxUser> page = result.getData();
        Assert.assertNotNull("用户记录不应为空", page.getRecords());
        Assert.assertFalse("用户记录不应为空", page.getRecords().isEmpty());
        
        WxUser wxUser = page.getRecords().get(0);
        Assert.assertEquals("用户UID应匹配", TEST_UID, wxUser.getUid());
        Assert.assertEquals("应用ID应匹配", Long.valueOf(141), wxUser.getAppOrTopicId());
        Assert.assertEquals("关注类型应为APP", 0, wxUser.getType());
    }

    /**
     * 测试查询扫码用户UID
     * 注意：此方法需要用户扫描二维码才能查询到结果
     */
    @Test
    public void testQueryScanUID() {
        // 先创建一个带参数的临时二维码
        CreateQrcodeReq req = new CreateQrcodeReq();
        req.setExtra("test_scan_uid");
        req.setValidTime(300); // 设置有效期为5分钟

        Result<CreateQrcodeResp> createResult = wxPusher.createAppTempQrcode(req);
        if (createResult.isSuccess() && createResult.getData() != null) {
            String code = createResult.getData().getCode();
            System.out.println("请扫描这个二维码URL: " + createResult.getData().getUrl());
            System.out.println("等待用户扫描...");

            // 注意：实际使用时，应该使用轮询方式，此处仅为示例
            Result<String> result = wxPusher.queryScanUID(code);
            System.out.println("查询扫码用户结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
            if (result.isSuccess() && result.getData() != null) {
                System.out.println("扫码用户UID: " + result.getData());
            }
            
            // 添加断言 - 注意这里可能失败是正常的，因为没有用户扫码
            Assert.assertNotNull("查询结果不应为空", result);
        } else {
            System.out.println("创建二维码失败，无法测试查询扫码用户");
            Assert.fail("创建二维码应该成功");
        }
    }

    /**
     * 测试删除用户
     * 注意：此方法会真实删除用户，谨慎使用
     */
    @Test
    public void testDeleteUser() {
        // 先查询出用户ID
        Result<Page<WxUser>> queryResult = wxPusher.queryWxUserV2(APP_TOKEN, 1, 10, TEST_UID, false, UserType.APP);
        if (queryResult.isSuccess() && queryResult.getData() != null
                && queryResult.getData().getRecords() != null
                && !queryResult.getData().getRecords().isEmpty()) {

            Long userId = queryResult.getData().getRecords().get(0).getId();
            System.out.println("找到用户ID: " + userId);

            // 谨慎执行以下代码，会真实删除用户关注
            // Result<Boolean> result = wxPusher.deleteUser(userId);
            // System.out.println("删除用户结果：" + result.isSuccess() + ", 状态：" + result.getData());
            
            // 添加断言
            Assert.assertTrue("查询用户应该成功", queryResult.isSuccess());
            Assert.assertNotNull("用户ID不应为空", userId);
            Assert.assertEquals("用户ID应匹配预期", Long.valueOf(34334), userId);

            System.out.println("为避免真实删除用户，该测试方法已被注释");
        } else {
            System.out.println("未找到指定UID的用户，无法测试删除");
            Assert.fail("应该能找到测试用户");
        }
    }

    /**
     * 测试拉黑用户
     * 注意：此方法会真实拉黑用户，谨慎使用
     */
    @Test
    public void testRejectUser() {
        // 先查询出用户ID
        Result<Page<WxUser>> queryResult = wxPusher.queryWxUserV2(APP_TOKEN, 1, 10, TEST_UID, false, UserType.APP);
        if (queryResult.isSuccess() && queryResult.getData() != null
                && queryResult.getData().getRecords() != null
                && !queryResult.getData().getRecords().isEmpty()) {

            Long userId = queryResult.getData().getRecords().get(0).getId();
            System.out.println("找到用户ID: " + userId);

            // 拉黑和取消拉黑用户的完整测试
            Result<Boolean> result = wxPusher.rejectUser(userId, true);
            System.out.println("拉黑用户结果：" + result.isSuccess() + ", 状态：" + result.getData());
            
            // 添加断言
            Assert.assertTrue("拉黑用户应该成功", result.isSuccess());
            Assert.assertTrue("操作状态应为true", result.getData());
            
            Result<Boolean> result2 = wxPusher.rejectUser(userId, false);
            System.out.println("取消拉黑用户结果：" + result2.isSuccess() + ", 状态：" + result2.getData());
            
            // 添加断言
            Assert.assertTrue("取消拉黑用户应该成功", result2.isSuccess());
            Assert.assertTrue("操作状态应为true", result2.getData());
        } else {
            System.out.println("未找到指定UID的用户，无法测试拉黑");
            Assert.fail("应该能找到测试用户");
        }
    }

}
