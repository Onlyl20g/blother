package com.jinke.project.customer.service;


import com.jinke.project.customer.domain.RepayRecord;
import com.jinke.project.customer.domain.RepayRecordDto;

import java.util.List;

/**
 * 还款计划 服务层
 *
 * @author jinke
 * @date 2019-08-13
 */
public interface IRepayRecordService {
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
    public List<RepayRecordDto> selectRepayRecordList(RepayRecord repayRecord);

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
     * 删除还款计划信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepayRecordByIds(String ids);

}
