package com.ebs.system.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import com.ebs.common.utils.spring.SpringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebs.common.constant.ShiroConstants;
import com.ebs.common.utils.DateUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.system.domain.SysUserOnline;
import com.ebs.system.mapper.SysUserOnlineMapper;
import com.ebs.system.service.ISysUserOnlineService;

/**
 * オンラインユーザー
 * 
 * @author ebs
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService
{
    @Autowired
    private SysUserOnlineMapper userOnlineDao;

   
    @Override
    public SysUserOnline selectOnlineById(String sessionId)
    {
        return userOnlineDao.selectOnlineById(sessionId);
    }

    @Override
    public void deleteOnlineById(String sessionId)
    {
        SysUserOnline userOnline = selectOnlineById(sessionId);
        if (StringUtils.isNotNull(userOnline))
        {
            userOnlineDao.deleteOnlineById(sessionId);
        }
    }

    @Override
    public void batchDeleteOnline(List<String> sessions)
    {
        for (String sessionId : sessions)
        {
            SysUserOnline userOnline = selectOnlineById(sessionId);
            if (StringUtils.isNotNull(userOnline))
            {
                userOnlineDao.deleteOnlineById(sessionId);
            }
        }
    }

    @Override
    public void saveOnline(SysUserOnline online)
    {
        userOnlineDao.saveOnline(online);
    }

    @Override
    public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline)
    {
        return userOnlineDao.selectUserOnlineList(userOnline);
    }

    @Override
    public void forceLogout(String sessionId)
    {
        userOnlineDao.deleteOnlineById(sessionId);
    }

    @Override
    public void removeUserCache(String loginName, String sessionId)
    {
        EhCacheManager ehCacheManager = SpringUtils.getBean(EhCacheManager.class);
        Cache<String, Deque<Serializable>> cache = ehCacheManager.getCache(ShiroConstants.SYS_USERCACHE);
        Deque<Serializable> deque = cache.get(loginName);
        if (StringUtils.isEmpty(deque) || deque.size() == 0)
        {
            return;
        }
        deque.remove(sessionId);
    }

    @Override
    public List<SysUserOnline> selectOnlineByExpired(Date expiredDate)
    {
        String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
        return userOnlineDao.selectOnlineByExpired(lastAccessTime);
    }
}
