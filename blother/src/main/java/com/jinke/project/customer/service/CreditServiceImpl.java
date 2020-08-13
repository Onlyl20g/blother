package com.jinke.project.customer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jinke.common.utils.security.ShiroUtils;
import com.jinke.common.utils.text.Convert;
import com.jinke.project.customer.domain.Credit;
import com.jinke.project.customer.domain.CreditDto;
import com.jinke.project.customer.mapper.CreditMapper;
import com.jinke.project.system.creditBuyerInfo.domain.CreditBuyerInfo;
import com.jinke.project.system.creditBuyerInfo.mapper.CreditBuyerInfoMapper;
import com.jinke.project.system.fileTemp.domain.FileTemp;
import com.jinke.project.system.fileTemp.domain.FileTempStatus;
import com.jinke.project.system.fileTemp.mapper.FileTempMapper;
import com.jinke.project.system.filepath.domain.Filepath;
import com.jinke.project.system.filepath.mapper.FilepathMapper;
import com.jinke.project.system.ncInteractivity.controller.NCInteractiveControllor;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.supplier.service.SupplierServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 授信申请 服务层实现
 *
 * @author jinke
 * @date 2019-07-17
 */
@Service("ICreditService")
public class CreditServiceImpl implements ICreditService {
    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private CreditBuyerInfoMapper creditBuyerInfoMapper;

    @Autowired
    private FilepathMapper filepathMapper;

    @Autowired
    private FileTempMapper fileTempMapper;

    @Autowired
    private SupplierServiceImpl supplierServiceImpl;

    @Autowired
    private WithdrawServiceImpl withdrawServiceImpl;

    @Autowired
    private NCInteractiveControllor ncInteractiveControllor;

    /**
     * 查询授信申请信息
     *
     * @param id 授信申请ID
     * @return 授信申请信息
     */
    @Override
    public Credit selectCreditById(Long id) {
        return creditMapper.selectCreditById(id);
    }

    /**
     * 查询授信申请列表
     *
     * @param credit 授信申请信息
     * @return 授信申请集合
     */
    @Override
    public List<CreditDto> selectCreditList(Credit credit) {
        List<CreditDto> creditDtos = new ArrayList<>();
        List<Credit> credits = creditMapper.selectCreditList(credit);
        for (Credit credit1 : credits) {
            CreditDto creditDto = new CreditDto();
            BeanUtils.copyProperties(credit1, creditDto);

            creditDtos.add(creditDto);
        }
        return creditDtos;
    }

    /**
     * 通过id查询授信申请列表
     *
     * @param supplierBillId
     * @return
     */
    @Override
    public List<CreditDto> selectCreditListByUsername(String supplierBillId) {
        Credit credit = new Credit();
        credit.setSupplierBillId(supplierBillId);
        findCreditInfo(supplierBillId);
        List<Credit> credits = creditMapper.selectCreditList(credit);
        List<CreditDto> list = new ArrayList();
        for (Credit credit1 : credits) {
            CreditDto creditDto = new CreditDto();
            BeanUtils.copyProperties(credit1, creditDto);
            if(credit1.getCustManagerName() ==null || "".equals(credit1.getCustManagerName())){
                Supplier supplier = new Supplier();
                supplier.setBillId(credit1.getSupplierBillId());
                Supplier supplier1 = supplierServiceImpl.selectSupplierList(supplier).get(0);//查找客户经理id
                creditDto.setCustManagerName(supplier1.getCustomerManagerName());
            }
            list.add(creditDto);
        }
        return list;
    }

    /**
     * 通过id查询授信申请列表
     *
     * @param billId
     * @return
     */
    @Override
    public List<CreditDto> selectSuccessCreditListByUsername(String billId) {
        Credit credit = new Credit();
        credit.setSupplierBillId(billId);
        List<Credit> list = creditMapper.selectCreditList(credit);
        credit.setCreditStatus("3");
        List<Credit> credits = creditMapper.selectCreditList(credit);

        List<CreditDto> creditDtos = new ArrayList<>();
        for (Credit credit1 : credits) {
            CreditDto creditDto = new CreditDto();
            BeanUtils.copyProperties(credit1, creditDto);
            if(credit1.getCustManagerName() ==null || "".equals(credit1.getCustManagerName())){
                Supplier supplier = new Supplier();
                supplier.setBillId(credit1.getSupplierBillId());
                Supplier supplier1 = supplierServiceImpl.selectSupplierList(supplier).get(0);//查找客户经理id
                creditDto.setCustManagerName(supplier1.getCustomerManagerName());
            }
            creditDtos.add(creditDto);
        }
        return creditDtos;
    }

