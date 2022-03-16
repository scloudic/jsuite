package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.core.configure.JsuiteProperties;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUser;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.jsuite.sysuser.mgr.service.SysRoleService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserService;
import com.scloudic.jsuite.sysuser.mgr.web.component.JsuiteSysUserProperties;
import com.scloudic.jsuite.sysuser.mgr.web.model.SysUserDto;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.*;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.security.realm.SecurityAuthorizingRealm;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import com.scloudic.rabbitframework.web.utils.ServletContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jsuite/userMgr")
public class UserController extends AbstractRabbitController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private JsuiteProperties jsuiteProperties;
    @Autowired
    private JsuiteSysUserProperties jsuiteSysUserProperties;

    /**
     * 分页查询用户信息
     *
     * @param name         name
     * @param userPhone    用户手机号
     * @param activeStatus 启用状态(1、正常,2、停用)
     * @param startDate    开始时间，yyyy-MM-dd
     * @param endDate      结束时间，yyyy-MM-dd
     * @param pageNum      开始页，默认1
     * @param pageSize     结束页，默认15
     * @return
     */
    @GetMapping("findUser")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询用户信息")
    public Result<PageBean<SysUser>> findUser(@RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "userPhone", required = false) String userPhone,
                                              @RequestParam(value = "activeStatus", required = false) Integer activeStatus,
                                              @RequestParam(value = "startDate", required = false) String startDate,
                                              @RequestParam(value = "endDate", required = false) String endDate,
                                              @RequestParam(value = "pageNum", required = false) Long pageNum,
                                              @RequestParam(value = "pageSize", required = false) Long pageSize) {
        if (activeStatus != null && activeStatus.intValue() == 0) {
            activeStatus = null;
        }
        PageBean<SysUser> sysUserPageBean = sysUserService.findUserInfoByParams(name, userPhone, pageNum,
                pageSize, activeStatus, startDate, endDate, jsuiteSysUserProperties.isShowAdmin());
        return success(sysUserPageBean);
    }

    @PostMapping("saveUser")
    @FormValid(fieldFilter = {"sysUserId", "activeStatus"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加用户信息")
    public Result<String> saveUser(@RequestBody SysUserDto sysUserDto) {
        SysUser tmpSysUser = sysUserService.getSysUserByLoginName(sysUserDto.getLoginName());
        if (tmpSysUser != null) {
            throw new BizException("user.already.exists");
        }
        Date createTime = new Date();
        SysUser saveUser = new SysUser();
        saveUser.setSysUserId(UUIDUtils.getTimeUUID32());
        saveUser.setCreateTime(createTime);
        saveUser.setAvatarPath(sysUserDto.getAvatarPath());
        saveUser.setPostId(sysUserDto.getPostId() == null ? 1 : sysUserDto.getPostId());
        saveUser.setDelStatus(Enums.DelStatus.NORMAL.getValue());
        saveUser.setActiveStatus(Enums.DelStatus.NORMAL.getValue());
        saveUser.setLoginName(sysUserDto.getLoginName());
        saveUser.setRealName(sysUserDto.getRealName());
        saveUser.setDeptId(sysUserDto.getDeptId() == null ? 1 : sysUserDto.getDeptId());
        saveUser.setUserPhone(sysUserDto.getUserPhone());
        saveUser.setUserMail(sysUserDto.getUserMail());
        saveUser.setGender(sysUserDto.getGender() == null ? 3 : sysUserDto.getGender());
        saveUser.setUpdateTime(createTime);
        saveUser.setLoginPwd(PasswordUtils.generate("888888"));
        sysUserService.saveSysUserAndRoles(saveUser, sysUserDto.getRoleIds());
        return success(saveUser.getSysUserId());
    }


    @PostMapping("updateUser")
    @FormValid(fieldFilter = {"activeStatus", "loginName"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改用户信息")
    public Result<String> updateUser(@RequestBody SysUserDto sysUserDto) {
        SysUser sysUser = sysUserService.getSysUserByUserId(sysUserDto.getSysUserId());
        if (sysUser == null) {
            throw new BizException("user.not.exists");
        }

        SysUser updateUser = new SysUser();
        updateUser.setSysUserId(sysUserDto.getSysUserId());
        updateUser.setRealName(sysUserDto.getRealName());
        updateUser.setPostId(sysUserDto.getPostId());
        updateUser.setAvatarPath(sysUserDto.getAvatarPath());
        updateUser.setUserPhone(sysUserDto.getUserPhone());
        updateUser.setGender(sysUserDto.getGender());
        updateUser.setUserMail(sysUserDto.getUserMail());
        updateUser.setUpdateTime(new Date());
        updateUser.setDeptId(sysUserDto.getDeptId());

        List<SysUserRole> userRoles = new ArrayList<SysUserRole>();
        if (!CollectionUtils.isEmpty(sysUserDto.getRoleIds())) {
            List<SysRole> sysRoles = sysRoleService.findSysRoleByRoleIds(sysUserDto.getRoleIds());
            for (SysRole sysRole : sysRoles) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setSysUserRoleId(UUIDUtils.getTimeUUID32());
                sysUserRole.setSysUserId(sysUserDto.getSysUserId());
                sysUserRole.setRoleCode(sysRole.getRoleCode());
                sysUserRole.setSysRoleId(sysRole.getSysRoleId());
                userRoles.add(sysUserRole);
            }
        }
        sysUserService.updateSysUserAndRoles(updateUser, userRoles);
        String realmServiceName = jsuiteProperties.getRealmServiceName();
        if (userRoles.size() > 0 && StringUtils.isNotBlank(realmServiceName)) {
            SecurityAuthorizingRealm securityAuthorizingRealm = (SecurityAuthorizingRealm) ServletContextHelper.getBean(realmServiceName);
            securityAuthorizingRealm.cleanAuthorizationCache(sysUserDto.getSysUserId());
        }
        return success(sysUserDto.getSysUserId());
    }

    @PostMapping("updateActiveStatus")
    @FormValid(fieldFilter = {"loginName", "userPhone", "sysUserId", "realName"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改用户启用状态")
    public Result<String> updateActiveStatus(@RequestBody SysUserDto sysUserDto) {
        SysUser updateUser = new SysUser();
        updateUser.setSysUserId(sysUserDto.getSysUserId());
        if (Enums.ActiveStatus.getActiveStatus(sysUserDto.getActiveStatus()) == null) {
            throw new BizException("activeStatus.null");
        }
        updateUser.setActiveStatus(sysUserDto.getActiveStatus());
        updateUser.setUpdateTime(new Date());
        sysUserService.updateByEntity(updateUser);
        return success(sysUserDto.getSysUserId());
    }


    /**
     * 逻辑删除用户
     *
     * @return json
     */
    @PostMapping("delUser")
    @FormValid(fieldFilter = {"loginName", "userPhone", "sysUserId", "realName", "activeStatus"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "删除用户")
    public Result delUser(@RequestBody SysUserDto dto) {
        SysUser tmpUser = sysUserService.getSysUserByUserId(dto.getSysUserId());
        if (tmpUser == null) {
            throw new BizException("user.not.found");
        }
        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(dto.getSysUserId());
        //tmpUser,删除用户修改登录名,以添加同名用户而报错
        sysUser.setLoginName(tmpUser.getLoginName() + dto.getSysUserId());
        sysUser.setDelStatus(Enums.DelStatus.DEL.getValue());
        sysUser.setUpdateTime(new Date());
        sysUserService.updateSysUser(sysUser);
        return success(dto.getSysUserId());
    }

    /**
     * 密码重置，重置后密码是：888888
     *
     * @return json
     */
    @PostMapping("pwdReset")
    @FormValid(fieldFilter = {"loginName", "userPhone", "sysUserId", "realName", "activeStatus"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "密码重置")
    public Result pwdReset(@RequestBody SysUserDto sysUserDto) {
        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(sysUserDto.getSysUserId());
        sysUser.setLoginPwd(PasswordUtils.generate("888888"));
        sysUserService.updateSysUser(sysUser);
        return success(sysUserDto.getSysUserId());
    }
}
