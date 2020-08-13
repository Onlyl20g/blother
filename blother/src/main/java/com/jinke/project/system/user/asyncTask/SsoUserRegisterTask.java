package com.jinke.project.system.user.asyncTask;

import com.jinke.framework.config.WebConfig;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.service.IUserService;
import com.jinke.project.tool.sso.SsoUtil;
import com.jinke.project.tool.tools.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SsoUserRegisterTask {
    @Autowired
    private IUserService userService;
    @Autowired
    private WebConfig webConfig;

    @Async
    public void doTaskSsoUserRegister(User user) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cust_id", user.getLoginName());
        params.put("username", user.getLoginName());
        params.put("name", user.getUserName());
        params.put("idno", user.getLoginName());

        try {
            HttpUtils.doPost(webConfig.getSsoBaseUrl(), webConfig.getSsoInitCustInfo(), null, new HashMap<String,String>(), SsoUtil.createEncryptData(params), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
