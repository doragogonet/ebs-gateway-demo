package com.ebs.system.service;

import java.util.Date;
import java.util.List;
import com.ebs.system.domain.SysUserOnline;

/**
 * オンラインユーザー
 * 
 * @author ebs
 */
public interface ISysUserOnlineService
{
   
    public SysUserOnline selectOnlineById(String sessionId);

    public void deleteOnlineById(String sessionId);

    public void batchDeleteOnline(List<String> sessions);

    public void saveOnline(SysUserOnline online);

    public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline);

    public void forceLogout(String sessionId);

    public void removeUserCache(String loginName, String sessionId);

    public List<SysUserOnline> selectOnlineByExpired(Date expiredDate);
}
