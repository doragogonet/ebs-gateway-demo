package com.ebs.framework.shiro.web.filter.kickout;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ebs.common.constant.ShiroConstants;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.utils.ServletUtils;
import com.ebs.common.utils.ShiroUtils;


public class KickoutSessionFilter extends AccessControlFilter
{
    private final static ObjectMapper objectMapper = new ObjectMapper();

   
    private int maxSession = -1;

   
    private Boolean kickoutAfter = false;

   
    private String kickoutUrl;

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o)
            throws Exception
    {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered() || maxSession == -1)
        {
            
            return true;
        }
        try
        {
            Session session = subject.getSession();
          
            SysUser user = ShiroUtils.getSysUser();
            String loginName = user.getLoginName();
            Serializable sessionId = session.getId();

            
            Deque<Serializable> deque = cache.get(loginName);
            if (deque == null)
            {
                
                deque = new ArrayDeque<Serializable>();
            }

           
            if (!deque.contains(sessionId) && session.getAttribute("kickout") == null)
            {
               
                deque.push(sessionId);
                
                cache.put(loginName, deque);
            }

           
            while (deque.size() > maxSession)
            {
               
                Serializable kickoutSessionId = kickoutAfter ? deque.removeFirst() : deque.removeLast();
               
                cache.put(loginName, deque);

                try
                {
                   
                    Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                    if (null != kickoutSession)
                    {
                      
                        kickoutSession.setAttribute("kickout", true);
                    }
                }
                catch (Exception e)
                {
                    
                }
            }

            
            if (session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout") == true)
            {
               
                subject.logout();
                saveRequest(request);
                return isAjaxResponse(request, response);
            }
            return true;
        }
        catch (Exception e)
        {
            return isAjaxResponse(request, response);
        }
    }

    private boolean isAjaxResponse(ServletRequest request, ServletResponse response) throws IOException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (ServletUtils.isAjaxRequest(req))
        {
            AjaxResult ajaxResult = AjaxResult.error("他の場所にログインしています。パスワードを変更するか、再ログインしてください。");
            ServletUtils.renderString(res, objectMapper.writeValueAsString(ajaxResult));
        }
        else
        {
            WebUtils.issueRedirect(request, response, kickoutUrl);
        }
        return false;
    }

    public void setMaxSession(int maxSession)
    {
        this.maxSession = maxSession;
    }

    public void setKickoutAfter(boolean kickoutAfter)
    {
        this.kickoutAfter = kickoutAfter;
    }

    public void setKickoutUrl(String kickoutUrl)
    {
        this.kickoutUrl = kickoutUrl;
    }

    public void setSessionManager(SessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }

   
    public void setCacheManager(CacheManager cacheManager)
    {
      
        this.cache = cacheManager.getCache(ShiroConstants.SYS_USERCACHE);
    }
}