package com.example.controller;

import com.example.MyUtils.CodeUtils.codeUtils;
import com.example.service.secKillservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill")
public class secKillcontroller {

    @Autowired
    secKillservice seckillservice;


    @GetMapping
    public void seckill(){
        //0-uid为空, 1-秒杀未开始, 2-重复秒杀了, 3-秒杀已结束, 4-秒杀成功
        String uid = codeUtils.getCode(6);
        int ans=seckillservice.trysecKill(uid);
        switch (ans) {
            case 0 -> System.out.println("uid为空");
            case 1 -> System.out.println("秒杀未开始");
            case 2 -> System.out.println("重复秒杀");
            case 3 -> System.out.println("秒杀已结束");
            case 4 -> System.out.println("秒杀成功");
        }
    }

}
