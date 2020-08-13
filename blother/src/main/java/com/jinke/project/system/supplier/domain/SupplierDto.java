package com.jinke.project.system.supplier.domain;

import com.jinke.project.system.enterprise.domain.Enterprise;
import com.jinke.project.system.supplierIdcardinfo.domain.SupplierIdcardinfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.Date;


/**
 * 供应商Dto
 *
 * @author jinke
 * @date 2019-07-25
 */
public class SupplierDto {
    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String billId;
    /**
     * 营业执照
     */
    private String businessLicenseFilepathId;
    /**
     *
     */
    private String businessLicenseCode;
    /**
     *
     */
    private String corprName;
    /**
     * 企业类型
     */
    private String corprType;
    /**
     * 企业通讯地址
     */
    private String corprAddres;
    /**
     * 注册资本
     */
    private String registeredCapital;
    /**
     * 成立日期
     */
    private String establishDate;
    /**
     * 营业期限
     */
    private String operationPeriod;
    /**
     * 客户经理编号
     */
    private String customerManagerId;
    /**
     * 客户经理姓名
     */
    private String customerManagerName;
    /**
     * 法人身份证扫描件（正面）
     */
    private String idcardFrontFilepathId;
    /**
     * 法人身份证扫描件（反面）
     */
    private String idcardBackFilepathId;
    /**
     * 法人姓名
     */
    private String legalPersonName;
    /**
     * 法人身份证号
     */
    private String legalPersonIdno;
    /**
     * 法人手机号
     */
    private String legalPersonPhone;
    /**
     * 操作U盾使用人姓名
     */
    private String operatorName;
    /**
     * 操作U盾使用人身份证号
     */
    private String operatorIdno;
    /**
     * 操作U盾使用人手机号
     */
    private String operatorPhone;
    /**
     * 审批U盾使用人姓名
     */
    private String auditOperatorName;
    /**
     * 审批U盾使用人身份证号
     */
    private String auditOperatorIdno;
    /**
     * 审批U盾使用人手机号
     */
    private String auditOpetatorPhone;
    /**
     * U盾申请及电子签章申请表
     */
    private String usbApplyPath;
    /**
     * 数字证书申请表
     */
    private String usbKeyPath;
    /**
     * 数字证书服务协议
     */
    private String agreementPath;

