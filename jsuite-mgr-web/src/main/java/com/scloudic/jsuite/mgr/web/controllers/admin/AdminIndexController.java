package com.scloudic.jsuite.mgr.web.controllers.admin;

import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "test", method = {RequestMethod.GET})
    //@UserAuthentication
    public ModelAndView index() {
        logger.info("index跳转页");
        return new ModelAndView("index");
    }

    @RequestMapping(value = "main", method = {RequestMethod.GET})
    @UserAuthentication
    public ModelAndView main() {
        return new ModelAndView("main");
    }
}
