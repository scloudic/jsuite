package com.scloudic.jsuite.third.entity;
import java.util.Date;
import java.beans.Transient;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

@Table
public class ThirdBindInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " third_bind_id,third_bind_name,create_time,third_bind_code,third_bind_params ";

    /**
    *
    * 配置主键
    *
    */
    @ID
    private Long thirdBindId;

    /**
    *
    * 配置名称
    *
    */
    @Column
    private String thirdBindName;

    /**
    *
    * 创建时间
    *
    */
    @Column
    private Date createTime;

    /**
    *
    * 配置编码
    *
    */
    @Column
    private String thirdBindCode;

    /**
    *
    * 删除状态(1、正常,2、删除)
    *
    */
    @Column
    private Integer delStatus;

    /**
    *
    * 配置参数值(json)
    *
    */
    @Column
    private String thirdBindParams;

    public void setThirdBindId(Long thirdBindId) {
        this.thirdBindId = thirdBindId;
    }

    public Long getThirdBindId() {
        return thirdBindId;
    }

    public void setThirdBindName(String thirdBindName) {
        this.thirdBindName = thirdBindName;
    }

    public String getThirdBindName() {
        return thirdBindName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setThirdBindCode(String thirdBindCode) {
        this.thirdBindCode = thirdBindCode;
    }

    public String getThirdBindCode() {
        return thirdBindCode;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Transient
    public Integer getDelStatus() {
        return delStatus;
    }

    public void setThirdBindParams(String thirdBindParams) {
        this.thirdBindParams = thirdBindParams;
    }

    public String getThirdBindParams() {
        return thirdBindParams;
    }

}
