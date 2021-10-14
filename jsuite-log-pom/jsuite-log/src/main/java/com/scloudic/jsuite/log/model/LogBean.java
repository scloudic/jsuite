package com.scloudic.jsuite.log.model;

import java.io.Serializable;
import java.util.Date;

public class LogBean implements Serializable {
    /**
     * description:日志备注
     */
    private String logRemark;

    /**
     * description:方法全称
     */
    private String methodFullName;

    /**
     * description:操作人名称
     */
    private String userName;

    /**
     * description:创建时间
     */
    private Date createTime;

    /**
     * description:方法名称
     */
    private String methodName;

    /**
     * description:
     */
    private String returnResult;

    /**
     * description:操作人
     */
    private String userId;

    /**
     * description:操作登录名称
     */
    private String loginName;

    /**
     * description:日志内容
     */
    private String content;

    /**
     * description:操作类型
     */
    private String operateType;


    public String getLogRemark() {
        return logRemark;
    }

    public void setLogRemark(String logRemark) {
        this.logRemark = logRemark;
    }

    public String getMethodFullName() {
        return methodFullName;
    }

    public void setMethodFullName(String methodFullName) {
        this.methodFullName = methodFullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

}
