package com.ebs.framework.aspectj;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.ebs.common.core.context.PermissionContextHolder;
import com.ebs.common.utils.StringUtils;

/**
 * カスタムアクセス権ブロック、複数のロールが要件を満たす権限に一致するように、権限文字列を現在の要求に配置する
 * 
 * @author ebs
 */
@Aspect
@Component
public class PermissionsAspect
{
    @Before("@annotation(controllerRequiresPermissions)")
    public void doBefore(JoinPoint point, RequiresPermissions controllerRequiresPermissions) throws Throwable
    {
        handleRequiresPermissions(point, controllerRequiresPermissions);
    }

    protected void handleRequiresPermissions(final JoinPoint joinPoint, RequiresPermissions requiresPermissions)
    {
        PermissionContextHolder.setContext(StringUtils.join(requiresPermissions.value(), ","));
    }
}
