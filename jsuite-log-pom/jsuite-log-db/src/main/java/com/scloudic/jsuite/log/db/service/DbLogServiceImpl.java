package com.scloudic.jsuite.log.db.service;

import com.scloudic.jsuite.log.db.mapper.OperateLogMapper;
import com.scloudic.jsuite.log.db.entity.OperateLog;
import com.scloudic.jsuite.log.db.service.impl.DbLogService;
import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.RowBounds;
import com.scloudic.rabbitframework.jbatis.mapping.lambda.SFunctionUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import com.scloudic.rabbitframework.core.utils.BeanUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("jsuiteLogService")
public class DbLogServiceImpl extends IServiceImpl<OperateLogMapper, OperateLog>
        implements DbLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public OperateLogMapper getBaseMapper() {
        return operateLogMapper;
    }

    @Override
    public int saveLog(LogBean logBean) {
        OperateLog operateLog = new OperateLog();
        BeanUtils.copyProperties(operateLog, logBean);
        operateLog.setOperateLogId(UUIDUtils.getRandomUUID32());
        return operateLogMapper.insertByEntity(operateLog);
    }

    @Transactional(readOnly = true)
    @Override
    public PageBean<LogBean> findLog(String searchKey,
                                     String operateType,
                                     String startDate,
                                     String endDate, Long pageNum, Long pageSize) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        if (StringUtils.isNotBlank(operateType)) {
            criteria.andEqual(OperateLog::getOperateType, operateType);
        }
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            String createTime = SFunctionUtils.getFieldName(OperateLog::getCreateTime);
            criteria.andGreaterThanEqual("date_format(" + createTime + ",'%Y-%m-%d')", startDate);
            criteria.andLessThanEqual("date_format(" + createTime + ",'%Y-%m-%d')", endDate);
        }
        if (StringUtils.isNotBlank(searchKey)) {
            Criteria addCriteria = where.addCreateCriteria();
            addCriteria.orLike(OperateLog::getContent, "%" + searchKey + "%");
            addCriteria.orLike(OperateLog::getLogRemark, "%" + searchKey + "%");
            addCriteria.orLike(OperateLog::getLoginName, "%" + searchKey + "%");
        }

        Long totalCount = operateLogMapper.selectCountByParams(where);
        PageBean<LogBean> pageBean = new PageBean<LogBean>(pageNum, pageSize, totalCount);
        if (totalCount.longValue() == 0) {
            return pageBean;
        }
        where.setOrderBy(OperateLog::getCreateTime);
        List<LogBean> operateLogs = operateLogMapper.findLogBeanByWhere(where,
                new RowBounds(pageBean.getStartPage(), pageBean.getPageSize()));
        pageBean.setDatas(operateLogs);
        return pageBean;
    }
}
