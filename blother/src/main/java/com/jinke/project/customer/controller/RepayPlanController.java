package com.jinke.project.customer.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.customer.domain.RepayPlan;
import com.jinke.project.customer.domain.RepayPlanDto;
import com.jinke.project.customer.service.IRepayPlanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 还款计划附信息操作处理
 *
 * @author jinke
 * @date 2019-08-13
 */
@Controller
@RequestMapping("/system/repayPlan")
public class RepayPlanController extends BaseController {
    private String prefix = "system/repayPlan";

    @Autowired
    private IRepayPlanService repayPlanService;

    @GetMapping()
    public String repayPlan() {
        /*Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        if (supplier.getUpdateBy() == null || !supplier.getBusinessLicenseCode().equals(supplier.getUpdateBy())) {
            return "customer/index";
        }*/
        return prefix + "/repayPlan";
    }

    /**
     * 查询还款计划附列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RepayPlan repayPlan) {
        startPage();
        List<RepayPlanDto> list = repayPlanService.selectRepayPlanList(repayPlan);
        return getDataTable(list);
    }

    /**
     * 导出还款计划附列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RepayPlan repayPlan) {
        List<RepayPlanDto> list = repayPlanService.selectRepayPlanList(repayPlan);
        ExcelUtil<RepayPlanDto> util = new ExcelUtil<RepayPlanDto>(RepayPlanDto.class);
        return util.exportExcel(list, "repayPlan");
    }

    /**
     * 新增还款计划附
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存还款计划附
     */
    @Log(title = "还款计划附", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RepayPlan repayPlan) {
        int result = repayPlanService.insertRepayPlan(repayPlan);
        return toAjax(result);
    }

    /**
     * 新增还款计划附
     */
    @GetMapping("/repayplay")
    public String repayPlay(@RequestParam("id") int id, ModelMap mmap) {
        mmap.put("id", id);
        return "customer/repayment/repayplay";
    }


    /**
     * 修改还款计划附
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        RepayPlan repayPlan = repayPlanService.selectRepayPlanById(id);
        mmap.put("repayPlan", repayPlan);
        return prefix + "/edit";
    }

    /**
     * 修改保存还款计划附
     */
    @RequiresPermissions("system:repayPlan:edit")
    @Log(title = "还款计划附", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RepayPlan repayPlan) {
        int result = repayPlanService.updateRepayPlan(repayPlan);
        return toAjax(result);
    }

    /**
     * 删除还款计划附
     */
    @RequiresPermissions("system:repayPlan:remove")
    @Log(title = "还款计划附", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(repayPlanService.deleteRepayPlanByIds(ids));
    }

}
