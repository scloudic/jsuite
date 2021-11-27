create table wei_xin_official_account_message
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

alter table wei_xin_official_account_message comment '微信公众号消息表';
