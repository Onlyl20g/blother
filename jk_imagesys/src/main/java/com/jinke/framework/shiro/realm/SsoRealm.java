package com.jinke.framework.shiro.realm;

import com.jinke.common.constant.ShiroConstants;
import com.jinke.common.exception.user.*;
import com.jinke.framework.shiro.service.LoginService;
import com.jinke.framework.shiro.token.ImageSysUsernamePasswordToken;
import com.jinke.project.system.user.domain.User;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SsoRealm extends UserRealm {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginService loginService;

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof ImageSysUsernamePasswordToken)) {
            return super.doGetAuthenticationInfo(token);
        }

        ImageSysUsernamePasswordToken upToken = (ImageSysUsernamePasswordToken) token;
        if (upToken.getTokenType() == ShiroConstants.ImageSysTokenType.DEFAULT) {
            return super.doGetAuthenticationInfo(token);
        }

        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

        User user;
        try {
            user = loginService.loginSso(username);
        } catch (CaptchaException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (RoleBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            logger.info("对用户[" + username + "]进行登录验证(SSO)..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }

        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
