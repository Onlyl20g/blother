package com.jinke.project.system.httplog.mapper;

import com.jinke.project.system.httplog.domain.Httplog;

import java.util.List;

/**
 * 对外HTTP调用日志 数据层
 *
 * @author jinke
 * @date 2019-07-16
 */
public interface HttplogMapper {
    /**
     * 查询对外HTTP调用日志信息
     *
     * @param id 对外HTTP调用日志ID
     * @return 对外HTTP调用日志信息
     */
    public Httplog selectHttplogById(Integer id);

    /**
     * 查询对外HTTP调用日志列表
     *
     * @param httplog 对外HTTP调用日志信息
     * @return 对外HTTP调用日志集合
     */
    public List<Httplog> selectHttplogList(Httplog httplog);

    /**
     * 新增对外HTTP调用日志
     *
     * @param httplog 对外HTTP调用日志信息
     * @return 结果
     */
    public int insertHttplog(Httplog httplog);

    /**
     * 修改对外HTTP调用日志
     *
     * @param httplog 对外HTTP调用日志信息
     * @return 结果
     */
    public int updateHttplog(Httplog httplog);

    /**
     * 删除对外HTTP调用日志
     *
     * @param id 对外HTTP调用日志ID
     * @return 结果
     */
    public int deleteHttplogById(Integer id);

    /**
     * 批量删除对外HTTP调用日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHttplogByIds(String[] ids);

}