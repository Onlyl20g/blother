package com.jinke.project.system.user.asyncTask;

import com.alibaba.fastjson.JSON;
import com.jinke.common.constant.Constants;
import com.jinke.common.constant.UserConstants;
import com.jinke.common.constant.WebConstants;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.service.IUserService;
import com.jinke.project.tool.sso.SsoUtil;
import com.jinke.project.tool.sso.bean.JinkResult;
import com.jinke.project.tool.tools.RemoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SsoUserRegisterTask {
    @Autowired
    private IUserService userService;

    @Async
    public void doTaskSsoUserRegister(User user) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cust_id", user.getLoginName());
        params.put("username", user.getLoginName());
        params.put("name", user.getUserName());
        params.put("idno", user.getLoginName());

        try {
            String data = RemoteUtil.postData(WebConstants.SSO_BASE_URL + WebConstants.SSO_INIT_CUST_INFO, null, SsoUtil.createEncryptData(params));
            if (data != null) {
                JinkResult result = JSON.parseObject(data, JinkResult.class);
                if (Constants.FLAG_SUCCESS.equals(result.getFlag())) {
                    user.setSsoStatus(UserConstants.SSO_STATUS_REGISTER);
                    userService.updateUser(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
