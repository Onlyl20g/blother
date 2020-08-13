package com.jinke.project.system.enterprise.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.enterprise.domain.Enterprise;
import com.jinke.project.system.enterprise.service.IEnterpriseService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 企业信息操作处理
 *
 * @author jinke
 * @date 2019-08-05
 */
@Controller
@RequestMapping("/system/enterprise")
public class EnterpriseController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(EnterpriseController.class);

    private String prefix = "system/enterprise";

    @Autowired
    private IEnterpriseService enterpriseService;

    @RequiresPermissions("system:enterprise:view")
    @GetMapping()
    public String enterprise() {
        return prefix + "/enterprise";
    }

    /**
     * 查询企业列表
     */
    @RequiresPermissions("system:enterprise:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Enterprise enterprise) {
        startPage();
        List<Enterprise> list = enterpriseService.selectEnterpriseList(enterprise);
//        log.info("[POST]:/system/enterprise/list" + "\r\n" + "[params]:" + enterprise + "\r\n [result]:" + list);
        return getDataTable(list);
    }


    /**
     * 导出企业列表
     */
    @RequiresPermissions("system:enterprise:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Enterprise enterprise) {
        List<Enterprise> list = enterpriseService.selectEnterpriseList(enterprise);
        ExcelUtil<Enterprise> util = new ExcelUtil<Enterprise>(Enterprise.class);
        return util.exportExcel(list, "enterprise");
    }

    /**
     * 新增企业
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存企业
     */
    @RequiresPermissions("system:enterprise:add")
    @Log(title = "企业", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Enterprise enterprise) {
        int result = enterpriseService.insertEnterprise(enterprise);
//        log.info("[POST]:/system/enterprise/add" + "\r\n" + "[params]:" + enterprise + "\r\n [result]:" + result);
        return toAjax(result);
    }

    /**
     * 修改企业
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Enterprise enterprise = enterpriseService.selectEnterpriseById(id);
        mmap.put("enterprise", enterprise);
        return prefix + "/edit";
    }

    /**
     * 修改保存企业
     */
    @Log(title = "企业", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Enterprise enterprise) {
        return toAjax(enterpriseService.updateEnterprise(enterprise));
    }

    /**
     * 修改保存企业
     */
    @Log(title = "企业", businessType = BusinessType.UPDATE)
    @PostMapping("/editEnterprise")
    @ResponseBody
    public Map editSaveEnterprise(Enterprise enterprise) {
        Map<String, Integer> map = new HashedMap();
        if ("".equals(enterprise.getEnterpriseCapital()) || "".equals(enterprise.getEnterpriseValidDateStart()) || "".equals(enterprise.getEnterpriseValidDateend())) {
            map.put("code", 0);
            return map;
        }
        int code = enterpriseService.updateEnterpriseAll(enterprise);
        map.put("code", code);
        return map;
    }

    /**
     * 删除企业
     */
    @RequiresPermissions("system:enterprise:remove")
    @Log(title = "企业", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(enterpriseService.deleteEnterpriseByIds(ids));
    }

}
