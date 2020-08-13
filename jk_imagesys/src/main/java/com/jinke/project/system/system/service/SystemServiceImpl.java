package com.jinke.project.system.system.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.system.domain.System;
import com.jinke.project.system.system.mapper.SystemMapper;
import com.jinke.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 其他系统密钥 服务层实现
 *
 * @author jinke
 * @date 2019-12-06
 */
@Service
public class SystemServiceImpl implements ISystemService {
    @Autowired
    private SystemMapper systemMapper;

    /**
     * 查询其他系统密钥信息
     *
     * @param id 其他系统密钥ID
     * @return 其他系统密钥信息
     */
    @Override
    public System selectSystemById(Integer id) {
        return systemMapper.selectSystemById(id);
    }

    /**
     * 查询其他系统密钥列表
     *
     * @param system 其他系统密钥信息
     * @return 其他系统密钥集合
     */
    @Override
    public List<System> selectSystemList(System system) {
        return systemMapper.selectSystemList(system);
    }

    /**
     * 新增其他系统密钥
     *
     * @param system 其他系统密钥信息
     * @return 结果
     */
    @Override
    public int insertSystem(System system) {
        if (system.getCreateTime() == null) {
            system.setCreateTime(new Date());
        }
        if (system.getDelFlag() == null || system.getDelFlag().equals("")) {
            system.setDelFlag("0");
        }
        if (system.getStatus() == null || system.getStatus().equals("")) {
            system.setStatus("0");
        }
        return systemMapper.insertSystem(system);
    }

    /**
     * 修改其他系统密钥
     *
     * @param system 其他系统密钥信息
     * @return 结果
     */
    @Override
    public int updateSystem(System system) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        system.setUpdateTime(new Date());
        system.setUpdateBy(user.getUserName());
        return systemMapper.updateSystem(system);
    }

    /**
     * 删除其他系统密钥对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSystemByIds(String ids) {
        return systemMapper.deleteSystemByIds(Convert.toStrArray(ids));
    }

}
