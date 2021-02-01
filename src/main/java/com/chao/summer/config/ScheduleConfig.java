package com.chao.summer.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
public class ScheduleConfig {

    @Scheduled(cron = "0/2 * * * * ?")
    public void tick() {
        System.out.println("Scheduled: " + new Date());
    }
}
