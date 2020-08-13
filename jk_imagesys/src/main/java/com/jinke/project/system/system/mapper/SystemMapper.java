package com.jinke.project.system.system.mapper;

import com.jinke.project.system.system.domain.System;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 其他系统密钥 数据层
 *
 * @author jinke
 * @date 2019-12-06
 */
@Component
public interface SystemMapper {
    /**
     * 查询其他系统密钥信息
     *
     * @param id 其他系统密钥ID
     * @return 其他系统密钥信息
     */
    public System selectSystemById(Integer id);

    /**
     * 查询其他系统密钥列表
     *
     * @param system 其他系统密钥信息
     * @return 其他系统密钥集合
     */
    public List<System> selectSystemList(System system);

    /**
     * 新增其他系统密钥
     *
     * @param system 其他系统密钥信息
     * @return 结果
     */
    public int insertSystem(System system);

    /**
     * 修改其他系统密钥
     *
     * @param system 其他系统密钥信息
     * @return 结果
     */
    public int updateSystem(System system);

    /**
     * 删除其他系统密钥
     *
     * @param id 其他系统密钥ID
     * @return 结果
     */
    public int deleteSystemById(Integer id);

    /**
     * 批量删除其他系统密钥
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSystemByIds(String[] ids);

}