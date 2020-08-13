package com.jinke.framework.shiro.token;

import com.jinke.common.constant.ShiroConstants;
import org.apache.shiro.authc.UsernamePasswordToken;

public class ImageSysUsernamePasswordToken extends UsernamePasswordToken {
    private ShiroConstants.ImageSysTokenType tokenType = ShiroConstants.ImageSysTokenType.DEFAULT;

    public ImageSysUsernamePasswordToken(UsernamePasswordToken token, ShiroConstants.ImageSysTokenType tokenType) {
        super(token.getUsername(), token.getPassword(), token.isRememberMe(), token.getHost());
        this.tokenType = tokenType;
    }

    public ImageSysUsernamePasswordToken(String username, String password) {
        super(username, password);
    }

    public ImageSysUsernamePasswordToken(String username, String password, boolean rememberMe, ShiroConstants.ImageSysTokenType tokenType) {
        super(username, password, rememberMe);
        this.tokenType = tokenType;
    }

    public ShiroConstants.ImageSysTokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(ShiroConstants.ImageSysTokenType tokenType) {
        this.tokenType = tokenType;
    }
}
