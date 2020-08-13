package com.jinke.project.system.ncInteractivity.controller;


import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


@Controller
public class YXInteractiveControllor {
    private final static Logger log = LoggerFactory.getLogger(NCInteractiveControllor.class);
    @Value("${hkyxurl}")
    public String hkyxurl;

    @Value("${yxupload}")
    public String yxupload;

    @Value("${yxdownload}")
    public String yxdownload;

    public String doUpload(MultipartFile file) {
        String url = hkyxurl + yxupload;
        String s = null;
        s = YXInteractiveControllor.httpPostFile(url, file);
        log.info("[post]" + url + " params:" + file + " result: " + s);
        return s;
    }

    public static String httpPostFile(String url, MultipartFile file) {

        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(
                CoreProtocolPNames.HTTP_CONTENT_CHARSET,
                Charset.forName("UTF-8"));
        HttpPost httppost = new HttpPost(url);
        String fileName = file.getOriginalFilename();
        ContentBody files = null;
        try {
            files = new ByteArrayBody(file.getBytes(), fileName);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", files);
            // 设置请求
            httppost.setEntity(reqEntity);
            // 执行
            HttpResponse response = client.execute(httppost);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] doDownload(String filesPath) {
        String url = hkyxurl + yxdownload;
        byte[] s = null;
        s = YXInteractiveControllor.httpClientDownload(url, filesPath);
        log.info("[post]" + url + " params:" + filesPath + " result: " + s);
        return s;
    }

    public static byte[] httpClientDownload(String url, String filesPath) {
        HttpClient httpclient = new DefaultHttpClient();
        // 请求处理页面
        HttpPost httppost = new HttpPost(url);
        // 创建待处理的文件
        // 对请求的表单域进行填充
        MultipartEntity reqEntity = new MultipartEntity();
        // 设置请求
        httppost.setEntity(reqEntity);
        // 执行
        HttpResponse response = null;
        byte[] fileBytes = null;
        try {
            reqEntity.addPart("url", new StringBody(filesPath));
            response = httpclient.execute(httppost);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                InputStream inputContent = entity.getContent();
                fileBytes = input2byte(inputContent);
                return fileBytes;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * inputStream转换为byte字节数组
     *
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}