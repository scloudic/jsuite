package com.scloudic.jsuite.mgr.web.controllers;

import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dialog")
public class DialogController extends AbstractRabbitController {

    @RequestMapping(value = "fileDialog", method = RequestMethod.GET)
    @UserAuthentication
    public ModelAndView fileDialog(@RequestParam(value = "fileType", required = false, defaultValue = "") String fileType) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileType", fileType);
        return new ModelAndView("/admin/dialog/fileDialog", params);
    }
}
