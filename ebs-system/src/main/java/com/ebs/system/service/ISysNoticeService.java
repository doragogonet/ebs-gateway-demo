package com.ebs.system.service;

import java.util.List;
import com.ebs.system.domain.SysNotice;

/**
 * 公告
 * 
 * @author ebs
 */
public interface ISysNoticeService
{
   
    public SysNotice selectNoticeById(Long noticeId);

    public List<SysNotice> selectNoticeList(SysNotice notice);

    public int insertNotice(SysNotice notice);

    public int updateNotice(SysNotice notice);

    public int deleteNoticeByIds(String ids);
}
