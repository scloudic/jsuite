package com.scloudic.jsuite.sysuser.mgr.service;

import com.scloudic.jsuite.sysuser.mgr.entity.SysPost;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.jbatis.service.IService;

public interface SysPostService extends IService<SysPost> {
    public PageBean<SysPost> listPage(String postName, Long pageNum, Long pageSize);
}

