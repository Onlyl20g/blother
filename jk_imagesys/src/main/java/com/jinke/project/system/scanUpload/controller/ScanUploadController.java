package com.jinke.project.system.scanUpload.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.scanUpload.domain.ScanUpload;
import com.jinke.project.system.scanUpload.service.IScanUploadService;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.tool.tools.ConfigEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 二维码上传 信息操作处理
 *
 * @author jinke
 * @date 2019-11-25
 */
@Controller
@RequestMapping("/system/scanUpload")
public class ScanUploadController extends BaseController {
    private String prefix = "system/scanUpload";

    @Autowired
    private IScanUploadService scanUploadService;

    @GetMapping()
    public String scanUpload() {
        return prefix + "/scanUpload";
    }

    /**
     * 查询二维码上传列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ScanUpload scanUpload) throws ParseException {
        startPage();
        String links = scanUpload.getSanLink();
        links = scanUpload.getSanLink().replace("amp;", "");
        scanUpload.setSanLink(links);
        String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date validTime = sdf.parse(formatStr);
        scanUpload.setValidTime(validTime);
        List<ScanUpload> list = scanUploadService.selectScanUploadList(scanUpload);
        return getDataTable(list);
    }


    /**
     * 导出二维码上传列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScanUpload scanUpload) {
        List<ScanUpload> list = scanUploadService.selectScanUploadList(scanUpload);
        ExcelUtil<ScanUpload> util = new ExcelUtil<ScanUpload>(ScanUpload.class);
        return util.exportExcel(list, "scanUpload");
    }

    /**
     * 新增二维码上传
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存二维码上传
     */
    @Log(title = "二维码上传", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addSave(ScanUpload scanUpload, HttpServletRequest request) throws ParseException {
        String sanLink = request.getParameter("sanLink");
        String host = ConfigEntity.getHost().equals("") ? request.getHeader("Host") : ConfigEntity.getHost();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        scanUpload.setUserBusinessId(user.getBusinessId());
        String businessId = UUID.randomUUID().toString().replaceAll("-", "");//业务id
        scanUpload.setBusinessId(businessId);
        Date date = new Date();
        scanUpload.setCreateTime(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, +30);
        date = calendar.getTime();
        String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date validTime = sdf.parse(formatStr);
        scanUpload.setValidTime(validTime);
        InetAddress address = null;//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String hostAddress = address.getHostAddress();
        scanUpload.setSanLink(request.getScheme() + "://" + host + sanLink + "?u=" + businessId);
        int i = scanUploadService.insertScanUpload(scanUpload);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (i == 1) {
            try {
                map.put("host", request.getScheme() + "://" + host);
                map.put("uuid", businessId);
                return map;
                //return request.getScheme() + "://" + host;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 修改二维码上传
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        ScanUpload scanUpload = scanUploadService.selectScanUploadById(id);
        mmap.put("scanUpload", scanUpload);
        return prefix + "/edit";
    }

    /**
     * 修改保存二维码上传
     */
    @Log(title = "二维码上传", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScanUpload scanUpload) {
        return toAjax(scanUploadService.updateScanUpload(scanUpload));
    }

    /**
     * 删除二维码上传
     */
    @Log(title = "二维码上传", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scanUploadService.deleteScanUploadByIds(ids));
    }

}
