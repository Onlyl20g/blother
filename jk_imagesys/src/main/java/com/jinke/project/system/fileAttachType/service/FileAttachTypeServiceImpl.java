package com.jinke.project.system.fileAttachType.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.fileAttachType.domain.FileAttachType;
import com.jinke.project.system.fileAttachType.mapper.FileAttachTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 其他系统文件分类 服务层实现
 *
 * @author jinke
 * @date 2019-11-14
 */
@Service
public class FileAttachTypeServiceImpl implements IFileAttachTypeService {
    @Autowired
    private FileAttachTypeMapper fileAttachTypeMapper;

    /**
     * 查询其他系统文件分类信息
     *
     * @param id 其他系统文件分类ID
     * @return 其他系统文件分类信息
     */
    @Override
    public FileAttachType selectFileAttachTypeById(Long id) {
        return fileAttachTypeMapper.selectFileAttachTypeById(id);
    }

    /**
     * 查询其他系统文件分类列表
     *
     * @param fileAttachType 其他系统文件分类信息
     * @return 其他系统文件分类集合
     */
    @Override
    public List<FileAttachType> selectFileAttachTypeList(FileAttachType fileAttachType) {
        return fileAttachTypeMapper.selectFileAttachTypeList(fileAttachType);
    }

    /**
     * 新增其他系统文件分类
     *
     * @param fileAttachType 其他系统文件分类信息
     * @return 结果
     */
    @Override
    public int insertFileAttachType(FileAttachType fileAttachType) {
        return fileAttachTypeMapper.insertFileAttachType(fileAttachType);
    }

    /**
     * 修改其他系统文件分类
     *
     * @param fileAttachType 其他系统文件分类信息
     * @return 结果
     */
    @Override
    public int updateFileAttachType(FileAttachType fileAttachType) {
        return fileAttachTypeMapper.updateFileAttachType(fileAttachType);
    }

    /**
     * 删除其他系统文件分类对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFileAttachTypeByIds(String ids) {
        return fileAttachTypeMapper.deleteFileAttachTypeByIds(Convert.toStrArray(ids));
    }

    public List<FileAttachType> selectLikeFileAttachTypeList(FileAttachType fileAttachType) {
        return fileAttachTypeMapper.selectLikeFileAttachTypeList(fileAttachType);
    }

}
