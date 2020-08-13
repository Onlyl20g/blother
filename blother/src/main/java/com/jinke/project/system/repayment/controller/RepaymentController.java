package com.jinke.project.system.repayment.controller;

import com.jinke.framework.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("repayment")
public class RepaymentController extends BaseController {

    @GetMapping()
    public String repayment() {
        return "customer/repayment/repayment";
    }
}
