package com.jinke.project.system.filepath.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.filepath.domain.Filepath;
import com.jinke.project.system.filepath.domain.FilepathDto;
import com.jinke.project.system.filepath.service.IFilepathService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件路径信息操作处理
 *
 * @author jinke
 * @date 2019-07-14
 */
@Controller
@RequestMapping("/system/filepath")
public class FilepathController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(FilepathController.class);

    private String prefix = "system/filepath";

    @Autowired
    private IFilepathService filepathService;

    @RequiresPermissions("system:filepath:view")
    @GetMapping()
    public String filepath() {
        return prefix + "/filepath";
    }

    /**
     * 查询文件路径列表
     */
    @RequiresPermissions("system:filepath:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Filepath filepath) {
        startPage();
        List<Filepath> list = filepathService.selectFilepathList(filepath);
        List<FilepathDto> filepathDtos = new ArrayList<>();
        for (Filepath filepath1 : list) {
            FilepathDto filepathDto = new FilepathDto();
            BeanUtils.copyProperties(filepath1, filepathDto);
            filepathDtos.add(filepathDto);
        }
//        log.info("[POST]:/system/filepath/list" + "\r\n" + "[params]:" + filepath + "\r\n [result]:" + filepathDtos);
        return getDataTable(filepathDtos);
    }

    @PostMapping("/findFilePath")
    @ResponseBody
    public Map findFilePath(Filepath filepath) {
        List<Filepath> filepaths = filepathService.selectFilepathList(filepath);
        Map<String, Object> map = new HashMap();
        map.put("filepath", filepaths);

        return map;
    }

    /**
     * 导出文件路径列表
     */
    @RequiresPermissions("system:filepath:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Filepath filepath) {
        List<Filepath> list = filepathService.selectFilepathList(filepath);
        ExcelUtil<Filepath> util = new ExcelUtil<Filepath>(Filepath.class);
        return util.exportExcel(list, "filepath");
    }

    /**
     * 新增文件路径
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存文件路径
     */
    @RequiresPermissions("system:filepath:add")
    @Log(title = "文件路径", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Filepath filepath) {
        return toAjax(filepathService.insertFilepath(filepath));
    }

    /**
     * 修改文件路径
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Filepath filepath = filepathService.selectFilepathById((id == null) ? null : id.longValue());
        mmap.put("filepath", filepath);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件路径
     */
    @RequiresPermissions("system:filepath:edit")
    @Log(title = "文件路径", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Filepath filepath) {
        int result = filepathService.updateFilepath(filepath);
//        log.info("[POST]:/system/filepath/edit" + "\r\n" + "[params]:" + filepath + "\r\n [result]:" + result);
        return toAjax(result);
    }

    /**
     * 删除文件路径
     */
    @RequiresPermissions("system:filepath:remove")
    @Log(title = "文件路径", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        int result = filepathService.deleteFilepathByIds(ids);
//        log.info("[POST]:/system/filepath/remove" + "\r\n" + "[params]:" + ids + "\r\n [result]:" + result);
        return toAjax(result);
    }

}
