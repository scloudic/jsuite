package com.scloudic.jsuite.log.aspects;

import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.log.annotation.LogParamExclude;
import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.jsuite.log.notification.OperateLogEvent;
import com.scloudic.rabbitframework.core.notification.NotificationServerManager;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.security.SecurityUser;
import com.scloudic.rabbitframework.security.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 操作日志拦截
 */
@Aspect
public class OperateLogInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(OperateLogInterceptor.class);
    private NotificationServerManager notificationServerManager;
    private boolean saveSelect = false;
    private boolean saveStat = false;
    private boolean saveCurd = true;
    private boolean export = false;
    private boolean importOperate = false;

    public void setNotificationServerManager(NotificationServerManager notificationServerManager) {
        this.notificationServerManager = notificationServerManager;
    }

    @Pointcut("@annotation(com.scloudic.jsuite.log.annotation.Log)")
    public void formAnnotatedMethod() {
    }

    @AfterReturning(value = "formAnnotatedMethod()", returning = "returnValue")
    public void doInterceptor(JoinPoint pjp, Object returnValue) {
        try {
            Object[] args = pjp.getArgs();
            Signature signature = pjp.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Log operationLog = method.getAnnotation(Log.class);
            String methodName = method.getDeclaringClass().getName() + "." + method.getName();
            Map<String, Object> paramsValue = new LinkedHashMap<>();
            Parameter[] parameters = method.getParameters();
            int parameterLength = 0;
            if (parameters != null) {
                parameterLength = parameters.length;
            }
            for (int i = 0; i < parameterLength; i++) {
                Parameter parameter = parameters[i];
                Annotation[] annotations = parameter.getAnnotations();
                LogParamExclude logParamExclude = parameter.getAnnotation(LogParamExclude.class);
                if (logParamExclude != null) {
                    continue;
                }
                boolean isExclude = isExclude(annotations);
                if (isExclude) {
                    continue;
                }
                paramsValue.put(parameter.getName(), args[i]);
            }

            LogBean operateLog = new LogBean();
            if (paramsValue.size() > 0) {
                operateLog.setContent(JsonUtils.toJson(paramsValue, true, true));
            }
            String userId = "";
            String loginName = "";
            String userName = "";
            SecurityUser securityUser = SecurityUtils.getSecurityUser();
            if (securityUser != null) {
                userId = securityUser.getUserId();
                loginName = securityUser.getLoginName();
                userName = securityUser.getNickName();
            }
            operateLog.setUserId(userId);
            operateLog.setLoginName(loginName);
            operateLog.setUserName(userName);
            operateLog.setLogRemark(operationLog.remark());
            operateLog.setOperateType(operationLog.operatorType().value);
            operateLog.setCreateTime(new Date());
            operateLog.setMethodFullName(methodName);
            operateLog.setMethodName(method.getName());
            if (returnValue != null) {
                try {
                    operateLog.setReturnResult(JsonUtils.toJson(returnValue, true, true));
                } catch (Exception e) {
                    operateLog.setReturnResult("error:" + e.getMessage());
                }
            }

            logger.debug("打印操作日志：" + JsonUtils.toJson(operateLog));
            boolean sendFlag = false;
            switch (operationLog.operatorType()) {
                case LOGIN:
                case DEL:
                case UPDATE:
                case ADD:
                    if (isSaveCurd()) {
                        sendFlag = true;
                    }
                    break;
                case STAT:
                    if (isSaveStat()) {
                        sendFlag = true;
                    }
                    break;
                case SELECT:
                    if (isSaveSelect()) {
                        sendFlag = true;
                    }
                case EXPORT:
                    if (isExport()) {
                        sendFlag = true;
                    }
                    break;
                case IMPORT:
                    if (isImportOperate()) {
                        sendFlag = true;
                    }
                    break;
            }
            if (sendFlag) {
                notificationServerManager.fireEvent(new OperateLogEvent(operateLog, 0));
            }
        } catch (Exception e) {
            logger.warn("operatorLogInterceptorError:" + e.getMessage(), e);
        }
    }

    private boolean isExclude(Annotation[] annotations) {
        int annotationsLength = annotations.length;
        boolean result = false;
        for (int i = 0; i < annotationsLength; i++) {
            Annotation annotation = annotations[i];
            if ("javax.ws.rs.core.Context".equals(annotation.annotationType().getName())) {
                result = true;
                break;
            }
        }
        return result;
    }


    public boolean isSaveSelect() {
        return saveSelect;
    }

    public void setSaveSelect(boolean saveSelect) {
        this.saveSelect = saveSelect;
    }

    public boolean isSaveStat() {
        return saveStat;
    }

    public void setSaveStat(boolean saveStat) {
        this.saveStat = saveStat;
    }

    public boolean isSaveCurd() {
        return saveCurd;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    public boolean isImportOperate() {
        return importOperate;
    }

    public void setImportOperate(boolean importOperate) {
        this.importOperate = importOperate;
    }

    public void setSaveCurd(boolean saveCurd) {
        this.saveCurd = saveCurd;
    }
}
