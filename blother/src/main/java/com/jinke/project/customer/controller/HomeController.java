package com.jinke.project.customer.controller;

import com.jinke.common.utils.ServletUtils;
import com.jinke.common.utils.StringUtils;
import com.jinke.framework.shiro.realm.UserRealm;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.supplier.domain.SupplierDto;
import com.jinke.project.system.supplier.service.ISupplierService;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.service.UserServiceImpl;
import com.jinke.project.tool.tools.SupplierNamePasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录验证
 *
 * @author jinke
 */
@Controller
@RequestMapping("")
public class HomeController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ISupplierService supplierService;

    @GetMapping()
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        // 如果是Ajax请求，返回Json字符串。
//        if (ServletUtils.isAjaxRequest(request)) {
//            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
//        }
        return "customer/index";
    }

    @PostMapping("login")
    @ResponseBody
    public AjaxResult ajaxLoginSupplier(String username, String password, boolean rememberMe) {
        SupplierNamePasswordToken token = new SupplierNamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        log.info("[POST]:login" + "\r\n" + "[params]:" + username + "\r\n" + password + "\r\n" + rememberMe);
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

    @GetMapping("/unauth")
    public String unauth() {
        return "error/unauth";
    }

    /**
     * 查询用户是否第一次登录
     */
    @PostMapping("/checkLoginNameFirst")
    @ResponseBody
    public Integer checkLoginNameFirst() {
        Supplier principal = (Supplier) SecurityUtils.getSubject().getPrincipal();
        if (principal.getUpdateBy() == null || principal.getUpdateBy().equals("") || !principal.getUpdateBy().equals(principal.getBusinessLicenseCode())) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @PostMapping("/updatePsw")
    @ResponseBody
    public Integer updatePsw(User user) {
        Integer id = userServiceImpl.resetUserPwd(user);
        return id;
    }

    /**
     * 供应商修改密码
     */
    @PostMapping("/updateSupPW")
    @ResponseBody
    public Integer updateSupPW(HttpServletRequest request) {
        Supplier sup = (Supplier) SecurityUtils.getSubject().getPrincipal();
        String password = request.getParameter("password");
        String oldpassword = request.getParameter("oldPassword");
        String businessLicenseCode = sup.getBusinessLicenseCode();
        String updateBy = sup.getBusinessLicenseCode();

        Supplier s1 = new Supplier();
        s1.setBusinessLicenseCode(businessLicenseCode);
        List<Supplier> suppliers = supplierService.selectSupplierList(s1);
        Supplier s2 = null;
        if (suppliers.size() > 0) {
            s2 = suppliers.get(0);
            if (s2.getPassword().equals(encryptPassword(businessLicenseCode, oldpassword, s2.getSalt()))) {
                s2.setPassword(encryptPassword(businessLicenseCode, password, s2.getSalt()));
                s2.setUpdateBy(updateBy);
                s2.setUpdateBy(s2.getBusinessLicenseCode());
                Subject subject = SecurityUtils.getSubject();
                PrincipalCollection principalCollection = subject.getPrincipals();
                String realmName = principalCollection.getRealmNames().iterator().next();
                PrincipalCollection newPrincipalCollection =
                        new SimplePrincipalCollection(s2, realmName);
                subject.runAs(newPrincipalCollection);


                return supplierService.updateSupplierNewPassWord(s2);
            } else {
                return 0;
            }
        }

        return null;
    }

    @PostMapping("/isSuplierLogin")
    @ResponseBody
    public Integer isSuplierLogin() {
        Supplier sup = (Supplier) SecurityUtils.getSubject().getPrincipal();
        if (sup != null) {
            return 1;
        } else {
            return 0;
        }
    }

    @PostMapping("/findUser")
    @ResponseBody
    public Map findUser() {
        Map<String, SupplierDto> map = new HashMap<String, SupplierDto>();
        Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        SupplierDto supplierDto = new SupplierDto();
        BeanUtils.copyProperties(supplier, supplierDto);
        if (supplier != null) {
            map.put("supplier", supplierDto);
        }
        return map;
    }

    public String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex().toString();
    }

}
