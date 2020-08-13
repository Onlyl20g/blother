package com.jinke.project.customer.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 授信dto
 *
 * @author jinke
 * @date 2019-07-17
 */
@Data
public class CreditDto {
    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String billId;
    /**
     * 申请授信额度
     */
    private BigDecimal appCreditAmount;

    /**
     * 授信额度
     */
    private BigDecimal creditAmount;

    /**
     * 授信额度
     */
    private String custManagerName;

    /**
     * 剩余额度
     */
    private BigDecimal currentAmount;

    /**
     * 申请额度期限
     */
    private Integer appCreditPeriod;

    /**
     * 授信期限
     */
    private Integer creditPeriod;

    /**
     * 授信状态
     */
    private String creditStatus;

    /**
     * 申请时间
     */
    private Date createTime;

    /**
     * 卖方企业名称
     */
    private String sellerCorprName;

    /**
     * 卖方联系人
     */
    private String sellerContactName;

    /**
     * 卖方联系人
     */
    private String sellerContactTel;

    /**
     * 保理类型
     */
    private String factorType;

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
     * 担保方式
     */
    private String guaranteeMode;

    /**
     * 授信时间
     */
    private Date approveTime;

    public void setCustManagerName(String custManagerName) {
        this.custManagerName = custManagerName;
    }
}