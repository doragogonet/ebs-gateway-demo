package com.ebs.framework.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import com.ebs.framework.shiro.realm.UserRealm;


public class AuthorizationUtils
{
   
    public static void clearAllCachedAuthorizationInfo()
    {
        getUserRealm().clearAllCachedAuthorizationInfo();
    }

  
    public static UserRealm getUserRealm()
    {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        return (UserRealm) rsm.getRealms().iterator().next();
    }
}
