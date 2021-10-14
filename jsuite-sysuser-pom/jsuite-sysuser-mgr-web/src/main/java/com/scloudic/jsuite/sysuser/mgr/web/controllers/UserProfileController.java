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
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.DateFormatUtil;
import com.scloudic.rabbitframework.core.utils.PasswordUtils;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
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
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 用户个人信息
 *
 * @since 1.8
 */
@Component
@Path("/jsuite/user")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class UserProfileController extends AbstractContextResource {
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

    @GET
    @Path("profile")
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


    @POST
    @Path("updatePwd")
    @UserAuthentication
    @FormValid
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改密码")
    public Result updatePwd(@NotBlank @FormParam("srcPwd") String srcPwd,
                            @NotBlank @FormParam("newPwd") String newPwd) {
        SysUser tmpSysUser = sysUserService.selectById(SecurityUtils.getUserId());
        String currLoginPwd = tmpSysUser.getLoginPwd();
        if (!PasswordUtils.verify(srcPwd, currLoginPwd)) {
            throw new BizException("原密码不对,请重新输入");
        }
        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(SecurityUtils.getUserId());
        sysUser.setLoginPwd(PasswordUtils.generate(newPwd));
        sysUserService.updateByEntity(sysUser);
        return success();
    }

    @POST
    @Path("update")
    @FormValid
    @UserAuthentication
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改当前登录用户信息")
    public Result update(@Context HttpServletRequest request,
                         @NotBlank @FormParam("realName") String realName,
                         @NotBlank @FormParam("userPhone") String userPhone,
                         @FormParam("userMail") String userMail,
                         @DefaultValue("3") @FormParam("gender") Integer gender) {
        String userId = SecurityUtils.getUserId();
        SysUser sysUser = sysUserService.getSysUserByUserId(userId);
        if (sysUser == null) {
            throw new BizException("用户不存在！");
        }
        SysUser updateUser = new SysUser();
        updateUser.setSysUserId(userId);
        updateUser.setRealName(realName);
        updateUser.setUserPhone(userPhone);
        updateUser.setUserMail(userMail);
        updateUser.setGender(gender);
        sysUserService.updateByEntity(updateUser);
        return success();
    }

    /**
     * 头像上传
     *
     * @param form form
     * @return
     */
    @POST
    @Path("avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @FormValid
    @UserAuthentication
    public Result<String> upload(FormDataMultiPart form) {
        InputStream is = null;
        try {
            String fileCategoryName = jsuiteSysUserProperties.getAvatarPath();
            List<FormDataBodyPart> bodyParts = form.getFields("files");
            if (bodyParts == null || bodyParts.size() <= 0) {
                return failure("文件为空");
            }
            String userId = SecurityUtils.getUserId();
            SysUser sysUser = sysUserService.getSysUserByUserId(userId);
            if (sysUser == null) {
                throw new BizException("用户不存在！");
            }
            FormDataBodyPart bodyPart = bodyParts.get(0);
            is = bodyPart.getValueAs(InputStream.class);
            FormDataContentDisposition formDataContentDisposition = bodyPart.getFormDataContentDisposition();
            String srcFileName = formDataContentDisposition.getFileName(); // 文件名
            MediaType mediaType = bodyPart.getMediaType();
            String fileMediaType = mediaType.getType() + File.separator + mediaType.getSubtype();
            FileBaseInfo fileBaseInfo = fileService.fileUpload(fileCategoryName, srcFileName, is, fileMediaType);
            SysUser updateUser = new SysUser();
            updateUser.setSysUserId(userId);
            updateUser.setAvatarPath(fileBaseInfo.getHttpUrl());
            sysUserService.updateByEntity(updateUser);
            return success(fileBaseInfo.getHttpUrl());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload.file.error");
        } finally {
            if (is != null) {
                IOUtils.closeQuietly(is);
            }
        }
    }
}
