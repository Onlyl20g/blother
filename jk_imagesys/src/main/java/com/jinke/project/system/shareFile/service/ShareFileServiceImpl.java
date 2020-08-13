package com.jinke.project.system.shareFile.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.shareFile.domain.ShareFile;
import com.jinke.project.system.shareFile.mapper.ShareFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件分享 服务层实现
 *
 * @author jinke
 * @date 2019-10-09
 */
@Service
public class ShareFileServiceImpl implements IShareFileService {
    @Autowired
    private ShareFileMapper shareFileMapper;

    /**
     * 查询文件分享信息
     *
     * @param id 文件分享ID
     * @return 文件分享信息
     */
    @Override
    public ShareFile selectShareFileById(Integer id) {
        return shareFileMapper.selectShareFileById(id);
    }

    /**
     * 查询文件分享列表
     *
     * @param shareFile 文件分享信息
     * @return 文件分享集合
     */
    @Override
    public List<ShareFile> selectShareFileList(ShareFile shareFile) {
        return shareFileMapper.selectShareFileList(shareFile);
    }

    /**
     * 新增文件分享
     *
     * @param shareFile 文件分享信息
     * @return 结果
     */
    @Override
    public int insertShareFile(ShareFile shareFile) {
        return shareFileMapper.insertShareFile(shareFile);
    }

    /**
     * 修改文件分享
     *
     * @param shareFile 文件分享信息
     * @return 结果
     */
    @Override
    public int updateShareFile(ShareFile shareFile) {
        return shareFileMapper.updateShareFile(shareFile);
    }

    /**
     * 删除文件分享对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteShareFileByIds(String ids) {
        return shareFileMapper.deleteShareFileByIds(Convert.toStrArray(ids));
    }

}
