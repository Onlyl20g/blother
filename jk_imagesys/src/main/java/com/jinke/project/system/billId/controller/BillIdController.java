package com.jinke.project.system.billId.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.billId.domain.BillId;
import com.jinke.project.system.billId.service.IBillIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务申请单据凭证 信息操作处理
 *
 * @author jinke
 * @date 2019-11-27
 */
@Controller
@RequestMapping("/system/billId")
public class BillIdController extends BaseController {
    private String prefix = "system/billId";

    @Autowired
    private IBillIdService billIdService;

    //    @RequiresPermissions("system:billId:view")
    @GetMapping()
    public String billId() {
        return prefix + "/billId";
    }

    /**
     * 查询业务申请单据凭证列表
     */
//    @RequiresPermissions("system:billId:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BillId billId) {
        startPage();
        List<BillId> list = billIdService.selectBillIdList(billId);
        return getDataTable(list);
    }


    /**
     * 导出业务申请单据凭证列表
     */
//    @RequiresPermissions("system:billId:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BillId billId) {
        List<BillId> list = billIdService.selectBillIdList(billId);
        ExcelUtil<BillId> util = new ExcelUtil<BillId>(BillId.class);
        return util.exportExcel(list, "billId");
    }

    /**
     * 新增业务申请单据凭证
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存业务申请单据凭证
     */
//    @RequiresPermissions("system:billId:add")
    @Log(title = "业务申请单据凭证", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BillId billId) {
        return toAjax(billIdService.insertBillId(billId));
    }

    /**
     * 修改业务申请单据凭证
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BillId billId = billIdService.selectBillIdById(id);
        mmap.put("billId", billId);
        return prefix + "/edit";
    }

    /**
     * 修改保存业务申请单据凭证
     */
//    @RequiresPermissions("system:billId:edit")
    @Log(title = "业务申请单据凭证", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BillId billId) {
        return toAjax(billIdService.updateBillId(billId));
    }

    /**
     * 删除业务申请单据凭证
     */
//    @RequiresPermissions("system:billId:remove")
    @Log(title = "业务申请单据凭证", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(billIdService.deleteBillIdByIds(ids));
    }

}
