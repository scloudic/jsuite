package com.scloudic.jsuite.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {
    String remark() default "";
    OperateType operatorType();


    public enum OperateType {
        ADD("add"),
        DEL("del"),
        SELECT("select"),
        UPDATE("update"),
        STAT("stat"),
        EXPORT("export"),
        IMPORT("import"),
        LOGIN("login"),
        ;
        public final String value;

        OperateType(String value) {
            this.value = value;
        }
    }
}
