package com.jinke.project.system.fileLabel.domain;


import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 文件标签表 sys_file_label
 *
 * @author jinke
 * @date 2019-09-09
 */
public class FileLabel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 用户业务id
     */
    private String userBusinessId;
    /**
     * 文件业务id
     */
    private String fileBusinessId;
    /**
     * 标签名称
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

    public void setUserBusinessId(String userBusinessId) {
        this.userBusinessId = userBusinessId;
    }

    public String getUserBusinessId() {
        return userBusinessId;
    }

    public void setFileBusinessId(String fileBusinessId) {
        this.fileBusinessId = fileBusinessId;
    }

    public String getFileBusinessId() {
        return fileBusinessId;
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
                .append("userBusinessId", getUserBusinessId())
                .append("fileBusinessId", getFileBusinessId())
                .append("name", getName())
                .append("delFlag", getDelFlag())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
