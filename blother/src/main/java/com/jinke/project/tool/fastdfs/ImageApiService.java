package com.jinke.project.tool.fastdfs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jinke.project.system.httplog.domain.Httplog;
import com.jinke.project.system.httplog.domain.HttplogStatus;
import com.jinke.project.system.httplog.domain.HttplogSyncType;
import com.jinke.project.system.httplog.domain.HttplogType;
import com.jinke.project.system.httplog.service.IHttplogService;
import com.jinke.project.tool.tools.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

@Service
public class ImageApiService {

    private static final Logger logger = LoggerFactory.getLogger(ImageApiService.class);

    @Autowired
    private IHttplogService httpService;

    @Value("${aliapihost}")
    private String aliapihost;

    @Value("${aliapipath}")
    private String aliapipath;

    @Value("${aliappcode}")
    private String aliappcode;

    @Value("${aliidcardapihost}")
    private String aliidcardapihost;

    @Value("${aliidcardapipath}")
    private String aliidcardapipath;


    /**
     * 营业执照识别接口
     *
     * @param file
     * @param response
     * @return
     * @throws Exception
     */
    public String parseLicense(byte[] file, HttpResponse response) throws Exception {

        Httplog httplog = null;

        try {

            httplog = new Httplog();
            httplog.setCreateTime(new Date());
            httplog.setStatus(HttplogStatus.UNKNOWN.getValue());
            httplog.setType(HttplogType.BUSINESS_LICENSE_RECON.getValue());
            httplog.setSyncType(HttplogSyncType.SYNC.getValue());

            //API产品路径
            String host = aliapihost;
            String path = aliapipath;
            String method = HttpMethod.POST.name();
            //阿里云APPCODE
            String appcode = aliappcode;
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "APPCODE " + appcode);
            //根据API的要求，定义相对应的Content-Type
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            Map<String, String> querys = new HashMap<String, String>();
            Map<String, String> bodys = new HashMap<String, String>();

            //内容数据类型，如：0，则表示BASE64编码；1，则表示图像文件URL链接
            //启用BASE64编码方式进行识别
            //内容数据类型是BASE64编码
            String imgBase64 = "";
            imgBase64 = new String(encodeBase64(file));

            bodys.put("AI_BUSINESS_LICENSE_IMAGE", imgBase64);
            bodys.put("AI_BUSINESS_LICENSE_IMAGE_TYPE", "0");

//            logger.info("business license recon parameters : " + HttpUtil.toParams(bodys));

            httplog.setRequestUrl(host + path);
            httplog.setRequestTime(new Date());
            httplog.setMethod(method);

            //启用URL方式进行识别
            //内容数据类型是图像文件URL链接
            //   bodys.put("AI_BUSINESS_LICENSE_IMAGE", "图片URL链接");
            //  bodys.put("AI_BUSINESS_LICENSE_IMAGE_TYPE", "1");

            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String result = EntityUtils.toString(response.getEntity());

            httplog.setResponseTime(new Date());
            httplog.setResponse(result);
            httplog.setStatus(HttplogStatus.SUCCESS.getValue());

            //获取response的body
            return result;
        } catch (Exception e) {
            logger.error("", e);

            if (httplog != null) {
                httplog.setStatus(HttplogStatus.FAILED.getValue());
                httplog.setMsg(e.getMessage());
            }

            return "";
        } finally {
            if (httplog != null) {
                this.httpService.insertHttplog(httplog);
            }
        }
    }

    /*
     * 获取参数的json对象
     */
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * 身份证识别接口
     *
     * @param file
     * @param response
     * @return
     * @throws Exception
     */
    public String parseIDCard(byte[] file, HttpResponse response) throws Exception {

        JSONObject resultObj = null;
        Httplog httplog = null;

        try {
            httplog = new Httplog();
            httplog.setCreateTime(new Date());
            httplog.setStatus(HttplogStatus.UNKNOWN.getValue());
            httplog.setType(HttplogType.BUSINESS_LICENSE_RECON.getValue());
            httplog.setSyncType(HttplogSyncType.SYNC.getValue());


            String host = aliidcardapihost;
            String path = aliidcardapipath;

            //阿里云APPCODE
            String appcode = aliappcode;
            Boolean is_old_format = false;//如果文档的输入中含有inputs字段，设置为True， 否则设置为False
            //请根据线上文档修改configure字段
            JSONObject configObj = new JSONObject();
            configObj.put("side", "face");
            String config_str = configObj.toString();
            //            configObj.put("min_size", 5);
            //            String config_str = "";

            String method = HttpMethod.POST.name();
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);

            Map<String, String> querys = new HashMap<String, String>();

            // 对图像进行base64编码
            String imgBase64 = "";
            imgBase64 = new String(encodeBase64(file));
            // 拼装请求body的json字符串
            JSONObject requestObj = new JSONObject();
            try {
                if (is_old_format) {
                    JSONObject obj = new JSONObject();
                    obj.put("image", getParam(50, imgBase64));
                    if (config_str.length() > 0) {
                        obj.put("configure", getParam(50, config_str));
                    }
                    JSONArray inputArray = new JSONArray();
                    inputArray.add(obj);
                    requestObj.put("inputs", inputArray);
                } else {
                    requestObj.put("image", imgBase64);
                    if (config_str.length() > 0) {
                        requestObj.put("configure", config_str);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String bodys = requestObj.toString();

            //logger.info("business license recon parameters : " + bodys);

            httplog.setRequestUrl(host + path);
            httplog.setRequestTime(new Date());
            httplog.setMethod(method);

            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if (stat != 200) {
                logger.info("Http code: " + stat);
                logger.info("http header error msg: " + response.getFirstHeader("X-Ca-Error-Message"));
                logger.info("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return "";
            }

            String res = EntityUtils.toString(response.getEntity());
            resultObj = JSON.parseObject(res);
            if (is_old_format) {
                JSONArray outputArray = resultObj.getJSONArray("outputs");
                String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                JSONObject out = JSON.parseObject(output);
            } else {
                System.err.println(resultObj.toJSONString());
            }

            httplog.setResponseTime(new Date());
            httplog.setResponse(resultObj.toJSONString());
            httplog.setStatus(HttplogStatus.SUCCESS.getValue());

            return resultObj.toJSONString();
        } catch (Exception e) {
            logger.error("", e);

            if (httplog != null) {
                httplog.setMsg(e.getMessage());
                httplog.setStatus(HttplogStatus.FAILED.getValue());
            }

            return "";
        } finally {
            if (httplog != null) {
                this.httpService.insertHttplog(httplog);
            }
        }

    }
}
