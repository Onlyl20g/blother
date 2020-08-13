package com.jinke.framework.shiro.authenticator;

import com.jinke.common.constant.ShiroConstants;
import com.jinke.framework.shiro.realm.SsoRealm;
import com.jinke.framework.shiro.realm.UserRealm;
import com.jinke.framework.shiro.token.ImageSysUsernamePasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

public class ImageSysModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();

        ImageSysUsernamePasswordToken token;
        // 兼容未使用自定义Token的场景, 默认按后台用户验证
        if (!(authenticationToken instanceof ImageSysUsernamePasswordToken)) {
            token = new ImageSysUsernamePasswordToken((UsernamePasswordToken) authenticationToken, ShiroConstants.ImageSysTokenType.DEFAULT);
        } else {
            // 强制转换回自定义的CustomizedToken
            token = (ImageSysUsernamePasswordToken) authenticationToken;
        }

        // 登录类型
        ShiroConstants.ImageSysTokenType tokenType = token.getTokenType();

        Class realmClass;

        // 根据token类型, 设置realm类型, 后续用这个类型做匹配
        switch (tokenType) {
            case DEFAULT:
                realmClass = UserRealm.class;
                break;
            case SSO:
                realmClass = SsoRealm.class;
                break;
            default:
                return null;
        }

        // 所有Realm
        Collection<Realm> realms = getRealms();
        for (Realm realm : realms) {
            if (realm.getClass() == realmClass) {
                return doSingleRealmAuthentication(realm, token);
            }
        }
        return null;
    }
}
