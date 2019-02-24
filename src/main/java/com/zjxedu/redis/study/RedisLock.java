package com.zjxedu.redis.study;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisLock {

    public String getLock(String key, int timeout) {
        try {
            Jedis jedis = RedisManager.getJedis();
            String value = UUID.randomUUID().toString();

            long end = System.currentTimeMillis() + timeout;
            //设置死循环对程序不好，可以设置一个过期时间
            while (System.currentTimeMillis() < end) {
                if (1 == jedis.setnx(key, value)) {
                    //设置锁的过期时间
                    jedis.expire(key,timeout);

                    //设置成功，redis操作成功
                    return value;
                }
                //判断jedis是否有过期时间，如果没有-1
                if(jedis.ttl(key)  == -1){
                    jedis.expire(key,timeout);
                }

                // 1秒操作一次
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public boolean releaseLock(String key, String value) {

        //通过获取一个key的值，判断是否成功
        try {
            Jedis jedis = RedisManager.getJedis();
            while (true) {
                //watch操作,监控一个或者多个key，如果key发生变化，事物就不会执行
                //防止其他地方更改对key发生影响
                String watch = jedis.watch(key);
                //确定当前的释放的锁的value和reids中存的是同一个
                if (value.equals(jedis.get(key))) {

                    Transaction transaction = jedis.multi();

                    transaction.del(key);

                    List<Object> list = transaction.exec();
                    if (null == list) {
                        continue;
                    }

                    return true;
                }
                jedis.unwatch();
                break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {

        RedisLock lock = new RedisLock();


        String lockId = lock.getLock("lock:aaa", 1000);

        if(null != lockId) {
            System.out.println("ok" + lockId);
        }

        String lockId2 = lock.getLock("lock:aaa", 5000);
        System.out.println(lockId2);

    }

}
