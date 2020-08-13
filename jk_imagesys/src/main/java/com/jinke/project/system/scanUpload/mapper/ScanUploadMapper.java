package com.jinke.project.system.scanUpload.mapper;

import com.jinke.project.system.scanUpload.domain.ScanUpload;

import java.util.List;

/**
 * 二维码上传 数据层
 *
 * @author jinke
 * @date 2019-11-25
 */
public interface ScanUploadMapper {
    /**
     * 查询二维码上传信息
     *
     * @param id 二维码上传ID
     * @return 二维码上传信息
     */
    public ScanUpload selectScanUploadById(Integer id);

    /**
     * 查询二维码上传列表
     *
     * @param scanUpload 二维码上传信息
     * @return 二维码上传集合
     */
    public List<ScanUpload> selectScanUploadList(ScanUpload scanUpload);

    /**
     * 新增二维码上传
     *
     * @param scanUpload 二维码上传信息
     * @return 结果
     */
    public int insertScanUpload(ScanUpload scanUpload);

    /**
     * 修改二维码上传
     *
     * @param scanUpload 二维码上传信息
     * @return 结果
     */
    public int updateScanUpload(ScanUpload scanUpload);

    /**
     * 删除二维码上传
     *
     * @param id 二维码上传ID
     * @return 结果
     */
    public int deleteScanUploadById(Integer id);

    /**
     * 批量删除二维码上传
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScanUploadByIds(String[] ids);

}