package com.jinke.project.system.file.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinke.common.utils.poi.ExcelUtil;
import com.jinke.framework.web.controller.BaseController;
import com.jinke.framework.web.domain.AjaxResult;
import com.jinke.framework.web.page.TableDataInfo;
import com.jinke.project.system.billId.domain.BillId;
import com.jinke.project.system.billId.service.IBillIdService;
import com.jinke.project.system.file.domain.File;
import com.jinke.project.system.file.service.IFileService;
import com.jinke.project.system.fileAttachType.domain.FileAttachType;
import com.jinke.project.system.fileAttachType.service.IFileAttachTypeService;
import com.jinke.project.system.folder.service.FolderServiceImpl;
import com.jinke.project.system.hkFile.domain.HkFile;
import com.jinke.project.system.hkFile.service.HkFileServiceImpl;
import com.jinke.project.system.hkFile.service.IHkFileService;
import com.jinke.project.system.system.domain.System;
import com.jinke.project.system.system.service.SystemServiceImpl;
import com.jinke.project.system.upload.service.UploadFileServiceImpl;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.viewdataUpload.domain.ViewdataUpload;
import com.jinke.project.system.viewdataUpload.domain.ViewdataUploadSource;
import com.jinke.project.system.viewdataUpload.service.IViewdataUploadService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 文件 信息操作处理
 *
 * @author jinke
 * @date 2019-09-09
 */
@Controller
@RequestMapping("/system/file")
public class FileController extends BaseController {
    private String prefix = "system/file";

    @Autowired
    private IFileService fileService;

    @Autowired
    private UploadFileServiceImpl uploadFileService;

    @Autowired
    private IViewdataUploadService iViewdataUploadService;

    @Autowired
    private HkFileServiceImpl hkFileService;

    @Autowired
    private IHkFileService iHkFileService;

    @Autowired
    private IFileAttachTypeService iFileAttachTypeService;

    @Autowired
    private IBillIdService iBillIdService;

    @Autowired
    private SystemServiceImpl systemService;

    @GetMapping()
    public String file() {
        return prefix + "/file";
    }

    /**
     * 查询文件
     */
    @PostMapping("/selectFileById")
    @ResponseBody
    public File selectFileById(String id) {
        return fileService.selectFileById(new Integer(id));
    }

    /**
     * 分享查询文件
     */
    @PostMapping("/selectFileByIdShare")
    @ResponseBody
    public File selectFileByIdShare(String id) {
        return fileService.selectFileById(new Integer(id));
    }

