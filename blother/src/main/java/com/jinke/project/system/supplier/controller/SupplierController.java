package com.jinke.project.system.supplier.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.shiro.service.PasswordService;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.enterprise.domain.Enterprise;
import com.jinke.project.system.enterprise.service.IEnterpriseService;
import com.jinke.project.system.filepath.domain.Filepath;
import com.jinke.project.system.filepath.service.FilepathServiceImpl;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.supplier.domain.SupplierDto;
import com.jinke.project.system.supplier.service.ISupplierService;
import com.jinke.project.system.supplierIdcardinfo.domain.SupplierIdcardinfo;
import com.jinke.project.system.supplierIdcardinfo.service.ISupplierIdcardinfoService;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.service.IUserService;
import com.jinke.project.tool.fastdfs.FastDFSClient;
import com.jinke.project.tool.tools.RandomPwd;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 供应商信息操作处理
 *
 * @author jinke
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/supplier")
public class SupplierController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(SupplierController.class);

    private String prefix = "system/supplier";

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private FilepathServiceImpl filepathServiceImpl;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private IEnterpriseService iEnterpriseService;

    @Autowired
    private ISupplierIdcardinfoService iSupplierIdcardinfoService;

    @RequiresPermissions("system:supplier:view")
    @GetMapping()
    public String supplier() {
        return prefix + "/supplier";
    }

    /**
     * 查询供应商列表
     */
    @RequiresPermissions("system:supplier:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Supplier supplier) {
        startPage();
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        log.info("[POST]:/system/supplier/list" + "\r\n" + "[params]:" + supplier + "\r\n [result]:" + list);
        return getDataTable(list);
    }

    /**
     * 查询供应商列表详细信息
     */
    @RequiresPermissions("system:supplier:listInfo")
    @PostMapping("/listInfo")
    @ResponseBody
    public TableDataInfo listInfo() {
        startPage();
        Supplier supplier = new Supplier();
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        supplier.setCustomerManagerName(principal.getLoginName());
        List<SupplierDto> list = supplierService.selectSupplierListInfo(supplier);
        log.info("[POST]:/system/supplier/listInfo" + "\r\n" + "[params]:" + supplier + "\r\n [result]:" + list);
        return getDataTable(list);
    }

    /**
     * 导出供应商列表
     */
    @RequiresPermissions("system:supplier:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Supplier supplier) {
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        return util.exportExcel(list, "supplier");
    }

    /**
     * 新增供应商
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存供应商
     */
    @RequiresPermissions("system:supplier:add")
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Supplier supplier) {
        int list = supplierService.insertSupplier(supplier);
        log.info("[POST]:/system/supplier/add" + "\r\n" + "[params]:" + supplier + "\r\n [result]:" + list);
        return toAjax(list);
    }

    /**
     * 展现图片
     */
    @GetMapping("/show")
    public String show() {
        return prefix + "/show";
    }

    /**
     * 修改供应商
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Supplier supplier = supplierService.selectSupplierById(id);
        String fileId = supplier.getBusinessLicenseFilepathId();
        String cardId = supplier.getIdcardFrontFilepathId();
        String cardBack = supplier.getIdcardBackFilepathId();
        Filepath filepathBus = new Filepath();//查找营业执照
        Filepath filepathCode = new Filepath();//查找身份证人头面
        Filepath filepathBack = new Filepath();//查找身份证人国徽面
        // 查询下载对应路径
        filepathBus.setFilepathId(fileId);
        filepathCode.setFilepathId(cardId);
        filepathBack.setFilepathId(cardBack);
        // 获取下载路径
        List<Filepath> filepaths = filepathServiceImpl.selectFilepathList(filepathBus);
        List<Filepath> filepaths1 = filepathServiceImpl.selectFilepathList(filepathCode);
        List<Filepath> filepaths2 = filepathServiceImpl.selectFilepathList(filepathBack);
        String businessLicenseFilepathId = null;
        String idcardFrontFilepathId = null;
        String idcardBackFilepathId = null;
        if (filepaths.size() != 0) {
            businessLicenseFilepathId = filepaths.get(0).getUrlBillid();
            supplier.setBusinessLicenseFilepathId(businessLicenseFilepathId);

        }
        if (filepaths1.size() != 0) {
            idcardFrontFilepathId = filepaths1.get(0).getUrlBillid();
            supplier.setIdcardFrontFilepathId(idcardFrontFilepathId);
        }

        if (filepaths2.size() != 0) {
            idcardBackFilepathId = filepaths2.get(0).getUrlBillid();
            supplier.setIdcardBackFilepathId(idcardBackFilepathId);
        }
        SupplierIdcardinfo info = new SupplierIdcardinfo();
        info.setSupplierBillId(supplier.getBillId());

        SupplierIdcardinfo supplierIdcardinfo = null;
        List<SupplierIdcardinfo> supplierIdcardinfos = iSupplierIdcardinfoService.selectSupplierIdcardinfoList(info);
        if (supplierIdcardinfos.size() != 0) {
            supplierIdcardinfo = supplierIdcardinfos.get(0);
        }
//        SupplierIdcardinfo supplierIdcardinfo = iSupplierIdcardinfoService.selectSupplierIdcardinfoList(info).get(0);

        Enterprise enterprise = new Enterprise();
        enterprise.setEnterpriseId(supplier.getBusinessLicenseCode());
        Enterprise en = iEnterpriseService.selectEnterpriseList(enterprise).get(0);

        supplier.setSupplierIdcardinfo(supplierIdcardinfo);
        supplier.setEnterprise(en);
        mmap.put("supplier", supplier);
        return prefix + "/edit";
    }

    /**
     * 修改保存供应商
     */
    @RequiresPermissions("system:supplier:edit")
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Map editSave(Supplier supplier) throws Exception {
        String pwd = RandomPwd.generatePassword(8);
        supplier.setPassword(pwd);
        supplier.setPassword(passwordService.encryptPassword(supplier.getBusinessLicenseCode(), supplier.getPassword(), supplier.getSalt()));
        int row = supplierService.updateSupplier(supplier);
        Map<String, Object> map = new HashMap();
        if (row > 0) {
            map.put("code", "success");
            map.put("data", pwd);
        } else {
            map.put("code", "flag");
            map.put("data", "操作失败");
        }
//        log.info("[POST]:/system/supplier/edit" + "\r\n" + "[params]:" + supplier + "\r\n [result]:" + map);
        return map;
    }

    /**
     * 修改保存供应商
     */
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PostMapping("/saveSupplier")
    @ResponseBody
    public Map editSaveSupplier(Supplier supplier) throws Exception {
        Map<String, Object> map = new HashMap();
        if ("".equals(supplier.getCorprAddres()) || "".equals(supplier.getCorprName()) || "".equals(supplier.getCorprType()) || "".equals(supplier.getRegisteredCapital()) || "".equals(supplier.getEstablishDate()) || "".equals(supplier.getOperationPeriod()) || "".equals(supplier.getLegalPersonIdno()) || "".equals(supplier.getLegalPersonName()) || "".equals(supplier.getLegalPersonPhone())) {
            map.put("row", "0");
        } else {
            int row = supplierService.updateSupplierEdit(supplier);
            map.put("row", row);
        }
        return map;
    }

    @PostMapping("/edieClose")
    @ResponseBody
    public AjaxResult closeWin() throws Exception {
        return toAjax(1);
    }

    /**
     * 删除供应商
     */
    @RequiresPermissions("system:supplier:remove")
    @Log(title = "供应商", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return error("您未登陆或者登陆过期");
        }
        User user = (User) principal;
        if (user.getRoles().get(0).getRoleId() != 1) {
            return error("您未拥有权限进行操作");
        }
        return toAjax(supplierService.deleteSupplierByIds(ids));
    }

    /**
     * 重置密码
     *
     * @param supplier
     * @return
     */
    @RequiresPermissions("system:supplier:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(Supplier supplier) throws Exception {
        supplier.randomSalt();
        supplier.setPassword(passwordService.encryptPassword(supplier.getBusinessLicenseCode(), supplier.getPassword(), supplier.getSalt()));
        int result = supplierService.updateSupplier(supplier);

//        log.info("[POST]:/system/supplier/resetPwd" + "\r\n" + "[params]:" + supplier + "\r\n [result]:" + result);
        return toAjax(result);
    }

    /**
     * 初始化密码
     *
     * @param supplier
     * @return
     * @throws Exception
     */
    @PostMapping("/resetPwdInfo")
    @ResponseBody
    public Map resetPwdInfo(Supplier supplier) throws Exception {

        Supplier supNew = supplierService.selectSupplierById(supplier.getId());
        String pwd = RandomPwd.generatePassword(8);//获取随机密码
        supplier.setPassword(pwd);
        supplier.setBusinessLicenseCode(supNew.getBusinessLicenseCode());
        supplier.randomSalt();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        supplier.setUpdateBy(user.getLoginName());
        supplier.setPassword(passwordService.encryptPassword(supplier.getBusinessLicenseCode(), supplier.getPassword(), supplier.getSalt()));
        int result = supplierService.updateSupplierNewPassWord(supplier);
        Map<String, Object> map = new HashMap();
        if (result > 0) {
            passwordService.clearLoginRecordCache(supplier.getBusinessLicenseCode());
            map.put("flag", "success");
            map.put("data", pwd);
        } else {
            map.put("flag", "failed");
            map.put("data", "重置失败,请重新设置");
        }
//        log.info("[POST]:/system/supplier/resetPwdInfo" + "\r\n" + "[params]:" + supplier + "\r\n [result]:" + result);
        return map;
    }

    //@RequiresPermissions("system:supplier:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{id}")
    public String resetPwd(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("supplier", supplierService.selectSupplierById(id));
        return prefix + "/resetPwd";
    }

    /**
     * 用户状态修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
//    @RequiresPermissions("system:supplier:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Supplier supplier) {
        int result = supplierService.changeStatus(supplier);
//        log.info("[POST]:/system/supplier/changeStatus" + "\r\n" + "[params]:" + supplier + "\r\n [result]:" + result);
        return toAjax(result);
    }


    /**
     * 供应商注册
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public String register(@RequestBody Map<String, String> map) {
        String result = supplierService.register(map);
//        log.info("[POST]:/system/supplier/register" + "\r\n" + "[params]:" + map + "\r\n [result]:" + result);
        return result;
    }


    @PostMapping(value = "/findCustomerManager")
    @ResponseBody
    public Map findCustomerManager() {
        User user = new User();
        user.setDeptId(Long.valueOf("110"));
        List<User> list = userService.selectUserListByDeptId("110");
        Map<String, Object> map = new HashMap();
        map.put("data", list);
//        log.info("[POST]:/system/supplier/findCustomerManager" + "\r\n" + "[params]:" + "" + "\r\n [result]:" + map);
        return map;
    }

    /**
     * 展现图片
     */
    @GetMapping("/userProtocol")
    public String userProtocol() {
        return prefix + "/userprotocol";
    }

}
