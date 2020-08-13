package com.jinke.project.system.billId.domain;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * 业务申请单据凭证表 sys_bill_id
 *
 * @author jinke
 * @date 2019-11-27
 */
public class BillId {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 业务申请单据号
     */
    private String billId;
    /**
     * 系统类别
     */
    private String system;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String note;

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

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystem() {
        return system;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("billId", getBillId())
                .append("system", getSystem())
                .append("createTime", getCreateTime())
                .append("note", getNote())
                .toString();
    }
}
