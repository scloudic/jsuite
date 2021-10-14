
/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu
(
   sys_menu_id          varchar(64) not null comment '菜单主键',
   menu_name            varchar(256) comment '菜单名称',
   parent_menu_id       varchar(64) comment '父菜单ID',
   front_end_url        varchar(256) comment '前端url地址',
   target               int comment '打开方式(1、当前页,2、新页面，3、模式对话框)',
   btn_flag             int comment '按钮标记(1、按钮接口,2、菜单接口)',
   sort_num             int comment '排序序号',
   menu_type            int comment '菜单类型(1、主菜单，2、顶部菜单，3、底部菜单)',
   icon_path            varchar(256) comment '菜单图标路径',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '最后一次修改时间',
   menu_desc            varchar(1024) comment '菜单描述',
   menu_level           int comment '菜单级别',
   back_end_url         varchar(256) comment '后端地址',
   front_end_param_name varchar(256) comment '前端传参名称',
   menu_code           varchar(256) comment '唯一标识',
   primary key (sys_menu_id)
);

alter table sys_menu comment '功能菜单表';

/*==============================================================*/
/* Index: unidx_menu_code                                       */
/*==============================================================*/
create unique index unidx_menu_code on sys_menu
(
   menu_code
);


/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   sys_role_id          bigint not null auto_increment comment '角色主键',
   role_name            varchar(128) comment '角色名称',
   role_code            varchar(128) comment '角色编码',
   role_desc            varchar(1024) comment '角色描述',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '最后一次修改时间',
   primary key (sys_role_id)
);

alter table sys_role comment '角色表';

/*==============================================================*/
/* Index: role_code                                             */
/*==============================================================*/
create index role_code on sys_role
(
   role_code
);

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/
create table sys_role_menu
(
   sys_role_menu_id     varchar(64) not null comment '角色菜单主键',
   sys_role_id          bigint comment '角色主键',
   sys_menu_id          varchar(64) comment '菜单主键',
   role_code            varchar(128) comment '角色编码',
   primary key (sys_role_menu_id)
);

alter table sys_role_menu comment '角色菜单关系表';

/*==============================================================*/
/* Index: inx_sys_role_id                                       */
/*==============================================================*/
create index inx_sys_role_id on sys_role_menu
(
   sys_role_id
);

/*==============================================================*/
/* Index: inx_sys_menu_id                                       */
/*==============================================================*/
create index inx_sys_menu_id on sys_role_menu
(
   sys_menu_id
);

/*==============================================================*/
/* Index: inx_sys_rmid                                          */
/*==============================================================*/
create unique index inx_sys_rmid on sys_role_menu
(
   sys_role_id,
   sys_menu_id
);


/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   sys_user_id          varchar(64) not null comment '用户主键',
   post_id              int comment '岗位主键',
   dept_id              int comment '部门主键',
   login_name           varchar(128) comment '登陆名称',
   login_pwd            varchar(64) comment '登陆密码',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '最后一次修改时间',
   del_status           int comment '删除状态(1、正常,2、删除,)',
   real_name            varchar(128) comment '真实姓名',
   active_status        int comment '活动状态(1、正常,2、停用)',
   avatar_path          varchar(512) comment '头像路径',
   user_phone           varchar(20) comment '手机号',
   user_mail            varchar(256) comment '邮箱',
   gender               int comment '性别(1男2女3保密)',
   remark               varchar(1024) comment '备注',
   dept_id            int comment '部门id',
   primary key (sys_user_id)
);

alter table sys_user comment '系统用户表';

/*==============================================================*/
/* Index: inx_login_name                                        */
/*==============================================================*/
create unique index inx_login_name on sys_user
(
   login_name
);

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   sys_user_role_id     varchar(64) not null comment '用户角色主键',
   sys_role_id          bigint comment '角色主键',
   sys_user_id          varchar(64) comment '用户主键',
   role_code            varchar(128) comment '角色编码',
   primary key (sys_user_role_id)
);

alter table sys_user_role comment '用户角色关系表';

/*==============================================================*/
/* Index: inx_sys_user_id                                       */
/*==============================================================*/
create index inx_sys_user_id on sys_user_role
(
   sys_user_id
);

/*==============================================================*/
/* Index: inx_sys_role_id                                       */
/*==============================================================*/
create index inx_sys_role_id on sys_user_role
(
   sys_role_id
);

/*==============================================================*/
/* Index: inx_sysurid                                           */
/*==============================================================*/
create unique index inx_sysurid on sys_user_role
(
   sys_role_id,
   sys_user_id
);


/*==============================================================*/
/* Table: sys_post                                              */
/*==============================================================*/
create table sys_post
(
   post_id              int not null auto_increment comment '岗位主键',
   del_status           int comment '删除状态(1、正常,2、删除,)',
   active_status        int comment '活动状态(1、正常,2、停用)',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '最后一次修改时间',
   post_name            varchar(512) comment '岗位名称',
   post_code            varchar(512) comment '岗位编码',
   remark               varchar(1024) comment '备注',
   sort_num             int comment '排序序号',
   primary key (post_id)
);

alter table sys_post comment '岗位表';


/*==============================================================*/
/* Table: sys_dept                                              */
/*==============================================================*/
create table sys_dept
(
   dept_id              int not null auto_increment comment '部门主键',
   dept_name            varchar(512) comment '部门名称',
   dept_parent_id       int comment '父主键(根为0)',
   dept_lead            varchar(512) comment '部门负责人',
   dept_phone           varchar(20) comment '负责人联系电话',
   dept_mail            varchar(128) comment '负责人联系邮箱',
   del_status           int comment '删除状态(1、正常,2、删除,)',
   active_status        int comment '活动状态(1、正常,2、停用)',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '最后一次修改时间',
   sort_num             int comment '排序序号',
   dept_ids             varchar(255) comment '部门id层次(多个逗号分离)',
   dept_names           varchar(1024) comment '部门名称层次',
   remark               varchar(1024) comment '备注',
   primary key (dept_id)
);

alter table sys_dept comment '部门表';
