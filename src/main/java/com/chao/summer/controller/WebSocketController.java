package com.chao.summer.controller;

import com.chao.summer.domain.entity.WebSocketMessage;
import com.chao.summer.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("websocket")
public class WebSocketController {
    @GetMapping()
    public String send(WebSocketMessage webSocketMessage) {
        System.out.println(webSocketMessage.getUser());
        System.out.println(webSocketMessage.getMessage());
        WebSocketServer.sendInfo(webSocketMessage.getMessage(), webSocketMessage.getUser());
        return "OK";
    }
}

