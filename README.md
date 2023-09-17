# WxPusher
微信消息实时推送服务[WxPusher]，可以通过API实时给个人微信推送消息.[http://wxpusher.zjiecode.com/admin](http://wxpusher.zjiecode.com/admin)

# 功能介绍说明

请直接访问官方说明文档[http://wxpusher.zjiecode.com/docs](http://wxpusher.zjiecode.com/docs)

## 目录说明
- sdk 目录：JAVA SDK的源代码
- demo 目录：[http://wxpusher.zjiecode.com/demo](http://wxpusher.zjiecode.com/demo)的源代码

# 其他语言SDK
- [Go-SDK](https://github.com/wxpusher/wxpusher-sdk-go)
- [Python-SDK](https://github.com/wxpusher/wxpusher-sdk-python)

如果不存在你需要语言的sdk，请你按照[参考文档](http://wxpusher.zjiecode.com/docs)直接使用http调用，另外，欢迎你贡献更多语言的SDK。
# Java版本SDK

[ ![version](https://img.shields.io/static/v1.svg?label=version&message=2.1.4&color=brightgreen) ](https://bintray.com/zjiecode/maven/wxpusher-java-sdk)
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
    compile 'com.zjiecode:wxpusher-java-sdk:2.1.4'//使用上面的版本号
    ......
}
```

###  在maven中使用
在*pom.xml*文件中添加：
```xml
<dependency>
  <groupId>com.zjiecode</groupId>
  <artifactId>wxpusher-java-sdk</artifactId>
  <version>2.1.4</version>
  <type>pom</type>
</dependency>
```
## 功能说明
### 发送消息
最后可以在你需要的地方，直接调用方法，即可实时推送消息到微信上了，比如下面这样：
```java
Message message = new Message();
message.setAppToken("AT_xxxxx");
message.setContentType(Message.CONTENT_TYPE_TEXT);
message.setContent("不加限制的自由是很可怕的，因为很容易让任何人滑向深渊。");
message.setUid("UID_xxxxxx");
message.setUrl("http://wxpuser.zjiecode.com");//可选参数
Result<List<MessageResult>> result = WxPusher.send(message);
```
### 创建带参数的二维码
创建一个带参数的二维码，用户扫码的时候，回调里面会携带二维码的参数.
```java
CreateQrcodeReq createQrcodeReq = new CreateQrcodeReq();
createQrcodeReq.setAppToken("xxxx"); //必填，应用的appTOken
createQrcodeReq.setExtra("parameter");//必填，携带的参数
createQrcodeReq.setValidTime(3600);//可选，二维码有效时间，默认1800 s，最大30天，单位是s
Result<CreateQrcodeResp> tempQrcode = WxPusher.createAppTempQrcode(createQrcodeReq);
if (tempQrcode.isSuccess()) {
    //创建成功
}
```


### 查询消息发送状态
发送消息是异步的，你可以通过这个api查询消息发送状态
```java
Result result = WxPusher.queryMessageStatus(messageId);
```

### 查询关注APP的微信用户列表
```java
//分页查询全部用户
Result<Page<WxUser>> wxUserList = WxPusher.queryWxUserV2("AT_xxx", 1, 50, null, false, 0);
wxUsers.getData().getRecords().forEach(d-> System.out.println(d.getUid()));
//根据查询指定UID用户
Result<Page<WxUser>> users = WxPusher.queryWxUserV2("AT_xxx", 1, 50, "UID_", false, 0);
System.out.println(JSONObject.toJSONString(users));
```

使用就是这么简单，有需要就来试试吧。
