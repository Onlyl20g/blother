package com.jinke.project.system.bankCardInfo.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.shiro.service.PasswordService;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.bankCardInfo.domain.BankCardInfo;
import com.jinke.project.system.bankCardInfo.domain.BankCardInfoDto;
import com.jinke.project.system.bankCardInfo.service.IBankCardInfoService;
import com.jinke.project.system.supplier.domain.Supplier;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 银行卡信息操作处理
 *
 * @author jinke
 * @date 2019-08-22
 */
@Controller
@RequestMapping("/system/bankCardInfo")
public class BankCardInfoController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(BankCardInfoController.class);
    private String prefix = "system/bankCardInfo";

    @Autowired
    private IBankCardInfoService bankCardInfoService;

    @Autowired
    private PasswordService passwordService;

    //@RequiresPermissions("system:bankCardInfo:view")
    @GetMapping()
    public String bankCardInfo() {
        return prefix + "/bankCardInfo";
    }

    /**
     * 查询银行卡列表
     */
    //@RequiresPermissions("system:bankCardInfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BankCardInfo bankCardInfo) {
        startPage();
        Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        bankCardInfo.setDeleteFlag("0");
        bankCardInfo.setSupplierBillId(supplier.getBillId());
        List<BankCardInfo> list = bankCardInfoService.selectBankCardInfoList(bankCardInfo);
        List<BankCardInfoDto> bankCardInfoDtos = new ArrayList<>();
        for (BankCardInfo bankCardInfo1 : list) {
            BankCardInfoDto bankCardInfoDto = new BankCardInfoDto();
            BeanUtils.copyProperties(bankCardInfo1, bankCardInfoDto);
            bankCardInfoDtos.add(bankCardInfoDto);
        }
