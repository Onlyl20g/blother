package com.jinke.project.system.httplog.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 接口调用日志表 sys_httplog
 *
 * @author jinke
 * @date 2019-09-09
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
    private Date createTime;
    /**
     *
     */
    private String requestParamEncrypt;
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setRequestParamEncrypt(String requestParamEncrypt) {
        this.requestParamEncrypt = requestParamEncrypt;
    }

    public String getRequestParamEncrypt() {
        return requestParamEncrypt;
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

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("type", getType())
                .append("status", getStatus())
                .append("syncType", getSyncType())
                .append("method", getMethod())
                .append("requestUrl", getRequestUrl())
                .append("requestParam", getRequestParam())
                .append("createTime", getCreateTime())
                .append("requestParamEncrypt", getRequestParamEncrypt())
                .append("response", getResponse())
                .append("responseTime", getResponseTime())
                .append("msg", getMsg())
                .toString();
    }
}
