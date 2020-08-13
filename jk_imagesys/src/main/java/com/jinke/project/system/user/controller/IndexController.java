package com.jinke.project.system.user.controller;

import com.jinke.framework.config.RuoYiConfig;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.project.system.menu.domain.Menu;
import com.jinke.project.system.menu.service.IMenuService;
import com.jinke.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 首页 业务处理
 *
 * @author jinke
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @Autowired
    private RuoYiConfig ruoYiConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        User user = getSysUser();
        User loginInfo = (User) SecurityUtils.getSubject().getPrincipal();
        if (loginInfo.getRoles().get(0).getRoleId() != 1) {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return "/system/login";
        }
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", ruoYiConfig.getCopyrightYear());
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", ruoYiConfig.getVersion());
        return "main";
    }
}
