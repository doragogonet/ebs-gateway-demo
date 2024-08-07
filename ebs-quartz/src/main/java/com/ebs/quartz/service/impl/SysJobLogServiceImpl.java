package com.ebs.quartz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebs.common.core.text.Convert;
import com.ebs.quartz.domain.SysJobLog;
import com.ebs.quartz.mapper.SysJobLogMapper;
import com.ebs.quartz.service.ISysJobLogService;


@Service
public class SysJobLogServiceImpl implements ISysJobLogService
{
    @Autowired
    private SysJobLogMapper jobLogMapper;

   
    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog)
    {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    @Override
    public SysJobLog selectJobLogById(Long jobLogId)
    {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    @Override
    public void addJobLog(SysJobLog jobLog)
    {
        jobLogMapper.insertJobLog(jobLog);
    }

    @Override
    public int deleteJobLogByIds(String ids)
    {
        return jobLogMapper.deleteJobLogByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteJobLogById(Long jobId)
    {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    @Override
    public void cleanJobLog()
    {
        jobLogMapper.cleanJobLog();
    }
}
