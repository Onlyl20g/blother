package com.jinke.project.tool.tools;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserSsoToken extends UsernamePasswordToken {
    public UserSsoToken(String username) {
        super(username, "", false);
    }
}
