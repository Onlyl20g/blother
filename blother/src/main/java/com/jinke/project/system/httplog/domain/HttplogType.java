package com.jinke.project.system.httplog.domain;

public enum HttplogType {
    BUSINESS_LICENSE_RECON("business_license_recon", "营业执照识别"),
    IDCARD_RECON("idcard_recon", "身份证识别");

    private String value;
    private String name;

    HttplogType(String value, String name) {
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
