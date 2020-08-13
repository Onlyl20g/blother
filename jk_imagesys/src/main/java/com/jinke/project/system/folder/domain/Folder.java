package com.jinke.project.system.folder.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 目录表 sys_folder
 *
 * @author jinke
 * @date 2019-09-09
 */
public class Folder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 业务id
     */
    private String businessId;
    /**
     * 业务pid
     */
    private String businessPid;

    private String folderPath;

    /**
     * 用户id
     */
    private String userBisId;

    /**
     * 文件夹名称
     */
    private String name;

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

    public void setBusinessPid(String businessPid) {
        this.businessPid = businessPid;
    }

    public String getBusinessPid() {
        return businessPid;
    }

    public void setUserBisId(String userBisId) {
        this.userBisId = userBisId;
    }

    public String getUserBisId() {
        return userBisId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
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
                .append("businessPid", getBusinessPid())
                .append("folderPath", getFolderPath())
                .append("userBisId", getUserBisId())
                .append("name", getName())
                .append("delFlag", getDelFlag())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }

    public Folder(Integer id, String businessId, String businessPid, String userBisId, String name, String delFlag, String status, Date createTime, Date updateTime) {
        this.id = id;
        this.businessId = businessId;
        this.businessPid = businessPid;
        this.userBisId = userBisId;
        this.name = name;
        this.delFlag = delFlag;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Folder() {
    }
}
