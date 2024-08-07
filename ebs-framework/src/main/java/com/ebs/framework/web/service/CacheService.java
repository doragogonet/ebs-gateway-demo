package com.ebs.framework.web.service;

import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import com.ebs.common.constant.Constants;
import com.ebs.common.utils.CacheUtils;


@Service
public class CacheService
{

    public String[] getCacheNames()
    {
        String[] cacheNames = CacheUtils.getCacheNames();
        return ArrayUtils.removeElement(cacheNames, Constants.SYS_AUTH_CACHE);
    }


    public Set<String> getCacheKeys(String cacheName)
    {
        return CacheUtils.getCache(cacheName).keys();
    }


    public Object getCacheValue(String cacheName, String cacheKey)
    {
        return CacheUtils.get(cacheName, cacheKey);
    }


    public void clearCacheName(String cacheName)
    {
        CacheUtils.removeAll(cacheName);
    }

    public void clearCacheKey(String cacheName, String cacheKey)
    {
        CacheUtils.remove(cacheName, cacheKey);
    }


    public void clearAll()
    {
        String[] cacheNames = getCacheNames();
        for (String cacheName : cacheNames)
        {
            CacheUtils.removeAll(cacheName);
        }
    }
}
