package com.scloudic.jsuite.mgr.web.controllers;

import com.scloudic.rabbitframework.web.AbstractRabbitController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 错误处理跳转
 *
 * @since 1.0
 */
@Controller
@RequestMapping("/error")
public class ErrorController extends AbstractRabbitController {
    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String sys404() {
        return "404";
    }

    @RequestMapping(value = "500", method = RequestMethod.GET)
    public String sys500() {
        return "500";
    }

    /**
     * 用户没有权限跳转
     *
     * @return
     */
    @RequestMapping(value = "unauthorized", method = RequestMethod.GET)
    public String unauthorized() {
        return "unauthorized";
    }
}
