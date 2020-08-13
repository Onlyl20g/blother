package com.jinke.project.system.creditBuyerInfo.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 授信申请附表 sys_credit_buyer_info
 *
 * @author jinke
 * @date 2019-08-07
 */
public class CreditBuyerInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long creditsId;
    /**
     *
     */
    private Long creditId;
    /**
     *
     */
    private String buyerCorprName;
    /**
     *
     */
    private String buyerContactName;
    /**
     *
     */
    private String buyerContactTel;
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

    public void setCreditsId(Long creditsId) {
        this.creditsId = creditsId;
    }

    public Long getCreditsId() {
        return creditsId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setBuyerCorprName(String buyerCorprName) {
        this.buyerCorprName = buyerCorprName;
    }

    public String getBuyerCorprName() {
        return buyerCorprName;
    }

    public void setBuyerContactName(String buyerContactName) {
        this.buyerContactName = buyerContactName;
    }

    public String getBuyerContactName() {
        return buyerContactName;
    }

    public void setBuyerContactTel(String buyerContactTel) {
        this.buyerContactTel = buyerContactTel;
    }

    public String getBuyerContactTel() {
        return buyerContactTel;
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
                .append("creditsId", getCreditsId())
                .append("creditId", getCreditId())
                .append("buyerCorprName", getBuyerCorprName())
                .append("buyerContactName", getBuyerContactName())
                .append("buyerContactTel", getBuyerContactTel())
                .append("str1", getStr1())
                .append("str2", getStr2())
                .append("str3", getStr3())
                .append("str4", getStr4())
                .append("str5", getStr5())
                .toString();
    }
}
