package com.chao.summer.domain;

import lombok.Data;

import java.util.HashMap;

@Data
public class MapBody {
    private String resultCode;
    private ResultData resultData;

    @Data
    public static class ResultData {
        private HashMap<String, String> value;
    }
}


/**
 * {
 *     "resultCode": "200",
 *     "resultData": {
 *         "value": {
 *             "国籍": "中国",
 *             "职业": "太守"
 *         }
 *     }
 * }
 */