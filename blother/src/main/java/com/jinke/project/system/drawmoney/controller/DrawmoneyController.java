package com.jinke.project.system.drawmoney.controller;

import com.jinke.framework.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("system/drawmoney")
public class DrawmoneyController extends BaseController {
    private String prefix = "system/drawmoney";

    @RequiresPermissions("system:drawmoney:view")
    @GetMapping()
    public String drawmoney() {
        return prefix + "/drawmoney";
    }
}
