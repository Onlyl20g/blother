package com.jinke.project.system.fileTemp.mapper;

import com.jinke.project.system.fileTemp.domain.FileTemp;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 临时文件路径 数据层
 *
 * @author jinke
 * @date 2019-07-15
 */
@Component
public interface FileTempMapper {
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
     * 删除临时文件路径
     *
     * @param id 临时文件路径ID
     * @return 结果
     */
    public int deleteFileTempById(Long id);

    /**
     * 批量删除临时文件路径
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFileTempByIds(String[] ids);

    /**
     * 删除临时文件路径
     *
     * @param url 需要删除的数据路径
     * @return 结果
     */
    public int deleteFileTempByUrl(String url);

    /**
     * 删除临时文件路径
     *
     * @param url 需要删除的数据路径
     * @return 结果
     */
    public FileTemp selectFileTempByUrl(String url);
}