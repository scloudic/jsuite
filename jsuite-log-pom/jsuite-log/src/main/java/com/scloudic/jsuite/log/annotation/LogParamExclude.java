package com.scloudic.jsuite.log.annotation;

import java.lang.annotation.*;

/**
 * 日志参数
 */
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogParamExclude {
}
