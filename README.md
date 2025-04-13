<h1> 
升级到3.x 的版本，做了很多break change，但是改动不大，2.x版本在<a href="https://github.com/wxpusher/wxpusher-sdk-java/tree/version_2.x">version_2.x</a>分支
</h1>


# WxPusher
微信消息实时推送服务[WxPusher]，可以通过API实时给个人微信推送消息.[http://wxpusher.zjiecode.com/admin](http://wxpusher.zjiecode.com/admin)

# 功能介绍说明

请直接访问官方说明文档[http://wxpusher.zjiecode.com/docs](http://wxpusher.zjiecode.com/docs)

## 目录说明
- client-sdk 目录：JAVA SDK的源代码
- demo 目录：[http://wxpusher.zjiecode.com/demo](http://wxpusher.zjiecode.com/demo)的源代码

# 其他语言SDK
- [Go-SDK](https://github.com/wxpusher/wxpusher-sdk-go)
- [Python-SDK](https://github.com/wxpusher/wxpusher-sdk-python)

如果不存在你需要语言的sdk，请你按照[参考文档](http://wxpusher.zjiecode.com/docs)直接使用http调用，另外，欢迎你贡献更多语言的SDK。
# Java版本SDK

[ ![version](https://img.shields.io/static/v1.svg?label=version&message=3.0.2&color=brightgreen) ](https://bintray.com/zjiecode/maven/wxpusher-java-sdk)
## 目录模块说明
- demo

  官网演示demo源码，开发的时候，可以做参考，demo演示网站地址: [http://wxpusher.zjiecode.com/demo](http://wxpusher.zjiecode.com/demo)
- sdk 

  Java版本SDK源码，开发的时候可以直接使用。
## 添加依赖
### gradle中使用

你需要添加Jcenter库，在“build.grade”中配置：
```groovy
dependencies {
    ......
    implementation 'com.smjcco.wxpusher:client-sdk:3.0.0'//使用上面的版本号
    ......
}
```

###  在maven中使用
在*pom.xml*文件中添加：
```xml
<dependency>
  <groupId>com.smjcco.wxpusher</groupId>
  <artifactId>client-sdk</artifactId>
  <version>3.0.2</version>
</dependency>
```
## 系统回调
WxPusher会提供一些用户事件，比如用户关注、用户上行消息等，我们会通过回调的方式把消息推送给你。
具体方式请阅读接入文档：https://wxpusher.zjiecode.com/docs/#/?id=callback
相关的数据模型，已经定义在：com.smjcco.wxpusher.sdk.bean.callback 包下面，也可以可以参考demo模块的实现。

## 功能说明
具体每个功能的说明，请参考<a href="https://wxpusher.zjiecode.com/docs/#/?id=http%e6%8e%a5%e5%8f%a3%e8%af%b4%e6%98%8e">API说明文档</a>，这里不再赘述。

不想看文档，可以直接参考[单元测试的用法(client-sdk/src/test/java/com/smjcco/wxpusher/client/sdk/ClientTest.java)](https://github.com/wxpusher/wxpusher-sdk-java/blob/master/client-sdk/src/test/java/com/smjcco/wxpusher/client/sdk/ClientTest.java)



### 初始化
如果是你只需要一个实例（一个appToken），可以直接使用default，在使用之前进行初始化；
```java
WxPusher.initDefaultWxPusher("你自己的应用appToken");
```
在需要使用WxPusher的地方，世界访问default即可，比如下面这样：
```java
WxPusher.getDefaultWxPusher().send(xxx);
```

如果需要同时使用多个实例（appToken）可以直接实例化，自己进行保存：
```java
new WxPusher("你自己的应用appToken");
```

### 发送消息
说明：调用此接口，即可把消息推送出去。 如果传递了多个uid或者topic，会返回多个结果。
```java
Message message = new Message();
message.setContent("扫描成功，你可以使用demo演示程序发送消息");
message.setContentType(Message.CONTENT_TYPE_TEXT);
message.setUid("UID_XXXX");

Result<List<MessageResult>> result = WxPusher.getDefaultWxPusher().send(message);
System.out.println("发送结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
if (result.isSuccess() && result.getData() != null) {
  for (MessageResult messageResult : result.getData()) {
    System.out.println("发送到UID: " + messageResult.getUid() + ", 结果：" + messageResult.getStatus());
  }
}
```
### 删除消息
说明：消息发送以后，可以调用次接口删除消息，但是请注意，只能删除用户点击详情查看的落地页面，已经推送到用户的消息记录不可以删除。
```java
Result<Boolean> result = WxPusher.getDefaultWxPusher().deleteMessage(messageId);
System.out.println("删除结果：" + result.isSuccess() + ", 状态：" + result.getData());
```

### 创建带参数的app临时二维码
说明：可以用于创建一个二维码，拿到扫码的用户，来和自己系统的用户做关联。

本接口和下面的queryScanUID配合使用，推荐更推荐回调：https://wxpusher.zjiecode.com/docs/#/?id=callback
```java
CreateQrcodeReq req = new CreateQrcodeReq();
req.setExtra("test_extra_data");
req.setValidTime(1800); // 设置有效期为30分钟

Result<CreateQrcodeResp> result = WxPusher.getDefaultWxPusher().createAppTempQrcode(req);
System.out.println("创建二维码结果：" + result.isSuccess() + ", 消息：" + result.getMsg());
if (result.isSuccess() && result.getData() != null) {
  CreateQrcodeResp resp = result.getData();
  System.out.println("二维码Code: " + resp.getCode());
  System.out.println("二维码URL: " + resp.getUrl());
  System.out.println("二维码短链接: " + resp.getShortUrl());
  System.out.println("过期时间: " + resp.getExpires());
  System.out.println("附加数据: " + resp.getExtra());
}
```
### 查询扫码用户UID
```java
String code = "上面 createAppTempQrcode 接口返回的code";
// 注意：实际使用时，应该使用轮询方式，但是轮训时间不可以小于10秒，此处仅为示例
// 更推荐使用回调的方式 https://wxpusher.zjiecode.com/docs/#/?id=callback
Result<String> result = WxPusher.getDefaultWxPusher().queryScanUID(code);
```

### 查询用户
说明：同一个用户关注多个，可能返回多个数据
```java
Result<Page<WxUser>> result = WxPusher.getDefaultWxPusher().queryWxUserV2(1, 10, null, false, UserType.APP);
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
```
### 删除用户
你可以通过本接口，删除用户对应用，主题的关注。

删除以后，用户可以重新关注，如不想让用户再次关注，可以调用拉黑接口，对用户拉黑。
```java
Result<Boolean> result = wxPusher.deleteUser(userId);
System.out.println("删除用户结果：" + result.isSuccess() + ", 状态：" + result.getData());
```

### 拉黑用户
拉黑以后不能再发送消息，用户也不能再次关注，除非你取消对他的拉黑。调用拉黑接口，不用再调用删除接口。
```java
 // 拉黑用户
Result<Boolean> result = WxPusher.getDefaultWxPusher().rejectUser(userId, true);
System.out.println("拉黑用户结果：" + result.isSuccess() + ", 状态：" + result.getData());

//取消拉黑用户
Result<Boolean> result2 = WxPusher.getDefaultWxPusher().rejectUser(userId, false);
System.out.println("取消拉黑用户结果：" + result2.isSuccess() + ", 状态：" + result2.getData());
```

使用就是这么简单，有需要就来试试吧。

