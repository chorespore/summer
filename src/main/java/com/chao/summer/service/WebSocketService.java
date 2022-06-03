package com.chao.summer.service;

import com.chao.summer.websocket.WebSocketServer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@EnableScheduling
public class WebSocketService {

    //打印时间
//    @Scheduled(fixedRate = 5000) //1000毫秒执行一次
    public void printTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(new Date());
        WebSocketServer.sendInfo(date, "user1");
        System.out.println(date);
    }

}
