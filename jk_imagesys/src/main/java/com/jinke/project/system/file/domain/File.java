package com.jinke.project.system.file.domain;


import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 文件表 sys_file
 *
 * @author jinke
 * @date 2019-09-09
 */
public class File extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String businessId;
    /**
     *
     */
    private String billId;
    /**
     *
     */
    private String userBusinessId;
    /**
     *
     */
    private String folderBusinessId;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String path;
    /**
     *
     */
    private String fileClass;
    /**
     *
     */
    private String fileSuffix;
    /**
     *
     */
    private Long fileSize;
    /**
     *
     */
    private String delFlag;
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
    private String fileLabel;

    /**
     * ids
     */
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getFileLabel() {
        return fileLabel;
    }

    public void setFileLabel(String fileLabel) {
        this.fileLabel = fileLabel;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillId() {
        return billId;
    }

    public void setUserBusinessId(String userBusinessId) {
        this.userBusinessId = userBusinessId;
    }

    public String getUserBusinessId() {
        return userBusinessId;
    }

    public void setFolderBusinessId(String folderBusinessId) {
        this.folderBusinessId = folderBusinessId;
    }

    public String getFolderBusinessId() {
        return folderBusinessId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setFileClass(String fileClass) {
        this.fileClass = fileClass;
    }

    public String getFileClass() {
        return fileClass;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
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

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("businessId", getBusinessId())
                .append("billId", getBillId())
                .append("userBusinessId", getUserBusinessId())
                .append("folderBusinessId", getFolderBusinessId())
                .append("name", getName())
                .append("path", getPath())
                .append("fileClass", getFileClass())
                .append("fileSuffix", getFileSuffix())
                .append("fileSize", getFileSize())
                .append("delFlag", getDelFlag())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("fileLabel", getFileLabel())
                .append("ids", getIds())
                .toString();
    }
}
