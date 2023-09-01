package com.example;

import com.example.MyUtils.ClusterUtils.ClusterUtil;
import redis.clients.jedis.JedisCluster;

public class testCluster {
    public static void main(String[] args) {
        //1.创建对象,可以写成工具类
        JedisCluster jedisCluster= ClusterUtil.getCluster();
        //使用jedis
        jedisCluster.set("a","b");
        System.out.println(jedisCluster.get("a"));
    }
}
