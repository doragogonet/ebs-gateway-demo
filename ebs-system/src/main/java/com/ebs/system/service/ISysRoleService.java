package com.ebs.system.service;

import java.util.List;
import java.util.Set;
import com.ebs.common.core.domain.entity.SysRole;
import com.ebs.system.domain.SysUserRole;

/**
 * ロール
 * 
 * @author ebs
 */
public interface ISysRoleService
{
 
    public List<SysRole> selectRoleList(SysRole role);

    public Set<String> selectRoleKeys(Long userId);

    public List<SysRole> selectRolesByUserId(Long userId);

    public List<SysRole> selectRoleAll();

    public SysRole selectRoleById(Long roleId);

    public boolean deleteRoleById(Long roleId);

    public int deleteRoleByIds(String ids);

    public int insertRole(SysRole role);

    public int updateRole(SysRole role);

    public int authDataScope(SysRole role);

    public boolean checkRoleNameUnique(SysRole role);

    public boolean checkRoleKeyUnique(SysRole role);

    public void checkRoleAllowed(SysRole role);

    public void checkRoleDataScope(Long roleId);

    public int countUserRoleByRoleId(Long roleId);

    public int changeStatus(SysRole role);

    public int deleteAuthUser(SysUserRole userRole);

    public int deleteAuthUsers(Long roleId, String userIds);

    public int insertAuthUsers(Long roleId, String userIds);
}
