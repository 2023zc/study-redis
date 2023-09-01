package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class newtest {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void test(){
        System.out.println(redisTemplate.opsForValue().get("product_id"));
    }


}
