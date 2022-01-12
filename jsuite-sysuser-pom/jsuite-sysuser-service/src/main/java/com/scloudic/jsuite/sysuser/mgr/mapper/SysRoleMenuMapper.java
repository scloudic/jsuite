package com.scloudic.jsuite.sysuser.mgr.mapper;

import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRoleMenu;
import com.scloudic.rabbitframework.jbatis.annontations.MapKey;
import com.scloudic.rabbitframework.jbatis.annontations.Mapper;
import com.scloudic.rabbitframework.jbatis.annontations.Param;
import com.scloudic.rabbitframework.jbatis.annontations.Select;
import com.scloudic.rabbitframework.jbatis.mapping.BaseMapper;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;

import java.util.List;
import java.util.Map;

/**
 * database table sys_role_menu mapper interface
 **/
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 关联角色菜单表,根据参数获取菜单信息
     *
     * @param where
     * @return
     */
    @Select("select distinct menu.* from sys_menu menu,sys_role_menu rm " +
            "where menu.sys_menu_id=rm.sys_menu_id ")
    List<SysMenu> findMenuJoinRoleMenuByParams(Where where);

    /**
     * 关联角色菜单表,根据参数获取菜单数量
     *
     * @param where
     * @return
     */
    @Select("select count(distinct menu.sys_menu_id) from sys_menu menu,sys_role_menu rm " +
            "where menu.sys_menu_id=rm.sys_menu_id ")
    Integer getCountMenuJoinRoleMenuByParams(Where where);

    /**
     * 获取用户角色菜单
     *
     * @param where
     * @return
     */
    @Select("select distinct menu.* from sys_role_menu rm,sys_menu menu,sys_user_role ur " +
            "where menu.sys_menu_id=rm.sys_menu_id and rm.sys_role_id=ur.sys_role_id ")
    List<SysMenu> findUserRoleMenuByParams(Where where);
    
    /**
     * 根据角
     *
     * @param sysRoleId
     * @return
     */
    @Select("select * from sys_role_menu where sys_role_id=#{sysRoleId}")
    @MapKey("sysMenuId")
    Map<Long, SysRoleMenu> findRoleMenuByRoleId(@Param("sysRoleId") Long sysRoleId);
}
