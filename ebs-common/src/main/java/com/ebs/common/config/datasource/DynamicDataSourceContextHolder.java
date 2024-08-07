package com.ebs.common.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceContextHolder
{
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * ThreadLocalメンテナンス変数を使用して、ThreadLocalは、変数を使用するスレッドごとに独立した変数コピーを提供します、
     * したがって、各スレッドは、他のスレッドに対応するコピーに影響を与えることなく、独立して自分のコピーを変更することができます。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSourceType(String dsType)
    {
        log.info("データソース{}に切り替え", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    public static String getDataSourceType()
    {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceType()
    {
        CONTEXT_HOLDER.remove();
    }
}