//        log.info("[POST]:/system/bankCardInfo/list" + "\r\n" + "[params]:" + bankCardInfo + "\r\n [result]:" + bankCardInfoDtos);
        return getDataTable(list);
    }

    @PostMapping("/findBankList")
    @ResponseBody
    public Map list() {
        BankCardInfo bankCardInfo = new BankCardInfo();
        Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        bankCardInfo.setDeleteFlag("0");
        bankCardInfo.setSupplierBillId(supplier.getBillId());
        List<BankCardInfo> list = bankCardInfoService.selectBankCardInfoList(bankCardInfo);
        List<BankCardInfoDto> bankCardInfoDtos = new ArrayList<>();
        for (BankCardInfo bankCardInfo1 : list) {
            BankCardInfoDto bankCardInfoDto = new BankCardInfoDto();
            BeanUtils.copyProperties(bankCardInfo1, bankCardInfoDto);
            bankCardInfoDtos.add(bankCardInfoDto);
        }
        Map<String, Object> map = new HashMap();
        map.put("data", bankCardInfoDtos);
//        log.info("[POST]:/system/bankCardInfo/findBankList" + "\r\n" + "[params]:" + "" + "\r\n [result]:" + map);
        return map;
    }

    @PostMapping("/findBankInfo")
    @ResponseBody
    public Boolean findBankCard(BankCardInfo bankCardInfo) {
        Boolean b = true;
        BankCardInfo bankCard = bankCardInfoService.selectBankCard(bankCardInfo.getBankCard());
        if (bankCard != null) {
            b = false;
        }
//        log.info("[POST]:/system/bankCardInfo/findBankInfo" + "\r\n" + "[params]:" + bankCardInfo + "\r\n [result]:" + b);
        return b;
    }

    /**
     * 导出银行卡列表
     */
    //@RequiresPermissions("system:bankCardInfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BankCardInfo bankCardInfo) {
        List<BankCardInfo> list = bankCardInfoService.selectBankCardInfoList(bankCardInfo);
        ExcelUtil<BankCardInfo> util = new ExcelUtil<BankCardInfo>(BankCardInfo.class);
        return util.exportExcel(list, "bankCardInfo");
    }

    /**
     * 新增银行卡
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存银行卡
     */
    //@RequiresPermissions("system:bankCardInfo:add")
    @Log(title = "银行卡", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BankCardInfo bankCardInfo) {
        Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        if (bankCardInfo.getBankCard() == null || bankCardInfo.getBankCard().length() < 5) {
            return AjaxResult.warn("请输入有效的收款账户");
        }
        Pattern p = Pattern.compile("[a-zA-Z0-9_\\-]*");
        Matcher m = p.matcher(bankCardInfo.getBankCard());
        if (!m.matches()) {
            return AjaxResult.warn("请输入有效的收款账户");
        }
        if (validatePassword(supplier, bankCardInfo.getStr5())) {
            if (isBankCardExist(bankCardInfo.getBankCard())) {
                return AjaxResult.warn("已存在该收款账户");
            }
            bankCardInfo.setStr5(null);
            bankCardInfo.setSupplierBillId(supplier.getBillId());
            bankCardInfo.setBillId(UUID.randomUUID().toString().replaceAll("-", ""));
            bankCardInfo.setCreateTime(new Date());
            bankCardInfo.setDeleteFlag("0");
            int result = bankCardInfoService.insertBankCardInfo(bankCardInfo);
//            log.info("[POST]:/system/bankCardInfo/add" + "\r\n" + "[params]:" + bankCardInfo + "\r\n [result]:" + result);
            return toAjax(result);
        } else {
//            log.info("[POST]:/system/bankCardInfo/add" + "\r\n" + "[params]:" + bankCardInfo + "\r\n [result]:" + error());
            return error();
        }
    }

    @Log(title = "银行卡", businessType = BusinessType.INSERT)
    @PostMapping("/addBank")
    @ResponseBody
    public Map addSaveBank(BankCardInfo bankCardInfo) {
        Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        bankCardInfo.setSupplierBillId(supplier.getBillId());
        bankCardInfo.setBillId(UUID.randomUUID().toString().replaceAll("-", ""));
        bankCardInfo.setCreateTime(new Date());
        bankCardInfo.setDeleteFlag("0");
        int row = bankCardInfoService.insertBankCardInfo(bankCardInfo);
        Map<String, Object> map = new HashMap();
        if (row > 0) {
            map.put("flag", "success");
            map.put("msg", bankCardInfo.getId());
        } else {
            map.put("flag", "failed");
            map.put("msg", "添加失败");
        }
//        log.info("[POST]:/system/bankCardInfo/addBank" + "\r\n" + "[params]:" + bankCardInfo + "\r\n [result]:" + map);
        return map;
    }

    /**
     * 修改银行卡
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BankCardInfo bankCardInfo = bankCardInfoService.selectBankCardInfoById(id);
        mmap.put("bankCardInfo", bankCardInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存银行卡
     */
    //@RequiresPermissions("system:bankCardInfo:edit")
    @Log(title = "银行卡", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BankCardInfo bankCardInfo) {
        int result = bankCardInfoService.updateBankCardInfo(bankCardInfo);
        return toAjax(result);
    }

    /**
     * 假删除银行卡
     */
    //@RequiresPermissions("system:bankCardInfo:edit")
    @Log(title = "银行卡", businessType = BusinessType.UPDATE)
    @PostMapping("/deleteBankCard")
    @ResponseBody
    public AjaxResult deleteSave(BankCardInfo bankCardInfo) {
        bankCardInfo.setDeleteFlag("1");
        int result = bankCardInfoService.updateBankCardInfoByBankCard(bankCardInfo);
//        log.info("[POST]:/system/bankCardInfo/deleteBankCard" + "\r\n" + "[params]:" + bankCardInfo + "\r\n [result]:" + result);
        return toAjax(result);
    }

    /**
     * 删除银行卡
     */
    //@RequiresPermissions("system:bankCardInfo:remove")
    @Log(title = "银行卡", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        int result = bankCardInfoService.deleteBankCardInfoByIds(ids);
        return toAjax(result);
    }

    @PostMapping("/isPwd")
    @ResponseBody
    public Map isPwd(@RequestParam("password") String password) {
        Map<String, Object> map = new HashMap();
        Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        boolean result = validatePassword(supplier, password);
        if (result) {
            map.put("result", true);
        } else {
            map.put("result", false);
        }
//        log.info("[POST]:/system/bankCardInfo/isPwd" + "\r\n" + "[params]:" + password + "\r\n [result]:" + map);
        return map;
    }


    /**
     * 验证密码是否正确
     *
     * @param supplier
     * @param password
     * @return
     */
    public boolean validatePassword(Supplier supplier, String password) {
        if (supplier.getPassword().equals(passwordService.encryptPassword(supplier.getBusinessLicenseCode(), password, supplier.getSalt()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断银行卡是否存在
     *
     * @param code
     * @return 存在 true 不存在 false
     */
    public boolean isBankCardExist(String code) {
        BankCardInfo bankCardInfo = new BankCardInfo();
        bankCardInfo.setBankCard(code);
        List<BankCardInfo> bankCardInfos = bankCardInfoService.selectBankCardInfoList(bankCardInfo);
        if (bankCardInfos.size() > 0) {
//            log.info("[POST]:/system/bankCardInfo/isPwd" + "\r\n" + "[params]:" + code + "\r\n [result]:" + true);
            return true;
        } else {
//            log.info("[POST]:/system/bankCardInfo/isPwd" + "\r\n" + "[params]:" + code + "\r\n [result]:" + false);
            return false;
        }
    }

}
