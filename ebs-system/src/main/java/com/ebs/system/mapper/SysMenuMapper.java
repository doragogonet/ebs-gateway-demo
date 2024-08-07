package com.ebs.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ebs.common.core.domain.entity.SysMenu;

/**
 * メニュー表
 * 
 * @author ebs
 */
public interface SysMenuMapper
{
  
    public List<SysMenu> selectMenuAll();

    public List<SysMenu> selectMenuAllByUserId(Long userId);

    public List<SysMenu> selectMenuNormalAll();

    public List<SysMenu> selectMenusByUserId(Long userId);

    public List<String> selectPermsByUserId(Long userId);

    public List<String> selectPermsByRoleId(Long roleId);

    public List<String> selectMenuTree(Long roleId);

    public List<SysMenu> selectMenuList(SysMenu menu);

    public List<SysMenu> selectMenuListByUserId(SysMenu menu);

    public int deleteMenuById(Long menuId);

    public SysMenu selectMenuById(Long menuId);

    public int selectCountMenuByParentId(Long parentId);

    public int insertMenu(SysMenu menu);

    public int updateMenu(SysMenu menu);

    public SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);
}
