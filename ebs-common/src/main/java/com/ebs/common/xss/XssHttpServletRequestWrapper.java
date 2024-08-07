package com.ebs.common.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import com.ebs.common.utils.html.EscapeUtil;


public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper
{
    /**
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request)
    {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name)
    {
        String[] values = super.getParameterValues(name);
        if (values != null)
        {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++)
            {
                
                escapseValues[i] = EscapeUtil.clean(values[i]).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }
}