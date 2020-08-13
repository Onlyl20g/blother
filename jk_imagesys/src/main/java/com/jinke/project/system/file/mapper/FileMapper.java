package com.jinke.project.system.file.mapper;

import com.jinke.project.system.file.domain.File;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文件 数据层
 *
 * @author jinke
 * @date 2019-09-09
 */
@Component
public interface FileMapper {
    /**
     * 查询文件信息
     *
     * @param id 文件ID
     * @return 文件信息
     */
    public File selectFileById(Integer id);

    /**
     * 查询文件列表
     *
     * @param file 文件信息
     * @return 文件集合
     */
    public List<File> selectFileList(File file);

    /**
     * 查询文件列表
     *
     * @param file 文件信息
     * @return 文件集合
     */
    public List<File> selectFileListByLabel(File file);

    /**
     * 新增文件
     *
     * @param file 文件信息
     * @return 结果
     */
    public int insertFile(File file);

    /**
     * 修改文件
     *
     * @param file 文件信息
     * @return 结果
     */
    public int updateFile(File file);

    /**
     * 删除文件
     *
     * @param id 文件ID
     * @return 结果
     */
    public int deleteFileById(Integer id);

    /**
     * 批量删除文件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFileByIds(String[] ids);

    /**
     * 找回文件
     *
     * @param
     * @return
     */
    public int returnFile(File file);

    /**
     * 查询分享文件
     *
     * @param file
     * @return
     */
    public List<File> selectShareFile(File file);

    /**
     * 查询文件
     *
     * @param file
     * @return
     */
    public List<File> selectFileLists(File file);

    public List<File> selectFileListGroupByPath(File file);

    public int cleanAllFile(File file);
}