package com.ebs.common.constant;

import java.util.Locale;

public class Constants
{
    public static final String UTF8 = "UTF-8";

    public static final String GBK = "GBK";

    public static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    public static final String HTTP = "http://";

    public static final String HTTPS = "https://";

    public static final String SUCCESS = "0";

    public static final String FAIL = "1";

    public static final String LOGIN_SUCCESS = "Success";

    public static final String LOGOUT = "Logout";

    public static final String REGISTER = "Register";

    public static final String LOGIN_FAIL = "Error";

    public static final String SYS_AUTH_CACHE = "sys-authCache";

    public static final String SYS_CONFIG_CACHE = "sys-config";

    public static final String SYS_CONFIG_KEY = "sys_config:";

    public static final String SYS_DICT_CACHE = "sys-dict";

    public static final String SYS_DICT_KEY = "sys_dict:";

    public static final String RESOURCE_PREFIX = "/profile";

    public static final String LOOKUP_RMI = "rmi:";

    public static final String LOOKUP_LDAP = "ldap:";

    public static final String LOOKUP_LDAPS = "ldaps:";

    public static final String[] JOB_WHITELIST_STR = { "com.ebs.quartz.task" };

    public static final String[] JOB_ERROR_STR = { "java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.ebs.common.utils.file", "com.ebs.common.config", "com.ebs.generator" };
}