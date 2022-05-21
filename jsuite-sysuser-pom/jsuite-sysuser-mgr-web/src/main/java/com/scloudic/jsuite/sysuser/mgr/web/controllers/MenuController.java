package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRoleMenu;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.jsuite.sysuser.mgr.service.SysRoleMenuService;
import com.scloudic.jsuite.sysuser.mgr.web.model.MenuVo;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.*;

@RestController
@RequestMapping("/jsuite/menuMgr")
public class MenuController extends AbstractRabbitController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 查询菜单功能列表
     *
     * @param menuId 菜单主键
     * @return json
     */
    @GetMapping("findMenu")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "功能菜单tree查询")
    public Result<List<MenuVo>> findMenu(@RequestParam(value = "menuId", defaultValue = "-1", required = false) String menuId) {
        List<MenuVo> menuVos = new ArrayList<>();
        menuVos = findChildrenMainMenu(menuId, new HashMap<>());
        return success(menuVos);
    }

    /**
     * 获取菜单集合
     *
     * @param menuName   查询菜单名称
     * @param rootStatus 是否包括根级节点
     * @return json
     */
    @GetMapping("listMenu")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "功能菜单列表查询")
    public Result<List<SysMenu>> listMenu(@RequestParam(value = "menuName", required = false) String menuName,
                                          @RequestParam(value = "rootStatus", defaultValue = "2", required = false) Integer rootStatus) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();

        if (rootStatus.intValue() == 2) {
            criteria.andNotEqual(SysMenu::getParentMenuId, -1);
        }
        if (StringUtils.isNotBlank(menuName)) {
            criteria.andLike(SysMenu::getMenuName, "%" + menuName + "%");
        }
        where.orderByAsc(SysMenu::getSortNum);
        return success(sysMenuService.selectByParams(where));

    }

    private List<MenuVo> findChildrenMainMenu(String parentMenuId, Map<Long, SysRoleMenu> menuMap) {
        List<MenuVo> result = new ArrayList<MenuVo>();
        List<SysMenu> menus = sysMenuService.findChildMenuByParentMenuId(parentMenuId, null);
        for (SysMenu menu : menus) {
            String menuId = menu.getSysMenuId();
            MenuVo menuVo = new MenuVo();
            menuVo.setMenuId(menuId);
            menuVo.setMenuLevel(menu.getMenuLevel());
            menuVo.setMenuName(menu.getMenuName());
            menuVo.setParentMenuId(menu.getParentMenuId());
            menuVo.setBtnFlag(menu.getBtnFlag());
            menuVo.setIconPath(menu.getIconPath());
            menuVo.setSortNum(menu.getSortNum());
            menuVo.setMenuDesc(menu.getMenuDesc());
            menuVo.setMenuType(menu.getMenuType());
            menuVo.setTarget(menu.getTarget());
            menuVo.setCreatTime(menu.getCreateTime());
            menuVo.setBackEndUrl(menu.getBackEndUrl());
            menuVo.setMenuCode(menu.getMenuCode());
            menuVo.setFrontEndUrl(menu.getFrontEndUrl());
            if (menuMap.get(menuId) != null) {
                menuVo.setCheckStatus(true);
            }
            int childMenuNum = sysMenuService.findChildMenuByParentMenuId(menuId, null).size();
            menuVo.setChildMenuNum(childMenuNum);
            if (childMenuNum > 0) {
                menuVo.setChildren(findChildrenMainMenu(menuId, menuMap));
            } else {
                menuVo.setChildren(new ArrayList<MenuVo>());
            }
            result.add(menuVo);

        }
        return result;
    }

    @PostMapping("addMenu")
    @FormValid(fieldFilter = "sysMenuId")
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加功能菜单")
    public Result addMenu(@RequestBody SysMenu menu) {
        String menuCode = menu.getMenuCode();
        String parentMenuId = menu.getParentMenuId();
        if (menu.getTarget() == null) {
            menu.setTarget(1);
        }
        if (menu.getSortNum() == null) {
            menu.setSortNum(0);
        }
        if (menu.getMenuType() == null) {
            menu.setMenuType(1);
        }
        if (StringUtils.isBlank(menu.getMenuCode())) {
            menu.setMenuCode(null);
        }
        if (menu.getBtnFlag() == null) {
            menu.setBtnFlag(1);
        }
        if (StringUtils.isNotBlank(menuCode)) {
            Where where = new Where();
            Criteria criteria = where.createCriteria();
            criteria.andEqual(SysMenu::getMenuCode, menuCode);
            long totalCount = sysMenuService.selectCountByParams(where);
            if (totalCount > 0) {
                throw new BizException("菜单标识不能重复!");
            }
        }
        SysMenu sysMenu = sysMenuService.selectById(parentMenuId);
        Date date = new Date();
        menu.setSysMenuId(UUIDUtils.getTimeUUID32());
        menu.setMenuLevel(sysMenu.getMenuLevel().intValue() + 1);
        menu.setCreateTime(date);
        menu.setUpdateTime(date);
        sysMenuService.saveMenu(menu);
        return success();
    }

    @PostMapping("updateMenu")
    @FormValid(fieldFilter = "parentMenuId")
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改功能菜单")
    public Result updateMenu(@RequestBody SysMenu sysMenu) {
        String menuCode = sysMenu.getMenuCode();
        String menuId = sysMenu.getSysMenuId();
        if (StringUtils.isNotBlank(menuCode)) {
            Where where = new Where();
            Criteria criteria = where.createCriteria();
            criteria.andEqual(SysMenu::getMenuCode, menuCode);
            criteria.andNotEqual(SysMenu::getSysMenuId, menuId);
            long totalCount = sysMenuService.selectCountByParams(where);
            if (totalCount > 0) {
                throw new BizException("菜单标识不能重复!");
            }
        } else {
            menuCode = null;
        }
        Date date = new Date();
        SysMenu menu = new SysMenu();
        menu.setSysMenuId(menuId);
        menu.setMenuDesc(sysMenu.getMenuDesc());
        menu.setBtnFlag(sysMenu.getBtnFlag());
        menu.setFrontEndParamName(sysMenu.getFrontEndParamName());
        menu.setIconPath(sysMenu.getIconPath());
        menu.setMenuName(sysMenu.getMenuName());
        menu.setFrontEndUrl(sysMenu.getFrontEndUrl());
        menu.setBackEndUrl(sysMenu.getBackEndUrl());
        menu.setMenuCode(menuCode);
        menu.setTarget(sysMenu.getTarget());
        menu.setSortNum(sysMenu.getSortNum());
        menu.setUpdateTime(date);
        menu.setMenuType(sysMenu.getMenuType());
        sysMenuService.updateMenu(menu);
        return success();
    }

    /**
     * 删除菜单
     *
     * @param sysMenu
     * @return
     */
    @PostMapping("delMenu")
    @FormValid(fieldFilter = {"parentMenuId", "menuName"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "删除功能菜单")
    public Result<String> delMenu(@RequestBody SysMenu sysMenu) {
        String menuId = sysMenu.getSysMenuId();
        if ("1".equals(menuId)) {
            throw new BizException("can.not.menu");
        }
        List<String> delMenuId = getDelMenuId(menuId);
        delMenuId.add(menuId);
        sysMenuService.delMenu(delMenuId);
        return success(menuId);
    }

    public List<String> getDelMenuId(String menuId) {
        List<String> delMenuId = new ArrayList<>();
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SysMenu::getParentMenuId, menuId);
        List<SysMenu> sysMenus = sysMenuService.selectByParams(where);
        for (SysMenu sysMenu : sysMenus) {
            String mId = sysMenu.getSysMenuId();
            delMenuId.add(mId);
            Where wherec = new Where();
            Criteria criteriac = wherec.createCriteria();
            criteriac.andEqual(SysMenu::getParentMenuId, mId);
            if (sysMenuService.selectCountByParams(wherec) > 0) {
                delMenuId.addAll(getDelMenuId(mId));
            }
        }
        return delMenuId;
    }

    /**
     * 获取所有菜单,并根据角色主键判断菜单是否选中
     *
     * @param parentMenuId 父菜单主键
     * @param sysRoleId    角色主键
     * @return json
     */
    @GetMapping("findRoleMenu")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.SELECT, remark = "根据角色查询功能菜单")
    public Result<List<MenuVo>> findRoleMenu(@RequestParam(value = "parentMenuId", required = false, defaultValue = "1") String parentMenuId,
                                             @NotBlank @RequestParam("sysRoleId") Long sysRoleId) {
        List<MenuVo> menuVos = new ArrayList<MenuVo>();
        Map<Long, SysRoleMenu> menuMap = sysRoleMenuService.findSysRoleMenuByRoleId(sysRoleId);
        menuVos = findChildrenMainMenu(parentMenuId, menuMap);
        return success(menuVos);
    }
}
