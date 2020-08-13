package com.jinke.project.system.creditBuyerInfo.service;

import com.jinke.common.utils.text.Convert;
import com.jinke.project.customer.domain.Credit;
import com.jinke.project.customer.mapper.CreditMapper;
import com.jinke.project.system.creditBuyerInfo.domain.CreditBuyerInfo;
import com.jinke.project.system.creditBuyerInfo.mapper.CreditBuyerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 授信申请附 服务层实现
 *
 * @author jinke
 * @date 2019-08-01
 */
@Service
public class CreditBuyerInfoServiceImpl implements ICreditBuyerInfoService {
    @Autowired
    private CreditBuyerInfoMapper creditBuyerInfoMapper;

    @Autowired
    private CreditMapper creditMapper;

    /**
     * 查询授信申请附信息
     *
     * @param creditsId 授信申请附ID
     * @return 授信申请附信息
     */
    @Override
    public CreditBuyerInfo selectCreditBuyerInfoById(Long creditsId) {
        return creditBuyerInfoMapper.selectCreditBuyerInfoById(creditsId);
    }

    /**
     * 查询授信申请附列表
     *
     * @param creditBuyerInfo 授信申请附信息
     * @return 授信申请附集合
     */
    @Override
    public List<CreditBuyerInfo> selectCreditBuyerInfoList(CreditBuyerInfo creditBuyerInfo) {
        return creditBuyerInfoMapper.selectCreditBuyerInfoList(creditBuyerInfo);
    }

    /**
     * 新增授信申请附
     *
     * @param creditBuyerInfo 授信申请附信息
     * @return 结果
     */
    @Override
    public int insertCreditBuyerInfo(CreditBuyerInfo creditBuyerInfo) {
        return creditBuyerInfoMapper.insertCreditBuyerInfo(creditBuyerInfo);
    }

    /**
     * 修改授信申请附
     *
     * @param creditBuyerInfo 授信申请附信息
     * @return 结果
     */
    @Override
    public int updateCreditBuyerInfo(CreditBuyerInfo creditBuyerInfo) {
        return creditBuyerInfoMapper.updateCreditBuyerInfo(creditBuyerInfo);
    }

    @Override
    public int deleteCreditSByIds(String ids) {
        return 0;
    }

    /**
     * 查询买方信息
     *
     * @param request
     * @return
     */
    @Override
    public List<CreditBuyerInfo> selectBuy(HttpServletRequest request) {
        CreditBuyerInfo creditBuyerInfo = new CreditBuyerInfo();
        Credit credit = new Credit();
        credit.setBillId(request.getParameter("billId"));
        List<Credit> credits = creditMapper.selectCreditList(credit);
        if (credits.size() > 0) {
            credit = credits.get(0);
            creditBuyerInfo.setCreditId(credit.getId());
            List<CreditBuyerInfo> list = creditBuyerInfoMapper.selectCreditBuyerInfoList(creditBuyerInfo);
            return list;
        }
        return null;
    }

    /**
     * 删除授信申请附对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCreditBuyerInfoByIds(String ids) {
        return creditBuyerInfoMapper.deleteCreditBuyerInfoByIds(Convert.toStrArray(ids));
    }

    public CreditBuyerInfo selectBuyTime() {
        return null;
    }
}
