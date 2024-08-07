package com.ebs.system.service;

import java.util.List;
import com.ebs.common.core.domain.Ztree;
import com.ebs.common.core.domain.entity.SysDept;
import com.ebs.common.core.domain.entity.SysRole;

/**
 * 部門管理
 * 
 * @author ebs
 */
public interface ISysDeptService
{
   
    public List<SysDept> selectDeptList(SysDept dept);

    public List<Ztree> selectDeptTree(SysDept dept);

    public List<Ztree> selectDeptTreeExcludeChild(SysDept dept);

    public List<Ztree> roleDeptTreeData(SysRole role);

    public int selectDeptCount(Long parentId);

    public boolean checkDeptExistUser(Long deptId);

    public int deleteDeptById(Long deptId);

    public int insertDept(SysDept dept);

    public int updateDept(SysDept dept);

    public SysDept selectDeptById(Long deptId);

    public int selectNormalChildrenDeptById(Long deptId);

    public boolean checkDeptNameUnique(SysDept dept);

    public void checkDeptDataScope(Long deptId);
}
