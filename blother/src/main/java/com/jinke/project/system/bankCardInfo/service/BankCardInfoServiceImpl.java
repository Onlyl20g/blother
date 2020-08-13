package com.jinke.project.system.bankCardInfo.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.bankCardInfo.domain.BankCardInfo;
import com.jinke.project.system.bankCardInfo.mapper.BankCardInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 银行卡 服务层实现
 *
 * @author jinke
 * @date 2019-08-22
 */
@Service
public class BankCardInfoServiceImpl implements IBankCardInfoService {
    @Autowired
    private BankCardInfoMapper bankCardInfoMapper;

    /**
     * 查询银行卡信息
     *
     * @param id 银行卡ID
     * @return 银行卡信息
     */
    @Override
    public BankCardInfo selectBankCardInfoById(Long id) {
        return bankCardInfoMapper.selectBankCardInfoById(id);
    }

    /**
     * 查询银行卡列表
     *
     * @param bankCardInfo 银行卡信息
     * @return 银行卡集合
     */
    @Override
    public List<BankCardInfo> selectBankCardInfoList(BankCardInfo bankCardInfo) {
        return bankCardInfoMapper.selectBankCardInfoList(bankCardInfo);
    }

    /**
     * 新增银行卡
     *
     * @param bankCardInfo 银行卡信息
     * @return 结果
     */
    @Override
    public int insertBankCardInfo(BankCardInfo bankCardInfo) {
        return bankCardInfoMapper.insertBankCardInfo(bankCardInfo);
    }

    /**
     * 修改银行卡
     *
     * @param bankCardInfo 银行卡信息
     * @return 结果
     */
    @Override
    public int updateBankCardInfo(BankCardInfo bankCardInfo) {
        return bankCardInfoMapper.updateBankCardInfo(bankCardInfo);
    }

    /**
     * 修改银行卡
     *
     * @param bankCardInfo 银行卡信息
     * @return 结果
     */
    @Override
    public int updateBankCardInfoByBankCard(BankCardInfo bankCardInfo) {
        return bankCardInfoMapper.updateBankCardInfoByBankCard(bankCardInfo);
    }

    /**
     * 删除银行卡对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBankCardInfoByIds(String ids) {
        return bankCardInfoMapper.deleteBankCardInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 验证密码
     *
     * @param bankCard
     * @return
     */
    public BankCardInfo selectBankCard(String bankCard) {
        return bankCardInfoMapper.selectBankCard(bankCard);
    }
}
