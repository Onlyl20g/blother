package com.jinke.project.system.supplierIdcardinfo.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.supplierIdcardinfo.domain.SupplierIdcardinfo;
import com.jinke.project.system.supplierIdcardinfo.service.ISupplierIdcardinfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商身份证信息操作处理
 *
 * @author jinke
 * @date 2019-08-20
 */
@Controller
@RequestMapping("/system/supplierIdcardinfo")
public class SupplierIdcardinfoController extends BaseController {
    private String prefix = "system/supplierIdcardinfo";

    @Autowired
    private ISupplierIdcardinfoService supplierIdcardinfoService;

    @RequiresPermissions("system:supplierIdcardinfo:view")
    @GetMapping()
    public String supplierIdcardinfo() {
        return prefix + "/supplierIdcardinfo";
    }

    /**
     * 查询供应商身份证列表
     */
    @RequiresPermissions("system:supplierIdcardinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SupplierIdcardinfo supplierIdcardinfo) {
        startPage();
        List<SupplierIdcardinfo> list = supplierIdcardinfoService.selectSupplierIdcardinfoList(supplierIdcardinfo);
        return getDataTable(list);
    }


    /**
     * 导出供应商身份证列表
     */
    @RequiresPermissions("system:supplierIdcardinfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SupplierIdcardinfo supplierIdcardinfo) {
        List<SupplierIdcardinfo> list = supplierIdcardinfoService.selectSupplierIdcardinfoList(supplierIdcardinfo);
        ExcelUtil<SupplierIdcardinfo> util = new ExcelUtil<SupplierIdcardinfo>(SupplierIdcardinfo.class);
        return util.exportExcel(list, "supplierIdcardinfo");
    }

    /**
     * 新增供应商身份证
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存供应商身份证
     */
    @RequiresPermissions("system:supplierIdcardinfo:add")
    @Log(title = "供应商身份证", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SupplierIdcardinfo supplierIdcardinfo) {
        return toAjax(supplierIdcardinfoService.insertSupplierIdcardinfo(supplierIdcardinfo));
    }

    /**
     * 修改供应商身份证
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SupplierIdcardinfo supplierIdcardinfo = supplierIdcardinfoService.selectSupplierIdcardinfoById(id);
        mmap.put("supplierIdcardinfo", supplierIdcardinfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存供应商身份证
     */
    @Log(title = "供应商身份证", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SupplierIdcardinfo supplierIdcardinfo) {
        return toAjax(supplierIdcardinfoService.updateSupplierIdcardinfo(supplierIdcardinfo));
    }

    /**
     * 修改保存供应商身份证
     */
    @Log(title = "供应商身份证", businessType = BusinessType.UPDATE)
    @PostMapping("/editSupplierCard")
    @ResponseBody
    public AjaxResult editSupplierCard(SupplierIdcardinfo supplierIdcardinfo) {
        return toAjax(supplierIdcardinfoService.updateSupplierIdcardinfoAll(supplierIdcardinfo));
    }

    /**
     * 删除供应商身份证
     */
    @RequiresPermissions("system:supplierIdcardinfo:remove")
    @Log(title = "供应商身份证", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(supplierIdcardinfoService.deleteSupplierIdcardinfoByIds(ids));
    }

}
