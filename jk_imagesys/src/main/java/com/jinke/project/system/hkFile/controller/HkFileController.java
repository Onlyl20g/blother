package com.jinke.project.system.hkFile.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.hkFile.domain.HkFile;
import com.jinke.project.system.hkFile.service.IHkFileService;
import com.jinke.project.system.shareFile.domain.ShareFile;
import com.jinke.project.system.shareFile.service.IShareFileService;
import com.jinke.project.system.upload.service.UploadFileService;
import com.jinke.project.tool.tools.ConfigEntity;
import com.jinke.project.tool.tools.GeneralMethods;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统上传文件 信息操作处理
 *
 * @author jinke
 * @date 2019-10-16
 */
@Controller
@RequestMapping("/system/hkFile")
public class HkFileController extends BaseController {
    private String prefix = "system/hkFile";

    @Autowired
    private IShareFileService iShareFileService;

    @Autowired
    private IHkFileService hkFileService;

    @GetMapping()
    public String hkFile() {
        return prefix + "/hkFile";
    }

    @Autowired
    private UploadFileService uploadFileService;

    /**
     * 查询系统上传文件列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(HkFile hkFile) {
        startPage();
        List<HkFile> list = hkFileService.selectHkFileList(hkFile);
        return getDataTable(list);
    }

    @PostMapping("/listAll")
    @ResponseBody
    public List listAll(HkFile hkFile) {
        ShareFile shareFile = new ShareFile();
        shareFile.setLink(hkFile.getBillId());
        List<ShareFile> shareFiles = iShareFileService.selectShareFileList(shareFile);
        List<HkFile> list = null;
        if (shareFiles.size() > 0) {
            Date date = shareFiles.get(0).getValidTime();
            Date newDate = new Date();
            int compareTo = date.compareTo(newDate);
            if (compareTo >= 0) {
                list = hkFileService.selectHkFileList(hkFile);
            }
        }
        return list;
    }

    @PostMapping("/findOtherSysFile")
    @ResponseBody
    public List findOtherSysFile(HkFile hkFile) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        List<HkFile> list = null;
        if (principal != null && !"".equals(hkFile.getBillId()) && !"".equals(hkFile.getCode()) && !"".equals(hkFile.getStr2())) {
            list = hkFileService.selectHkFileListByCode(hkFile);
        }
        return list;
    }

    @PostMapping("/linkOtherSys")
    @ResponseBody
    public List linkOtherSys(String u, HkFile hkFile) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        List<HkFile> list = null;
        list = hkFileService.selectHkFileList(hkFile);
        return list;
    }

    @PostMapping("/listFile")
    @ResponseBody
    public List listFile(HkFile hkFile) {
        List<HkFile> list = hkFileService.selectHkFileListBlockName(hkFile);
        return list;
    }

    /**
     * 查询业务申请
     *
     * @param hkFile
     * @return
     */
    @PostMapping("/listBillId")
    @ResponseBody
    public List listBillId(HkFile hkFile) {
        List<HkFile> list = hkFileService.selectHkFileListByBillId(hkFile);
        return list;
    }


    /**
     * 根据id查找文件信息
     *
     * @param hkFile
     * @param request
     * @return
     */
    @PostMapping("/findHKById")
    @ResponseBody
    public Map findHKById(HkFile hkFile, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HkFile hkFile1 = hkFileService.selectHkFileById(hkFile.getId());
        insertShareFile(hkFile1.getBillId());
//        String url =  Propertie.getProperties("yxip");
        String url = GeneralMethods.getUrl(request);
        map.put("hkFile", hkFile1);
        map.put("url", url);
        return map;
    }

    /**
     * 根据id查找文件信息
     *
     * @param hkFile
     * @return
     */
    @PostMapping("/findHKFileById")
    @ResponseBody
    public Map findHKFileById(HkFile hkFile) {
        Map<String, Object> map = new HashMap<>();
        HkFile hkFile1 = hkFileService.selectHkFileById(hkFile.getId());
        map.put("hkFile", hkFile1);
        return map;
    }

    /**
     * 查看文件信息接口
     *
     * @param request
     * @return
     */
    @PostMapping("/shareFileUrl")
    @ResponseBody
    public Map shareFileUrl(String billId, String sysType, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String host = GeneralMethods.getUrl(request);
        String url = host + "/customer/pages/hkwithdraw?s=" + billId + "&t=" + sysType;
        map.put("url", url);
        return map;
    }

