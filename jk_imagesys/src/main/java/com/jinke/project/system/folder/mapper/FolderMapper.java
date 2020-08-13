package com.jinke.project.system.folder.mapper;

import com.jinke.project.system.folder.domain.Folder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 目录 数据层
 *
 * @author jinke
 * @date 2019-09-09
 */
@Component
public interface FolderMapper {
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
     * 查询目录列表top
     *
     * @param folder 目录信息
     * @return 目录集合
     */
    public List<Folder> selectFolderListTop(Folder folder);


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
     * 删除目录
     *
     * @param id 目录ID
     * @return 结果
     */
    public int deleteFolderById(Integer id);

    /**
     * 批量删除目录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFolderByIds(String[] ids);

    public int updatePFolder(Folder folder);

    /**
     * 查询带有（）的文件夹
     *
     * @param folder
     * @return
     */
    public List<Folder> selectFolderLists(Folder folder);

    public int cleanAllFolder(Folder folder);
}