package com.jinke.project.system.user.controller;

import com.jinke.common.constant.UserConstants;
import com.jinke.common.utils.StringUtils;
import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.shiro.service.PasswordService;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.post.service.IPostService;
import com.jinke.project.system.role.domain.Role;
import com.jinke.project.system.role.service.IRoleService;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.service.IUserService;
import com.jinke.project.tool.tools.RandomPwd;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author jinke
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private String prefix = "system/user";

    @Value("${cunstommanageroleId}")
    private String cunstommanageroleId;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPostService postService;

    @Autowired
    private PasswordService passwordService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user() {
        return prefix + "/user";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(User user) {
        startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(User user) {
        List<User> list = userService.selectUserList(user);
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        List<User> userList = util.importExcel(file.getInputStream());
        String message = userService.importUser(userList, updateSupport);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<Role> roles = roleService.selectRoleAll();
        List<Role> roleList = new ArrayList<Role>();
        Long deptId = Long.valueOf(cunstommanageroleId);
        for (Role role : roles) {
            if (deptId != role.getRoleId()) {
                roleList.add(role);
            }
        }
        mmap.put("roles", roleList);
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName()))) {
            return error("保存用户'" + user.getLoginName() + "'失败，登录账号已存在");
        }
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(User user) {
        int result = userService.resetUserPwd(user);
        return toAjax(result);
    }

    @PostMapping("/resetPwdByUserId")
    @ResponseBody
    public Map resetPwdByUserId(User user) {
//        return toAjax(userService.resetUserPwd(user));
        String pwd = RandomPwd.generatePassword(8);
        User user1 = userService.selectUserById(user.getUserId());
        User user2 = (User) SecurityUtils.getSubject().getPrincipal();
        user.setLoginName(user1.getLoginName());
        user.setUpdateBy(user2.getLoginName());
        user.setPassword(pwd);
        int row = userService.resetUserPwd(user);
        Map<String, Object> map = new HashMap();
        if (row > 0) {
            passwordService.clearLoginRecordCache(user.getLoginName());
            map.put("code", "success");
            map.put("data", pwd);
        } else {
            map.put("code", "flag");
            map.put("data", "操作失败");
        }
        return map;
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(userService.deleteUserByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(User user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(User user) {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(User user) {
        return userService.checkEmailUnique(user);
    }

    /**
     * 用户状态修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(User user) {
        return toAjax(userService.changeStatus(user));
    }

    @PostMapping("/findUser")
    @ResponseBody
    public Map findUserByFirstLand(User user) {
        Map<String, Object> map = new HashMap();
        Object obj = SecurityUtils.getSubject().getPrincipal();
        if (obj instanceof User) {
            User principal = (User) obj;
            User u = userService.selectUserByFistLand(principal);
            map.put("data", u);
            return map;
        } else {
            return null;
        }
    }

    @GetMapping("/resetPwdBy/{userId}")
    public String resetPwdBy(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return "system/resetPwd";
    }

    @PostMapping("/resetPwdBy")
    @ResponseBody
    public AjaxResult resetPwdBy(User user) {
        return toAjax(userService.resetUserPwd(user));
    }

    @PostMapping("/resetPwdByLoginName")
    @ResponseBody
    public AjaxResult resetPwdByLoginName(User user) {
//        User user1 = userService.selectUserByLoginName(user.getLoginName());
        User user1 = (User) SecurityUtils.getSubject().getPrincipal();
        user.setUserId(user1.getUserId());
        user.setUpdateBy(user1.getLoginName());
        user.setSalt(user1.getSalt());
        int result = userService.resetUserPwd(user);
        return toAjax(result);
    }

    @PostMapping("/findUserRole")
    @ResponseBody
    public Map resetPwdByLoginName() {
        Map<String, Object> map = new HashedMap();
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        map.put("id", user.getRoles().get(0).getRoleId());
        return map;
    }

    @PostMapping("/getUserInfo")
    @ResponseBody
    public Map getUserInfo() {
        Map<String, Object> map = new HashedMap();
        Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        map.put("corprName", supplier.getCorprName());
        map.put("code", supplier.getBusinessLicenseCode());
        map.put("legalPersonName", supplier.getLegalPersonName());
        map.put("customerManagerName", supplier.getCustomerManagerName());
        map.put("bil", supplier.getBillId());
        return map;
    }
}