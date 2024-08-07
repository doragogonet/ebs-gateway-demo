package com.ebs.system.mapper;

import java.util.List;
import com.ebs.common.core.domain.entity.SysUser;

/**
 * ユーザーテーブル
 * 
 * @author ebs
 */
public interface SysUserMapper
{
 
    public List<SysUser> selectUserList(SysUser sysUser);

    public List<SysUser> selectAllocatedList(SysUser user);

    public List<SysUser> selectUnallocatedList(SysUser user);

    public SysUser selectUserByLoginName(String userName);

    public SysUser selectUserByPhoneNumber(String phoneNumber);

    public SysUser selectUserByEmail(String email);

    public SysUser selectUserById(Long userId);

    public int deleteUserById(Long userId);

    public int deleteUserByIds(Long[] ids);

    public int updateUser(SysUser user);

    public int insertUser(SysUser user);

    public SysUser checkLoginNameUnique(String loginName);

    public SysUser checkPhoneUnique(String phonenumber);

    public SysUser checkEmailUnique(String email);
}
