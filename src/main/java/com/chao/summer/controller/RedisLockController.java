package com.chao.summer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisLockController {

    private final StringRedisTemplate redis;

    public RedisLockController(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @GetMapping("/deduct_stock")
    public String deductStock() {
        String lockKey = "product_1";
        String lockId = UUID.randomUUID().toString();
        try {
            Boolean res = redis.opsForValue().setIfAbsent(lockKey, lockId, 30, TimeUnit.SECONDS);
            if (!res) {
                return "error";
            }

            int stock = Integer.parseInt(redis.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                redis.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余：" + realStock);

            } else {
                System.out.println("扣减失败");
            }

        } finally {
            if (lockId.equals(redis.opsForValue().get(lockKey))) {
                redis.delete(lockKey);
            }
        }

    }

}
