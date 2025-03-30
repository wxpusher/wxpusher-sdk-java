package com.smjcco.wxpusher.sdk;

import com.alibaba.fastjson2.TypeReference;
import com.smjcco.wxpusher.sdk.bean.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明：WxPusher的客户端<br/>
 * 一般通过调用 initDefaultWxPusher()，初始化以后，然后就直接用getDefaultWxPusher()即可<br/>
 * 如果有多实例需求，可以用构造方法，多次实例化<br/>
 * 具体接口使用可以参考<a href="https://wxpusher.zjiecode.com/docs/#/">接口说明文档</a> <br/>
 * 作者：zjiecode <br/>
 * 时间：2019-05-03 <br/>
 */
public final class WxPusher {


    /**
     * 用来保存发送消息用的AppToken
     */
    private String appToken;

    private static WxPusher defaultWxPusher;

    public WxPusher(String appToken) {
        this.appToken = appToken;
    }

    /**
     * 初始化WxPusher的默认实力<br/>
     *
     * @param appToken 获取到的AppToken，获取方式<a href="https://wxpusher.zjiecode.com/docs/#/?id=%e8%8e%b7%e5%8f%96apptoken">AppToken</a>
     */
    public static void initDefaultWxPusher(String appToken) {
        defaultWxPusher = new WxPusher(appToken);
    }

    public WxPusher getDefaultWxPusher() {
        return defaultWxPusher;
    }

    /**
     * 发送消息
     */
    public Result<List<MessageResult>> send(Message message) {
        Result<List<MessageResult>> result = verify(message);
        if (result != null) {
            return result;
        }
        Type resultType = new TypeReference<List<MessageResult>>() {
        }.getType();
        return HttpUtils.post(message, "/api/send/message", resultType);
    }

    /**
     * 查询消息发送状态
     *
     * @deprecated 此接口返回数据可能不准确，只能确保wxpusher已经将消息推送到微信服务器了，用户可能拒绝接收
     */
    public Result<Integer> queryMessageStatus(Long messageId) {
        if (messageId == null || messageId <= 0) {
            return new Result<>(ResultCode.BIZ_FAIL, "messageId为空");
        }
        return HttpUtils.get(null, String.format("/api/send/query/%s", messageId), Integer.class);
    }


