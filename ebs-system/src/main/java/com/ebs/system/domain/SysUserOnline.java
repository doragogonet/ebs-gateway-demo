package com.ebs.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ebs.common.core.domain.BaseEntity;
import com.ebs.common.enums.OnlineStatus;

/**
 * オンラインユーザーレコード sys_user_online
 * 
 * @author ebs
 */
public class SysUserOnline extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    
    /** セッションiid */
    private String sessionId;

    /** 部署名 */
    private String deptName;

    /** ログインID */
    private String loginName;

    /** ログインIP */
    private String ipaddr;

    /** ログイン場所 */
    private String loginLocation;

    /** ブラウザタイプ */
    private String browser;

    /** OS */
    private String os;

    /** session作成時間 */
    private Date startTimestamp;

    /** session最終アクセス時間 */
    private Date lastAccessTime;

    /** タイムアウト時間、分単位 */
    private Long expireTime;

    /** オンライン状態 */
    private OnlineStatus status = OnlineStatus.on_line;

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation()
    {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation)
    {
        this.loginLocation = loginLocation;
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

    public Date getStartTimestamp()
    {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp)
    {
        this.startTimestamp = startTimestamp;
    }

    public Date getLastAccessTime()
    {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime)
    {
        this.lastAccessTime = lastAccessTime;
    }

    public Long getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Long expireTime)
    {
        this.expireTime = expireTime;
    }

    public OnlineStatus getStatus()
    {
        return status;
    }

    public void setStatus(OnlineStatus status)
    {
        this.status = status;
    }
    
	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sessionId", getSessionId())
            .append("loginName", getLoginName())
            .append("deptName", getDeptName())
            .append("ipaddr", getIpaddr())
            .append("loginLocation", getLoginLocation())
            .append("browser", getBrowser())
            .append("os", getOs())
            .append("status", getStatus())
            .append("startTimestamp", getStartTimestamp())
            .append("lastAccessTime", getLastAccessTime())
            .append("expireTime", getExpireTime())
            .toString();
    }
}
