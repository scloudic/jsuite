package com.scloudic.jsuite.mgr.web.model;

import javax.validation.constraints.NotBlank;

public class UserLoginDto {
    @NotBlank
    private String verifyKey;
    @NotBlank
    private String validateCode;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
