package com.example.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot.module.User;
import com.example.springboot.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SpringBootApplication(scanBasePackages = {"com.example.springboot.service","com.example.springboot.mapper"})
@MapperScan("com.example.springboot.mapper")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/userPage")
    public ModelAndView userPage() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/user/list");

        List<User> list = new ArrayList<>();
        User user = userService.getUserById(1);
        list.add(user);
        user = userService.getUserById(2);
        list.add(user);
        view.addObject("userList",list);
        return view;
    }

    @RequestMapping("/view")
    @ResponseBody
    public String view () {
        User user = userService.getUserById(2);
        return JSONObject.toJSONString(user);
    }

    @RequestMapping("/selectUserList")
    @ResponseBody
    public String selectUserList () {
        List<User> userList = userService.selectUserList();
        return JSONObject.toJSONString(userList);
    }

    /**
     * vue项目开发的后台接口
     * @param request
     * @param response
     */
    @RequestMapping("/getList")
    @ResponseBody
    public void getListByParam(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("调用getListByParam方法");
            String callbackFunName = request.getParameter("callback");
            String keywords = request.getParameter("keywords");
            int curPage = Integer.parseInt(request.getParameter("curr") == null ? "1" : request.getParameter("curr"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "2" : request.getParameter("pageSize"));
            List<User> userList = userService.selectUserList(keywords, curPage, pageSize);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //创建JSONObject对象
            JSONObject result = new JSONObject();
            //创建JSONArray实例
            JSONArray jsonArray = new JSONArray();
            //for each循环取出每个User对象
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                //JSONObject是一个{}包裹起来的一个对象(Object)，
                //JSONArray则是[]包裹起来的一个数组(Array)
                //此处为对象，所以用得到JSONObject
                JSONObject jo = new JSONObject();
                jo.put("id", user.getId());
                jo.put("username", user.getUsername());
                jo.put("password", user.getPassword());
                jo.put("pic", user.getPic());
                jsonArray.add(jo);
            }
            result.put("code", "0");
            result.put("msg", "");
            result.put("count", userList.size());
            result.put("curr", curPage);
            result.put("limit", pageSize);
            result.put("data", jsonArray);
            System.out.println("enterList.size(): " + userList.size());
            System.out.println("curPage: " + curPage);
            System.out.println("pageSize: " + pageSize);
            //设置字符集
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setCharacterEncoding("UTF-8");
            //页面输出
            PrintWriter out = response.getWriter();
            out.write( result.toString() );
            out.flush();
            out.close();
            /*return JSONObject.toJSONString(result);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*return null;*/
    }

    /**
     * vue项目开发的后台接口
     * @param request
     * @param response
     */
    @RequestMapping("/getOptionList")
    @ResponseBody
    public void getOptionList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        List<Map<String,String>> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("value","选项1");
        map.put("label","黄金糕");
        list.add(map);
        Map map2 = new HashMap();
        map2.put("value","选项2");
        map2.put("label","双皮奶");
        list.add(map2);
        result.put("data", list);

        //设置字符集
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        //页面输出
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write( result.toString() );
        out.flush();
        out.close();
    }
}
