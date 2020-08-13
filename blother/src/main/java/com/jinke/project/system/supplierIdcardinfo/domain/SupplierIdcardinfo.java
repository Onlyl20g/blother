package com.jinke.project.system.supplierIdcardinfo.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 供应商身份证表 sys_supplier_idcardinfo
 *
 * @author jinke
 * @date 2019-08-20
 */
public class SupplierIdcardinfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 供应商身份证信息表
     */
    private Long id;
    /**
     * 唯一编号
     */
    private String billId;
    /**
     *
     */
    private String supplierBillId;
    /**
     * 身份证上住址
     */
    private String address;
    /**
     * 民族
     */
    private String nationality;
    /**
     * 身份证号码
     */
    private String num;
    /**
     * 性别
     */
    private String sex;
    /**
     * 姓名
     */
    private String name;
    /**
     * 生日
     */
    private String birth;
    /**
     *
     */
    private String requestId;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getBirth() {
        return birth;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
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
                .append("address", getAddress())
                .append("nationality", getNationality())
                .append("num", getNum())
                .append("sex", getSex())
                .append("name", getName())
                .append("birth", getBirth())
                .append("requestId", getRequestId())
                .append("str1", getStr1())
                .append("str2", getStr2())
                .append("str3", getStr3())
                .append("str4", getStr4())
                .append("str5", getStr5())
                .toString();
    }
}
