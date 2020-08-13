package com.jinke.project.system.upload.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinke.project.system.enterprise.domain.Enterprise;
import com.jinke.project.system.enterprise.service.IEnterpriseService;
import com.jinke.project.system.fileTemp.service.IFileTempService;
import com.jinke.project.system.upload.service.UploadFileService;
import com.jinke.project.tool.fastdfs.ImageApiService;
import com.jinke.project.tool.tools.StaticFileConstants;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件操作处理
 * Created by user on 2019/7/10.
 */
@Controller
@RequestMapping("system/file")
public class UploadFileController {
    private final static Logger log = LoggerFactory.getLogger(UploadFileController.class);

    private String prefix = "system/file";

    @Value("${folderurl}")
    private String folderurl;

    /*@Value("${withdrawdemourl}")
    private String withdrawdemourl;*/

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private ImageApiService imageApiService;

    @Autowired
    private IFileTempService fileTempService;

    @Autowired
    private IEnterpriseService iEnterpriseService;

    @GetMapping()
    public String credit() {
        return prefix + "/file";
    }


    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String url = uploadFileService.UploadFilesToServer(file);
        return "{\"path\":\"" + url + "\"}";
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/uploadOther")
    @ResponseBody
    public String uploadFileOther(@RequestParam("file") MultipartFile file) throws Exception {
        String url = uploadFileService.UploadFilesToServer(file);
        Long size = file.getSize();
        String name = file.getOriginalFilename();
        Map<String,String> map = new HashMap<String,String>();
        map.put("path",url);
        map.put("size",size.toString());
        map.put("name",name);
        return JSON.toJSONString(map);
    }

