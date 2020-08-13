package com.jinke.project.customer.mapper;


import com.jinke.project.customer.domain.RepayRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 还款计划 数据层
 *
 * @author jinke
 * @date 2019-08-13
 */
@Component
public interface RepayRecordMapper {
    /**
     * 查询还款计划信息
     *
     * @param id 还款计划ID
     * @return 还款计划信息
     */
    public RepayRecord selectRepayRecordById(Long id);

    /**
     * 查询还款计划列表
     *
     * @param repayRecord 还款计划信息
     * @return 还款计划集合
     */
    public List<RepayRecord> selectRepayRecordList(RepayRecord repayRecord);

    /**
     * 新增还款计划
     *
     * @param repayRecord 还款计划信息
     * @return 结果
     */
    public int insertRepayRecord(RepayRecord repayRecord);

    /**
     * 修改还款计划
     *
     * @param repayRecord 还款计划信息
     * @return 结果
     */
    public int updateRepayRecord(RepayRecord repayRecord);

    /**
     * 删除还款计划
     *
     * @param id 还款计划ID
     * @return 结果
     */
    public int deleteRepayRecordById(Long id);

    /**
     * 批量删除还款计划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepayRecordByIds(String[] ids);

    public void deleteRepayRecordByCashApplyId(@Param("cashApplyId") String cashApplyId);
}