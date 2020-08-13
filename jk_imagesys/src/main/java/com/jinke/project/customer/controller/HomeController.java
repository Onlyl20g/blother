package com.jinke.project.customer.controller;


import com.jinke.common.utils.ServletUtils;
import com.jinke.framework.shiro.realm.UserRealm;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录验证
 *
 * @author jinke
 */
@Controller
@RequestMapping("")
public class HomeController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserServiceImpl userServiceImpl;


    @GetMapping()
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        return "customer/index";
    }

    @GetMapping("/main")
    public String main(ModelMap mmap) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return "customer/index";
        }
        User user = (User) principal;
        String loginName = user.getLoginName();
        String updateBy = user.getUpdateBy();
        if (user.getSsoStatus() == 1) {
            return "customer/main";
        }
        if (!loginName.equals(updateBy)) {
            return "customer/index";
        }
        return "customer/main";
    }
}
