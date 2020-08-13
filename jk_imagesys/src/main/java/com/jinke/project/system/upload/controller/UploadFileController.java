package com.jinke.project.system.upload.controller;

import com.jinke.project.system.file.mapper.FileMapper;
import com.jinke.project.system.file.service.FileServiceImpl;
import com.jinke.project.system.file.service.IFileService;
import com.jinke.project.system.folder.domain.Folder;
import com.jinke.project.system.folder.service.FolderServiceImpl;
import com.jinke.project.system.hkFile.domain.HkFile;
import com.jinke.project.system.hkFile.service.IHkFileService;
import com.jinke.project.system.upload.service.UploadFileService;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.viewdataUpload.domain.ViewdataUpload;
import com.jinke.project.system.viewdataUpload.domain.ViewdataUploadSource;
import com.jinke.project.system.viewdataUpload.mapper.ViewdataUploadMapper;
import com.jinke.project.tool.fastdfs.FastDFSClient;
import com.jinke.project.tool.tools.ConfigEntity;
import com.jinke.project.tool.tools.GeneralMethods;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 上传文件操作处理
 * Created by user on 2019/7/10.
 */
@Controller
@RequestMapping("system/file")
public class UploadFileController {
    private String prefix = "system/file";
    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private FastDFSClient fdfsClient;

    @Autowired
    private IFileService iFileService;

    @Autowired
    private IHkFileService iHkFileService;

    @Autowired
    private FolderServiceImpl folderService;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private IHkFileService hkFileService;

