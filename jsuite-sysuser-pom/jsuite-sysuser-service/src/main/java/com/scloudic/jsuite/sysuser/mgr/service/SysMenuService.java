package com.scloudic.jsuite.sysuser.mgr.service;

import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRoleMenu;
import com.scloudic.rabbitframework.jbatis.service.IService;

import java.util.List;

/**
 * 功能菜单服务接口类
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 根据父主键查询所有子菜单
     *
     * @param parentMenuId
     * @param menuBtnFlag  可以为空
     * @return
     */
    List<SysMenu> findChildMenuByParentMenuId(String parentMenuId, Integer menuBtnFlag);

    /**
     * 保存菜单
     *
     * @param menu
     * @return
     */
    int saveMenu(SysMenu menu);

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    int updateMenu(SysMenu menu);

    /**
     * 删除菜单,级联删除角色菜单{@link SysRoleMenu} 中的信息
     *
     * @param menuId
     * @return
     */
    int delMenu(List<String> menuId);

    /**
     * 根据角色主键获取当前角色下的子菜单
     *
     * @param roleIds
     * @param btnFlag
     * @param parentMenuId
     * @return
     */
    List<SysMenu> findMenuByRole(List<Long> roleIds, String parentMenuId, Integer btnFlag);

    /**
     * 根据角色等参数获取当前角色下的子菜单数量
     *
     * @param roleIds
     * @param parentMenuId
     * @param btnFlag
     * @return
     */
    Integer getCountMenuByRole(List<Long> roleIds, String parentMenuId, Integer btnFlag);

    /**
     * 根用户主键获取
     *
     * @param userId
     * @return
     */
    List<SysMenu> findUserRoleMenuByUserId(String userId, Integer btnFlag);


}
