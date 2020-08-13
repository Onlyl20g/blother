package com.jinke.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Value("${ssobaseurl}")
    private String ssoBaseUrl;
    @Value("${ssopublickey}")
    private String ssoPublicKey;
    @Value("${ssoprivatekey}")
    private String ssoPrivateKey;
    private String ssoCheckToken = "/checktoken";
    private String ssoInitCustInfo = "/initcustInfo";

    public String getSsoBaseUrl() {
        return ssoBaseUrl;
    }

    public String getSsoCheckToken() {
        return ssoCheckToken;
    }

    public String getSsoInitCustInfo() {
        return ssoInitCustInfo;
    }

    public String getSsoPublicKey() {
        return ssoPublicKey;
    }

    public String getSsoPrivateKey() {
        return ssoPrivateKey;
    }
}
