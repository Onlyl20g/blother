package com.jinke.project.system.scanUpload.domain;


import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * 二维码上传表 sys_scan_upload
 *
 * @author jinke
 * @date 2019-12-02
 */
public class ScanUpload extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String sanLink;
    /**
     *
     */
    private Date validTime;
    /**
     *
     */
    private String folderPath;
    /**
     *
     */
    private String folderId;
    /**
     *
     */
    private String userBusinessId;
    /**
     *
     */
    private String status;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private String businessId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setSanLink(String sanLink) {
        this.sanLink = sanLink;
    }

    public String getSanLink() {
        return sanLink;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setUserBusinessId(String userBusinessId) {
        this.userBusinessId = userBusinessId;
    }

    public String getUserBusinessId() {
        return userBusinessId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sanLink", getSanLink())
                .append("validTime", getValidTime())
                .append("folderPath", getFolderPath())
                .append("folderId", getFolderId())
                .append("userBusinessId", getUserBusinessId())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("businessId", getBusinessId())
                .toString();
    }
}
