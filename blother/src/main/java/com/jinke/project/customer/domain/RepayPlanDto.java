package com.jinke.project.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 还款计划Dto
 *
 * @author jinke
 * @date 2019-08-13
 */
public class RepayPlanDto {
    /**
     *
     */
    private Long id;
    /**
     * 提款申请id
     */
    private String cashApplyId;
    /**
     * 待还金额
     */
    private String amount;
    /**
     * 计划还款日期
     */
    private String repayDate;
    /**
     * 放款凭证编号
     */
    private String loanRecord;
    /**
     * 还款状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCashApplyId(String cashApplyId) {
        this.cashApplyId = cashApplyId;
    }

    public String getCashApplyId() {
        return cashApplyId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setLoanRecord(String loanRecord) {
        this.loanRecord = loanRecord;
    }

    public String getLoanRecord() {
        return loanRecord;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("cashApplyId", getCashApplyId())
                .append("amount", getAmount())
                .append("repayDate", getRepayDate())
                .append("loanRecord", getLoanRecord())
                .append("status", getStatus())
                .append("remark", getRemark())
                .toString();
    }
}