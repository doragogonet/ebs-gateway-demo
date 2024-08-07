package com.ebs.quartz.service;

import java.util.List;
import com.ebs.quartz.domain.SysJobLog;

public interface ISysJobLogService
{
  
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog);

 
    public SysJobLog selectJobLogById(Long jobLogId);

    public void addJobLog(SysJobLog jobLog);

    public int deleteJobLogByIds(String ids);

    public int deleteJobLogById(Long jobId);
   
    public void cleanJobLog();
}
