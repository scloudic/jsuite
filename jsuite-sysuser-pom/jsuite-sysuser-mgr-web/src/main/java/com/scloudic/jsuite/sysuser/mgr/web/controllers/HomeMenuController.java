package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.jsuite.sysuser.mgr.web.component.HomeMenuComponent;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
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


    /**
     * 功能首页跳转，主要用于前后端不分离，通过后台调用跳转到不同的前端首页
     *
     * @param menuCode 菜单编码
     * @param params   传参参数
     * @return 跳转界面
     */
    @RequestMapping(value = "funIndex/{menuCode}", method = RequestMethod.GET)
    @UserAuthentication
    @FormValid
    public ModelAndView funIndex(@NotBlank @PathVariable("menuCode") String menuCode,
                                 @RequestParam(value = "params", required = false, defaultValue = "") String params) {
        if (StringUtils.isBlank(menuCode)) {
            throw new BizException("菜单标识不能为空!");
        }
        Where where = new Where();
        where.createCriteria().andEqual(SysMenu::getMenuCode, menuCode);
        SysMenu sysMenu = sysMenuService.selectOneByParams(where);
        if (sysMenu == null) {
            throw new BizException("menu.null");
        }
        Map<String, String> paramsMap = null;
        if (StringUtils.isNotBlank(sysMenu.getFrontEndParamName())) {
            paramsMap = new HashMap<>();
            paramsMap.put(sysMenu.getFrontEndParamName(), params);
        }
        return new ModelAndView(sysMenu.getFrontEndUrl(), paramsMap);
    }
}
