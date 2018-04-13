package com.aerozhonghuan.demo.net.core;

import android.text.TextUtils;
import android.util.Log;

import com.aerozhonghuan.demo.net.callback.MessageStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 网络核心层
 */
public class HttpEngine {

    private final static String TAG = "HttpEngine";
    private final static String ENCODE_TYPE = "utf-8";
    private final static int TIMEOUT_IN_MILLIONS = 15 * 1000;
    private String requestMethod = "GET";
    private String contentType = "application/x-www-form-urlencoded";

    private HttpEngine(Builder builder) {
        if (!TextUtils.isEmpty(builder.requestMethod)) {
            requestMethod = builder.requestMethod;
        }
        if (!TextUtils.isEmpty(builder.contentType)) {
            contentType = builder.contentType;
        }
    }

    /**
     * GET 请求
     *
     * @param urlStr 请求路径，请求参数赋值在url上
     * @return 返回的数据
     */
    public String doGet(String urlStr, Map<String, Object> hashMap) {
        Log.d(TAG, "doGet : urlStr = [" + urlStr + "]");
        // 将请求参数进行编码
        String params = addParameter(hashMap);
        if (!TextUtils.isEmpty(params)) {
            urlStr = urlStr + "?" + params;
        }
        Log.d(TAG, "doGet : newUrl = [" + urlStr + "]");
        return sendHttpRequest(urlStr, "");
    }

    /**
     * POST 请求
     * 提交表单数据需要编码
     * 提交json数据不需要编码
     *
     * @param urlStr 请求路径
     * @return 返回的数据
     */
    public String doPost(String urlStr, Map<String, Object> hashMap) {
        Log.d(TAG, "doPost : urlStr = [" + urlStr + "]");
        String params;
        // 装换成json格式
        if (contentType.equals("application/json")) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            params = gson.toJson(hashMap);
        } else {
            params = addParameter(hashMap); // 进行编码
        }
        Log.d(TAG, "addParameter = [" + params + "]");
        return sendHttpRequest(urlStr, params);
    }

    private String sendHttpRequest(String urlStr, String params) {
        HttpURLConnection connection = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        PrintWriter out = null;
        int responseCode = -1;
        String result = "网络异常";
        try {
            URL realUrl = new URL(urlStr);
            connection = (HttpURLConnection) realUrl.openConnection();
            // 设置连接主机超时
            connection.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            // 设置从主机读取数据超时
            connection.setReadTimeout(TIMEOUT_IN_MILLIONS);
            // 设置请求方式
            connection.setRequestMethod(requestMethod);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Charset", ENCODE_TYPE);
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty("Response-Type", "json");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Content-Type", "application/json");
            // 设置POST方式
            if (requestMethod.equals("POST")) {
                // Post请求不能使用缓存
                connection.setUseCaches(false);
                // 发送POST请求必须设置如下两行
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 设置参数
                if (!TextUtils.isEmpty(params) && !"".equals(params.trim())) {
                    // 获取URLConnection对象对应的输出流
                    out = new PrintWriter(connection.getOutputStream());
                    // 发送请求参数
                    out.print(params);
                    // flush 输出流的缓冲
                    out.flush();
                }
            }
            // 发送请求设置连接，也可以不写
            connection.connect();
            // 获得服务器响应的结果和状态码
            responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                result = baos.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (baos != null)
                    baos.close();
                if (connection != null)
                    connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createMsg(responseCode, result);
    }


    // 拼接参数列表
    private String addParameter(Map<String, Object> paramsMap) {
        String paramStr = "";
        if (paramsMap != null && paramsMap.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String key : paramsMap.keySet()) {
                stringBuilder.append(key);
                stringBuilder.append("=");
                try {

                    String value = String.valueOf(paramsMap.get(key));
                    stringBuilder.append(URLEncoder.encode(value, ENCODE_TYPE));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "";
                }
                stringBuilder.append("&");
            }
            paramStr = stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return paramStr;
    }

    private String createMsg(int code, String msg) {
        MessageStatus status = new MessageStatus(code, msg);
        Gson gson = new Gson();
        return gson.toJson(status);
    }

    // Builder模式
    static class Builder {
        private String requestMethod = "";
        private String contentType = "";

        public Builder setRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        HttpEngine builder() {
            return new HttpEngine(this);
        }
    }
}