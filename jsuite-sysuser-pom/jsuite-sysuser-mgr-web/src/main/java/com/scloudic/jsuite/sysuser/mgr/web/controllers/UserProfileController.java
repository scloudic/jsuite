package com.scloudic.jsuite.sysuser.mgr.web.controllers;


import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.file.api.model.FileBaseInfo;
import com.scloudic.jsuite.file.api.service.FileService;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysDept;
import com.scloudic.jsuite.sysuser.mgr.entity.SysPost;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUser;
import com.scloudic.jsuite.sysuser.mgr.service.SysDeptService;
import com.scloudic.jsuite.sysuser.mgr.service.SysPostService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserRoleService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserService;
import com.scloudic.jsuite.sysuser.mgr.web.component.JsuiteSysUserProperties;
import com.scloudic.jsuite.sysuser.mgr.web.model.SysUserDto;
import com.scloudic.jsuite.sysuser.mgr.web.model.UpdatePwdDto;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.PasswordUtils;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 用户个人信息
 *
 * @since 1.8
 */
@RestController
@RequestMapping("/jsuite/user")
public class UserProfileController extends AbstractRabbitController {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysPostService sysPostService;
    @Autowired
    private FileService fileService;
    @Autowired
    private JsuiteSysUserProperties jsuiteSysUserProperties;

    @GetMapping("profile")
    @UserAuthentication
    @Log(operatorType = Log.OperateType.SELECT, remark = "获取当前登录用户信息")
    public Result<SysUser> profile() {
        String userId = SecurityUtils.getUserId();
        SysUser sysUser = sysUserService.getSysUserByUserId(userId,
                Enums.ActiveStatus.OPEN.getValue(), Enums.DelStatus.NORMAL.getValue());
        if (sysUser == null) {
            return Result.failure("当前用户不存在");
        }
        Integer postId = sysUser.getPostId();
        SysPost sysPost = sysPostService.selectById(postId);
        if (sysPost != null) {
            sysUser.setPostName(sysPost.getPostName());
        }
        SysDept sysDept = sysDeptService.selectById(sysUser.getDeptId());
        sysUser.setDeptNames(sysDept.getDeptNames());
        List<SysRole> sysRoles = sysUserRoleService.findSysRoleByUserId(userId);
        sysUser.setSysRoles(sysRoles);
        return success(sysUser);
    }

    @PostMapping("updatePwd")
    @UserAuthentication
    @FormValid
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改密码")
    public Result updatePwd(@RequestBody UpdatePwdDto updatePwdDto) {
        SysUser tmpSysUser = sysUserService.selectById(SecurityUtils.getUserId());
        String currLoginPwd = tmpSysUser.getLoginPwd();
        if (!PasswordUtils.verify(updatePwdDto.getSrcPwd(), currLoginPwd)) {
            throw new BizException("原密码不对,请重新输入");
        }
        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(SecurityUtils.getUserId());
        sysUser.setLoginPwd(PasswordUtils.generate(updatePwdDto.getNewPwd()));
        sysUserService.updateByEntity(sysUser);
        return success();
    }

    @PostMapping("update")
    @FormValid
    @UserAuthentication
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改当前登录用户信息")
    public Result update(@RequestBody SysUserDto sysUserDto) {
        String userId = SecurityUtils.getUserId();
        SysUser sysUser = sysUserService.getSysUserByUserId(userId);
        if (sysUser == null) {
            throw new BizException("用户不存在！");
        }
        SysUser updateUser = new SysUser();
        updateUser.setSysUserId(userId);
        updateUser.setRealName(sysUserDto.getRealName());
        updateUser.setUserPhone(sysUserDto.getUserPhone());
        updateUser.setUserMail(sysUserDto.getUserMail());
        updateUser.setGender(sysUserDto.getGender());
        sysUserService.updateByEntity(updateUser);
        return success();
    }

    /**
     * 头像上传
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("avatar")
    @UserAuthentication
    public Result<String> upload(@RequestPart("file") MultipartFile multipartFile) {
        InputStream is = null;
        try {
            String fileCategoryName = jsuiteSysUserProperties.getAvatarPath();
            String userId = SecurityUtils.getUserId();
            SysUser sysUser = sysUserService.getSysUserByUserId(userId);
            if (sysUser == null) {
                throw new BizException("用户不存在！");
            }
            is = multipartFile.getInputStream();
            String srcFileName = multipartFile.getOriginalFilename(); // 文件名
            FileBaseInfo fileBaseInfo = fileService.fileUpload(fileCategoryName, srcFileName, is);
            SysUser updateUser = new SysUser();
            updateUser.setSysUserId(userId);
            updateUser.setAvatarPath(fileBaseInfo.getHttpUrl());
            sysUserService.updateByEntity(updateUser);
            return success(fileBaseInfo.getHttpUrl());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload.file.error");
        } finally {
            IOUtils.closeQuietly(is, null);
        }
    }
}