package com.example.MyUtils.ClusterUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public final class ClusterUtil {
    private static JedisCluster jedisCluster;
    private static HostAndPort hostAndPort;
    static {
        hostAndPort = new HostAndPort("192.168.37.130",6379);
        jedisCluster=new JedisCluster(hostAndPort);
    }

    public static JedisCluster getCluster(){
        return jedisCluster;
    }

}
