package com.jinke.project.system.usercenter.controller;

import com.jinke.common.constant.Constants;
import com.jinke.common.utils.ServletUtils;
import com.jinke.common.utils.StringUtils;
import com.jinke.project.tool.sso.SsoUtil;
import com.jinke.project.tool.sso.bean.JinkResult;
import com.jinke.project.tool.sso.bean.SsoCheckTokenResultBean;
import com.jinke.project.tool.tools.UserSsoToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SsoController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/sso")
    public String ssoLogin(ModelMap mmap) {
        HttpServletRequest request = ServletUtils.getRequest();

        String custId = request.getParameter("cust_id");
        String accessToken = request.getParameter("access_token");

        if (StringUtils.isEmpty(custId) || StringUtils.isEmpty(accessToken)) {
            return "error/500";
        }
        // 验证sso登录权限
        JinkResult<SsoCheckTokenResultBean> result = SsoUtil.checkToken(custId, accessToken);
        if (result != null && Constants.FLAG_SUCCESS.equals(result.getFlag())) {
            UserSsoToken token = new UserSsoToken(result.getResult().getTarget_cust_id());
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                ServletUtils.getSession().setAttribute("loginType", Constants.LOGIN_TYPE_SSO);
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/system/login";
            }
        } else {
            return "redirect:/system/login";
        }

        String target = request.getParameter("target");
        if (StringUtils.isEmpty(target)) {
            target = Constants.SSO_TARGET_USER_LOGIN; // 默认行为设置为用户登录
        }
        // 判断目标模块, 后续有更多模块时, 修改为switch实现
//        if (Constants.SSO_TARGET_APPLY_ATTACH.equalsIgnoreCase(target)) {
//            return applyAttachList(request);
//        }

        // 默认行为为用户登录
        return "redirect:/system";
    }
}
