package com.jinke.project.system.user.controller;

import com.jinke.common.utils.ServletUtils;
import com.jinke.common.utils.StringUtils;
import com.jinke.framework.config.RuoYiConfig;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.project.system.menu.domain.Menu;
import com.jinke.project.system.menu.service.IMenuService;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 首页 业务处理
 *
 * @author jinke
 */
@Controller
@RequestMapping("/system")
public class IndexController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @Autowired
    private RuoYiConfig ruoYiConfig;

    @Autowired
    private IUserService iUserService;

    // 系统首页
    @GetMapping()
    public String index(ModelMap mmap) {
        boolean tag = SecurityUtils.getSubject().getPrincipal() instanceof User;
        if (!tag) {
            return "/";
        }
        // 取身份信息
        User user = getSysUser();
//        User user1 = iUserService.selectUserById(user.getUserId());
       /* if (user.getUpdateBy() == null || !user.getLoginName().equals(user.getUpdateBy())) {
            return "system/login";
        }*/
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);

        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", ruoYiConfig.getCopyrightYear());
        mmap.put("demoEnabled", ruoYiConfig.isDemoEnabled());
        return "system/index";
    }

    @PostMapping("login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return success();
        } catch (AuthenticationException e) {
            String msg = "用户名或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        return "system/login";
    }
}