    /**
     * 营业执照类
     */
    private Enterprise enterprise;
    /**
     *
     */
    private SupplierIdcardinfo supplierIdcardinfo;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 0启用，1禁用
     */
    private String status;
    /**
     * 头像路径
     */
    private String avatar;
    /**
     * 0删除状态，1未删除
     */
    private String delFlag;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private Date updateBy;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Date updateBy) {
        this.updateBy = updateBy;
    }

    public SupplierIdcardinfo getSupplierIdcardinfo() {
        return supplierIdcardinfo;
    }

    public void setSupplierIdcardinfo(SupplierIdcardinfo supplierIdcardinfo) {
        this.supplierIdcardinfo = supplierIdcardinfo;
    }


    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBusinessLicenseFilepathId() {
        return businessLicenseFilepathId;
    }

    public String getCustomerManagerId() {
        return customerManagerId;
    }

    public void setCustomerManagerId(String customerManagerId) {
        this.customerManagerId = customerManagerId;
    }

    public void setBusinessLicenseFilepathId(String businessLicenseFilepathId) {
        this.businessLicenseFilepathId = businessLicenseFilepathId;
    }

    public String getBusinessLicenseCode() {
        return businessLicenseCode;
    }

    public void setBusinessLicenseCode(String businessLicenseCode) {
        this.businessLicenseCode = businessLicenseCode;
    }

    public String getCorprName() {
        return corprName;
    }

    public void setCorprName(String corprName) {
        this.corprName = corprName;
    }

    public String getCorprType() {
        return corprType;
    }

    public void setCorprType(String corprType) {
        this.corprType = corprType;
    }

    public String getCorprAddres() {
        return corprAddres;
    }

    public void setCorprAddres(String corprAddres) {
        this.corprAddres = corprAddres;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    public String getOperationPeriod() {
        return operationPeriod;
    }

    public void setOperationPeriod(String operationPeriod) {
        this.operationPeriod = operationPeriod;
    }

    public String getCustomerManagerName() {
        return customerManagerName;
    }

    public void setCustomerManagerName(String customerManagerName) {
        this.customerManagerName = customerManagerName;
    }

    public String getIdcardFrontFilepathId() {
        return idcardFrontFilepathId;
    }

    public void setIdcardFrontFilepathId(String idcardFrontFilepathId) {
        this.idcardFrontFilepathId = idcardFrontFilepathId;
    }

    public String getIdcardBackFilepathId() {
        return idcardBackFilepathId;
    }

    public void setIdcardBackFilepathId(String idcardBackFilepathId) {
        this.idcardBackFilepathId = idcardBackFilepathId;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonIdno() {
        return legalPersonIdno;
    }

    public void setLegalPersonIdno(String legalPersonIdno) {
        this.legalPersonIdno = legalPersonIdno;
    }

    public String getLegalPersonPhone() {
        return legalPersonPhone;
    }

    public void setLegalPersonPhone(String legalPersonPhone) {
        this.legalPersonPhone = legalPersonPhone;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorIdno() {
        return operatorIdno;
    }

    public void setOperatorIdno(String operatorIdno) {
        this.operatorIdno = operatorIdno;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getAuditOperatorName() {
        return auditOperatorName;
    }

    public void setAuditOperatorName(String auditOperatorName) {
        this.auditOperatorName = auditOperatorName;
    }

    public String getAuditOperatorIdno() {
        return auditOperatorIdno;
    }

    public void setAuditOperatorIdno(String auditOperatorIdno) {
        this.auditOperatorIdno = auditOperatorIdno;
    }

    public String getAuditOpetatorPhone() {
        return auditOpetatorPhone;
    }

    public void setAuditOpetatorPhone(String auditOpetatorPhone) {
        this.auditOpetatorPhone = auditOpetatorPhone;
    }

    public String getUsbApplyPath() {
        return usbApplyPath;
    }

    public void setUsbApplyPath(String usbApplyPath) {
        this.usbApplyPath = usbApplyPath;
    }

    public String getUsbKeyPath() {
        return usbKeyPath;
    }

    public void setUsbKeyPath(String usbKeyPath) {
        this.usbKeyPath = usbKeyPath;
    }

    public String getAgreementPath() {
        return agreementPath;
    }

    public void setAgreementPath(String agreementPath) {
        this.agreementPath = agreementPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 生成随机盐
     */
    public void randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        setSalt(hex);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("billId", getBillId())
                .append("businessLicenseFilepathId", getBusinessLicenseFilepathId())
                .append("businessLicenseCode", getBusinessLicenseCode())
                .append("corprName", getCorprName())
                .append("corprType", getCorprType())
                .append("corprAddres", getCorprAddres())
                .append("registeredCapital", getRegisteredCapital())
                .append("establishDate", getEstablishDate())
                .append("operationPeriod", getOperationPeriod())
                .append("customerManagerId", getCustomerManagerId())
                .append("customerManagerName", getCustomerManagerName())
                .append("idcardFrontFilepathId", getIdcardFrontFilepathId())
                .append("idcardBackFilepathId", getIdcardBackFilepathId())
                .append("legalPersonName", getLegalPersonName())
                .append("legalPersonIdno", getLegalPersonIdno())
                .append("legalPersonPhone", getLegalPersonPhone())
                .append("operatorName", getOperatorName())
                .append("operatorIdno", getOperatorIdno())
                .append("operatorPhone", getOperatorPhone())
                .append("auditOperatorName", getAuditOperatorName())
                .append("auditOperatorIdno", getAuditOperatorIdno())
                .append("auditOpetatorPhone", getAuditOpetatorPhone())
                .append("usbApplyPath", getUsbApplyPath())
                .append("usbKeyPath", getUsbKeyPath())
                .append("agreementPath", getAgreementPath())
                .append("password", getPassword())
                .append("salt", getSalt())
                .append("status", getStatus())
                .append("avatar", getAvatar())
                .append("delFlag", getDelFlag())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }

}