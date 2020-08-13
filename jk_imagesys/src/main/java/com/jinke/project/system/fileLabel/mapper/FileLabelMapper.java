package com.jinke.project.system.fileLabel.mapper;

import com.jinke.project.system.fileLabel.domain.FileLabel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文件标签 数据层
 *
 * @author jinke
 * @date 2019-09-09
 */
@Component
public interface FileLabelMapper {
    /**
     * 查询文件标签信息
     *
     * @param id 文件标签ID
     * @return 文件标签信息
     */
    public FileLabel selectFileLabelById(Integer id);

    /**
     * 查询文件标签列表
     *
     * @param fileLabel 文件标签信息
     * @return 文件标签集合
     */
    public List<FileLabel> selectFileLabelList(FileLabel fileLabel);

    /**
     * 新增文件标签
     *
     * @param fileLabel 文件标签信息
     * @return 结果
     */
    public int insertFileLabel(FileLabel fileLabel);

    /**
     * 修改文件标签
     *
     * @param fileLabel 文件标签信息
     * @return 结果
     */
    public int updateFileLabel(FileLabel fileLabel);

    /**
     * 删除文件标签
     *
     * @param id 文件标签ID
     * @return 结果
     */
    public int deleteFileLabelById(Integer id);

    /**
     * 批量删除文件标签
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFileLabelByIds(String[] ids);

}