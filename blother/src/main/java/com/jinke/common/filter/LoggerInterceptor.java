package com.jinke.common.filter;

import com.jinke.framework.shiro.web.filter.LogoutFilter;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private LogoutFilter logoutFilter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpServletRequest req = (HttpServletRequest) request;
        String method = req.getRequestURI();
        String params = request.getQueryString();
        log.info("[POST]:" + method + "\t" + "[params]:" + params);

        Object principal = SecurityUtils.getSubject().getPrincipal();
        boolean result = SecurityUtils.getSubject().getPrincipal() instanceof User;
        if (result) {
            User user = (User) principal;
            if (method.equals("/system/user/findUser") || method.equals("/system/user/resetPwdByLoginName")) {
                return true;
            }
            if (!method.equals("/system/login")) {
                if (user != null) {
                    if (user.getUpdateBy() == null || !user.getLoginName().equals(user.getUpdateBy())) {
                        WebUtils.issueRedirect(request, response, "/system/login");
                        return false;
                    }
                }
            }
        }
        boolean supResult = SecurityUtils.getSubject().getPrincipal() instanceof Supplier;
        if (supResult) {
            Supplier supplier = (Supplier) principal;
            if (method.equals("/checkLoginNameFirst") || method.equals("/") || method.equals("/isSuplierLogin") || method.equals("/findUser") || method.equals("/updateSupPW") || method.equals("/system/user/getUserInfo")) {
                    return true;
            }
            if (!method.equals("/login")) {
                if (supplier.getUpdateBy() == null || !supplier.getBusinessLicenseCode().equals(supplier.getUpdateBy())) {
                    WebUtils.issueRedirect(request, response, "/");
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}