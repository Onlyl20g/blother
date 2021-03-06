package com.jinke.project.system.enterprise.mapper;

import com.jinke.project.system.enterprise.domain.Enterprise;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 企业 数据层
 *
 * @author jinke
 * @date 2019-08-05
 */
@Component
public interface EnterpriseMapper {
    /**
     * 查询企业信息
     *
     * @param id 企业ID
     * @return 企业信息
     */
    public Enterprise selectEnterpriseById(Long id);

    /**
     * 查询企业列表
     *
     * @param enterprise 企业信息
     * @return 企业集合
     */
    public List<Enterprise> selectEnterpriseList(Enterprise enterprise);

    /**
     * 新增企业
     *
     * @param enterprise 企业信息
     * @return 结果
     */
    public int insertEnterprise(Enterprise enterprise);

    /**
     * 修改企业
     *
     * @param enterprise 企业信息
     * @return 结果
     */
    public int updateEnterprise(Enterprise enterprise);

    /**
     * 删除企业
     *
     * @param id 企业ID
     * @return 结果
     */
    public int deleteEnterpriseById(Long id);

    /**
     * 批量删除企业
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEnterpriseByIds(String[] ids);

    /**
     * @param enterpriseId
     * @return
     */
    public Enterprise selectEnterpriseByEId(String enterpriseId);

    public int updateEnterpriseAll(Enterprise enterprise);
}