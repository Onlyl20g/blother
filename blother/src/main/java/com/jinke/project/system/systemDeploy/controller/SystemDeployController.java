package com.jinke.project.system.systemDeploy.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.systemDeploy.domain.SystemDeploy;
import com.jinke.project.system.systemDeploy.service.ISystemDeployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统标识信息操作处理@Autowired
 * private ISystemDeployService systemDeployService;
 *
 * @author jinke
 * @date 2019-12-04
 */
@Controller
@RequestMapping("/system/systemDeploy")
public class SystemDeployController extends BaseController {
    private String prefix = "system/systemDeploy";

    @Autowired
    private ISystemDeployService systemDeployService;

    //    @RequiresPermissions("system:systemDeploy:view")
    @GetMapping()
    public String systemDeploy() {
        return prefix + "/systemDeploy";
    }

    /**
     * 查询系统标识列表
     */
//    @RequiresPermissions("system:systemDeploy:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SystemDeploy systemDeploy) {
        startPage();
        List<SystemDeploy> list = systemDeployService.selectSystemDeployList(systemDeploy);
        return getDataTable(list);
    }


    /**
     * 导出系统标识列表
     */
//    @RequiresPermissions("system:systemDeploy:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SystemDeploy systemDeploy) {
        List<SystemDeploy> list = systemDeployService.selectSystemDeployList(systemDeploy);
        ExcelUtil<SystemDeploy> util = new ExcelUtil<SystemDeploy>(SystemDeploy.class);
        return util.exportExcel(list, "systemDeploy");
    }

    /**
     * 新增系统标识
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存系统标识
     */
//    @RequiresPermissions("system:systemDeploy:add")
    @Log(title = "系统标识", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SystemDeploy systemDeploy) {
        return toAjax(systemDeployService.insertSystemDeploy(systemDeploy));
    }

    /**
     * 修改系统标识
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SystemDeploy systemDeploy = systemDeployService.selectSystemDeployById(id);
        mmap.put("systemDeploy", systemDeploy);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统标识
     */
//    @RequiresPermissions("system:systemDeploy:edit")
    @Log(title = "系统标识", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SystemDeploy systemDeploy) {
        return toAjax(systemDeployService.updateSystemDeploy(systemDeploy));
    }

    /**
     * 删除系统标识
     */
//    @RequiresPermissions("system:systemDeploy:remove")
    @Log(title = "系统标识", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(systemDeployService.deleteSystemDeployByIds(ids));
    }

}
