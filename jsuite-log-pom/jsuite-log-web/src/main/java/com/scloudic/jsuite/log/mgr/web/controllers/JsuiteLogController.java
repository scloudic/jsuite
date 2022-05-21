package com.scloudic.jsuite.log.mgr.web.controllers;


import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.jsuite.log.service.JsuiteLogService;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/jsuite/logMgr")
public class JsuiteLogController extends AbstractRabbitController {
    @Autowired
    private JsuiteLogService jsuiteLogService;

    @GetMapping("list")
    @UriPermissions
    public Result<PageBean<LogBean>> list(HttpServletRequest request,
                                          @RequestParam(value = "searchKey", required = false) String searchKey,
                                          @RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "operateType", required = false) String operateType,
                                          @RequestParam(value = "endDate", required = false) String endDate,
                                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Long pageNum,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "15") Long pageSize) {
        PageBean<LogBean> pageBean = jsuiteLogService.findLog(searchKey, operateType, startDate, endDate, pageNum, pageSize);
        return success(pageBean);
    }
}
