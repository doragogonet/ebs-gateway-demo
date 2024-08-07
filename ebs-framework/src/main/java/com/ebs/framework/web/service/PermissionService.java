package com.ebs.framework.web.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ebs.common.utils.StringUtils;


@Service("permission")
public class PermissionService
{
    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

 
    public static final String NOACCESS = "hidden";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";


    public String hasPermi(String permission)
    {
        return isPermitted(permission) ? StringUtils.EMPTY : NOACCESS;
    }

    public String lacksPermi(String permission)
    {
        return isLacksPermitted(permission) ? StringUtils.EMPTY : NOACCESS;
    }


    public String hasAnyPermi(String permissions)
    {
        return hasAnyPermissions(permissions, PERMISSION_DELIMETER) ? StringUtils.EMPTY : NOACCESS;
    }


    public String hasRole(String role)
    {
        return isRole(role) ? StringUtils.EMPTY : NOACCESS;
    }


    public String lacksRole(String role)
    {
        return isLacksRole(role) ? StringUtils.EMPTY : NOACCESS;
    }


    public String hasAnyRoles(String roles)
    {
        return isAnyRoles(roles, ROLE_DELIMETER) ? StringUtils.EMPTY : NOACCESS;
    }


    public boolean isUser()
    {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.getPrincipal() != null;
    }


    public boolean isPermitted(String permission)
    {
        return SecurityUtils.getSubject().isPermitted(permission);
    }


    public boolean isLacksPermitted(String permission)
    {
        return isPermitted(permission) != true;
    }


    public boolean hasAnyPermissions(String permissions)
    {
        return hasAnyPermissions(permissions, PERMISSION_DELIMETER);
    }


    public boolean hasAnyPermissions(String permissions, String delimeter)
    {
        Subject subject = SecurityUtils.getSubject();

        if (subject != null)
        {
            if (delimeter == null || delimeter.length() == 0)
            {
                delimeter = PERMISSION_DELIMETER;
            }

            for (String permission : permissions.split(delimeter))
            {
                if (permission != null && subject.isPermitted(permission.trim()) == true)
                {
                    return true;
                }
            }
        }

        return false;
    }


    public boolean isRole(String role)
    {
        return SecurityUtils.getSubject().hasRole(role);
    }


    public boolean isLacksRole(String role)
    {
        return isRole(role) != true;
    }


    public boolean isAnyRoles(String roles)
    {
        return isAnyRoles(roles, ROLE_DELIMETER);
    }

  
    public boolean isAnyRoles(String roles, String delimeter)
    {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null)
        {
            if (delimeter == null || delimeter.length() == 0)
            {
                delimeter = ROLE_DELIMETER;
            }

            for (String role : roles.split(delimeter))
            {
                if (subject.hasRole(role.trim()) == true)
                {
                    return true;
                }
            }
        }

        return false;
    }


    public Object getPrincipalProperty(String property)
    {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null)
        {
            Object principal = subject.getPrincipal();
            try
            {
                BeanInfo bi = Introspector.getBeanInfo(principal.getClass());
                for (PropertyDescriptor pd : bi.getPropertyDescriptors())
                {
                    if (pd.getName().equals(property) == true)
                    {
                        return pd.getReadMethod().invoke(principal, (Object[]) null);
                    }
                }
            }
            catch (Exception e)
            {
                log.error("Error reading property [{}] from principal of type [{}]", property, principal.getClass().getName());
            }
        }
        return null;
    }
}
