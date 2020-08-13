package com.jinke.project.customer.controller;

import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ContentController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/customer/pages/myfiles")
    public String myfiles() {
        return "customer/pages/myfiles/myfiles";
    }

    @GetMapping("/customer/pages/ocrdistinguish")
    public String ocrdistinguish() {
        return "customer/pages/ocrdistinguish/choose";
    }

    @GetMapping("/customer/pages/ocrinvoice")
    public String ocrinvoice() {
        return "customer/pages/ocrdistinguish/ocrinvoice";
    }

    @GetMapping("/customer/pages/viewdataupload")
    public String viewdataupload() {
        return "customer/pages/viewdataupload/viewdataupload";
    }

    @GetMapping("/customer/pages/viewdatascan")
    public String viewdatascan() {
        return "customer/pages/viewdatascan/choose";
    }

    @GetMapping("/customer/pages/viewdatamatching")
    public String viewdatamatching() {
        return "customer/pages/viewdatamatching/qrcodeupload";
    }

    @GetMapping("/customer/pages/ocrdistinguishother")
    public String ocrdistinguishother() {
        return "customer/pages/ocrdistinguish/ocrimageword";
    }

    @GetMapping("/customer/pages/onlinedemoscan")
    public String onlinedemoscan() {
        return "customer/pages/viewdatascan/onlinedemoscan";
    }

    @GetMapping("/customer/sharefiles")
    public String sharefiles() {
        return "customer/sharefiles";
    }

    @GetMapping("/customer/qrupload")
    public String sanupload() {
        return "customer/qrupload";
    }

    @GetMapping("/customer/pages/hkapplicationfile")
    public String hkapplicationfile() {
        return "customer/pages/hkapplicationfile/hkapplicationfile";
    }

    @GetMapping("customer/pages/viewdataupload/uploadFile")
    public String uploadFile() {
        return "customer/uploadFile";
    }

    @GetMapping("customer/uploadsysfile")
    public String uploadSysFile() {
        return "customer/uploadsysfile";
    }

    @GetMapping("customer/uploadsysfilebyqcr")
    public String uploadSysFile(@RequestParam("i") String billId, @RequestParam("s") String sysType, @RequestParam("c") String code) {
        return "customer/uploadsysfilebyqcr";
    }

    @GetMapping("/customer/pages/hkcredit")
    public String hkcredit(@RequestParam("s") String billId, ModelMap mmap) {
        mmap.put("billId", billId);
        return "customer/hkcredit";
    }

    @GetMapping("/customer/pages/hkwithdraw")
    public String hkwithdraw(@RequestParam("s") String billId, ModelMap mmap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object optType = session.getAttribute("optType");
        if (optType == null || "1".equals(optType)) {//只读
            mmap.put("readState", false);
            mmap.put("insertState", false);
        } else if ("2".equals(optType)) {//可以新增
            mmap.put("readState", false);
            mmap.put("insertState", true);
        } else {//不受限制
            mmap.put("readState", true);
            mmap.put("insertState", true);
        }

        mmap.put("billId", billId);
        Object system = session.getAttribute("system");
//        system = "factoring";
        mmap.put("system", system);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Object subject = session.getAttribute("subject");
        mmap.put("subject", subject);
        if (user == null) {
            return "customer/index";
        }
        user.setSsoStatus(1);
        iUserService.updateUser(user);
        return "customer/hkwithdraw";
    }

    @GetMapping("/customer/pages/onlinefile")
    public String onlinefile(ModelMap mmap) {
        return "customer/onlinefile";
    }

    @GetMapping("/customer/pages/online")
    public String online(ModelMap mmap) {
        return "customer/online";
    }
}