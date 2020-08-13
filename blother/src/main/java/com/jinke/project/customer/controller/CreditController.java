package com.jinke.project.customer.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.customer.domain.Credit;
import com.jinke.project.customer.domain.CreditDto;
import com.jinke.project.customer.service.ICreditService;
import com.jinke.project.system.filepath.domain.Filepath;
import com.jinke.project.system.filepath.domain.FilepathDto;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.upload.service.UploadFileService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 授信申请信息操作处理
 *
 * @author jinke
 * @date 2019-07-17
 */
@Controller
@RequestMapping("/credit")
public class CreditController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(CreditController.class);

    @Autowired
    private ICreditService creditService;

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping()
    public String credit() {
        /*Supplier supplier = (Supplier) SecurityUtils.getSubject().getPrincipal();
        if (supplier.getUpdateBy() == null || !supplier.getBusinessLicenseCode().equals(supplier.getUpdateBy())) {
            return "customer/index";
        }*/
        return "customer/credit/credit";
    }

    /**
     * 查询授信申请列表
     */
    @PostMapping("list")
    @ResponseBody
    public TableDataInfo list(Credit credit) {
        startPage();
        List<CreditDto> list = creditService.selectCreditList(credit);
        return getDataTable(list);
    }

    /**
     * 通过billId查询授信申请列表
     */
    @PostMapping("listByBillId")
    @ResponseBody
    public TableDataInfo listByUsername() {
        Supplier sup = (Supplier) SecurityUtils.getSubject().getPrincipal();
        startPage();
        List<CreditDto> list = creditService.selectCreditListByUsername(sup.getBillId());
        return getDataTable(list);
    }

    /**
     * 通过billid查询授信通过申请列表
     */
    @PostMapping("selectSuccessByBillId")
    @ResponseBody
    public TableDataInfo selectById() {
        startPage();
        Supplier sup = (Supplier) SecurityUtils.getSubject().getPrincipal();
        List<CreditDto> list = creditService.selectSuccessCreditListByUsername(sup.getBillId());
        return getDataTable(list);
    }

    /**
     * 通过id查询授信申请列表
     */
    @PostMapping("selectByBillId")
    @ResponseBody
    public TableDataInfo selectById(HttpServletRequest request) {
        Credit credit = new Credit();
        credit.setBillId(request.getParameter("billId"));
        List<CreditDto> list = creditService.selectCreditList(credit);
        return getDataTable(list);
    }

    /**
     * 导出授信申请列表
     */
    @PostMapping("export")
    @ResponseBody
    public AjaxResult export(Credit credit) {
        List<CreditDto> list = creditService.selectCreditList(credit);
        ExcelUtil<CreditDto> util = new ExcelUtil<>(CreditDto.class);
        return util.exportExcel(list, "credit");
    }

    /**
     * 新增授信申请
     */
    @GetMapping("add")
    public String add() {
        return "customer/credit/add";
    }

    /**
     * 修改授信申请
     */
    @GetMapping("edit")
    public String edit() {
        return "customer/credit/edit";
    }

    /**
     * 修改授信申请
     */
    @GetMapping("showCredit")
    public String showCredit() {
        return "customer/credit/showCredit";
    }

    /**
     * 新增保存授信申请
     */
    @Log(title = "授信申请", businessType = BusinessType.INSERT)
    @PostMapping("add")
    @ResponseBody
    public AjaxResult addSave(Credit credit) {
        int row = creditService.insertCredit(credit);
        return toAjax(row);
    }

    /**
     * 修改授信申请
     */
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Credit credit = creditService.selectCreditById(id);
        CreditDto creditDto = new CreditDto();
        BeanUtils.copyProperties(credit, creditDto);
        mmap.put("credit", creditDto);
        return "customer/credit/edit";
    }

    /**
     * 修改保存授信申请
     */
    @Log(title = "授信申请", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult editSave(Credit credit) {
        int result = creditService.updateCredit(credit);
        return toAjax(result);
    }

    /**
     * 资料补交
     */
    @PostMapping("saveEdit")
    @ResponseBody
    public int saveEdit(@RequestBody Map<String, String> map) {
        int result = creditService.saveEdit(map);
        return result;
    }

    /**
     * 删除授信申请
     */
    @Log(title = "授信申请", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        int result = creditService.deleteCreditByIds(ids);
        return toAjax(result);
    }


    /**
     * 新增授信申请
     */
    @PostMapping("save")
    @ResponseBody
    public Map<String, Object> save(@RequestBody Map<String, String> map) {
        Map<String, Object> result = creditService.save(map);
        return result;
    }

    /**
     * 新增授信申请附表
     */
    @PostMapping("saveCredits")
    @ResponseBody
    public Map<String, Object> saveCredits(@RequestBody List<Map<String, String>> map) {
        Map<String, Object> result = creditService.saveCredits(map);
        return result;
    }

    /**
     * 查询授信申请成功列表
     */
    @PostMapping("sucList")
    @ResponseBody
    public TableDataInfo sucList(Credit credit) {
        startPage();
        List<Credit> list = creditService.selectCreditSucList(credit);
        List<CreditDto> creditDtos = new ArrayList<>();
        for (Credit credit1 : list) {
            CreditDto creditDto = new CreditDto();
            BeanUtils.copyProperties(credit1, creditDto);
            creditDtos.add(creditDto);
        }
        return getDataTable(creditDtos);
    }

    /**
     * 通过billId查询授信申请成功列表
     */
    @PostMapping("creditPassList")
    @ResponseBody
    public TableDataInfo creditPassList(Credit credit) {
        startPage();
        List<Credit> list = creditService.selectCreditSucList(credit);
        List<CreditDto> creditDtos = new ArrayList<>();
        for (Credit credit1 : list) {
            CreditDto creditDto = new CreditDto();
            BeanUtils.copyProperties(credit1, creditDto);
            creditDtos.add(creditDto);
        }
        return getDataTable(creditDtos);
    }

    /**
     * 查询文件列表
     */
    @PostMapping("selectFile")
    @ResponseBody
    public TableDataInfo selectFileList(HttpServletRequest request) {
        List<Filepath> filepaths = uploadFileService.selectList(request.getParameter("fileId"));
        List<FilepathDto> filepathDtos = new ArrayList<>();
        for (Filepath filepath : filepaths) {
            FilepathDto filepathDto = new FilepathDto();
            BeanUtils.copyProperties(filepath, filepathDto);
            filepathDtos.add(filepathDto);
        }
        return getDataTable(filepathDtos);
    }
}
