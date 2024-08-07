package com.ebs.quartz.task;

import org.springframework.stereotype.Component;
import com.ebs.common.utils.StringUtils;

/**
 * スケジュールタスクスケジューリングテスト
 * 
 * @author ebs
 */
@Component("ryTask")
public class RyTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        
    }

    public void ryParams(String params)
    {
    }

    public void ryNoParams()
    {
    }
}
