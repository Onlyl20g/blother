package com.jinke.project.system.viewdataScan.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.viewdataScan.domain.ViewdataScan;
import com.jinke.project.system.viewdataScan.service.IViewdataScanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 影像扫描 信息操作处理
 *
 * @author jinke
 * @date 2019-09-09
 */
@Controller
@RequestMapping("/system/viewdataScan")
public class ViewdataScanController extends BaseController {
    private String prefix = "system/viewdataScan";

    @Autowired
    private IViewdataScanService viewdataScanService;

    @RequiresPermissions("system:viewdataScan:view")
    @GetMapping()
    public String viewdataScan() {
        return prefix + "/viewdataScan";
    }

    /**
     * 查询影像扫描列表
     */
    @RequiresPermissions("system:viewdataScan:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ViewdataScan viewdataScan) {
        startPage();
        List<ViewdataScan> list = viewdataScanService.selectViewdataScanList(viewdataScan);
        return getDataTable(list);
    }


    /**
     * 导出影像扫描列表
     */
    @RequiresPermissions("system:viewdataScan:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ViewdataScan viewdataScan) {
        List<ViewdataScan> list = viewdataScanService.selectViewdataScanList(viewdataScan);
        ExcelUtil<ViewdataScan> util = new ExcelUtil<ViewdataScan>(ViewdataScan.class);
        return util.exportExcel(list, "viewdataScan");
    }

    /**
     * 新增影像扫描
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存影像扫描
     */
    @RequiresPermissions("system:viewdataScan:add")
    @Log(title = "影像扫描", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ViewdataScan viewdataScan) {
        return toAjax(viewdataScanService.insertViewdataScan(viewdataScan));
    }

    /**
     * 修改影像扫描
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        ViewdataScan viewdataScan = viewdataScanService.selectViewdataScanById(id);
        mmap.put("viewdataScan", viewdataScan);
        return prefix + "/edit";
    }

    /**
     * 修改保存影像扫描
     */
    @RequiresPermissions("system:viewdataScan:edit")
    @Log(title = "影像扫描", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ViewdataScan viewdataScan) {
        return toAjax(viewdataScanService.updateViewdataScan(viewdataScan));
    }

    /**
     * 删除影像扫描
     */
    @RequiresPermissions("system:viewdataScan:remove")
    @Log(title = "影像扫描", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(viewdataScanService.deleteViewdataScanByIds(ids));
    }

}
