package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/testLock")
public class Lock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping
    public void testlock() {
        //使用uuid解决误删除问题
        String uuid = UUID.randomUUID().toString();
        //获取锁,设置过期时间
        boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid,3, TimeUnit.SECONDS);
        if (lock) {
            String num = redisTemplate.opsForValue().get("num");
            if (num == null) {
                return;
            }
            int Num = Integer.parseInt(num);
            //让num++
            redisTemplate.opsForValue().increment("num", 1);
            if(redisTemplate.opsForValue().get("lock").equals(uuid)){
                //释放锁
                redisTemplate.delete("lock");
            }
        } else {
            try {
                Thread.sleep(100);
                testlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
