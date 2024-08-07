package com.ebs.common.utils;

import java.util.Iterator;
import java.util.Set;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ebs.common.utils.spring.SpringUtils;


public class CacheUtils
{
    private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    private static CacheManager cacheManager = SpringUtils.getBean(CacheManager.class);

    private static final String SYS_CACHE = "sys-cache";


    public static Object get(String key)
    {
        return get(SYS_CACHE, key);
    }


    public static Object get(String key, Object defaultValue)
    {
        Object value = get(key);
        return value != null ? value : defaultValue;
    }


    public static void put(String key, Object value)
    {
        put(SYS_CACHE, key, value);
    }


    public static void remove(String key)
    {
        remove(SYS_CACHE, key);
    }


    public static Object get(String cacheName, String key)
    {
        return getCache(cacheName).get(getKey(key));
    }


    public static Object get(String cacheName, String key, Object defaultValue)
    {
        Object value = get(cacheName, getKey(key));
        return value != null ? value : defaultValue;
    }


    public static void put(String cacheName, String key, Object value)
    {
        getCache(cacheName).put(getKey(key), value);
    }


    public static void remove(String cacheName, String key)
    {
        getCache(cacheName).remove(getKey(key));
    }


    public static void removeAll(String cacheName)
    {
        Cache<String, Object> cache = getCache(cacheName);
        Set<String> keys = cache.keys();
        for (Iterator<String> it = keys.iterator(); it.hasNext();)
        {
            cache.remove(it.next());
        }
        logger.info("キャッシュクリーンアップ： {} => {}", cacheName, keys);
    }


    public static void removeByKeys(Set<String> keys)
    {
        removeByKeys(SYS_CACHE, keys);
    }


    public static void removeByKeys(String cacheName, Set<String> keys)
    {
        for (Iterator<String> it = keys.iterator(); it.hasNext();)
        {
            remove(it.next());
        }
        logger.info("キャッシュクリーンアップ： {} => {}", cacheName, keys);
    }


    private static String getKey(String key)
    {
        return key;
    }


    public static Cache<String, Object> getCache(String cacheName)
    {
        Cache<String, Object> cache = cacheManager.getCache(cacheName);
        if (cache == null)
        {
            throw new RuntimeException("キャッシュ「" + cacheName + "」が定義されていません");
        }
        return cache;
    }


    public static String[] getCacheNames()
    {
        return ((EhCacheManager) cacheManager).getCacheManager().getCacheNames();
    }
}
