package com.jinke.project.system.creditBuyerInfo.service;

import com.jinke.project.system.creditBuyerInfo.domain.CreditBuyerInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 授信申请附 服务层
 *
 * @author jinke
 * @date 2019-08-01
 */
public interface ICreditBuyerInfoService {
    /**
     * 查询授信申请附信息
     *
     * @param creditsId 授信申请附ID
     * @return 授信申请附信息
     */
    public CreditBuyerInfo selectCreditBuyerInfoById(Long creditsId);

    /**
     * 查询授信申请附列表
     *
     * @param creditBuyerInfo 授信申请附信息
     * @return 授信申请附集合
     */
    public List<CreditBuyerInfo> selectCreditBuyerInfoList(CreditBuyerInfo creditBuyerInfo);

    /**
     * 新增授信申请附
     *
     * @param creditBuyerInfo 授信申请附信息
     * @return 结果
     */
    public int insertCreditBuyerInfo(CreditBuyerInfo creditBuyerInfo);

    /**
     * 修改授信申请附
     *
     * @param creditBuyerInfo 授信申请附信息
     * @return 结果
     */
    public int updateCreditBuyerInfo(CreditBuyerInfo creditBuyerInfo);

    /**
     * 删除授信申请附信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCreditSByIds(String ids);

    /**
     * 查询对应买方企业
     *
     * @param
     * @return
     */
    public List<CreditBuyerInfo> selectBuy(HttpServletRequest request);

    /**
     * 删除买方企业
     *
     * @param ids
     * @return
     */
    public int deleteCreditBuyerInfoByIds(String ids);

    /**
     * 查询最新更新时间
     */
    public CreditBuyerInfo selectBuyTime();
}
