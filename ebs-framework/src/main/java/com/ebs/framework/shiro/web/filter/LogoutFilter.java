package com.ebs.framework.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ebs.common.constant.Constants;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.utils.MessageUtils;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.common.utils.spring.SpringUtils;
import com.ebs.framework.manager.AsyncManager;
import com.ebs.framework.manager.factory.AsyncFactory;
import com.ebs.system.service.ISysUserOnlineService;


public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter
{
    private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);

   
    private String loginUrl;

    public String getLoginUrl()
    {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl)
    {
        this.loginUrl = loginUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {
        try
        {
            Subject subject = getSubject(request, response);
            String redirectUrl = getRedirectUrl(request, response, subject);
            try
            {
                SysUser user = ShiroUtils.getSysUser();
                if (StringUtils.isNotNull(user))
                {
                    String loginName = user.getLoginName();
                   
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGOUT, MessageUtils.message("user.logout.success")));
                    
                    SpringUtils.getBean(ISysUserOnlineService.class).removeUserCache(loginName, ShiroUtils.getSessionId());
                }
              
                subject.logout();
            }
            catch (SessionException ise)
            {
                log.error("logout fail.", ise);
            }
            issueRedirect(request, response, redirectUrl);
        }
        catch (Exception e)
        {
            log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
        }
        return false;
    }

    /**
     * 退出跳转URL
     */
    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject)
    {
        String url = getLoginUrl();
        if (StringUtils.isNotEmpty(url))
        {
            return url;
        }
        return super.getRedirectUrl(request, response, subject);
    }
}
