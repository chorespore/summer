package com.chao.summer;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class SummerTest {

    @Test
    void timeTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
    }
}
