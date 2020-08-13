package com.jinke.project.system.ocr.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.customer.domain.Enterprise;
import com.jinke.project.system.file.service.IFileService;
import com.jinke.project.system.ocr.domain.Ocr;
import com.jinke.project.system.ocr.service.IOcrService;
import com.jinke.project.system.upload.service.UploadFileService;
import com.jinke.project.tool.fastdfs.FastDFSClient;
import com.jinke.project.tool.fastdfs.ImageApiService;
import com.jinke.project.tool.fastdfs.VatInvoiceParse;
import com.jinke.project.tool.tools.GeneralMethods;
import com.jinke.project.tool.tools.OcrProcess;
import com.jinke.project.tool.tools.PublicUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ocr 信息操作处理
 *
 * @author jinke
 * @date 2019-09-09
 */
@Controller
@RequestMapping("/ocr")
public class OcrController extends BaseController {
    private String prefix = "ocr";

    @Autowired
    private IOcrService ocrService;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private ImageApiService imageApiService;

    @Autowired
    private FastDFSClient fastDFSClient;

    //    @RequiresPermissions("system:ocr:view")
    @GetMapping()
    public String ocr() {
        return prefix + "/ocr";
    }

    /**
     * 查询ocr列表
     */
//    @RequiresPermissions("system:ocr:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Ocr ocr) {
        startPage();
        List<Ocr> list = ocrService.selectOcrList(ocr);
        return getDataTable(list);
    }

    @PostMapping("/parseImage")
    @ResponseBody
    public Map ocrImg(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashedMap();
        File file1 = PublicUtil.MultipartFileToFile(file);
        String data = OcrProcess.ocrImg(file1);
        file1.delete();
        map.put("result", data);

        return map;
    }


    /**
     * 导出ocr列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Ocr ocr) {
        List<Ocr> list = ocrService.selectOcrList(ocr);
        ExcelUtil<Ocr> util = new ExcelUtil<Ocr>(Ocr.class);
        return util.exportExcel(list, "ocr");
    }

    /**
     * 新增ocr
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存ocr
     */
//    @RequiresPermissions("system:ocr:add")
    @Log(title = "ocr", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Ocr ocr) {
        return toAjax(ocrService.insertOcr(ocr));
    }


    /**
     * 保存解析结果
     *
     * @param request
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Map saveResult(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        String businessType = request.getParameter("businessType");
        String dataSource = request.getParameter("dataSource");
        String result = request.getParameter("result");
//        String url = uploadFileService.UploadFilesToServer(file);
//        int row = fileService.insertOtherFile(file,url);
        Ocr ocr = new Ocr();
        ocr.setResult(result);
        ocr.setBusinessType(businessType);
        ocr.setDataSource(dataSource);
        int row = ocrService.insertOcr(ocr);
        Map<String, Object> map = new HashMap();
        if (row > 0) {
            map.put("code", 200);
        } else {
            map.put("code", 400);
        }
        return map;
    }

    @PostMapping("/saveFile")
    @ResponseBody
    public Map saveFile(@RequestParam(value = "fileAddressId") String fileAddressId, @RequestParam(value = "RemoteFile", required = false) MultipartFile file) throws Exception {
        String url = fastDFSClient.uploadFile(file);
        int row = fileService.insertSaveFile(fileAddressId, url, file);
        Map<String, Object> map = new HashMap();
        if (row > 0) {
            map.put("code", "200");
        } else {
            map.put("code", "400");
        }
        return map;
    }

    @PostMapping("/parseBusiness")
    @ResponseBody
    public Map saveBusiness(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        String data = imageApiService.parseLicense(bytes, null);
        Enterprise enterprise = GeneralMethods.styleEnterprise(data);
        Map<String, Object> map = new HashMap();
        map.put("result", enterprise);
        return map;
    }

    @PostMapping("/parseInvoice")
    @ResponseBody
    public Map parseInvoice(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        String result = VatInvoiceParse.parseVatInvoice(file);
        Map<String, Object> map = new HashMap();
        map.put("result", result);
        return map;
    }

    /**
     * 修改ocr
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Ocr ocr = ocrService.selectOcrById(id);
        mmap.put("ocr", ocr);
        return prefix + "/edit";
    }

    /**
     * 修改保存ocr
     */
//    @RequiresPermissions("system:ocr:edit")
    @Log(title = "ocr", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Ocr ocr) {
        return toAjax(ocrService.updateOcr(ocr));
    }

    /**
     * 删除ocr
     */
//    @RequiresPermissions("system:ocr:remove")
    @Log(title = "ocr", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(ocrService.deleteOcrByIds(ids));
    }

}
