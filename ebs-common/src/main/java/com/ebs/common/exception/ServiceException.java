package com.ebs.common.exception;


public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;


    private String message;


    private String detailMessage;

 
    public ServiceException()
    {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }
}