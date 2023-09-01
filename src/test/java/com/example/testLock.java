package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class testLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //redis中的分布式锁是通过setnx这个命令实现的。可以设置过期时间
    //通过del删除数据来释放锁
    @Test
    public void testlock(){
        //获取锁
        boolean lock=redisTemplate.opsForValue().setIfAbsent("lock","true");
        if(lock){
            Object num = redisTemplate.opsForValue().get("num");
            if(num==null){
                return;
            }
            int Num=Integer.parseInt(num+"");
            //让num++
            redisTemplate.opsForValue().increment("num",++Num);
            //释放锁
            redisTemplate.delete("lock");
        }
        else{
            try{
                Thread.sleep(100);
                testlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test(){
        redisTemplate.delete("lock");
    }
}
