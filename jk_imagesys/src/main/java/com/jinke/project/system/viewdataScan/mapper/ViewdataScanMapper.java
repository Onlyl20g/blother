package com.jinke.project.system.viewdataScan.mapper;

import com.jinke.project.system.viewdataScan.domain.ViewdataScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 影像扫描 数据层
 *
 * @author jinke
 * @date 2019-09-09
 */
@Component
public interface ViewdataScanMapper {
    /**
     * 查询影像扫描信息
     *
     * @param id 影像扫描ID
     * @return 影像扫描信息
     */
    public ViewdataScan selectViewdataScanById(Integer id);

    /**
     * 查询影像扫描列表
     *
     * @param viewdataScan 影像扫描信息
     * @return 影像扫描集合
     */
    public List<ViewdataScan> selectViewdataScanList(ViewdataScan viewdataScan);

    /**
     * 新增影像扫描
     *
     * @param viewdataScan 影像扫描信息
     * @return 结果
     */
    public int insertViewdataScan(ViewdataScan viewdataScan);

    /**
     * 修改影像扫描
     *
     * @param viewdataScan 影像扫描信息
     * @return 结果
     */
    public int updateViewdataScan(ViewdataScan viewdataScan);

    /**
     * 删除影像扫描
     *
     * @param id 影像扫描ID
     * @return 结果
     */
    public int deleteViewdataScanById(Integer id);

    /**
     * 批量删除影像扫描
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewdataScanByIds(String[] ids);

}