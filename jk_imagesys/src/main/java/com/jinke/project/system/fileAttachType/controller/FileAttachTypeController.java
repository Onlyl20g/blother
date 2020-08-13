package com.jinke.project.system.fileAttachType.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.fileAttachType.domain.FileAttachType;
import com.jinke.project.system.fileAttachType.service.IFileAttachTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 其他系统文件分类 信息操作处理
 *
 * @author jinke
 * @date 2019-11-14
 */
@Controller
@RequestMapping("/system/fileAttachType")
public class FileAttachTypeController extends BaseController {
    private String prefix = "system/fileAttachType";

    @Autowired
    private IFileAttachTypeService fileAttachTypeService;

    @RequiresPermissions("system:fileAttachType:view")
    @GetMapping()
    public String fileAttachType() {
        return prefix + "/fileAttachType";
    }

    /**
     * 查询其他系统文件分类列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FileAttachType fileAttachType) {
        startPage();
        List<FileAttachType> list = fileAttachTypeService.selectFileAttachTypeList(fileAttachType);
        return getDataTable(list);
    }

    /**
     * 查询其他系统文件分类列表
     */
    @PostMapping("/listLike")
    @ResponseBody
    public TableDataInfo listLike(FileAttachType fileAttachType) {
        startPage();
        List<FileAttachType> list = fileAttachTypeService.selectLikeFileAttachTypeList(fileAttachType);
        return getDataTable(list);
    }

    /**
     * 查询其他系统文件分类列表
     */
    @PostMapping("/listFile")
    @ResponseBody
    public TableDataInfo listFile(FileAttachType fileAttachType) {
        if(fileAttachType.getSysType()  == null || fileAttachType.getSysType().equals("")){
            return getDataTable(new ArrayList<>());
        }
        List<FileAttachType> list = fileAttachTypeService.selectFileAttachTypeList(fileAttachType);
        return getDataTable(list);
    }

    /**
     * 导出其他系统文件分类列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FileAttachType fileAttachType) {
        List<FileAttachType> list = fileAttachTypeService.selectFileAttachTypeList(fileAttachType);
        ExcelUtil<FileAttachType> util = new ExcelUtil<FileAttachType>(FileAttachType.class);
        return util.exportExcel(list, "fileAttachType");
    }

    /**
     * 新增其他系统文件分类
     */

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存其他系统文件分类
     */
    @RequiresPermissions("system:fileAttachType:add")
    @Log(title = "其他系统文件分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FileAttachType fileAttachType) {
        FileAttachType fileAttachType1 = new FileAttachType();
        fileAttachType1.setSysType(fileAttachType.getSysType());
        fileAttachType1.setCode(fileAttachType.getCode());
        List<FileAttachType> fileAttachTypes = fileAttachTypeService.selectFileAttachTypeList(fileAttachType1);
        if (fileAttachTypes.size() > 0) {
            return errorMsg();
        }
        FileAttachType fileAttachType2 = new FileAttachType();
        fileAttachType2.setSysType(fileAttachType.getSysType());
        fileAttachType2.setInfoName(fileAttachType.getInfoName());
        List<FileAttachType> fileAttachTypes2 = fileAttachTypeService.selectFileAttachTypeList(fileAttachType2);
        if (fileAttachTypes2.size() > 0) {
            return errorMsg();
        }
        return toAjax(fileAttachTypeService.insertFileAttachType(fileAttachType));
    }

    /**
     * 修改其他系统文件分类
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        FileAttachType fileAttachType = fileAttachTypeService.selectFileAttachTypeById(id);
        mmap.put("fileAttachType", fileAttachType);
        return prefix + "/edit";
    }

    /**
     * 修改保存其他系统文件分类
     */
    @RequiresPermissions("system:fileAttachType:edit")
    @Log(title = "其他系统文件分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FileAttachType fileAttachType) {
        FileAttachType fileAttachType1 = new FileAttachType();
        fileAttachType1.setSysType(fileAttachType.getSysType());
        fileAttachType1.setInfoName(fileAttachType.getInfoName());
        List<FileAttachType> fileAttachTypes = fileAttachTypeService.selectFileAttachTypeList(fileAttachType1);
        if (fileAttachTypes.size() > 0) {
            return errorMsg();
        }
        return toAjax(fileAttachTypeService.updateFileAttachType(fileAttachType));
    }

    /**
     * 删除其他系统文件分类
     */
    @RequiresPermissions("system:fileAttachType:remove")
    @Log(title = "其他系统文件分类", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(fileAttachTypeService.deleteFileAttachTypeByIds(ids));
    }

}
