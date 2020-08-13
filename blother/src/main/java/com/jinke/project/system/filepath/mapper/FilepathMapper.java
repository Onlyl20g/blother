package com.jinke.project.system.filepath.mapper;

import com.jinke.project.system.filepath.domain.Filepath;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文件路径 数据层
 *
 * @author jinke
 * @date 2019-07-14
 */
@Component
public interface FilepathMapper {
    /**
     * 查询文件路径信息
     *
     * @param id 文件路径ID
     * @return 文件路径信息
     */
    public Filepath selectFilepathById(Long id);

    /**
     * 查询文件路径列表
     *
     * @param filepath 文件路径信息
     * @return 文件路径集合
     */
    public List<Filepath> selectFilepathList(Filepath filepath);

    /**
     * 新增文件路径
     *
     * @param filepath 文件路径信息
     * @return 结果
     */
    public int insertFilepath(Filepath filepath);

    /**
     * 修改文件路径
     *
     * @param filepath 文件路径信息
     * @return 结果
     */
    public int updateFilepath(Filepath filepath);

    /**
     * 删除文件路径
     *
     * @param id 文件路径ID
     * @return 结果
     */
    public int deleteFilepathById(Long id);

    /**
     * 批量删除文件路径
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFilepathByIds(String[] ids);

}