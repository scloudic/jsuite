package com.scloudic.jsuite.log.db.mapper;

import com.scloudic.jsuite.log.db.entity.OperateLog;
import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.rabbitframework.jbatis.annontations.Mapper;
import com.scloudic.rabbitframework.jbatis.annontations.Select;
import com.scloudic.rabbitframework.jbatis.mapping.BaseMapper;
import com.scloudic.rabbitframework.jbatis.mapping.RowBounds;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;

import java.util.List;

/**
 * database table operate_log mapper interface
 **/
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {
    @Select("select * from operate_log where 1=1 ")
    public List<LogBean> findLogBeanByWhere(Where where, RowBounds rowBounds);
}
