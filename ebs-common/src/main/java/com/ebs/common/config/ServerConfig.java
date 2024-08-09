package com.ebs.common.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.ebs.common.utils.ServletUtils;

@Component
public class ServerConfig
{
   
    public String getUrl()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }
}