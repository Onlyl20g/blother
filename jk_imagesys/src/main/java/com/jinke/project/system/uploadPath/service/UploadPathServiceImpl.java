package com.jinke.project.system.uploadPath.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.uploadPath.domain.UploadPath;
import com.jinke.project.system.uploadPath.mapper.UploadPathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 其他系统上传文件路径地址 服务层实现
 *
 * @author jinke
 * @date 2019-12-10
 */
@Service
public class UploadPathServiceImpl implements IUploadPathService {
    @Autowired
    private UploadPathMapper uploadPathMapper;

    /**
     * 查询其他系统上传文件路径地址信息
     *
     * @param id 其他系统上传文件路径地址ID
     * @return 其他系统上传文件路径地址信息
     */
    @Override
    public UploadPath selectUploadPathById(Long id) {
        return uploadPathMapper.selectUploadPathById(id);
    }

    /**
     * 查询其他系统上传文件路径地址列表
     *
     * @param uploadPath 其他系统上传文件路径地址信息
     * @return 其他系统上传文件路径地址集合
     */
    @Override
    public List<UploadPath> selectUploadPathList(UploadPath uploadPath) {
        return uploadPathMapper.selectUploadPathList(uploadPath);
    }

    /**
     * 新增其他系统上传文件路径地址
     *
     * @param uploadPath 其他系统上传文件路径地址信息
     * @return 结果
     */
    @Override
    public int insertUploadPath(UploadPath uploadPath) {
        return uploadPathMapper.insertUploadPath(uploadPath);
    }

    /**
     * 修改其他系统上传文件路径地址
     *
     * @param uploadPath 其他系统上传文件路径地址信息
     * @return 结果
     */
    @Override
    public int updateUploadPath(UploadPath uploadPath) {
        return uploadPathMapper.updateUploadPath(uploadPath);
    }

    /**
     * 删除其他系统上传文件路径地址对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUploadPathByIds(String ids) {
        return uploadPathMapper.deleteUploadPathByIds(Convert.toStrArray(ids));
    }

}
