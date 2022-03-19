package com.scloudic.jsuite.mgr.web.controllers;

import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * 主页请求控制
 *
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class AdminIndexController extends AbstractRabbitController {
    private static final Logger logger = LoggerFactory.getLogger(AdminIndexController.class);
    @Autowired
    private MgrJsuiteProperties mgrJsuiteProperties;
    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping(value = "", method = {RequestMethod.GET})
    public String index() {
        logger.debug("进入后台首页");
        return "admin/index";
    }

    /**
     * 跳转到登录界面
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }

    @RequestMapping(value = "main", method = {RequestMethod.GET})
    @UserAuthentication
    public ModelAndView main() {
        return new ModelAndView("admin/main");
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
