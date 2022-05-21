package com.scloudic.jsuite.sysuser.mgr.service.impl;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.sysuser.mgr.entity.SysPost;
import com.scloudic.jsuite.sysuser.mgr.mapper.SysPostMapper;
import com.scloudic.jsuite.sysuser.mgr.service.SysPostService;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.RowBounds;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysPostServiceImpl extends IServiceImpl<SysPostMapper, SysPost> implements SysPostService {
    @Autowired
    private SysPostMapper sysPostMapper;

    @Override
    public SysPostMapper getBaseMapper() {
        return sysPostMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public PageBean<SysPost> listPage(String postName, Long pageNum, Long pageSize) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SysPost::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        criteria.andEqual(StringUtils.isNotBlank(postName), SysPost::getPostName, postName);
        long totalCount = sysPostMapper.selectCountByParams(where);
        PageBean<SysPost> pageBean = new PageBean<SysPost>(pageNum, pageSize, totalCount);
        pageBean.setDatas(new ArrayList<SysPost>());
        if (totalCount > 0) {
            where.orderByAsc(SysPost::getSortNum);
            RowBounds rowBounds = new RowBounds(pageBean.getStartPage(), pageBean.getPageSize());
            List<SysPost> sysPosts = sysPostMapper.selectPageByParams(where, rowBounds);
            pageBean.setDatas(sysPosts);
        }
        return pageBean;
    }
}

