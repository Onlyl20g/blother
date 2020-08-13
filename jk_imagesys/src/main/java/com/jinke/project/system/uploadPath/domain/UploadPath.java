package com.jinke.project.system.uploadPath.domain;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * 其他系统上传文件路径地址表 sys_upload_path
 *
 * @author jinke
 * @date 2019-12-10
 */
public class UploadPath {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 路径地址id
     */
    private String billId;
    /**
     * 路径地址
     */
    private String path;
    /**
     * 创建时间
     */
    private Date creditTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillId() {
        return billId;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setCreditTime(Date creditTime) {
        this.creditTime = creditTime;
    }

    public Date getCreditTime() {
        return creditTime;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("billId", getBillId())
                .append("path", getPath())
                .append("creditTime", getCreditTime())
                .toString();
    }
}
