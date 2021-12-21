package com.scloudic.jsuite.sysuser.mgr.service.impl;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.sysuser.mgr.entity.*;
import com.scloudic.jsuite.sysuser.mgr.mapper.*;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserService;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.jbatis.mapping.RowBounds;
import com.scloudic.rabbitframework.jbatis.mapping.lambda.SFunctionUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import com.scloudic.rabbitframework.core.utils.CollectionUtils;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户信息服务接口实现类
 */
@Service
public class SysUserServiceImpl extends IServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysPostMapper sysPostMapper;


    @Override
    public SysUserMapper getBaseMapper() {
        return sysUserMapper;
    }

    @Override
    public SysUser getSysUserByLoginName(String loginName) {
        Where whereParamType = new Where();
        Criteria criteria = whereParamType.createCriteria();
        criteria.andEqual(SysUser::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        criteria.andEqual(SysUser::getLoginName, loginName);
        List<SysUser> sysUsers = sysUserMapper.selectByParams(whereParamType);
        if (sysUsers.size() > 0) {
            return sysUsers.get(0);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public PageBean<SysUser> findUserInfoByParams(String name, String userPhone, Long pageNum, Long pageSize,
                                                  Integer activeStatus, String startDate, String endDate, boolean showAdmin) {
        Where whereParamType = new Where();
        Criteria criteria = whereParamType.createCriteria();
        criteria.andEqual(SysUser::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        if (StringUtils.isNotBlank(name)) {
            whereParamType.addCreateCriteria()
                    .orEqual(SysUser::getRealName, "%" + name + "%");
        }
        if (activeStatus != null) {
            criteria.andEqual(SysUser::getActiveStatus, activeStatus);
        }
        if (!showAdmin) {
            criteria.andNotEqual(SysUser::getSysUserId, "1");
        }
        if (StringUtils.isNotBlank(userPhone)) {
            criteria.andEqual(SysUser::getUserPhone, userPhone);
        }
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            String createTimeField = SFunctionUtils.getFieldName(SysUser::getCreateTime);
            criteria.andBetween("DATE_FORMAT(" + createTimeField + ",'%Y-%m-%d')", startDate, endDate);
        }
        Long totalCount = sysUserMapper.selectCountByParams(whereParamType);
        PageBean<SysUser> pageBean = new PageBean<SysUser>(pageNum, pageSize, totalCount);
        whereParamType.setOrderBy(SysUser::getCreateTime);
        List<SysUser> sysUsers = sysUserMapper.selectPageByParams(whereParamType,
                new RowBounds(pageBean.getStartPage(), pageBean.getPageSize()));
        int sysUserSize = sysUsers.size();
        for (int i = 0; i < sysUserSize; i++) {
            SysUser sysUser = sysUsers.get(i);
            Integer deptId = sysUser.getDeptId();
            if (deptId != null) {
                SysDept sysDept = sysDeptMapper.selectById(deptId);
                if (sysDept != null) {
                    sysUser.setDeptNames(sysDept.getDeptNames());
                }
            }
            String sysUserId = sysUser.getSysUserId();
            Integer postId = sysUser.getPostId();
            SysPost sysPost = sysPostMapper.selectById(postId);
            if (sysPost != null) {
                sysUser.setPostName(sysPost.getPostName());
            }
            List<SysRole> sysRoles = sysUserRoleMapper.findSysRoleByUserId(sysUserId);
            sysUser.setSysRoles(sysRoles);
        }
        pageBean.setDatas(sysUsers);
        return pageBean;
    }

    public static void main(String[] args) {
        String deptIds = "1,2,5";
        String[] deptIdArr = StringUtils.split(deptIds, ",");
        List<String> deptIdList = Arrays.asList(deptIdArr);
        for (int i = deptIdList.size() - 1; i >= 0; i--) {
            System.out.println(deptIdList.get(i));
        }
    }

    @Transactional
    @Override
    public int saveSysUserAndRoles(SysUser sysUser, List<Long> roleIds) {
        sysUserMapper.insertByEntity(sysUser);
        if (!CollectionUtils.isEmpty(roleIds)) {
            Where paramType = new Where();
            Criteria criteria = paramType.createCriteria();
            criteria.andIn(SysRole::getSysRoleId, roleIds);
            List<SysRole> sysRoles = sysRoleMapper.selectByParams(paramType);
            List<SysUserRole> userRoles = new ArrayList<>();
            for (SysRole sysRole : sysRoles) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setSysUserRoleId(UUIDUtils.getTimeUUID32());
                sysUserRole.setSysUserId(sysUser.getSysUserId());
                sysUserRole.setRoleCode(sysRole.getRoleCode());
                sysUserRole.setSysRoleId(sysRole.getSysRoleId());
                userRoles.add(sysUserRole);
            }
            if (!CollectionUtils.isEmpty(userRoles)) {
                sysUserRoleMapper.batchInsertEntity(userRoles);
            }
        }
        return 1;
    }

    @Transactional
    @Override
    public int updateSysUserAndRoles(SysUser sysUser, List<SysUserRole> sysUserRole) {
        if (sysUser != null) {
            sysUserMapper.updateByEntity(sysUser);
        }
        if (!CollectionUtils.isEmpty(sysUserRole)) {
            Where paramType = new Where();
            Criteria criteria = paramType.createCriteria();
            criteria.andEqual(SysUserRole.SYS_USER_ID, sysUser.getSysUserId());
            sysUserRoleMapper.deleteByParams(paramType);
            sysUserRoleMapper.batchInsertEntity(sysUserRole);
        }
        return 1;
    }

    @Transactional
    @Override
    public int updateSysUser(SysUser sysUser) {
        return sysUserMapper.updateByEntity(sysUser);
    }

    @Override
    public SysUser getSysUserByUserId(String userId, Integer activityStatus, Integer delStatus) {
        if (activityStatus == null) {
            throw new BizException("用户活动状态不能为空！");
        }
        if (delStatus == null) {
            throw new BizException("删除标记不能为空!");
        }
        Where paramType = new Where();
        Criteria criteria = paramType.createCriteria();
        criteria.andEqual(SysUser::getSysUserId, userId);
        criteria.andEqual(SysUser::getDelStatus, delStatus);
        criteria.andEqual(SysUser::getActiveStatus, activityStatus);
        return sysUserMapper.selectOneByParams(paramType);
    }

    @Override
    public SysUser getSysUserByUserId(String userId) {
        return getSysUserByUserId(userId, Enums.ActiveStatus.OPEN.getValue(), Enums.DelStatus.NORMAL.getValue());
    }
}
