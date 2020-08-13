package com.jinke.project.tool.fastdfs;

import com.jinke.project.tool.tools.ConfigEntity;
import com.jinke.project.tool.tools.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VatInvoiceParse {
    public static String parseVatInvoice(MultipartFile file) {
        //API产品路径
        String host = "http://vatinvoice.market.alicloudapi.com";
        String path = "/ai_vat_invoice";
        String method = "POST";
        //阿里云APPCODE
        String appcode = ConfigEntity.getAliappCode();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();

        //内容数据类型，如：0，则表示BASE64编码；1，则表示图像文件URL链接
        //启用BASE64编码方式进行识别
        //内容数据类型是BASE64编码
//        String imgFile = "图片文件路径";
//        String imgBase64 = "";
        /*try {
            File file = new File(imgFile);
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(encodeBase64(content));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }*/

        BASE64Encoder base64Encoder = new BASE64Encoder();
        String imgBase64 = null;
        try {
            imgBase64 = base64Encoder.encode(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        bodys.put("AI_VAT_INVOICE_IMAGE", imgBase64);
        bodys.put("AI_VAT_INVOICE_IMAGE_TYPE", "0");

        //启用URL方式进行识别
        //内容数据类型是图像文件URL链接
//        bodys.put("AI_VAT_INVOICE_IMAGE", "图片URL链接");
//        bodys.put("AI_VAT_INVOICE_IMAGE_TYPE", "1");

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //获取response的body
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}