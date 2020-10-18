<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="keywords" content="微信推送,推送服务,WxPusher,消息提醒,免费推送">
    <meta name="description" content="微信消息实时推送服务[WxPusher]，可以通过API实时给个人微信推送消息。">
    <title>WxPusher-消息推送服务-演示程序</title>
    <style>
        .main {
            margin: 0 auto;
            width: 90%;
            display: flex;
            flex-direction: column;
            align-items: center;
            min-width: 760px;
        }

        .target {
            display: flex;
            flex-direction: row;
            justify-content: space-around;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .target a {
            font-size: 15px;
            color: #42b983;
            margin-left: 10px;
            margin-right: 10px;
        }

        .qrcode {
            width: 150px;
        }

        .link {
            color: #42b983;
            margin-left: 10px;
            cursor: pointer;
        }

        .line {
            display: flex;
            flex-direction: row;
            margin-bottom: 10px;
            padding-top: 10px;
            align-items: center;
        }

        .send {
            display: flex;
            flex-direction: column;
            margin-left: 25px;
        }

        .input {
            padding: 5px;
            height: 100px;
            margin-top: 10px;
            margin-bottom: 10px;
            outline: none;
            font-size: 15px;
            border-radius: 5px;
            border-color: #cccccc;
            border-style: solid;
        }

        .input:focus {
            border-style: solid;
            border-color: #42b983;
            border-radius: 5px;
            animation: glow 1000ms ease-out infinite alternate;
        }

        @keyframes glow {
            0% {
                border-color: #cccccc;
                box-shadow: rgba(66, 185, 131, .2);
            }
            100% {
                border-color: #42b983;
                box-shadow: 0 0 8px rgba(66, 185, 131, 0.8);
            }
        }
    </style>
</head>
<body>
<div class="main">
    <h2>WxPusher 演示程序</h2>
    <span>使用WxPusher免费推送消息到个人微信上，更快更便捷。<br/>
        本程序已经开源，你可以<a target="_blank" href="https://github.com/wxpusher/wxpusher-sdk-java/">点击这里</a>参考源码。</span>
    <div class="target">
        <a target="_blank" href="/admin">管理后台</a>
        <a target="_blank" href="/docs">API接口说明文档</a>
        <a target="_blank" href="https://github.com/wxpusher/wxpusher-sdk-java/">本演示程序源码</a>
        <a target="_blank" href="https://github.com/zjiecode/wxpusher-client">接入SDK</a>
    </div>

    <div style="display: flex;flex-direction: row;align-items: center;">
        <div style="display: flex;flex-direction: column;width:64%;">
            <h2 style="align-self: start;">发送消息(下行消息)</h2>
            <span>说明：调用API就可以发送消息到个人微信上。</span>
            <img class="qrcode" id="qrcode" src="${qrcodeUrl}" style="align-self: center;"/>
            <div class="line" id="user-info" style="margin-left: 25px;display: none;">
                <img id="head-img" src="" style="width: 90px;height: 100%;margin-right: 10px;">
                <div>
                    <p>用户昵称：<span id="nick-name"></span></p>
                    <p>UID：<span id="uid"></span></p>
                </div>
            </div>
            <div id="step-1">1、请使用微信扫描上方二维码获取你的UID；</div>
            <div class="line" id="user-info" style="margin-left: 25px;display: none;">
                <img id="head-img" src="" style="width: 90px;height: 100%;margin-right: 10px;">
                <div>
                    <p>用户昵称：<span id="nick-name"></span></p>
                    <p>UID：<span id="uid"></span></p>
                </div>
            </div>
            <div class="line">2、发送消息：</div>
            <div class="send">
                <div>
                    2.1、快捷发送<span style="color: #cccccc;font-size: 12px">(点击即可发送测试消息)</span>
                </div>
                <div class="line">
                    <span class="link" id="send-text">发送文本消息</span><span class="link"
                                                                         id="send-html">发送HTML消息</span><span
                            class="link"
                            id="send-markdown">发送Markdown消息</span>
                </div>
                2.2、发送自定义消息
                <textarea class="input" placeholder="请输入你要发送的内容" id="input"></textarea>
                <span class="link" style="align-self: flex-end;" id="send-custom">发送消息</span>
            </div>
        </div>
        <div style="background: #eeeeee;width: 1px;margin-top: 100px;margin-bottom: 100px;margin-left: 20px;margin-right: 20px;align-self: stretch;"></div>
        <div style="width:35%;align-self: start;">
            <h2>接收消息(上行消息)</h2>
            <div>说明：个人微信发送消息，服务器可以收到回调。</div>
            <div class="line">1、请使用微信关注公众号 WxPusher (公众号名字：新消息服务）或者扫左侧二维码也可以关注</div>
            <div>2、在公众号里面发送「#11 自定义参数」，收到的消息将在下方显示：</div>
            <div id="msg"></div>
        </div>
    </div>
</div>
</body>
</html>
<script src="//static.zjiecode.com/wxpusher/demo/fly-0.6.14.min.js"></script>
<script>
    var fly = new Fly;
    var hasUser = false;
    var uid;
    var interval = setInterval(function () {
        fly.get('/demo/getuid/${qrcodeId}')
            .then(function (response) {
                console.log(response.data.msg);
                var user = response.data.data.scan;
                if (!hasUser && user) {
                    hasUser = true;
                    var tips = document.getElementById("step-1");
                    tips.innerText = "1、已经成功获取到扫码用户，信息如下："
                    tips.setAttribute("style", "color:#42b983");
                    document.getElementById("user-info").setAttribute("style", "margin-left: 25px;");
                    document.getElementById("nick-name").innerText = user.userName;
                    document.getElementById("uid").innerText = user.uid;
                    uid = user.uid;
                    document.getElementById("head-img").setAttribute("src", user.userHeadImg);
                    document.getElementById("qrcode").setAttribute("style", "display:none;");
                }
                var upCommand = response.data.data.upCommand;
                if (upCommand) {
                    var text = "";
                    for (var i = 0; i < upCommand.length; i++) {
                        var msg = upCommand[i];
                        text += createMessage(msg);
                    }
                    var msgDom = document.getElementById("msg");
                    msgDom.innerHTML = text;
                }
            }).catch(function (error) {
            console.log(error);
        })
    }, 2000);

    function createMessage(msg){
        return "<div style=\"display: flex;flex-direction: row;align-items: center;margin-top: 5px;\">\n" +
            "                    <img src=\""+msg.userHeadImg+"\" style=\"width: 30px;height: 100%;\"/>\n" +
            "                    <span style=\"margin-left: 10px;margin-right: 10px;white-space: nowrap;\">"+msg.userName+"</span>:" +
            "<span style=\"margin-left: 10px;margin-right: 10px;\">"+msg.content+"</span>\n" +
            "                </div>";
    }
    function send(type) {
        if (!uid) {
            alert("请先扫描二维码,获取你的UID");
            return;
        }
        fly.get('/demo/send/' + type + '/' + uid).then(function (response) {
            if (response.data.data) {
                //只有一个uid，直接取第0个的状态
                alert(response.data.data[0].status);
                return;
            } else {
                alert(response.data.msg);
            }
        })
            .catch(function (error) {
                alert(error);
                console.log(error);
            });
    }

    document.getElementById("send-text").addEventListener("click", function (ev) {
        send("text");
    })
    document.getElementById("send-html").addEventListener("click", function (ev) {
        send("html");
    })
    document.getElementById("send-markdown").addEventListener("click", function (ev) {
        send("markdown");
    })
    document.getElementById("send-custom").addEventListener("click", function (ev) {
        var text = document.getElementById("input").value;
        if (!text) {
            alert("发送内容不能为空");
            return;
        }
        fly.get('/demo/send/custom/' + uid, {content: text}).then(function (response) {
            if (response.data.data) {
                //只有一个uid，直接取第0个的状态
                alert(response.data.data[0].status);
                return;
            } else {
                alert(response.data.msg);
            }
        })
            .catch(function (error) {
                alert(error);
                console.log(error);
            });
    })

</script>
