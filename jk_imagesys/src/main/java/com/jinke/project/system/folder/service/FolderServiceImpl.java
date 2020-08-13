package com.jinke.project.system.folder.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.file.domain.File;
import com.jinke.project.system.file.mapper.FileMapper;
import com.jinke.project.system.folder.domain.Folder;
import com.jinke.project.system.folder.mapper.FolderMapper;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.tool.tools.PublicUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 目录 服务层实现
 *
 * @author jinke
 * @date 2019-09-09
 */
@Service
public class FolderServiceImpl implements IFolderService {
    @Autowired
    private FolderMapper folderMapper;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 查询目录信息
     *
     * @param id 目录ID
     * @return 目录信息
     */
    @Override
    public Folder selectFolderById(Integer id) {
        return folderMapper.selectFolderById(id);
    }

    /**
     * 查询目录列表
     *
     * @param folder 目录信息
     * @return 目录集合
     */
    @Override
    public List<Folder> selectFolderList(Folder folder) {
        return folderMapper.selectFolderList(folder);
    }

    /**
     * 查询目录列表
     *
     * @param folder 目录信息
     * @return 目录集合
     */
    @Override
    public List<Folder> selectFolderListByBusinessPid(Folder folder) {
        return folderMapper.selectFolderListByBusinessPid(folder);
    }

    /**
     * 新增目录
     *
     * @param folder 目录信息
     * @return 结果
     */
    @Override
    public int insertFolder(Folder folder) {
        folder.setStatus("0");
        folder.setDelFlag("0");
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        Folder folder1 = new Folder();
        folder1.setDelFlag("0");
        folder1.setBusinessId(folder.getBusinessPid());
        List<Folder> folders1 = folderMapper.selectFolderList(folder1);
        if (folders1 != null && folders1.size() == 1) {
            folder.setFolderPath(folders1.get(0).getFolderPath() + "\\" + folder.getName());
        } else {
            folder.setFolderPath("\\" + folder.getName());
        }
        folder.setDelFlag("0");
        List<Folder> folders = folderMapper.selectFolderList(folder);
        folder.setBusinessId(UUID.randomUUID().toString().replaceAll("-", ""));
        if (folders == null || folders.size() == 0) {
            if (folder.getBusinessPid() == "" || folder.getBusinessPid().equals("")) {
                User user = (User) SecurityUtils.getSubject().getPrincipal();//获取当前用户
                String userBusinessId = user.getBusinessId();//用户id
                String businessPid = user.getBusinessId();
                folder.setUserBisId(userBusinessId);
                folder.setBusinessPid(businessPid);
            } else {
                User user = (User) SecurityUtils.getSubject().getPrincipal();//获取当前用户
                String userBusinessId = user.getBusinessId();//用户id
                folder.setUserBisId(userBusinessId);
            }
            folder.setCreateTime(new Date());
            return folderMapper.insertFolder(folder);
        } else {
            return -1;
        }
    }

    /**
     * 新增目录
     *
     * @param folder 目录信息
     * @return 结果
     */
    @Override
    public int addFolder(Folder folder) {
        folder.setCreateTime(new Date());
        return folderMapper.insertFolder(folder);
    }

    /**
     * 修改目录
     *
     * @param folder 目录信息
     * @return 结果
     */
    @Override
    public int updateFolder(Folder folder) {
        folder.setUpdateTime(new Date());
        return folderMapper.updateFolder(folder);
    }

