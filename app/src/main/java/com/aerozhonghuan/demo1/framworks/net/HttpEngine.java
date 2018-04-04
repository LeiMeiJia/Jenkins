package com.aerozhonghuan.demo1.framworks.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

// 网络层
public class HttpEngine {

    private final static String TAG = "HttpEngine";
    private final static String ENCODE_TYPE = "UTF-8";
    private final static int TIMEOUT_IN_MILLIONS = 15 * 1000;

    private static HttpEngine instance = null;

    private HttpEngine() {
    }

    public static HttpEngine getInstance() {
        if (instance == null) {
            instance = new HttpEngine();
        }
        return instance;
    }

    public <T> T getHandle(String urlStr, Type typeOfT) throws Throwable {
        Log.d(TAG, "getHandle(): called with: urlStr = [" + urlStr + "], typeOfT = [" + typeOfT + "]");
        String result = doGet(urlStr);
        Log.d(TAG, "getHandle: result:" + result);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        T t = gson.fromJson(result, typeOfT);
        return t;
    }

    public <T> T postHandle(String urlStr, Map<String, String> paramsMap, Type typeOfT) throws Throwable {
        Log.d(TAG, "postHandle() called with: urlStr = [" + urlStr + "], paramsMap = [" + paramsMap + "], typeOfT = [" + typeOfT + "]");
        String result = doPost(urlStr, joinParams(paramsMap));
        Log.d(TAG, "postHandle: result:" + result);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        T t = gson.fromJson(result, typeOfT);
        return t;
    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public String doGet(String urlStr) throws Exception {
        Log.d(TAG, "doGet() called with: urlStr = [" + urlStr + "]");
        HttpURLConnection connection = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL realUrl = new URL(urlStr);
            connection = (HttpURLConnection) realUrl.openConnection();
            connection.setReadTimeout(TIMEOUT_IN_MILLIONS);
            connection.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            connection.setRequestMethod("GET");
            // connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Response-Type", "json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "keep-alive");
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];
                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("Exception");
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            connection.disconnect();
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public String doPost(String url, String param) {
        Log.d(TAG, "doPost() called with: url = [" + url + "], param = [" + param + "]");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            // connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Response-Type", "json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(TIMEOUT_IN_MILLIONS);
            connection.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(connection.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 获得服务器响应的结果和状态码
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                return result;
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("");
        } finally {
            // 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // 拼接参数列表
    public String joinParams(Map<String, String> paramsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : paramsMap.keySet()) {
            stringBuilder.append(key);
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(paramsMap.get(key), ENCODE_TYPE));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringBuilder.append("&");
        }
        String paramStr = stringBuilder.substring(0, stringBuilder.length() - 1);
        Log.d(TAG, "joinParams() called with: paramsMap = [" + paramsMap + "]");
        return paramStr;
    }
}