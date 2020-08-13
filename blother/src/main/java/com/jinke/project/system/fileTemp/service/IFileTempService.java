package com.jinke.project.system.fileTemp.service;

import com.jinke.project.system.fileTemp.domain.FileTemp;

import java.util.List;

/**
 * 临时文件路径 服务层
 *
 * @author jinke
 * @date 2019-07-15
 */
public interface IFileTempService {
    /**
     * 查询临时文件路径信息
     *
     * @param id 临时文件路径ID
     * @return 临时文件路径信息
     */
    public FileTemp selectFileTempById(Long id);

    /**
     * 查询临时文件路径列表
     *
     * @param fileTemp 临时文件路径信息
     * @return 临时文件路径集合
     */
    public List<FileTemp> selectFileTempList(FileTemp fileTemp);

    /**
     * 新增临时文件路径
     *
     * @param fileTemp 临时文件路径信息
     * @return 结果
     */
    public int insertFileTemp(FileTemp fileTemp);

    /**
     * 修改临时文件路径
     *
     * @param fileTemp 临时文件路径信息
     * @return 结果
     */
    public int updateFileTemp(FileTemp fileTemp);

    /**
     * 删除临时文件路径信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFileTempByIds(String ids);

    /**
     * 修改已上传的临时文件的状态
     *
     * @param url
     * @return
     */
    public Integer updateStatus(String url);

}
