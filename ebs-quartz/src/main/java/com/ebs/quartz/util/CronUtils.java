package com.ebs.quartz.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.quartz.CronExpression;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import com.ebs.common.utils.DateUtils;

public class CronUtils
{
 
    public static boolean isValid(String cronExpression)
    {
        return CronExpression.isValidExpression(cronExpression);
    }

    public static String getInvalidMessage(String cronExpression)
    {
        try
        {
            new CronExpression(cronExpression);
            return null;
        }
        catch (ParseException pe)
        {
            return pe.getMessage();
        }
    }

    public static Date getNextExecution(String cronExpression)
    {
        try
        {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static List<String> getRecentTriggerTime(String cron)
    {
        List<String> list = new ArrayList<String>();
        try
        {
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            cronTriggerImpl.setCronExpression(cron);
            List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 10);
            for (Date date : dates)
            {
                list.add(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, date));
            }
        }
        catch (ParseException e)
        {
            return null;
        }
        return list;
    }
}
