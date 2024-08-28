package com.fre.nettyserversemo.service;

public interface RedisService {
    boolean set(String key,Object value);
    boolean set(String key,Object value,Long expireTime);
    void remove(String... value);

    /**
     * 批量删除key
     * @param pattern
     */
    void removePattern(String pattern);

    /**
     * 删除对应的value
     * @param key
     */
    void remove(String key);

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 读取缓存
     * @param key
     * @return
     */
    Object get(String key);
    /**
     * 哈希添加
     */
    void hmSet(String key,Object hashKey,Object value,Long expireTime);
    /**
     * 哈希添加
     */
    void hmSet(String key,Object hashKey,Object value);
    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    Object hmget(String key,Object hashKey);

}
