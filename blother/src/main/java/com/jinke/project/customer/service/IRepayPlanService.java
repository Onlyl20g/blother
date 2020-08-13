package com.jinke.project.customer.service;


import com.jinke.project.customer.domain.RepayPlan;
import com.jinke.project.customer.domain.RepayPlanDto;

import java.util.List;

/**
 * 还款计划附 服务层
 *
 * @author jinke
 * @date 2019-08-13
 */
public interface IRepayPlanService {
    /**
     * 查询还款计划附信息
     *
     * @param id 还款计划附ID
     * @return 还款计划附信息
     */
    public RepayPlan selectRepayPlanById(Long id);

    /**
     * 查询还款计划附列表
     *
     * @param repayPlan 还款计划附信息
     * @return 还款计划附集合
     */
    public List<RepayPlanDto> selectRepayPlanList(RepayPlan repayPlan);

    /**
     * 新增还款计划附
     *
     * @param repayPlan 还款计划附信息
     * @return 结果
     */
    public int insertRepayPlan(RepayPlan repayPlan);

    /**
     * 修改还款计划附
     *
     * @param repayPlan 还款计划附信息
     * @return 结果
     */
    public int updateRepayPlan(RepayPlan repayPlan);

    /**
     * 删除还款计划附信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepayPlanByIds(String ids);

}
