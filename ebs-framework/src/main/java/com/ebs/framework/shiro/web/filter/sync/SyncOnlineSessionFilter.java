package com.ebs.framework.shiro.web.filter.sync;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.filter.PathMatchingFilter;
import com.ebs.common.constant.ShiroConstants;
import com.ebs.framework.shiro.session.OnlineSession;
import com.ebs.framework.shiro.session.OnlineSessionDAO;


public class SyncOnlineSessionFilter extends PathMatchingFilter
{
    private OnlineSessionDAO onlineSessionDAO;

   
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception
    {
        OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
       
        if (session != null && session.getUserId() != null && session.getStopTimestamp() == null)
        {
            onlineSessionDAO.syncToDb(session);
        }
        return true;
    }

    public void setOnlineSessionDAO(OnlineSessionDAO onlineSessionDAO)
    {
        this.onlineSessionDAO = onlineSessionDAO;
    }
}
