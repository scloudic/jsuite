package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.jsuite.sysuser.mgr.web.component.HomeMenuComponent;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 首页获取功能菜单
 */
@Controller
@RequestMapping("/jsuite/homeMenu")
public class HomeMenuController extends AbstractRabbitController {
    @Autowired
    private HomeMenuComponent homeMenuComponent;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取首页所有功能菜单
     *
     * @param parentMenuId 父菜单ID
     * @return json
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @UserAuthentication
    @ResponseBody
    public Result<List<Map<String, Object>>> homeMenu(@RequestParam(value = "parentMenuId", required = false, defaultValue = "1")
                                                              String parentMenuId) {
        List<Map<String, Object>> homeMenu = homeMenuComponent.homeMenu(parentMenuId);
        return success(homeMenu);
    }

    /**
     * 根据父菜单主键按层级加载获取当前用户可访问的功能菜单。
     *
     * @param parentMenuId 父菜单ID
     * @return json
     */
    @RequestMapping(value = "findRoleMenuByParentId", method = RequestMethod.GET)
    @UserAuthentication
    @ResponseBody
    public Result<List<Map<String, Object>>> findRoleMenuByMenuParentId(@RequestParam(value = "parentMenuId", required = false, defaultValue = "-1")
                                                                                String parentMenuId) {
        List<Map<String, Object>> result = homeMenuComponent.findRoleMenuByMenuParentId(parentMenuId);
        return success(result);
    }
}
