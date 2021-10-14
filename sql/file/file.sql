create table file_category
(
   file_category_id     varchar(36) not null comment '文件分类主键',
   file_category_name   varchar(512) comment '文件分类名称',
   user_id              varchar(36) comment '创建人主键',
   create_time          datetime comment '创建时间',
   sort_num             int comment '排序序号',
   file_category_name_code varchar(512) comment '文件分类名称编码',
   primary key (file_category_id)
);

alter table file_category comment '文件分类';

create table file_info
(
   file_id              varchar(36) not null comment '文件主键',
   file_category_id     varchar(36) comment '文件分类主键',
   file_type            varchar(36) comment '文件类型',
   file_name            varchar(128) comment '文件名',
   src_file_name        varchar(512) comment '源文件名',
   create_time          datetime comment '创建时间',
   http_url             varchar(512) comment '请求地址',
   del_status           int comment '删除状态(1、正常,2、删除)',
   file_path            varchar(512) comment '文件内部路径',
   file_store_type      varchar(31) comment '文件存储类型(如本地,阿里云)',
   user_id              varchar(36) comment '上传者主键',
   primary key (file_id)
);

alter table file_info comment '文件信息表';

/*==============================================================*/
/* Index: file_category_id                                      */
/*==============================================================*/
create index file_category_id on file_info
(
   file_category_id
);
