package com.jinke.project.system.httplog.domain;

public enum HttplogStatus {
    SUCCESS("success", "成功"),
    UNKNOWN("unknown", "未知"),
    FAILED("failed", "失败");

    private String value;
    private String name;

    HttplogStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
