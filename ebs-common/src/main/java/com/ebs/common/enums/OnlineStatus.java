package com.ebs.common.enums;


public enum OnlineStatus
{
    on_line("オンライン"), off_line("オフライン");

    private final String info;

    private OnlineStatus(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }
}
