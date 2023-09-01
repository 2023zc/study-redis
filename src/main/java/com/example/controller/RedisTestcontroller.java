package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisTestcontroller {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public String getsource(){
        redisTemplate.opsForValue().set("name","lucy");
        String name=(String)redisTemplate.opsForValue().get("name");
        System.out.println(name);
        return name;
    }
}
