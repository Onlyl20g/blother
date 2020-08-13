package com.jinke.project.system.fileAttachType.mapper;

import com.jinke.project.system.fileAttachType.domain.FileAttachType;

import java.util.List;

/**
 * 其他系统文件分类 数据层
 *
 * @author jinke
 * @date 2019-11-14
 */
public interface FileAttachTypeMapper {
    /**
     * 查询其他系统文件分类信息
     *
     * @param id 其他系统文件分类ID
     * @return 其他系统文件分类信息
     */
    public FileAttachType selectFileAttachTypeById(Long id);

    /**
     * 查询其他系统文件分类列表
     *
     * @param fileAttachType 其他系统文件分类信息
     * @return 其他系统文件分类集合
     */
    public List<FileAttachType> selectFileAttachTypeList(FileAttachType fileAttachType);

    /**
     * 新增其他系统文件分类
     *
     * @param fileAttachType 其他系统文件分类信息
     * @return 结果
     */
    public int insertFileAttachType(FileAttachType fileAttachType);

    /**
     * 修改其他系统文件分类
     *
     * @param fileAttachType 其他系统文件分类信息
     * @return 结果
     */
    public int updateFileAttachType(FileAttachType fileAttachType);

    /**
     * 删除其他系统文件分类
     *
     * @param id 其他系统文件分类ID
     * @return 结果
     */
    public int deleteFileAttachTypeById(Long id);

    /**
     * 批量删除其他系统文件分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFileAttachTypeByIds(String[] ids);

    public List<FileAttachType> selectLikeFileAttachTypeList(FileAttachType fileAttachType);
}