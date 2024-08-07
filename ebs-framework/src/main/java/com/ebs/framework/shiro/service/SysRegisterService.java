package com.ebs.framework.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ebs.common.constant.Constants;
import com.ebs.common.constant.ShiroConstants;
import com.ebs.common.constant.UserConstants;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.utils.DateUtils;
import com.ebs.common.utils.MessageUtils;
import com.ebs.common.utils.ServletUtils;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.framework.manager.AsyncManager;
import com.ebs.framework.manager.factory.AsyncFactory;
import com.ebs.system.service.ISysUserService;

/**
 * 検証方法を登録
 * 
 * @author ebs
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    /**
     * 登録
     */
    public String register(SysUser user)
    {
        String msg = "", loginName = user.getLoginName(), password = user.getPassword();

        if (ShiroConstants.CAPTCHA_ERROR.equals(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
        {
            msg = "認証コードエラー";
        }
        else if (StringUtils.isEmpty(loginName))
        {
            msg = "ユーザー名を入力してください";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "パスワードを入力してください";
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            msg = "パスワードの長さは5～20文字でなければなりません";
        }
        else if (loginName.length() < UserConstants.USERNAME_MIN_LENGTH
                || loginName.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            msg = "アカウントの長さは2～20文字でなければなりません";
        }
        else if (!userService.checkLoginNameUnique(user))
        {
            msg = "「" + loginName + "」アカウントが既に存在しました";
        }
        else
        {
            user.setPwdUpdateDate(DateUtils.getNowDate());
            user.setUserName(loginName);
            user.setSalt(ShiroUtils.randomSalt());
            user.setPassword(passwordService.encryptPassword(loginName, password, user.getSalt()));
            boolean regFlag = userService.registerUser(user);
            if (!regFlag)
            {
                msg = "登録失敗、システム管理者にお問い合わせください";
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }
}
