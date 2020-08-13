package com.jinke.project.system.folder.controller;

import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.aspectj.lang.annotation.Log;
import com.jinke.framework.aspectj.lang.enums.BusinessType;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.file.domain.File;
import com.jinke.project.system.file.service.IFileService;
import com.jinke.project.system.folder.domain.Folder;
import com.jinke.project.system.folder.service.IFolderService;
import com.jinke.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 目录 信息操作处理
 *
 * @author jinke
 * @date 2019-09-09
 */
@Controller
@RequestMapping("/system/folder")
public class FolderController extends BaseController {
    private String prefix = "system/folder";

    @Autowired
    private IFolderService folderService;

    @Autowired
    private IFileService fileService;

    @GetMapping()
    public String folder() {
        return prefix + "/folder";
    }

    /**
     * 查询目录列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Folder folder) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (folder == null || "".equals(folder.getBusinessId())) {
            folder.setBusinessPid(user.getBusinessId());
        }
        folder.setUserBisId(user.getBusinessId());
        folder.setDelFlag("0");
        List<Folder> list = folderService.selectFolderList(folder);
        return getDataTable(list);
    }

    @PostMapping("/listTop")
    @ResponseBody
    public TableDataInfo listTop() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Folder folder = new Folder();
        folder.setUserBisId(user.getBusinessId());
        folder.setBusinessPid(user.getBusinessId());
        folder.setDelFlag("0");
        return getDataTable(folderService.selectFolderList(folder));
    }

    /**
     * 分享界面文件夹
     *
     * @return
     */
    @PostMapping("/listTopShare")
    @ResponseBody
    public TableDataInfo listTopShare(Folder folder) {
        if (folder.getBusinessId() == null || "".equals(folder.getBusinessId())) {
            folder.setBusinessId("~");
        }
        folder.setDelFlag("0");
        return getDataTable(folderService.selectFolderList(folder));
    }

