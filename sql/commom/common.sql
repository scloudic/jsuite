/*==============================================================*/
/* Table: area_info                                             */
/*==============================================================*/
create table area_info
(
   area_id              int not null auto_increment comment '机构主键',
   area_name            varchar(128) comment '机构名称',
   parent_area_id       integer comment '父机构主键，根值为0',
   area_level           int comment '机构级别',
   latitude             varchar(16) comment '纬度',
   longitude            varchar(16) comment '经度',
   area_names           varchar(1024) comment '地区全称(多个逗号分离)',
   area_ids             varchar(256) comment '地区主键集(多个逗号分离)',
   active_status        int comment '活动状态(1、正常,2、停用)',
   create_time          datetime comment '创建时间',
   sort_num            int comment '排序序号',
   hot_status           int comment '热门状态(1、是,2,否)',
   primary key (area_id)
);

alter table area_info comment '地区表';



/*==============================================================*/
/* Table: setting_info                                          */
/*==============================================================*/
create table setting_info
(
   setting_id           int not null auto_increment comment '配置主键',
   setting_name         varchar(512) comment '配置名称',
   setting_value        varchar(1024) comment '配置值',
   setting_code         varchar(128) comment '配置code',
   remark               varchar(1024) comment '备注',
   create_time          datetime comment '创建时间',
   primary key (setting_id)
);

alter table setting_info comment '配置信息表';

create unique index uidx_code on setting_info
(
   setting_code
);


