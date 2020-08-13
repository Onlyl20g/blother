package com.jinke.project.system.creditBuyerInfo.mapper;

import com.jinke.project.system.creditBuyerInfo.domain.CreditBuyerInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 授信申请附 数据层
 *
 * @author jinke
 * @date 2019-08-01
 */
@Component
public interface CreditBuyerInfoMapper {
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
     * 删除授信申请附
     *
     * @param creditsId 授信申请附ID
     * @return 结果
     */
    public int deleteCreditBuyerInfoById(Long creditsId);

    /**
     * 批量删除授信申请附
     *
     * @param creditsIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCreditBuyerInfoByIds(String[] creditsIds);

}