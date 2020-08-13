package com.jinke.project.system.viewdataScan.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.viewdataScan.domain.ViewdataScan;
import com.jinke.project.system.viewdataScan.mapper.ViewdataScanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 影像扫描 服务层实现
 *
 * @author jinke
 * @date 2019-09-09
 */
@Service
public class ViewdataScanServiceImpl implements IViewdataScanService {
    @Autowired
    private ViewdataScanMapper viewdataScanMapper;

    /**
     * 查询影像扫描信息
     *
     * @param id 影像扫描ID
     * @return 影像扫描信息
     */
    @Override
    public ViewdataScan selectViewdataScanById(Integer id) {
        return viewdataScanMapper.selectViewdataScanById(id);
    }

    /**
     * 查询影像扫描列表
     *
     * @param viewdataScan 影像扫描信息
     * @return 影像扫描集合
     */
    @Override
    public List<ViewdataScan> selectViewdataScanList(ViewdataScan viewdataScan) {
        return viewdataScanMapper.selectViewdataScanList(viewdataScan);
    }

    /**
     * 新增影像扫描
     *
     * @param viewdataScan 影像扫描信息
     * @return 结果
     */
    @Override
    public int insertViewdataScan(ViewdataScan viewdataScan) {
        return viewdataScanMapper.insertViewdataScan(viewdataScan);
    }

    /**
     * 修改影像扫描
     *
     * @param viewdataScan 影像扫描信息
     * @return 结果
     */
    @Override
    public int updateViewdataScan(ViewdataScan viewdataScan) {
        return viewdataScanMapper.updateViewdataScan(viewdataScan);
    }

    /**
     * 删除影像扫描对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteViewdataScanByIds(String ids) {
        return viewdataScanMapper.deleteViewdataScanByIds(Convert.toStrArray(ids));
    }

}
