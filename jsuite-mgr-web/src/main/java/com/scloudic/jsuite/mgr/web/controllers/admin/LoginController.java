package com.scloudic.jsuite.mgr.web.controllers.admin;

import com.scloudic.jsuite.common.api.web.component.CaptchaVerify;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.jsuite.log.notification.OperateLogEvent;
import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.jsuite.mgr.web.model.UserLoginDto;
import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.rabbitframework.core.notification.NotificationServerManager;
import com.scloudic.rabbitframework.security.LoginFailException;
import com.scloudic.rabbitframework.security.SecurityUser;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import com.scloudic.rabbitframework.web.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/")
public class LoginController extends AbstractRabbitController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private MgrJsuiteProperties mgrJsuiteProperties;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private NotificationServerManager notificationServerManager;
    @Autowired
    private CaptchaVerify captchaVerify;

    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    @ResponseBody
    @FormValid
    public Result<Map<String, Object>> userLogin(HttpServletRequest request, @RequestBody UserLoginDto userLoginDto) {
        if (!captchaVerify.verify(request, userLoginDto.getVerifyKey(), userLoginDto.getValidateCode())) {
            return Result.failure("验证码不匹配,请重新输入");
        }
        boolean isLogin = SecurityUtils.userLogin(userLoginDto.getUserName(), userLoginDto.getPassword());
        if (!isLogin) {
            LogBean operateLog = new LogBean();
            operateLog.setLogRemark("登录");
            operateLog.setReturnResult("登录失败");
            operateLog.setCreateTime(new Date());
            operateLog.setUserName(userLoginDto.getUserName());
            operateLog.setLoginName(userLoginDto.getUserName());
            operateLog.setOperateSource("PC");
            operateLog.setIpAddress(new WebUtils().getRemoteAddr(request));
            operateLog.setContent("登录名：" + userLoginDto.getUserName());
            operateLog.setMethodFullName(LoginController.class.getName() + ".userLogin");
            operateLog.setOperateType(Log.OperateType.LOGIN.value);
            operateLog.setMethodName("userLogin");
            notificationServerManager.fireEvent(new OperateLogEvent(operateLog, 0));
            throw new LoginFailException("login.error");
        }
        Map<String, Object> data = new HashMap<String, Object>();
        String authorization = SecurityUtils.getSessionId();
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        String userId = securityUser.getUserId();
        logger.debug("authorization:{}", authorization);
        data.put("authorization", authorization);
        data.put("sysUserId", userId);
        data.put("loginName", securityUser.getLoginName());
        data.put("realName", securityUser.getRealName());
        List<String> permissions = new ArrayList<>();
        List<SysMenu> menus = sysMenuService.findUserRoleMenuByUserId(userId, Enums.BtnFlag.BUTTON.getValue());
        int menuSize = menus.size();
        for (int i = 0; i < menuSize; i++) {
            String url = menus.get(i).getBackEndUrl();
            permissions.add(url);
        }
        data.put("permissions", permissions);
        LogBean operateLog = new LogBean();
        operateLog.setLogRemark("登录");
        operateLog.setReturnResult("登录成功");
        operateLog.setOperateSource("PC");
        operateLog.setUserId(userId);
        operateLog.setCreateTime(new Date());
        operateLog.setUserName(securityUser.getRealName());
        operateLog.setLoginName(securityUser.getLoginName());
        operateLog.setIpAddress(new WebUtils().getRemoteAddr(request));
        operateLog.setContent("登录名：" + userLoginDto.getUserName());
        operateLog.setMethodFullName(LoginController.class.getName() + ".userLogin");
        operateLog.setOperateType(Log.OperateType.LOGIN.value);
        operateLog.setMethodName("userLogin");
        notificationServerManager.fireEvent(new OperateLogEvent(operateLog, 0));
        return success(data);
    }

    /**
     * 跳转到登录界面
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @UserAuthentication
    public String logout() {
        try {
            SecurityUtils.logout();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return "redirect:login";
    }

}