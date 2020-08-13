package com.jinke.project.customer.mapper;

import com.jinke.project.customer.domain.Withdraw;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 提款申请 数据层
 *
 * @author jinke
 * @date 2019-07-23
 */
@Component
public interface WithdrawMapper {
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
     * 删除提款申请
     *
     * @param id 提款申请ID
     * @return 结果
     */
    public int deleteWithdrawById(Long id);

    /**
     * 批量删除提款申请
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWithdrawByIds(String[] ids);


    /**
     * 根据授信id查询提款列表
     *
     * @param withdraw
     * @return
     */
    public List<Withdraw> findWithdrawList(Withdraw withdraw);
}
