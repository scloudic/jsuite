package com.scloudic.jsuite.log.db.service.impl;

import com.scloudic.jsuite.log.db.entity.OperateLog;
import com.scloudic.jsuite.log.service.JsuiteLogService;
import com.scloudic.rabbitframework.jbatis.service.IService;

public interface DbLogService extends JsuiteLogService, IService<OperateLog> {
}
