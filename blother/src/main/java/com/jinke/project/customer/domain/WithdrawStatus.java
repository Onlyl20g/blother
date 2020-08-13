package com.jinke.project.customer.domain;

public enum WithdrawStatus {
    AUDITING("AUDITING", "审核中"),
    REFUSAL("REFUSAL", "驳回"),
    REJECTION("REJECTION", "拒绝"),
    AUDITED("AUDITED", "通过");

    private String value;
    private String name;

    WithdrawStatus(String value, String name) {
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
