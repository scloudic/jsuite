package com.scloudic.jsuite.sysuser.mgr.service.impl;

import com.scloudic.jsuite.sysuser.mgr.entity.SysDept;
import com.scloudic.jsuite.sysuser.mgr.mapper.SysDeptMapper;
import com.scloudic.jsuite.sysuser.mgr.service.SysDeptService;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.lambda.SFunctionUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysDeptServiceImpl extends IServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public SysDeptMapper getBaseMapper() {
        return sysDeptMapper;
    }

    @Transactional
    public void updateDept(SysDept sysDept) {
        SysDept tempDept = sysDeptMapper.selectById(sysDept.getDeptId());

        if (sysDept.getDeptId().intValue() != 1 && !tempDept.getDeptName().equals(sysDept.getDeptName())) {
            String deptNames = tempDept.getDeptNames();
            sysDept.setDeptNames(StringUtils.replace(deptNames, tempDept.getDeptName(), sysDept.getDeptName()));
            Where where = new Where();
            Criteria criteria = where.createCriteria();
            String filedName = SFunctionUtils.getFieldName(SysDept::getDeptIds);
            criteria.andGreaterThan("find_in_set(" + sysDept.getDeptId() + "," + filedName + ")", 0);
            criteria.andNotEqual(SysDept::getDeptId, sysDept.getDeptId());
            List<SysDept> sysDepts = sysDeptMapper.selectByParams(where);
            for (SysDept dept : sysDepts) {
                String dns = dept.getDeptNames();
                dept.setDeptNames(StringUtils.replace(dns, tempDept.getDeptName(), sysDept.getDeptName()));
                sysDeptMapper.updateByEntity(dept);
            }

            sysDeptMapper.updateByEntity(sysDept);
        }
    }
}

