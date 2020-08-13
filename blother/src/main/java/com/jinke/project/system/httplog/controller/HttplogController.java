package com.jinke.project.system.httplog.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.httplog.domain.Httplog;
import com.jinke.project.system.httplog.service.IHttplogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对外HTTP调用日志信息操作处理
 *
 * @author jinke
 * @date 2019-07-16
 */
@Controller
@RequestMapping("/system/httplog")
public class HttplogController extends BaseController {
    private String prefix = "system/httplog";

    @Autowired
    private IHttplogService httplogService;

    @RequiresPermissions("system:httplog:view")
    @GetMapping()
    public String httplog() {
        return prefix + "/httplog";
    }

    /**
     * 查询对外HTTP调用日志列表
     */
    @RequiresPermissions("system:httplog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Httplog httplog) {
        startPage();
        List<Httplog> list = this.httplogService.selectHttplogList(httplog);
        return getDataTable(list);
    }

    /**
     * 导出对外HTTP调用日志列表
     */
    @RequiresPermissions("system:httplog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Httplog httplog) {
        List<Httplog> list = this.httplogService.selectHttplogList(httplog);
        ExcelUtil<Httplog> util = new ExcelUtil<Httplog>(Httplog.class);
        return util.exportExcel(list, "httplog");
    }

    /**
     * 新增对外HTTP调用日志
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存对外HTTP调用日志
     */
    @RequiresPermissions("system:httplog:add")
    @Log(title = "对外HTTP调用日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Httplog httplog) {
        return toAjax(this.httplogService.insertHttplog(httplog));
    }

    /**
     * 修改对外HTTP调用日志
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Httplog httplog = this.httplogService.selectHttplogById(id);
        mmap.put("httplog", httplog);
        return prefix + "/edit";
    }

    /**
     * 修改保存对外HTTP调用日志
     */
    @RequiresPermissions("system:httplog:edit")
    @Log(title = "对外HTTP调用日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Httplog httplog) {
        return toAjax(this.httplogService.updateHttplog(httplog));
    }

    /**
     * 删除对外HTTP调用日志
     */
    @RequiresPermissions("system:httplog:remove")
    @Log(title = "对外HTTP调用日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(this.httplogService.deleteHttplogByIds(ids));
    }

}
