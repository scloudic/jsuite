create table wei_xin_mp_message
(
   message_id           varchar(64) not null comment '公众号消息主键',
   from_user_name       varchar(512) comment '发送人',
   to_user_name         varchar(512) comment '接收人',
   create_time          datetime comment '创建时间',
   send_time            datetime comment '发送时间',
   msg_type             varchar(64) comment '消息类型',
   msg_id               bigint comment '消息id',
   app_id               varchar(128) comment 'appId',
   send_status          int comment '接收/发送(1、接收,2、发送)',
   msg_content          longtext comment '消息内容',
   primary key (message_id)
);

alter table wei_xin_mp_message comment '微信公众号消息表';

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

/*==============================================================*/
/* Index: uidx_third_bind_code                                  */
/*==============================================================*/
create unique index uidx_third_bind_code on third_bind_info
(
   third_bind_code
);
