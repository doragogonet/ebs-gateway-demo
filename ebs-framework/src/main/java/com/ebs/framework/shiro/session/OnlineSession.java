package com.ebs.framework.shiro.session;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.session.mgt.SimpleSession;
import com.ebs.common.enums.OnlineStatus;


public class OnlineSession extends SimpleSession
{
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String loginName;

    private String deptName;
	
	private String avatar;

    private String host;

    private String browser;

    private String os;

    private OnlineStatus status = OnlineStatus.on_line;

    private transient boolean attributeChanged = false;

    @Override
    public String getHost()
    {
        return host;
    }

    @Override
    public void setHost(String host)
    {
        this.host = host;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public OnlineStatus getStatus()
    {
        return status;
    }

    public void setStatus(OnlineStatus status)
    {
        this.status = status;
    }

    public void markAttributeChanged()
    {
        this.attributeChanged = true;
    }

    public void resetAttributeChanged()
    {
        this.attributeChanged = false;
    }

    public boolean isAttributeChanged()
    {
        return attributeChanged;
    }

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
    @Override
    public void setAttribute(Object key, Object value)
    {
        super.setAttribute(key, value);
    }

    @Override
    public Object removeAttribute(Object key)
    {
        return super.removeAttribute(key);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("loginName", getLoginName())
            .append("deptName", getDeptName())
            .append("avatar", getAvatar())
            .append("host", getHost())
            .append("browser", getBrowser())
            .append("os", getOs())
            .append("status", getStatus())
            .append("attributeChanged", isAttributeChanged())
            .toString();
    }
}
