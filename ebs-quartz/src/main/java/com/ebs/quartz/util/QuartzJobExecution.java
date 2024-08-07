package com.ebs.quartz.util;

import org.quartz.JobExecutionContext;
import com.ebs.quartz.domain.SysJob;


public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
