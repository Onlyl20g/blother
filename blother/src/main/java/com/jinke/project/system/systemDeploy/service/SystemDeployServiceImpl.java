package com.jinke.project.system.systemDeploy.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.systemDeploy.domain.SystemDeploy;
import com.jinke.project.system.systemDeploy.mapper.SystemDeployMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统标识 服务层实现
 *
 * @author jinke
 * @date 2019-12-04
 */
@Service
public class SystemDeployServiceImpl implements ISystemDeployService {
    @Autowired
    private SystemDeployMapper systemDeployMapper;

    /**
     * 查询系统标识信息
     *
     * @param id 系统标识ID
     * @return 系统标识信息
     */
    @Override
    public SystemDeploy selectSystemDeployById(Long id) {
        return systemDeployMapper.selectSystemDeployById(id);
    }

    /**
     * 查询系统标识列表
     *
     * @param systemDeploy 系统标识信息
     * @return 系统标识集合
     */
    @Override
    public List<SystemDeploy> selectSystemDeployList(SystemDeploy systemDeploy) {
        return systemDeployMapper.selectSystemDeployList(systemDeploy);
    }

    /**
     * 新增系统标识
     *
     * @param systemDeploy 系统标识信息
     * @return 结果
     */
    @Override
    public int insertSystemDeploy(SystemDeploy systemDeploy) {
        return systemDeployMapper.insertSystemDeploy(systemDeploy);
    }

    /**
     * 修改系统标识
     *
     * @param systemDeploy 系统标识信息
     * @return 结果
     */
    @Override
    public int updateSystemDeploy(SystemDeploy systemDeploy) {
        return systemDeployMapper.updateSystemDeploy(systemDeploy);
    }

    /**
     * 删除系统标识对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSystemDeployByIds(String ids) {
        return systemDeployMapper.deleteSystemDeployByIds(Convert.toStrArray(ids));
    }

}
