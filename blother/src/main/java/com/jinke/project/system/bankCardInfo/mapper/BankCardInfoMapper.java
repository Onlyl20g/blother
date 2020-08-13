package com.jinke.project.system.bankCardInfo.mapper;

import com.jinke.project.system.bankCardInfo.domain.BankCardInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 银行卡 数据层
 *
 * @author jinke
 * @date 2019-08-22
 */
@Component
public interface BankCardInfoMapper {
    /**
     * 查询银行卡信息
     *
     * @param id 银行卡ID
     * @return 银行卡信息
     */
    public BankCardInfo selectBankCardInfoById(Long id);

    /**
     * 查询银行卡列表
     *
     * @param bankCardInfo 银行卡信息
     * @return 银行卡集合
     */
    public List<BankCardInfo> selectBankCardInfoList(BankCardInfo bankCardInfo);

    /**
     * 新增银行卡
     *
     * @param bankCardInfo 银行卡信息
     * @return 结果
     */
    public int insertBankCardInfo(BankCardInfo bankCardInfo);

    /**
     * 修改银行卡
     *
     * @param bankCardInfo 银行卡信息
     * @return 结果
     */
    public int updateBankCardInfo(BankCardInfo bankCardInfo);

    /**
     * 删除银行卡
     *
     * @param id 银行卡ID
     * @return 结果
     */
    public int deleteBankCardInfoById(Long id);

    /**
     * 批量删除银行卡
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBankCardInfoByIds(String[] ids);

    /**
     * 修改银行卡
     *
     * @param bankCardInfo 银行卡信息
     * @return 结果
     */
    public int updateBankCardInfoByBankCard(BankCardInfo bankCardInfo);

    /**
     * 验证密码
     *
     * @param bankCard
     * @return
     */
    public BankCardInfo selectBankCard(@Param("bankCard") String bankCard);
}