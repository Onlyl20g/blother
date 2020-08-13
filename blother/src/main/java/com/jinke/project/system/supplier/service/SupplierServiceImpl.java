package com.jinke.project.system.supplier.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinke.common.utils.Threads;
import com.jinke.common.utils.text.Convert;
import com.jinke.project.system.enterprise.domain.Enterprise;
import com.jinke.project.system.enterprise.mapper.EnterpriseMapper;
import com.jinke.project.system.enterprise.service.IEnterpriseService;
import com.jinke.project.system.fileTemp.domain.FileTemp;
import com.jinke.project.system.fileTemp.mapper.FileTempMapper;
import com.jinke.project.system.ncInteractivity.controller.NCInteractiveControllor;
import com.jinke.project.system.supplier.domain.Supplier;
import com.jinke.project.system.supplier.domain.SupplierDto;
import com.jinke.project.system.supplier.mapper.SupplierMapper;
import com.jinke.project.system.supplierIdcardinfo.domain.SupplierIdcardinfo;
import com.jinke.project.system.supplierIdcardinfo.service.ISupplierIdcardinfoService;
import com.jinke.project.system.upload.service.UploadFileService;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.domain.UserRole;
import com.jinke.project.system.user.mapper.UserMapper;
import com.jinke.project.system.user.mapper.UserRoleMapper;
import com.jinke.project.system.user.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 供应商 服务层实现
 *
 * @author jinke
 */
