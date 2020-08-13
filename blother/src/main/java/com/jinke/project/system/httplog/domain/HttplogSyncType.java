package com.jinke.project.system.httplog.domain;

public enum HttplogSyncType {
    SYNC("sync", "同步"),
    ASYNC("async", "异步");

    private String value;
    private String name;

    HttplogSyncType(String value, String name) {
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
