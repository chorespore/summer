package com.chao.summer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    StringRedisTemplate redis;

    @Test
    void redisSetTest() {
        redis.opsForValue().set("num", "123", 100, TimeUnit.SECONDS);

        redis.boundHashOps("HashKey").put("k1", "HashVaue1");
        BoundHashOperations boundHashOperations = redis.boundHashOps("HashKey");
        boundHashOperations.put("k2", "Value2");
        redis.opsForHash().put("HashKey", "k3", "HashVaue2");
        HashOperations hashOps = redis.opsForHash();
        hashOps.put("HashKey", "k4", "HashVaue3");
        boundHashOperations.expire(1, TimeUnit.HOURS);


        //1、通过redisTemplate设置值
        redis.boundListOps("listKey").leftPush("listLeftValue1");
        redis.boundListOps("listKey").rightPush("listRightValue2");

        //2、通过BoundValueOperations设置值
        BoundListOperations boundListOperations = redis.boundListOps("listKey");
        boundListOperations.leftPush("listLeftValue3");
        boundListOperations.rightPush("listRightValue4");

        //3、通过ValueOperations设置值
        ListOperations listOperations = redis.opsForList();
        listOperations.leftPush("listKey", "listLeftValue5");
        listOperations.rightPush("listKey", "listRightValue6");

        redis.boundValueOps("listKey").expire(8, TimeUnit.MINUTES);
        redis.expire("listKey", 6, TimeUnit.MINUTES);


        //1、通过redisTemplate设置值
        redis.boundZSetOps("zSetKey").add("zSetVaule1", 100D);

        //2、通过BoundValueOperations设置值
        BoundZSetOperations boundZSetOperations = redis.boundZSetOps("zSetKey");
        boundZSetOperations.add("zSetVaule", 108D);

        //3、通过ValueOperations设置值
        ZSetOperations zSetOps = redis.opsForZSet();
        zSetOps.add("zSetKey", "zSetVaule3", 80D);

        DefaultTypedTuple<String> p1 = new DefaultTypedTuple<>("zSetVaul4", 2.1D);
        DefaultTypedTuple<String> p2 = new DefaultTypedTuple<>("zSetVaule5", 3.3D);
        redis.boundZSetOps("zSetKey").add(new HashSet<>(Arrays.asList(p1, p2)));

        boundZSetOperations.expire(8, TimeUnit.MINUTES);
    }

    @Test
    void redisGetTest() {
        BoundHashOperations boundHashOperations = redis.boundHashOps("HashKey");
        Map entries = boundHashOperations.entries();

        Long size = redis.boundListOps("listKey").size();

        List listItems = redis.boundListOps("listKey").range(0, size);

        Set<TypedTuple<String>> tuples = redis.boundZSetOps("zSetKey").rangeWithScores(0L, 100L);

        for (TypedTuple<String> tuple : tuples) {
            System.out.println(tuple.getValue() + " : " + tuple.getScore());
        }

        System.out.println(entries);
        System.out.println(size);
        System.out.println(listItems);
    }

}
