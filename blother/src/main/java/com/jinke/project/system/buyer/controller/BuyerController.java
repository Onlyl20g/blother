package com.jinke.project.system.buyer.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.buyer.domain.Buyer;
import com.jinke.project.system.buyer.service.IBuyerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 买方(nc)信息操作处理
 *
 * @author jinke
 * @date 2019-08-09
 */
@Controller
@RequestMapping("/system/buyer")
public class BuyerController extends BaseController {

    private final static Logger log = LoggerFactory.getLogger(BuyerController.class);

    private String prefix = "system/buyer";

    @Autowired
    private IBuyerService buyerService;

    @RequiresPermissions("system:buyer:view")
    @GetMapping()
    public String buyer() {
        return prefix + "/buyer";
    }

    /**
     * 查询买方(nc)列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Buyer buyer) {
        startPage();
        List<Buyer> list = buyerService.selectBuyerList(buyer);
//        log.info("[POST]:/system/buyer/list" + "\r\n" + "[params]:" + buyer + "\r\n [result]:" + list);
        return getDataTable(list);
    }


    /**
     * 导出买方(nc)列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Buyer buyer) {
        List<Buyer> list = buyerService.selectBuyerList(buyer);
        ExcelUtil<Buyer> util = new ExcelUtil<Buyer>(Buyer.class);
        return util.exportExcel(list, "buyer");
    }

    /**
     * 新增买方(nc)
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存买方(nc)
     */
    @Log(title = "买方(nc)", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Buyer buyer) {
        int result = buyerService.insertBuyer(buyer);
//        log.info("[POST]:/system/buyer/add" + "\r\n" + "[params]:" + buyer + "\r\n [result]:" + result);
        return toAjax(result);
    }

    /**
     * 修改买方(nc)
     */
    @GetMapping("/edit/{buyerId}")
    public String edit(@PathVariable("buyerId") String buyerId, ModelMap mmap) {
        Buyer buyer = buyerService.selectBuyerById(buyerId);
        mmap.put("buyer", buyer);
//        log.info("[POST]:/system/buyer/edit/{buyerId}" + "\r\n" + "[params]:" + buyerId + "\r\n [result]:" + buyer);
        return prefix + "/edit";
    }

    /**
     * 修改保存买方(nc)
     */
    @Log(title = "买方(nc)", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Buyer buyer) {
        int result = buyerService.updateBuyer(buyer);
//        log.info("[POST]:/system/buyer/edit" + "\r\n" + "[params]:" + buyer + "\r\n [result]:" + result);
        return toAjax(result);
    }

    /**
     * 删除买方(nc)
     */
    @RequiresPermissions("system:buyer:remove")
    @Log(title = "买方(nc)", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        int result = buyerService.deleteBuyerByIds(ids);
//        log.info("[POST]:/system/buyer/remove" + "\r\n" + "[params]:" + ids + "\r\n [result]:" + result);
        return toAjax(result);
    }

}
