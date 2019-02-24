package com.zjxedu.redis.study;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {

    private static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.177.131",6379);
    }

    public static Jedis getJedis(){
        if( null != jedisPool){
            return jedisPool.getResource();
        }
        throw new RuntimeException("jedispool is null");
    }

}