    /**
     * 上传营业执照
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/uploadBus")
    @ResponseBody
    public String uploadBus(@RequestParam("file") MultipartFile file) throws Exception {

        byte[] bytes = file.getBytes();
        String s = this.imageApiService.parseLicense(bytes, null);
        String url = uploadFileService.UploadFilesToServer(file);
        if (s != null && !"".equals(s) && !"null".equals(s)) {
            JSONObject jsonObject = JSON.parseObject(s);
            if (jsonObject.getString("ENTERPRISE_ID") != null && !"".equals(jsonObject.getString("ENTERPRISE_ID"))) {
                Enterprise enterprise = styleEnterprise(s);
                Enterprise en = iEnterpriseService.selectEnterpriseByEId(enterprise.getEnterpriseId());
                if (en == null) {
                    iEnterpriseService.insertEnterprise(enterprise);
                }
            }
            jsonObject.put("ENTERPRISE_VALID_DATE_START", dateFormat(jsonObject.getString("ENTERPRISE_VALID_DATE_START")));
            String timeEnd = jsonObject.getString("ENTERPRISE_VALID_DATE_END");
            if ("永久".equals(timeEnd)) {
                jsonObject.put("ENTERPRISE_VALID_DATE_END", "永久");
            } else {
                jsonObject.put("ENTERPRISE_VALID_DATE_END", dateFormat(jsonObject.getString("ENTERPRISE_VALID_DATE_END")));
            }
            jsonObject.put("ENTERPRISE_TIME", dateFormat(jsonObject.getString("ENTERPRISE_TIME")));
            s = jsonObject.toJSONString();
        }
        return putAll(s, url);
    }

    public static String putAll(String s, String url) {
        if (s == null || "".equals(s)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("url", url);
            return JSON.toJSONString(map);
        }
        JSONObject jsonObject = JSON.parseObject(s);
        jsonObject.put("url", url);
        return jsonObject.toJSONString();
    }

    /**
     * 上传身份证
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/uploadIDCard")
    @ResponseBody
    public String uploadIDCard(@RequestParam("file") MultipartFile file) throws Exception {

        byte[] bytes = file.getBytes();
        String s = imageApiService.parseIDCard(bytes, null);
        String url = uploadFileService.UploadFilesToServer(file);
        if ("".equals(s) && !"".equals(url)) {
            return "{\"url\":\"" + url + "\"}";
        } else if ("".equals(s) && "".equals(url)) {
            return "";
        }
        return putAll(s, url);
    }

    /**
     * 单文件下载
     *
     * @param filesPath
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/down")
    @ResponseBody
    public void downloadFile(@RequestParam("url") String filesPath, @RequestParam("name") String name, HttpServletResponse response) throws IOException {
        uploadFileService.downloadFile(filesPath, name, response);
    }

    /**
     * 单文件下载
     *
     * @param filesPath
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/loadImg")
    @ResponseBody
    public void loadImg(@RequestParam("url") String filesPath, HttpServletResponse response) throws IOException {
        uploadFileService.loadImg(filesPath, response);
    }

    /**
     * 下载模板
     *
     * @param flag
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/downStaticFile")
    @ResponseBody
    public void downloadDemoFile(@RequestParam("flag") String flag, HttpServletResponse response) throws IOException {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            return;
        }
        if ("业务申请书".equals(flag)) {
            uploadFileService.downStaticFile(folderurl + File.separator + StaticFileConstants.STATIC_CREDIR_APPLY_TEMPLATE, "业务申请书.docx", response);
        } else if ("提款申请书".equals(flag)) {
            uploadFileService.downStaticFile(folderurl + File.separator + StaticFileConstants.STATIC_WITHDRAW_TEMPLATE, "提款申请书.docx", response);
        }
    }

    /**
     * 删除单个文件
     *
     * @param url
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public Integer delfile(@RequestParam("url") String url) {
        Integer row = uploadFileService.deleteFile(url);
        return row;
    }

    /**
     * @param enterpriseInfo
     * @return
     */
    public Enterprise styleEnterprise(String enterpriseInfo) {
        Enterprise enterprise = new Enterprise();
        JSONObject jsonObject = JSON.parseObject(enterpriseInfo);
        enterprise.setEnterpriseBankAddress(jsonObject.getString("ENTERPRISE_BANK_ADDRESS"));
        enterprise.setEnterpriseBankCity(jsonObject.getString("ENTERPRISE_BANK_CITY"));
        enterprise.setEnterpriseBankCountryId(jsonObject.getString("ENTERPRISE_BANK_COUNTRY_ID"));
        enterprise.setEnterpriseBankDistrict(jsonObject.getString("ENTERPRISE_BANK_DISTRICT"));
        enterprise.setEnterpriseBankId(jsonObject.getString("ENTERPRISE_BANK_ID"));
        enterprise.setEnterpriseBankName(jsonObject.getString("ENTERPRISE_BANK_NAME"));
        enterprise.setEnterpriseBankProvince(jsonObject.getString("ENTERPRISE_BANK_PROVINCE"));
        enterprise.setEnterpriseCapital(jsonObject.getString("ENTERPRISE_CAPITAL"));
        enterprise.setEnterpriseCity(jsonObject.getString("ENTERPRISE_CITY"));
        enterprise.setEnterpriseBankAddress(jsonObject.getString("ENTERPRISE_BANK_ADDRESS"));
        enterprise.setEnterpriseCountryId(jsonObject.getString("ENTERPRISE_COUNTRY_ID"));
        enterprise.setEnterpriseDistrict(jsonObject.getString("ENTERPRISE_DISTRICT"));
        enterprise.setEnterpriseId(jsonObject.getString("ENTERPRISE_ID"));
        enterprise.setEnterpriseIndustry(jsonObject.getString("ENTERPRISE_INDUSTRY"));
        enterprise.setEnterpriseInsured(jsonObject.getString("ENTERPRISE_INSURED"));
        enterprise.setEnterpriseNameCh(jsonObject.getString("ENTERPRISE_NAME_CH"));
        enterprise.setEnterpriseNameEn(jsonObject.getString("ENTERPRISE_NAME_EN"));
        enterprise.setEnterpriseOirganization(jsonObject.getString("ENTERPRISE_ORGANIZATION"));
        enterprise.setEnterpriseOwner(jsonObject.getString("ENTERPRISE_OWNER"));
        enterprise.setEnterpriseBankAddress(jsonObject.getString("ENTERPRISE_BANK_ADDRESS"));
        enterprise.setEnterprisePeople(jsonObject.getString("ENTERPRISE_PEOPLE"));
//        enterprise.setEnterprisePro(jsonObject.getString("ENTERPRISE_PROVINCE"));
        enterprise.setEnterpriseRegisterAddress(jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS"));
        enterprise.setEnterpriseRegisterAddressCity(jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_CITY"));
        enterprise.setEnterpriseRegisterAddressDistrict(jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_DISTRICT"));
        enterprise.setEnterpriseRegisterAddressFormatAddress(jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_FORMAT_ADDRESS"));
        enterprise.setEnterpriseRegisterAddressGps(jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_GPS"));
        enterprise.setEnterpriseRegisterAddressProvince(jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_PROVINCE"));
//        enterprise.setEnterpriseR(jsonObject.getString("ENTERPRISE_REGISTER_ID"));
        enterprise.setEnterpriseScope(jsonObject.getString("ENTERPRISE_SCOPE"));
        enterprise.setEnterpriseTaxpayerRegisterId(jsonObject.getString("ENTERPRISE_TAXPAYER_REGISTER_ID"));
        enterprise.setEnterpriseTel(jsonObject.getString("ENTERPRISE_TEL"));
        String time = dateFormat(jsonObject.getString("ENTERPRISE_TIME"));
        enterprise.setEnterpriseTime(time);
        String s = jsonObject.getString("ENTERPRISE_VALID_DATE_END");
        String timeEnd = dateFormat(jsonObject.getString("ENTERPRISE_VALID_DATE_END"));
        if (s == null || s.equals("") || s.equals("永久")) {
            enterprise.setEnterpriseValidDateend("永久");
        } else {
            enterprise.setEnterpriseValidDateend(timeEnd);
        }
        String timeStart = dateFormat(jsonObject.getString("ENTERPRISE_VALID_DATE_START"));
        enterprise.setEnterpriseValidDateStart(timeStart);
        enterprise.setEnterpriseType(jsonObject.getString("ENTERPRISE_TYPE"));
        enterprise.setEnterpriseUniversalCreditIdAgencyCode(jsonObject.getString("ENTERPRISE_UNIVERSAL_CREDIT_ID_AGENCY_CODE"));
        enterprise.setEnterpriseUsedName(jsonObject.getString("ENTERPRISE_USED_NAME"));
        return enterprise;
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
}
