package com.ebs.common.utils.sql;

import com.ebs.common.exception.UtilException;
import com.ebs.common.utils.StringUtils;


public class SqlUtil
{

    public static String SQL_REGEX = "and |extractvalue|updatexml|exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |or |+|user()";

  
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";


    private static final int ORDER_BY_MAX_LENGTH = 500;


    public static String escapeOrderBySql(String value)
    {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            throw new UtilException("パラメータが仕様に一致せず、クエリを実行できません");
        }
        if (StringUtils.length(value) > ORDER_BY_MAX_LENGTH)
        {
            throw new UtilException("パラメータが最大制限を超えており、クエリを実行できません");
        }
        return value;
    }

    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }


    public static void filterKeyword(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return;
        }
        String[] sqlKeywords = StringUtils.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords)
        {
            if (StringUtils.indexOfIgnoreCase(value, sqlKeyword) > -1)
            {
                throw new UtilException("パラメータにSQLインジェクションのリスクがある");
            }
        }
    }
}
