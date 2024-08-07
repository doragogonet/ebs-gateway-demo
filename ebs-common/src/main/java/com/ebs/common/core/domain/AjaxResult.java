package com.ebs.common.core.domain;

import java.util.HashMap;
import java.util.Objects;
import com.ebs.common.utils.StringUtils;


public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";

    public enum Type
    {
        
        SUCCESS(0),
       
        WARN(301),
       
        ERROR(500);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

   
    public AjaxResult()
    {
    }

   
    public AjaxResult(Type type, String msg)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

  
    public AjaxResult(Type type, String msg, Object data)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

   
    public static AjaxResult success()
    {
        return AjaxResult.success("操作成功");
    }

    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("操作成功", data);
    }

    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(Type.SUCCESS, msg, data);
    }

    public static AjaxResult warn(String msg)
    {
        return AjaxResult.warn(msg, null);
    }

    public static AjaxResult warn(String msg, Object data)
    {
        return new AjaxResult(Type.WARN, msg, data);
    }

    public static AjaxResult error()
    {
        return AjaxResult.error("操作失敗");
    }

    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(Type.ERROR, msg, data);
    }

    public boolean isSuccess()
    {
        return Objects.equals(Type.SUCCESS.value, this.get(CODE_TAG));
    }

    public boolean isWarn()
    {
        return Objects.equals(Type.WARN.value, this.get(CODE_TAG));
    }

    public boolean isError()
    {
        return Objects.equals(Type.ERROR.value, this.get(CODE_TAG));
    }


    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
