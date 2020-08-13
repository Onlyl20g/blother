package com.jinke.project.customer.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.customer.domain.RepayRecord;
import com.jinke.project.customer.domain.RepayRecordDto;
import com.jinke.project.customer.service.IRepayRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 还款计划信息操作处理
 *
 * @author jinke
 * @date 2019-08-13
 */
@Controller
@RequestMapping("/system/repayRecord")
public class RepayRecordController extends BaseController {
    private String prefix = "system/repayRecord";
    private final static Logger log = LoggerFactory.getLogger(RepayRecordController.class);

    @Autowired
    private IRepayRecordService repayRecordService;

    @RequiresPermissions("system:repayRecord:view")
    @GetMapping()
    public String repayRecord() {
        /*Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        if (supplier.getUpdateBy() == null || !supplier.getBusinessLicenseCode().equals(supplier.getUpdateBy())) {
            return "customer/index";
        }*/
        return prefix + "/repayRecord";
    }

    /**
     * 查询还款计划列表
     */
//    @RequiresPermissions("system:repayRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RepayRecord repayRecord) {
        startPage();
        List<RepayRecordDto> list = repayRecordService.selectRepayRecordList(repayRecord);
        return getDataTable(list);
    }

    /**
     * 查询还款历史记录
     */
    @GetMapping("/repaycord")
    public String findRepayCord(@RequestParam("id") int id, ModelMap mmap) {
        mmap.put("id", id);
        return "customer/repayment/repayrecord";
    }


    /**
     * 导出还款计划列表
     */
    @RequiresPermissions("system:repayRecord:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RepayRecord repayRecord) {
        List<RepayRecordDto> list = repayRecordService.selectRepayRecordList(repayRecord);
        ExcelUtil<RepayRecordDto> util = new ExcelUtil<RepayRecordDto>(RepayRecordDto.class);
        return util.exportExcel(list, "repayRecord");
    }

    /**
     * 新增还款计划
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存还款计划
     */
    @RequiresPermissions("system:repayRecord:add")
    @Log(title = "还款计划", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RepayRecord repayRecord) {
        int result = repayRecordService.insertRepayRecord(repayRecord);
        return toAjax(result);
    }

    /**
     * 修改还款计划
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        RepayRecord repayRecord = repayRecordService.selectRepayRecordById(id);
        mmap.put("repayRecord", repayRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存还款计划
     */
    @RequiresPermissions("system:repayRecord:edit")
    @Log(title = "还款计划", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RepayRecord repayRecord) {
        int result = repayRecordService.updateRepayRecord(repayRecord);
        return toAjax(result);
    }

    /**
     * 删除还款计划
     */
    @RequiresPermissions("system:repayRecord:remove")
    @Log(title = "还款计划", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(repayRecordService.deleteRepayRecordByIds(ids));
    }

}
