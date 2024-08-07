package com.ebs.web.controller.system;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
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
import org.springframework.web.multipart.MultipartFile;
import com.ebs.common.annotation.Log;
import com.ebs.common.core.controller.BaseController;
import com.ebs.common.core.domain.AjaxResult;
import com.ebs.common.core.domain.Ztree;
import com.ebs.common.core.domain.entity.SysDept;
import com.ebs.common.core.domain.entity.SysRole;
import com.ebs.common.core.domain.entity.SysUser;
import com.ebs.common.core.page.TableDataInfo;
import com.ebs.common.core.text.Convert;
import com.ebs.common.enums.BusinessType;
import com.ebs.common.utils.DateUtils;
import com.ebs.common.utils.ShiroUtils;
import com.ebs.common.utils.StringUtils;
import com.ebs.common.utils.poi.ExcelUtil;
import com.ebs.framework.shiro.service.SysPasswordService;
import com.ebs.framework.shiro.util.AuthorizationUtils;
import com.ebs.system.service.ISysDeptService;
import com.ebs.system.service.ISysPostService;
import com.ebs.system.service.ISysRoleService;
import com.ebs.system.service.ISysUserService;


@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController
{
    private String prefix = "system/user";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;
    
    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private SysPasswordService passwordService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/user";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @Log(title = "ユーザー管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysUser user)
    {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "ユーザーデータ");
    }

    @Log(title = "ユーザー管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String message = userService.importUser(userList, updateSupport, getLoginName());
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("ユーザーデータ");
    }

 
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("roles", roleService.selectRoleAll().stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

 
    @RequiresPermissions("system:user:add")
    @Log(title = "ユーザー管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysUser user)
    {
        if (!userService.checkLoginNameUnique(user))
        {
            return error("「" + user.getLoginName() + "」が存在しました");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
        {
            return error("「" + user.getLoginName() + "」が存在しました");
        }
        else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
        {
            return error("「" + user.getLoginName() + "」が存在しました");
        }
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setPwdUpdateDate(DateUtils.getNowDate());
        user.setCreateBy(getLoginName());
        return toAjax(userService.insertUser(user));
    }


    @RequiresPermissions("system:user:edit")
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        userService.checkUserDataScope(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }


    @RequiresPermissions("system:user:list")
    @GetMapping("/view/{userId}")
    public String view(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        userService.checkUserDataScope(userId);
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roleGroup", userService.selectUserRoleGroup(userId));
        mmap.put("postGroup", userService.selectUserPostGroup(userId));
        return prefix + "/view";
    }


    @RequiresPermissions("system:user:edit")
    @Log(title = "ユーザー管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (!userService.checkLoginNameUnique(user))
        {
            return error("「" + user.getLoginName() + "」が存在しました");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
        {
            return error("「" + user.getLoginName() + "」が存在しました");
        }
        else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
        {
            return error("「" + user.getLoginName() + "」が存在しました");
        }
        user.setUpdateBy(getLoginName());
        AuthorizationUtils.clearAllCachedAuthorizationInfo();
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "パスワードリセット", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        if (userService.resetUserPwd(user) > 0)
        {
            if (ShiroUtils.getUserId().longValue() == user.getUserId().longValue())
            {
                setSysUser(userService.selectUserById(user.getUserId()));
            }
            return success();
        }
        return error();
    }


    @GetMapping("/authRole/{userId}")
    public String authRole(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        SysUser user = userService.selectUserById(userId);
    
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        mmap.put("user", user);
        mmap.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return prefix + "/authRole";
    }

  
    @RequiresPermissions("system:user:edit")
    @Log(title = "ユーザー管理", businessType = BusinessType.GRANT)
    @PostMapping("/authRole/insertAuthRole")
    @ResponseBody
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds)
    {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        AuthorizationUtils.clearAllCachedAuthorizationInfo();
        return success();
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "ユーザー管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        if (ArrayUtils.contains(Convert.toLongArray(ids), getUserId()))
        {
            return error("ユーザーを削除できません");
        }
        return toAjax(userService.deleteUserByIds(ids));
    }

   
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public boolean checkLoginNameUnique(SysUser user)
    {
        return userService.checkLoginNameUnique(user);
    }

   
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public boolean checkPhoneUnique(SysUser user)
    {
        return userService.checkPhoneUnique(user);
    }

   
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public boolean checkEmailUnique(SysUser user)
    {
        return userService.checkEmailUnique(user);
    }

   
    @Log(title = "ユーザー管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        return toAjax(userService.changeStatus(user));
    }

 
    @RequiresPermissions("system:user:list")
    @GetMapping("/deptTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData()
    {
        List<Ztree> ztrees = deptService.selectDeptTree(new SysDept());
        return ztrees;
    }

 
    @RequiresPermissions("system:user:list")
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(deptId));
        return prefix + "/deptTree";
    }
}