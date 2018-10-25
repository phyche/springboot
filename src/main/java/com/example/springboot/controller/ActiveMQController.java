package com.example.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot.activeMQ.Producer;
import com.example.springboot.module.User;
import com.example.springboot.service.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@RequestMapping(value = "/jms")
@RestController
public class ActiveMQController {

    @Autowired
    private Producer producer;

    @Autowired
    private UserService userService;

    //这里就是队列消息生产，方便使用postman测试
    @RequestMapping("/sendMsg")
    public void send() {
        Destination destination = new ActiveMQQueue("sample.queue");
        Map<String,Object> map = new HashMap();
//        map = sendMap(map);
        map = sendBean(map);
        producer.sendMessage(destination,map);
    }

    public Map sendMap(Map<String,Object> map) {
        User u = userService.getUserById(2);

        map.put("userId",u.getId());
        map.put("userName",u.getUsername());
        return map;
    }

    public Map sendBean(Map<String,Object> map) {
        User u = userService.getUserById(2);

        //消息体为对象的时候，对象必须可序列化，并且得将对象转成string放入消息体才能发送
        //同时，接收的时候先接收的是string，然后用JSONObject.parseObject((String) map.get("user"), User.class);转成对应的实体
        map.put("user", JSONObject.toJSONString(u));
        return map;
    }
}
