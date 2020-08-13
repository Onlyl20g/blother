package com.jinke.project.customer.controller;

import com.jinke.common.constant.Constants;
import com.jinke.common.constant.ShiroConstants;
import com.jinke.common.utils.ServletUtils;
import com.jinke.common.utils.StringUtils;
import com.jinke.framework.shiro.token.ImageSysUsernamePasswordToken;
import com.jinke.project.system.billId.domain.BillId;
import com.jinke.project.system.billId.service.IBillIdService;
import com.jinke.project.system.fileAttachType.domain.FileAttachType;
import com.jinke.project.system.fileAttachType.service.IFileAttachTypeService;
import com.jinke.project.system.shareFile.service.IShareFileService;
import com.jinke.project.tool.sso.SsoUtil;
import com.jinke.project.tool.sso.bean.JinkResult;
import com.jinke.project.tool.sso.bean.SsoCheckTokenResultBean;
import com.jinke.project.tool.tools.GeneralMethods;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SsoController {
    @Autowired
    private IShareFileService iShareFileService;

    @Autowired
    private IFileAttachTypeService iFileAttachTypeService;

    @Autowired
    private IBillIdService iBillIdService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/sso")
    public String ssoLogin(ModelMap mmap) {
        HttpServletRequest request = ServletUtils.getRequest();

        String custId = request.getParameter("cust_id");
        String accessToken = request.getParameter("access_token");

        if (StringUtils.isEmpty(custId) || StringUtils.isEmpty(accessToken)) {
            return "error/500";
        }

        JinkResult<SsoCheckTokenResultBean> result = SsoUtil.checkToken(custId, accessToken);
        if (result != null && Constants.FLAG_SUCCESS.equals(result.getFlag())) {
            ImageSysUsernamePasswordToken token = new ImageSysUsernamePasswordToken(result.getResult().getTarget_cust_id(), "", false, ShiroConstants.ImageSysTokenType.SSO);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/";
            }
        }

        String target = request.getParameter("target");
        String applyId = request.getParameter("applyId");
        String system = request.getParameter("system");
        String optType = request.getParameter("opt_type");
        String subject = request.getParameter("subject");
        HttpSession session = request.getSession();
        session.setAttribute("optType", optType);
        session.setAttribute("subject", subject);
        session.setAttribute("system", system);

        if (!StringUtils.isEmpty(target) && !StringUtils.isEmpty(applyId) && target.equalsIgnoreCase(Constants.SSO_TARGET_APPLY_ATTACH)) {
            String host = GeneralMethods.getUrl(request);
            String url = host + "/customer/pages/hkwithdraw?s=" + applyId;

            return "redirect:" + url;
        }

        return "redirect:/main";
    }

    @PostMapping("/sso")
    @ResponseBody
    public Map ssoRegister() {
        HttpServletRequest request = ServletUtils.getRequest();

        Map<Object, String> map = new HashMap<>();
        String custId = request.getParameter("cust_id");
        String accessToken = request.getParameter("access_token");

        if (StringUtils.isEmpty(custId) || StringUtils.isEmpty(accessToken)) {
            map.put("code", "1");
            map.put("flag", "fail");
            map.put("msg", "参数错误");
            return map;
        }

        JinkResult<SsoCheckTokenResultBean> result = SsoUtil.checkToken(custId, accessToken);
        if (result != null && Constants.FLAG_SUCCESS.equals(result.getFlag())) {
            ImageSysUsernamePasswordToken token = new ImageSysUsernamePasswordToken(result.getResult().getTarget_cust_id(), "", false, ShiroConstants.ImageSysTokenType.SSO);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", "2");
                map.put("flag", "fail");
                map.put("msg", "签名验证失败");
                return map;
            }
        }
        String target = request.getParameter("target");
        String applyId = request.getParameter("apply_id");
        String system = request.getParameter("system");

        FileAttachType fileAttachType = new FileAttachType();
        fileAttachType.setSysType(system);
        List<FileAttachType> fileAttachTypes = iFileAttachTypeService.selectFileAttachTypeList(fileAttachType);
        if (fileAttachTypes.size() == 0) {
            map.put("code", "10001");
            map.put("flag", "fail");
            map.put("msg", "系统不存在");
            return map;
        }
        if (!StringUtils.isEmpty(target) && !StringUtils.isEmpty(applyId) && target.equalsIgnoreCase(Constants.SSO_TARGET_CREATE_BILLID)) {
            BillId billId = new BillId();
            billId.setSystem(system);
            billId.setBillId(applyId);
            List<BillId> billIds = iBillIdService.selectBillIdList(billId);
            if (billIds.size() > 0) {
                map.put("code", "10002");
                map.put("flag", "fail");
                map.put("msg", "此业务单据已创建");
                return map;
            }
            billId.setCreateTime(new Date());
            int row = iBillIdService.insertBillId(billId);
            if (row > 0) {
                map.put("flag", "success");
                map.put("code", "0");
                map.put("msg", "成功");
                return map;
            }
            map.put("code", "10003");
            map.put("flag", "fail");
            map.put("msg", "系统错误");
            return map;
        }
        map.put("code", "10003");
        map.put("flag", "fail");
        map.put("msg", "系统错误");
        return map;
    }
}
