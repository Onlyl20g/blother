package com.jinke.project.customer.controller;

import com.jinke.framework.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注册
 */
@Controller
public class RegisterController extends BaseController {
    @GetMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response) {
        return "customer/register";
    }
}
