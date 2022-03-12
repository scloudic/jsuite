package com.scloudic.jsuite.mgr.web.controllers.admin;

import com.scloudic.jsuite.common.api.web.component.CaptchaVerify;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.jsuite.log.notification.OperateLogEvent;
import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.rabbitframework.core.notification.NotificationServerManager;
import com.scloudic.rabbitframework.core.utils.CommonResponseUrl;
import com.scloudic.rabbitframework.security.LoginFailException;
import com.scloudic.rabbitframework.security.SecurityUser;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import com.scloudic.rabbitframework.web.utils.WebUtils;
import org.glassfish.jersey.server.mvc.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.*;

@Controller
@RequestMapping("/")
public class LoginController extends AbstractContextResource {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private MgrJsuiteProperties mgrJsuiteProperties;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private NotificationServerManager notificationServerManager;
    @Autowired
    private CaptchaVerify captchaVerify;

    @Path("userLogin")
    @POST
    @Pos
    @FormValid
    public Result<Map<String, Object>> userLogin(@Context HttpServletRequest request,
                                                 @NotBlank @FormParam("verifyKey") String verifyKey,
                                                 @NotBlank @FormParam("validateCode") String validateCode,
                                                 @NotBlank @FormParam("userName") String userName,
                                                 @NotBlank @FormParam("password") String password) {
        if (!captchaVerify.verify(request, verifyKey, validateCode)) {
            return Result.failure("验证码不匹配,请重新输入");
        }
        boolean isLogin = SecurityUtils.userLogin(userName, password);
        if (!isLogin) {
            LogBean operateLog = new LogBean();
            operateLog.setLogRemark("登录");
            operateLog.setReturnResult("登录失败");
            operateLog.setCreateTime(new Date());
            operateLog.setUserName(userName);
            operateLog.setLoginName(userName);
            operateLog.setOperateSource("PC");
            operateLog.setIpAddress(new WebUtils().getRemoteAddr(request));
            operateLog.setContent("登录名：" + userName);
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
        data.put("nickName", securityUser.getNickName());
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
        operateLog.setContent("登录名：" + userName);
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
    @GET
    @Path("login")
    @Produces(MediaType.TEXT_HTML)
    public Object login() {
        return new Viewable(mgrJsuiteProperties.getAdminPath() + "/login.html");
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GET
    @Path("logout")
    @UserAuthentication
    @Produces(MediaType.TEXT_HTML)
    public Object logout() {
        try {
            SecurityUtils.logout();
            return Response.temporaryRedirect(new URI(CommonResponseUrl
                    .dislodgeFirstSlash(CommonResponseUrl.getLoginUrl()))).build();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return login();
    }

}