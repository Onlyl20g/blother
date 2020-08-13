package com.jinke.project.system.fileTemp.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.fileTemp.domain.FileTemp;
import com.jinke.project.system.fileTemp.service.IFileTempService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 临时文件路径信息操作处理
 *
 * @author jinke
 * @date 2019-07-14
 */
@Controller
@RequestMapping("/system/fileTemp")
public class FileTempController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(FileTempController.class);

    private String prefix = "system/fileTemp";

    @Autowired
    private IFileTempService fileTempService;

    @RequiresPermissions("system:fileTemp:view")
    @GetMapping()
    public String fileTemp() {
        return prefix + "/fileTemp";
    }

    /**
     * 查询临时文件路径列表
     */
    @RequiresPermissions("system:fileTemp:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FileTemp fileTemp) {
        startPage();
        List<FileTemp> list = fileTempService.selectFileTempList(fileTemp);
        log.info("params:" + fileTemp + "\r\n result" + list);
        return getDataTable(list);
    }


    /**
     * 导出临时文件路径列表
     */
    @RequiresPermissions("system:fileTemp:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FileTemp fileTemp) {
        List<FileTemp> list = fileTempService.selectFileTempList(fileTemp);
        ExcelUtil<FileTemp> util = new ExcelUtil<FileTemp>(FileTemp.class);
        return util.exportExcel(list, "fileTemp");
    }

    /**
     * 新增临时文件路径
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存临时文件路径
     */
    @RequiresPermissions("system:fileTemp:add")
    @Log(title = "临时文件路径", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FileTemp fileTemp) {
        int result = fileTempService.insertFileTemp(fileTemp);
        log.info("params:" + fileTemp + "\r\n result" + result);
        return toAjax(result);
    }

    /**
     * 修改临时文件路径
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        FileTemp fileTemp = fileTempService.selectFileTempById((id == null) ? null : id.longValue());
        mmap.put("fileTemp", fileTemp);
        log.info("params:" + id + "\r\n result" + fileTemp);
        return prefix + "/edit";
    }

    /**
     * 修改保存临时文件路径
     */
    @RequiresPermissions("system:fileTemp:edit")
    @Log(title = "临时文件路径", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FileTemp fileTemp) {
        int result = fileTempService.updateFileTemp(fileTemp);
        log.info("params:" + fileTemp + "\r\n result" + result);
        return toAjax(result);
    }

    /**
     * 删除临时文件路径
     */
    @RequiresPermissions("system:fileTemp:remove")
    @Log(title = "临时文件路径", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        int result = fileTempService.deleteFileTempByIds(ids);
        log.info("params:" + ids + "\r\n result" + result);
        return toAjax(result);
    }

}
