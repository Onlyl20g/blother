package com.jinke.project.customer.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 授信申请表 sys_credit
 *
 * @author jinke
 * @date 2019-07-17
 */
public class Credit extends BaseEntity {
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
     *
     */
    private String supplierBillId;
    /**
     * 客户经理id
     */
    private String custManagerName;
    /**
     * 客户经理名称
     */
    private String custManagerId;
    /**
     *
     */
    private BigDecimal appCreditAmount;
    /**
     * 授信额度
     */
    private BigDecimal creditAmount;
    /**
     * 授信类型
     */
    private String creditType;
    /**
     * 当前可用额度
     */
    private BigDecimal currentAmount;
    /**
     * 授信期限
     */
    private Integer creditPeriod;
    /**
     * 申请额度期限
     */
    private Integer appCreditPeriod;
    /**
     * 卖方企业名称
     */
    private String sellerCorprName;
    /**
     * 卖方联系人
     */
    private String sellerContactName;
    /**
     * 卖方联系人电话
     */
    private String sellerContactTel;
    /**
     * 保理类型
     */
    private String factorType;
    /**
     * 担保方式
     */
    private String guaranteeMode;
    /**
     * 授信状态
     */
    private String creditStatus;
    /**
     * 业务申请书
     */
    private String businessAppPath;
    /**
     * 企业基本情况介绍
     */
    private String fundamentalStatePath;
    /**
     * 公司章程或合伙、联营协议复印件
     */
    private String bylawPath;
    /**
     * 法定代表人：身份证复印件、履历
     */
    private String corprPath;
    /**
     * 财务负责人：身份证复印件、履历
     */
    private String financePrincipalPath;
    /**
     * 实际控制人：结婚证、本人及配偶身份证复印件、本人履历
     */
    private String actualControllerPath;
    /**
     * 其他补充资料
     */
    private String additionFilePath;
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 更新授信申请时间
     */
    private Date updateTime;
    /**
     * 授信时间
     */
    private Date approveTime;

    /**
     * 买方信息
     */
    private List buyerInfoList;

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

    public List getBuyerInfoList() {
        return buyerInfoList;
    }

    public void setBuyerInfoList(List buyerInfoList) {
        this.buyerInfoList = buyerInfoList;
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

    public void setSupplierBillId(String supplierBillId) {
        this.supplierBillId = supplierBillId;
    }

    public String getSupplierBillId() {
        return supplierBillId;
    }

    public void setCustManagerName(String custManagerName) {
        this.custManagerName = custManagerName;
    }

    public String getCustManagerName() {
        return custManagerName;
    }

    public void setCustManagerId(String custManagerId) {
        this.custManagerId = custManagerId;
    }

    public String getCustManagerId() {
        return custManagerId;
    }

    public void setAppCreditAmount(BigDecimal appCreditAmount) {
        this.appCreditAmount = appCreditAmount;
    }

    public BigDecimal getAppCreditAmount() {
        return appCreditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCreditPeriod(Integer creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public Integer getCreditPeriod() {
        return creditPeriod;
    }

    public void setAppCreditPeriod(Integer appCreditPeriod) {
        this.appCreditPeriod = appCreditPeriod;
    }

    public Integer getAppCreditPeriod() {
        return appCreditPeriod;
    }

    public void setSellerCorprName(String sellerCorprName) {
        this.sellerCorprName = sellerCorprName;
    }

    public String getSellerCorprName() {
        return sellerCorprName;
    }

    public void setSellerContactName(String sellerContactName) {
        this.sellerContactName = sellerContactName;
    }

    public String getSellerContactName() {
        return sellerContactName;
    }

    public void setSellerContactTel(String sellerContactTel) {
        this.sellerContactTel = sellerContactTel;
    }

    public String getSellerContactTel() {
        return sellerContactTel;
    }

    public void setFactorType(String factorType) {
        this.factorType = factorType;
    }

    public String getFactorType() {
        return factorType;
    }

    public void setGuaranteeMode(String guaranteeMode) {
        this.guaranteeMode = guaranteeMode;
    }

    public String getGuaranteeMode() {
        return guaranteeMode;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setBusinessAppPath(String businessAppPath) {
        this.businessAppPath = businessAppPath;
    }

    public String getBusinessAppPath() {
        return businessAppPath;
    }

    public void setFundamentalStatePath(String fundamentalStatePath) {
        this.fundamentalStatePath = fundamentalStatePath;
    }

    public String getFundamentalStatePath() {
        return fundamentalStatePath;
    }

    public void setBylawPath(String bylawPath) {
        this.bylawPath = bylawPath;
    }

    public String getBylawPath() {
        return bylawPath;
    }

    public void setCorprPath(String corprPath) {
        this.corprPath = corprPath;
    }

    public String getCorprPath() {
        return corprPath;
    }

    public void setFinancePrincipalPath(String financePrincipalPath) {
        this.financePrincipalPath = financePrincipalPath;
    }

    public String getFinancePrincipalPath() {
        return financePrincipalPath;
    }

    public void setActualControllerPath(String actualControllerPath) {
        this.actualControllerPath = actualControllerPath;
    }

    public String getActualControllerPath() {
        return actualControllerPath;
    }

    public void setAdditionFilePath(String additionFilePath) {
        this.additionFilePath = additionFilePath;
    }

    public String getAdditionFilePath() {
        return additionFilePath;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
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
                .append("supplierBillId", getSupplierBillId())
                .append("appCreditAmount", getAppCreditAmount())
                .append("creditAmount", getCreditAmount())
                .append("creditType", getCreditType())
                .append("currentAmount", getCurrentAmount())
                .append("creditPeriod", getCreditPeriod())
                .append("appCreditPeriod", getAppCreditPeriod())
                .append("sellerCorprName", getSellerCorprName())
                .append("sellerContactName", getSellerContactName())
                .append("sellerContactTel", getSellerContactTel())
                .append("factorType", getFactorType())
                .append("guaranteeMode", getGuaranteeMode())
                .append("creditStatus", getCreditStatus())
                .append("businessAppPath", getBusinessAppPath())
                .append("fundamentalStatePath", getFundamentalStatePath())
                .append("bylawPath", getBylawPath())
                .append("corprPath", getCorprPath())
                .append("financePrincipalPath", getFinancePrincipalPath())
                .append("actualControllerPath", getActualControllerPath())
                .append("additionFilePath", getAdditionFilePath())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("approveTime", getApproveTime())
                .append("str1", getStr1())
                .append("str2", getStr2())
                .append("str3", getStr3())
                .append("str4", getStr4())
                .append("str5", getStr5())
                .toString();
    }
}
