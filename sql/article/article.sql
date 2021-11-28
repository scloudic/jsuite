
/*==============================================================*/
/* Table: article                                               */
/*==============================================================*/
create table article
(
   article_id           varchar(36) not null comment '文章主键',
   article_title        varchar(512) comment '文章标题',
   article_content      longtext comment '文章内容',
   link_to              varchar(512) comment '外部链接',
   thumbnail_path       varchar(1024) comment '缩略图地址(json)',
   sort_num             int comment '排序编号',
   comment_status       int comment '评论状态(1、允许评论，2、不允许评论)',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '最后一次修改时间',
   article_status       int comment '文章状态(1、已发布,2、草稿)',
   edit_mode            varchar(32) comment '编辑模式(html,markdown)',
   user_id              varchar(36) comment '发布人',
   summary              text comment '摘要',
   comment_count        int comment '评论总数',
   view_count           int comment '访问量',
   del_status           int comment '删除状态(1、正常,2、删除)',
   primary key (article_id)
);

alter table article comment '文章表';

/*==============================================================*/
/* Table: article_category                                      */
/*==============================================================*/
create table article_category
(
   article_category_id  bigint not null auto_increment comment '文章分类主键',
   article_category_parent_id bigint comment '文章分类父主键',
   article_category_name varchar(512) comment '文章分类名称',
   user_id              varchar(36) comment '创建人',
   content              text comment '内容',
   summary              text comment '摘要',
   icon_path            varchar(512) comment '图标地址',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '修改时间',
   category_type        int comment '分类类型(1、资讯、2、专题)',
   del_status           int comment '删除状态(1、正常,2、删除)',
   primary key (article_category_id)
);

alter table article_category comment '文章分类表';

/*==============================================================*/
/* Table: article_category_mapping                              */
/*==============================================================*/
create table article_category_mapping
(
   article_category_mapping_id bigint not null auto_increment comment '文章分类关联主键',
   article_id           varchar(36) comment '文章主键',
   article_category_id  bigint comment '文章分类主键',
   primary key (article_category_mapping_id)
);

alter table article_category_mapping comment '文章分类关联表';

/*==============================================================*/
/* Index: inx_acid                                              */
/*==============================================================*/
create unique index inx_acid on article_category_mapping
(
   article_id,
   article_category_id
);

/*==============================================================*/
/* Index: inx_category_id                                       */
/*==============================================================*/
create index inx_category_id on article_category_mapping
(
   article_category_id
);

/*==============================================================*/
/* Index: inx_article_id                                        */
/*==============================================================*/
create index inx_article_id on article_category_mapping
(
   article_id
);

/*==============================================================*/
/* Table: article_comment                                       */
/*==============================================================*/
create table article_comment
(
   article_comment_id   varchar(36) not null comment '文章评论主键',
   article_id           varchar(36) comment '文章主键',
   parent_article_comment_id varchar(36) comment '回复评论主键',
   user_id              varchar(36) comment '评论人',
   comment_content      longtext comment '评论内容',
   create_time          datetime comment '创建时间',
   vote_up              int comment '“顶”的数量',
   vote_down            int comment '“踩”的数量',
   reply_count          int comment '评论的回复数量',
   sort_num             int comment '排序编号',
   primary key (article_comment_id)
);

alter table article_comment comment '文章评论表';

/*==============================================================*/
/* Index: inx_article_id                                        */
/*==============================================================*/
create index inx_article_id on article_comment
(
   article_id
);

/*==============================================================*/
/* Table: user_article_favorite                                 */
/*==============================================================*/
create table user_article_favorite
(
   article_favorite_id  varchar(36) not null comment '用户文章收藏主键',
   article_id           varchar(36) comment '文章主键',
   article_title        varchar(512) comment '文章标题',
   thumbnail_path       varchar(1024) comment '缩略图地址(json)',
   create_time          datetime comment '创建时间',
   user_id              varchar(36) comment '收藏人',
   summary              text comment '摘要',
   primary key (article_favorite_id)
);

alter table user_article_favorite comment '用户文章收藏表';

/*==============================================================*/
/* Index: inx_article_id                                        */
/*==============================================================*/
create index inx_article_id on user_article_favorite
(
   article_id
);


