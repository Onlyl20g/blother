package com.jinke.project.system.file.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.file.domain.File;
import com.jinke.project.system.file.mapper.FileMapper;
import com.jinke.project.system.folder.domain.Folder;
import com.jinke.project.system.folder.service.FolderServiceImpl;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.viewdataUpload.service.ViewdataUploadServiceImpl;
import com.jinke.project.tool.tools.PublicUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 文件 服务层实现
 *
 * @author jinke
 * @date 2019-09-09
 */
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FolderServiceImpl folderService;
    @Autowired
    ViewdataUploadServiceImpl viewdataUploadService;

    /**
     * 查询文件信息
     *
     * @param id 文件ID
     * @return 文件信息
     */
    @Override
    public File selectFileById(Integer id) {
        return fileMapper.selectFileById(id);
    }

    /**
     * 查询文件列表
     *
     * @param file 文件信息
     * @return 文件集合
     */
    @Override
    public List<File> selectFileList(File file) {
        return fileMapper.selectFileList(file);
    }

    /**
     * 查询文件列表
     *
     * @param file 文件信息
     * @return 文件集合
     */
    @Override
    public List<File> selectFileLists(File file) {
        return fileMapper.selectFileLists(file);
    }

    /**
     * 查询分享文件列表
     *
     * @param file 文件信息
     * @return 文件集合
     */
    @Override
    public List<File> selectShareFile(File file) {
        return fileMapper.selectShareFile(file);
    }

    @Override
    public List<File> selectFileListByLabel(File file) {
        file.setFileLabel(file.getFileLabel());
        return fileMapper.selectFileListByLabel(file);
    }

    /**
     * 新增文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @Override
    public int insertFile(HttpServletRequest request, File file) {
        String fileType = file.getFileSuffix().toLowerCase();
        HttpSession session = request.getSession();
        String businessId = UUID.randomUUID().toString().replaceAll("-", "");//业务id
        file.setBusinessId(businessId);
        User user = (User) SecurityUtils.getSubject().getPrincipal();//获取当前用户
        String userBusinessId = user.getBusinessId();//用户id
        file.setUserBusinessId(userBusinessId);
        file.setCreateTime(new Date());
        if (fileType.equals("bmp") || fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png") || fileType.equals("gif")) {
            file.setFileClass("img");
            return fileMapper.insertFile(file);
        } else if (fileType.equals("doc") || fileType.equals("txt") || fileType.equals("xlsx") || fileType.equals("xls") || fileType.equals("ppt") || fileType.equals("docx") || fileType.equals("pdf")) {
            file.setFileClass("document");
            return fileMapper.insertFile(file);
        //} else if (fileType.equals("mvb") || fileType.equals("mp4") || fileType.equals("avi") || fileType.equals("rmvb") || fileType.equals("mp3") || fileType.equals("wma") || fileType.equals("mkv")) {
        } else if (fileType.equals("mp4") || fileType.equals("mp3") || fileType.equals("webm") || fileType.equals("ogg")) {
            file.setFileClass("video");
            return fileMapper.insertFile(file);
        } else {
            file.setFileClass("other");
            return fileMapper.insertFile(file);
        }
    }

    /**
     * 新增文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @Override
    public int insertScanFile(HttpServletRequest request, File file) {
        String fileType = file.getFileSuffix().toLowerCase();
        HttpSession session = request.getSession();
        String businessId = UUID.randomUUID().toString().replaceAll("-", "");//业务id
        file.setBusinessId(businessId);
        file.setCreateTime(new Date());
        String userBusinessId = file.getUserBusinessId();//用户id
        file.setUserBusinessId(userBusinessId);
        if (fileType.equals("bmp") || fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png") || fileType.equals("gif")) {
            file.setFileClass("img");
            return fileMapper.insertFile(file);
        } else if (fileType.equals("doc") || fileType.equals("txt") || fileType.equals("xlsx") || fileType.equals("xls") || fileType.equals("ppt") || fileType.equals("docx") || fileType.equals("pdf")) {
            file.setFileClass("document");
            return fileMapper.insertFile(file);
        } else if (fileType.equals("mvb") || fileType.equals("mp4") || fileType.equals("avi") || fileType.equals("rmvb") || fileType.equals("mp3") || fileType.equals("wma")) {
            file.setFileClass("video");
            return fileMapper.insertFile(file);
        } else {
            file.setFileClass("other");
            return fileMapper.insertFile(file);
        }
    }


    /**
     * 新增文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @Override
    public int addFile(File file) {
        String fileType = file.getFileSuffix();
        String businessId = UUID.randomUUID().toString().replaceAll("-", "");//业务id
        file.setBusinessId(businessId);
        file.setCreateTime(new Date());
        return fileMapper.insertFile(file);
    }

    /**
     * 修改文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @Override
    public int updateFile(File file) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUpdateBy(user.getUserName());
        file.setUpdateTime(new Date());
        return fileMapper.updateFile(file);
    }

    /**
     * 删除文件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFileByIds(String ids) {
        return fileMapper.deleteFileByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文件对象
     *
     * @return 结果
     */
    @Override
    public int returnFile(File file) {
        return fileMapper.returnFile(file);
    }

    /**
     * 修改状态为删除
     *
     * @param ids 12,23,34
     * @return
     */
    @Override
    public int deleteFiles(String ids) {
        String[] str = ids.split(",");
        int flag = 0;
        File file = new File();
        if (str.length == 1) {
            file = fileMapper.selectFileById(Integer.parseInt(ids));
            file.setDelFlag("1");
            return fileMapper.updateFile(file);
        }
        for (int i = 0; i < str.length; i++) {
            file = fileMapper.selectFileById(Integer.parseInt(str[i]));
            file.setDelFlag("1");
            fileMapper.updateFile(file);
            file = null;
            flag++;
        }
        if (flag == str.length) {
            return 1;
        }
        return 0;
    }

    /**
     * 修改状态为删除
     *
     * @param ids 12,23,34
     * @return
     */
    @Override
    public int reallyRemove(String ids) {
        String[] str = ids.split(",");
        int flag = 0;
        File file = new File();
        if (str.length == 1) {
            file = fileMapper.selectFileById(Integer.parseInt(ids));
            file.setDelFlag("3");
            return fileMapper.updateFile(file);
        }
        for (int i = 0; i < str.length; i++) {
            file = fileMapper.selectFileById(Integer.parseInt(str[i]));
            file.setDelFlag("3");
            fileMapper.updateFile(file);
            file = null;
            flag++;
        }
        if (flag == str.length) {
            return 1;
        }
        return 0;
    }

    @Override
    public int insertSaveFile(String fileAddressId, String url, MultipartFile mfile) {
        File file = new File();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (fileAddressId == null || "".equals(fileAddressId)) {
            file.setFolderBusinessId(user.getBusinessId());
        } else {
            file.setFolderBusinessId(fileAddressId);
        }
        String type = url.split("\\.")[1].toLowerCase();
        if ("pdf".equals(type)) {
            file.setFileClass("other");
        } else {
            file.setFileClass("img");
        }
        file.setBusinessId(UUID.randomUUID().toString().replaceAll("-", ""));
        file.setUserBusinessId(user.getBusinessId());
        file.setBillId(UUID.randomUUID().toString().replaceAll("-", ""));//单据id
        file.setFileSize(mfile.getSize());
        file.setName(mfile.getOriginalFilename().split("\\.")[0]);
        file.setPath(url);
        file.setFileSuffix(type);
        file.setStatus("0");
        file.setDelFlag("0");
        file.setCreateTime(new Date());
        return fileMapper.insertFile(file);
    }

    public List<File> selectFileListGroupByPath(File file) {
        return fileMapper.selectFileListGroupByPath(file);
    }

    /**
     * 移动文件、文件夹
     *
     * @param pid
     * @param files
     * @param folders
     * @return
     */
    @Override
    public int moveFile(String pid, String files, String folders) {
        List<Folder> folderList = null;
        List<File> fileList = null;
        User user = null;
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return 501;
        }
        user = (User) principal;
        String pBusinessId = "";
        String newFolderPath = "";
        if ("".equals(pid)) {//如果传值为空，那么是根目录
            pid = user.getBusinessId();
            pBusinessId = user.getBusinessId();
        } else {
            Folder folder = folderService.selectFolderById(Integer.valueOf(pid));
            pBusinessId = folder.getBusinessId();
            newFolderPath = folder.getFolderPath();
        }
        if (!"".equals(folders)) {
            String[] folderStrs = folders.split(",");
            for (String folderBus : folderStrs) {
                folderList = folderService.selectFoldersByBus(folderBus);
                for (Folder f : folderList) {
                    if (f.getBusinessPid().equals(pid)) {
                        return 0;
                    }
                    if (!user.getBusinessId().equals(pid)) {
                        if (!f.getBusinessId().equals(pid)) {
                            Folder folder = folderService.selectFolderById(Integer.valueOf(pid));
                            Folder folder1 = new Folder();
                            folder1.setName(f.getName());
                            folder1.setDelFlag("0");
                            folder1.setBusinessPid(folder.getBusinessId());
                            List<Folder> folders1 = folderService.selectFolderList(folder1);
                            if (folders1.size() > 0) {
                                return 2;
                            }
                            if (f.getId().equals(Integer.valueOf(pid))) {
                                return 0;
                            }
                            if (!folderService.checkMoveTo(f, Integer.valueOf(pid))) {
                                return 0;
                            }
                        }
                    } else {
                        Folder folder = new Folder();
                        folder.setBusinessPid(pid);
                        folder.setDelFlag("0");
                        folder.setName(f.getName());
                        List<Folder> folders1 = folderService.selectFolderList(folder);
                        if (folders1.size() > 0) {
                            return 2;
                        }
                    }
                }
            }
        }
        if (!"".equals(files)) {
            fileList = new ArrayList<File>();
            String[] fileStrs = files.split(",");
            for (String fileStr : fileStrs) {
                if (!"".equals(fileStr)) {
                    fileList.add(fileMapper.selectFileById(Integer.valueOf(fileStr)));
                }
            }
        }
        if (fileList != null && fileList.size() > 0) {
            for (File file : fileList) {
                file.setFolderBusinessId(pBusinessId);
                fileMapper.updateFile(file);
            }
            File tempFile = new File();
            tempFile.setDelFlag("0");
            tempFile.setStatus("0");
            tempFile.setFolderBusinessId(pBusinessId);
            List<File> fileList1 = fileMapper.selectFileList(tempFile);
            if (fileList1.size() > 0) {
                for (int i = 0; i < fileList1.size(); i++) {
                    for (int j = fileList1.size() - 1; j > i; j--) {
                        if (fileList1.get(i).getName().equals(fileList1.get(j).getName()) && fileList1.get(i).getFileSuffix().equals(fileList1.get(j).getFileSuffix())) {
                            fileList1.get(j).setName(fileList1.get(j).getName() + "_" + PublicUtil.formatTime(fileList1.get(j).getCreateTime()));
                            fileMapper.updateFile(fileList1.get(j));
                        }
                    }
                }
            }
        }
        if (folderList != null && folderList.size() > 0) {
            for (Folder folder : folderList) {
                folder.setBusinessPid(pBusinessId);
                String oldPath = folder.getFolderPath();
                folder.setFolderPath(newFolderPath + "\\" + folder.getName());
                int i = folderService.updateFolder(folder);
                if (i > 0) {
                    folderService.updatePath(folder, oldPath, newFolderPath + "\\" + folder.getName());
                }
            }
        }
        return 1;
    }

    /**
     * 移动并合并
     *
     * @param pid
     * @param files
     * @param folders
     * @return
     */
    @Override
    public int moveMerger(String pid, String files, String folders) {
        List<Folder> folderList = null;
        List<File> fileList = null;
        User user = null;
        //判断是否登录
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return 501;
        }
        user = (User) principal;
        String pBusinessId = "";
        String newFolderPath = "";
        if ("".equals(pid)) {
            pid = user.getBusinessId();
            pBusinessId = user.getBusinessId();
        } else {
            Folder folder = folderService.selectFolderById(Integer.valueOf(pid));
            pBusinessId = folder.getBusinessId();
            newFolderPath = folder.getFolderPath();
        }
        if (!"".equals(folders)) {
            String[] folderStrs = folders.split(",");
            for (String folderBus : folderStrs) {
                folderList = folderService.selectFoldersByBus(folderBus);
                for (Folder f : folderList) {
                    if (f.getBusinessPid().equals(pid)) {
                        return 0;
                    }
                    if (!user.getBusinessId().equals(pid)) {
                        if (!f.getBusinessId().equals(pid)) {
                            //不能移动到当前文件夹内部
                            if (f.getId().equals(Integer.valueOf(pid))) {
                                return 0;
                            }
                            if (!folderService.checkMoveTo(f, Integer.valueOf(pid))) {
                                return 0;
                            }
                        }
                    }
                    Folder folder = new Folder();
                    folder.setBusinessPid(pBusinessId);
                    folder.setName(f.getName());
                    List<Folder> folders1 = folderService.selectFolderList(folder);
                    if (folders1.size() > 0) {
                        File file = new File();
                        file.setFolderBusinessId(f.getBusinessId());
                        List<File> fileList1 = fileMapper.selectFileList(file);
                        if (fileList1.size() > 0) {
                            for (File file1 : fileList1) {
                                file1.setFolderBusinessId(folders1.get(0).getBusinessId());
                                int i = fileMapper.updateFile(file1);
                            }
                        }
                        File tempFile = new File();
                        tempFile.setDelFlag("0");
                        tempFile.setStatus("0");
                        tempFile.setFolderBusinessId(folders1.get(0).getBusinessId());
                        List<File> fileList2 = fileMapper.selectFileList(tempFile);
                        if (fileList2.size() > 0) {
                            for (int i = 0; i < fileList2.size(); i++) {
                                for (int j = fileList2.size() - 1; j > i; j--) {
                                    if (fileList2.get(i).getName().equals(fileList2.get(j).getName()) && fileList2.get(i).getFileSuffix().equals(fileList2.get(j).getFileSuffix())) {
                                        fileList2.get(j).setName(fileList2.get(j).getName() + "_" + PublicUtil.formatTime(fileList2.get(j).getCreateTime()));
                                        fileMapper.updateFile(fileList2.get(j));
                                    }
                                }
                            }
                        }
                        Folder folder1 = new Folder();
                        folder1.setBusinessPid(f.getBusinessId());
                        folder1.setUserBisId(user.getBusinessId());
                        List<Folder> folders2 = folderService.selectFolderList(folder1);
                        if (!folders2.isEmpty()) {
                            for (Folder folder2 : folders2) {
                                String oldPath = folder2.getFolderPath();
                                folder2.setBusinessPid(folders1.get(0).getBusinessId());
                                folder2.setFolderPath(folders1.get(0).getFolderPath() + "\\" + folder2.getName());
                                int i = folderService.updateFolder(folder2);
                                folderService.updatePath(folder2, oldPath, folders1.get(0).getFolderPath() + "\\" + folder2.getName());
                            }
                        }
                        int i = folderService.deleteFolderById(f.getId());
                        folderService.mergeFolders(folders1.get(0));
                    } else {
                        f.setBusinessPid(pBusinessId);
                        String oldPath = f.getFolderPath();
                        newFolderPath = newFolderPath + "\\" + f.getName();
                        int i = folderService.updateFolder(f);
                        folderService.updatePath(folder, oldPath, newFolderPath);
                    }
                }
            }
        }
        if (!"".equals(files)) {
            fileList = new ArrayList<File>();
            String[] fileStrs = files.split(",");
            for (String fileStr : fileStrs) {
                if (!"".equals(fileStr)) {
                    fileList.add(fileMapper.selectFileById(Integer.valueOf(fileStr)));
                }
            }
        }
        if (fileList != null && fileList.size() > 0) {
            for (File file : fileList) {
                file.setFolderBusinessId(pBusinessId);
                fileMapper.updateFile(file);
            }
        }
        return 1;
    }

    @Override
    public int cleanAllFile(File file) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        file.setUserBusinessId(user.getBusinessId());
        file.setDelFlag("'1','2'");
        file.setUpdateTime(new Date());
        return fileMapper.cleanAllFile(file);
    }
}