    /**
     * 删除目录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFolderByIds(String ids) {
        return folderMapper.deleteFolderByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteFolderById(Integer id) {
        return folderMapper.deleteFolderById(id);
    }

    /**
     * 通过文件夹bussinessId将子文件和子文件夹状态改为删除为2
     * file.delFlag 0：正常状态； 1：删除状态； 2:删除状态； 3:是彻底删除
     * folder.delFlag 0：正常状态； 1：删除状态； 2:彻底删除； 3:是删除
     *
     * @param id
     */
    @Override
    public int deleteByFolderId(String id) {
        File file = new File();
        file.setDelFlag("0");
        file.setFolderBusinessId(id);
        List<File> files = fileMapper.selectFileList(file);
        if (files != null && files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                File file1 = files.get(i);
                file1.setDelFlag("2");// 2 为 文件删除状态
                fileMapper.updateFile(file1);
            }
        }
        Folder folder = new Folder();
        folder.setDelFlag("0");
        folder.setBusinessPid(id);
        List<Folder> folders = folderMapper.selectFolderList(folder);
        if (folders != null && folders.size() > 0) {
            for (int i = 0; i < folders.size(); i++) {
                Folder folder1 = folders.get(i);
                folder1.setDelFlag("1"); //1为 删除状态
                folderMapper.updateFolder(folder1);
                this.deleteByFolderId(folder1.getBusinessId());
            }
        }
        return 0;
    }

    /**
     * 删除   修改当前文件夹状态
     *
     * @param id
     * @return
     */
    @Override
    public int updatePFolder(String id) {
        Folder folder = new Folder();
        folder.setBusinessId(id);
        folder.setDelFlag("3"); //查询删除状态为3的文件夹
        return folderMapper.updatePFolder(folder);
    }

    /**
     * 找回   修改当前文件夹状态
     *
     * @param id
     * @return
     */
    @Override
    public int updateReturnFolder(String id) {
        Folder folder = new Folder();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        folder.setUserBisId(user.getBusinessId());
        folder.setBusinessId(id);
        List<Folder> folders = folderMapper.selectFolderList(folder);//查出当前文件夹
        folders.get(0).getName();
        folders.get(0).getBusinessPid();
        //把对象传入 修改子集文件path
        Folder folderObject = new Folder();
        folderObject.setBusinessId(id);
        String folderPathNow = folders.get(0).getFolderPath();
        folderObject.setFolderPath(folderPathNow);
        folderObject.setBusinessPid(folders.get(0).getBusinessPid());
        folderObject.setName(folders.get(0).getName());
        folderObject.setId(folders.get(0).getId());
        //查是否有同名文件
        folder.setBusinessId("");
        folder.setName(folders.get(0).getName());
        folder.setBusinessPid(folders.get(0).getBusinessPid());
        folder.setDelFlag("0");
        List<Folder> folders1 = folderMapper.selectFolderList(folder);
        if (folders1.size() != 0) {
            int folderSize = folders1.size();
            List<Folder> folders3 = folderMapper.selectFolderLists(folder);
            if (folders3.size() != 0) {
                folderSize = folders3.size() + 1;
            }
            Folder folder1 = new Folder();
            folder1.setBusinessId(folders.get(0).getBusinessPid());
            List<Folder> folders2 = folderMapper.selectFolderList(folder1);
            //查询是否是父文件夹
            if (folders2.size() == 0) {
                String folderPath = "";
                folder.setFolderPath(folderPath + "\\" + folders.get(0).getName() + "(" + folderSize + ")");
                String folderPathNew = folderPath + "\\" + folders.get(0).getName() + "(" + folderSize + ")";
                folder.setBusinessId(id);
                folder.setDelFlag("0");
                folder.setName(folders.get(0).getName() + "(" + folderSize + ")");
                updatePath(folderObject, folderPathNow, folderPathNew);
                return folderMapper.updatePFolder(folder);
            } else {
                String folderPath = folders2.get(0).getFolderPath();
                folder.setFolderPath(folderPath + "\\" + folders.get(0).getName() + "(" + folderSize + ")");
                String folderPathNew1 = folderPath + "\\" + folders.get(0).getName() + "(" + folderSize + ")";
                folder.setBusinessId(id);
                folder.setDelFlag("0");
                folder.setName(folders.get(0).getName() + "(" + folderSize + ")");
                updatePath(folderObject, folderPathNow, folderPathNew1);
                return folderMapper.updatePFolder(folder);
            }
        } else {
            folder.setBusinessId(id);
            folder.setDelFlag("0");
            return folderMapper.updatePFolder(folder);
        }
    }

    /**
     * 通过文件夹bussinessId将子文件和子文件夹状态改为删除为0
     * 找回文件
     * file.delFlag 0：正常状态； 1：删除状态； 2:删除状态； 3:是彻底删除
     * folder.delFlag 0：正常状态； 1：删除状态； 2:彻底删除； 3:是彻底删除
     *
     * @param id
     */
    @Override
    public int returnFolder(String id) {
        File file = new File();
        file.setFolderBusinessId(id);
        List<File> fileN = fileMapper.selectFileList(file);
        file.setDelFlag("2");
        List<File> files = fileMapper.selectFileList(file);
        if (files != null && files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                File file1 = files.get(i);
                file1.setDelFlag("0");
                fileMapper.updateFile(file1);
            }
        }
        Folder folder = new Folder();
        folder.setDelFlag("1");
        folder.setBusinessPid(id);
        List<Folder> folders = folderMapper.selectFolderList(folder);
        if (folders != null && folders.size() > 0) {
            for (int i = 0; i < folders.size(); i++) {
                Folder folder1 = folders.get(i);
                folder1.setDelFlag("0");
                folderMapper.updateFolder(folder1);
                this.returnFolder(folder1.getBusinessId());
            }
        }
        return 0;
    }

    /**
     * 通过文件夹bussinessId将子文件和子文件夹状态改为删除为2
     * file.delFlag 0：正常状态； 1：删除状态； 2:删除状态； 3:是彻底删除
     * folder.delFlag 0：正常状态； 1：删除状态； 2:彻底删除； 3:是彻底删除
     *
     * @param id
     */
    @Override
    public int deleteReallyFolderId(String id) {
        File file = new File();
        file.setDelFlag("2");
        file.setFolderBusinessId(id);
        List<File> files = fileMapper.selectFileList(file);
        if (files != null && files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                File file1 = files.get(i);
                file1.setDelFlag("3");//文件彻底删除为3状态
                fileMapper.updateFile(file1);
            }
        }
        Folder folder = new Folder();
        folder.setDelFlag("3");
        folder.setBusinessPid(id);
        List<Folder> folders = folderMapper.selectFolderList(folder);
        if (folders != null && folders.size() > 0) {
            for (int i = 0; i < folders.size(); i++) {
                Folder folder1 = folders.get(i);
                folder1.setDelFlag("2");//彻底删除文件夹 为2状态
                folderMapper.updateFolder(folder1);
                this.deleteReallyFolderId(folder1.getBusinessId());
            }
        }
        return 0;
    }

    /**
     * 删除   修改当前文件夹状态
     *
     * @param id
     * @return
     */
    @Override
    public int updateReallyFolder(String id) {
        Folder folder = new Folder();
        folder.setBusinessId(id);
        folder.setDelFlag("2");//彻底删除文件夹为2状态
        return folderMapper.updatePFolder(folder);
    }

    /**
     * 传入一个folder 一个原本的path 一个要改成的path
     * 将会对其子代进行path修改
     *
     * @param folder
     * @param path
     * @param tagPath
     */
    @Override
    public void updatePath(Folder folder, String path, String tagPath) {
        Folder folder1 = new Folder();
        folder1.setBusinessPid(folder.getBusinessId());
        folder1.setUserBisId(folder.getUserBisId());
        List<Folder> folders = folderMapper.selectFolderList(folder1);
        if (folders != null && folders.size() > 0) {
            for (Folder f : folders) {
                f.setFolderPath(f.getFolderPath().replaceFirst(path, tagPath));
                int i = folderMapper.updateFolder(f);
                updatePath(f, path, tagPath);
            }
        }
    }

    /**
     * 通过以,分割的businessId字符串查询
     *
     * @return
     */
    public List<Folder> selectFoldersByBus(String bus) {
        List<Folder> folderList = new ArrayList<Folder>();
        String[] folderStrs = bus.split(",");
        for (String folderStr : folderStrs) {
            if (folderStr.equals("")) {
                continue;
            }
            Folder folderTemp = new Folder();
            folderTemp.setBusinessId(folderStr);
            folderList.add(folderMapper.selectFolderList(folderTemp).get(0));
        }
        return folderList;
    }

    /**
     * 用于检查该id是否存在于该folder下
     *
     * @param folder
     * @param id
     * @return
     */
    public boolean checkMoveTo(Folder folder, Integer id) {
        Folder folder1 = new Folder();
        folder1.setBusinessPid(folder.getBusinessId());
        List<Folder> folders = folderMapper.selectFolderList(folder1);
        for (Folder f : folders) {
            if (f.getId().equals(id)) {
                return false;
            }
            checkMoveTo(f, id);
        }
        return true;
    }

    @Override
    public int cleanAllFolder(Folder folder) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        folder.setUserBisId(user.getBusinessId());
        folder.setDelFlag("'1','3'");
        folder.setUpdateTime(new Date());
        return folderMapper.cleanAllFolder(folder);
    }

    /**
     * 传入一个文件夹，对其内部所有文件夹进行合并
     *
     * @param folder
     */
    @Override
    public void mergeFolders(Folder folder) {
        Folder folder1 = new Folder();
        folder1.setBusinessPid(folder.getBusinessId());
        List<Folder> folders = folderMapper.selectFolderList(folder1);
        if (folders.size() == 0) {
            return;
        }
        for (int i = 0; i < folders.size(); i++) {
            for (int j = folders.size() - 1; j > i; j--) {
                if (folders.get(i).getName().equals(folders.get(j).getName())) {
                    Folder folder2 = folders.get(j);
                    Folder folder3 = folders.get(i);//3的文件与文件夹移动到2
                    File file = new File();
                    file.setFolderBusinessId(folder3.getBusinessId());
                    List<File> fileList = fileMapper.selectFileList(file);
                    if (fileList.size() > 0) {
                        for (File f : fileList) {
                            f.setFolderBusinessId(folder2.getBusinessId());
                            fileMapper.updateFile(f);
                        }
                    }
                    File tempFile = new File();
                    tempFile.setDelFlag("0");
                    tempFile.setStatus("0");
                    tempFile.setFolderBusinessId(folder2.getBusinessId());
                    List<File> fileList2 = fileMapper.selectFileList(tempFile);
                    if (fileList2.size() > 0) {
                        for (int m = 0; m < fileList2.size(); m++) {
                            for (int n = fileList2.size() - 1; n > m; n--) {
                                if (fileList2.get(m).getName().equals(fileList2.get(n).getName()) && fileList2.get(m).getFileSuffix().equals(fileList2.get(n).getFileSuffix())) {
                                    fileList2.get(n).setName(fileList2.get(n).getName() + "_" + PublicUtil.formatTime(fileList2.get(n).getCreateTime()));
                                    fileMapper.updateFile(fileList2.get(n));
                                }
                            }
                        }
                    }
                    Folder tmpFolder = new Folder();
                    tmpFolder.setBusinessPid(folder3.getBusinessId());
                    List<Folder> folders1 = folderMapper.selectFolderList(tmpFolder);
                    if (folders1.size() > 0) {
                        for (int k = 0; k < folders1.size(); k++) {
                            folders1.get(k).setBusinessPid(folder2.getBusinessId());
                            folders1.get(k).setFolderPath(folder2.getFolderPath() + "\\" + folders1.get(k).getName());
                            folderMapper.updateFolder(folders1.get(k));
                        }
                    }
                    this.deleteFolderById(folder3.getId());
                    mergeFolders(folder2);
                }
            }
        }
    }
}
