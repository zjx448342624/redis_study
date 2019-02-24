package com.zjxedu.redis.study;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

public class LuaDemo {

    public static void main(String[] args) {

        Jedis jedis = RedisManager.getJedis();

        String lua = "local num=redis.call(\"incr\",KEYS[1])\n" +
                "if tonumber(num) == 1 then\n" +
                " redis.call(\"expire\",KEYS[1],ARGV[1])\n" +
                " return 1\n" +
                "else\n" +
                "return 0\n" +
                "end\n";


        List<String> keys = Arrays.asList("phone:limit:1888888881");
        List<String> values = Arrays.asList("60");
        //直接调用lua脚本
        /*Object obj = jedis.eval(lua,keys,values);*/

        //在redis 生成一个摘要，如果已经执行过就可以直接调用该摘要了，避免网络传输大量的字符串
        // redis中会缓存lua脚本
        Object evalObj = jedis.evalsha(jedis.scriptLoad(lua), keys, values);
        System.out.println(evalObj);


    }
}
