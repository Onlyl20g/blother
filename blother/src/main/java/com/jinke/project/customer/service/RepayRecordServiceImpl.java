package com.jinke.project.customer.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jinke.common.utils.text.Convert;
import com.jinke.project.customer.domain.RepayRecord;
import com.jinke.project.customer.domain.RepayRecordDto;
import com.jinke.project.customer.domain.Withdraw;
import com.jinke.project.customer.mapper.RepayRecordMapper;
import com.jinke.project.customer.mapper.WithdrawMapper;
import com.jinke.project.system.ncInteractivity.controller.NCInteractiveControllor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 还款计划 服务层实现
 *
 * @author jinke
 * @date 2019-08-13
 */
@Service
public class RepayRecordServiceImpl implements IRepayRecordService {
    @Autowired
    private RepayRecordMapper repayRecordMapper;

    @Autowired
    private WithdrawMapper withdrawMapper;

    @Autowired
    private NCInteractiveControllor ncInteractiveControllor;

    /**
     * 查询还款计划信息
     *
     * @param id 还款计划ID
     * @return 还款计划信息
     */
    @Override
    public RepayRecord selectRepayRecordById(Long id) {
        return repayRecordMapper.selectRepayRecordById(id);
    }

    /**
     * 查询还款计划列表
     *
     * @param repayRecord 还款计划信息
     * @return 还款计划集合
     */
    @Override
    public List<RepayRecordDto> selectRepayRecordList(RepayRecord repayRecord) {
        Withdraw withdraw = withdrawMapper.selectWithdrawById(Long.valueOf(repayRecord.getCashApplyId()));
        findInfoByNc(withdraw.getBillId());
        repayRecord.setCashApplyId(withdraw.getBillId());
        List<RepayRecord> repayRecords = repayRecordMapper.selectRepayRecordList(repayRecord);
        List<RepayRecordDto> repayRecordDtos = new ArrayList<>();
        for (RepayRecord repayRecord1 : repayRecords) {
            RepayRecordDto repayRecordDto = new RepayRecordDto();
            BeanUtils.copyProperties(repayRecord1, repayRecordDto);
            repayRecordDtos.add(repayRecordDto);
        }
        return repayRecordDtos;
    }

    public void findInfoByNc(String billId) {
        String data = null;
        try {
            data = ncInteractiveControllor.applyStatusPost(billId);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String data = "{\"flag\":\"success\",\"code\":\"200\",\"msg\":\"\",\"body\":{\"cashApplyId\":\"00001\",\"applyCashPeriod\":\"36\",\"pendingAmount\":\"568540.00\",\"repayInfo\":[{\"amount\":\"100000.00\",\"repayDate\":\"2019-08-08\",\"remark\":\"\"},{\"amount\":\"100000.00\",\"repayDate\":\"2019-09-08\",\"remark\":\"\"}]}}";
        JSONObject obj = JSON.parseObject(data);
        if (obj == null) {
            return;
        }
        if (obj.get("flag").equals("success")) {
            String item = JSON.toJSONString(obj.get("body"));
            JSONObject it = JSON.parseObject(item);
            JSONArray listItem = JSON.parseArray(it.get("repayInfo").toString());
            repayRecordMapper.deleteRepayRecordByCashApplyId(it.get("cashApplyId").toString());
            for (Object ob : listItem) {
                Map<String, Object> map = JSON.parseObject(ob.toString());
                RepayRecord re = new RepayRecord();
                re.setCashApplyId(it.get("cashApplyId").toString());
                if (map.get("repayDate") != null) {
                    re.setRepayDate(map.get("repayDate").toString());
                }
                if (it.get("pendingAmount") != null) {
                    re.setPendingAmount(it.get("pendingAmount").toString());
                }
                if (map.get("loanRecord") != null) {
                    re.setLoanRecord(map.get("loanRecord").toString());
                }
                if (map.get("amount") != null) {
                    re.setAmount(map.get("amount").toString());
                }
                repayRecordMapper.insertRepayRecord(re);

            }
        }
    }

    /**
     * 新增还款计划
     *
     * @param repayRecord 还款计划信息
     * @return 结果
     */
    @Override
    public int insertRepayRecord(RepayRecord repayRecord) {
        return repayRecordMapper.insertRepayRecord(repayRecord);
    }

    /**
     * 修改还款计划
     *
     * @param repayRecord 还款计划信息
     * @return 结果
     */
    @Override
    public int updateRepayRecord(RepayRecord repayRecord) {
        return repayRecordMapper.updateRepayRecord(repayRecord);
    }

    /**
     * 删除还款计划对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRepayRecordByIds(String ids) {
        return repayRecordMapper.deleteRepayRecordByIds(Convert.toStrArray(ids));
    }

}
