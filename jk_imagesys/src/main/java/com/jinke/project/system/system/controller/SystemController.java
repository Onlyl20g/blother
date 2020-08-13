package com.jinke.project.system.system.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.system.domain.System;
import com.jinke.project.system.system.service.ISystemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 其他系统密钥 信息操作处理
 *
 * @author jinke
 * @date 2019-12-06
 */
@Controller
@RequestMapping("/system/system")
public class SystemController extends BaseController {
    private String prefix = "system/system";

    @Autowired
    private ISystemService systemService;

    @RequiresPermissions("system:system:view")
    @GetMapping()
    public String system() {
        return prefix + "/system";
    }

    /**
     * 查询其他系统密钥列表
     */
    @RequiresPermissions("system:system:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(System system) {
        startPage();
        List<System> list = systemService.selectSystemList(system);
        return getDataTable(list);
    }


    /**
     * 导出其他系统密钥列表
     */
    @RequiresPermissions("system:system:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(System system) {
        List<System> list = systemService.selectSystemList(system);
        ExcelUtil<System> util = new ExcelUtil<System>(System.class);
        return util.exportExcel(list, "system");
    }

    /**
     * 新增其他系统密钥
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存其他系统密钥
     */
    @RequiresPermissions("system:system:add")
    @Log(title = "其他系统密钥", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(System system) {
        return toAjax(systemService.insertSystem(system));
    }

    /**
     * 修改其他系统密钥
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        System system = systemService.selectSystemById(id);
        mmap.put("system", system);
        return prefix + "/edit";
    }

    /**
     * 修改保存其他系统密钥
     */
    @RequiresPermissions("system:system:edit")
    @Log(title = "其他系统密钥", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(System system) {
        return toAjax(systemService.updateSystem(system));
    }

    /**
     * 删除其他系统密钥
     */
    @RequiresPermissions("system:system:remove")
    @Log(title = "其他系统密钥", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(systemService.deleteSystemByIds(ids));
    }

}
