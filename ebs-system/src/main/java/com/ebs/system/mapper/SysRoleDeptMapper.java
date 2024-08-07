package com.ebs.system.mapper;

import java.util.List;
import com.ebs.system.domain.SysRoleDept;

/**
 * ロールと部門の関連表
 * 
 * @author ebs
 */
public interface SysRoleDeptMapper
{
    
    public int deleteRoleDeptByRoleId(Long roleId);

    public int deleteRoleDept(Long[] ids);

    public int selectCountRoleDeptByDeptId(Long deptId);

    public int batchRoleDept(List<SysRoleDept> roleDeptList);
}