    /**
     * 查询删除状态文件
     */
    @PostMapping("/selectFileDelete")
    @ResponseBody
    public TableDataInfo selectFileDelete(File file) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
        file.setDelFlag("1");
        List<File> list = fileService.selectFileList(file);
        return getDataTable(list);
    }

    /**
     * 对外接口，用于保存保理项目文件
     * 可以用hashmap装载参数后转json传
     *
     * @return
     */
    @PostMapping("/hkSaveFile")
    @ResponseBody
    public String hkSaveFile(HttpServletRequest request) {
        return "{\"msg\":\"缺少参数\",\"flag\":\"failed\"}";
    }

    /**
     * 对外接口，用于保存保理项目文件
     * 可以用hashmap装载参数后转json传
     *
     * @return
     */
    @PostMapping("/hkSaveFile/{name}/{key}")
    @ResponseBody
    public String hkSaveFile(HttpServletRequest request, @PathVariable("name") String name, @PathVariable("key") String key) {
        System system = new System();
        system.setName(name);
        system.setDelFlag("0");
        system.setStatus("0");
        List<System> systems = systemService.selectSystemList(system);
        if (systems == null || systems.size() == 0) {
            return "{\"msg\":\"无权限\",\"flag\":\"failed\"}";
        }
        boolean flag = false;
        for (System s : systems) {
            if (s.getKeyCode().equals(key)) {
                if (new Date().before(s.getExpiryTime())) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            return "{\"msg\":\"无权限\",\"flag\":\"failed\"}";
        }
        String parameter = request.getParameter("body");
        JSONObject jsonObject = JSON.parseObject(parameter);
        FileAttachType fileAttachType = new FileAttachType();
        if (jsonObject == null) {
            return "{\"msg\":\"参数异常\",\"flag\":\"failed\"}";
        }
        if ("".equals(jsonObject.getString("filePath"))) {
            return "{\"msg\":\"filePath为空\",\"flag\":\"failed\"}";
        }
        fileAttachType.setSysType(jsonObject.getString("sysType"));
        fileAttachType.setCode(jsonObject.getString("code"));
        List<FileAttachType> fileAttachTypes = iFileAttachTypeService.selectFileAttachTypeList(fileAttachType);
        if (fileAttachTypes.size() != 1) {
            return error("系统错误").toString();
        }
        //创建来源记录
        ViewdataUpload viewdataUpload = new ViewdataUpload();
        viewdataUpload.setFileBillId(jsonObject.getString("billId"));
        viewdataUpload.setDataSource(ViewdataUploadSource.IFC.getValue());
        viewdataUpload.setDelFlag("0");
        viewdataUpload.setStatus("0");
        viewdataUpload.setCreateTime(new Date());
        iViewdataUploadService.addViewdataUpload(viewdataUpload);
        Map<String, String> map = new HashMap<String, String>();
        //创建文件夹存放
        com.jinke.project.system.file.domain.File file1 = new com.jinke.project.system.file.domain.File();
        file1.setBillId(jsonObject.getString("filePath"));
        List<com.jinke.project.system.file.domain.File> fileList = fileService.selectFileList(file1);
        String url = "";
        if (fileList.size() > 0) {
            url = fileList.get(0).getPath();
            //存入hkFile表
            HkFile hkFile = new HkFile();
            hkFile.setBillId(jsonObject.getString("billId"));
            hkFile.setState("0");
            hkFile.setCreditTime(new Date());
            hkFile.setFileFullName(jsonObject.getString("fileFullName"));
            hkFile.setFilePath(url);
            hkFile.setSubordinate(jsonObject.getString("subordinate"));
            hkFile.setUserName(jsonObject.getString("userName"));
            hkFile.setCode(jsonObject.getString("code"));
            hkFile.setStr2(jsonObject.getString("sysType"));
            int row = hkFileService.insertHkFile(hkFile);

            // 添加单据查询凭证
            BillId billId = new BillId();
            billId.setBillId(jsonObject.getString("billId"));
            List<BillId> billIds = iBillIdService.selectBillIdList(billId);
            if (billIds.size() == 0) {
                billId.setSystem(jsonObject.getString("sysType"));
                billId.setCreateTime(new Date());
                iBillIdService.insertBillId(billId);
            }
            if (row > 0) {
                map.put("code", "200");
                map.put("flag", "success");
            } else {
                map.put("code", "500");
                map.put("flag", "failed");
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 查询文件downloadFileBase
     */
    @PostMapping("/selectFileImg")
    @ResponseBody
    public Map selectFileImg(String id) {
        File file = fileService.selectFileById(new Integer(id));
        String baseDate = "";
        try {
            baseDate = uploadFileService.downloadFileBase(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap();
        String type = file.getFileSuffix();
        if (type.equals("jpg")) {
            type = "jpeg";
        }
        baseDate = "data:image/" + type + ";base64," + baseDate;
//        data:image/png;base64,
        map.put("baseDate", baseDate);
        map.put("type", type);
        map.put("fileSize", file.getFileSize());
        return map;
    }

    /**
     * 查询文件downloadFileBase
     */
    @PostMapping("/addSysOtherFile")
    @ResponseBody
    public Map selectFileImg(String ids, String billId, String code, String sysType, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        int row = 0;

        if (ids != null && !"".equals(ids)) {
            for (String id : ids.split("\\,")) {
                File file = fileService.selectFileById(new Integer(id));
                HkFile hkFile = new HkFile();
                hkFile.setStr2(sysType);
                hkFile.setBillId(billId);
                hkFile.setCode(code);
                hkFile.setFileFullName(file.getName() + "." + file.getFileSuffix());
                List<HkFile> hkFiles = iHkFileService.selectHkFileList(hkFile);
                if (hkFiles.size() > 0) {
                    map.put("result", "0");
                    return map;
                }
            }
        }

        if (ids != null && !"".equals(ids)) {
            for (String id : ids.split("\\,")) {
                File file = fileService.selectFileById(new Integer(id));
                if (file != null && !"".equals(billId) && !"".equals(code) && !"".equals(sysType)) {
                    HkFile hkFile = new HkFile();
                    hkFile.setStr2(sysType);
                    hkFile.setBillId(billId);
                    hkFile.setFilePath(file.getPath());
                    hkFile.setCode(code);
                    hkFile.setFileFullName(file.getName() + "." + file.getFileSuffix());
                    hkFile.setState("0");
                    hkFile.setCreditTime(new Date());
                    row = iHkFileService.insertHkFile(hkFile);
                }
            }
        }
        map.put("result", row);
        return map;
    }

    /**
     * 查询文件列表
     */
    @PostMapping("/search/{bus}")
    @ResponseBody
    public TableDataInfo searchByBill(@PathVariable("bill") String bill) {
        File file = new File();
        file.setBillId(bill);
        List<File> files = fileService.selectFileList(file);
        if (files != null && files.size() > 0) {
            return getDataTable(files);
        }
        return null;
    }

    /**
     * 查询文件列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(File file) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
        if (file.getFolderBusinessId() == null || "".equals(file.getFolderBusinessId())) {
            file.setFolderBusinessId(user.getBusinessId());
        }
        file.setDelFlag("0");
        if ("all".equals(file.getFileClass())) {
            file.setFileClass(null);
        }
        List<File> list = fileService.selectFileList(file);
        return getDataTable(list);
    }

    /**
     * 查询文件列表
     */
    @PostMapping("/listShare")
    @ResponseBody
    public TableDataInfo listShare(File file) {
        file.setDelFlag("0");
        if ("all".equals(file.getFileClass())) {
            file.setFileClass(null);
        }
        List<File> list = fileService.selectFileList(file);
        return getDataTable(list);
    }

    /**
     * 通过file_label模糊查询文件列表
     */
    @PostMapping("/listByLabel")
    @ResponseBody
    public TableDataInfo listByLabel(File file) {
        startPage();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
        file.setFileLabel(file.getFileLabel());
        file.setDelFlag("0");
        List<File> list = fileService.selectFileListByLabel(file);
        return getDataTable(list);
    }

    /**
     * 查询top文件列表
     */
    @PostMapping("/listTop")
    @ResponseBody
    public TableDataInfo listTop() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        File file = new File();
        file.setUserBusinessId(user.getBusinessId());
        file.setDelFlag("0");
        file.setFolderBusinessId(user.getBusinessId());
        List<File> list = fileService.selectFileList(file);
        return getDataTable(list);
    }

    /**
     * 查询文件列表
     */
    @PostMapping("/listImg")
    @ResponseBody
    public TableDataInfo listImg() {
        File file = new File();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
        file.setFileClass("img");
        file.setDelFlag("0");
        List<File> list = fileService.selectFileList(file);
        return getDataTable(list);
    }

    /**
     * 查询文件列表
     */
    @PostMapping("/listImgBySys")
    @ResponseBody
    public TableDataInfo listImgBySys(String name) {
        startPage();
        File file = new File();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
        file.setFileClass("img");
        file.setDelFlag("0");
        file.setName(name);
        List<File> list = fileService.selectFileListGroupByPath(file);
        return getDataTable(list);
    }

    /**
     * 查询当前用户所有文件
     */
    @PostMapping("/listFiles")
    @ResponseBody
    public TableDataInfo listFiles(String name) {
        File file = new File();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
//        file.setFileClass("img");
        file.setDelFlag("0");
        if (name != null && !"".equals(name) && !"null".equals(name)) {
            file.setName(name);
        }
        startPage();
        List<File> list = fileService.selectFileListGroupByPath(file);
        TableDataInfo dataTable = getDataTable(list);
        return dataTable;
    }

    /**
     * 分享文件查询
     *
     * @return
     */
    @PostMapping("/shareFile")
    @ResponseBody
    public TableDataInfo shareFile(File file) {
        if (file.getIds() == null || "".equals(file.getIds())) {
            file.setIds("~");
        }
        file.setDelFlag("0");
        List<File> list = fileService.selectShareFile(file);
        return getDataTable(list);
    }

    /**
     * 查询文件列表
     */
    @PostMapping("/listDoc")
    @ResponseBody
    public TableDataInfo listDoc() {
        File file = new File();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
        file.setFileClass("document");
        file.setDelFlag("0");
        List<File> list = fileService.selectFileList(file);
        return getDataTable(list);
    }

    /**
     * 查询文件列表
     */
    @PostMapping("/listVideo")
    @ResponseBody
    public TableDataInfo listVideo() {
        File file = new File();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
        file.setFileClass("video");
        file.setDelFlag("0");
        List<File> list = fileService.selectFileList(file);
        return getDataTable(list);
    }

    /**
     * 查询文件列表
     */
    @PostMapping("/listOther")
    @ResponseBody
    public TableDataInfo listOther() {
        File file = new File();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
        file.setFileClass("other");
        file.setDelFlag("0");
        List<File> list = fileService.selectFileList(file);
        return getDataTable(list);
    }


    /**
     * 导出文件列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(File file) {
        List<File> list = fileService.selectFileList(file);
        ExcelUtil<File> util = new ExcelUtil<File>(File.class);
        return util.exportExcel(list, "file");
    }

    /**
     * 新增文件
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存文件
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HttpServletRequest request, File file) {
        file.setName(file.getName().replace("/", "_").replace("\\", "_").replace(":", "_")
                .replace("*", "_").replace("?", "_").replace("？", "_").replace("<", "_").replace(">", "_"));
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (file.getFolderBusinessId() == null || "".equals(file.getFolderBusinessId()) || file.getFolderBusinessId().equals("undefined")) {
            file.setFolderBusinessId(user.getBusinessId());
        }
        file.setUserBusinessId(user.getBusinessId());
        String billId = "";
        if (file.getBillId() == null || "".equals(file.getBillId())) {
            billId = UUID.randomUUID().toString().replaceAll("-", "");//单据id
            file.setBillId(billId);
        }
        file.setDelFlag("0");
        List<File> list = fileService.selectFileLists(file);
        if (list.size() != 0) {
            file.setName(file.getName() + "(" + list.size() + ")");
        }
        int i = fileService.insertFile(request, file);
        if (i > 0) {
            return success(billId + "");
        }
        return toAjax(i);
    }

    /**
     * 新增保存文件
     */
    @PostMapping("/addScan")
    @ResponseBody
    public AjaxResult addSave1(HttpServletRequest request, File file) {
        file.setName(file.getName().replace("/", "_").replace("\\", "_").replace(":", "_")
                .replace("*", "_").replace("?", "_").replace("？", "_").replace("<", "_").replace(">", "_"));
        if (file.getFolderBusinessId() == null || "".equals(file.getFolderBusinessId()) || "null".equals(file.getFolderBusinessId())) {
            file.setFolderBusinessId(file.getUserBusinessId());
        }
        String billId = "";
        if (file.getBillId() == null || "".equals(file.getBillId())) {
            billId = UUID.randomUUID().toString().replaceAll("-", "");//单据id
            file.setBillId(billId);
        }
        List<File> list = fileService.selectFileLists(file);
        if (list.size() != 0) {
            file.setName(file.getName() + "(" + list.size() + ")");
        }
        int i = fileService.insertScanFile(request, file);
        if (i > 0) {
            return success(billId + "");
        }
        return toAjax(i);
    }

    /**
     * 修改文件
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        File file = fileService.selectFileById(id);
        mmap.put("file", file);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(File file) {
        return toAjax(fileService.updateFile(file));
    }

    /**
     * 修改保存文件label
     */
    @PostMapping("/editLabel")
    @ResponseBody
    public AjaxResult editLabel(File file) {
        File file1 = fileService.selectFileById(file.getId());
        file1.setFileLabel(file.getFileLabel());
        return toAjax(fileService.updateFile(file1));
    }

    /**
     * 删除文件
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(fileService.deleteFileByIds(ids));
    }

    /**
     * 找回文件
     */
    @PostMapping("/returnFile")
    @ResponseBody
    public AjaxResult returnFile(File file, HttpServletRequest request) {
        file.setDelFlag("0");
        return toAjax(fileService.returnFile(file));
    }

    /**
     * 通过id下载文件
     */
    @GetMapping("/download")
    @ResponseBody
    public void download(Integer id,HttpServletRequest request, HttpServletResponse response) {
        File file = fileService.selectFileById(id);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (file != null && file.getUserBusinessId().equals(user.getBusinessId())) {
            try {
                uploadFileService.downloadFile(file.getPath(), file.getName() + "." + file.getFileSuffix(),request, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过id加载文件
     */
    @GetMapping("/loadFile")
    @ResponseBody
    public void loadFile(Integer id,HttpServletRequest request, HttpServletResponse response) {
        File file = fileService.selectFileById(id);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (file != null && user.getBusinessId().equals(file.getUserBusinessId())) {
            try {
                uploadFileService.downloadFile(file.getPath(), file.getName() + "." + file.getFileSuffix(),request, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过id加载压缩文件
     */
    @GetMapping("/loadMinFile")
    @ResponseBody
    public void downloadMiFile(Integer id, HttpServletResponse response) {
        File file = fileService.selectFileById(id);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (file != null && file.getUserBusinessId().equals(user.getBusinessId())) {
            try {
                uploadFileService.downloadMiFile(file.getPath(), file.getName() + "." + file.getFileSuffix(), response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过id加载压缩文件
     */
    @GetMapping("/downloadMiFileShare")
    @ResponseBody
    public void downloadMiFileShare(Integer id, HttpServletResponse response) {
        File file = fileService.selectFileById(id);
        try {
            uploadFileService.downloadMiFile(file.getPath(), file.getName() + "." + file.getFileSuffix(), response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过id加载压缩分享文件
     */
    @GetMapping("/loadShareMinFile")
    @ResponseBody
    public void loadShareMinFile(Integer id, HttpServletResponse response) {
        File file = fileService.selectFileById(id);
        try {
            uploadFileService.downloadMiFile(file.getPath(), file.getName() + "." + file.getFileSuffix(), response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过id加载文件
     */
    @GetMapping("/loadShareFile")
    @ResponseBody
    public void loadShareFile(Integer id,HttpServletRequest request, HttpServletResponse response) {
        File file = fileService.selectFileById(id);
        try {
            uploadFileService.downloadFile(file.getPath(), file.getName() + "." + file.getFileSuffix(),request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     */
    @PostMapping("/deleteFiles")
    @ResponseBody
    public AjaxResult deleteFiles(String ids) {
        return toAjax(fileService.deleteFiles(ids));
    }

    /**
     * 回收站删除文件
     */
    @PostMapping("/reallyRemove")
    @ResponseBody
    public AjaxResult reallyRemove(String ids) {
        return toAjax(fileService.reallyRemove(ids));
    }

    /**
     * 通过id加载压缩文件
     */
    @GetMapping("/loadCompressFile")
    @ResponseBody
    public void loadCompressFile(Integer id, HttpServletResponse response) {
        File file = fileService.selectFileById(id);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (file != null && file.getUserBusinessId().equals(user.getBusinessId())) {
            try {
                uploadFileService.loadFile2(file.getPath(), file.getName() + "." + file.getFileSuffix(), response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/selectNameByBusId")
    @ResponseBody
    public String selectNameByBusId(String id) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return "";
        }
        User user = (User) principal;
        File file = fileService.selectFileById(Integer.valueOf(id));
        if (!file.getUserBusinessId().equals(user.getBusinessId())) {
            return "";
        }
        return file.getName();
    }

    /**
     * 修改文件名称方法
     */
    @PostMapping("/updateNameById")
    @ResponseBody
    public AjaxResult updateNameByBusId(File file) {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return error("未登录或登录超时。请重新登录");
        }
        User user = (User) principal;
        File file1 = fileService.selectFileById(file.getId());
        if (!file1.getUserBusinessId().equals(user.getBusinessId())) {
            return error("此文件所属不是当前登录用户，无法修改");
        }
        File file2 = new File();
        file2.setName(file.getName());
        file2.setFolderBusinessId(file1.getFolderBusinessId());
        file2.setFileSuffix(file1.getFileSuffix());
        file2.setDelFlag("0");
        if (fileService.selectFileList(file2).size() > 0) {
            return error("已存在同名文件");
        }
        file1.setName(file.getName());
        return fileService.updateFile(file1) > 0 ? success("修改成功") : error("修改失败");
    }

    /**
     * 移动文件
     */
    @PostMapping("/moveFilsAndFolders")
    @ResponseBody
    public int moveFilsAndFolders(@RequestBody Map<String, String> map) {
        return fileService.moveFile(map.get("pid"), map.get("files"), map.get("folders"));
    }

    /**
     * 移动合并文件
     */
    @PostMapping("/moveAndMerger")
    @ResponseBody
    public int moveAndMerger(@RequestBody Map<String, String> map) {
        return fileService.moveMerger(map.get("pid"), map.get("files"), map.get("folders"));
    }
}

