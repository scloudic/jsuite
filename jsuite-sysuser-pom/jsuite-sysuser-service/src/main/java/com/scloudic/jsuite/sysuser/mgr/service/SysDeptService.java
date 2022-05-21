package com.scloudic.jsuite.sysuser.mgr.service;

import com.scloudic.jsuite.sysuser.mgr.entity.SysDept;
import com.scloudic.rabbitframework.jbatis.service.IService;

public interface SysDeptService extends IService<SysDept> {
    public void updateDept(SysDept sysDept);

}

