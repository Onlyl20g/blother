package com.jinke.project.system.ncInteractivity.domain;

/**
 * 客户经理信息dto
 *
 * @author jinke
 */
public class CustomerManager {

    private String userId;
    private String userCode;
    private String name;
    private String updateTime;
    private String status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "userId='" + userId + '\'' +
                ", userCode='" + userCode + '\'' +
                ", name='" + name + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}