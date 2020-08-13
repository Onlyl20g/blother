package com.jinke.project.system.billId.service;

import com.jinke.project.system.billId.domain.BillId;

import java.util.List;

/**
 * 业务申请单据凭证 服务层
 *
 * @author jinke
 * @date 2019-11-27
 */
public interface IBillIdService {
    /**
     * 查询业务申请单据凭证信息
     *
     * @param id 业务申请单据凭证ID
     * @return 业务申请单据凭证信息
     */
    public BillId selectBillIdById(Long id);

    /**
     * 查询业务申请单据凭证列表
     *
     * @param billId 业务申请单据凭证信息
     * @return 业务申请单据凭证集合
     */
    public List<BillId> selectBillIdList(BillId billId);

    /**
     * 新增业务申请单据凭证
     *
     * @param billId 业务申请单据凭证信息
     * @return 结果
     */
    public int insertBillId(BillId billId);

    /**
     * 修改业务申请单据凭证
     *
     * @param billId 业务申请单据凭证信息
     * @return 结果
     */
    public int updateBillId(BillId billId);

    /**
     * 删除业务申请单据凭证信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBillIdByIds(String ids);

}
