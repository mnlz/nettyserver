package com.fre.nettyserversemo.service.serviceImpl;

import com.fre.nettyserversemo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl  implements RedisService{
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(String key, Object value) {
        boolean result = false;
        try{
            ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    @Override
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public void remove(String... value) {

    }

    @Override
    public void removePattern(String pattern) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public boolean exists(String key) {
        return false;
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;

    }

    /**
     * 存入哈希
     */
    @Override
    public void hmSet(String key,Object hashKey,Object value,Long expireTime) {
        HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
        redisTemplate.expire(key,expireTime,TimeUnit.SECONDS);
    }

    @Override
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    @Override
    public Object hmget(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }
}
