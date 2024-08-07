package com.ebs.quartz.mapper;

import com.ebs.quartz.domain.SysJobLog;
import java.util.List;

/**
 * タスクログ
 * 
 * @author ebs
 */
public interface SysJobLogMapper
{
  
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog);

    public List<SysJobLog> selectJobLogAll();

    public SysJobLog selectJobLogById(Long jobLogId);

   
    public int insertJobLog(SysJobLog jobLog);

   
    public int deleteJobLogByIds(String[] ids);

    public int deleteJobLogById(Long jobId);

    public void cleanJobLog();
}
