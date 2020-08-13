package com.jinke.project.system.ncInteractivity.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinke.common.constant.Constants;
import com.jinke.project.customer.domain.Credit;
import com.jinke.project.customer.domain.Withdraw;
import com.jinke.project.system.bankCardInfo.domain.BankCardInfo;
import com.jinke.project.system.enterprise.domain.Enterprise;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.systemDeploy.domain.SystemDeploy;
import com.jinke.project.system.systemDeploy.service.ISystemDeployService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NCInteractiveControllor {
    private final static Logger logger = LoggerFactory.getLogger(NCInteractiveControllor.class);

    @Autowired
    private ISystemDeployService systemDeployService;

    @Value("${hxurl}")
    public String hxurl;

    @Value("${suppliermanagerurl}")
    public String suppliermanagerurl;

    @Value("${supplierregisterurl}")
    public String supplierregisterurl;

    @Value("${creditediturl}")
    public String creditediturl;

    @Value("${hkyxurl}")
    public String hkyxurl;

    @Value("${yxuploadfile}")
    public String yxuploadfile;

    @Value("${withdrawselecturl}")
    public String withdrawselecturl;

    @Value("${withdrawurl}")
    public String withdrawurl;

    @Value("${applylisturl}")
    public String applylisturl;

    @Value("${applystatusurl}")
    public String applystatusurl;

    @Value("${byerinfoselecturl}")
    public String byerinfoselecturl;

    @Value("${crediturl}")
    public String crediturl;

    @Value("${creditselecturl}")
    public String creditselecturl;

    /**
     * 供应商注册
     *
     * @param supplier
     * @param info
     * @return
     * @throws Exception
     */
    public String supplierHttpPost(Supplier supplier, Enterprise info) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("businessLicenseNo", supplier.getBusinessLicenseCode());
        if (supplier.getCorprName() == null || "".equals(supplier.getCorprName())) {
            map.put("corpName", "未知");
        } else {
            map.put("corpName", supplier.getCorprName());
        }
        if (supplier.getCorprType() == null || "".equals(supplier.getCorprType())) {
            map.put("corpType", "未知");
        } else {
            map.put("corpType", supplier.getCorprType());
        }
        if (supplier.getCorprAddres() == null || "".equals(supplier.getCorprAddres())) {
            map.put("corpAddres", "未知");
        } else {
            map.put("corpAddres", supplier.getCorprAddres());
        }
        if (supplier.getCustomerManagerId() == null || "".equals(supplier.getCustomerManagerId())) {
            map.put("custManagerId", "未知");
        } else {
            map.put("custManagerId", supplier.getCustomerManagerId());
        }
        if (supplier.getLegalPersonName() == null || "".equals(supplier.getLegalPersonName())) {
            map.put("legalPersonName", "未知");
        } else {
            map.put("legalPersonName", supplier.getLegalPersonName());
        }
        if (supplier.getLegalPersonIdno() == null || "".equals(supplier.getLegalPersonIdno())) {
            map.put("legalPersonIdno", "未知");
        } else {
            map.put("legalPersonIdno", supplier.getLegalPersonIdno());
        }
        if (supplier.getLegalPersonPhone() == null || "".equals(supplier.getLegalPersonPhone())) {
            map.put("legalPersonPhone", "未知");
        } else {
            map.put("legalPersonPhone", supplier.getLegalPersonPhone());
        }
        if (supplier.getBillId() == null || "".equals(supplier.getBillId())) {
            map.put("sellerId", "未知");
        } else {
            map.put("sellerId", supplier.getBillId());
        }
        map.put("regCapital", info.getEnterpriseCapital());
        map.put("establishDate", supplier.getEstablishDate());
        map.put("validityDuration", info.getEnterpriseValidDateStart() + "~" + info.getEnterpriseValidDateend());
        map.put("businessLicenseInfo", info);
        String url = hxurl + supplierregisterurl;
        String data = JSON.toJSONString(map);
        String s = post(url, data, null);
        return s;
    }

    /**
     * 查询客户经理
     *
     * @param time
     * @throws Exception
     */
    public String supplierHttpPost(String time) {
        String url = hxurl + suppliermanagerurl;
        String s = null;
        try {
            s = post(url, "lastUpdateTime", time);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * 补交资料
     *
     * @param creditApplyId
     * @param sellerId
     * @return
     */
    public String payCreditHttpPost(String creditApplyId, String sellerId) {
        String url = hxurl + creditediturl;

        Map<String, Object> map = new HashMap();
        map.put("creditApplyId", creditApplyId);
        map.put("sellerId", sellerId);
        String s = null;
        try {
            s = post(url, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * post请求
     *
     * @param url
     * @param param
     * @param d
     * @return
     * @throws IOException
     */
    public static String post(String url, String param, String d) throws IOException {
        // 服务端通常是根据请求头（headers）中的 Content-Type 字段来获知请求中的消息主体是用何种方式编码，再对主体进行解析。所以说到 POST 提交数据方案，包含了 Content-Type 和消息主体编码方式两部分
        HttpClient client = new HttpClient(); // 客户端实例化
        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        PostMethod postMethod = new PostMethod(url); // 请求方法post，可以将请求路径传入构造参数中
        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        postMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        NameValuePair[] nvs = new NameValuePair[1];
        if (d == null) {
            nvs[0] = new NameValuePair(param, "");
        } else {
            nvs[0] = new NameValuePair(param, d);
        }
        postMethod.setRequestBody(nvs);
        int i = client.executeMethod(postMethod);  // 执行方法
        String result = postMethod.getResponseBodyAsString(); // 得到相应数据

        logger.info("[post]" + url + "params:" + param + " result: " + result);
        JSONObject jsonObject = JSON.parseObject(result);
        jsonObject.put("code", i);
        String jsonStr = jsonObject.toJSONString();

        return jsonStr;
    }

    /**
     * 授信申请数据传输d
     *
     * @param credit
     * @return
     */
    public String creditHttpPost(Credit credit) throws IOException {

        Map<String, Object> map = new HashMap();
        map.put("creditApplyId", credit.getBillId());
        map.put("applyCreditAmount", credit.getAppCreditAmount());
        map.put("applyPeriod", credit.getAppCreditPeriod());
        map.put("custManagerId", credit.getCustManagerId());//客户经理(核心id)
        map.put("FactoringType", credit.getFactorType());
        map.put("guaranteeMode", credit.getGuaranteeMode());
        map.put("sellerId", credit.getSupplierBillId());
        map.put("sellerName", credit.getSellerCorprName());
        map.put("sellerContactName", credit.getSellerContactName());
        map.put("sellerContactTel", credit.getSellerContactTel());
        map.put("buyerInfoList", credit.getBuyerInfoList());//买方信息列表
        String url = hxurl + crediturl;
        String data = JSON.toJSONString(map);
        String s = post(url, data, null);
        return s;
    }

    /**
     * 授信查询
     *
     * @param sellerCustId
     * @return
     * @throws Exception
     */
    public String creditHttpPost(String sellerCustId) throws Exception {
        String url = hxurl + creditselecturl;
        Map<String, Object> map = new HashMap();
        map.put("sellerCustId", sellerCustId);
        map.put("curPageNum", 1);
        map.put("pageSize", 1000);
        String s = post(url, map);
        return s;
    }

    /**
     * 查询买方信息
     *
     * @param time
     * @return
     * @throws Exception
     */
    public String buyerPost(String time) throws Exception {
        String url = hxurl + byerinfoselecturl;
        String s = post(url, "lastUpdateTime", time);
        return s;
    }

    /**
     * 传输提款信息
     *
     * @param withdraw
     * @return
     * @throws Exception
     */
    public String withdrawPost(Withdraw withdraw, BankCardInfo bankCardInfo) throws Exception {
        Map<String, Object> map = new HashMap();
        map.put("applyCashId", withdraw.getBillId());
        map.put("applyCashAmount", withdraw.getAmount());
        map.put("applyCashPeriod", withdraw.getPeriod());
        map.put("sellerId", withdraw.getSellerId());
        map.put("accountNo", bankCardInfo.getBankCard());
        map.put("accountUsername", bankCardInfo.getOwner());
        map.put("accountBank", bankCardInfo.getBank());
//        map.put("sellerCorpName", withdraw.getSellerCorprName());
        map.put("buyerId", withdraw.getBuyerId());
        map.put("buyerCorpName", withdraw.getBuyerCorprName());
        map.put("creditApplyId", withdraw.getCreditBillId());
        String url = hxurl + withdrawurl;
        String data = JSON.toJSONString(map);
        String s = post(url, data, null);
        return s;
    }


    public String sendToImageSystem(String map) {
        SystemDeploy systemDeploy = new SystemDeploy();
        systemDeploy.setSysKey(Constants.BL_CODE);
        List<SystemDeploy> systemDeploys = systemDeployService.selectSystemDeployList(systemDeploy);
        if (systemDeploys.size() == 0) {
            return "";
        }
//        String url = hkyxurl + yxuploadfile;
        String url = systemDeploys.get(0).getValue();
        String s = null;
//        map.put("fileName",map.get("fileFullName").split("\\.")[0]);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("body", map);
        try {
            s = post(url, map1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 查询提款信息
     *
     * @param creditApplyId
     * @return
     * @throws Exception
     */
    public String withdrawPost(String creditApplyId) throws Exception {
        String url = hxurl + withdrawselecturl;
        Map<String, Object> map = new HashMap();
        map.put("creditApplyId", creditApplyId);
        map.put("curPageNum", 1);
        map.put("pageSize", 1000);
        String s = post(url, map);
        logger.info("[" + url + "] " + s);
        return s;
    }

    /**
     * 还款计划查询
     *
     * @param cashApplyId
     * @return
     * @throws Exception
     */
    public String applyListPost(String cashApplyId) throws Exception {
        String url = hxurl + applylisturl;
        Map<String, Object> map = new HashMap();
        map.put("cashApplyId", cashApplyId);
        String s = post(url, map);
        return s;
    }

    /**
     * 还款状态查询
     *
     * @param cashApplyId
     * @return
     * @throws Exception
     */
    public String applyStatusPost(String cashApplyId) throws Exception {
        String url = hxurl + applystatusurl;
        Map<String, Object> map = new HashMap();
        map.put("cashApplyId", cashApplyId);
        String s = post(url, map);
        return s;
    }

    /**
     * 多参数post请求
     *
     * @param url
     * @param map
     * @return
     * @throws IOException
     */
    public static String post(String url, Map map) throws IOException {
        // 服务端通常是根据请求头（headers）中的 Content-Type 字段来获知请求中的消息主体是用何种方式编码，再对主体进行解析。所以说到 POST 提交数据方案，包含了 Content-Type 和消息主体编码方式两部分
        HttpClient client = new HttpClient(); // 客户端实例化
        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        PostMethod postMethod = new PostMethod(url); // 请求方法post，可以将请求路径传入构造参数中
        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        postMethod.addRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        NameValuePair[] nvs = new NameValuePair[map.keySet().size()];
        List listKey = new ArrayList();
        List listValue = new ArrayList();
        for (Object key : map.keySet()) {
            listKey.add(key);
            listValue.add(map.get(key));
        }
        for (int i = 0; i < listKey.size(); i++) {
            nvs[i] = new NameValuePair(listKey.get(i).toString(), listValue.get(i).toString());
        }
        postMethod.setRequestBody(nvs);
        int i = client.executeMethod(postMethod);  // 执行方法
        byte[] responseBody = postMethod.getResponseBody(); // 得到相应数据
        String result = new String(responseBody);
        logger.info("[post]" + url + " params:" + map + " result: " + result);
        JSONObject jsonObject = JSON.parseObject(result);
        jsonObject.put("code", i);
        String jsonStr = jsonObject.toJSONString();
        return jsonStr;
    }
}