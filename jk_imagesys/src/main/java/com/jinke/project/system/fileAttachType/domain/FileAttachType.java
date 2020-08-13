package com.jinke.project.system.fileAttachType.domain;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 其他系统文件分类表 sys_file_attach_type
 *
 * @author jinke
 * @date 2019-11-14
 */
public class FileAttachType {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String sysType;
    /**
     *
     */
    private String code;
    /**
     *
     */
    private String infoName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getSysType() {
        return sysType;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoName() {
        return infoName;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sysType", getSysType())
                .append("code", getCode())
                .append("infoName", getInfoName())
                .toString();
    }
}
