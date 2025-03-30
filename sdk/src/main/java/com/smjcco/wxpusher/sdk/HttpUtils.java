package com.smjcco.wxpusher.sdk;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.smjcco.wxpusher.sdk.bean.Result;
import com.smjcco.wxpusher.sdk.bean.ResultCode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * 说明：网络请求工具
 * 作者：zjiecode
 * 时间：2019-09-05
 */
public final class HttpUtils {
    private static final String BASE_URL = "https://wxpusher.zjiecode.com";
    private static final String CHARSET_NAME = "UTF-8";

    private HttpUtils() {
    }

    public static <T> Result<T> request(Object body, Map<String, Object> queryMap, String path, String method, Type returnType) {
        try {
            String url = buildUrl(path);
            if (queryMap != null) {
                String query = parseMap2Query(queryMap);
                if (!query.isEmpty()) {
                    url = url + "?" + query;
                }
            }
            URL cUrl = new URL(url);
            HttpURLConnection urlConnection = getHttpURLConnection(cUrl, method);
            if (body != null) {
                OutputStream outputStream = urlConnection.getOutputStream();
                String dataStr = JSON.toJSONString(body);
                outputStream.write(dataStr.getBytes(Charset.forName(CHARSET_NAME)));
                outputStream.flush();
            }
            return dealConnect(urlConnection, returnType);
        } catch (MalformedURLException e) {
            return new Result<>(ResultCode.NETWORK_ERROR, e.getMessage());
        } catch (IOException e) {
            return new Result<>(ResultCode.NETWORK_ERROR, e.toString());
        } catch (Throwable e) {
            return new Result<>(ResultCode.UNKNOWN_ERROR, e.toString());
        }
    }

    public static <T> Result<T> post(Object body, String path, Type returnType) {
        return request(body, null, path, "POST", returnType);
    }

    public static <T> Result<T> get(Map<String, Object> queryMap, String path, Type returnType) {
        return request(null, queryMap, path, "GET", returnType);
    }


    public static <T> Result<T> delete(Map<String, Object> queryData, String path, Type returnType) {
        return request(null, queryData, path, "DELETE", returnType);
    }

    public static <T> Result<T> put(Map<String, Object> queryData, String path, Type returnType) {
        return request(null, queryData, path, "PUT", returnType);
    }

    private static HttpURLConnection getHttpURLConnection(URL cUrl, String method) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) cUrl.openConnection();
        urlConnection.setConnectTimeout(60000);
        urlConnection.setReadTimeout(60000);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestMethod(method);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("Charset", CHARSET_NAME);
        urlConnection.setDoOutput(true);
        urlConnection.connect();
        return urlConnection;
    }

    /**
     * 把map转成query查询字符串
     */
    private static String parseMap2Query(Map<String, Object> data) {
        if (data == null || data.size() <= 0) {
            return "";
        }
        Set<Map.Entry<String, Object>> entries = data.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : entries) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return stringBuilder.toString();
    }

    /**
     *
     */
    private static String buildUrl(String path) {
        String url = BASE_URL;
        if (path != null && !path.isEmpty()) {
            if (path.startsWith("/")) {
                url = BASE_URL + path;
            } else {
                url = BASE_URL + "/" + path;
            }
        }
        return url;
    }

    /**
     * 处理连接以后的状态信息
     *
     * @param urlConnection 打开的连接
     * @param type          返回的结果数据类型
     * @return 返回发送结果
     */
    private static <T> Result<T> dealConnect(HttpURLConnection urlConnection, Type type) throws IOException {
        try {
            int responseCode = urlConnection.getResponseCode();
            if (responseCode != 200) {
                return new Result<>(urlConnection.getResponseCode(), "http请求错误:" + responseCode);
            }
            InputStream inputStream = urlConnection.getInputStream();
            String res = inputStream2String(inputStream);
            if (res == null || res.isEmpty()) {
                return new Result<>(ResultCode.INTERNAL_SERVER_ERROR, "服务器返回异常");
            }
            // 构造 Result<T> 的完整类型信息
            Type resultType = new TypeReference<Result<T>>(type) {
            }.getType();
            // 反序列化
            Result<T> result = JSONObject.parseObject(res, resultType);
            if (result == null) {
                return new Result<>(ResultCode.DATA_ERROR, "服务器返回数据解析异常");
            }
            return result;
        } catch (MalformedURLException e) {
            return new Result<>(ResultCode.NETWORK_ERROR, e.getMessage());
        } catch (IOException e) {
            return new Result<>(ResultCode.NETWORK_ERROR, e.getMessage());
        } catch (Throwable e) {
            return new Result<>(ResultCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    /**
     * 从输入流中读取内容到字符串
     *
     * @param inputStream 输入路
     * @return 返回字符串
     */
    private static String inputStream2String(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] bytes = new byte[4096];
        try {
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            return outputStream.toString(CHARSET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
