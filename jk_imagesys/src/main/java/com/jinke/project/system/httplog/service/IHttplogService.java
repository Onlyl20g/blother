package com.jinke.project.system.httplog.service;

import com.jinke.project.system.httplog.domain.Httplog;

import java.util.List;

/**
 * 接口调用日志 服务层
 *
 * @author jinke
 * @date 2019-09-09
 */
public interface IHttplogService {
    /**
     * 查询接口调用日志信息
     *
     * @param id 接口调用日志ID
     * @return 接口调用日志信息
     */
    public Httplog selectHttplogById(Integer id);

    /**
     * 查询接口调用日志列表
     *
     * @param httplog 接口调用日志信息
     * @return 接口调用日志集合
     */
    public List<Httplog> selectHttplogList(Httplog httplog);

    /**
     * 新增接口调用日志
     *
     * @param httplog 接口调用日志信息
     * @return 结果
     */
    public int insertHttplog(Httplog httplog);

    /**
     * 修改接口调用日志
     *
     * @param httplog 接口调用日志信息
     * @return 结果
     */
    public int updateHttplog(Httplog httplog);

    /**
     * 删除接口调用日志信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHttplogByIds(String ids);

}
