package com.jinke.project.system.viewdataUpload.domain;

public enum ViewdataUploadSource {
    ITU("ITU", "内部上传"),
    IFC("IFC", "接口调用");

    private String value;
    private String name;

    ViewdataUploadSource(String value, String name) {
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
