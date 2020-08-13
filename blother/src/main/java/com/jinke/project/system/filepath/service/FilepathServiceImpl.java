package com.jinke.project.system.filepath.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.filepath.domain.Filepath;
import com.jinke.project.system.filepath.mapper.FilepathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件路径 服务层实现
 *
 * @author jinke
 * @date 2019-07-14
 */
@Service
public class FilepathServiceImpl implements IFilepathService {
    @Autowired
    private FilepathMapper filepathMapper;

    /**
     * 查询文件路径信息
     *
     * @param id 文件路径ID
     * @return 文件路径信息
     */
    @Override
    public Filepath selectFilepathById(Long id) {
        return filepathMapper.selectFilepathById(id);
    }

    /**
     * 查询文件路径列表
     *
     * @param filepath 文件路径信息
     * @return 文件路径集合
     */
    @Override
    public List<Filepath> selectFilepathList(Filepath filepath) {
        return filepathMapper.selectFilepathList(filepath);
    }

    /**
     * 新增文件路径
     *
     * @param filepath 文件路径信息
     * @return 结果
     */
    @Override
    public int insertFilepath(Filepath filepath) {
        return filepathMapper.insertFilepath(filepath);
    }

    /**
     * 修改文件路径
     *
     * @param filepath 文件路径信息
     * @return 结果
     */
    @Override
    public int updateFilepath(Filepath filepath) {
        return filepathMapper.updateFilepath(filepath);
    }

    /**
     * 删除文件路径对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFilepathByIds(String ids) {
        return filepathMapper.deleteFilepathByIds(Convert.toStrArray(ids));
    }

}
