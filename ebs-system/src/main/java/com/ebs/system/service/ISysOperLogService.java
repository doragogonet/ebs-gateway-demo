package com.ebs.system.service;

import java.util.List;
import com.ebs.system.domain.SysOperLog;

/**
 * 操作ログ
 * 
 * @author ebs
 */
public interface ISysOperLogService
{
 
    public void insertOperlog(SysOperLog operLog);

   
    public List<SysOperLog> selectOperLogList(SysOperLog operLog);

    public int deleteOperLogByIds(String ids);

    public SysOperLog selectOperLogById(Long operId);

    public void cleanOperLog();
}
