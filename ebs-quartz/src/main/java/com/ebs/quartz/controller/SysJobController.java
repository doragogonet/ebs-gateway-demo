package com.ebs.quartz.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebs.common.annotation.Log;
import com.ebs.common.constant.Constants;
import com.ebs.common.core.controller.BaseController;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.core.page.TableDataInfo;
import com.ebs.common.enums.BusinessType;
import com.ebs.common.exception.job.TaskException;
import com.ebs.common.utils.StringUtils;
import com.ebs.common.utils.poi.ExcelUtil;
import com.ebs.quartz.domain.SysJob;
import com.ebs.quartz.service.ISysJobService;
import com.ebs.quartz.util.CronUtils;
import com.ebs.quartz.util.ScheduleUtils;

/**
 * スケジュールタスク
 * 
 * @author ebs
 */
@Controller
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController
{
    private String prefix = "monitor/job";

    @Autowired
    private ISysJobService jobService;

    @RequiresPermissions("monitor:job:view")
    @GetMapping()
    public String job()
    {
        return prefix + "/job";
    }

    @RequiresPermissions("monitor:job:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysJob job)
    {
        startPage();
        List<SysJob> list = jobService.selectJobList(job);
        return getDataTable(list);
    }

    @Log(title = "タイミングタスク", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:job:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysJob job)
    {
        List<SysJob> list = jobService.selectJobList(job);
        ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
        return util.exportExcel(list, "タイミングタスク");
    }

    @Log(title = "タイミングタスク", businessType = BusinessType.DELETE)
    @RequiresPermissions("monitor:job:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) throws SchedulerException
    {
        jobService.deleteJobByIds(ids);
        return success();
    }

    @RequiresPermissions("monitor:job:detail")
    @GetMapping("/detail/{jobId}")
    public String detail(@PathVariable("jobId") Long jobId, ModelMap mmap)
    {
        mmap.put("name", "job");
        mmap.put("job", jobService.selectJobById(jobId));
        return prefix + "/detail";
    }

    @Log(title = "タイミングタスク", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:job:changeStatus")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysJob job) throws SchedulerException
    {
        SysJob newJob = jobService.selectJobById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return toAjax(jobService.changeStatus(newJob));
    }

    @Log(title = "タイミングタスク", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:job:changeStatus")
    @PostMapping("/run")
    @ResponseBody
    public AjaxResult run(SysJob job) throws SchedulerException
    {
        boolean result = jobService.run(job);
        return result ? success() : error("タスクが存在しないか期限切れです");
    }

    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }


    @Log(title = "タイミングタスク", businessType = BusinessType.INSERT)
    @RequiresPermissions("monitor:job:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysJob job) throws SchedulerException, TaskException
    {
        if (!CronUtils.isValid(job.getCronExpression()))
        {
            return error("新規タスク'" + job.getJobName() + "'失敗、Cron式が正しくありません");
        }
        else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI))
        {
            return error("新規タスク'" + job.getJobName() + "'失敗、'rmi'コール不可");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS }))
        {
            return error("新規タスク'" + job.getJobName() + "'失敗、ldap(s)'コール不可");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.HTTP, Constants.HTTPS }))
        {
            return error("新規タスク'" + job.getJobName() + "'失敗、'http(s)'コール不可");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR))
        {
            return error("新規タスク'" + job.getJobName() + "'失敗、ターゲット文字列に違反があります");
        }
        else if (!ScheduleUtils.whiteList(job.getInvokeTarget()))
        {
            return error("新規タスク'" + job.getJobName() + "'失敗、ターゲット文字列はホワイトリストにありません");
        }
        job.setCreateBy(getLoginName());
        return toAjax(jobService.insertJob(job));
    }


    @RequiresPermissions("monitor:job:edit")
    @GetMapping("/edit/{jobId}")
    public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap)
    {
        mmap.put("job", jobService.selectJobById(jobId));
        return prefix + "/edit";
    }


    @Log(title = "タイミングタスク", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:job:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysJob job) throws SchedulerException, TaskException
    {
        if (!CronUtils.isValid(job.getCronExpression()))
        {
            return error("タスク変更'" + job.getJobName() + "'失敗、Cron式が正しくありません");
        }
        else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI))
        {
            return error("タスク変更''" + job.getJobName() + "'失敗、'rmi'コール不可");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS }))
        {
            return error("タスク変更''" + job.getJobName() + "'失敗、'ldap'コール不可");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.HTTP, Constants.HTTPS }))
        {
            return error("タスク変更''" + job.getJobName() + "'失敗、'http(s)'コール不可");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR))
        {
            return error("タスク変更''" + job.getJobName() + "'失敗、ターゲット文字列に違反があります");
        }
        else if (!ScheduleUtils.whiteList(job.getInvokeTarget()))
        {
            return error("タスク変更''" + job.getJobName() + "'失敗、ターゲット文字列はホワイトリストにありません");
        }
        return toAjax(jobService.updateJob(job));
    }

    @PostMapping("/checkCronExpressionIsValid")
    @ResponseBody
    public boolean checkCronExpressionIsValid(SysJob job)
    {
        return jobService.checkCronExpressionIsValid(job.getCronExpression());
    }


    @GetMapping("/cron")
    public String cron()
    {
        return prefix + "/cron";
    }

    @GetMapping("/queryCronExpression")
    @ResponseBody
    public AjaxResult queryCronExpression(@RequestParam(value = "cronExpression", required = false) String cronExpression)
    {
        if (jobService.checkCronExpressionIsValid(cronExpression))
        {
            List<String> dateList = CronUtils.getRecentTriggerTime(cronExpression);
            return success(dateList);
        }
        else
        {
            return error("無効な式");
        }
    }
}
