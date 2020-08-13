package com.jinke.project.system.uploadPath.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.uploadPath.domain.UploadPath;
import com.jinke.project.system.uploadPath.service.IUploadPathService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 其他系统上传文件路径地址 信息操作处理
 *
 * @author jinke
 * @date 2019-12-10
 */
@Controller
@RequestMapping("/system/uploadPath")
public class UploadPathController extends BaseController {
    private String prefix = "system/uploadPath";

    @Autowired
    private IUploadPathService uploadPathService;

    @RequiresPermissions("system:uploadPath:view")
    @GetMapping()
    public String uploadPath() {
        return prefix + "/uploadPath";
    }

    /**
     * 查询其他系统上传文件路径地址列表
     */
    @RequiresPermissions("system:uploadPath:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UploadPath uploadPath) {
        startPage();
        List<UploadPath> list = uploadPathService.selectUploadPathList(uploadPath);
        return getDataTable(list);
    }


    /**
     * 导出其他系统上传文件路径地址列表
     */
    @RequiresPermissions("system:uploadPath:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UploadPath uploadPath) {
        List<UploadPath> list = uploadPathService.selectUploadPathList(uploadPath);
        ExcelUtil<UploadPath> util = new ExcelUtil<UploadPath>(UploadPath.class);
        return util.exportExcel(list, "uploadPath");
    }

    /**
     * 新增其他系统上传文件路径地址
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存其他系统上传文件路径地址
     */
    @RequiresPermissions("system:uploadPath:add")
    @Log(title = "其他系统上传文件路径地址", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(UploadPath uploadPath) {
        return toAjax(uploadPathService.insertUploadPath(uploadPath));
    }

    /**
     * 修改其他系统上传文件路径地址
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        UploadPath uploadPath = uploadPathService.selectUploadPathById(id);
        mmap.put("uploadPath", uploadPath);
        return prefix + "/edit";
    }

    /**
     * 修改保存其他系统上传文件路径地址
     */
    @RequiresPermissions("system:uploadPath:edit")
    @Log(title = "其他系统上传文件路径地址", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UploadPath uploadPath) {
        return toAjax(uploadPathService.updateUploadPath(uploadPath));
    }

    /**
     * 删除其他系统上传文件路径地址
     */
    @RequiresPermissions("system:uploadPath:remove")
    @Log(title = "其他系统上传文件路径地址", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(uploadPathService.deleteUploadPathByIds(ids));
    }

}
