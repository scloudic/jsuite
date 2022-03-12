package com.scloudic.jsuite.mgr.web.controllers.admin;

import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitContextController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/dialog")
public class DialogController extends AbstractRabbitContextController {
    @Autowired
    private MgrJsuiteProperties mgrJsuiteProperties;

    @RequestMapping(value = "fileDialog", method = RequestMethod.GET)
    @UserAuthentication
    public ModelAndView fileDialog(@RequestParam(value = "fileType", required = false, defaultValue = "") String fileType) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileType", fileType);
        return new ModelAndView("dialog/fileDialog", params);
    }
}
