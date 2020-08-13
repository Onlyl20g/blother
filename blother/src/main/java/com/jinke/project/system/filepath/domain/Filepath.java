package com.jinke.project.system.filepath.domain;

import com.jinke.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件路径表 sys_filepath
 *
 * @author jinke
 * @date 2019-07-14
 */
public class Filepath extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String filepathId;
    /**
     *
     */
    private String urlBillid;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private String formName;
    /**
     *
     */
    private String operatorName;
    /**
     *
     */
    private String status;
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

    public void setFilepathId(String filepathId) {
        this.filepathId = filepathId;
    }

    public String getFilepathId() {
        return filepathId;
    }

    public void setUrlBillid(String urlBillid) {
        this.urlBillid = urlBillid;
    }

    public String getUrlBillid() {
        return urlBillid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormName() {
        return formName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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
                .append("filepathId", getFilepathId())
                .append("urlBillid", getUrlBillid())
                .append("type", getType())
                .append("formName", getFormName())
                .append("operatorName", getOperatorName())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("str1", getStr1())
                .append("str2", getStr2())
                .append("str3", getStr3())
                .append("str4", getStr4())
                .append("str5", getStr5())
                .toString();
    }
}
