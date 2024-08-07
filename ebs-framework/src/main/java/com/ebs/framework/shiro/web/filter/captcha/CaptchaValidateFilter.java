package com.ebs.framework.shiro.web.filter.captcha;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.web.filter.AccessControlFilter;
import com.google.code.kaptcha.Constants;
import com.ebs.common.constant.ShiroConstants;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;


public class CaptchaValidateFilter extends AccessControlFilter
{
   
    private boolean captchaEnabled = false;

   
    private String captchaType = "math";

    public void setCaptchaEnabled(boolean captchaEnabled)
    {
        this.captchaEnabled = captchaEnabled;
    }

    public void setCaptchaType(String captchaType)
    {
        this.captchaType = captchaType;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception
    {
        request.setAttribute(ShiroConstants.CURRENT_ENABLED, captchaEnabled);
        request.setAttribute(ShiroConstants.CURRENT_TYPE, captchaType);
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
       
        if (captchaEnabled == false || !"post".equals(httpServletRequest.getMethod().toLowerCase()))
        {
            return true;
        }
        return validateResponse(httpServletRequest, httpServletRequest.getParameter(ShiroConstants.CURRENT_VALIDATECODE));
    }

    public boolean validateResponse(HttpServletRequest request, String validateCode)
    {
        Object obj = ShiroUtils.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String code = String.valueOf(obj != null ? obj : "");
    
        request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isEmpty(validateCode) || !validateCode.equalsIgnoreCase(code))
        {
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        request.setAttribute(ShiroConstants.CURRENT_CAPTCHA, ShiroConstants.CAPTCHA_ERROR);
        return true;
    }
}
