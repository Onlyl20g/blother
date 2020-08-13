package com.jinke.project.customer.mapper;

import com.jinke.project.customer.domain.RepayPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 还款计划附 数据层
 *
 * @author jinke
 * @date 2019-08-13
 */
@Component
public interface RepayPlanMapper {
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
    public List<RepayPlan> selectRepayPlanList(RepayPlan repayPlan);

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
     * 删除还款计划附
     *
     * @param id 还款计划附ID
     * @return 结果
     */
    public int deleteRepayPlanById(Long id);

    /**
     * 批量删除还款计划附
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepayPlanByIds(String[] ids);

    public void deleteRepayPlanByCashApplyId(@Param("cashApplyId") String cashApplyId);
}