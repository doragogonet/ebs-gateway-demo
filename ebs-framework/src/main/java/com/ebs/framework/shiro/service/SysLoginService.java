package com.ebs.framework.shiro.service;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ebs.common.constant.Constants;
import com.ebs.common.constant.ShiroConstants;
import com.ebs.common.constant.UserConstants;
import com.ebs.common.core.domain.entity.SysRole;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.enums.UserStatus;
import com.ebs.common.exception.user.BlackListException;
import com.ebs.common.exception.user.CaptchaException;
import com.ebs.common.exception.user.UserBlockedException;
import com.ebs.common.exception.user.UserDeleteException;
import com.ebs.common.exception.user.UserNotExistsException;
import com.ebs.common.exception.user.UserPasswordNotMatchException;
import com.ebs.common.utils.DateUtils;
import com.ebs.common.utils.IpUtils;
import com.ebs.common.utils.MessageUtils;
import com.ebs.common.utils.ServletUtils;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.framework.manager.AsyncManager;
import com.ebs.framework.manager.factory.AsyncFactory;
import com.ebs.system.service.ISysConfigService;
import com.ebs.system.service.ISysMenuService;
import com.ebs.system.service.ISysUserService;

/**
 * 検証方法を登録
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 登録
     */
    public SysUser login(String username, String password)
    {
 
        if (ShiroConstants.CAPTCHA_ERROR.equals(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
 
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }


        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

    
        String blackStr = configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, ShiroUtils.getIp()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("login.blocked")));
            throw new BlackListException();
        }

        SysUser user = userService.selectUserByLoginName(username);

        /**
        if (user == null && maybeMobilePhoneNumber(username))
        {
            user = userService.selectUserByPhoneNumber(username);
        }

        if (user == null && maybeEmail(username))
        {
            user = userService.selectUserByEmail(username);
        }
        */

        if (user == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }
        
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
            throw new UserDeleteException();
        }
        
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked")));
            throw new UserBlockedException();
        }

        passwordService.validate(user, password);

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        setRolePermission(user);
        recordLoginInfo(user.getUserId());
        return user;
    }

    /**
    private boolean maybeEmail(String username)
    {
        if (!username.matches(UserConstants.EMAIL_PATTERN))
        {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username)
    {
        if (!username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN))
        {
            return false;
        }
        return true;
    }
    */


    public void setRolePermission(SysUser user)
    {
        List<SysRole> roles = user.getRoles();
        if (!roles.isEmpty() && roles.size() > 1)
        {
            
            for (SysRole role : roles)
            {
                Set<String> rolePerms = menuService.selectPermsByRoleId(role.getRoleId());
                role.setPermissions(rolePerms);
            }
        }
    }


    public void recordLoginInfo(Long userId)
    {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserInfo(user);
    }
}
