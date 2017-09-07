-- ----------------------------
-- Records of t_dic_data
-- ----------------------------
INSERT INTO t_dic_data (ID, NAME, CODE, PID, SORT, REMARK, STATE, TYPEKEY) 
VALUES ('16b80bfb-f0ee-47a0-ba94-cc256abaed17', '专科', '', null, null, '', '1', 'xueli');
INSERT INTO t_dic_data (ID, NAME, CODE, PID, SORT, REMARK, STATE, TYPEKEY) 
VALUES ('7ed23330-5538-4943-8678-0c5a2121cf57', '高中', '', null, null, '', '1', 'xueli');
INSERT INTO t_dic_data (ID, NAME, CODE, PID, SORT, REMARK, STATE, TYPEKEY) 
VALUES ('936db407-ae1-45a7-a657-b60580e2a77a', '汉族', '101', null, null, '', '1', 'minzu');
INSERT INTO t_dic_data (ID, NAME, CODE, PID, SORT, REMARK, STATE, TYPEKEY) 
VALUES ('936db407-ae2-45a7-a657-b60580e2a77a', '回族', '', null, null, '', '1', 'minzu');
INSERT INTO t_dic_data (ID, NAME, CODE, PID, SORT, REMARK, STATE, TYPEKEY) 
VALUES ('936db407-ae3-45a7-a657-b60580e2a77a', '一级', '', null, null, '', '1', 'grade');
INSERT INTO t_dic_data (ID, NAME, CODE, PID, SORT, REMARK, STATE, TYPEKEY) 
VALUES ('936db407-ae4-45a7-a657-b60580e2a77a', '二级', '', null, null, '', '1', 'grade');
INSERT INTO t_dic_data (ID, NAME, CODE, PID, SORT, REMARK, STATE, TYPEKEY) 
VALUES ('d7d1744b-e69f-48d0-9760-b2eae6af039b', '本科', '', null, null, '', '1', 'xueli');

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('business_manager', '业务管理', null, null, null, '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('dic_manager', '字典管理', null, null, null, '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('system_manager', '系统管理', null, null, null, '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_auditlog_list', '修改日志', 'system_manager', null, '/system/auditlog/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_auditlog_look', '查看修改日志', 't_auditlog_list', null, '/system/auditlog/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_grade_delete', '删除级别', 't_dic_data_grade_list', null, '/system/dicdata/grade/delete', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_grade_deletemore', '批量删除级别', 't_dic_data_grade_list', null, '/system/dicdata/grade/delete/more', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_grade_list', '级别管理', 'dic_manager', null, '/system/dicdata/grade/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_grade_look', '查看级别', 't_dic_data_grade_list', null, '/system/dicdata/grade/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_grade_update', '修改级别', 't_dic_data_grade_list', null, '/system/dicdata/grade/update', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_grade_tree', '级别树形结构', 't_dic_data_grade_list', null, '/system/dicdata/grade/tree', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_minzu_delete', '删除民族', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/delete', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_minzu_deletemore', '批量删除民族', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/delete/more', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_minzu_list', '民族管理', 'dic_manager', null, '/system/dicdata/minzu/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_minzu_look', '查看民族', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_minzu_update', '修改民族', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/update', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_minzu_tree', '民族树形结构', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/tree', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_xueli_delete', '删除学历', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/delete', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_xueli_deletemore', '批量删除学历', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/delete/more', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_xueli_list', '学历管理', 'dic_manager', null, '/system/dicdata/xueli/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_xueli_look', '查看学历', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_xueli_update', '修改学历', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/update', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_dic_data_xueli_tree', '学历树形结构', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/tree', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_fwlog_list', '访问日志', 'system_manager', null, '/system/fwlog/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_fwlog_look', '查看访问日志', 't_fwlog_list', null, '/system/fwlog/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_menu_delete', '删除菜单', 't_menu_list', null, '/system/menu/delete', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_menu_deletemore', '批量删除菜单', 't_menu_list', null, '/system/menu/delete/more', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_menu_list', '菜单管理', 'system_manager', null, '/system/menu/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_menu_look', '查看菜单', 't_menu_list', null, '/system/menu/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_menu_update', '修改菜单', 't_menu_list', null, '/system/menu/update', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_menu_tree', '菜单树形结构', 't_menu_list', null, '/system/menu/tree', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_org_delete', '删除部门', 't_org_list', null, '/system/org/delete', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_org_deletemore', '批量删除部门', 't_org_list', null, '/system/org/delete/more', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_org_list', '部门管理', 'business_manager', null, '/system/org/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_org_look', '查看部门', 't_org_list', null, '/system/org/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_org_update', '修改部门', 't_org_list', null, '/system/org/update', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_org_tree', '部门树形结构', 't_org_list', null, '/system/org/tree', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_role_delete', '删除角色', 't_role_list', null, '/system/role/delete', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_role_deletemore', '批量删除角色', 't_role_list', null, '/system/role/delete/more', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_role_list', '角色管理', 'system_manager', '', '/system/role/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_role_look', '查看角色', 't_role_list', null, '/system/role/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_role_update', '修改角色', 't_role_list', null, '/system/role/update', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_user_delete', '删除用户', 't_user_list', null, '/system/user/delete', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_user_deletemore', '批量删除用户', 't_user_list', null, '/system/user/delete/more', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_user_list', '用户管理', 'business_manager', null, '/system/user/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_user_list_export', '导出用户', 't_user_list', null, '/system/user/list/export', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_user_look', '查看用户', 't_user_list', null, '/system/user/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_user_update', '修改用户', 't_user_list', null, '/system/user/update', '0', '1');

INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('weixin_manager', '微信管理', null, null, null, '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_weixin_menu_list', '微信菜单管理', 'weixin_manager', null, '/system/weixin/menu/list', '1', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_weixin_menu_look', '查看微信菜单', 't_weixin_menu_list', null, '/system/weixin/menu/look', '0', '1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES ('t_weixin_menu_update', '修改微信菜单', 't_weixin_menu_list', null, '/system/weixin/menu/update', '0', '1');

insert into T_MENU (id, name, pid, description, pageurl, type, state) values ('t_region_business_search', '业务关系表查询操作', 'T_REGION_list', null, '/regionbusiness/buslist', 0, '1');
insert into T_MENU (id, name, pid, description, pageurl, type, state) values ('t_region_business_update', '业务关系表更新操作', 'T_REGION_list', null, '/regionbusiness/updates', 0, '1');
insert into T_MENU (id, name, pid, description, pageurl, type, state) values ('t_region_business_return', '业务关系表返回操作', 'T_REGION_list', null, '/region/list', 0, '1');

insert into T_MENU (id, name, pid, description, pageurl, type, state) values ('t_user_business_search', '业务关系表查询操作', 't_user_list', null, '/userbusiness/buslist', 0, '1');
insert into T_MENU (id, name, pid, description, pageurl, type, state) values ('t_user_business_update', '业务关系表更新操作', 't_user_list', null, '/userbusiness/updates', 0, '1');
insert into T_MENU (id, name, pid, description, pageurl, type, state) values ('t_user_business_return', '业务关系表返回操作', 't_user_list', null, '/user/list', 0, '1');
insert into T_MENU (id, name, pid, description, pageurl, type, state) values ('t_user_business_goto', '业务关系表跳转操作', 't_user_list', null, '/userbusiness/buslist', 0, '1');
-- ----------------------------
-- Records of t_org
-- ----------------------------
INSERT INTO t_org (id, name, comcode, pid, sysid, type, leaf, sortno, description, state)
VALUES ('8ab05a1acfd54590942b88fc6d4d77ee', '测试部门', 'test', null, null, null, null, '1', '', '1');

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO t_role (id, name, code, pid, remark, state) VALUES ('admin', '管理员', null, null, '', '1');

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('09bf2e03f127496cb8bb0b157ecb325a', 'admin', 't_org_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('1c497f5e142e4da883eef3cba9766501', 'admin', 't_dic_data_minzu_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('1c497f5e142e4da883eef3cba9766591', 'admin', 't_dic_data_minzu_tree');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('1e972905e7cc46ca8eb601aca19abaa6', 'admin', 't_dic_data_grade_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('1e972905e7cc46ca8eb601aca19ab8a6', 'admin', 't_dic_data_grade_tree');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('23c6f39769da49049b574e445ec2a222', 'admin', 'system_manager');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('24e38c16d029435882a89777fe8d7822', 'admin', 't_org_deletemore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('24e38c16d029435882a89777fe8d7722', 'admin', 't_org_tree');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('29c92c1e1b4a4c13b5c731b9997e0c92', 'admin', 't_menu_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('29c92c1e1b4a4c13b5c731b9997e0c82', 'admin', 't_menu_tree');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('2fa0a289b0a04d508bede70dcd76516c', 'admin', 't_user_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('45a071f489024a769173b9a9418e1312', 'admin', 't_auditlog_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('4c5ecbab923347d88ff41391eec84b2c', 'admin', 't_auditlog_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('63d8308fb9944ff8a141cadc9e30a992', 'admin', 't_dic_data_xueli_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('63d8308fb9944ff8a141cadc9e30a982', 'admin', 't_dic_data_xueli_tree');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('6780735e60564141bb3ba88c1b6cf0bd', 'admin', 't_fwlog_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('69e596a905a049ec98759d8dd0ec22ca', 'admin', 't_role_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('6d8f4da381b246858f05c6051ea917ff', 'admin', 't_dic_data_xueli_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('6eaf9d1109b64667912b69172c945850', 'admin', 't_dic_data_grade_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('709609415b60464e823ecbb8cde40ae7', 'admin', 't_role_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('70d08e9699234a6d8ed53af6f38c2497', 'admin', 't_dic_data_grade_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('7a291125162a45398b55d5531af4704d', 'admin', 't_dic_data_minzu_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('7c1cc86c2f8a4037a61073935acaa6e6', 'admin', 't_role_deletemore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('8bf46ebc137c47d880239f88ce141279', 'admin', 't_dic_data_xueli_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('966b72eb5aec46a6ae90731e35c260b6', 'admin', 't_org_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('a407a97128ae418ebb02401b7a36a200', 'admin', 't_fwlog_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('a46e434392ac4e169d41a2d454d04e09', 'admin', 't_menu_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('a56cbbbb5d80460aa906a7548ac0be52', 'admin', 't_menu_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('a81ad30ab08b428b8cfe26a0f15526fe', 'admin', 't_dic_data_xueli_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('ae758ef55f374d28a034d1ed12c4c8b3', 'admin', 't_role_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('afcf3bb5bc5b4cc3bebf2c31772222c3', 'admin', 't_user_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('b981bf4d6c344f6d840ab4aac0b12e02', 'admin', 't_user_list_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('ba87ada9266f49849e83343f65a39d28', 'admin', 't_user_deletemore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('bb95749540704db6a8478992c7fc36cb', 'admin', 't_user_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('be1c18c1c91b4268b16f1e26258a137c', 'admin', 't_role_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('c1fce66d083443bc8726c515f8952653', 'admin', 't_user_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('c3755d00f1f64fee969ba7bad7da384b', 'admin', 't_dic_data_grade_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('c81550d9114b4c5f99ae4db691957264', 'admin', 't_dic_data_minzu_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('cfc436828e814893b42956a608eba31a', 'admin', 't_dic_data_xueli_deletemore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('d1fca3af82ec497d8e1e71afab6f2fd0', 'admin', 't_dic_data_minzu_deletemore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('d65212b71f044973990ca37f770f20d2', 'admin', 'business_manager');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('d9b92e67b1724cafac39f789cdc9ac42', 'admin', 't_dic_data_grade_deletemore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('d9ca534d4d35411a8deda2cf1955625b', 'admin', 't_dic_data_minzu_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('de4a08807d274bd89e556e0ef907eff6', 'admin', 't_org_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('e0602d38c0ce483181a925d4bb321593', 'admin', 't_menu_deletemore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('e6f1fd90c4064c5396dcd95f865fbd98', 'admin', 't_menu_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('e9bef821d5234e17ae20c8c5d57b921f', 'admin', 'dic_manager');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('fb2021ec726b44709090e5df9dbe2e7a', 'admin', 't_org_list');

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO t_user (id, name,account, password, workno, sex, state)
VALUES ('admin', 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', '1', '1');
-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO t_user_role (id, user_id, role_id) VALUES ('admin_admin', 'admin', 'admin');

