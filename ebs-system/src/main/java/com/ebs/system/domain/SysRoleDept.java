package com.ebs.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ロールと部門の関連表 sys_role_dept
 * 
 * @author ebs
 */
public class SysRoleDept
{
    /** ロールID */
    private Long roleId;
    
    /** 部門ID */
    private Long deptId;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("deptId", getDeptId())
            .toString();
    }
}
