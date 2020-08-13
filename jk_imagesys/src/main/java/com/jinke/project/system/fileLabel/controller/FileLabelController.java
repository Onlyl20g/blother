package com.jinke.project.system.fileLabel.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.fileLabel.domain.FileLabel;
import com.jinke.project.system.fileLabel.service.IFileLabelService;
import com.jinke.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件标签 信息操作处理
 *
 * @author jinke
 * @date 2019-09-09
 */
@Controller
@RequestMapping("/system/fileLabel")
public class FileLabelController extends BaseController {
    private String prefix = "system/fileLabel";

    @Autowired
    private IFileLabelService fileLabelService;

    @RequiresPermissions("system:fileLabel:view")
    @GetMapping()
    public String fileLabel() {
        return prefix + "/fileLabel";
    }

    /**
     * 查询文件标签列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FileLabel fileLabel) {
        startPage();
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return null;
        }
        User user = (User) principal;
        fileLabel.setUserBusinessId(user.getBusinessId());
        List<FileLabel> list = fileLabelService.selectFileLabelList(fileLabel);
        return getDataTable(list);
    }

    /**
     * 查询文件标签列表
     */
    @PostMapping("/selectFileLabels")
    @ResponseBody
    public TableDataInfo selectFileLabels(FileLabel fileLabel) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return null;
        }
        User user = (User) principal;
        fileLabel.setDelFlag("0");
        fileLabel.setUserBusinessId(user.getBusinessId());
        List<FileLabel> list = fileLabelService.selectFileLabelList(fileLabel);
        return getDataTable(list);
    }

    /**
     * 查询分享文件标签列表
     */
    @PostMapping("/selectShareFileLabels")
    @ResponseBody
    public TableDataInfo selectShareFileLabels(FileLabel fileLabel) {
        fileLabel.setDelFlag("0");
        List<FileLabel> list = fileLabelService.selectFileLabelList(fileLabel);
        return getDataTable(list);
    }


    /**
     * 导出文件标签列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FileLabel fileLabel) {
        List<FileLabel> list = fileLabelService.selectFileLabelList(fileLabel);
        ExcelUtil<FileLabel> util = new ExcelUtil<FileLabel>(FileLabel.class);
        return util.exportExcel(list, "fileLabel");
    }

    /**
     * 新增文件标签
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存文件标签
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FileLabel fileLabel) {
        int i = fileLabelService.insertFileLabel(fileLabel);
        if (i == -1) {
            return error("存在同名标签");
        }
        return toAjax(i);
    }

    /**
     * 通过id查询标签
     */
    @PostMapping("/selectById")
    @ResponseBody
    public FileLabel selectById(String id) {
        return fileLabelService.selectFileLabelById(new Integer(id));
    }

    /**
     * 修改文件标签
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        FileLabel fileLabel = fileLabelService.selectFileLabelById(id);
        mmap.put("fileLabel", fileLabel);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件标签
     */
    @Log(title = "文件标签", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FileLabel fileLabel) {
        return toAjax(fileLabelService.updateFileLabel(fileLabel));
    }

    /**
     * 删除文件标签
     */
    @Log(title = "文件标签", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(fileLabelService.deleteFileLabelByIds(ids));
    }

    /**
     * 修改保存文件标签
     */

    @PostMapping("/deleteLabel")
    @ResponseBody
    public AjaxResult deleteLabel(FileLabel fileLabel) {
        FileLabel fileLabel1 = fileLabelService.selectFileLabelById(fileLabel.getId());
        fileLabel1.setDelFlag("1");
        return toAjax(fileLabelService.updateFileLabel(fileLabel1));
    }

}
