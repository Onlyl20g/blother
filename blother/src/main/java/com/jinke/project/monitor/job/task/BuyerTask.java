package com.jinke.project.monitor.job.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jinke.project.system.buyer.domain.Buyer;
import com.jinke.project.system.buyer.service.IBuyerService;
import com.jinke.project.system.creditBuyerInfo.domain.CreditBuyerInfo;
import com.jinke.project.system.ncInteractivity.controller.NCInteractiveControllor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("buyer")
public class BuyerTask {

    private final static Logger log = LoggerFactory.getLogger(BuyerTask.class);

    @Autowired
    private IBuyerService iBuyerService;

    @Autowired
    private NCInteractiveControllor ncInteractiveControllor;

    public void updateBuyer() {

        Buyer buyerInfo = iBuyerService.selectBuyerTime();
        String data = null;
        try {
            if (buyerInfo != null) {//判断数据库里是否存在数据
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                data = ncInteractiveControllor.buyerPost(format.format(format.parse(buyerInfo.getCreateTime())));
            } else {
                data = ncInteractiveControllor.buyerPost("1970-01-01 00:00:00");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject obj = JSON.parseObject(data);
        if (obj == null) {
            return;
        }
        if ("success".equals(obj.get("flag"))) {
            String item = JSON.toJSONString(obj.get("body"));
            JSONArray listItem = JSON.parseArray(item);
            List<Buyer> list = new ArrayList<Buyer>();
            for (Object ob : listItem) {
                Buyer buyer = new Buyer();
                Map<String, Object> map = JSON.parseObject(ob.toString());
                if ("1".equals(map.get("status").toString())) {
                    buyer.setBuyerId(map.get("buyerId").toString());
                    buyer.setBuyerName(map.get("buyerName").toString());
                    buyer.setCreateTime(map.get("updateTime").toString());
                    Buyer result = iBuyerService.selectBuyerById(map.get("buyerId").toString());
                    log.info("data:" + result);
                    if (result == null) {
                        iBuyerService.insertBuyer(buyer);//添加买方信息
                    } else {
                        iBuyerService.updateBuyer(buyer);//修改买方信息
                    }
                } else {
                    iBuyerService.deleteBuyerByIds(map.get("buyerId").toString());
                }
            }
        }
    }
}