package com.wsf.generator.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * redis工具类
 * @author wsf
 */
public class RedissonUtils {

    private static RedissonClient redissonClient = ApplicationContextUtils.getBean(RedissonClient.class);

    public static void putObject(String key, Object value) {
        RedissonUtils.redissonClient.getBucket(key).set(value);
    }

    public static void putObject(String mapKey, Object key, Object value) {
        RedissonUtils.redissonClient.getMap(mapKey).put(key, value);
    }

    public static Object getObject(String key) {
        return RedissonUtils.redissonClient.getBucket(key).get();
    }

    public static Object getObject(String mapKey, Object key) {
        return RedissonUtils.redissonClient.getMap(mapKey).get(key);
    }

    public static RLock lock(String lockKey) {
        return RedissonUtils.redissonClient.getLock(lockKey);
    }
}
