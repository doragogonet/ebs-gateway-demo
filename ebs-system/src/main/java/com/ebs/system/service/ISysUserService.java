package com.ebs.system.service;

import java.util.List;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.system.domain.SysUserRole;

/**
 * ユーザー
 * 
 * @author ebs
 */
public interface ISysUserService
{
   
    public List<SysUser> selectUserList(SysUser user);

    public List<SysUser> selectAllocatedList(SysUser user);

    public List<SysUser> selectUnallocatedList(SysUser user);

    public SysUser selectUserByLoginName(String userName);

    public SysUser selectUserByPhoneNumber(String phoneNumber);

    public SysUser selectUserByEmail(String email);

    public SysUser selectUserById(Long userId);

    public List<SysUserRole> selectUserRoleByUserId(Long userId);

    public int deleteUserById(Long userId);

    public int deleteUserByIds(String ids);

    public int insertUser(SysUser user);

    public boolean registerUser(SysUser user);

    public int updateUser(SysUser user);

    public int updateUserInfo(SysUser user);

    public void insertUserAuth(Long userId, Long[] roleIds);

    public int resetUserPwd(SysUser user);

    public boolean checkLoginNameUnique(SysUser user);

    public boolean checkPhoneUnique(SysUser user);

    public boolean checkEmailUnique(SysUser user);

    public void checkUserAllowed(SysUser user);

    public void checkUserDataScope(Long userId);

    public String selectUserRoleGroup(Long userId);

    public String selectUserPostGroup(Long userId);

    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    public int changeStatus(SysUser user);
}
