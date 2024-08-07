package com.ebs.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;
import com.ebs.common.annotation.Excel;
import com.ebs.common.annotation.Excel.ColumnType;
import com.ebs.common.core.domain.BaseEntity;

/**
 * システムアクセスレコード sys_logininfor
 * 
 * @author ebs
 */
public class SysLogininfor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "ID", cellType = ColumnType.NUMERIC)
    private Long infoId;

    /** 用户账号 */
    @Excel(name = "ログインID")
    private String loginName;

    /** ログインステータス 0成功 1失敗 */
    @Excel(name = "ログインステータス", readConverterExp = "0=成功,1=失敗")
    private String status;

    /** ログインIP */
    @Excel(name = "ログインIP")
    private String ipaddr;

    /** ログイン場所 */
    @Excel(name = "ログイン場所")
    private String loginLocation;

    /** ブラウザタイプ */
    @Excel(name = "ブラウザ")
    private String browser;

    /** OS */
    @Excel(name = "OS")
    private String os;

    /** ヒントメッセージ */
    @Excel(name = "ヒントメッセージ")
    private String msg;

    /** アクセス時間 */
    @Excel(name = "アクセス時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    public Long getInfoId()
    {
        return infoId;
    }

    public void setInfoId(Long infoId)
    {
        this.infoId = infoId;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Date getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("infoId", getInfoId())
            .append("loginName", getLoginName())
            .append("ipaddr", getIpaddr())
            .append("loginLocation", getLoginLocation())
            .append("browser", getBrowser())
            .append("os", getOs())
            .append("status", getStatus())
            .append("msg", getMsg())
            .append("loginTime", getLoginTime())
            .toString();
    }
}