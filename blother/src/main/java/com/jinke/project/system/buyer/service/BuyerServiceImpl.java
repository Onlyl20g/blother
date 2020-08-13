package com.jinke.project.system.buyer.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.buyer.domain.Buyer;
import com.jinke.project.system.buyer.mapper.BuyerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 买方(nc) 服务层实现
 *
 * @author jinke
 * @date 2019-08-09
 */
@Service
public class BuyerServiceImpl implements IBuyerService {
    @Autowired
    private BuyerMapper buyerMapper;

    /**
     * 查询买方(nc)信息
     *
     * @param buyerId 买方(nc)ID
     * @return 买方(nc)信息
     */
    @Override
    public Buyer selectBuyerById(String buyerId) {
        return buyerMapper.selectBuyerById(buyerId);
    }

    /**
     * 查询买方(nc)列表
     *
     * @param buyer 买方(nc)信息
     * @return 买方(nc)集合
     */
    @Override
    public List<Buyer> selectBuyerList(Buyer buyer) {
        return buyerMapper.selectBuyerList(buyer);
    }

    /**
     * 新增买方(nc)
     *
     * @param buyer 买方(nc)信息
     * @return 结果
     */
    @Override
    public int insertBuyer(Buyer buyer) {
        return buyerMapper.insertBuyer(buyer);
    }

    /**
     * 修改买方(nc)
     *
     * @param buyer 买方(nc)信息
     * @return 结果
     */
    @Override
    public int updateBuyer(Buyer buyer) {
        return buyerMapper.updateBuyer(buyer);
    }

    /**
     * 删除买方(nc)对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBuyerByIds(String ids) {
        return buyerMapper.deleteBuyerByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询买方信息最新更新时间
     *
     * @return
     */
    public Buyer selectBuyerTime() {
        return buyerMapper.selectBuyerTime();
    }
}
