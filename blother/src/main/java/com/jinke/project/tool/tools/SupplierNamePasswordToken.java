package com.jinke.project.tool.tools;

import org.apache.shiro.authc.UsernamePasswordToken;

public class SupplierNamePasswordToken extends UsernamePasswordToken {
    public SupplierNamePasswordToken(String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

//
//    private String loginType;
//
//    public SupplierNamePasswordToken() {}
//
//    public SuppliernamePasswordToken(final String supplierName, final String password,
//                                     final String loginType) {
//        super(supplierName, password);
//        this.loginType = loginType;
//    }
//
//    public String getLoginType() {
//        return loginType;
//    }
//
//    public void setLoginType(String loginType) {
//        this.loginType = loginType;
//    }
}
