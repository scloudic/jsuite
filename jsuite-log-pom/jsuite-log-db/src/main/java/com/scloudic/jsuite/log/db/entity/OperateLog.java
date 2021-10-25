package com.scloudic.jsuite.log.db.entity;

import com.scloudic.rabbitframework.jbatis.annontations.Column;
import com.scloudic.rabbitframework.jbatis.annontations.ID;
import com.scloudic.rabbitframework.jbatis.annontations.Table;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table operate_log
 */
@Table
public class OperateLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column operate_log.operate_log_id
     * <p>
     * description:操作日志主键
     */
    @ID(keyType = GenerationType.MANUAL)
    private String operateLogId;

    /**
     * This field corresponds to the database column operate_log.log_remark
     * <p>
     * description:日志备注
     */
    @Column
    private String logRemark;

    /**
     * This field corresponds to the database column operate_log.method_full_name
     * <p>
     * description:方法全称
     */
    @Column
    private String methodFullName;

    /**
     * This field corresponds to the database column operate_log.user_name
     * <p>
     * description:操作人名称
     */
    @Column
    private String userName;

    /**
     * This field corresponds to the database column operate_log.create_time
     * <p>
     * description:创建时间
     */
    @Column
    private Date createTime;

    /**
     * This field corresponds to the database column operate_log.method_name
     * <p>
     * description:方法名称
     */
    @Column
    private String methodName;

    /**
     * This field corresponds to the database column operate_log.return_result
     * <p>
     * description:
     */
    @Column
    private String returnResult;

    /**
     * This field corresponds to the database column operate_log.user_id
     * <p>
     * description:操作人
     */
    @Column
    private String userId;

    /**
     * This field corresponds to the database column operate_log.login_name
     * <p>
     * description:操作登录名称
     */
    @Column
    private String loginName;

    /**
     * This field corresponds to the database column operate_log.content
     * <p>
     * description:日志内容
     */
    @Column
    private String content;

    /**
     * This field corresponds to the database column operate_log.operate_type
     * <p>
     * description:操作类型
     */
    @Column
    private String operateType;


    @Column
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getOperateLogId() {
        return operateLogId;
    }

    public void setOperateLogId(String operateLogId) {
        this.operateLogId = operateLogId;
    }

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
