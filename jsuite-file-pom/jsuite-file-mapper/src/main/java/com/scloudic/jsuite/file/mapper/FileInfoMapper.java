package com.scloudic.jsuite.file.mapper;

import com.scloudic.jsuite.file.entity.FileInfo;
import com.scloudic.rabbitframework.jbatis.annontations.Insert;
import com.scloudic.rabbitframework.jbatis.annontations.Mapper;
import com.scloudic.rabbitframework.jbatis.annontations.Select;
import com.scloudic.rabbitframework.jbatis.mapping.BaseMapper;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;

import java.util.List;

/**
 * database table file_info mapper interface
 **/
@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {

    @Select("select count(*) from file_info fi left join file_category fc " +
            "on fi.file_category_id=fc.file_category_id where 1=1 ")
    public long countListByParams(Where where);

    @Select("select fi.*,fc.file_category_name from file_info fi left join file_category fc " +
            "on fi.file_category_id=fc.file_category_id where 1=1 ")
    public List<FileInfo> findListByParams(Where where);
}
