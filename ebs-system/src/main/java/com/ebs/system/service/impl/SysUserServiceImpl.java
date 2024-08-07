package com.ebs.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.ebs.common.annotation.DataScope;
import com.ebs.common.constant.UserConstants;
import com.ebs.common.core.domain.entity.SysRole;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.core.text.Convert;
import com.ebs.common.exception.ServiceException;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.common.utils.bean.BeanValidators;
import com.ebs.common.utils.security.Md5Utils;
import com.ebs.common.utils.spring.SpringUtils;
import com.ebs.system.domain.SysPost;
import com.ebs.system.domain.SysUserPost;
import com.ebs.system.domain.SysUserRole;
import com.ebs.system.mapper.SysPostMapper;
import com.ebs.system.mapper.SysRoleMapper;
import com.ebs.system.mapper.SysUserMapper;
import com.ebs.system.mapper.SysUserPostMapper;
import com.ebs.system.mapper.SysUserRoleMapper;
import com.ebs.system.service.ISysConfigService;
import com.ebs.system.service.ISysUserService;

/**
 * ユーザー
 * 
 * @author ebs
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    protected Validator validator;

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user)
    {
        return userMapper.selectAllocatedList(user);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return userMapper.selectUnallocatedList(user);
    }

    @Override
    public SysUser selectUserByLoginName(String userName)
    {
        return userMapper.selectUserByLoginName(userName);
    }

    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber)
    {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    @Override
    public SysUser selectUserByEmail(String email)
    {
        return userMapper.selectUserByEmail(email);
    }

    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    @Override
    public List<SysUserRole> selectUserRoleByUserId(Long userId)
    {
        return userRoleMapper.selectUserRoleByUserId(userId);
    }

    @Override
    @Transactional
    public int deleteUserById(Long userId)
    {
       
        userRoleMapper.deleteUserRoleByUserId(userId);
       
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

   
    @Override
    @Transactional
    public int deleteUserByIds(String ids)
    {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        
        userRoleMapper.deleteUserRole(userIds);
        
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    @Override
    @Transactional
    public int insertUser(SysUser user)
    {
       
        int rows = userMapper.insertUser(user);
        
        insertUserPost(user);
        
        insertUserRole(user.getUserId(), user.getRoleIds());
        return rows;
    }

    @Override
    public boolean registerUser(SysUser user)
    {
        user.setUserType(UserConstants.REGISTER_USER_TYPE);
        return userMapper.insertUser(user) > 0;
    }

    @Override
    @Transactional
    public int updateUser(SysUser user)
    {
        Long userId = user.getUserId();
       
        userRoleMapper.deleteUserRoleByUserId(userId);
        
        insertUserRole(user.getUserId(), user.getRoleIds());
      
        userPostMapper.deleteUserPostByUserId(userId);
      
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    @Override
    public int updateUserInfo(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    @Override
    public int resetUserPwd(SysUser user)
    {
        return updateUserInfo(user);
    }

    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (StringUtils.isNotNull(roleIds))
        {
           
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts))
        {
            
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    @Override
    public boolean checkLoginNameUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkLoginNameUnique(user.getLoginName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public boolean checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public boolean checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new ServiceException("管理者を操作できません");
        }
    }

    @Override
    public void checkUserDataScope(Long userId)
    {
        if (!SysUser.isAdmin(ShiroUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw new ServiceException("ユーザーデータにアクセスする権限がありません。");
            }
        }
    }

    @Override
    public String selectUserRoleGroup(Long userId)
    {
        List<SysRole> list = roleMapper.selectRolesByUserId(userId);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    @Override
    public String selectUserPostGroup(Long userId)
    {
        List<SysPost> list = postMapper.selectPostsByUserId(userId);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("インポートデータを空にすることはできません。");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                
                SysUser u = userMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(Md5Utils.hash(user.getLoginName() + password));
                    user.setCreateBy(operName);
                    userMapper.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、アカウント " + user.getLoginName() + " インポート成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, user);
                    checkUserAllowed(u);
                    checkUserDataScope(u.getUserId());
                    user.setUserId(u.getUserId());
                    user.setUpdateBy(operName);
                    userMapper.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、アカウント" + user.getLoginName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、アカウント " + user.getLoginName() + " が既に存在しました");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、アカウント " + user.getLoginName() + " インポート失敗：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "インポート失敗、" + failureNum + " 件データが正しくありません、下記のエラー：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "インポート成功、" + successNum + " 件：");
        }
        return successMsg.toString();
    }

  
    @Override
    public int changeStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }
}
