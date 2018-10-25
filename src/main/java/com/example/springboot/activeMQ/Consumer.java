package com.example.springboot.activeMQ;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot.module.User;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Consumer {

    //JmsListener注解监听队列
    @JmsListener(destination = "sample.queue")
    public void receiveQueue(Map<String,Object> text) {

//        receiveMap(text);
        receiveMap2(text);
    }

    public void receiveMap(Map map) {
        String userId = String.valueOf(map.get("userId"));
        String userName = String.valueOf(map.get("userName"));
        System.out.println("Consumer收到的报文为:" + userId + " 名称为：" + userName);
    }

    public void receiveMap2(Map map) {
        User user = JSONObject.parseObject((String) map.get("user"), User.class);
        System.out.println("Consumer收到的报文为:" +  user.getId() + " 名称为：" + user.getUsername());
    }
}