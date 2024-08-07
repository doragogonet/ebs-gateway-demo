package com.ebs.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebs.common.annotation.Log;
import com.ebs.common.constant.UserConstants;
import com.ebs.common.core.controller.BaseController;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.core.domain.Ztree;
import com.ebs.common.core.domain.entity.SysDept;
import com.ebs.common.enums.BusinessType;
import com.ebs.common.utils.StringUtils;
import com.ebs.system.service.ISysDeptService;


@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{
    private String prefix = "system/dept";

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept()
    {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysDept> list(SysDept dept)
    {
        List<SysDept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

 
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap)
    {
        if (!getSysUser().isAdmin())
        {
            parentId = getSysUser().getDeptId();
        }
        mmap.put("dept", deptService.selectDeptById(parentId));
        return prefix + "/add";
    }

  
    @Log(title = "部門管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDept dept)
    {
        if (!deptService.checkDeptNameUnique(dept))
        {
            return error("「" + dept.getDeptName() + "」が存在しました");
        }
        dept.setCreateBy(getLoginName());
        return toAjax(deptService.insertDept(dept));
    }


    @RequiresPermissions("system:dept:edit")
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap)
    {
        deptService.checkDeptDataScope(deptId);
        SysDept dept = deptService.selectDeptById(deptId);
        if (StringUtils.isNotNull(dept) && 100L == deptId)
        {
            dept.setParentName("无");
        }
        mmap.put("dept", dept);
        return prefix + "/edit";
    }


    @Log(title = "部門管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDept dept)
    {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (!deptService.checkDeptNameUnique(dept))
        {
            return error("「" + dept.getDeptName() + "」が存在しました");
        }
        else if (dept.getParentId().equals(deptId))
        {
            return error("「" + dept.getDeptName() + "」が失敗しました");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0)
        {
            return AjaxResult.error("失敗しました");
        }
        dept.setUpdateBy(getLoginName());
        return toAjax(deptService.updateDept(dept));
    }

  
    @Log(title = "部門管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:remove")
    @GetMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") Long deptId)
    {
        if (deptService.selectDeptCount(deptId) > 0)
        {
            return AjaxResult.warn("削除できません");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return AjaxResult.warn("削除できません");
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }


    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public boolean checkDeptNameUnique(SysDept dept)
    {
        return deptService.checkDeptNameUnique(dept);
    }

   
    @GetMapping(value = { "/selectDeptTree/{deptId}", "/selectDeptTree/{deptId}/{excludeId}" })
    public String selectDeptTree(@PathVariable("deptId") Long deptId,
            @PathVariable(value = "excludeId", required = false) Long excludeId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(deptId));
        mmap.put("excludeId", excludeId);
        return prefix + "/tree";
    }

   
    @GetMapping("/treeData/{excludeId}")
    @ResponseBody
    public List<Ztree> treeDataExcludeChild(@PathVariable(value = "excludeId", required = false) Long excludeId)
    {
        SysDept dept = new SysDept();
        dept.setExcludeId(excludeId);
        List<Ztree> ztrees = deptService.selectDeptTreeExcludeChild(dept);
        return ztrees;
    }
}
