package com.ebs.system.mapper;

import java.util.List;
import com.ebs.system.domain.SysUserOnline;

/**
 * オンラインユーザー
 * 
 * @author ebs
 */
public interface SysUserOnlineMapper
{
   
    public SysUserOnline selectOnlineById(String sessionId);

    public int deleteOnlineById(String sessionId);

    public int saveOnline(SysUserOnline online);

    public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline);

    public List<SysUserOnline> selectOnlineByExpired(String lastAccessTime);
}
