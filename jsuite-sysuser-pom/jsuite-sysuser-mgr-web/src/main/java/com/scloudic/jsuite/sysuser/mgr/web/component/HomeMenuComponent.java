package com.scloudic.jsuite.sysuser.mgr.web.component;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserRoleService;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理端获取主页菜单信息组件
 *
 * @since 1.0
 */
@Component
public class HomeMenuComponent {
    private static final Logger logger = LoggerFactory.getLogger(HomeMenuComponent.class);
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    public List<Map<String, Object>> homeMenu(Long parentMenuId) {
        List<Map<String, Object>> result = new ArrayList<>();
        long userId = StringUtils.stringToLong(SecurityUtils.getUserId(), 0);
        if (userId == 0) {
            logger.warn("当前用户没有登录,不能获取功能菜单信息");
            return result;
        }
        Where where = new Where();
        where.createCriteria().andEqual(SysUserRole.SYS_USER_ID, userId);
        List<SysUserRole> sysUserRoles = sysUserRoleService.selectByParams(where);
        if (sysUserRoles.size() > 0) {
            List<Long> roleIds = new ArrayList<Long>();
            int userRoleSize = sysUserRoles.size();
            for (int i = 0; i < userRoleSize; i++) {
                roleIds.add(sysUserRoles.get(i).getSysRoleId());
            }
            result = findRoleMenuByParentId(parentMenuId, roleIds);
        }
        return result;
    }

    private List<Map<String, Object>> findRoleMenuByParentId(Long parentMenuId, List<Long> roleIds) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<SysMenu> menus = sysMenuService.findMenuByRole(roleIds, parentMenuId, Enums.BtnFlag.MENU.getValue());
        for (SysMenu menu : menus) {
            Long parentId = menu.getSysMenuId();
            String icon = menu.getIconPath();
            String path = menu.getFrontEndUrl();
            Map<String, Object> hashMap = new HashMap<>();
            Integer childMenuNum = sysMenuService.getCountMenuByRole(roleIds, parentId, Enums.BtnFlag.MENU.getValue());
            hashMap.put("path", path == null ? "" : path);
            hashMap.put("name", menu.getMenuName());
            hashMap.put("menuId", menu.getSysMenuId());
            hashMap.put("menuLevel", menu.getMenuLevel());
            hashMap.put("menuCode", menu.getMenuCode());
            hashMap.put("icon", icon == null ? "" : icon);
            if (childMenuNum.intValue() > 0) {
                hashMap.put("children", findRoleMenuByParentId(parentId, roleIds));
            } else {
                hashMap.put("children", new ArrayList<Map<String, Object>>());
            }
            result.add(hashMap);
        }
        return result;
    }

    public List<Map<String, Object>> findRoleMenuByMenuParentId(Long parentMenuId) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        long userId = StringUtils.stringToLong(SecurityUtils.getUserId(), 0);
        if (userId == 0) {
            logger.warn("当前用户没有登录,不能获取功能菜单信息");
            return result;
        }
        Long parentId = parentMenuId;
        if (-1L == parentMenuId.longValue()) {
            List<SysMenu> menus = sysMenuService.findChildMenuByParentMenuId(parentMenuId, Enums.BtnFlag.MENU.getValue());
            for (SysMenu menu : menus) {
                String icon = menu.getIconPath();
                String path = menu.getFrontEndUrl();
                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("path", path == null ? "" : path);
                hashMap.put("name", menu.getMenuName());
                hashMap.put("menuId", menu.getSysMenuId());
                hashMap.put("menuCode", menu.getMenuCode());
                hashMap.put("icon", icon == null ? "" : icon);
                hashMap.put("menuLevel", menu.getMenuLevel());
                List<SysMenu> childMenus = sysMenuService.findChildMenuByParentMenuId(menu.getSysMenuId(), Enums.BtnFlag.MENU.getValue());
                hashMap.put("childMenuNum", childMenus.size());
                result.add(hashMap);
            }
            return result;
        }
        Where where = new Where();
        where.createCriteria().andEqual(SysUserRole.SYS_USER_ID, userId);
        List<SysUserRole> sysUserRoles = sysUserRoleService.selectByParams(where);
        if (sysUserRoles.size() > 0) {
            List<Long> roleIds = new ArrayList<Long>();
            int userRoleSize = sysUserRoles.size();
            for (int i = 0; i < userRoleSize; i++) {
                roleIds.add(sysUserRoles.get(i).getSysRoleId());
            }
            List<SysMenu> menus = sysMenuService.findMenuByRole(roleIds, parentId, Enums.BtnFlag.MENU.getValue());
            for (SysMenu menu : menus) {
                String icon = menu.getIconPath();
                String path = menu.getFrontEndUrl();
                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("path", path == null ? "" : path);
                hashMap.put("name", menu.getMenuName());
                hashMap.put("menuId", menu.getSysMenuId());
                hashMap.put("menuCode", menu.getMenuCode());
                hashMap.put("icon", icon == null ? "" : icon);
                hashMap.put("menuLevel", menu.getMenuLevel());
                List<SysMenu> childMenus = sysMenuService.findMenuByRole(roleIds, menu.getSysMenuId(), Enums.BtnFlag.MENU.getValue());
                hashMap.put("childMenuNum", childMenus.size());
                result.add(hashMap);
            }
        }
        return result;
    }
}
