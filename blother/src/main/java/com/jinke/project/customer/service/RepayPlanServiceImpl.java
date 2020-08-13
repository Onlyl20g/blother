package com.jinke.project.customer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jinke.common.utils.text.Convert;
import com.jinke.project.customer.domain.RepayPlan;
import com.jinke.project.customer.domain.RepayPlanDto;
import com.jinke.project.customer.domain.Withdraw;
import com.jinke.project.customer.mapper.RepayPlanMapper;
import com.jinke.project.customer.mapper.WithdrawMapper;
import com.jinke.project.system.ncInteractivity.controller.NCInteractiveControllor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 还款计划附 服务层实现
 *
 * @author jinke
 * @date 2019-08-13
 */
@Service
public class RepayPlanServiceImpl implements IRepayPlanService {
    @Autowired
    private RepayPlanMapper repayPlanMapper;

    @Autowired
    private WithdrawMapper withdrawMapper;

    @Autowired
    private NCInteractiveControllor ncInteractiveControllor;

    /**
     * 查询还款计划附信息
     *
     * @param id 还款计划附ID
     * @return 还款计划附信息
     */
    @Override
    public RepayPlan selectRepayPlanById(Long id) {
        return repayPlanMapper.selectRepayPlanById(id);
    }

    /**
     * 查询还款计划附列表
     *
     * @param repayPlan 还款计划附信息
     * @return 还款计划附集合
     */
    @Override
    public List<RepayPlanDto> selectRepayPlanList(RepayPlan repayPlan) {
        Withdraw withdraw = withdrawMapper.selectWithdrawById(Long.valueOf(repayPlan.getCashApplyId()));
        findInfoByNc(withdraw.getBillId());
        repayPlan.setCashApplyId(withdraw.getBillId());
        List<RepayPlan> repayPlans = repayPlanMapper.selectRepayPlanList(repayPlan);
        List<RepayPlanDto> repayPlanDtos = new ArrayList<>();
        for (RepayPlan repayPlan1 : repayPlans) {
            RepayPlanDto repayPlanDto = new RepayPlanDto();
            BeanUtils.copyProperties(repayPlan1, repayPlanDto);
            repayPlanDtos.add(repayPlanDto);
        }
        return repayPlanDtos;
    }

    public void findInfoByNc(String ashApplyId) {
        String data = null;
        try {
            data = ncInteractiveControllor.applyListPost(ashApplyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String data = "{\"flag\":\"success\",\"code\":\"200\",\"msg\":\"\",\"body\":{\"cashApplyId\":\"00001\",\"applyCashPeriod\":\"36\",\"repayPlan\":[{\"amount\":\"100000.00\",\"repayDate\":\"2019-08-08\",\"status\":\"1\"},{\"amount\":\"100000.00\",\"repayDate\":\"2019-09-08\",\"status\":\"1\"}]}}";
        JSONObject obj = JSON.parseObject(data);
        if (obj == null) {
            return;
        }
        if ("success".equals(obj.get("flag"))) {
            String item = JSON.toJSONString(obj.get("body"));
            JSONObject it = JSON.parseObject(item);
            JSONArray listItem = JSON.parseArray(it.get("repayPlan").toString());
            repayPlanMapper.deleteRepayPlanByCashApplyId(it.get("cashApplyId").toString());
            for (Object ob : listItem) {
                Map<String, Object> map = JSON.parseObject(ob.toString());
                RepayPlan re = new RepayPlan();
                if (it.get("cashApplyId") != null) {
                    re.setCashApplyId(it.get("cashApplyId").toString());
                }
                if (map.get("repayDate") != null) {
                    re.setRepayDate(map.get("repayDate").toString());
                }
                if (map.get("loanRecord") != null) {
                    re.setLoanRecord(map.get("loanRecord").toString());
                }
                if (map.get("amount") != null) {
                    re.setAmount(map.get("amount").toString());
                }
                if (map.get("status") != null) {
                    re.setStatus(map.get("status").toString());
                }
                if (map.size() == 0) {
                    break;
                }
                if (map.get("status") != null) {
                    re.setStatus(map.get("status").toString());
                }
                repayPlanMapper.insertRepayPlan(re);
            }
        }
    }

    /*public static void main(String[] args) {
        RepayPlanServiceImpl r = new RepayPlanServiceImpl();
        r.findInfoByNc("11");
    }*/

    /**
     * 新增还款计划附
     *
     * @param repayPlan 还款计划附信息
     * @return 结果
     */
    @Override
    public int insertRepayPlan(RepayPlan repayPlan) {
        return repayPlanMapper.insertRepayPlan(repayPlan);
    }

    /**
     * 修改还款计划附
     *
     * @param repayPlan 还款计划附信息
     * @return 结果
     */
    @Override
    public int updateRepayPlan(RepayPlan repayPlan) {
        return repayPlanMapper.updateRepayPlan(repayPlan);
    }

    /**
     * 删除还款计划附对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRepayPlanByIds(String ids) {
        return repayPlanMapper.deleteRepayPlanByIds(Convert.toStrArray(ids));
    }

}
