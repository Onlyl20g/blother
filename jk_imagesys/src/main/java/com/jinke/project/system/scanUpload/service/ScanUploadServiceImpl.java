package com.jinke.project.system.scanUpload.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.scanUpload.domain.ScanUpload;
import com.jinke.project.system.scanUpload.mapper.ScanUploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 二维码上传 服务层实现
 *
 * @author jinke
 * @date 2019-11-25
 */
@Service
public class ScanUploadServiceImpl implements IScanUploadService {
    @Autowired
    private ScanUploadMapper scanUploadMapper;

    /**
     * 查询二维码上传信息
     *
     * @param id 二维码上传ID
     * @return 二维码上传信息
     */
    @Override
    public ScanUpload selectScanUploadById(Integer id) {
        return scanUploadMapper.selectScanUploadById(id);
    }

    /**
     * 查询二维码上传列表
     *
     * @param scanUpload 二维码上传信息
     * @return 二维码上传集合
     */
    @Override
    public List<ScanUpload> selectScanUploadList(ScanUpload scanUpload) {
        return scanUploadMapper.selectScanUploadList(scanUpload);
    }

    /**
     * 新增二维码上传
     *
     * @param scanUpload 二维码上传信息
     * @return 结果
     */
    @Override
    public int insertScanUpload(ScanUpload scanUpload) {
        return scanUploadMapper.insertScanUpload(scanUpload);
    }

    /**
     * 修改二维码上传
     *
     * @param scanUpload 二维码上传信息
     * @return 结果
     */
    @Override
    public int updateScanUpload(ScanUpload scanUpload) {
        return scanUploadMapper.updateScanUpload(scanUpload);
    }

    /**
     * 删除二维码上传对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScanUploadByIds(String ids) {
        return scanUploadMapper.deleteScanUploadByIds(Convert.toStrArray(ids));
    }

}
