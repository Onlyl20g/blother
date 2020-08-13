package com.jinke.project.tool.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinke.project.customer.domain.Enterprise;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class GeneralMethods {
    /**
     * @param enterpriseInfo
     * @return
     */
    public static Enterprise styleEnterprise(String enterpriseInfo) {
        Enterprise enterprise = new Enterprise();
        JSONObject jsonObject = JSON.parseObject(enterpriseInfo);
        enterprise.setEnterpriseBankAddress("企业开户行地址: " + jsonObject.getString("ENTERPRISE_BANK_ADDRESS"));
        enterprise.setEnterpriseBankCity("企业开户行所在城市: " + jsonObject.getString("ENTERPRISE_BANK_CITY"));
        enterprise.setEnterpriseBankCountryId("企业开户行所在地国家行政编码: " + jsonObject.getString("ENTERPRISE_BANK_COUNTRY_ID"));
        enterprise.setEnterpriseBankDistrict("企业开户行所在区域: " + jsonObject.getString("ENTERPRISE_BANK_DISTRICT"));
        enterprise.setEnterpriseBankId("企业对公账户号: " + jsonObject.getString("ENTERPRISE_BANK_ID"));
        enterprise.setEnterpriseBankName("企业开户行名称: " + jsonObject.getString("ENTERPRISE_BANK_NAME"));
        enterprise.setEnterpriseBankProvince("企业开户行所在省份: " + jsonObject.getString("ENTERPRISE_BANK_PROVINCE"));
        enterprise.setEnterpriseCapital("企业注册资本: " + jsonObject.getString("ENTERPRISE_CAPITAL"));
        enterprise.setEnterpriseCity("企业所在城市: " + jsonObject.getString("ENTERPRISE_CITY"));
        enterprise.setEnterpriseCountryId("企业所在地国家行政编码: " + jsonObject.getString("ENTERPRISE_COUNTRY_ID"));
        enterprise.setEnterpriseDistrict("企业所在区域: " + jsonObject.getString("ENTERPRISE_DISTRICT"));
        enterprise.setEnterpriseId("企业统一社会信用编码: " + jsonObject.getString("ENTERPRISE_ID"));
        enterprise.setEnterpriseIndustry("企业所属行业: " + jsonObject.getString("ENTERPRISE_INDUSTRY"));
        enterprise.setEnterpriseInsured("企业缴纳社会保险人数: " + jsonObject.getString("ENTERPRISE_INSURED"));
        enterprise.setEnterpriseNameCh("企业名称: " + jsonObject.getString("ENTERPRISE_NAME_CH"));
        enterprise.setEnterpriseNameEn("企业英文名称: " + jsonObject.getString("ENTERPRISE_NAME_EN"));
        enterprise.setEnterpriseOirganization("企业组织: " + jsonObject.getString("ENTERPRISE_ORGANIZATION"));
        enterprise.setEnterpriseOwner("企业法人: " + jsonObject.getString("ENTERPRISE_OWNER"));
        enterprise.setEnterpriseBankAddress("企业开户号地址: " + jsonObject.getString("ENTERPRISE_BANK_ADDRESS"));
        enterprise.setEnterprisePeople("企业人数: " + jsonObject.getString("ENTERPRISE_PEOPLE"));
//        enterprise.setEnterprisePro(jsonObject.getString("ENTERPRISE_PROVINCE"));
        enterprise.setEnterpriseRegisterAddress("企业注册地址: " + jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS"));
        enterprise.setEnterpriseRegisterAddressCity("企业注册地址所在城市: " + jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_CITY"));
        enterprise.setEnterpriseRegisterAddressDistrict("企业注册地址所在区域: " + jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_DISTRICT"));
        enterprise.setEnterpriseRegisterAddressFormatAddress("企业注册地址格式标准化: " + jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_FORMAT_ADDRESS"));
        enterprise.setEnterpriseRegisterAddressGps("企业注册地址gps: " + jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_GPS"));
        enterprise.setEnterpriseRegisterAddressProvince("企业注册地址所在省份: " + jsonObject.getString("ENTERPRISE_REGISTER_ADDRESS_PROVINCE"));
//        enterprise.setEnterpriseR(jsonObject.getString("ENTERPRISE_REGISTER_ID"));
        enterprise.setEnterpriseScope("企业经营范围: " + jsonObject.getString("ENTERPRISE_SCOPE"));
        enterprise.setEnterpriseTaxpayerRegisterId("企业税务登记号: " + jsonObject.getString("ENTERPRISE_TAXPAYER_REGISTER_ID"));
        enterprise.setEnterpriseTel("企业联系电话: " + jsonObject.getString("ENTERPRISE_TEL"));
        String time = dateFormat(jsonObject.getString("ENTERPRISE_TIME"));
        enterprise.setEnterpriseTime("企业注册时间: " + time);
        String s = jsonObject.getString("ENTERPRISE_VALID_DATE_END");
        String timeEnd = dateFormat(jsonObject.getString("ENTERPRISE_VALID_DATE_END"));
        if (s == null || s.equals("") || s.equals("永久")) {
            enterprise.setEnterpriseValidDateend("企业营业期限结束时间: " + "永久");
        } else {
            enterprise.setEnterpriseValidDateend("企业营业期限结束时间: " + timeEnd);
        }
        String timeStart = dateFormat(jsonObject.getString("ENTERPRISE_VALID_DATE_START"));
        enterprise.setEnterpriseValidDateStart("企业营业期限开始时间: " + timeStart);
        enterprise.setEnterpriseType("企业类型: " + jsonObject.getString("ENTERPRISE_TYPE"));
        enterprise.setEnterpriseUniversalCreditIdAgencyCode("企业组织机构代码号: " + jsonObject.getString("ENTERPRISE_UNIVERSAL_CREDIT_ID_AGENCY_CODE"));
        enterprise.setEnterpriseUsedName("企业曾用名: " + jsonObject.getString("ENTERPRISE_USED_NAME"));
        return enterprise;
    }

    /**
     * 日期格式化
     *
     * @param str
     * @return
     */
    public static String dateFormat(String str) {
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
     * 日期加减
     *
     * @param dateTime
     * @param n
     * @return
     */
    public static Date addAndSubtractDaysByCalendar(Date dateTime/*待处理的日期*/, int n/*加减天数*/) {
        //日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Calendar calstart = java.util.Calendar.getInstance();
        calstart.setTime(dateTime);
        calstart.add(java.util.Calendar.DAY_OF_WEEK, n);
        System.out.println(df.format(calstart.getTime()));
        return calstart.getTime();
    }

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    public static String getUrl(HttpServletRequest request) {
        String host = request.getHeader("Host");
        return request.getScheme() + "://" + host;
    }

    /**
     * 下载过滤重名文件
     *
     * @param set
     * @param name
     * @return
     */
    public static String filterAlikeName(Set<String> set, String name) {
        String result = "";
        if (set.size() > 0) {
            boolean contains = set.contains(name);
            if (contains) {
                String[] value = name.split("\\.");
                if (value[0].indexOf("(") == -1) {
                    result = value[0] + "(1)." + value[1];
                    result = filterAlikeName(set, result);
                } else {
                    int begin = value[0].indexOf("(");
                    int end = value[0].indexOf(")");
                    String num = value[0].substring(begin + 1, end);
                    int row = Integer.parseInt(num) + 1;
                    String after = value[0].split("\\(")[0];
                    result = after + "(" + row + ")." + value[1];
                    result = filterAlikeName(set, result);
                }
            } else {
                result = name;
            }
        } else {
            result = name;
        }
        return result;
    }
}