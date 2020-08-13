package com.jinke.project.system.buyer.mapper;

import com.jinke.project.system.buyer.domain.Buyer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 买方(nc) 数据层
 *
 * @author jinke
 * @date 2019-08-09
 */
@Component
public interface BuyerMapper {
    /**
     * 查询买方(nc)信息
     *
     * @param buyerId 买方(nc)ID
     * @return 买方(nc)信息
     */
    public Buyer selectBuyerById(String buyerId);

    /**
     * 查询买方(nc)列表
     *
     * @param buyer 买方(nc)信息
     * @return 买方(nc)集合
     */
    public List<Buyer> selectBuyerList(Buyer buyer);

    /**
     * 新增买方(nc)
     *
     * @param buyer 买方(nc)信息
     * @return 结果
     */
    public int insertBuyer(Buyer buyer);

    /**
     * 修改买方(nc)
     *
     * @param buyer 买方(nc)信息
     * @return 结果
     */
    public int updateBuyer(Buyer buyer);

    /**
     * 删除买方(nc)
     *
     * @param buyerId 买方(nc)ID
     * @return 结果
     */
    public int deleteBuyerById(String buyerId);

    /**
     * 批量删除买方(nc)
     *
     * @param buyerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBuyerByIds(String[] buyerIds);

    /**
     * 查询买方信息最新更新时间
     *
     * @return
     */
    public Buyer selectBuyerTime();
}