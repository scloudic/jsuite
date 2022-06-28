package com.scloudic.jsuite.mgr.web;

import com.scloudic.rabbitframework.jbatis.springboot.configure.MapperScan;
import com.scloudic.rabbitframework.web.springboot.RabbitWebApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 应用启动
 *
 * @author justin
 */
@MapperScan({"com.scloudic.jsuite.**.mapper"})
@ComponentScan({"com.scloudic.jsuite"})
public class MgrApplication extends RabbitWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MgrApplication.class, args);
    }
}