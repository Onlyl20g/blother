package com.jinke.project.system.hkFile.service;

import com.jinke.project.system.hkFile.domain.HkFile;

import java.util.List;

/**
 * 获客系统上传文件 服务层
 *
 * @author jinke
 * @date 2019-10-16
 */
public interface IHkFileService {
    /**
     * 查询获客系统上传文件信息
     *
     * @param id 获客系统上传文件ID
     * @return 获客系统上传文件信息
     */
    public HkFile selectHkFileById(Integer id);

    /**
     * 查询获客系统上传文件列表
     *
     * @param hkFile 获客系统上传文件信息
     * @return 获客系统上传文件集合
     */
    public List<HkFile> selectHkFileList(HkFile hkFile);

    /**
     * 新增获客系统上传文件
     *
     * @param hkFile 获客系统上传文件信息
     * @return 结果
     */
    public int insertHkFile(HkFile hkFile);

    /**
     * 修改获客系统上传文件
     *
     * @param hkFile 获客系统上传文件信息
     * @return 结果
     */
    public int updateHkFile(HkFile hkFile);

    /**
     * 删除获客系统上传文件信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHkFileByIds(String ids);

    /**
     * 查询业务申请
     *
     * @param hkFile
     * @return
     */
    public List<HkFile> selectHkFileListByBillId(HkFile hkFile);

    /**
     * 查询业务模块名
     *
     * @param hkFile
     * @return
     */
    public List<HkFile> selectHkFileListBlockName(HkFile hkFile);

    public List<HkFile> selectHkFileListByCode(HkFile hkFile);
}
