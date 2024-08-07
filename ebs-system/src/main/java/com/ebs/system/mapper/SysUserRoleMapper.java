package com.ebs.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ebs.system.domain.SysUserRole;

/**
 * ユーザーとロール
 * 
 * @author ebs
 */
public interface SysUserRoleMapper
{
   
    public List<SysUserRole> selectUserRoleByUserId(Long userId);

    public int deleteUserRoleByUserId(Long userId);

    public int deleteUserRole(Long[] ids);

    public int countUserRoleByRoleId(Long roleId);

    public int batchUserRole(List<SysUserRole> userRoleList);

    public int deleteUserRoleInfo(SysUserRole userRole);

    public int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);
}
