package com.scloudic.jsuite.mgr.web.test;

import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.jsuite.log.service.JsuiteLogService;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.core.utils.PageBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LogServiceTest extends ApplicationTest {
    private static final Logger logger = LoggerFactory.getLogger(LogServiceTest.class);
    @Autowired
    private JsuiteLogService jsuiteLogService;

    @Test
    public void find() {
        PageBean<LogBean> logBeans = jsuiteLogService.findLog("add", "add",
                "2021-08-20", "2021-10-20", 1L, 10L);
        logger.info("打印日志：" + JsonUtils.toJson(logBeans, true));
    }
}
