package com.scloudic.jsuite.mgr.web.controllers.admin;

import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitContextController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class UserProfileIndexController extends AbstractRabbitContextController {
    @Autowired
    private MgrJsuiteProperties mgrJsuiteProperties;

    @RequestMapping(value = "userProfile", method = RequestMethod.GET)
    @UserAuthentication
    public ModelAndView index() {
        return new ModelAndView("user/profileIndex.html");
    }
}
