package com.jinke.project.system.shareFile.mapper;

import com.jinke.project.system.shareFile.domain.ShareFile;

import java.util.List;

/**
 * 文件分享 数据层
 *
 * @author jinke
 * @date 2019-10-09
 */
public interface ShareFileMapper {
    /**
     * 查询文件分享信息
     *
     * @param id 文件分享ID
     * @return 文件分享信息
     */
    public ShareFile selectShareFileById(Integer id);

    /**
     * 查询文件分享列表
     *
     * @param shareFile 文件分享信息
     * @return 文件分享集合
     */
    public List<ShareFile> selectShareFileList(ShareFile shareFile);

    /**
     * 新增文件分享
     *
     * @param shareFile 文件分享信息
     * @return 结果
     */
    public int insertShareFile(ShareFile shareFile);

    /**
     * 修改文件分享
     *
     * @param shareFile 文件分享信息
     * @return 结果
     */
    public int updateShareFile(ShareFile shareFile);

    /**
     * 删除文件分享
     *
     * @param id 文件分享ID
     * @return 结果
     */
    public int deleteShareFileById(Integer id);

    /**
     * 批量删除文件分享
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteShareFileByIds(String[] ids);

}