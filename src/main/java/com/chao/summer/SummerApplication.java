package com.chao.summer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.chao.summer.mapper")
@EnableRedisHttpSession(redisNamespace = "summer:session", maxInactiveIntervalInSeconds = 864000)
public class SummerApplication {

    public static void main(String[] args) {

        SpringApplication.run(SummerApplication.class, args);

        // 变更配置文件读取位置启动
/*        new SpringApplicationBuilder(SpringbootConfigApplication.class)
                .properties("spring.config.location=classpath:/springbootconfig.yml").run(args);*/
    }

}
