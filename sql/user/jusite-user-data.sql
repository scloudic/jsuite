insert into sys_user ( gender, real_name, login_pwd, avatar_path, user_mail, login_name, user_phone, del_status, dept_id, post_id, remark, active_status, create_time, update_time, sys_user_id) values ( '3', 'admin', '0024ec4W01m4aU7aY74Q13d04Kb5cdcq13A12tc9X1cD2a25', null, '28554768@qq.com', 'admin', '18601029496', '1', '1', '1', null, '1', '2020-05-06 17:43:13', '2021-08-07 05:49:28', '1');
insert into sys_post ( update_time, post_code, post_id, del_status, post_name, active_status, remark, sort_num, create_time) values ( '2021-07-06 00:12:10', 'super_admin', '1', '2', '超级用户', '2', 'admin用户特定', '0', '2021-07-06 00:12:01');
insert into sys_dept ( dept_phone, dept_names, dept_lead, dept_id, del_status, dept_mail, remark, active_status, dept_ids, dept_name, create_time, sort_num, update_time, dept_parent_id) values ( null, '机构部门', null, '1', '1', null, '机构部门', '1', '1', '机构部门', '2021-07-06 00:15:11', '1', '2021-07-06 00:15:14', '0');
insert into sys_role ( role_name, role_code, role_desc, sys_role_id, create_time, update_time) values ( '超级管理员', 'super_sys_manager', '超级管理员', '1', '2020-05-10 16:14:09', '2020-05-10 16:14:12');
insert into sys_user_role ( sys_user_id, sys_user_role_id, role_code, sys_role_id) values ( '1', '1', 'super_sys_manager', '1');

INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('0caf2783630e11eca759e0d55e1326fa', '文章删除', '287aad1261a111ec90f64a338a845ba0', '', '1', '1', '0', '1', NULL, '2021-12-22 04:00:54', '2021-12-22 04:00:54', '文章删除', '3', '/jsuite/articleMgr/articleDel', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('1', '系统菜单', '-1', '', '1', '2', '0', '1', '', '2020-05-08 17:14:29', '2020-05-08 17:14:31', '系统菜单', '0', '', '', '1');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('10', '用户删除', '3', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2021-03-20 01:35:29', '用户管理删除', '3', '/jsuite/userMgr/delUser', '', '10');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('11', '菜单查询', '6', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2020-08-30 22:50:16', '菜单查询', '3', '/jsuite/menuMgr/listMenu', '', '11');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('12', '菜单添加', '6', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2020-08-30 22:50:16', '菜单添加', '3', '/jsuite/menuMgr/addMenu', '', '12');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('13', '菜单修改', '6', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2020-08-30 22:50:16', '菜单修改', '3', '/jsuite/menuMgr/updateMenu', '', '13');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('14', '菜单删除', '6', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2020-08-30 22:50:16', '菜单删除', '3', '/jsuite/menuMgr/delMenu', '', '14');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('14ba2a89551d11ec9da276d0b9574ed8', '文章分类修改', '7cf1a8e6551c11ec9da276d0b9574ed8', '', '1', '1', '0', '1', NULL, '2021-12-04 10:13:13', '2021-12-06 08:14:26', '文章分类修改', '3', '/jsuite/articleCategoryMgr/update', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('15', '角色菜单查询', '5', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2020-08-30 22:50:16', '角色菜单查询', '3', '/jsuite/menuMgr/findRoleMenu', '', '15');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('16', '查询角色', '5', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2021-03-20 01:36:06', '查询角色', '3', '/jsuite/roleMgr/findRoles', '', '16');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('17', '添加角色', '5', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2021-03-20 01:36:19', '查询角色', '3', '/jsuite/roleMgr/addRole', '', '17');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('18', '删除角色', '5', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2021-03-20 01:36:32', '删除角色', '3', '/jsuite/roleMgr/delRole', '', '18');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('19', '添加角色菜单', '5', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2021-03-20 01:36:48', '添加角色菜单', '3', '/jsuite/roleMgr/addRoleMenu', '', '19');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('2', '系统管理', '1', '', '1', '2', '10', '1', '', '2020-05-08 17:18:02', '2021-05-16 04:58:51', '系统管理', '1', '', '', '2');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('20', '角色修改', '5', '', '1', '1', '5', '1', NULL, '2021-03-25 10:03:01', '2021-03-25 10:03:01', '修改角色', '3', '/jsuite/roleMgr/updateRole', '', '20');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('21', '角色分页查询', '5', '', '1', '1', '0', '1', NULL, '2021-05-17 21:33:06', '2021-05-17 21:33:09', '分页角色查询', '3', '/jsuite/roleMgr/findRolesPage', '', '21');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('22', '用户密码重置', '3', '', '1', '1', '0', '1', NULL, '2021-05-23 23:56:58', '2021-05-23 23:57:02', '用户密码重置', '3', '/jsuite/userMgr/pwdReset', '', '22');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('24c8942a551d11ec9da276d0b9574ed8', '文章分类删除', '7cf1a8e6551c11ec9da276d0b9574ed8', '', '1', '1', '0', '1', NULL, '2021-12-04 10:13:40', '2021-12-06 08:14:52', '文章分类删除', '3', '/jsuite/articleCategoryMgr/del', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('287aad1261a111ec90f64a338a845ba0', '文章管理', '58ab9e55551c11ec9da276d0b9574ed8', '/article/articleIndex', '1', '2', '1', '1', NULL, '2021-12-20 08:28:54', '2021-12-20 08:28:54', '文章管理', '2', '', '', 'articleIndex');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('3', '用户管理', '2', '/admin/user/userIndex', '1', '2', '1', '1', '', '2020-05-08 22:57:41', '2021-05-16 09:37:20', '用户管理', '2', '', '', '3');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('37', '文件管理', '54', '/admin/file/fileIndex', '1', '2', '0', '1', NULL, '2021-05-31 11:52:02', '2021-06-09 11:18:33', '文件管理', '2', NULL, NULL, '37');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('4', '用户启禁用', '3', '', '1', '1', '0', '1', NULL, '2021-05-22 17:27:05', '2021-05-22 17:27:07', '用户启用或禁用修改', '3', '/jsuite/userMgr/updateActiveStatus', '', '4');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('44', '文件分类管理', '54', '/admin/file/categoryIndex', '1', '2', '3333', '1', NULL, '2021-06-06 09:06:47', '2021-10-14 10:22:28', '文件分类管理', '2', '', '', '44');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('45', '分类查询', '44', '', '1', '1', '1', '1', NULL, '2021-06-08 11:30:33', '2021-09-12 09:17:54', '文件分类查询', '3', '/jsuite/file/categoryMgr/listPage', '', '45');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('46', '分类添加', '44', '', '1', '1', '1', '1', NULL, '2021-06-08 11:39:22', '2021-06-08 11:39:22', '分类添加', '3', '/jsuite/file/categoryMgr/add', NULL, '46');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('47', '分类修改', '44', '', '1', '1', '2', '1', NULL, '2021-06-08 11:39:47', '2021-06-08 11:39:47', '分类修改', '3', '/jsuite/file/categoryMgr/update', NULL, '47');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('48', '分类删除', '44', '', '1', '1', '2', '1', NULL, '2021-06-08 11:40:11', '2021-06-08 11:40:11', '分类删除', '3', '/jsuite/file/categoryMgr/del', NULL, '48');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('49', '文件上传', '37', '', '1', '1', '0', '1', NULL, '2021-06-11 09:12:46', '2021-09-13 09:44:46', '文件上传', '3', '/jsuite/fileMgr/upload', '', '49');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('5', '角色管理', '2', '/admin/role/roleIndex', '1', '2', '0', '1', NULL, '2020-05-10 16:01:20', '2021-05-16 09:37:38', '角色管理', '2', '', '', '5');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('50', '查询所有分类', '44', '', '1', '1', '0', '1', NULL, '2021-06-11 09:13:53', '2021-09-12 09:18:16', '查询所有分类', '3', '/jsuite/file/categoryMgr/listAll', '', '50');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('51', '文件查询', '37', '', '1', '1', '0', '1', NULL, '2021-06-12 05:29:26', '2021-09-03 20:03:33', '文件查询', '3', '/jsuite/fileMgr/list', '', '51');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('52', '修改', '37', '', '1', '1', '0', '1', NULL, '2021-06-12 05:30:06', '2021-06-12 05:30:06', '修改', '3', '/jsuite/fileMgr/update', NULL, '52');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('53', '文件删除', '37', '', '1', '1', '0', '1', NULL, '2021-06-12 05:45:16', '2021-06-12 05:45:16', '文件删除', '3', '/jsuite/fileMgr/del', NULL, '53');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('54', '文件管理', '1', '', '1', '2', '50', '1', NULL, '2021-06-13 00:28:07', '2021-06-13 00:28:07', '文件管理', '1', NULL, NULL, '54');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('55', '岗位管理', '2', '/admin/post/postIndex', '1', '2', '10', '1', NULL, '2021-07-15 08:35:21', '2021-07-15 08:35:21', '', '2', '', '', 'postIndex');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('55b24c7361a111ec90f64a338a845ba0', '文章查询', '287aad1261a111ec90f64a338a845ba0', '', '1', '1', '0', '1', NULL, '2021-12-20 08:30:10', '2021-12-20 08:30:10', '文章查询', '3', '/jsuite/articleMgr/list', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('56', '岗位添加', '55', '', '1', '1', '0', '1', NULL, '2021-07-15 08:36:15', '2021-07-15 08:36:15', '岗位添加', '3', '/jsuite/postMgr/add', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('57', '岗位分页查询', '55', '', '1', '1', '0', '1', NULL, '2021-07-15 08:36:55', '2021-07-23 10:09:38', '岗位分页查询', '3', '/jsuite/postMgr/listPage', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('58', '获取所有岗位', '55', '', '1', '1', '0', '1', NULL, '2021-07-15 08:37:25', '2021-07-25 03:13:47', 'api获取所有岗位', '3', '/jsuite/postMgr/apiList', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('58ab9e55551c11ec9da276d0b9574ed8', '文章管理', '1', '', '1', '2', '30', '1', NULL, '2021-12-04 10:07:58', '2021-12-04 10:08:14', '文章管理', '1', '', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('59', '修改', '55', '', '1', '1', '0', '1', NULL, '2021-07-15 08:38:51', '2021-07-15 08:38:51', '岗位修改', '3', '/jsuite/postMgr/update', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('6', '菜单管理', '2', '/admin/menu/menuIndex', '1', '2', '0', '1', NULL, '2020-05-10 16:02:10', '2020-05-10 16:02:13', '菜单管理', '2', '', '', '6');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('60', '删除', '55', '', '1', '1', '0', '1', NULL, '2021-07-15 08:39:51', '2021-07-15 08:39:51', '岗位删除', '3', '/jsuite/postMgr/del', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('61', '岗位禁启用', '55', '', '1', '1', '0', '1', NULL, '2021-07-23 10:06:34', '2021-07-23 10:06:34', '岗位禁启用', '3', '/jsuite/postMgr/updateActiveStatus', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('62', '部门管理', '2', '/admin/dept/deptIndex', '1', '2', '11', '1', NULL, '2021-07-24 21:42:45', '2021-07-24 21:42:45', '', '2', '', '', 'deptIndex');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('63', '部门树结构', '62', '', '1', '1', '0', '1', NULL, '2021-07-25 08:59:18', '2021-07-25 08:59:18', '获取所有部门信息树型结构', '3', '/jsuite/deptMgr/deptTree', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('631a54c7630c11eca759e0d55e1326fa', '文章修改', '287aad1261a111ec90f64a338a845ba0', '/article/articleEditIndex', '1', '1', '0', '1', NULL, '2021-12-22 03:49:00', '2021-12-22 04:48:33', '', '3', '/jsuite/articleMgr/update', 'articleId', 'articleEditIndex');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('64', '获取所有部门', '62', '', '1', '1', '0', '1', NULL, '2021-07-25 09:00:08', '2021-07-25 09:00:08', '获取所有部门集合', '3', '/jsuite/deptMgr/listAll', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('65', '添加部门', '62', '', '1', '1', '0', '1', NULL, '2021-07-25 09:01:42', '2021-07-25 09:01:42', '添加部门', '3', '/jsuite/deptMgr/addDept', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('66', '部门修改', '62', '', '1', '1', '0', '1', NULL, '2021-07-25 09:06:52', '2021-07-25 09:06:52', '部门修改', '3', '/jsuite/deptMgr/updateDept', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('67', '禁启用部门', '62', '', '1', '1', '0', '1', NULL, '2021-07-25 09:08:00', '2021-07-25 09:08:00', '禁启用部门', '3', '/jsuite/deptMgr/updateActiveStatus', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('68', '部门删除', '62', '', '1', '1', '0', '1', NULL, '2021-07-25 09:09:10', '2021-07-25 09:09:10', '逻辑删除部门信息', '3', '/jsuite/deptMgr/del', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('69', '字典管理', '1', '', '1', '2', '1', '1', NULL, '2021-08-23 08:12:38', '2021-09-27 23:38:59', '字典管理', '1', '', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('7', '用户查询', '3', '', '1', '1', '0', '1', '', '2020-08-30 21:31:09', '2021-03-20 01:34:35', '用户管理查询', '3', '/jsuite/userMgr/findUser', '', '7');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('70', '地区管理', '69', '/admin/area/index', '1', '2', '0', '1', NULL, '2021-08-23 08:15:52', '2021-08-23 08:15:52', '', '2', '', '', 'areaIndex');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('71', '查询', '70', '', '1', '1', '0', '1', NULL, '2021-08-23 08:18:16', '2021-11-06 10:35:52', '查询地区', '3', '/jsuite/areaMgr/areaTree', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('73', '添加', '70', '', '1', '1', '0', '1', NULL, '2021-09-02 08:46:40', '2021-09-02 08:46:40', '添加地区信息', '3', '/jsuite/areaMgr/add', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('74', '修改', '70', '', '1', '1', '0', '1', NULL, '2021-09-02 08:47:55', '2021-09-02 08:47:55', '修改地区信息', '3', '/jsuite/areaMgr/update', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('75', '删除', '70', '', '1', '1', '0', '1', NULL, '2021-09-02 08:49:01', '2021-09-02 08:49:01', '删除地区信息', '3', '/jsuite/areaMgr/del', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('76', '启用禁用地区', '70', '', '1', '1', '0', '1', NULL, '2021-09-02 08:49:35', '2021-09-02 08:49:35', '启用禁用地区', '3', '/jsuite/areaMgr/updateActiveStatus', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('77', '运维管理', '1', '', '1', '2', '0', '1', NULL, '2021-09-27 23:38:48', '2021-09-27 23:38:48', '', '1', '', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('78', '操作日志管理', '77', '/admin/log/operLogIndex', '1', '2', '0', '1', NULL, '2021-09-27 23:41:21', '2021-09-27 23:41:21', '操作日志管理', '2', '', '', 'operLogIndex');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('79', '查询', '78', '', '1', '1', '0', '1', NULL, '2021-09-27 23:42:34', '2021-09-27 23:42:34', '操作日志查询', '3', '/jsuite/logMgr/list', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('7cf1a8e6551c11ec9da276d0b9574ed8', '文章分类管理', '58ab9e55551c11ec9da276d0b9574ed8', '/article/categoryIndex', '1', '2', '10', '1', NULL, '2021-12-04 10:08:59', '2021-12-04 10:10:02', '文章分类管理', '2', '', '', 'articleCategoryIndex');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('8', '用户保存', '3', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2021-03-20 01:34:51', '用户管理保存', '3', '/jsuite/userMgr/saveUser', '', '8');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('80', '配置管理', '69', '/admin/setting/index', '1', '2', '0', '1', NULL, '2021-09-30 00:33:50', '2021-09-30 00:36:37', '', '2', '', '', 'settingIndex');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('81', '配置查询', '80', '', '1', '1', '0', '1', NULL, '2021-09-30 00:34:27', '2021-09-30 00:37:07', '配置查询', '3', '/jsuite/settingMgr/list', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('82', '配置添加', '80', '', '1', '1', '0', '1', NULL, '2021-09-30 00:37:25', '2021-09-30 00:37:25', '', '3', '/jsuite/settingMgr/add', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('83', '配置修改', '80', '', '1', '1', '0', '1', NULL, '2021-09-30 00:37:55', '2021-09-30 00:37:55', '配置修改', '3', '/jsuite/settingMgr/update', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('84', '配置删除', '80', '', '1', '1', '0', '1', NULL, '2021-09-30 00:38:19', '2021-09-30 00:38:19', '配置删除', '3', '/jsuite/settingMgr/del', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('89d9c3c8630c11eca759e0d55e1326fa', '根据主键查询', '287aad1261a111ec90f64a338a845ba0', '', '1', '1', '0', '1', NULL, '2021-12-22 03:50:05', '2021-12-22 03:50:05', '根据主键查询', '3', '/jsuite/articleMgr/getArticleDetail', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('9', '用户修改', '3', '', '1', '1', '0', '1', '', '2020-08-30 21:42:08', '2021-03-20 01:35:05', '用户管理修改', '3', '/jsuite/userMgr/updateUser', '', '9');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('c737ab0c5b5311ec83063e29b10a5268', '文章发布', '58ab9e55551c11ec9da276d0b9574ed8', '/admin/article/articleCreateIndex', '1', '2', '0', '1', NULL, '2021-12-12 07:59:52', '2021-12-20 21:08:35', '文章发布', '2', '/jsuite/articleMgr/add', 'newFlag', 'articleCreateIndex');
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('d99c0a97551c11ec9da276d0b9574ed8', '文章分类查询', '7cf1a8e6551c11ec9da276d0b9574ed8', '', '1', '1', '0', '1', NULL, '2021-12-04 10:11:34', '2021-12-06 08:15:01', '文章分类查询', '3', '/jsuite/articleCategoryMgr/list', '', NULL);
INSERT INTO sys_menu (sys_menu_id, menu_name, parent_menu_id, front_end_url, target, btn_flag, sort_num, menu_type, icon_path, create_time, update_time, menu_desc, menu_level, back_end_url, front_end_param_name, menu_code) VALUES ('fc2067f8551c11ec9da276d0b9574ed8', '文章分类新增', '7cf1a8e6551c11ec9da276d0b9574ed8', '', '1', '1', '0', '1', NULL, '2021-12-04 10:12:32', '2021-12-06 08:15:09', '文章分类新增', '3', '/jsuite/articleCategoryMgr/add', '', NULL);

insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '2', 'b5ab80062b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '3', 'b5ab80072b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '4', 'b5ab80082b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '5', 'b5ab800e2b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '6', 'b5ab80162b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '7', 'b5ab80092b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '8', 'b5ab800a2b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '9', 'b5ab800b2b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '10', 'b5ab800c2b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '11', 'b5ab80172b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '12', 'b5ab80182b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '13', 'b5ab80192b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '14', 'b5ab801a2b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '15', 'b5ab80102b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '16', 'b5ab80112b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '17', 'b5ab80122b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '18', 'b5ab80132b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '19', 'b5ab80142b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '20', 'b5ab800f2b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '21', 'b5ab80152b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');
insert into sys_role_menu ( sys_menu_id, sys_role_menu_id, sys_role_id, role_code) values ( '22', 'b5ab800d2b7b11ec8e572a629a2229dd', '1', 'super_sys_manager');