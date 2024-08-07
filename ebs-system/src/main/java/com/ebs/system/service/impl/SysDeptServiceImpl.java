package com.ebs.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ebs.common.annotation.DataScope;
import com.ebs.common.constant.UserConstants;
import com.ebs.common.core.domain.Ztree;
import com.ebs.common.core.domain.entity.SysDept;
import com.ebs.common.core.domain.entity.SysRole;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.core.text.Convert;
import com.ebs.common.exception.ServiceException;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.common.utils.spring.SpringUtils;
import com.ebs.system.mapper.SysDeptMapper;
import com.ebs.system.service.ISysDeptService;

/**
 * 部門管理
 * 
 * @author ebs
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    @Autowired
    private SysDeptMapper deptMapper;

    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept)
    {
        return deptMapper.selectDeptList(dept);
    }

    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTree(SysDept dept)
    {
        List<SysDept> deptList = deptMapper.selectDeptList(dept);
        List<Ztree> ztrees = initZtree(deptList);
        return ztrees;
    }

    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTreeExcludeChild(SysDept dept)
    {
        Long excludeId = dept.getExcludeId();
        List<SysDept> depts = deptMapper.selectDeptList(dept);
        if (excludeId.intValue() > 0)
        {
            depts.removeIf(d -> d.getDeptId().intValue() == excludeId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), excludeId + ""));
        }
        List<Ztree> ztrees = initZtree(depts);
        return ztrees;
    }

    @Override
    public List<Ztree> roleDeptTreeData(SysRole role)
    {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysDept> deptList = SpringUtils.getAopProxy(this).selectDeptList(new SysDept());
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        }
        else
        {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    public List<Ztree> initZtree(List<SysDept> deptList)
    {
        return initZtree(deptList, null);
    }

    public List<Ztree> initZtree(List<SysDept> deptList, List<String> roleDeptList)
    {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (SysDept dept : deptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck)
                {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    @Override
    public int selectDeptCount(Long parentId)
    {
        SysDept dept = new SysDept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    @Override
    public int deleteDeptById(Long deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }

    @Override
    public int insertDept(SysDept dept)
    {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new ServiceException("部門は廃止しましたので、追加できません");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    @Override
    @Transactional
    public int updateDept(SysDept dept)
    {
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
                && !StringUtils.equals("0", dept.getAncestors()))
        {
           
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    private void updateParentDeptStatusNormal(SysDept dept)
    {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        deptMapper.updateDeptStatusNormal(deptIds);
    }

    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    @Override
    public SysDept selectDeptById(Long deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    @Override
    public int selectNormalChildrenDeptById(Long deptId)
    {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    @Override
    public boolean checkDeptNameUnique(SysDept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void checkDeptDataScope(Long deptId)
    {
        if (!SysUser.isAdmin(ShiroUtils.getUserId()))
        {
            SysDept dept = new SysDept();
            dept.setDeptId(deptId);
            List<SysDept> depts = SpringUtils.getAopProxy(this).selectDeptList(dept);
            if (StringUtils.isEmpty(depts))
            {
                throw new ServiceException("部門データへのアクセス権限がありません。");
            }
        }
    }
}