@Service
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IEnterpriseService iEnterpriseService;

    @Autowired
    private ISupplierIdcardinfoService iSupplierIdcardinfoService;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private NCInteractiveControllor ncInteractiveControllor;

    @Autowired
    private FileTempMapper fileTempMapper;

    private static final Logger logger = LoggerFactory.getLogger(Threads.class);

    /**
     * 查询供应商信息
     *
     * @param id 供应商ID
     * @return 供应商信息
     */
    @Override
    public Supplier selectSupplierById(Long id) {
        return supplierMapper.selectSupplierById(id);
    }

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商信息
     * @return 供应商集合
     */
    @Override
    public List<Supplier> selectSupplierList(Supplier supplier) {
        return supplierMapper.selectSupplierList(supplier);
    }

    public Supplier selectSupplierByUserName(String username) {
        return supplierMapper.selectSupplierByUserName(username);
    }


    /**
     * 查询供应商列表详细信息
     *
     * @param supplier 供应商信息
     * @return 供应商集合
     */
    @Override
    public List<SupplierDto> selectSupplierListInfo(Supplier supplier) {
        User user = userMapper.findUserId(supplier.getCustomerManagerName());
        UserRole userRole = userRoleMapper.findUserRole(user.getUserId());
        if (userRole != null) {
            Supplier supplier2 = new Supplier();
            supplier2.setBusinessLicenseCode(supplier.getBusinessLicenseCode());
            supplier2.setStatus(supplier.getStatus());
            supplier2.setParams(supplier.getParams());
            List<Supplier> suppliers = supplierMapper.selectSupplierListInfo(supplier2);
            List<SupplierDto> supplierDtos = new ArrayList<>();
            for (Supplier supplier1 : suppliers) {
                SupplierDto supplierDto = new SupplierDto();
                BeanUtils.copyProperties(supplier1, supplierDto);
                supplierDtos.add(supplierDto);
            }
            return supplierDtos;
        } else {
            supplier.setCustomerManagerId(user.getBillId());
            List<Supplier> sup = supplierMapper.selectSupplierListInfo(supplier);
            List<SupplierDto> supplierDtos = new ArrayList<>();
            for (Supplier supplier1 : sup) {
                SupplierDto supplierDto = new SupplierDto();
                BeanUtils.copyProperties(supplier1, supplierDto);
                supplierDtos.add(supplierDto);
            }
            return supplierDtos;
        }
    }

    /**
     * 新增供应商
     *
     * @param supplier 供应商信息
     * @return 结果
     */
    @Override
    public int insertSupplier(Supplier supplier) {
        return supplierMapper.insertSupplier(supplier);
    }

    /**
     * 修改供应商
     *
     * @param supplier 供应商信息
     * @return 结果
     */
    @Override
    public int updateSupplier(Supplier supplier) {

        Enterprise enterprise = new Enterprise();
        enterprise.setEnterpriseId(supplier.getBusinessLicenseCode());

        Enterprise en = new Enterprise();
        List<Enterprise> enterprises = iEnterpriseService.selectEnterpriseList(enterprise);
        if (enterprises.size() > 0) {
            en = enterprises.get(0);
        }
        supplier.setStatus("1");
        Supplier sup = this.selectSupplierList(supplier).get(0);//查询供应商所有信息
        supplier.setStatus("0");
        String msg = null;//调用核心接口传输供应商数据
        try {
            msg = ncInteractiveControllor.supplierHttpPost(sup, en);
        } catch (Exception e) {
            return 0;
        }
        JSONObject objmsg = JSON.parseObject(msg);
        if ("success".equals(objmsg.get("flag"))) {//判断是否传输成功
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            supplier.setUpdateBy(user.getBillId());
            return supplierMapper.updateSupplier(supplier);//修改状态
        } else {
            return 0;
        }
    }

    /**
     * 删除供应商对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSupplierByIds(String ids) {
        String[] idss = Convert.toStrArray(ids);
        return supplierMapper.updateSupplierByIds(idss);
    }


    /**
     * 供应商注册
     */
    public String register(@RequestBody Map<String, String> map) {
        String businessLicensePath = map.get("businessLicensePath");
        String companyname = map.get("companyname");
        String code = map.get("code");
        String corprType = map.get("corprType");
        String corprAddres = map.get("corprAddres");
        String customerManagerName = map.get("customerManagerName");
        String idcardFrontPath = map.get("idcardFrontPath");
        String idcardBackPath = map.get("idcardBackPath");
        String legalPersonName = map.get("legalPersonName");
        String legalPersonIdno = map.get("legalPersonIdno");
        String legalPersonPhone = map.get("legalPersonPhone");
        String customerManagerId = map.get("customerManagerId");
        Supplier supplier = new Supplier();

        SupplierIdcardinfo supplierIdcardinfo = new SupplierIdcardinfo();
        supplierIdcardinfo.setAddress(map.get("address"));
        supplierIdcardinfo.setBirth(map.get("birth"));
        supplierIdcardinfo.setName(map.get("legalPersonName"));
        supplierIdcardinfo.setNationality(map.get("nationality"));
        supplierIdcardinfo.setSex(map.get("sex"));
        supplierIdcardinfo.setRequestId(map.get("requestId"));
        supplierIdcardinfo.setNum(map.get("legalPersonIdno"));

        supplier.setCorprName(companyname);
        supplier.setBusinessLicenseCode(code);
        supplier.setSalt(randomSalt());
        supplier.setPassword(encryptPassword(supplier.getBusinessLicenseCode(), "", supplier.getSalt()));
        supplier.setStatus("1");//需要审核成功后改为0
        supplier.setCreateTime(new Date());
        supplier.setEstablishDate(map.get("enterpriseTime"));
        supplier.setRegisteredCapital(map.get("enterpriseCapital"));
        supplier.setOperationPeriod(map.get("validDateEnd"));
        supplier.setBusinessLicenseFilepathId(UUID.randomUUID().toString().replaceAll("-", ""));
        supplier.setCorprType(corprType);
        supplier.setCustomerManagerName(customerManagerName);
        supplier.setIdcardFrontFilepathId(UUID.randomUUID().toString().replaceAll("-", ""));
        supplier.setIdcardBackFilepathId(UUID.randomUUID().toString().replaceAll("-", ""));
        supplier.setCorprAddres(corprAddres);
        supplier.setCustomerManagerId(customerManagerId);
        supplier.setLegalPersonIdno(legalPersonIdno);
        supplier.setLegalPersonName(legalPersonName);
        supplier.setLegalPersonPhone(legalPersonPhone);
        supplier.setBillId(UUID.randomUUID().toString().replaceAll("-", ""));
        supplier.setDelFlag("0");
        Supplier suppliers = supplierMapper.selectSupplierByUserName(code);
        int smsg = 0;

        if (suppliers != null) {
            if ("1".equals(suppliers.getDelFlag())) {
                supplier.setId(suppliers.getId());
                smsg = supplierMapper.updateSupplier(supplier);
            } else {
                return "0";
            }
        } else {
            try {
                updateEnterprise(map);//修改或添加营业执照信息
                smsg = supplierMapper.insertSupplier(supplier);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        uploadFileService.save(supplier.getBusinessLicenseFilepathId(), businessLicensePath);
        uploadFileService.save(supplier.getIdcardFrontFilepathId(), idcardFrontPath);
        uploadFileService.save(supplier.getIdcardBackFilepathId(), idcardBackPath);

        Integer business = sendFileToImageSys("co8", companyname, businessLicensePath, supplier.getBillId());
        if (business == 0) {
            return "";
        }
        Integer idcardFront = sendFileToImageSys("co9", companyname, idcardFrontPath, supplier.getBillId());
        if (idcardFront == 0) {
            return "";
        }
        Integer idcardBack = sendFileToImageSys("co10", companyname, idcardBackPath, supplier.getBillId());
        if (idcardBack == 0) {
            return "";
        }

        if (smsg > 0) {
            supplierIdcardinfo.setBillId(UUID.randomUUID().toString().replaceAll("-", ""));
            supplierIdcardinfo.setSupplierBillId(supplier.getBillId());
            supplierIdcardinfo.setBirth(dateFormat(supplierIdcardinfo.getBirth()));
            iSupplierIdcardinfoService.insertSupplierIdcardinfo(supplierIdcardinfo);
            return JSON.toJSONString(smsg);
        } else {
            return "";
        }
    }

    /*public void updateSupplierIdcardinfo(Map<String, String> map){
        SupplierIdcardinfo supplierIdcardinfo = new SupplierIdcardinfo();
        supplierIdcardinfo.
    }*/


    public Integer sendFileToImageSys(String cnName, String corprName, String path, String billId) {
        FileTemp fileTempid = new FileTemp();
        fileTempid.setUrl(path);
        List<FileTemp> fileTemps = fileTempMapper.selectFileTempList(fileTempid);
        String type = fileTemps.get(0).getName().split("\\.")[1];
        String info = "{";
//        info += "\"code\":\"" + cnName + "\",\"subordinate\":\"保理供应商注册\",\"userName\":\"" + corprName + "\",\"filePath\":\"" + path + "\",\"fileFullName\":\"" + fileTemps.get(0).getName() + "\",\"billId\":\"" + billId + "\",\"sysType\":\"" + "factoring" + "\"}";
        if ("co8".equals(cnName)) {
            info += "\"code\":\"" + cnName + "\",\"subordinate\":\"保理供应商注册\",\"userName\":\"" + corprName + "\",\"filePath\":\"" + path + "\",\"fileFullName\":\"" + "营业执照." + type + "\",\"billId\":\"" + billId + "\",\"sysType\":\"" + "factoring" + "\"}";
        } else if ("co9".equals(cnName)) {
            info += "\"code\":\"" + cnName + "\",\"subordinate\":\"保理供应商注册\",\"userName\":\"" + corprName + "\",\"filePath\":\"" + path + "\",\"fileFullName\":\"" + "身份证国徽面." + type + "\",\"billId\":\"" + billId + "\",\"sysType\":\"" + "factoring" + "\"}";
        } else {
            info += "\"code\":\"" + cnName + "\",\"subordinate\":\"保理供应商注册\",\"userName\":\"" + corprName + "\",\"filePath\":\"" + path + "\",\"fileFullName\":\"" + "身份证人像面." + type + "\",\"billId\":\"" + billId + "\",\"sysType\":\"" + "factoring" + "\"}";
        }
        String result = ncInteractiveControllor.sendToImageSystem(info);
        JSONObject date = JSON.parseObject(result);
        if ("failed".equals(date.get("flag"))) {
            return 0;
        }
        return 1;
    }

    public void updateEnterprise(Map<String, String> map) {
        Enterprise enterprise = new Enterprise();
        enterprise.setEnterpriseId(map.get("code"));
        List<Enterprise> enterprises = enterpriseMapper.selectEnterpriseList(enterprise);
        enterprise.setEnterpriseTaxpayerRegisterId(map.get("code"));
        enterprise.setEnterpriseNameCh(map.get("companyname"));
        enterprise.setEnterpriseType(map.get("corprType"));
        enterprise.setEnterpriseRegisterAddress(map.get("corprAddres"));
        enterprise.setEnterpriseCapital(map.get("enterpriseCapital"));
        enterprise.setEnterpriseTime(map.get("enterpriseTime"));
        enterprise.setEnterpriseValidDateStart(map.get("validDateStart"));
        enterprise.setEnterpriseValidDateend(map.get("validDateEnd"));
        if (enterprises.size() > 0) {
            enterprise.setId(enterprises.get(0).getId());
            enterprise.setEnterpriseId(enterprises.get(0).getEnterpriseId());
            int row = enterpriseMapper.updateEnterprise(enterprise);
        } else {
            int row = enterpriseMapper.insertEnterprise(enterprise);
        }
    }

    public int updateSupplierEdit(Supplier supplier) throws Exception {
        int row = supplierMapper.updateSupplierEdit(supplier);
        return row;
    }

    /**
     * 更改供应商账号状态
     *
     * @param supplier
     * @return
     */
    @Override
    public int changeStatus(Supplier supplier) {
        return supplierMapper.updateSupplier(supplier);
    }

    public int updateSupplierNewPassWord(Supplier supplier) {
        return supplierMapper.updateSupplier(supplier);//修改状态
    }

    /**
     * 生成随机盐
     */
    public String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        return hex;
    }

    public String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex().toString();
    }

    /**
     * 日期格式化
     *
     * @param str
     * @return
     */
    public String dateFormat(String str) {
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            if (str != null && !"".equals(str)) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                    if (str2.length() == 4) {
                        str2 += "-";
                    }
                    if (str2.length() == 7) {
                        str2 += "-";
                    }
                }
            }
        }
        return str2;
    }

    /**
     * 验证社会统一信用代码
     *
     * @return
     */
    public boolean validateCode(String code) {
        String patrn = "^[0-9A-Z]";
        //18位校验及大写校验
        if ((code.length() != 18) || (Pattern.matches(patrn, code) == false)) {
            logger.info(code + " 不是有效的社会信用代码！");
            return false;
        } else {
            String Ancode;//统一社会信用代码的每一个值
            int Ancodevalue;//统一社会信用代码每一个值的权重
            int total = 0;
            ArrayList<Integer> weightedfactors = new ArrayList<>();
            weightedfactors.add(1);
            weightedfactors.add(3);
            weightedfactors.add(9);
            weightedfactors.add(27);
            weightedfactors.add(19);
            weightedfactors.add(26);
            weightedfactors.add(16);
            weightedfactors.add(17);
            weightedfactors.add(20);
            weightedfactors.add(29);
            weightedfactors.add(25);
            weightedfactors.add(13);
            weightedfactors.add(8);
            weightedfactors.add(24);
            weightedfactors.add(10);
            weightedfactors.add(30);
            weightedfactors.add(28);
            String str = "0123456789ABCDEFGHJKLMNPQRTUWXY";
            //不用I、O、S、V、Z
            for (int i = 0; i < code.length() - 1; i++) {
                Ancode = code.substring(i, i + 1);
                Ancodevalue = str.indexOf(Ancode);
                total = total + Ancodevalue * weightedfactors.get(i);
            }
            int logiccheckcode = 31 - total % 31;
            if (logiccheckcode == 31) {
                logiccheckcode = 0;
            }
            String Str = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,T,U,W,X,Y";
            String[] Array_Str = Str.split(",");
            String logiccheckcodeStr = Array_Str[logiccheckcode];
            String checkcode = code.substring(17, 18);
            if (logiccheckcodeStr != checkcode) {
                logger.info(code + " 不是有效的社会信用代码！");
                return false;
            }
            return true;
        }
    }
}
