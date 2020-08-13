package com.jinke.project.system.billId.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.billId.domain.BillId;
import com.jinke.project.system.billId.mapper.BillIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务申请单据凭证 服务层实现
 *
 * @author jinke
 * @date 2019-11-27
 */
@Service
public class BillIdServiceImpl implements IBillIdService {
    @Autowired
    private BillIdMapper billIdMapper;

    /**
     * 查询业务申请单据凭证信息
     *
     * @param id 业务申请单据凭证ID
     * @return 业务申请单据凭证信息
     */
    @Override
    public BillId selectBillIdById(Long id) {
        return billIdMapper.selectBillIdById(id);
    }

    /**
     * 查询业务申请单据凭证列表
     *
     * @param billId 业务申请单据凭证信息
     * @return 业务申请单据凭证集合
     */
    @Override
    public List<BillId> selectBillIdList(BillId billId) {
        return billIdMapper.selectBillIdList(billId);
    }

    /**
     * 新增业务申请单据凭证
     *
     * @param billId 业务申请单据凭证信息
     * @return 结果
     */
    @Override
    public int insertBillId(BillId billId) {
        return billIdMapper.insertBillId(billId);
    }

    /**
     * 修改业务申请单据凭证
     *
     * @param billId 业务申请单据凭证信息
     * @return 结果
     */
    @Override
    public int updateBillId(BillId billId) {
        return billIdMapper.updateBillId(billId);
    }

    /**
     * 删除业务申请单据凭证对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBillIdByIds(String ids) {
        return billIdMapper.deleteBillIdByIds(Convert.toStrArray(ids));
    }

}
