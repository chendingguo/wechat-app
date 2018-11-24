package com.airsupply.monitor.controller;

import com.airsupply.monitor.CheckUtil;
import com.airsupply.monitor.Service.HttpMonitorService;
import com.airsupply.monitor.Service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
@EnableAutoConfiguration
public class HelloController {

    @Autowired
    HttpMonitorService httpMonitorService;

    @Autowired
    WeChatService weChatService;


    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping("/listService")
    public Object listService() {
        return httpMonitorService.getUrl();
    }

    @GetMapping(value = "wechat")
    public void tokenVerify(HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            System.out.println("signature:" + signature);
            System.out.println("timestamp:" + timestamp);
            System.out.println("nonce:" + nonce);
            System.out.println("echostr:" + echostr);
            PrintWriter out = response.getWriter();

            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
                System.out.println("成功");
            } else {
                out.print(echostr);
                System.out.println("失败");

            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @PostMapping(value = "wechat")
    public String processMsg(HttpServletRequest request) {

        return weChatService.processRequest(request);
    }

    @GetMapping(value = "sendMsg")
    public String sendMsg(HttpServletRequest request) {

        return weChatService.sendMsg();
    }


}