    @PostMapping("/listBackTop")
    @ResponseBody
    public TableDataInfo listBackTop() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Folder folder = new Folder();
        folder.setUserBisId(user.getBusinessId());
        //folder.setBusinessPid(user.getBusinessId());
        folder.setDelFlag("3");//加載刪除状态为3的文件夹
        return getDataTable(folderService.selectFolderList(folder));
    }

    @PostMapping("/listByBusinessPid")
    @ResponseBody
    public TableDataInfo listByBusinessPid(Folder folder) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (folder.getBusinessPid() == null || "".equals(folder.getBusinessPid())) {
            folder.setBusinessPid(user.getBusinessId());
        }
        folder.setUserBisId(user.getBusinessId());
        folder.setDelFlag("0");
        List<Folder> list = folderService.selectFolderListByBusinessPid(folder);
        return getDataTable(list);
    }

    @PostMapping("/listByBusinessPidShare")
    @ResponseBody
    public TableDataInfo listByBusinessPidShare(Folder folder) {
        folder.setDelFlag("0");
        List<Folder> list = folderService.selectFolderListByBusinessPid(folder);
        return getDataTable(list);
    }

    /**
     * 导出目录列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Folder folder) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        folder.setUserBisId(user.getBusinessId());
        List<Folder> list = folderService.selectFolderList(folder);
        ExcelUtil<Folder> util = new ExcelUtil<Folder>(Folder.class);
        return util.exportExcel(list, "folder");
    }

    /**
     * 新增目录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存目录
     */
    @Log(title = "目录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Folder folder) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (folder.getBusinessPid() == null || "".equals(folder.getBusinessPid())) {
            folder.setBusinessPid(user.getBusinessId());
        }
        folder.setUserBisId(user.getBusinessId());
        //folder.setDelFlag("0,1,3");
        /*List<Folder> list = folderService.selectFolderList(folder);
        if (list.size()!=0) {
            folder.setName(folder.getName()+"("+list.size()+")");
        }*/
        int i = folderService.insertFolder(folder);
        if (i == -1) {
            return error("存在同名文件夹");
        }
        return toAjax(i);
    }

    /**
     * 修改目录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Folder folder = folderService.selectFolderById(id);
        mmap.put("folder", folder);
        return prefix + "/edit";
    }

    /**
     * 修改保存目录
     */
    @Log(title = "目录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Folder folder) {
        return toAjax(folderService.updateFolder(folder));
    }

    /**
     * 删除目录
     */
    @Log(title = "目录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(folderService.deleteFolderByIds(ids));
    }

    /**
     * 删除目录
     */
    @PostMapping("/deleteFolder")
    @ResponseBody
    public AjaxResult deleteFolder(Integer id) {
        File file = new File();
        Folder folder = folderService.selectFolderById(id);
        file.setDelFlag("0");
        file.setFolderBusinessId(folder.getBusinessId());
        List<File> files = fileService.selectFileList(file);
        Folder folder1 = new Folder();
        folder1.setBusinessPid(folder.getBusinessId());
        folder1.setDelFlag("0");
        List<Folder> folders = folderService.selectFolderList(folder1);
        if (files.size() == 0) {
            if (folders.size() == 0) {
                folder.setDelFlag("3");//状态3为删除状态，进入回收站
                return toAjax(folderService.updateFolder(folder));
            }
        }
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("code", "501");
        ajaxResult.put("msg", "该文件夹下有子目录或子文件，请先删除子目录或子文件");
        return ajaxResult;
    }

    /**
     * 查询目录列表
     */
    @PostMapping("/selectPname")
    @ResponseBody
    public TableDataInfo selectPname(Folder folder) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (folder == null || "".equals(folder.getBusinessId())) {
            folder.setBusinessPid(user.getBusinessId());
        }
        folder.setUserBisId(user.getBusinessId());
        folder.setDelFlag("0");
        List<Folder> list = folderService.selectFolderList(folder);
        return getDataTable(list);
    }

    /**
     * 删除父级和子目录
     */
    @PostMapping("/deleteByFolderId")
    @ResponseBody
    public AjaxResult deleteByFolderId(String id) {
        return toAjax(folderService.deleteByFolderId(id) + folderService.updatePFolder(id));
    }

    /**
     * 找回已删除父级和子目录
     */
    @PostMapping("/returnFolder")
    @ResponseBody
    public AjaxResult returnFolder(String id) {
        return toAjax(folderService.returnFolder(id) + folderService.updateReturnFolder(id));
    }

    /**
     * 回收站删除父级和子目录
     */
    @PostMapping("/deleteReallyFolderId")
    @ResponseBody
    public AjaxResult deleteReallyFolderId(String id) {
        return toAjax(folderService.deleteReallyFolderId(id) + folderService.updateReallyFolder(id));
    }

    /**
     * 回收站清空
     */
    @PostMapping("/cleanAll")
    @ResponseBody
    public AjaxResult cleanAll(Folder folder, File file) {
        return toAjax(folderService.cleanAllFolder(folder) + fileService.cleanAllFile(file));
    }

    /**
     * 根据busId获取文件夹名称
     */
    @PostMapping("/selectNameByBusId")
    @ResponseBody
    public String selectNameByBusId(String busId) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return "";
        }
        User user = (User) principal;
        Folder folder = new Folder();
        folder.setBusinessId(busId);
        folder.setUserBisId(user.getBusinessId());
        return folderService.selectFolderList(folder).get(0).getName();
    }

    /**
     * 修改文件夹名称方法
     */
    @PostMapping("/updateNameByBusId")
    @ResponseBody
    public AjaxResult updateNameByBusId(Folder folder) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return error("未登录或登录超时。请重新登录");
        }
        User user = (User) principal;
        Folder folder1 = new Folder();
        folder1.setBusinessId(folder.getBusinessId());
        folder1 = folderService.selectFolderList(folder1).get(0);
        //String oldName = folder1.getName();
        Folder folder2 = new Folder();
        folder2.setName(folder.getName());
        folder2.setBusinessPid(folder1.getBusinessPid());
        folder2.setUserBisId(user.getBusinessId());
        folder2.setDelFlag("0");
        List<Folder> folders = folderService.selectFolderList(folder2);
        if (folders.size() > 0) {
            return error("已存在同名文件夹");
        }
        folder1.setName(folder.getName());
        folder.setUserBisId(user.getBusinessId());
        if (!folder1.getBusinessPid().equals(user.getBusinessId())) {
            Folder folder3 = new Folder();
            folder3.setBusinessId(folder1.getBusinessPid());
            folder3 = folderService.selectFolderList(folder3).get(0);
            String newPath = folder3.getFolderPath() + "\\" + folder.getName();
            String oldPath = folder1.getFolderPath();
            folder1.setFolderPath(newPath);
            if (folder.getBusinessId() != null && !"".equals(folder.getBusinessId()) && !"".equals(folder.getName())) {
                int i = folderService.updateFolder(folder1);
                if (i > 0) {
                    folderService.updatePath(folder1, oldPath, newPath);
                }
            }
        } else {
            String newPath = "\\" + folder.getName();
            String oldPath = folder1.getFolderPath();
            folder1.setFolderPath(newPath);
            if (folder.getBusinessId() != null && !"".equals(folder.getBusinessId()) && !"".equals(folder.getName())) {
                int i = folderService.updateFolder(folder1);
                if (i > 0) {
                    folderService.updatePath(folder1, oldPath, newPath);
                }
            }
        }
        return success("success");
    }
}
