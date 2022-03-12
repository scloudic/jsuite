package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import javax.validation.constraints.NotBlank;

public class ProfileUpdateUser {
    @NotBlank
    private String realName;
    @NotBlank
    private String userPhone;
    @NotBlank
    private String userMail;
    private Integer gender;
}
