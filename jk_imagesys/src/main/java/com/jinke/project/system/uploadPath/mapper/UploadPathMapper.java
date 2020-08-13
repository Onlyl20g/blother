package com.jinke.project.system.uploadPath.mapper;

import com.jinke.project.system.uploadPath.domain.UploadPath;

import java.util.List;

/**
 * 其他系统上传文件路径地址 数据层
 *
 * @author jinke
 * @date 2019-12-10
 */
public interface UploadPathMapper {
    /**
     * 查询其他系统上传文件路径地址信息
     *
     * @param id 其他系统上传文件路径地址ID
     * @return 其他系统上传文件路径地址信息
     */
    public UploadPath selectUploadPathById(Long id);

    /**
     * 查询其他系统上传文件路径地址列表
     *
     * @param uploadPath 其他系统上传文件路径地址信息
     * @return 其他系统上传文件路径地址集合
     */
    public List<UploadPath> selectUploadPathList(UploadPath uploadPath);

    /**
     * 新增其他系统上传文件路径地址
     *
     * @param uploadPath 其他系统上传文件路径地址信息
     * @return 结果
     */
    public int insertUploadPath(UploadPath uploadPath);

    /**
     * 修改其他系统上传文件路径地址
     *
     * @param uploadPath 其他系统上传文件路径地址信息
     * @return 结果
     */
    public int updateUploadPath(UploadPath uploadPath);

    /**
     * 删除其他系统上传文件路径地址
     *
     * @param id 其他系统上传文件路径地址ID
     * @return 结果
     */
    public int deleteUploadPathById(Long id);

    /**
     * 批量删除其他系统上传文件路径地址
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUploadPathByIds(String[] ids);

}