    /**
     * 删除消息
     * 说明：消息发送以后，可以调用次接口删除消息，但是请注意，只能删除用户点击详情查看的落地页面，已经推送到用户的消息记录不可以删除。
     *
     * @param messageId 发送接口返回的消息内容id，调用一次接口生成一个，如果是发送给多个用户，多个用户共享一个messageContentId，通过messageContentId可以删除内容，删除后本次发送的所有用户都无法再查看本条消息
     * @return 是否删除成功
     */
    public Result<Boolean> deleteMessage(Long messageId) {
        if (messageId == null || messageId <= 0) {
            return new Result<>(ResultCode.BIZ_FAIL, "messageId错误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("messageContentId", messageId);
        params.put("appToken", appToken);
        return HttpUtils.delete(params, "/api/send/message", Boolean.class);
    }

    /**
     * 创建带参数的app临时二维码
     */
    public Result<CreateQrcodeResp> createAppTempQrcode(CreateQrcodeReq createQrcodeReq) {
        return HttpUtils.post(createQrcodeReq, "/api/fun/create/qrcode", CreateQrcodeResp.class);
    }

    /**
     * 查询用户,同一个用户关注多个，可能返回多个数据
     * <p>
     * <a href="https://wxpusher.zjiecode.com/docs/#/?id=user-list">查询用户信息</a>
     * </p>
     *
     * @param appToken 应用密钥标志
     * @param page     请求数据的页码
     * @param pageSize 分页大小，不能超过100
     * @param uid      用户的uid，可选，如果不传就是查询所有用户，传uid就是查某个用户的信息。
     * @param isBlock  查询拉黑用户，可选，不传查询所有用户，true查询拉黑用户，false查询没有拉黑的用户
     * @param type     关注的类型，可选，不传查询所有用户，0是应用，1是主题
     */
    public Result<Page<WxUser>> queryWxUserV2(String appToken, Integer page, Integer pageSize,
                                              String uid, boolean isBlock, UserType type) {
        if (appToken == null || appToken.isEmpty()) {
            return new Result<>(ResultCode.BIZ_FAIL, "appToken不能为空");
        }
        if (page == null || page <= 0) {
            return new Result<>(ResultCode.BIZ_FAIL, "page不合法");
        }
        if (pageSize == null || pageSize <= 0 || pageSize > 100) {
            return new Result<>(ResultCode.BIZ_FAIL, "pageSize不合法");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("appToken", appToken);
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("isBlock", isBlock);
        params.put("type", type.getType());
        if (uid != null && !uid.isEmpty()) {
            params.put("uid", uid);
        }

        Type resultType = new TypeReference<List<MessageResult>>() {
        }.getType();
        return HttpUtils.get(params, "/api/fun/wxuser/v2", resultType);
    }

    /**
     * 查询扫码用户UID
     * 用户扫描参数二维码后，设置了回调地址，我们会通过回调地址把用户的UID推送给你的服务，具体见回调说明，推荐使用这种回调的方式。
     * 但是部分用户场景简单，或者没有后端服务，比如客户端软件，使用很不方便，因此我们增加了这个查询接口，通过上面的创建参数二维码接口创建一个二维码，你会拿到一个二维码的code，用此code配合这个接口，可以查询到最后一次扫描参数二维码用户的UID。
     * 【轮训时间间隔不能小于10秒！！禁止死循环轮训，用户退出后，必须关闭轮训，否则封号。】
     *
     * @param code 创建参数二维码接口返回的code参数。
     * @return 扫码用户的UID
     */
    public Result<String> queryScanUID(String code) {
        if (appToken == null || appToken.isEmpty()) {
            return new Result<>(ResultCode.BIZ_FAIL, "appToken不能为空");
        }
        if (code == null || code.isEmpty()) {
            return new Result<>(ResultCode.BIZ_FAIL, "code不能为空");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("appToken", appToken);
        params.put("code", code);
        return HttpUtils.get(params, "/api/fun/scan-qrcode-uid", String.class);
    }

    /**
     * 删除用户<br/>
     * 你可以通过本接口，删除用户对应用，主题的关注。<br/>
     * 说明：你可以删除用户对应用、主题的关注，删除以后，用户可以重新关注，如不想让用户再次关注，可以调用拉黑接口，对用户拉黑。<br/>
     *
     * @param id 用户id，通过用户查询接口可以获取
     * @return 操作是否成功
     */
    public Result<Boolean> deleteUser(Long id) {
        if (id == null || id <= 0) {
            return new Result<>(ResultCode.BIZ_FAIL, "id错误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("appToken", appToken);
        Result<String> respResult = HttpUtils.delete(params, "/api/fun/remove", String.class);
        //兼容一下返回数据
        Result<Boolean> result = new Result<>(respResult.getCode(), respResult.getMsg());
        result.setData(respResult.isSuccess());
        return result;
    }

    /**
     * 你可以通过本接口，可以拉黑用户
     * 说明：拉黑以后不能再发送消息，用户也不能再次关注，除非你取消对他的拉黑。调用拉黑接口，不用再调用删除接口。
     *
     * @param id     通过用户查询接口可以获取
     * @param reject 是否拉黑，true表示拉黑，false表示取消拉黑
     * @return
     */
    public Result<Boolean> rejectUser(Long id, Boolean reject) {
        if (id == null || id <= 0) {
            return new Result<>(ResultCode.BIZ_FAIL, "id错误");
        }
        if (reject == null) {
            return new Result<>(ResultCode.BIZ_FAIL, "reject错误");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("appToken", appToken);
        params.put("reject", reject);
        Result<String> respResult = HttpUtils.put(params, "/api/fun/reject", String.class);
        //兼容一下返回数据
        Result<Boolean> result = new Result<>(respResult.getCode(), respResult.getMsg());
        result.setData(respResult.isSuccess());
        return result;
    }

    /**
     * 验证消息合法性，客户端验证比较宽松，主要在服务端进行校验
     */
    private static <T> Result<T> verify(Message message) {
        if (message == null) {
            return new Result<>(ResultCode.BIZ_FAIL, "消息不能为空");
        }
        if (message.getAppToken() == null || message.getAppToken().length() <= 0) {
            return new Result<>(ResultCode.BIZ_FAIL, "appToken不能为空");
        }
        if (message.getContent() == null || message.getContent().length() <= 0) {
            return new Result<>(ResultCode.BIZ_FAIL, "content内容不能为空");
        }
        return null;
    }

}
