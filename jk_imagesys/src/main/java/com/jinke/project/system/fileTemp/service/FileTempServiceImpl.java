package com.jinke.project.system.fileTemp.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.fileTemp.domain.FileTemp;
import com.jinke.project.system.fileTemp.domain.FileTempStatus;
import com.jinke.project.system.fileTemp.mapper.FileTempMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 临时文件路径 服务层实现
 *
 * @author jinke
 * @date 2019-07-15
 */
@Service
public class FileTempServiceImpl implements IFileTempService {
    @Autowired
    private FileTempMapper fileTempMapper;

    /**
     * 查询临时文件路径信息
     *
     * @param id 临时文件路径ID
     * @return 临时文件路径信息
     */
    @Override
    public FileTemp selectFileTempById(Long id) {
        return fileTempMapper.selectFileTempById(id);
    }

    /**
     * 查询临时文件路径列表
     *
     * @param fileTemp 临时文件路径信息
     * @return 临时文件路径集合
     */
    @Override
    public List<FileTemp> selectFileTempList(FileTemp fileTemp) {
        return fileTempMapper.selectFileTempList(fileTemp);
    }

    /**
     * 新增临时文件路径
     *
     * @param fileTemp 临时文件路径信息
     * @return 结果
     */
    @Override
    public int insertFileTemp(FileTemp fileTemp) {
        return fileTempMapper.insertFileTemp(fileTemp);
    }

    /**
     * 修改临时文件路径
     *
     * @param fileTemp 临时文件路径信息
     * @return 结果
     */
    @Override
    public int updateFileTemp(FileTemp fileTemp) {
        return fileTempMapper.updateFileTemp(fileTemp);
    }

    /**
     * 删除临时文件路径对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFileTempByIds(String ids) {
        return fileTempMapper.deleteFileTempByIds(Convert.toStrArray(ids));
    }


    /**
     * 修改filetemp状态
     *
     * @param url
     * @return 0==false;1==true
     */
    @Override
    public Integer updateStatus(String url) {
        FileTemp ft0 = new FileTemp();
        ft0.setUrl(url);
        List<FileTemp> fileTemps = fileTempMapper.selectFileTempList(ft0);
        FileTemp ft1 = new FileTemp();
        ft1.setId(fileTemps.get(0).getId());
        ft1.setStatus(FileTempStatus.EFF.getValue());
        Integer i = fileTempMapper.updateFileTemp(ft1);
        return i;
    }

    /**
     * 根据url 删除 记录文件数据
     *
     * @param url
     * @return
     */
    public int deleteFileTempByUrl(String url) {
        Integer row = fileTempMapper.deleteFileTempByUrl(url);
        return row;
    }
}
