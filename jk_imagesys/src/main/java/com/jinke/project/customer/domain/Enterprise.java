package com.jinke.project.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 企业
 *
 * @author jinke
 * @date 2019-08-05
 */
public class Enterprise {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 企业名称
     */
    private String enterpriseNameCh;
    /**
     * 企业统一社会信用编码
     */
    private String enterpriseId;
    /**
     * 企业注册地址
     */
    private String enterpriseRegisterAddress;
    /**
     * 企业所在地国家行政编码
     */
    private String enterpriseCountryId;
    /**
     * 企业所在省份
     */
    private String enterprisePovince;
    /**
     * 企业所在城市
     */
    private String enterpriseCity;
    /**
     * 企业所在区域
     */
    private String enterpriseDistrict;
    /**
     * 企业开户号地址
     */
    private String enterpriseBankAddress;
    /**
     * 企业联系电话
     */
    private String enterpriseTel;
    /**
     * 企业开户行名称
     */
    private String enterpriseBankName;
    /**
     * 企业开户行所在地国家行政编码
     */
    private String enterpriseBankCountryId;
    /**
     * 企业开户行所在省份
     */
    private String enterpriseBankProvince;
    /**
     * 企业开户行所在城市
     */
    private String enterpriseBankCity;
    /**
     * 企业开户行所在区域
     */
    private String enterpriseBankDistrict;
    /**
     * 企业对公账户号
     */
    private String enterpriseBankId;
    /**
     * 企业法人
     */
    private String enterpriseOwner;
    /**
     * 企业注册资本
     */
    private String enterpriseCapital;
    /**
     * 企业注册时间
     */
    private String enterpriseTime;
    /**
     * 企业类型
     */
    private String enterpriseType;
    /**
     * 企业所属行业
     */
    private String enterpriseIndustry;
    /**
     * 企业所属市场管理机构
     */
    private String enterpriseOirganization;
    /**
     * 企业注册地址所在城市
     */
    private String enterpriseRegisterAddressCity;
    /**
     * 企业曾用名
     */
    private String enterpriseUsedName;
    /**
     * 企业英文名称
     */
    private String enterpriseNameEn;
    /**
     * 企业缴纳社会保险人数
     */
    private String enterpriseInsured;
    /**
     * 企业人数
     */
    private String enterprisePeople;
    /**
     * 企业经营范围
     */
    private String enterpriseScope;
    /**
     * 企业营业期限开始时间
     */
    private String enterpriseValidDateStart;
    /**
     * 企业营业期限结束时间
     */
    private String enterpriseValidDateend;
    /**
     * 企业注册地址所在城市
     */
    private String enterpriseEgisterAddressCity;
    /**
     * 企业注册地址所在区域
     */
    private String enterpriseRegisterAddressDistrict;
    /**
     * 企业注册地址格式标准化
     */
    private String enterpriseRegisterAddressFormatAddress;
    /**
     * 企业注册地址gps
     */
    private String enterpriseRegisterAddressGps;
    /**
     * 企业注册地址所在省份
     */
    private String enterpriseRegisterAddressProvince;
    /**
     * 企业税务登记号
     */
    private String enterpriseTaxpayerRegisterId;
    /**
     * 企业组织机构代码号
     */
    private String enterpriseUniversalCreditIdAgencyCode;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEnterpriseNameCh(String enterpriseNameCh) {
        this.enterpriseNameCh = enterpriseNameCh;
    }