    /**
     * 查询核心授信信息
     *
     * @param supplierBillId
     */
    public void findCreditInfo(String supplierBillId) {
        String date = null;
        try {
            date = ncInteractiveControllor.creditHttpPost(supplierBillId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (date != null) {
            JSONObject obj = JSON.parseObject(date);
            if (obj.get("flag").equals("success")) {
                Object o = JSON.parseObject(obj.get("body").toString()).get("items");
                String item = JSON.toJSONString(o);
                JSONArray listItem = JSON.parseArray(item);
                List<Credit> list = new ArrayList<Credit>();
                for (Object ob : listItem) {
                    Credit credit = new Credit();
                    Map map = JSON.parseObject(ob.toString());
                    credit.setBillId(map.get("creditApplyId") == null ? "nullValue" : map.get("creditApplyId").toString());
                    if (!map.get("status").equals("deleted")) {
                        credit.setCreditAmount(new BigDecimal(map.get("amount").toString()));
                        String activeAmount = map.get("activeAmount").toString();
                        if (activeAmount.length() > 0) {
                            credit.setCurrentAmount(new BigDecimal(map.get("activeAmount").toString()));
                        }
                        credit.setCreditPeriod(new Integer(map.get("period").toString()));
                        credit.setCreditType(map.get("type").toString());
                        if (map.get("status").equals("auditing")) {//审核中
                            credit.setCreditStatus("0");
                        } else if (map.get("status").equals("audited")) {//通过
                            credit.setCreditStatus("3");
                        } else if (map.get("status").equals("rejection")) {//拒绝
                            credit.setCreditStatus("2");
                        } else if (map.get("status").equals("expired")) {//过期
                            credit.setCreditStatus("4");
                        } else {
                            credit.setCreditStatus("1");//未通过
                        }
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date d = null;
                        try {
                            if (map.get("approveTime") == null || "".equals(map.get("approveTime").toString())) {
                                d = new Date();
                            } else {
                                d = formatter.parse(map.get("approveTime").toString());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Credit c = new Credit();
                        credit.setApproveTime(d);
                        c.setBillId(credit.getBillId());
                        List<Credit> credits = creditMapper.selectCreditList(c);
                        if (credits.size() > 0) {
                            credit.setId(credits.get(0).getId());
                            creditMapper.updateCredit(credit);
                            list.add(credit);
                        }
                    } else {

                        List<Credit> credits = creditMapper.selectCreditList(credit);
                        credit.setCreditStatus("5");
                        if (credits.size() > 0) {
                            credit.setId(credits.get(0).getId());
                            creditMapper.updateCredit(credit);
                            list.add(credit);
                        }
                    }
                }
//                暂时预留,后面添加功能
//                creditMapper.updateCreditList(list);
            }
        }
    }

    /**
     * 新增授信申请
     *
     * @param credit 授信申请信息
     * @return 结果
     */
    @Override
    public int insertCredit(Credit credit) {
        return creditMapper.insertCredit(credit);
    }

    /**
     * 修改授信申请
     *
     * @param credit 授信申请信息
     * @return 结果
     */
    @Override
    public int updateCredit(Credit credit) {
        return creditMapper.updateCredit(credit);
    }

    /**
     * 删除授信申请对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCreditByIds(String ids) {
        return creditMapper.deleteCreditByIds(Convert.toStrArray(ids));
    }

    /**
     * 保存授信申请
     *
     * @param
     * @return success>0
     */
    @Override
    public Map<String, Object> save(@RequestBody Map<String, String> map) {
        Map<String, Object> row = new HashMap<String, Object>();
        Credit credit = new Credit();
        Supplier sup = (Supplier) SecurityUtils.getSubject().getPrincipal();
        String businessLicenseCode = map.get("businessLicenseCode");
        credit.setSupplierBillId(sup.getBillId());
        credit.setBillId(UUID.randomUUID().toString().replaceAll("-", ""));
        credit.setAppCreditAmount(new BigDecimal(Optional.ofNullable(map.get("appCreditAmount")).get().replaceAll(",", "")));
        credit.setAppCreditPeriod(new Integer(Optional.ofNullable(map.get("appCreditLife")).get()));
        credit.setSellerCorprName(Optional.ofNullable(map.get("sellerCorporationName")).get());
        credit.setSellerContactName(Optional.ofNullable(map.get("sellerPerson")).get());
        credit.setSellerContactTel(Optional.ofNullable(map.get("sellerPersonTel")).get());
        credit.setFactorType(Optional.ofNullable(map.get("factorType")).get());
        credit.setGuaranteeMode(Optional.ofNullable(map.get("guaranteeMode")).get());
        credit.setCustManagerId(Optional.ofNullable(map.get("customerManagerId")).get());
        credit.setCustManagerName(Optional.ofNullable(map.get("customerManagerName")).get());
        credit.setStr2(Optional.ofNullable(map.get("customerManagerName")).get());
        credit.setCreditStatus("0");
        credit.setCreateTime(new Date());

        FileTemp fileTemp = new FileTemp();
        Filepath filepath = new Filepath();
        filepath.setCreateTime(new Date());
        Boolean resultBoolean = true;
        String[] temp = map.get("businessAppForm").split(",");//获取到多个文件url
        if (temp != null && !"".equals(temp)) {
            credit.setBusinessAppPath(UUID.randomUUID().toString().replaceAll("-", ""));
            filepath.setFilepathId(credit.getBusinessAppPath());
            Integer integer = changeStat(temp, credit, fileTemp, filepath, "businessAppForm", "co1");
            if (integer == 0) {
                resultBoolean = false;
            }
        }
        temp = map.get("bylawForm").split(",");
        if (temp != null && !"".equals(temp)) {
            credit.setBylawPath(UUID.randomUUID().toString().replaceAll("-", ""));
            filepath.setFilepathId(credit.getBylawPath());
            Integer integer = changeStat(temp, credit, fileTemp, filepath, "bylawForm", "co2");
            if (integer == 0) {
                resultBoolean = false;
            }
        }
        temp = map.get("corporationForm").split(",");
        if (temp != null && !"".equals(temp)) {
            credit.setCorprPath(UUID.randomUUID().toString().replaceAll("-", ""));
            filepath.setFilepathId(credit.getCorprPath());
            Integer integer = changeStat(temp, credit, fileTemp, filepath, "corporationForm", "co3");
            if (integer == 0) {
                resultBoolean = false;
            }
        }
        temp = map.get("financePrincipalForm").split(",");
        if (temp != null && !"".equals(temp)) {
            credit.setFinancePrincipalPath(UUID.randomUUID().toString().replaceAll("-", ""));
            filepath.setFilepathId(credit.getFinancePrincipalPath());
            Integer integer = changeStat(temp, credit, fileTemp, filepath, "financePrincipalForm", "co4");
            if (integer == 0) {
                resultBoolean = false;
            }
        }
        temp = map.get("actualControllerForm").split(",");
        if (temp != null && !"".equals(temp)) {
            credit.setActualControllerPath(UUID.randomUUID().toString().replaceAll("-", ""));
            filepath.setFilepathId(credit.getActualControllerPath());
            Integer integer = changeStat(temp, credit, fileTemp, filepath, "actualControllerForm", "co5");
            if (integer == 0) {
                resultBoolean = false;
            }
        }
        temp = map.get("fundamentalStateForm").split(",");
        if (temp != null && !"".equals(temp) && temp.length > 0) {
            credit.setFundamentalStatePath(UUID.randomUUID().toString().replaceAll("-", ""));
            filepath.setFilepathId(credit.getFundamentalStatePath());
            Integer integer = changeStat(temp, credit, fileTemp, filepath, "fundamentalStateForm", "co6");
            if (integer == 0) {
                resultBoolean = false;
            }
        }
        temp = map.get("otherForm").split(",");
        if (temp != null && !"".equals(temp[0])) {
            credit.setAdditionFilePath(UUID.randomUUID().toString().replaceAll("-", ""));
            filepath.setFilepathId(credit.getAdditionFilePath());
            Integer integer = changeStat(temp, credit, fileTemp, filepath, "otherForm", "co7");
            if (integer == 0) {
                resultBoolean = false;
            }
        }

        Filepath filepath1 = new Filepath();
        filepath1.setFilepathId(sup.getBusinessLicenseFilepathId());
        List<Filepath> filepaths = filepathMapper.selectFilepathList(filepath1);
        if (filepaths.size() > 0) {
            Integer integer = addSupplierFile(filepaths.get(0).getUrlBillid(), credit, "营业执照." + filepaths.get(0).getFormName().split("\\.")[1], "co8");
            if (integer == 0) {
                resultBoolean = false;
            }
        }
        filepath1.setFilepathId(sup.getIdcardBackFilepathId());
        List<Filepath> filepaths1 = filepathMapper.selectFilepathList(filepath1);
        if (filepaths1.size() > 0) {
            Integer integer = addSupplierFile(filepaths1.get(0).getUrlBillid(), credit, "身份证人像面." + filepaths.get(0).getFormName().split("\\.")[1], "co10");
            if (integer == 0) {
                resultBoolean = false;
            }
        }

        filepath1.setFilepathId(sup.getIdcardFrontFilepathId());
        List<Filepath> filepaths2 = filepathMapper.selectFilepathList(filepath1);
        if (filepaths1.size() > 0) {
            Integer integer = addSupplierFile(filepaths2.get(0).getUrlBillid(), credit, "身份证国徽面." + filepaths.get(0).getFormName().split("\\.")[1], "co9");
            if (integer == 0) {
                resultBoolean = false;
            }
        }

        int i = creditMapper.insertCredit(credit);
        Credit credit1 = new Credit();
        if (i > 0) {
            credit1.setBillId(credit.getBillId());
            credit1 = creditMapper.selectCreditList(credit1).get(0);
            row.put("id", credit1.getId());
        }
        row.put("bill", credit.getSupplierBillId());
        String msg = (i > 0 && resultBoolean) ? "1" : "0";
        if ("0".equals(msg)) {
            creditMapper.deleteCreditById(credit1.getId());
        }
        row.put("status", msg);
        return row;
    }

    public Integer addSupplierFile(String temp, Credit credit, String fullName, String cnName) {
        Supplier supplier = (Supplier) ShiroUtils.getSubject().getPrincipal();
        String info = "{";
        info += "\"code\":\"" + cnName + "\",\"subordinate\":\"保理授信申请\",\"userName\":\"" + supplier.getCorprName() + "\",\"filePath\":\"" + temp + "\",\"fileFullName\":\"" + fullName + "\",\"billId\":\"" + credit.getBillId() + "\",\"sysType\":\"" + "factoring" + "\"}";
        String result = ncInteractiveControllor.sendToImageSystem(info);
        JSONObject date = JSON.parseObject(result);
        if ("failed".equals(date.get("flag"))) {
            return 0;
        }
        return 1;
    }

    public Integer changeStat(String[] temp, Credit credit, FileTemp fileTemp, Filepath filepath, String blockName, String cnName) {
        Supplier supplier = (Supplier) ShiroUtils.getSubject().getPrincipal();
        String[] url = new String[temp.length];
        for (int i = 0; i < temp.length; i++) {
            String[] data = temp[i].split("\\:");
            url[i] = data[0];
            String info = "{";
//            info += "\"cnName\":\"" + cnName + "\",\"blockName\":\"" + blockName + "\",\"amount\":\"" + credit.getAppCreditAmount().intValue() + "\",\"subordinate\":\"credit\",\"userName\":\"" + supplier.getCorprName() + "\",\"filePath\":\"" + data[0] + "\",\"fileFullName\":\"" + data[2] + "\",\"fileSize\":\"" + data[1] + "\",\"billId\":\"" + credit.getBillId() + "\",\"fileName\":\"" + data[2].split("\\.")[0] + "\"}";
            info += "\"code\":\"" + cnName + "\",\"subordinate\":\"保理授信申请\",\"userName\":\"" + supplier.getCorprName() + "\",\"filePath\":\"" + data[0] + "\",\"fileFullName\":\"" + data[2] + "\",\"billId\":\"" + credit.getBillId() + "\",\"sysType\":\"" + "factoring" + "\"}";
            String result = ncInteractiveControllor.sendToImageSystem(info);
            JSONObject date = JSON.parseObject(result);
            if ("failed".equals(date.get("flag"))) {
                return 0;
            }
        }
        if (temp.length > 0) {
            insertAll(url, fileTemp, filepath);
        }
        return 1;
    }


    public Integer tranpInfo(String[] temp, Credit credit, FileTemp fileTemp, Filepath filepath, String blockName, String cnName) {
        Supplier supplier = (Supplier) ShiroUtils.getSubject().getPrincipal();
        String[] url = new String[3];
        for (int i = 0; i < temp.length; i++) {
            String[] data = temp[i].split("\\:");
            url[i] = data[0];
            String info = "{";
//            info += "\"cnName\":\"" + cnName + "\",\"blockName\":\"" + blockName + "\",\"amount\":\"" + credit.getAppCreditAmount().intValue() + "\",\"subordinate\":\"credit\",\"userName\":\"" + supplier.getCorprName() + "\",\"filePath\":\"" + data[0] + "\",\"fileFullName\":\"" + data[2] + "\",\"fileSize\":\"" + data[1] + "\",\"billId\":\"" + credit.getBillId() + "\",\"fileName\":\"" + data[2].split("\\.")[0] + "\"}";
            info += "\"code\":\"" + cnName + "\",\"subordinate\":\"保理授信申请\",\"userName\":\"" + supplier.getCorprName() + "\",\"filePath\":\"" + data[0] + "\",\"fileFullName\":\"" + data[2] + "\",\"billId\":\"" + credit.getBillId() + "\",\"sysType\":\"" + "factoring" + "\"}";
            String result = ncInteractiveControllor.sendToImageSystem(info);
            JSONObject date = JSON.parseObject(result);
            if ("failed".equals(date.get("flag"))) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * 用于修改文件列表中对应的文件状态
     *
     * @param strings
     * @param fileTemp
     * @param filepath
     */
    public void insertAll(String[] strings, FileTemp fileTemp, Filepath filepath) {
        if (strings != null) {
            List<FileTemp> fileTemps = null;
            for (String s : strings) {
                fileTemp.setUrl(s);
                fileTemps = fileTempMapper.selectFileTempList(fileTemp);
                if (fileTemps.size() > 0) {
                    FileTemp fileTemp1 = fileTemps.get(0);
                    filepath.setFormName(fileTemp1.getName());
                    filepath.setUrlBillid(fileTemp.getUrl());
                    filepathMapper.insertFilepath(filepath);
                    fileTemp1.setStatus(FileTempStatus.EFF.getValue());
                    fileTempMapper.updateFileTemp(fileTemp1);
                }
            }
        }
    }

    /**
     * 保存授信申请附表
     *
     * @param list
     * @return
     */
    @Override
    public Map<String, Object> saveCredits(List<Map<String, String>> list) {
        Map<String, Object> row = new HashMap<String, Object>();
        String result = ncTransmit(list);
        if ("failed".equals(result)) {
            creditMapper.deleteCreditById(Long.valueOf(list.get(0).get("billId")));
            row.put("msg", "err");
            return row;
        } else {
            for (int i = 0; i < list.size(); i++) {
                CreditBuyerInfo creditBuyerInfo = new CreditBuyerInfo();
                creditBuyerInfo.setBuyerCorprName(list.get(i).get("buyerCorprName"));
                creditBuyerInfo.setBuyerContactName(list.get(i).get("buyerContactName"));
                creditBuyerInfo.setBuyerContactTel(list.get(i).get("buyerContactTel"));
                creditBuyerInfo.setCreditId(Long.valueOf(list.get(i).get("billId")));
                int i1 = creditBuyerInfoMapper.insertCreditBuyerInfo(creditBuyerInfo);
                row.put(i + "", i1);
            }
        }
        if (row.size() == list.size()) {
            row.put("msg", "suc");
        } else {
            row.put("msg", "err");
        }
        return row;
    }

    /**
     * 向核心传输数据
     *
     * @param list
     */
    public String ncTransmit(List<Map<String, String>> list) {
        if (list.size() > 0) {
            List<Map> buyerInfoList = new ArrayList<Map>();
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();
                map.put("buyerName", list.get(i).get("buyerCorprName"));
                map.put("buyerContactName", list.get(i).get("buyerContactName") == null ? "" : list.get(i).get("buyerContactName"));
                map.put("buyerContactTel", list.get(i).get("buyerContactTel") == null ? "" : list.get(i).get("buyerContactTel"));
                buyerInfoList.add(map);
            }
            Credit credit = creditMapper.selectCreditById(Long.valueOf(list.get(0).get("billId")));
            /*Supplier s = new Supplier();
            s.setBillId(credit.getSupplierBillId());
            Supplier supplier = supplierServiceImpl.selectSupplierList(s).get(0);//查找客户经理id*/
//            credit.setCustManagerId(supplier.getCustomerManagerId());
//            credit.setCustManagerId(credit.getStr1());
            credit.setBuyerInfoList(buyerInfoList);
            String info = null;
            try {
                info = ncInteractiveControllor.creditHttpPost(credit);
            } catch (IOException e) {
                info = "{\"flag\": \"failed\"}";
            }

            JSONObject obj = JSON.parseObject(info);
            return obj.get("flag").toString();
        }
        return null;
    }

    /**
     * 资料补交
     *
     * @param
     * @return
     */
    @Override
    public int saveEdit(@RequestBody Map<String, String> map) {
        //先通过billId查出对应的申请
        Credit credit = new Credit();
        FileTemp fileTemp = new FileTemp();
        Filepath filepath = new Filepath();
        String[] businessAppForms = null;
        //获取到对应的授信申请
        credit.setBillId(map.get("billId"));
        List<Credit> credits = creditMapper.selectCreditList(credit);
        if (credits.size() > 0) {
            credit = credits.get(0);
        }
        //获取上传文件的Url
        Boolean resultBoolean = true;
        String businessAppForm = map.get("businessAppForm");
        if (!(businessAppForm == null || businessAppForm.equals(""))) {//如果没传就跳过
            if (credit.getBusinessAppPath() == null || credit.getBusinessAppPath().equals("")) {    //如果没有uuid就生成一个填入（在第一次授信申请时，有部分可以不传，如其他补充资料）
                credit.setBusinessAppPath(UUID.randomUUID().toString().replaceAll("-", ""));//生成一个去掉‘-’的uuid
            }
            Integer integer = tranpInfo(businessAppForm.split(","), credit, fileTemp, filepath, "businessAppForm", "co1");
            saveEditFile(businessAppForm, credit.getBusinessAppPath(), fileTemp, filepath);
            if (integer == 0) {
                resultBoolean = false;
            }
//            transferNC(businessAppForm, credit);
        }

        String fundamentalStateForm = map.get("fundamentalStateForm");
        if (!(fundamentalStateForm == null || fundamentalStateForm.equals(""))) {
            if (credit.getFundamentalStatePath() == null || credit.getFundamentalStatePath().equals("")) {
                credit.setFundamentalStatePath(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            Integer integer = tranpInfo(fundamentalStateForm.split(","), credit, fileTemp, filepath, "fundamentalStateForm", "co6");
            saveEditFile(fundamentalStateForm, credit.getFundamentalStatePath(), fileTemp, filepath);
            if (integer == 0) {
                resultBoolean = false;
            }
        }

        String bylawForm = map.get("bylawForm");
        if (!(bylawForm == null || bylawForm.equals(""))) {
            if (credit.getBylawPath() == null || credit.getBylawPath().equals("")) {
                credit.setBylawPath(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            Integer integer = tranpInfo(bylawForm.split(","), credit, fileTemp, filepath, "bylawForm", "co2");
            saveEditFile(bylawForm, credit.getBylawPath(), fileTemp, filepath);
            if (integer == 0) {
                resultBoolean = false;
            }
        }

        String corporationForm = map.get("corporationForm");
        if (!(corporationForm == null || corporationForm.equals(""))) {
            if (credit.getCorprPath() == null || credit.getCorprPath().equals("")) {
                credit.setCorprPath(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            Integer integer = tranpInfo(corporationForm.split(","), credit, fileTemp, filepath, "corporationForm", "co3");
            saveEditFile(corporationForm, credit.getCorprPath(), fileTemp, filepath);
            if (integer == 0) {
                resultBoolean = false;
            }
        }

        String financePrincipalForm = map.get("financePrincipalForm");
        if (!(financePrincipalForm == null || financePrincipalForm.equals(""))) {
            if (credit.getFinancePrincipalPath() == null || credit.getFinancePrincipalPath().equals("")) {
                credit.setFinancePrincipalPath(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            Integer integer = tranpInfo(financePrincipalForm.split(","), credit, fileTemp, filepath, "financePrincipalForm", "co4");
            saveEditFile(financePrincipalForm, credit.getFinancePrincipalPath(), fileTemp, filepath);
            if (integer == 0) {
                resultBoolean = false;
            }
        }

        String actualControllerForm = map.get("actualControllerForm");
        if (!(actualControllerForm == null || actualControllerForm.equals(""))) {
            if (credit.getActualControllerPath() == null || credit.getActualControllerPath().equals("")) {
                credit.setActualControllerPath(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            Integer integer = tranpInfo(actualControllerForm.split(","), credit, fileTemp, filepath, "actualControllerForm", "co5");
            saveEditFile(actualControllerForm, credit.getActualControllerPath(), fileTemp, filepath);
            if (integer == 0) {
                resultBoolean = false;
            }
        }

        String additionFilePath = map.get("otherForm");
        if (!(additionFilePath == null || additionFilePath.equals(""))) {
            if (credit.getAdditionFilePath() == null || credit.getAdditionFilePath().equals("")) {
                credit.setAdditionFilePath(UUID.randomUUID().toString().replaceAll("-", ""));
            }
            Integer integer = tranpInfo(additionFilePath.split(","), credit, fileTemp, filepath, "otherForm", "co7");
            saveEditFile(additionFilePath, credit.getAdditionFilePath(), fileTemp, filepath);
            if (integer == 0) {
                resultBoolean = false;
            }
        }
        //将授信状态改为审核中
        Credit credit1 = credit;
        credit1.setCreditStatus("0");
        credit1.setUpdateTime(new Date());

        //向核心发送请求
        String info = ncInteractiveControllor.payCreditHttpPost(credit1.getBillId(), credit1.getSupplierBillId());
        JSONObject obj = JSON.parseObject(info);
        int updateResult = 0;
        if ("success".equals(obj.get("flag")) && resultBoolean) {//判断是否发送成功，成功着保存到本地
            updateResult = creditMapper.updateCreditByBillId(credit1);
        }
        return updateResult;
    }

    /**
     * 用于保存新上传的授信文件资料
     *
     * @param urlStr
     * @param uuid
     * @param fileTemp
     * @param filepath
     */
    public void saveEditFile(String urlStr, String uuid, FileTemp fileTemp, Filepath filepath) {
        if (!(urlStr == null || urlStr.equals(""))) {
            String[] urls = urlStr.split(",");
            if (urls != null) {
                for (String s : urls) {
                    String[] data = s.split("\\:");
                    fileTemp.setUrl(data[0]);
                    fileTemp = fileTempMapper.selectFileTempByUrl(data[0]);
                    if (fileTemp.getId() != null && !fileTemp.getId().equals("")) {
                        fileTemp.setStatus(FileTempStatus.EFF.getValue());// 状态改为有效
                        fileTempMapper.updateFileTemp(fileTemp);
                        filepath.setUrlBillid(data[0]);
                        filepath.setFormName(fileTemp.getName());
                        filepath.setFilepathId(uuid);
                        filepath.setCreateTime(new Date());
                        filepathMapper.insertFilepath(filepath);
                    }
                }
            }
        }
    }

    /**
     * @param credit 授信申请通过
     * @return
     */
    @Override
    public List<Credit> selectCreditSucList(Credit credit) {
        Credit temp = new Credit();
        temp.setCreditStatus("3");
        temp.setApproveTime(new Date());
        return creditMapper.selectCreditList(temp);
    }
}
