package com.jinke.project.system.filepath.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 文件路Dto
 *
 * @author jinke
 * @date 2019-07-14
 */
public class FilepathDto {
    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String filepathId;
    /**
     *
     */
    private String urlBillid;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private String formName;
    /**
     *
     */
    private String operatorName;
    /**
     *
     */
    private String status;
    /**
     *
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilepathId() {
        return filepathId;
    }

    public void setFilepathId(String filepathId) {
        this.filepathId = filepathId;
    }

    public String getUrlBillid() {
        return urlBillid;
    }

    public void setUrlBillid(String urlBillid) {
        this.urlBillid = urlBillid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("filepathId", getFilepathId())
                .append("url", getUrlBillid())
                .append("type", getType())
                .append("formName", getFormName())
                .append("operatorName", getOperatorName())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .toString();
    }
}