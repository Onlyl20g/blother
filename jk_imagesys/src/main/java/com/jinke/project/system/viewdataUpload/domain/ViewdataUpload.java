package com.jinke.project.system.viewdataUpload.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 影像上传表 sys_viewdata_upload
 *
 * @author jinke
 * @date 2019-09-09
 */
public class ViewdataUpload extends BaseEntity {
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
     * 用户业务id
     */
    private String userBusinessId;
    /**
     * 文件单据id
     */
    private String fileBillId;
    /**
     * 文件来源
     */
    private String dataSource;
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

    public void setUserBusinessId(String userBusinessId) {
        this.userBusinessId = userBusinessId;
    }

    public String getUserBusinessId() {
        return userBusinessId;
    }

    public void setFileBillId(String fileBillId) {
        this.fileBillId = fileBillId;
    }

    public String getFileBillId() {
        return fileBillId;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
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
                .append("userBusinessId", getUserBusinessId())
                .append("fileBillId", getFileBillId())
                .append("dataSource", getDataSource())
                .append("delFlag", getDelFlag())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
