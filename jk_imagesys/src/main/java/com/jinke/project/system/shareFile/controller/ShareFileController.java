package com.jinke.project.system.shareFile.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.shareFile.domain.ShareFile;
import com.jinke.project.system.shareFile.service.IShareFileService;
import com.jinke.project.tool.tools.ConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 文件分享 信息操作处理
 *
 * @author jinke
 * @date 2019-10-09
 */
@Controller
@RequestMapping("/system/shareFile")
public class ShareFileController extends BaseController {
    private String prefix = "system/shareFile";

    @Autowired
    private IShareFileService shareFileService;

    @GetMapping()
    public String shareFile() {
        return prefix + "/shareFile";
    }

    /**
     * 查询文件分享列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ShareFile shareFile) throws ParseException {
        startPage();
        String links = shareFile.getLink();
        links = shareFile.getLink().replace("amp;", "");
        shareFile.setLink(links);
        String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date validTime = sdf.parse(formatStr);
        shareFile.setValidTime(validTime);
        List<ShareFile> list = shareFileService.selectShareFileList(shareFile);
        return getDataTable(list);
    }

    /**
     * 导出文件分享列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ShareFile shareFile) {
        List<ShareFile> list = shareFileService.selectShareFileList(shareFile);
        ExcelUtil<ShareFile> util = new ExcelUtil<ShareFile>(ShareFile.class);
        return util.exportExcel(list, "shareFile");
    }

    /**
     * 新增文件分享
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存文件分享
     */
    @Log(title = "文件分享", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public String addSave(ShareFile shareFile, HttpServletRequest request) throws ParseException {
        String day = request.getParameter("objectTime");
        String link = request.getParameter("linkId");
        String host = ConfigEntity.getHost().equals("") ? request.getHeader("Host") : ConfigEntity.getHost();
        int Intday = Integer.parseInt(day);
        Date date = new Date();
        shareFile.setCreateTime(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +Intday);//+1今天的时间加一天
        date = calendar.getTime();
        String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date validTime = sdf.parse(formatStr);
        shareFile.setValidTime(validTime);
        InetAddress address = null;//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String hostAddress = address.getHostAddress();
        shareFile.setLink(request.getScheme() + "://" + host + link);
        int i = shareFileService.insertShareFile(shareFile);
        if (i == 1) {
            try {
                return request.getScheme() + "://" + host;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 修改文件分享
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        ShareFile shareFile = shareFileService.selectShareFileById(id);
        mmap.put("shareFile", shareFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件分享
     */
    @Log(title = "文件分享", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ShareFile shareFile) {
        return toAjax(shareFileService.updateShareFile(shareFile));
    }

    /**
     * 删除文件分享
     */
    @Log(title = "文件分享", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(shareFileService.deleteShareFileByIds(ids));
    }

}
