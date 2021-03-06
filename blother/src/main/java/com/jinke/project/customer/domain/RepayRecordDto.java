package com.jinke.project.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 还款记录DTO
 *
 * @author jinke
 * @date 2019-08-13
 */
public class RepayRecordDto {
    /**
     *
     */
    private Long id;
    /**
     * 提款申请id
     */
    private String cashApplyId;
    /**
     * 还款金额
     */
    private String amount;
    /**
     * 未还款金额
     */
    private String pendingAmount;
    /**
     * 放款凭证编号
     */
    private String loanRecord;
    /**
     * 还款日期
     */
    private String repayDate;
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

    public void setPendingAmount(String pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getPendingAmount() {
        return pendingAmount;
    }

    public void setLoanRecord(String loanRecord) {
        this.loanRecord = loanRecord;
    }

    public String getLoanRecord() {
        return loanRecord;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getRepayDate() {
        return repayDate;
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
                .append("pendingAmount", getPendingAmount())
                .append("loanRecord", getLoanRecord())
                .append("repayDate", getRepayDate())
                .append("remark", getRemark())
                .toString();
    }
}