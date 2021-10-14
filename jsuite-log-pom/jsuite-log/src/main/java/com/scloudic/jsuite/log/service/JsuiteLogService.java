package com.scloudic.jsuite.log.service;

import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.rabbitframework.core.utils.PageBean;

/**
 * 日志接口服务
 */
public interface JsuiteLogService {
    public int saveLog(LogBean logBean);

    public PageBean<LogBean> findLog(String searchKey,
                                     String operateType,
                                     String startDate,
                                     String endDate, Long pageNum, Long pageSize);
}
