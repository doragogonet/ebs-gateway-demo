package com.ebs.system.mapper;

import java.util.List;
import com.ebs.system.domain.SysRoleMenu;

/**
 * ロールとメニューの関連
 * 
 * @author ebs
 */
public interface SysRoleMenuMapper
{
   
    public int deleteRoleMenuByRoleId(Long roleId);
    
    public int deleteRoleMenu(Long[] ids);
    
    public int selectCountRoleMenuByMenuId(Long menuId);
   
    public int batchRoleMenu(List<SysRoleMenu> roleMenuList);
}
