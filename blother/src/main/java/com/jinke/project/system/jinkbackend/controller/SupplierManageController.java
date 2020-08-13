package com.jinke.project.system.jinkbackend.controller;

import com.jinke.framework.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("system/suppliermanage")
public class SupplierManageController extends BaseController {
    private String prefix = "system/suppliermanage";

    @RequiresPermissions("system:suppliermanage:view")
    @GetMapping
    public String custommanage() {
        return prefix + "/suppliermanage";
    }
}

