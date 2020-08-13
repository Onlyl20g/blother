package com.jinke.project.system.httplog.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.httplog.domain.Httplog;
import com.jinke.project.system.httplog.mapper.HttplogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对外HTTP调用日志 服务层实现
 *
 * @author jinke
 * @date 2019-07-16
 */
@Service
public class HttplogServiceImpl implements IHttplogService {

    @Autowired
    private HttplogMapper httplogMapper;

    /**
     * 查询对外HTTP调用日志信息
     *
     * @param id 对外HTTP调用日志ID
     * @return 对外HTTP调用日志信息
     */
    @Override
    public Httplog selectHttplogById(Integer id) {
        return this.httplogMapper.selectHttplogById(id);
    }

    /**
     * 查询对外HTTP调用日志列表
     *
     * @param httplog 对外HTTP调用日志信息
     * @return 对外HTTP调用日志集合
     */
    @Override
    public List<Httplog> selectHttplogList(Httplog httplog) {
        return this.httplogMapper.selectHttplogList(httplog);
    }

    /**
     * 新增对外HTTP调用日志
     *
     * @param httplog 对外HTTP调用日志信息
     * @return 结果
     */
    @Override
    public int insertHttplog(Httplog httplog) {
        return this.httplogMapper.insertHttplog(httplog);
    }

    /**
     * 修改对外HTTP调用日志
     *
     * @param httplog 对外HTTP调用日志信息
     * @return 结果
     */
    @Override
    public int updateHttplog(Httplog httplog) {
        return this.httplogMapper.updateHttplog(httplog);
    }

    /**
     * 删除对外HTTP调用日志对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHttplogByIds(String ids) {
        return this.httplogMapper.deleteHttplogByIds(Convert.toStrArray(ids));
    }


}
