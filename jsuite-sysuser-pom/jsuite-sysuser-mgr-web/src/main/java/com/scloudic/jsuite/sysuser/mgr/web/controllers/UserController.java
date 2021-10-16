package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.core.configure.JsuiteProperties;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUser;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.jsuite.sysuser.mgr.service.SysDeptService;
import com.scloudic.jsuite.sysuser.mgr.service.SysRoleService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserService;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.*;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.security.realm.SecurityAuthorizingRealm;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import com.scloudic.rabbitframework.web.utils.ServletContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Path("/jsuite/userMgr")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class UserController extends AbstractContextResource {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private JsuiteProperties jsuiteProperties;
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 分页查询用户信息
     *
     * @param request      request
     * @param name         name
     * @param userPhone    用户手机号
     * @param activeStatus 启用状态(1、正常,2、停用)
     * @param startDate    开始时间，yyyy-MM-dd
     * @param endDate      结束时间，yyyy-MM-dd
     * @param pageNum      开始页，默认1
     * @param pageSize     结束页，默认15
     * @return
     */
    @GET
    @Path("findUser")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询用户信息")
    public Result<PageBean<SysUser>> findUser(@Context HttpServletRequest request,
                                              @QueryParam("name") String name,
                                              @QueryParam("userPhone") String userPhone,
                                              @DefaultValue("0") @QueryParam("activeStatus") Integer activeStatus,
                                              @QueryParam("startDate") String startDate,
                                              @QueryParam("endDate") String endDate,
                                              @QueryParam("pageNum") Long pageNum,
                                              @QueryParam("pageSize") Long pageSize) {
        if (activeStatus.intValue() == 0) {
            activeStatus = null;
        }
        PageBean<SysUser> sysUserPageBean = sysUserService.findUserInfoByParams(name, userPhone, pageNum, pageSize, activeStatus, startDate, endDate);
        return success(sysUserPageBean);
    }


    @POST
    @Path("saveUser")
    @FormValid
    @UriPermissions
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Log(operatorType = Log.OperateType.ADD, remark = "添加用户信息")
    public Result saveUser(@Context HttpServletRequest request,
                           @FormParam("avatarPath") String avatarPath,
                           @NotBlank @FormParam("loginName") String loginName,
                           @NotBlank @FormParam("realName") String realName,
                           @NotBlank @FormParam("userPhone") String userPhone,
                           @DefaultValue("1") @FormParam("postId") Integer postId,
                           @DefaultValue("1") @FormParam("deptId") Integer deptId,
                           @FormParam("userMail") String userMail,
                           @DefaultValue("3") @FormParam("gender") Integer gender,
                           @FormParam("roleIds") List<Long> roleIds) {
        SysUser tmpSysUser = sysUserService.getSysUserByLoginName(loginName);
        if (tmpSysUser != null) {
            throw new BizException("user.already.exists");
        }
        Date createTime = new Date();
        SysUser saveUser = new SysUser();
        saveUser.setSysUserId(UUIDUtils.getTimeUUID32());
        saveUser.setCreateTime(createTime);
        saveUser.setAvatarPath(avatarPath);
        saveUser.setPostId(postId);
        saveUser.setDelStatus(Enums.DelStatus.NORMAL.getValue());
        saveUser.setActiveStatus(Enums.DelStatus.NORMAL.getValue());
        saveUser.setLoginName(loginName);
        saveUser.setRealName(realName);
        saveUser.setDeptId(deptId);
        saveUser.setUserPhone(userPhone);
        saveUser.setUserMail(userMail);
        saveUser.setGender(gender);
        saveUser.setUpdateTime(createTime);
        saveUser.setLoginPwd(PasswordUtils.generate("888888"));
        sysUserService.saveSysUserAndRoles(saveUser, roleIds);
        return success();
    }


    @POST
    @Path("updateUser")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改用户信息")
    public Result updateUser(@Context HttpServletRequest request,
                             @FormParam("avatarPath") String avatarPath,
                             @NotBlank @FormParam("sysUserId") String sysUserId,
                             @NotBlank @FormParam("realName") String realName,
                             @FormParam("activeStatus") Integer activeStatus,
                             @FormParam("deptId") Integer deptId,
                             @FormParam("postId") Integer postId,
                             @NotBlank @FormParam("userPhone") String userPhone,
                             @FormParam("userMail") String userMail,
                             @DefaultValue("3") @FormParam("gender") Integer gender,
                             @FormParam("roleIds") List<Long> roleIds) {
        SysUser sysUser = sysUserService.getSysUserByUserId(sysUserId);
        if (sysUser == null) {
            throw new BizException("user.not.exists");
        }

        SysUser updateUser = new SysUser();
        updateUser.setSysUserId(sysUserId);
        updateUser.setRealName(realName);
        updateUser.setPostId(postId);
        updateUser.setAvatarPath(avatarPath);
        updateUser.setUserPhone(userPhone);
        updateUser.setGender(gender);
        updateUser.setUserMail(userMail);
        if (Enums.ActiveStatus.getActiveStatus(activeStatus) == null) {
            throw new BizException("activeStatus.null");
        }
        updateUser.setActiveStatus(activeStatus);
        updateUser.setUpdateTime(new Date());
        updateUser.setDeptId(deptId);

        List<SysUserRole> userRoles = new ArrayList<SysUserRole>();
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<SysRole> sysRoles = sysRoleService.findSysRoleByRoleIds(roleIds);
            for (SysRole sysRole : sysRoles) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setSysUserRoleId(UUIDUtils.getTimeUUID32());
                sysUserRole.setSysUserId(sysUserId);
                sysUserRole.setRoleCode(sysRole.getRoleCode());
                sysUserRole.setSysRoleId(sysRole.getSysRoleId());
                userRoles.add(sysUserRole);
            }
        }
        sysUserService.updateSysUserAndRoles(updateUser, userRoles);
        String realmServiceName = jsuiteProperties.getRealmServiceName();
        if (userRoles.size() > 0 && StringUtils.isNotBlank(realmServiceName)) {
            SecurityAuthorizingRealm securityAuthorizingRealm = (SecurityAuthorizingRealm) ServletContextHelper.getBean(realmServiceName);
            securityAuthorizingRealm.cleanAuthorizationCache(sysUserId);
        }
        return success();
    }


    @POST
    @Path("updateActiveStatus")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改用户启用状态")
    public Result updateActiveStatus(@Context HttpServletRequest request,
                                     @NotBlank @FormParam("sysUserId") String sysUserId,
                                     @NotBlank @FormParam("activeStatus") Integer activeStatus) {
        SysUser updateUser = new SysUser();
        updateUser.setSysUserId(sysUserId);
        if (Enums.ActiveStatus.getActiveStatus(activeStatus) == null) {
            throw new BizException("activeStatus.null");
        }
        updateUser.setActiveStatus(activeStatus);
        updateUser.setUpdateTime(new Date());
        sysUserService.updateByEntity(updateUser);
        return success();
    }


    /**
     * 逻辑删除用户
     *
     * @param request   request
     * @param sysUserId 用户主键
     * @return json
     */
    @POST
    @Path("delUser")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "删除用户")
    public Result delUser(@Context HttpServletRequest request,
                          @NotBlank @FormParam("sysUserId") String sysUserId) {
        SysUser tmpUser = sysUserService.getSysUserByUserId(sysUserId);
        if (tmpUser == null) {
            throw new BizException("user.not.found");
        }
        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(sysUserId);
        //tmpUser,删除用户修改登录名,以添加同名用户而报错
        sysUser.setLoginName(tmpUser.getLoginName() + sysUserId);
        sysUser.setDelStatus(Enums.DelStatus.DEL.getValue());
        sysUser.setUpdateTime(new Date());
        sysUserService.updateSysUser(sysUser);
        return success();
    }

    /**
     * 密码重置，重置后密码是：888888
     *
     * @param request   request
     * @param sysUserId 用户主键
     * @return json
     */
    @POST
    @Path("pwdReset")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "密码重置")
    public Result pwdReset(@Context HttpServletRequest request,
                           @NotBlank @FormParam("sysUserId") String sysUserId) {
        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(sysUserId);
        sysUser.setLoginPwd(PasswordUtils.generate("888888"));
        sysUserService.updateSysUser(sysUser);
        return success();
    }
}
