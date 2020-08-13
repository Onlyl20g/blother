package com.jinke.project.system.ocr.domain;


import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * ocr表 sys_ocr
 *
 * @author jinke
 * @date 2019-09-09
 */
public class Ocr extends BaseEntity {
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
     * 业务类型（发票识别、营业执照识别、表单图片、文字图片）
     */
    private String businessType;
    /**
     * 文件来源（系统内部上传、接口调用）
     */
    private String dataSource;
    /**
     * 识别结果
     */
    private String result;
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

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
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
                .append("businessType", getBusinessType())
                .append("dataSource", getDataSource())
                .append("result", getResult())
                .append("delFlag", getDelFlag())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
