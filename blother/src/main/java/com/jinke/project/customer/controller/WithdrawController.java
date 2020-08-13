package com.jinke.project.customer.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.customer.domain.Credit;
import com.jinke.project.customer.domain.Withdraw;
import com.jinke.project.customer.domain.WithdrawDto;
import com.jinke.project.customer.service.ICreditService;
import com.jinke.project.customer.service.IWithdrawService;
import com.jinke.project.system.supplier.domain.Supplier;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 提款申请信息操作处理
 *
 * @author jinke
 * @date 2019-07-23
 */
@Controller
@RequestMapping("/withdraw")
public class WithdrawController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(WithdrawController.class);

    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private ICreditService creditService;

    @GetMapping()
    public String withdraw() {
       /* Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        if (supplier.getUpdateBy() == null || !supplier.getBusinessLicenseCode().equals(supplier.getUpdateBy())) {
            return "customer/index";
        }*/
        return "customer/withdraw/withdraw";
    }

    /**
     * 查询提款申请列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Withdraw withdraw) {
        startPage();
        List<Withdraw> list = withdrawService.selectWithdrawList(withdraw);
        return getDataTable(list);
    }

    /**
     * 查询提款申请列表
     */
    @PostMapping("/select/{creditBillId}")
    @ResponseBody
    public TableDataInfo select(@PathVariable("creditBillId") String creditBillId) {
//        startPage();
        List<WithdrawDto> list = withdrawService.selectWithdrawByCreditBillId(creditBillId);
        return getDataTable(list);
    }

    /**
     * 查询还款信息列表
     *
     * @param
     * @return
     */
    @PostMapping("/listBySupplierId")
    @ResponseBody
    public TableDataInfo findRepayPlanList() {
        startPage();
        List<WithdrawDto> list = withdrawService.findRepayPlanList();
        return getDataTable(list);
    }

    /**
     * 导出提款申请列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Withdraw withdraw) {
        List<Withdraw> list = withdrawService.selectWithdrawList(withdraw);
        ExcelUtil<Withdraw> util = new ExcelUtil<>(Withdraw.class);
        return util.exportExcel(list, "withdraw");
    }

    /**
     * 新增提款申请
     */
    @GetMapping("/drawrecord")
    public String drawrecord(@RequestParam("id") String id, ModelMap mmap) {
        Credit credit = creditService.selectCreditById(Long.valueOf(id));
        mmap.put("billId", credit.getBillId());
        mmap.put("id", id);
        return "customer/withdraw/drawrecord";
    }

    @GetMapping("/repaydetails")
    public String repaydetails(@RequestParam("id") String id, ModelMap mmap) {
        Withdraw withdraw = withdrawService.selectWithdrawById(Long.valueOf(id));
        mmap.put("withdraw", withdraw);
        return "customer/repayment/repaydetails";
    }

    /**
     * 新增提款申请
     */
    @GetMapping("/add")
    public String add() {
        return "customer/withdraw/add";
    }

    /**
     * 新增保存提款申请
     */
    @Log(title = "提款申请", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Withdraw withdraw) {
        int result = withdrawService.insertWithdraw(withdraw);
        return toAjax(result);
    }

    /**
     * 新增提款申请
     */
    @Log(title = "提款申请", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> save(@RequestBody Map<String, String> map) {
        Supplier Supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        map.put("billId", Supplier.getBillId());
        Map<String, Object> result = withdrawService.save(map);
        return result;
    }

    /**
     * 修改提款申请
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Withdraw withdraw = withdrawService.selectWithdrawById(id);
        mmap.put("withdraw", withdraw);
        return "customer/withdraw/edit";
    }

    /**
     * 修改保存提款申请
     */
    @Log(title = "提款申请", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Withdraw withdraw) {
        int result = withdrawService.updateWithdraw(withdraw);
        return toAjax(result);
    }

    /**
     * 删除提款申请
     */
    @Log(title = "提款申请", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        int result = withdrawService.deleteWithdrawByIds(ids);
        return toAjax(result);
    }

}
