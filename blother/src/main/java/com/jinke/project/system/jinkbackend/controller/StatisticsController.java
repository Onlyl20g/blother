package com.jinke.project.system.jinkbackend.controller;

import com.jinke.framework.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("system/statistics")
public class StatisticsController extends BaseController {
    private String prefix = "system/statistics";

    @RequiresPermissions("system:statistics:view")
    @GetMapping
    public String statistics() {
        return prefix + "/statistics";
    }

}
