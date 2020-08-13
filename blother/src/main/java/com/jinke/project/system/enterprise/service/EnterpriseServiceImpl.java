package com.jinke.project.system.enterprise.service;

import cn.hutool.core.convert.Convert;
import com.jinke.project.system.enterprise.domain.Enterprise;
import com.jinke.project.system.enterprise.mapper.EnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业 服务层实现
 *
 * @author jinke
 * @date 2019-08-05
 */
@Service
public class EnterpriseServiceImpl implements IEnterpriseService {
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    /**
     * 查询企业信息
     *
     * @param id 企业ID
     * @return 企业信息
     */
    @Override
    public Enterprise selectEnterpriseById(Long id) {
        return enterpriseMapper.selectEnterpriseById(id);
    }

    /**
     * 查询企业列表
     *
     * @param enterprise 企业信息
     * @return 企业集合
     */
    @Override
    public List<Enterprise> selectEnterpriseList(Enterprise enterprise) {
        return enterpriseMapper.selectEnterpriseList(enterprise);
    }

    /**
     * 新增企业
     *
     * @param enterprise 企业信息
     * @return 结果
     */
    @Override
    public int insertEnterprise(Enterprise enterprise) {
        return enterpriseMapper.insertEnterprise(enterprise);
    }

    /**
     * 修改企业
     *
     * @param enterprise 企业信息
     * @return 结果
     */
    @Override
    public int updateEnterprise(Enterprise enterprise) {
        return enterpriseMapper.updateEnterprise(enterprise);
    }

    /**
     * 删除企业对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteEnterpriseByIds(String ids) {
        return enterpriseMapper.deleteEnterpriseByIds(Convert.toStrArray(ids));
    }

    public Enterprise selectEnterpriseByEId(String enterpriseId) {
        return enterpriseMapper.selectEnterpriseByEId(enterpriseId);
    }

    public int updateEnterpriseAll(Enterprise enterprise) {
        return enterpriseMapper.updateEnterpriseAll(enterprise);
    }
}
