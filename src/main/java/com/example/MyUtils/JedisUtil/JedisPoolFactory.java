package com.example.MyUtils.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public class JedisPoolFactory {

    private JedisPoolFactory(){}
    private static final redis.clients.jedis.JedisPool jedispool;

    static{
        //连接池配置
        JedisPoolConfig jedisConfigaration=new JedisPoolConfig();
        jedisConfigaration.setMaxTotal(200);  //设置最大连接数
        jedisConfigaration.setMaxIdle(32);  //设置最大空闲连接数
        jedisConfigaration.setMinIdle(8);  //设置最小空闲连接数
        jedisConfigaration.setMaxWait(Duration.ofMillis(1000));
        //连接数据库
        jedispool=new redis.clients.jedis.JedisPool(jedisConfigaration,"192.168.37.129",6379,1000,"123456");
    }
    public static Jedis getJedis(){
        return jedispool.getResource();   //单例模式
    }
}
