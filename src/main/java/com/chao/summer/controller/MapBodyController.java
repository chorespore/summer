package com.chao.summer.controller;

import com.chao.summer.domain.MapBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("mapbody")
public class MapBodyController {
    @PostMapping("")
    public String hi(@RequestBody MapBody mapBody) {
        String code = mapBody.getResultCode();
        System.out.println(mapBody.getResultCode());
        HashMap<String, String> map = mapBody.getResultData().getValue();
        return map.getOrDefault("国籍", "") + "-" + map.getOrDefault("职业", "");
    }
}
