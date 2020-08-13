package com.jinke.project.customer.mapper;

import com.jinke.project.customer.domain.Credit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 授信申请 数据层
 *
 * @author jinke
 * @date 2019-07-17
 */

@Component
public interface CreditMapper {
    /**
     * 查询授信申请信息
     *
     * @param id 授信申请ID
     * @return 授信申请信息
     */
    public Credit selectCreditById(Long id);

    /**
     * 查询授信申请列表
     *
     * @param credit 授信申请信息
     * @return 授信申请集合
     */
    public List<Credit> selectCreditList(Credit credit);

    /**
     * 新增授信申请
     *
     * @param credit 授信申请信息
     * @return 结果
     */
    public int insertCredit(Credit credit);

    /**
     * 修改授信申请
     *
     * @param credit 授信申请信息
     * @return 结果
     */
    public int updateCredit(Credit credit);

    /**
     * 修改授信申请byBillId
     *
     * @param credit 授信申请信息
     * @return 结果
     */
    public int updateCreditByBillId(Credit credit);

    /**
     * 删除授信申请
     *
     * @param id 授信申请ID
     * @return 结果
     */
    public int deleteCreditById(Long id);

    /**
     * 批量删除授信申请
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCreditByIds(String[] ids);

    /**
     * 批量修改授信申请
     *
     * @param list
     */
    public void updateCreditList(@Param(value = "list") List<Credit> list);
}
