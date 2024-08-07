package com.ebs.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.ebs.common.core.domain.Ztree;
import com.ebs.common.core.domain.entity.SysMenu;
import com.ebs.common.core.domain.entity.SysRole;
import com.ebs.common.core.domain.entity.SysUser;

/**
 * メニュー
 * 
 * @author ebs
 */
public interface ISysMenuService
{
  
    public List<SysMenu> selectMenusByUser(SysUser user);

    public List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    public List<SysMenu> selectMenuAll(Long userId);

    public Set<String> selectPermsByUserId(Long userId);

    public Set<String> selectPermsByRoleId(Long roleId);

    public List<Ztree> roleMenuTreeData(SysRole role, Long userId);

    public List<Ztree> menuTreeData(Long userId);

    public Map<String, String> selectPermsAll(Long userId);

    public int deleteMenuById(Long menuId);

    public SysMenu selectMenuById(Long menuId);

    public int selectCountMenuByParentId(Long parentId);

    public int selectCountRoleMenuByMenuId(Long menuId);

    public int insertMenu(SysMenu menu);

    public int updateMenu(SysMenu menu);

    public boolean checkMenuNameUnique(SysMenu menu);
}
