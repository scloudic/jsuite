package com.scloudic.jsuite.mgr.web.controllers;

import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class UserProfileIndexController extends AbstractRabbitController {
    @Autowired
    private MgrJsuiteProperties mgrJsuiteProperties;

    @RequestMapping(value = "userProfile", method = RequestMethod.GET)
    @UserAuthentication
    public String index() {
        return "admin/user/profileIndex";
    }
}