    public String getEnterpriseNameCh() {
        return enterpriseNameCh;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseRegisterAddress(String enterpriseRegisterAddress) {
        this.enterpriseRegisterAddress = enterpriseRegisterAddress;
    }

    public String getEnterpriseRegisterAddress() {
        return enterpriseRegisterAddress;
    }

    public void setEnterpriseCountryId(String enterpriseCountryId) {
        this.enterpriseCountryId = enterpriseCountryId;
    }

    public String getEnterpriseCountryId() {
        return enterpriseCountryId;
    }

    public void setEnterprisePovince(String enterprisePovince) {
        this.enterprisePovince = enterprisePovince;
    }

    public String getEnterprisePovince() {
        return enterprisePovince;
    }

    public void setEnterpriseCity(String enterpriseCity) {
        this.enterpriseCity = enterpriseCity;
    }

    public String getEnterpriseCity() {
        return enterpriseCity;
    }

    public void setEnterpriseDistrict(String enterpriseDistrict) {
        this.enterpriseDistrict = enterpriseDistrict;
    }

    public String getEnterpriseDistrict() {
        return enterpriseDistrict;
    }

    public void setEnterpriseBankAddress(String enterpriseBankAddress) {
        this.enterpriseBankAddress = enterpriseBankAddress;
    }

    public String getEnterpriseBankAddress() {
        return enterpriseBankAddress;
    }

    public void setEnterpriseTel(String enterpriseTel) {
        this.enterpriseTel = enterpriseTel;
    }

    public String getEnterpriseTel() {
        return enterpriseTel;
    }

    public void setEnterpriseBankName(String enterpriseBankName) {
        this.enterpriseBankName = enterpriseBankName;
    }

    public String getEnterpriseBankName() {
        return enterpriseBankName;
    }

    public void setEnterpriseBankCountryId(String enterpriseBankCountryId) {
        this.enterpriseBankCountryId = enterpriseBankCountryId;
    }

    public String getEnterpriseBankCountryId() {
        return enterpriseBankCountryId;
    }

    public void setEnterpriseBankProvince(String enterpriseBankProvince) {
        this.enterpriseBankProvince = enterpriseBankProvince;
    }

    public String getEnterpriseBankProvince() {
        return enterpriseBankProvince;
    }

    public void setEnterpriseBankCity(String enterpriseBankCity) {
        this.enterpriseBankCity = enterpriseBankCity;
    }

    public String getEnterpriseBankCity() {
        return enterpriseBankCity;
    }

    public void setEnterpriseBankDistrict(String enterpriseBankDistrict) {
        this.enterpriseBankDistrict = enterpriseBankDistrict;
    }

    public String getEnterpriseBankDistrict() {
        return enterpriseBankDistrict;
    }

    public void setEnterpriseBankId(String enterpriseBankId) {
        this.enterpriseBankId = enterpriseBankId;
    }

    public String getEnterpriseBankId() {
        return enterpriseBankId;
    }

    public void setEnterpriseOwner(String enterpriseOwner) {
        this.enterpriseOwner = enterpriseOwner;
    }

    public String getEnterpriseOwner() {
        return enterpriseOwner;
    }

    public void setEnterpriseCapital(String enterpriseCapital) {
        this.enterpriseCapital = enterpriseCapital;
    }

    public String getEnterpriseCapital() {
        return enterpriseCapital;
    }

    public void setEnterpriseTime(String enterpriseTime) {
        this.enterpriseTime = enterpriseTime;
    }

    public String getEnterpriseTime() {
        return enterpriseTime;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseIndustry(String enterpriseIndustry) {
        this.enterpriseIndustry = enterpriseIndustry;
    }

    public String getEnterpriseIndustry() {
        return enterpriseIndustry;
    }

    public void setEnterpriseOirganization(String enterpriseOirganization) {
        this.enterpriseOirganization = enterpriseOirganization;
    }

    public String getEnterpriseOirganization() {
        return enterpriseOirganization;
    }

    public void setEnterpriseRegisterAddressCity(String enterpriseRegisterAddressCity) {
        this.enterpriseRegisterAddressCity = enterpriseRegisterAddressCity;
    }

    public String getEnterpriseRegisterAddressCity() {
        return enterpriseRegisterAddressCity;
    }

    public void setEnterpriseUsedName(String enterpriseUsedName) {
        this.enterpriseUsedName = enterpriseUsedName;
    }

    public String getEnterpriseUsedName() {
        return enterpriseUsedName;
    }

    public void setEnterpriseNameEn(String enterpriseNameEn) {
        this.enterpriseNameEn = enterpriseNameEn;
    }

    public String getEnterpriseNameEn() {
        return enterpriseNameEn;
    }

    public void setEnterpriseInsured(String enterpriseInsured) {
        this.enterpriseInsured = enterpriseInsured;
    }

    public String getEnterpriseInsured() {
        return enterpriseInsured;
    }

    public void setEnterprisePeople(String enterprisePeople) {
        this.enterprisePeople = enterprisePeople;
    }

    public String getEnterprisePeople() {
        return enterprisePeople;
    }

    public void setEnterpriseScope(String enterpriseScope) {
        this.enterpriseScope = enterpriseScope;
    }

    public String getEnterpriseScope() {
        return enterpriseScope;
    }

    public void setEnterpriseValidDateStart(String enterpriseValidDateStart) {
        this.enterpriseValidDateStart = enterpriseValidDateStart;
    }

    public String getEnterpriseValidDateStart() {
        return enterpriseValidDateStart;
    }

    public void setEnterpriseValidDateend(String enterpriseValidDateend) {
        this.enterpriseValidDateend = enterpriseValidDateend;
    }

    public String getEnterpriseValidDateend() {
        return enterpriseValidDateend;
    }

    public void setEnterpriseEgisterAddressCity(String enterpriseEgisterAddressCity) {
        this.enterpriseEgisterAddressCity = enterpriseEgisterAddressCity;
    }

    public String getEnterpriseEgisterAddressCity() {
        return enterpriseEgisterAddressCity;
    }

    public void setEnterpriseRegisterAddressDistrict(String enterpriseRegisterAddressDistrict) {
        this.enterpriseRegisterAddressDistrict = enterpriseRegisterAddressDistrict;
    }

    public String getEnterpriseRegisterAddressDistrict() {
        return enterpriseRegisterAddressDistrict;
    }

    public void setEnterpriseRegisterAddressFormatAddress(String enterpriseRegisterAddressFormatAddress) {
        this.enterpriseRegisterAddressFormatAddress = enterpriseRegisterAddressFormatAddress;
    }

    public String getEnterpriseRegisterAddressFormatAddress() {
        return enterpriseRegisterAddressFormatAddress;
    }

    public void setEnterpriseRegisterAddressGps(String enterpriseRegisterAddressGps) {
        this.enterpriseRegisterAddressGps = enterpriseRegisterAddressGps;
    }

    public String getEnterpriseRegisterAddressGps() {
        return enterpriseRegisterAddressGps;
    }

    public void setEnterpriseRegisterAddressProvince(String enterpriseRegisterAddressProvince) {
        this.enterpriseRegisterAddressProvince = enterpriseRegisterAddressProvince;
    }

    public String getEnterpriseRegisterAddressProvince() {
        return enterpriseRegisterAddressProvince;
    }

    public void setEnterpriseTaxpayerRegisterId(String enterpriseTaxpayerRegisterId) {
        this.enterpriseTaxpayerRegisterId = enterpriseTaxpayerRegisterId;
    }

    public String getEnterpriseTaxpayerRegisterId() {
        return enterpriseTaxpayerRegisterId;
    }

    public void setEnterpriseUniversalCreditIdAgencyCode(String enterpriseUniversalCreditIdAgencyCode) {
        this.enterpriseUniversalCreditIdAgencyCode = enterpriseUniversalCreditIdAgencyCode;
    }

    public String getEnterpriseUniversalCreditIdAgencyCode() {
        return enterpriseUniversalCreditIdAgencyCode;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("enterpriseNameCh", getEnterpriseNameCh())
                .append("enterpriseId", getEnterpriseId())
                .append("enterpriseRegisterAddress", getEnterpriseRegisterAddress())
                .append("enterpriseCountryId", getEnterpriseCountryId())
                .append("enterprisePovince", getEnterprisePovince())
                .append("enterpriseCity", getEnterpriseCity())
                .append("enterpriseDistrict", getEnterpriseDistrict())
                .append("enterpriseBankAddress", getEnterpriseBankAddress())
                .append("enterpriseTel", getEnterpriseTel())
                .append("enterpriseBankName", getEnterpriseBankName())
                .append("enterpriseBankCountryId", getEnterpriseBankCountryId())
                .append("enterpriseBankProvince", getEnterpriseBankProvince())
                .append("enterpriseBankCity", getEnterpriseBankCity())
                .append("enterpriseBankDistrict", getEnterpriseBankDistrict())
                .append("enterpriseBankId", getEnterpriseBankId())
                .append("enterpriseOwner", getEnterpriseOwner())
                .append("enterpriseCapital", getEnterpriseCapital())
                .append("enterpriseTime", getEnterpriseTime())
                .append("enterpriseType", getEnterpriseType())
                .append("enterpriseIndustry", getEnterpriseIndustry())
                .append("enterpriseOirganization", getEnterpriseOirganization())
                .append("enterpriseRegisterAddressCity", getEnterpriseRegisterAddressCity())
                .append("enterpriseUsedName", getEnterpriseUsedName())
                .append("enterpriseNameEn", getEnterpriseNameEn())
                .append("enterpriseInsured", getEnterpriseInsured())
                .append("enterprisePeople", getEnterprisePeople())
                .append("enterpriseScope", getEnterpriseScope())
                .append("enterpriseValidDateStart", getEnterpriseValidDateStart())
                .append("enterpriseValidDateend", getEnterpriseValidDateend())
                .append("enterpriseEgisterAddressCity", getEnterpriseEgisterAddressCity())
                .append("enterpriseRegisterAddressDistrict", getEnterpriseRegisterAddressDistrict())
                .append("enterpriseRegisterAddressFormatAddress", getEnterpriseRegisterAddressFormatAddress())
                .append("enterpriseRegisterAddressGps", getEnterpriseRegisterAddressGps())
                .append("enterpriseRegisterAddressProvince", getEnterpriseRegisterAddressProvince())
                .append("enterpriseTaxpayerRegisterId", getEnterpriseTaxpayerRegisterId())
                .append("enterpriseUniversalCreditIdAgencyCode", getEnterpriseUniversalCreditIdAgencyCode())
                .toString();
    }
}
