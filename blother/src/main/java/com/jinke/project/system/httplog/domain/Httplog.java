package com.jinke.project.system.httplog.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 对外HTTP调用日志表 sys_httplog
 *
 * @author jinke
 * @date 2019-07-16
 */
public class Httplog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private String status;
    /**
     *
     */
    private String syncType;
    /**
     *
     */
    private String method;
    /**
     *
     */
    private String requestUrl;
    /**
     *
     */
    private String requestParam;
    /**
     *
     */
    private String requestParamEncrypt;
    /**
     *
     */
    private Date requestTime;
    /**
     *
     */
    private String response;
    /**
     *
     */
    private Date responseTime;
    /**
     *
     */
    private String msg;
    /**
     *
     */
    private String str1;
    /**
     *
     */
    private String str2;
    /**
     *
     */
    private String str3;
    /**
     *
     */
    private String str4;
    /**
     *
     */
    private String str5;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParamEncrypt(String requestParamEncrypt) {
        this.requestParamEncrypt = requestParamEncrypt;
    }

    public String getRequestParamEncrypt() {
        return requestParamEncrypt;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr4(String str4) {
        this.str4 = str4;
    }

    public String getStr4() {
        return str4;
    }

    public void setStr5(String str5) {
        this.str5 = str5;
    }

    public String getStr5() {
        return str5;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
                .append("type", getType()).append("status", getStatus()).append("syncType", getSyncType())
                .append("method", getMethod()).append("requestUrl", getRequestUrl())
                .append("requestParam", getRequestParam()).append("requestParamEncrypt", getRequestParamEncrypt())
                .append("createTime", getCreateTime()).append("requestTime", getRequestTime())
                .append("response", getResponse()).append("responseTime", getResponseTime()).append("msg", getMsg())
                .append("str1", getStr1()).append("str2", getStr2()).append("str3", getStr3())
                .append("str4", getStr4()).append("str5", getStr5()).toString();
    }
}
