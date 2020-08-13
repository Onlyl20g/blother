package com.jinke.project.system.folder.service;

import com.jinke.project.system.folder.domain.Folder;

import java.util.List;

/**
 * 目录 服务层
 *
 * @author jinke
 * @date 2019-09-09
 */
public interface IFolderService {
    /**
     * 查询目录信息
     *
     * @param id 目录ID
     * @return 目录信息
     */
    public Folder selectFolderById(Integer id);

    /**
     * 查询目录列表
     *
     * @param folder 目录信息
     * @return 目录集合
     */
    public List<Folder> selectFolderList(Folder folder);

    /**
     * 查询目录列表
     *
     * @param folder 目录信息
     * @return 目录集合
     */
    public List<Folder> selectFolderListByBusinessPid(Folder folder);

    /**
     * 新增目录
     *
     * @param folder 目录信息
     * @return 结果
     */
    public int insertFolder(Folder folder);

    /**
     * 修改目录
     *
     * @param folder 目录信息
     * @return 结果
     */
    public int updateFolder(Folder folder);

    /**
     * 删除目录信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFolderByIds(String ids);

    /**
     * 删除目录信息
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteFolderById(Integer id);

    public int addFolder(Folder folder);

    /**
     * 删除父级和子文件夹
     *
     * @param id
     * @return
     */
    public int deleteByFolderId(String id);

    /**
     * 只删除当前文件夹
     *
     * @param id
     * @return
     */
    public int updatePFolder(String id);

    /**
     * 找回文件夹
     *
     * @param id
     * @return
     */
    public int returnFolder(String id);

    /**
     * 找回当前文件夹
     *
     * @param id
     * @return
     */
    public int updateReturnFolder(String id);

    /**
     * 回收站 删除文件夹
     *
     * @param id
     * @return
     */
    public int deleteReallyFolderId(String id);

    /**
     * 回收站 删除文件
     *
     * @param id
     * @return
     */
    public int updateReallyFolder(String id);

    /**
     * @param folder
     * @param path
     * @param tagPath
     */
    public void updatePath(Folder folder, String path, String tagPath);

    /**
     * 清空回收站
     *
     * @return
     */
    public int cleanAllFolder(Folder folder);

    public void mergeFolders(Folder folder);
}
