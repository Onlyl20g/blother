package com.jinke.project.monitor.job.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jinke.project.system.ncInteractivity.controller.NCInteractiveControllor;
import com.jinke.project.system.ncInteractivity.domain.CustomerManager;
import com.jinke.project.system.user.asyncTask.SsoUserRegisterTask;
import com.jinke.project.system.user.domain.User;
import com.jinke.project.system.user.domain.UserRole;
import com.jinke.project.system.user.mapper.UserRoleMapper;
import com.jinke.project.system.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("customerManagerTask")
public class CustomerManagerTask {

    private final static Logger log = LoggerFactory.getLogger(CustomerManagerTask.class);

    @Value("${password}")
    private String password;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private NCInteractiveControllor ncInteractiveControllor;

    @Autowired
    private SsoUserRegisterTask ssoUserRegisterTask;

    /**
     * 查找客户经理信息
     *
     * @param id
     */
    public void findCustomerManager(String id) {
        User u = new User();
        u.setDeptId(Long.valueOf(Integer.parseInt(id)));

        User user = iUserService.selectUserByDeptId(u);
        String info = null;
//        if (user == null) {//从核心查询客户经理信息
        info = ncInteractiveControllor.supplierHttpPost("1900-01-01 00:00:00");
        /*} else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(user.getCreateTime());
            info = NCInteractiveControllor.supplierHttpPost(time);
        }*/

        JSONObject d = JSON.parseObject(info);
        if (d == null) {
            return;
        }
        if ("success".equals(d.get("flag"))) {
            String body = JSON.toJSONString(d.get("body"));
            List<CustomerManager> list = JSON.parseArray(body, CustomerManager.class);
            List<UserRole> userRoles = new ArrayList<>();
            List<User> users = iUserService.selectUserListByDeptId(id);
            for (User us : users) {//删除核心删除的数据
                Boolean b = true;
                for (CustomerManager customer : list) {
                    if (us.getBillId().equals(customer.getUserId())) {//判断是核心是否删除客当前户经理信息
                        b = false;
                    }
                }
                if (b) {
                    iUserService.deleteUserById(us.getUserId());
                    UserRole ur = new UserRole();
                    ur.setRoleId(Long.valueOf(4));
                    ur.setUserId(us.getUserId());
                    userRoleMapper.deleteUserRoleInfo(ur);
                }
            }
//            iUserService.deleteUserByDeptId(id);
            for (CustomerManager customer : list) {//向user表添加客户经理信息
                User us = new User();
                User user1 = iUserService.selectUserByLoginName(customer.getUserCode());
                us.setLoginName(customer.getUserCode());
                us.setUserName(customer.getName());
                us.setBillId(customer.getUserId());
                us.setDeptId(Long.valueOf(id));
                us.setStatus(customer.getStatus().equals("1") ? "0" : "1");
                if (user1 != null) {
                    us.setUserId(user1.getUserId());
                    iUserService.updateUserInfo(us);
                } else {
                    us.setPassword(password);
                    iUserService.insertUser(us);
                    UserRole ur = new UserRole();//添加角色绑定
                    ur.setRoleId(Long.valueOf(4));
                    ur.setUserId(us.getUserId());
                    userRoles.add(ur);

                    ssoUserRegisterTask.doTaskSsoUserRegister(user);
                }
            }
            if (userRoles.size() != 0) {
                userRoleMapper.batchUserRole(userRoles);
            }
        }
    }
}