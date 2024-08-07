package com.ebs.framework.manager.factory;

import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ebs.common.constant.Constants;
import com.ebs.common.utils.AddressUtils;
import com.ebs.common.utils.LogUtils;
import com.ebs.common.utils.ServletUtils;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.common.utils.spring.SpringUtils;
import com.ebs.framework.shiro.session.OnlineSession;
import com.ebs.system.domain.SysLogininfor;
import com.ebs.system.domain.SysOperLog;
import com.ebs.system.domain.SysUserOnline;
import com.ebs.system.service.ISysOperLogService;
import com.ebs.system.service.ISysUserOnlineService;
import com.ebs.system.service.impl.SysLogininforServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;


public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

   
    public static TimerTask syncSessionToDb(final OnlineSession session)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                SysUserOnline online = new SysUserOnline();
                online.setSessionId(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus(session.getStatus());
                SpringUtils.getBean(ISysUserOnlineService.class).saveOnline(online);

            }
        };
    }

    
    public static TimerTask recordOper(final SysOperLog operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
            
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }

    
    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();
        return new TimerTask()
        {
            @Override
            public void run()
            {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
               
                sys_user_logger.info(s.toString(), args);
            
                String os = userAgent.getOperatingSystem().getName();
              
                String browser = userAgent.getBrowser().getName();
              
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
              
                if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER))
                {
                    logininfor.setStatus(Constants.SUCCESS);
                }
                else if (Constants.LOGIN_FAIL.equals(status))
                {
                    logininfor.setStatus(Constants.FAIL);
                }
           
                SpringUtils.getBean(SysLogininforServiceImpl.class).insertLogininfor(logininfor);
            }
        };
    }
}
