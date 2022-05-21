package com.scloudic.jsuite.sysuser.mgr.web.model;

import javax.validation.constraints.NotBlank;

public class UpdatePwdDto {
    @NotBlank
    private String srcPwd;
    @NotBlank
    private String newPwd;

    public String getSrcPwd() {
        return srcPwd;
    }

    public void setSrcPwd(String srcPwd) {
        this.srcPwd = srcPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
