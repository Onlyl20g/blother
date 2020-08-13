package com.jinke.common.constant;

import com.jinke.project.tool.tools.ConfigEntity;

public class WebConstants {
    public static final String SSO_BASE_URL = ConfigEntity.getSsoBaseUrl();
    public static final String SSO_CHECK_TOKEN = "/checktoken";
    public static final String SSO_INIT_CUST_INFO = "/initcustInfo";

    public static final String SSO_PUBLIC_KEY = ConfigEntity.getSsoPublicKey();
    public static final String SSO_PRIVATE_KEY = ConfigEntity.getSsoPrivateKey();
}
