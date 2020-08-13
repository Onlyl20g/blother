package com.jinke.project.system.creditBuyerInfo.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.creditBuyerInfo.domain.CreditBuyerInfo;
import com.jinke.project.system.creditBuyerInfo.service.ICreditBuyerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 授信申请附信息操作处理
 *
 * @author jinke
 * @date 2019-08-01
 */
@Controller
@RequestMapping("/system/creditBuyerInfo")
public class CreditBuyerInfoController extends BaseController {
    private String prefix = "system/creditBuyerInfo";

    @Autowired
    private ICreditBuyerInfoService creditBuyerInfoService;

    @GetMapping()
    public String creditBuyerInfo() {
        return prefix + "/creditBuyerInfo";
    }

    /**
     * 查询授信申请附列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CreditBuyerInfo creditBuyerInfo) {
        startPage();
        List<CreditBuyerInfo> list = creditBuyerInfoService.selectCreditBuyerInfoList(creditBuyerInfo);
        return getDataTable(list);
    }

    /**
     * 查询对应买方企业
     */
    @PostMapping("/selectBuy")
    @ResponseBody
    public TableDataInfo selectBuy(HttpServletRequest request) {
        TableDataInfo tableDataInfo = getDataTable(creditBuyerInfoService.selectBuy(request));
        return tableDataInfo;
    }


    /**
     * 导出授信申请附列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CreditBuyerInfo creditBuyerInfo) {
        List<CreditBuyerInfo> list = creditBuyerInfoService.selectCreditBuyerInfoList(creditBuyerInfo);
        ExcelUtil<CreditBuyerInfo> util = new ExcelUtil<CreditBuyerInfo>(CreditBuyerInfo.class);
        return util.exportExcel(list, "creditBuyerInfo");
    }

    /**
     * 新增授信申请附
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存授信申请附
     */
    @Log(title = "授信申请附", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CreditBuyerInfo creditBuyerInfo) {
        return toAjax(creditBuyerInfoService.insertCreditBuyerInfo(creditBuyerInfo));
    }

    /**
     * 修改授信申请附
     */
    @GetMapping("/edit/{creditsId}")
    public String edit(@PathVariable("creditsId") Long creditsId, ModelMap mmap) {
        CreditBuyerInfo creditBuyerInfo = creditBuyerInfoService.selectCreditBuyerInfoById(creditsId);
        mmap.put("creditBuyerInfo", creditBuyerInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存授信申请附
     */
    @Log(title = "授信申请附", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CreditBuyerInfo creditBuyerInfo) {
        return toAjax(creditBuyerInfoService.updateCreditBuyerInfo(creditBuyerInfo));
    }

    /**
     * 删除授信申请附
     */
    @Log(title = "授信申请附", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(creditBuyerInfoService.deleteCreditBuyerInfoByIds(ids));
    }

}
