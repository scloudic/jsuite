create table operate_log
(
   operate_log_id      varchar(64) not null comment '操作日志主键',
   method_full_name     varchar(1024) comment '方法全称',
   content              longtext comment '日志内容',
   log_remark           text comment '日志备注',
   operate_type        varchar(32) comment '操作类型',
   create_time          datetime comment '创建时间',
   user_id      varchar(64) comment '操作人',
   ip_address varchar(256) comment 'ip地址',
   login_name  varchar(256) comment '操作登录名称',
   user_name   varchar(256) comment '操作人名称',
   method_name          varchar(512) comment '方法名称',
   return_result        longtext comment '返回结果',
   primary key (operate_log_id)
);

alter table operate_log comment '操作日志表';
