package com.jinke.project.tool.sso;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jinke.common.constant.WebConstants;
import com.jinke.common.utils.DateUtils;
import com.jinke.common.utils.StringUtils;
import com.jinke.project.tool.sso.bean.JinkResult;
import com.jinke.project.tool.sso.bean.SsoCheckTokenResultBean;
import com.jinke.project.tool.tools.EncryptDecryptUtil;
import com.jinke.project.tool.tools.RemoteUtil;

import java.util.*;

import static com.jinke.common.constant.Constants.PLATFORM;

public class SsoUtil {
    public static JinkResult<SsoCheckTokenResultBean> checkToken(String custId, String accessToken) {
        if (StringUtils.isEmpty(custId) || StringUtils.isEmpty(accessToken)) {
            return null;
        }
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("cust_id", custId);
            params.put("access_token", accessToken);

            String data = RemoteUtil.postData(WebConstants.SSO_BASE_URL + WebConstants.SSO_CHECK_TOKEN, null, createEncryptData(params));
            if (data != null) {
                return JSON.parseObject(data, new TypeReference<JinkResult<SsoCheckTokenResultBean>>() {
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static HashMap<String, String> createEncryptData(HashMap<String, String> params) {
        String json = JSON.toJSONString(params);
        HashMap<String, String> body = new HashMap<>();
        body.put("signdata", EncryptDecryptUtil.buildRSASignByPrivateKey(getOrderedParams(params), WebConstants.SSO_PRIVATE_KEY));
        body.put("encryptdata", EncryptDecryptUtil.buildRSAEncryptByPublicKey(json, WebConstants.SSO_PUBLIC_KEY));
        body.put("timestamp", DateUtils.dateTimeNow());
        body.put("platform", PLATFORM);

        return body;
    }

    private static String getOrderedParams(Map<String, String> params) {
        // 将参数转化为一个键值对的数组模式
        List<Map.Entry<String, String>> list = new ArrayList<>(params.entrySet());
        // 按照key字典类型进行排序
        // list.sort(Comparator.comparing(Map.Entry::getKey));
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        // 组装签名串
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> en : list) {
            sb.append(en.getKey());
            sb.append("=");
            sb.append(en.getValue());
            sb.append("&");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
