package com.ebs.quartz.mapper;

import com.ebs.quartz.domain.SysJob;
import java.util.List;

/**
 * スケジュールタスク
 * 
 * @author ebs
 */
public interface SysJobMapper
{

    public List<SysJob> selectJobList(SysJob job);

  
    public List<SysJob> selectJobAll();

  
    public SysJob selectJobById(Long jobId);

    public int deleteJobById(Long jobId);

 
    public int deleteJobByIds(Long[] ids);

    public int updateJob(SysJob job);

 
    public int insertJob(SysJob job);
}
