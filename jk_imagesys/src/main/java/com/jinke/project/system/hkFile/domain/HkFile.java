package com.jinke.project.system.hkFile.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * 获客系统上传文件表 sys_hk_file
 *
 * @author jinke
 * @date 2019-10-16
 */
public class HkFile {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;
    /**
     * 所属单据id
     */
    private String billId;
    /**
     * 文件全名称
     */
    private String fileFullName;
    /**
     * 文件在fast服务器中的path
     */
    private String filePath;
    /**
     * 所属业务类型（授信，提款）
     */
    private String subordinate;
    /**
     * 所属业务模块名称
     */
    private String code;
    /**
     * 状态
     */
    private String state;
    /**
     * 创建时间
     */
    private Date creditTime;
    /**
     * 预留1
     */
    private String str1;
    /**
     * 预留2
     */
    private String str2;
    /**
     *
     */
    private String userName;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillId() {
        return billId;
    }

    public void setFileFullName(String fileFullName) {
        this.fileFullName = fileFullName;
    }

    public String getFileFullName() {
        return fileFullName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setSubordinate(String subordinate) {
        this.subordinate = subordinate;
    }

    public String getSubordinate() {
        return subordinate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCreditTime(Date creditTime) {
        this.creditTime = creditTime;
    }

    public Date getCreditTime() {
        return creditTime;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("billId", getBillId())
                .append("fileFullName", getFileFullName())
                .append("filePath", getFilePath())
                .append("subordinate", getSubordinate())
                .append("code", getCode())
                .append("state", getState())
                .append("creditTime", getCreditTime())
                .append("str1", getStr1())
                .append("str2", getStr2())
                .append("userName", getUserName())
                .toString();
    }
}
