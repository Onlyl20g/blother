package com.jinke.project.system.usercenter.controller;

import com.jinke.framework.web.controller.BaseController;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.supplier.service.ISupplierService;
import com.jinke.project.system.user.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("usercenter")
public class UsercenterController extends BaseController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ISupplierService iSupplierService;

    @GetMapping()
    public String UserCenter() {
        Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        if (supplier.getUpdateBy() == null || !supplier.getBusinessLicenseCode().equals(supplier.getUpdateBy())) {
            return "customer/index";
        }
        return "customer/usercenter/usercenter";
    }


}
