package com.jinke.project.system.hkFile.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.hkFile.domain.HkFile;
import com.jinke.project.system.hkFile.mapper.HkFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获客系统上传文件 服务层实现
 *
 * @author jinke
 * @date 2019-10-16
 */
@Service
public class HkFileServiceImpl implements IHkFileService {
    @Autowired
    private HkFileMapper hkFileMapper;

    /**
     * 查询获客系统上传文件信息
     *
     * @param id 获客系统上传文件ID
     * @return 获客系统上传文件信息
     */
    @Override
    public HkFile selectHkFileById(Integer id) {
        return hkFileMapper.selectHkFileById(id);
    }

    /**
     * 查询业务申请
     *
     * @param hkFile
     * @return
     */
    @Override
    public List<HkFile> selectHkFileListByBillId(HkFile hkFile) {
        return hkFileMapper.selectHkFileListByBillId(hkFile);
    }

    /**
     * 查询
     *
     * @param hkFile
     * @return
     */
    @Override
    public List<HkFile> selectHkFileListByCode(HkFile hkFile) {
        return hkFileMapper.selectHkFileListByCode(hkFile);
    }

    /**
     * 查询获客系统上传文件列表
     *
     * @param hkFile 获客系统上传文件信息
     * @return 获客系统上传文件集合
     */
    @Override
    public List<HkFile> selectHkFileList(HkFile hkFile) {
        return hkFileMapper.selectHkFileList(hkFile);
    }

    /**
     * 新增获客系统上传文件
     *
     * @param hkFile 获客系统上传文件信息
     * @return 结果
     */
    @Override
    public int insertHkFile(HkFile hkFile) {
        return hkFileMapper.insertHkFile(hkFile);
    }

    /**
     * 修改获客系统上传文件
     *
     * @param hkFile 获客系统上传文件信息
     * @return 结果
     */
    @Override
    public int updateHkFile(HkFile hkFile) {
        return hkFileMapper.updateHkFile(hkFile);
    }

    /**
     * 删除获客系统上传文件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHkFileByIds(String ids) {
        return hkFileMapper.deleteHkFileByIds(Convert.toStrArray(ids));
    }


    /**
     * 查询业务模块名
     *
     * @param hkFile
     * @return
     */
    @Override
    public List<HkFile> selectHkFileListBlockName(HkFile hkFile) {
        return hkFileMapper.selectHkFileListBlockName(hkFile);
    }
}
