package com.scloudic.jsuite.sysuser.mgr.mapper;

import com.scloudic.jsuite.sysuser.mgr.entity.SysDept;
import com.scloudic.rabbitframework.jbatis.annontations.MapKey;
import com.scloudic.rabbitframework.jbatis.annontations.Select;
import com.scloudic.rabbitframework.jbatis.mapping.BaseMapper;
import com.scloudic.rabbitframework.jbatis.annontations.Mapper;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;

import java.util.List;
import java.util.Map;

/**
 * database table sys_dept mapper interface
 **/
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    @Select("select * from sys_dept where 1=1")
    @MapKey("deptId")
    public Map<Integer, SysDept> findSysDeptByWhere(Where where);
}
