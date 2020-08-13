package com.jinke.project.system.fileTemp.domain;

public enum FileTempStatus {
    EFF("EFF", "有效"),
    INV("INV", "无效");

    private String value;
    private String name;

    FileTempStatus(String value, String name) {
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
