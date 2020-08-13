package com.jinke.project.customer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jinke.common.utils.security.ShiroUtils;
import com.jinke.common.utils.text.Convert;
import com.jinke.project.customer.domain.Credit;
import com.jinke.project.customer.domain.Withdraw;
import com.jinke.project.customer.domain.WithdrawDto;
import com.jinke.project.customer.domain.WithdrawStatus;
import com.jinke.project.customer.mapper.CreditMapper;
import com.jinke.project.customer.mapper.WithdrawMapper;
import com.jinke.project.system.bankCardInfo.domain.BankCardInfo;
import com.jinke.project.system.bankCardInfo.mapper.BankCardInfoMapper;
import com.jinke.project.system.buyer.domain.Buyer;
import com.jinke.project.system.buyer.mapper.BuyerMapper;
import com.jinke.project.system.ncInteractivity.controller.NCInteractiveControllor;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.supplier.mapper.SupplierMapper;
import com.jinke.project.system.supplier.service.ISupplierService;
import com.jinke.project.system.upload.service.UploadFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * 提款申请 服务层实现
 *
 * @author jinke
 * @date 2019-07-23
 */
@Service
public class WithdrawServiceImpl implements IWithdrawService {
    @Autowired
    private WithdrawMapper withdrawMapper;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private BuyerMapper buyerMapper;

    @Autowired
    private ISupplierService iSupplierService;

    @Autowired
    private BankCardInfoMapper bankCardInfoMapper;

    @Autowired
    private NCInteractiveControllor ncInteractiveControllor;

    /**
     * 查询提款申请信息
     *
     * @param id 提款申请ID
     * @return 提款申请信息
     */
    @Override
    public Withdraw selectWithdrawById(Long id) {
        return withdrawMapper.selectWithdrawById(id);
    }

    /**
     * 查询提款申请列表
     *
     * @param withdraw 提款申请信息
     * @return 提款申请集合
     */
    @Override
    public List<Withdraw> selectWithdrawList(Withdraw withdraw) {
        return withdrawMapper.selectWithdrawList(withdraw);
    }

    @Override
    public List<WithdrawDto> selectWithdrawByCreditBillId(String creditBillId) {
        Withdraw temp = new Withdraw();
        temp.setCreditBillId(creditBillId);
        findWithdrawByNC(creditBillId);//新增向核心发送查询提款信息
        List<Withdraw> withdraws = withdrawMapper.selectWithdrawList(temp);
        List<WithdrawDto> withdrawDtos = new ArrayList<>();
        for (Withdraw withdraw : withdraws) {
            WithdrawDto withdrawDto = new WithdrawDto();
            BeanUtils.copyProperties(withdraw, withdrawDto);
            withdrawDtos.add(withdrawDto);
        }
        return withdrawDtos;
    }

    /**
     * 查询还款金额
     *
     * @param
     * @return
     */
    public List<WithdrawDto> findRepayPlanList() {
        Supplier supplier = (Supplier) ShiroUtils.getSubject().getPrincipal();
        Withdraw w = new Withdraw();
        w.setBillId(supplier.getBillId());
        List<Withdraw> withdraws = withdrawMapper.findWithdrawList(w);
        List<WithdrawDto> withdrawDtos = new ArrayList<>();
        for (Withdraw withdraw : withdraws) {
            WithdrawDto withdrawDto = new WithdrawDto();
            BeanUtils.copyProperties(withdraw, withdrawDto);
            withdrawDtos.add(withdrawDto);
        }
        return withdrawDtos;
    }

