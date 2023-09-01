package com.example.service.imp;

import com.example.MyUtils.JedisUtil.JedisPoolFactory;
import com.example.service.secKillservice;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;


@Service
public class secKillserviceimpl implements secKillservice {

    @Override
    public int trysecKill(String uid) {

        //获取jedis对象
        Jedis jedis= JedisPoolFactory.getJedis();
        //0-uid为空, 1-秒杀未开始, 2-重复秒杀了, 3-秒杀已结束, 4-秒杀成功
        //拼接key
        String userKey="sk:product_id:user";
        //监视product_id
        jedis.watch("product_id");
        //判断uid是否为空
        if(uid==null) {
            jedis.close();
            return 0;
        }
        //判断秒杀是否开始
        if(jedis.get("product_id")==null) {
            jedis.close();
            return 1;
        }
        //判断重复秒杀
        if(jedis.sismember(userKey,uid)) {
            jedis.close();
            return 2;
        }
        //判断是否已经没有库存了
        if(Integer.parseInt(jedis.get("product_id"))<1) {
            jedis.close();
            return 3;
        }


        //生成事务
        Transaction multi = jedis.multi();
        //模拟秒杀
        multi.decr("product_id");
        multi.sadd(userKey,uid);
        //提交事务
        List<Object> Results = multi.exec();
        if(Results==null|| Results.isEmpty()){
            jedis.close();
            return 3;
        }
        jedis.close();
        return 4;
    }
}
