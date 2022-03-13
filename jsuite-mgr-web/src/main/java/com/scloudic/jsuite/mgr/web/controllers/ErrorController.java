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
    public ModelAndView sys404() {
        return new ModelAndView("error/404.html");
    }

    @RequestMapping(value = "500", method = RequestMethod.GET)
    public ModelAndView sys500() {
        return new ModelAndView("error/500.html");
    }

    /**
     * 用户没有权限跳转
     *
     * @return
     */
    @RequestMapping(value = "unauthorized", method = RequestMethod.GET)
    public ModelAndView unauthorized() {
        return new ModelAndView("/error/unauthorized.html");
    }
}
