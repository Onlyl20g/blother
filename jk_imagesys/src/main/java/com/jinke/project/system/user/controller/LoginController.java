package com.jinke.project.system.user.controller;

import com.jinke.common.utils.ServletUtils;
import com.jinke.common.utils.StringUtils;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.project.monitor.online.domain.UserOnline;
import com.jinke.project.monitor.online.service.IUserOnlineService;
import com.jinke.project.system.role.domain.Role;
import com.jinke.project.system.role.service.IRoleService;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * 登录验证
 *
 * @author jinke
 */
@Controller
@RequestMapping("/system")
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserOnlineService iUserOnlineService;

    // 是否只能同时登录一个
    @Value("${shiro.session.maxSessionLogin}")
    private boolean maxSessionLogin;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if (user.getRoles() != null && user.getRoles().size() > 0 && user.getRoles().get(0).getRoleId() == 1) {
                return success();
            }
            subject.logout();
            return error("需要管理员权限");
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @PostMapping("/loginUser")
    @ResponseBody
    public AjaxResult ajaxLoginUser(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            if (maxSessionLogin) {
                UserOnline userOnline = new UserOnline();
                userOnline.setLoginName(username);
                List<UserOnline> userOnlines = iUserOnlineService.selectUserOnlineList(userOnline);
                Serializable id = subject.getSession().getId();
                if (userOnlines != null && !"".equals(userOnlines) && userOnlines.size() > 0) {
                    for (UserOnline userOnline1 : userOnlines) {
                        if (!id.toString().equals(userOnline1.getSessionId())) {
                            iUserOnlineService.forceLogout(userOnline1.getSessionId());
                        }
                    }
                }
            }
            return success();
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }


    @PostMapping("/isUserRole")
    @ResponseBody
    public Boolean isUserRole() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roles = iRoleService.selectRolesByUserLoginName(user.getLoginName());
        Boolean b = false;
        if (roles.size() != 0) {//判断是否是管理员
            for (Role role : roles) {
                if (role.getRoleId() == 1) {
                    b = true;
                }
            }
        }
        return b;
    }


    @PostMapping("/checkFirstLogin")
    @ResponseBody
    public Boolean checkFirstLogin() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        User user1 = userService.selectUserLoginName(user.getLoginName());
        Boolean result = false;
        if (user1.getLoginName().equals(user1.getUpdateBy())) {
            result = true;
        }
        return result;
    }

    @PostMapping("/updatePwd")
    @ResponseBody
    public Boolean updatePwd(HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        user.setUpdateBy(loginName);
        user.setPassword(password);
        int row = userService.resetUserPwd(user);
        Boolean result = false;
        if (row > 0) {
            result = true;
        }
        return result;
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "/error/unauth";
    }
}