    /**
     * 添加分享记录
     *
     * @param billId
     */
    public void insertShareFile(String billId) {
        ShareFile shareFile = new ShareFile();
        shareFile.setLink(billId);
        List<ShareFile> shareFiles = iShareFileService.selectShareFileList(shareFile);
        shareFile.setCreateTime(new Date());
        shareFile.setValidTime(GeneralMethods.addAndSubtractDaysByCalendar(new Date(), 3));
        if (shareFiles.size() == 0) {
            iShareFileService.insertShareFile(shareFile);
        } else {
            shareFile.setId(shareFiles.get(0).getId());
            iShareFileService.updateShareFile(shareFile);
        }
    }

    @PostMapping("/findHKFile")
    @ResponseBody
    public Map findHKFile(HkFile hkFile) {
        Map<String, Object> map = new HashedMap();
        HkFile hkFile1 = hkFileService.selectHkFileById(hkFile.getId());
        map.put("hkFile", hkFile1);
        return map;
    }

    /**
     * 导出系统上传文件列表
     */
//    @RequiresPermissions("system:hkFile:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(HkFile hkFile) {
        List<HkFile> list = hkFileService.selectHkFileList(hkFile);
        ExcelUtil<HkFile> util = new ExcelUtil<HkFile>(HkFile.class);
        return util.exportExcel(list, "hkFile");
    }

    /**
     * 新增系统上传文件
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存系统上传文件
     */
//    @RequiresPermissions("system:hkFile:add")
//    @Log(title = "系统上传文件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HkFile hkFile) {
        return toAjax(hkFileService.insertHkFile(hkFile));
    }

    @PostMapping("/addHkFile")
    @ResponseBody
    public AjaxResult addSaveHkFile(HkFile hkFile, HttpServletRequest request) {

        hkFile.setCreditTime(new Date());
        hkFile.setState("0");
        return toAjax(hkFileService.insertHkFile(hkFile));
    }

    @PostMapping("/uploadHkFile")
    @ResponseBody
    public AjaxResult uploadHkFile(HkFile hkFile, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String url = "";
        try {
            url = uploadFileService.UploadFilesToServer(file);
        } catch (Exception e) {
            e.printStackTrace();
            return toAjax(0);
        }
        hkFile.setFilePath(url);
        hkFile.setCreditTime(new Date());
        hkFile.setState("0");
        return toAjax(hkFileService.insertHkFile(hkFile));
    }

    /**
     * 修改系统上传文件
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        HkFile hkFile = hkFileService.selectHkFileById(id);
        mmap.put("hkFile", hkFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统上传文件
     */
//    @RequiresPermissions("system:hkFile:edit")
    @Log(title = "系统上传文件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HkFile hkFile, HttpServletRequest request) {
        return toAjax(hkFileService.updateHkFile(hkFile));
    }

    /**
     * 删除系统上传文件
     */
//    @RequiresPermissions("system:hkFile:remove")
    @Log(title = "系统上传文件", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids, HttpServletRequest request) {
        int row = hkFileService.deleteHkFileByIds(ids);
        return toAjax(row);
    }

    /**
     * 删除系统上传文件
     */
//    @RequiresPermissions("system:hkFile:remove")
    @Log(title = "系统上传文件", businessType = BusinessType.DELETE)
    @PostMapping("/findPath")
    @ResponseBody
    public Map findPath(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object subject = session.getAttribute("subject");
        Map<String, Object> map = new HashMap<>();
        String host = ConfigEntity.getHost().equals("") ? request.getHeader("Host") : ConfigEntity.getHost();
        map.put("host", host);
        map.put("subject", subject);
        return map;
    }

    /**
     * 删除系统上传文件
     */
//    @RequiresPermissions("system:hkFile:remove")
    @Log(title = "系统上传文件", businessType = BusinessType.DELETE)
    @PostMapping("/isSameName")
    @ResponseBody
    public Map isSameName(HkFile hkFile) {
        Integer result = 1;
        String[] fileNames = hkFile.getFileFullName().split("\\,");
        Map<String, Object> map = new HashMap<>();
        for (String fileName : fileNames) {
            hkFile.setFileFullName(fileName);
            List<HkFile> hkFiles = hkFileService.selectHkFileList(hkFile);
            if (hkFiles.size() > 0) {
                map.put("result", 0);
                return map;
            }
        }
        map.put("result", result);
        return map;
    }

}
