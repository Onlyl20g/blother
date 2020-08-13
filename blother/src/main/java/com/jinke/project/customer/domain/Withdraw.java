package com.jinke.project.customer.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 提款申请表 sys_withdraw
 *
 * @author jinke
 * @date 2019-08-12
 */
public class Withdraw extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String billId;
    /**
     * 授信申请id
     */
    private String creditBillId;
    /**
     * 提款金额
     */
    private BigDecimal amount;
    /**
     * 提款期限
     */
    private Integer period;
    /**
     * 卖方企业Id
     */
    private String sellerId;
    /**
     * 卖方企业名称
     */
    private String sellerCorprName;
    /**
     * 买方企业Id
     */
    private String buyerId;
    /**
     * 买方企业名称
     */
    private String buyerCorprName;
    /**
     * 收款账号
     */
    private String accountNumber;
    /**
     * 收款开户行
     */
    private String accountBank;
    /**
     * 收款名称
     */
    private String accountName;
    /**
     * 回款账号
     */
    private String repaymentNumber;
    /**
     * 回款开户行
     */
    private String repaymentBank;
    /**
     * 回款名称
     */
    private String repaymentName;
    /**
     * 提款申请书
     */
    private String withdrawAppPath;
    /**
     * 提款状态
     */
    private String status;
    /**
     * 银行卡账号id
     */
    private String bankId;
    /**
     * 其他补充资料
     */
    private String additionFilePath;
    /**
     *
     */
    private String str1;
    /**
     *
     */
    private String str2;
    /**
     *
     */
    private String str3;
    /**
     *
     */
    private String str4;
    /**
     *
     */
    private String str5;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

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

    public void setCreditBillId(String creditBillId) {
        this.creditBillId = creditBillId;
    }

    public String getCreditBillId() {
        return creditBillId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setSellerCorprName(String sellerCorprName) {
        this.sellerCorprName = sellerCorprName;
    }

    public String getSellerCorprName() {
        return sellerCorprName;
    }

    public void setBuyerCorprName(String buyerCorprName) {
        this.buyerCorprName = buyerCorprName;
    }

    public String getBuyerCorprName() {
        return buyerCorprName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setRepaymentNumber(String repaymentNumber) {
        this.repaymentNumber = repaymentNumber;
    }

    public String getRepaymentNumber() {
        return repaymentNumber;
    }

    public void setRepaymentBank(String repaymentBank) {
        this.repaymentBank = repaymentBank;
    }

    public String getRepaymentBank() {
        return repaymentBank;
    }

    public void setRepaymentName(String repaymentName) {
        this.repaymentName = repaymentName;
    }

    public String getRepaymentName() {
        return repaymentName;
    }

    public void setWithdrawAppPath(String withdrawAppPath) {
        this.withdrawAppPath = withdrawAppPath;
    }

    public String getWithdrawAppPath() {
        return withdrawAppPath;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setAdditionFilePath(String additionFilePath) {
        this.additionFilePath = additionFilePath;
    }

    public String getAdditionFilePath() {
        return additionFilePath;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr4(String str4) {
        this.str4 = str4;
    }

    public String getStr4() {
        return str4;
    }

    public void setStr5(String str5) {
        this.str5 = str5;
    }

    public String getStr5() {
        return str5;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("billId", getBillId())
                .append("creditBillId", getCreditBillId())
                .append("amount", getAmount())
                .append("period", getPeriod())
                .append("buyerId", getBuyerId())
                .append("sellerId", getSellerId())
                .append("sellerCorprName", getSellerCorprName())
                .append("buyerCorprName", getBuyerCorprName())
                .append("accountNumber", getAccountNumber())
                .append("accountBank", getAccountBank())
                .append("accountName", getAccountName())
                .append("repaymentNumber", getRepaymentNumber())
                .append("repaymentBank", getRepaymentBank())
                .append("repaymentName", getRepaymentName())
                .append("withdrawAppPath", getWithdrawAppPath())
                .append("status", getStatus())
                .append("additionFilePath", getAdditionFilePath())
                .append("bankId", getBankId())
                .append("str1", getStr1())
                .append("str2", getStr2())
                .append("str3", getStr3())
                .append("str4", getStr4())
                .append("str5", getStr5())
                .toString();
    }
}
