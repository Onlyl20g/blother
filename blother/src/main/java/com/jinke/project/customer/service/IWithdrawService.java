package com.jinke.project.customer.service;

import com.jinke.project.customer.domain.Withdraw;
import com.jinke.project.customer.domain.WithdrawDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 提款申请 服务层
 *
 * @author jinke
 * @date 2019-07-23
 */
public interface IWithdrawService {
    /**
     * 查询提款申请信息
     *
     * @param id 提款申请ID
     * @return 提款申请信息
     */
    public Withdraw selectWithdrawById(Long id);

    /**
     * 查询提款申请列表
     *
     * @param withdraw 提款申请信息
     * @return 提款申请集合
     */
    public List<Withdraw> selectWithdrawList(Withdraw withdraw);


    public List<WithdrawDto> findRepayPlanList();

    /**
     * 查询提款申请列表
     *
     * @return 提款申请集合
     */
    public List<WithdrawDto> selectWithdrawByCreditBillId(String creditBillId);

    /**
     * 新增提款申请
     *
     * @param withdraw 提款申请信息
     * @return 结果
     */
    public int insertWithdraw(Withdraw withdraw);

    /**
     * 修改提款申请
     *
     * @param withdraw 提款申请信息
     * @return 结果
     */
    public int updateWithdraw(Withdraw withdraw);

    /**
     * 删除提款申请信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWithdrawByIds(String ids);

    /**
     * 新增保存提款申请
     */
    public Map<String, Object> save(@RequestBody Map<String, String> map);
}