    /**
     * 查询提款列表信息
     *
     * @param creditId
     * @return
     */
    public String findWithdrawByNC(String creditId) {
        String result = null;
        try {
            result = ncInteractiveControllor.withdrawPost(creditId);
            JSONObject obj = JSON.parseObject(result);
            if ("success".equals(obj.get("flag"))) {
                Object resultdata = JSON.parseObject(obj.get("body").toString()).get("items");
                String item = JSON.toJSONString(resultdata);
                JSONArray listWithdraw = JSON.parseArray(item);
//                JSONArray listWithdraw = JSON.parseArray(JSON.toJSONString(obj.get("body")));
                for (Object ob : listWithdraw) {
                    Withdraw withdraw = new Withdraw();
                    Map<String, Object> map = JSON.parseObject(ob.toString());
                    withdraw.setBillId(map.get("cashApplyId").toString());
                    if (map.get("collectionAccount") != null) {
                        withdraw.setRepaymentNumber(map.get("collectionAccount").toString());
                    }
                    if (map.get("collectionBank") != null) {
                        withdraw.setRepaymentBank(map.get("collectionBank").toString());
                    }
                    if (map.get("collectionName") != null) {
                        withdraw.setRepaymentName(map.get("collectionName").toString());
                    }
                    if (map.get("paymentAccount") != null) {
                        withdraw.setAccountNumber(map.get("paymentAccount").toString());
                    }
                    if (map.get("paymentBank") != null) {
                        withdraw.setAccountBank(map.get("paymentBank").toString());
                    }
                    if (map.get("paymentName") != null) {
                        withdraw.setAccountName(map.get("paymentName").toString());
                    }
                    withdraw.setStatus(map.get("status").toString());

                    Withdraw w = new Withdraw();
                    w.setBillId(withdraw.getBillId());
                    Withdraw with = withdrawMapper.selectWithdrawList(w).get(0);
                    withdraw.setId(with.getId());
                    withdraw.setUpdateTime(new Date());
                    withdrawMapper.updateWithdraw(withdraw);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 新增提款申请
     *
     * @param withdraw 提款申请信息
     * @return 结果
     */
    @Override
    public int insertWithdraw(Withdraw withdraw) {
        return withdrawMapper.insertWithdraw(withdraw);
    }

    /**
     * 修改提款申请
     *
     * @param withdraw 提款申请信息
     * @return 结果
     */
    @Override
    public int updateWithdraw(Withdraw withdraw) {
        return withdrawMapper.updateWithdraw(withdraw);
    }

    /**
     * 删除提款申请对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWithdrawByIds(String ids) {
        return withdrawMapper.deleteWithdrawByIds(Convert.toStrArray(ids));
    }

    /**
     * 提交提款申请
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> save(@RequestBody Map<String, String> map) {
        String creditBillId = map.get("creditBillId");
        String amount = map.get("amount");
        String buyerCorprName = map.get("buyerCorprName");
        String period = map.get("period");
        String sellerCorprName = map.get("sellerCorprName");
        String withdrawAppPath = map.get("withdrawAppPath");
        String additionFilePath = map.get("additionFilePath");
        String billId = map.get("billId");
        String bankId = map.get("bankId");
        //用于返回
        Map<String, Object> row = new HashMap<String, Object>();

        Withdraw withdraw = new Withdraw();
        Credit cre = creditMapper.selectCreditById(Long.valueOf(creditBillId));
        withdraw.setBillId(UUID.randomUUID().toString().replaceAll("-", ""));
        withdraw.setCreditBillId(cre.getBillId());
        withdraw.setAmount(new BigDecimal(amount));
        withdraw.setBuyerCorprName(buyerCorprName);
        withdraw.setPeriod(Integer.valueOf(period));
        withdraw.setSellerCorprName(sellerCorprName);
        withdraw.setBankId(bankId);
        withdraw.setStatus(WithdrawStatus.AUDITING.getValue());
        withdraw.setWithdrawAppPath(UUID.randomUUID().toString().replaceAll("-", ""));
        withdraw.setAdditionFilePath(UUID.randomUUID().toString().replaceAll("-", ""));
        if (!withdrawAppPath.equals("")) {
            String[] withdrawAppPaths = withdrawAppPath.split(",");
            Integer integer = changeStat(withdrawAppPaths, withdraw, "businessAppForm", "co1");
            if (integer == 0) {
                row.put("insert", 0);
                return row;
            }
        }
        if (!additionFilePath.equals("")) {
            String[] additionFilePaths = additionFilePath.split(",");
            Integer integer = changeStat(additionFilePaths, withdraw, "otherForm", "co7");
            if (integer == 0) {
                row.put("insert", 0);
                return row;
            }
        }
        Credit credit = creditMapper.selectCreditById(new Long(creditBillId));
        credit.setCurrentAmount(credit.getCurrentAmount().subtract(new BigDecimal(amount)));
        int i = withdrawMapper.insertWithdraw(withdraw);
        String info = sendWithdraw(withdraw);
        JSONObject obj = JSON.parseObject(info);
        if (obj.get("flag").equals("failed")) {
            withdrawMapper.deleteWithdrawById(withdraw.getId());
            i = 0;
        } else {
            creditMapper.updateCredit(credit);
        }
        row.put("insert", i);
        return row;
    }

    public Integer changeStat(String[] temp, Withdraw withdraw, String blockName, String cnName) {
        Supplier supplier = (Supplier) ShiroUtils.getSubject().getPrincipal();
        for (int i = 0; i < temp.length; i++) {
            String[] data = temp[i].split("\\:");
            String info = "{";
//            info += "\"code\":\"" + cnName + "\",\"subordinate\":\"credit\",\"userName\":\"" + supplier.getCorprName() + "\",\"filePath\":\"" + data[0] + "\",\"fileFullName\":\"" + data[2] +  "\",\"billId\":\"" + credit.getBillId() + "\",\"sysType\":\"" +"factoring" + "\"}";
//            info += "\"cnName\":\"" + cnName + "\",\"blockName\":\"" + blockName + "\",\"subordinate\":\"withdraw\",\"userName\":\"" + supplier.getCorprName() + "\",\"amount\":\"" + withdraw.getAmount() + "\",\"filePath\":\"" + data[0] + "\",\"fileFullName\":\"" + data[2] + "\",\"fileSize\":\"" + data[1] + "\",\"billId\":\"" + withdraw.getBillId() + "\",\"fileName\":\"" + data[2].split("\\.")[0] + "\"}";
            info += "\"code\":\"" + cnName + "\",\"subordinate\":\"保理提款申请\",\"userName\":\"" + supplier.getCorprName() + "\",\"filePath\":\"" + data[0] + "\",\"fileFullName\":\"" + data[2] + "\",\"billId\":\"" + withdraw.getBillId() + "\",\"sysType\":\"" + "factoring" + "\"}";
            String result = ncInteractiveControllor.sendToImageSystem(info);
            JSONObject date = JSON.parseObject(result);
            if ("failed".equals(date.get("flag"))) {
                return 0;
            }
            if (cnName.equals("co1")) {
                uploadFileService.save(withdraw.getWithdrawAppPath(), data[0]);
            } else {
                uploadFileService.save(withdraw.getAdditionFilePath(), data[0]);
            }
        }
        return 1;
    }

    /**
     * 想核心传输提款申请信息
     *
     * @param withdraw
     * @return
     */
    public String sendWithdraw(Withdraw withdraw) {
        Supplier supplier = new Supplier();
        supplier.setCorprName(withdraw.getSellerCorprName());
        Supplier sup = supplierMapper.selectSupplierList(supplier).get(0);

        Buyer buyer = new Buyer();
        buyer.setBuyerName(withdraw.getBuyerCorprName());
        Buyer buy = buyerMapper.selectBuyerList(buyer).get(0);

        withdraw.setSellerId(sup.getBillId());
        withdraw.setBuyerId(buy.getBuyerId());

        BankCardInfo bankCardInfo = bankCardInfoMapper.selectBankCardInfoById(Long.valueOf(withdraw.getBankId()));

        String s = null;
        try {
            s = ncInteractiveControllor.withdrawPost(withdraw, bankCardInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
