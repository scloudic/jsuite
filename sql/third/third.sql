create table third_bind_info
(
   third_bind_id        bigint not null auto_increment comment '配置主键',
   third_bind_name      varchar(256) comment '配置名称',
   third_bind_params    text comment '配置参数值(json)',
   create_time          datetime comment '创建时间',
   del_status           int comment '删除状态(1、正常,2、删除)',
   third_bind_code      varchar(128) comment '配置编码',
   primary key (third_bind_id)
);

alter table third_bind_info comment '第三方绑定信息表';

create unique index uidx_third_bind_code on third_bind_info
(
   third_bind_code
);
