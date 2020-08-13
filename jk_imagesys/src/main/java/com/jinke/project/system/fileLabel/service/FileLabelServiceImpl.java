package com.jinke.project.system.fileLabel.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.fileLabel.domain.FileLabel;
import com.jinke.project.system.fileLabel.mapper.FileLabelMapper;
import com.jinke.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 文件标签 服务层实现
 *
 * @author jinke
 * @date 2019-09-09
 */
@Service
public class FileLabelServiceImpl implements IFileLabelService {
    @Autowired
    private FileLabelMapper fileLabelMapper;

    /**
     * 查询文件标签信息
     *
     * @param id 文件标签ID
     * @return 文件标签信息
     */
    @Override
    public FileLabel selectFileLabelById(Integer id) {
        FileLabel fileLabel = fileLabelMapper.selectFileLabelById(id);
        if (!"0".equals(fileLabel.getDelFlag())) {
            fileLabel = null;
        }
        return fileLabel;
    }

    /**
     * 查询文件标签列表
     *
     * @param fileLabel 文件标签信息
     * @return 文件标签集合
     */
    @Override
    public List<FileLabel> selectFileLabelList(FileLabel fileLabel) {
        fileLabel.setDelFlag("0");
        User user = (User) SecurityUtils.getSubject().getPrincipal();//获取当前用户
        String userBusinessId = user.getBusinessId();//用户id
        fileLabel.setUserBusinessId(userBusinessId);
        return fileLabelMapper.selectFileLabelList(fileLabel);
    }

    /**
     * 新增文件标签
     *
     * @param fileLabel 文件标签信息
     * @return 结果
     */
    @Override
    public int insertFileLabel(FileLabel fileLabel) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();//获取当前用户
        String userBusinessId = user.getBusinessId();//用户id
        fileLabel.setUserBusinessId(userBusinessId);
        fileLabel.setDelFlag("0");
        List<FileLabel> list = fileLabelMapper.selectFileLabelList(fileLabel);
        if (list == null || list.size() < 1) {
            Date date = new Date();
            fileLabel.setCreateTime(date);
            fileLabel.setDelFlag("0");
            fileLabel.setStatus("0");
            return fileLabelMapper.insertFileLabel(fileLabel);
        } else {
            return -1;
        }

    }

    /**
     * 修改文件标签
     *
     * @param fileLabel 文件标签信息
     * @return 结果
     */
    @Override
    public int updateFileLabel(FileLabel fileLabel) {
        return fileLabelMapper.updateFileLabel(fileLabel);
    }

    /**
     * 删除文件标签对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFileLabelByIds(String ids) {
        return fileLabelMapper.deleteFileLabelByIds(Convert.toStrArray(ids));
    }

}
