package com.jinke.project.system.bankCardInfo.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 银行卡表 sys_bank_card_info
 *
 * @author jinke
 * @date 2019-08-22
 */
public class BankCardInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String supplierBillId;
    /**
     *
     */
    private String billId;
    /**
     *
     */
    private String bankCard;
    /**
     *
     */
    private String bank;
    /**
     *
     */
    private String owner;
    /**
     *
     */
    private String deleteFlag;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSupplierBillId(String supplierBillId) {
        this.supplierBillId = supplierBillId;
    }

    public String getSupplierBillId() {
        return supplierBillId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank() {
        return bank;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
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
                .append("supplierBillId", getSupplierBillId())
                .append("billId", getBillId())
                .append("bankCard", getBankCard())
                .append("bank", getBank())
                .append("owner", getOwner())
                .append("deleteFlag", getDeleteFlag())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("str1", getStr1())
                .append("str2", getStr2())
                .append("str3", getStr3())
                .append("str4", getStr4())
                .append("str5", getStr5())
                .toString();
    }
}
