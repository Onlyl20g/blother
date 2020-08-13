package com.jinke.project.tool.tools;

import okhttp3.*;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RemoteUtil {
    private static Logger logger = LoggerFactory.getLogger(RemoteUtil.class.getName());
    private static final int CONNECT_TIMEOUT_MS = 60000; // in milliseconds
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static String postDataWithBody(String url, String paramJson) {
        return postDataWithBody(url, paramJson, null, null);
    }

    public static String postDataWithBody(String url, HashMap<String, String> headers, String paramJson) {
        return postDataWithBody(url, paramJson, headers, null);
    }

    public static String postDataWithBody(String url, String paramJson, HashMap<String, String> headers, Map<String, String> params) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            return "RemoteUtil:" + url + " --> is invalid";
        }

        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                urlBuilder.addQueryParameter(key, value);
            }
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                headerBuilder.add(key, value);
            }
        }

        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, paramJson);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .headers(headerBuilder.build())
                .post(requestBody)
                .build();

        //发送请求获取响应
        try {
            return callRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String postData(String url, HashMap<String, String> headers, Map<String, String> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                formBodyBuilder.add(key, value);
            }
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                headerBuilder.add(key, value);
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .headers(headerBuilder.build())
                .post(formBodyBuilder.build())
                .build();

        //发送请求获取响应
        try {
            return callRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getData(String url, HashMap<String, String> headers, Map<String, String> params) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            return "RemoteUtil:" + url + " --> is invalid";
        }

        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                urlBuilder.addQueryParameter(key, value);
            }
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                headerBuilder.add(key, value);
            }
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .headers(headerBuilder.build())
                .get()
                .build();
        try {
            return callRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String callRequest(Request request) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS) // connect timeout
                .readTimeout(CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS) //socket timeout
                .build();
        //发送请求获取响应
        Response response = client.newCall(request).execute();
        String requestBodyStr = "";
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(StandardCharsets.UTF_8);
            }
            if (charset != null) {
                requestBodyStr = buffer.readString(charset);
            }
        }
        logger.info("[" + request.method() + "]" + request.url().toString() + " " + requestBodyStr);
        //判断请求是否成功
        if (response.isSuccessful()) {
            //打印服务端返回结果
            String result = null;
            if (response.body() != null) {
                result = response.body().string();
                response.body().close();
                logger.info(request.method() + " response: " + result);
            }
            return result;
        } else {
            logger.info("[" + request.method() + "]" + request.url().toString() + ":" + response.code() + " message: " + response.message());
        }
        response.close();
        return null;
    }
}
