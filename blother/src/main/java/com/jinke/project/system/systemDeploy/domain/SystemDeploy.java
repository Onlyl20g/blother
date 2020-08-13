package com.jinke.project.system.systemDeploy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 系统标识表 sys_system_deploy
 *
 * @author jinke
 * @date 2019-12-04
 */
public class SystemDeploy {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 配置key值
     */
    private String sysKey;
    /**
     * 值
     */
    private String value;
    /**
     * 状态
     */
    private String state;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sysKey", getSysKey())
                .append("value", getValue())
                .append("state", getState())
                .toString();
    }
}
