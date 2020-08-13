package com.jinke.project.system.viewdataUpload.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.viewdataUpload.domain.ViewdataUpload;
import com.jinke.project.system.viewdataUpload.service.IViewdataUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 影像上传 信息操作处理
 *
 * @author jinke
 * @date 2019-09-09
 */
@Controller
@RequestMapping("/system/viewdataUpload")
public class ViewdataUploadController extends BaseController {
    private String prefix = "system/viewdataUpload";

    @Autowired
    private IViewdataUploadService viewdataUploadService;

    @GetMapping()
    public String viewdataUpload() {
        return prefix + "/viewdataUpload";
    }

    /**
     * 查询影像上传列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ViewdataUpload viewdataUpload) {
        startPage();
        List<ViewdataUpload> list = viewdataUploadService.selectViewdataUploadList(viewdataUpload);
        return getDataTable(list);
    }


    /**
     * 导出影像上传列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ViewdataUpload viewdataUpload) {
        List<ViewdataUpload> list = viewdataUploadService.selectViewdataUploadList(viewdataUpload);
        ExcelUtil<ViewdataUpload> util = new ExcelUtil<ViewdataUpload>(ViewdataUpload.class);
        return util.exportExcel(list, "viewdataUpload");
    }

    /**
     * 新增影像上传
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存影像上传
     */
    @Log(title = "影像上传", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ViewdataUpload viewdataUpload) {

        return toAjax(viewdataUploadService.insertViewdataUpload(viewdataUpload));
    }

    /**
     * 新增保存影像上传
     */
    @Log(title = "影像上传", businessType = BusinessType.INSERT)
    @PostMapping("/addScan")
    @ResponseBody
    public AjaxResult addSaveScan(ViewdataUpload viewdataUpload) {

        return toAjax(viewdataUploadService.insertViewdataUploadScan(viewdataUpload));
    }

    /**
     * 修改影像上传
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        ViewdataUpload viewdataUpload = viewdataUploadService.selectViewdataUploadById(id);
        mmap.put("viewdataUpload", viewdataUpload);
        return prefix + "/edit";
    }

    /**
     * 修改保存影像上传
     */
    @Log(title = "影像上传", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ViewdataUpload viewdataUpload) {
        return toAjax(viewdataUploadService.updateViewdataUpload(viewdataUpload));
    }

    /**
     * 删除影像上传
     */
    @Log(title = "影像上传", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(viewdataUploadService.deleteViewdataUploadByIds(ids));
    }

}
