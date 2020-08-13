package com.jinke.project.tool.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigEntity {

    private static String tempFilePath;

    private static String aliappCode;

    private static String aliApiHost;

    private static String aliApiPath;

    private static String aliIdCardApiHost;

    private static String aliIdCardApiPath;

    private static String tess4j;

    private static String ssoBaseUrl;

    private static String ssoPublicKey;

    private static String ssoPrivateKey;

    private static String host;

    public static String getTempFilePath() {
        return tempFilePath;
    }

    @Value("${config.tempfilepath}")
    public void setTempFilePath(String tempFilePath) {
        ConfigEntity.tempFilePath = tempFilePath;
    }

    public static String getAliappCode() {
        return aliappCode;
    }

    @Value("${config.aliappCode}")
    public void setAliappCode(String aliappCode) {
        ConfigEntity.aliappCode = aliappCode;
    }

    public static String getAliApiHost() {
        return aliApiHost;
    }

    @Value("${config.aliApiHost}")
    public void setAliApiHost(String aliApiHost) {
        ConfigEntity.aliApiHost = aliApiHost;
    }

    public static String getAliApiPath() {
        return aliApiPath;
    }

    @Value("${config.aliApiPath}")
    public void setAliApiPath(String aliApiPath) {
        ConfigEntity.aliApiPath = aliApiPath;
    }

    public static String getAliIdCardApiHost() {
        return aliIdCardApiHost;
    }

    @Value("${config.aliIdCardApiHost}")
    public void setAliIdCardApiHost(String aliIdCardApiHost) {
        ConfigEntity.aliIdCardApiHost = aliIdCardApiHost;
    }

    public static String getAliIdCardApiPath() {
        return aliIdCardApiPath;
    }

    @Value("${config.aliIdCardApiPath}")
    public void setAliIdCardApiPath(String aliIdCardApiPath) {
        ConfigEntity.aliIdCardApiPath = aliIdCardApiPath;
    }

    public static String getTess4j() {
        return tess4j;
    }

    @Value("${config.tess4j}")
    public void setTess4j(String tess4j) {
        ConfigEntity.tess4j = tess4j;
    }

    public static String getSsoBaseUrl() {
        return ssoBaseUrl;
    }

    @Value("${config.ssoBaseUrl}")
    public void setSsoBaseUrl(String ssoBaseUrl) {
        ConfigEntity.ssoBaseUrl = ssoBaseUrl;
    }

    public static String getSsoPublicKey() {
        return ssoPublicKey;
    }

    @Value("${config.ssoPublicKey}")
    public void setSsoPublicKey(String ssoPublicKey) {
        ConfigEntity.ssoPublicKey = ssoPublicKey;
    }

    public static String getSsoPrivateKey() {
        return ssoPrivateKey;
    }

    @Value("${config.ssoPrivateKey}")
    public void setSsoPrivateKey(String ssoPrivateKey) {
        ConfigEntity.ssoPrivateKey = ssoPrivateKey;
    }

    public static String getHost() {
        return host;
    }

    @Value("${config.host}")
    public void setHost(String host) {
        ConfigEntity.host = host;
    }
}
