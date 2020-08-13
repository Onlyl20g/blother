package com.jinke.project.system.systemDeploy.service;

import com.jinke.project.system.systemDeploy.domain.SystemDeploy;

import java.util.List;

/**
 * 系统标识 服务层
 *
 * @author jinke
 * @date 2019-12-04
 */
public interface ISystemDeployService {
    /**
     * 查询系统标识信息
     *
     * @param id 系统标识ID
     * @return 系统标识信息
     */
    public SystemDeploy selectSystemDeployById(Long id);

    /**
     * 查询系统标识列表
     *
     * @param systemDeploy 系统标识信息
     * @return 系统标识集合
     */
    public List<SystemDeploy> selectSystemDeployList(SystemDeploy systemDeploy);

    /**
     * 新增系统标识
     *
     * @param systemDeploy 系统标识信息
     * @return 结果
     */
    public int insertSystemDeploy(SystemDeploy systemDeploy);

    /**
     * 修改系统标识
     *
     * @param systemDeploy 系统标识信息
     * @return 结果
     */
    public int updateSystemDeploy(SystemDeploy systemDeploy);

    /**
     * 删除系统标识信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSystemDeployByIds(String ids);

}