    @Autowired
    private ViewdataUploadMapper viewdataUploadMapper;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String url = null;
        String fileNameType = file.getOriginalFilename();
        String fileName = fileNameType.substring(0, fileNameType.lastIndexOf("."));
        String fileSize = Long.toString(file.getSize() / 1024); //kb
        try {
            url = uploadFileService.UploadFilesToServer(file);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"path\":\"\",\"code\":\"500\"}";
        }
        return "{\"path\":\"" + url + "\",\"code\":\"200\",\"fileName\":\"" + fileName + "\",\"fileSize\":\"" + fileSize + "\"}";
    }

    /**
     * 其他系统调用fast上传
     */
    @PostMapping(value = "/doUpload")
    @ResponseBody
    public String doUpload(@RequestParam("file") MultipartFile file) {
        String url = null;
        try {
            url = uploadFileService.UploadFilesToServer(file);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"path\":\"\",\"code\":\"500\"}";
        }
        ViewdataUpload viewdataUpload = new ViewdataUpload();
        String businessId = UUID.randomUUID().toString().replaceAll("-", "");//业务id
        String filebillId = UUID.randomUUID().toString().replaceAll("-", "");//单据id
        Date date = new Date();//获取当前时间
        viewdataUpload.setDataSource(ViewdataUploadSource.ITU.getValue());
        viewdataUpload.setCreateTime(date);
        viewdataUpload.setBusinessId(businessId);
        viewdataUpload.setStatus("0");//状态默认0
        viewdataUpload.setFileBillId(filebillId);
        try {
            viewdataUploadMapper.insertViewdataUpload(viewdataUpload);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"path\":\"\",\"code\":\"500\"}";
        }

        com.jinke.project.system.file.domain.File file1 = new com.jinke.project.system.file.domain.File();
        file1.setPath(url);
        file1.setBusinessId(UUID.randomUUID().toString().replaceAll("-", ""));
        file1.setBillId(filebillId);
        file1.setCreateTime(new Date());
        file1.setDelFlag("0");
        file1.setStatus("0");
        try {
            fileMapper.insertFile(file1);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"path\":\"\",\"code\":\"500\"}";
        }
        return "{\"path\":\"" + filebillId + "\",\"code\":\"200\"}";
    }

    /**
     * 其他系统调用fast上传(map)
     */
    @PostMapping(value = "/doUploadMap")
    @ResponseBody
    public String doUploadMap(HttpServletRequest request) {
        byte[] bytes = null;
        String file = request.getParameter("file");
        String url = null;
        try {
            //url = uploadFileService.UploadFilesToServer(file);
            //return "{\"path\":\"" + url + "\",\"code\":\"200\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"path\":\",\"code\":\"500\"}";
        }
        return "{\"path\":\",\"code\":\"500\"}";
    }

    /**
     * 其他系统调用fast下载
     */
    @PostMapping(value = "/doDownload")
    @ResponseBody
    public byte[] doDownload(@RequestParam("url") String url) {
        if (url == null || "".equals(url) || "null".equals(url)) {
            return null;
        }
        com.jinke.project.system.file.domain.File file = new com.jinke.project.system.file.domain.File();
        file.setBillId(url);
        List<com.jinke.project.system.file.domain.File> fileList = fileService.selectFileList(file);
        url = fileList.get(0).getPath();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            return uploadFileService.doDownload(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 单文件下载
     *
     * @param filesPath
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/down")
    @ResponseBody
    public void downloadFile(@RequestParam("url") String filesPath, @RequestParam("name") String name,HttpServletRequest request, HttpServletResponse response) throws IOException {
        uploadFileService.downloadFile(filesPath, name,request, response);
    }

    /**
     * 单文件下载
     *
     * @param filesPath
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/downFile")
    @ResponseBody
    public void downloadHkFile(@RequestParam("url") String filesPath, @RequestParam("id") Integer id,HttpServletRequest request, HttpServletResponse response) throws IOException {
        HkFile hkFile = iHkFileService.selectHkFileById(id);
        uploadFileService.downloadFile(filesPath, hkFile.getFileFullName(),request, response);
    }

    /**
     * 单文件下载
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/downloadFile")
    @ResponseBody
    public void downloadFile(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws IOException {
        com.jinke.project.system.file.domain.File file = iFileService.selectFileById(new Integer(id));
        uploadFileService.downloadFile(file.getPath(), file.getName() + "." + file.getFileSuffix(),request, response);
    }

    /**
     * 对选中的所有文件与文件夹进行打包下载
     *
     * @param fileStr
     * @param folderStr
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/downAll")
    @ResponseBody
    public void downloadZip(@RequestParam("files") String fileStr, @RequestParam("folders") String folderStr, @RequestParam("fileType") String fileType, HttpServletResponse response) throws IOException {
        List<Folder> folderList = new ArrayList<Folder>();
        List<com.jinke.project.system.file.domain.File> fileList = new ArrayList<com.jinke.project.system.file.domain.File>();
        String[] files = fileStr.split(",");
        String[] folders = folderStr.split(",");
        String pFolderName = "";
        Folder tempFolder = new Folder();
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return;
        }
        User user = (User) principal;
        tempFolder.setUserBisId(user.getBusinessId());
        if (fileStr != null && !"".equals(fileStr) && files.length > 0) {
            com.jinke.project.system.file.domain.File tempFile = new com.jinke.project.system.file.domain.File();
            tempFile.setDelFlag("0");
            if (fileType != null && !"".equals(fileType)) {
                tempFile.setFileClass(fileType);
            }
            for (String f : files) {
                tempFile.setId(Integer.valueOf(f));
                List<com.jinke.project.system.file.domain.File> fileList1 = fileService.selectFileList(tempFile);
                if (fileList1.size() > 0) {
                    fileList.add(fileList1.get(0));
                }
            }
        }
        for (String f : folders) {
            tempFolder.setBusinessId(f);
            List<Folder> folders1 = folderService.selectFolderList(tempFolder);
            if (folders1 != null && folders1.size() == 1) {
                folderList.add(folders1.get(0));
            }
        }
        Folder folder1 = new Folder();
        if (folderList.size() > 0) {
            folder1.setBusinessId(folderList.get(0).getBusinessPid());
            if (folder1.getBusinessId().equals(user.getBusinessId())) {
                pFolderName = user.getUserName();
            } else {
                List<Folder> folders1 = folderService.selectFolderList(folder1);
                if (folders1 != null && folders1.size() > 0) {
                    pFolderName = folders1.get(0).getName();
                }
            }
        } else if (fileList.size() > 0) {
            folder1.setBusinessId(fileList.get(0).getFolderBusinessId());
            if (folder1.getBusinessId().equals(user.getBusinessId())) {
                pFolderName = user.getUserName();
            } else {
                List<Folder> folders1 = folderService.selectFolderList(folder1);
                if (folders1 != null && folders1.size() > 0) {
                    pFolderName = folders1.get(0).getName();
                }
            }
        }
        uploadFileService.downloadAllZip(folderList, fileList, fileType, pFolderName, response);
    }

    /**
     * 对选中的所有文件与文件夹进行打包下载
     *
     * @param fileStr
     * @param folderStr
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/downloadZipShare")
    @ResponseBody
    public void downloadZipShare(@RequestParam("files") String fileStr, @RequestParam("folders") String folderStr, HttpServletResponse response) throws IOException {
        List<Folder> folderList = new ArrayList<Folder>();
        List<com.jinke.project.system.file.domain.File> fileList = new ArrayList<com.jinke.project.system.file.domain.File>();
        String[] files = fileStr.split(",");
        String[] folders = folderStr.split(",");
        Folder tempFolder = new Folder();
        if (fileStr != null && !"".equals(fileStr) && files.length > 0) {
            for (String f : files) {
                fileList.add(fileService.selectFileById(Integer.valueOf(f)));
            }
        }
        for (String f : folders) {
            tempFolder.setBusinessId(f);
            List<Folder> folders1 = folderService.selectFolderList(tempFolder);
            if (folders1 != null && folders1.size() == 1) {
                folderList.add(folders1.get(0));
            }
        }
        uploadFileService.downloadAllZips(folderList, fileList, response);
    }


    /**
     * 单文件下载 id
     */
    @GetMapping(value = "/downloadFileByHKId")
    @ResponseBody
    public void downloadFileByHKId(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!"".equals(id)) {
            HkFile file = iHkFileService.selectHkFileById((new Integer(id)));
            uploadFileService.downloadFile(file.getFilePath(), file.getFileFullName(),request, response);
        }
    }


    /**
     * 多文件下载
     *
     * @param filesPath
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/downFileByHKAll")
    @ResponseBody
    public void downloadHKFileZip(@RequestParam("urls") String filesPath, HttpServletResponse response) throws IOException {
        String[] split = filesPath.split(",");
        File[] files = new File[split.length];
        Set<String> set = new HashSet<>();
        for (int i = 0; i < split.length; i++) {
            if (!"".equals(split[i])) {
                String id = split[i];
                HkFile file = iHkFileService.selectHkFileById((new Integer(id)));
                if (file == null) {
                    return;
                }
                byte[] download = fdfsClient.download(file.getFilePath());
                try {
                    String fileFullName = GeneralMethods.filterAlikeName(set, file.getFileFullName());
                    files[i] = ByteToFile(fileFullName, download);
                    set.add(fileFullName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        uploadFileService.downloadAll(response, files);
    }

    /**
     * 删除单个文件
     *
     * @param url
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public Integer delfile(@RequestParam("url") String url) {
        Integer row = uploadFileService.deleteFile(url);
        return row;
    }

    /**
     * 将字节流转换成文件
     *
     * @param filename
     * @param data
     * @throws Exception
     */
    public File ByteToFile(String filename, byte[] data) throws Exception {
        if (data != null) {
            String filepath = ConfigEntity.getTempFilePath() + filename;
            File file = new File(filepath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data, 0, data.length);
            fos.flush();
            fos.close();
            return new File(filepath);
        }
        return null;
    }

    /**
     * 查询文件
     *
     * @param
     */
    @PostMapping(value = "/findHKFiles")
    @ResponseBody
    public Integer findHKFiles(HkFile hkFile) {
        List<HkFile> hkFiles = hkFileService.selectHkFileListByBillId(hkFile);
        return hkFiles.size();
    }

    /**
     * 下载全部文件
     */
//    @RequiresPermissions("system:hkFile:remove")
    @GetMapping("/downHKFiles")
    @ResponseBody
    public void downHKFiles(HkFile hkFile, HttpServletResponse response) {
        List<HkFile> hkFiles = hkFileService.selectHkFileListByBillId(hkFile);
        List list = new ArrayList();
        File[] files = new File[hkFiles.size()];
        Set<String> set = new HashSet<>();

        if (hkFiles.size() == 0) {
            return;
        }

        for (int i = 0; i < hkFiles.size(); i++) {
            list.add(hkFiles.get(i).getId());
            byte[] download = fdfsClient.download(hkFiles.get(i).getFilePath());
            try {
                String fileFullName = GeneralMethods.filterAlikeName(set, hkFiles.get(i).getFileFullName());
                files[i] = ByteToFile(fileFullName, download);
                set.add(fileFullName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        uploadFileService.downloadAll(response, files);
    }
}
