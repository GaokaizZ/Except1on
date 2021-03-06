<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>  
<#assign classNameLowerCase = className?lower_case>

<#assign pid="business_manager" />
<#if table.sqlName?starts_with("t_")>
<#assign pid="system_manager" />
</#if>

-- -----------------------------------
-- ${table.sqlName}
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('${table.sqlName}_list','${table.tableAlias}管理', '${pid}', null,'/${classNameLowerCase}/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('${table.sqlName}_update','修改${table.tableAlias}', '${table.sqlName}_list', null,'/${classNameLowerCase}/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('${table.sqlName}_look','查看${table.tableAlias}', '${table.sqlName}_list', null,'/${classNameLowerCase}/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('${table.sqlName}_export','导出${table.tableAlias}', '${table.sqlName}_list', null,'/${classNameLowerCase}/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('${table.sqlName}_delete','删除${table.tableAlias}', '${table.sqlName}_list', null,'/${classNameLowerCase}/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('${table.sqlName}_deleteMore','批量删除${table.tableAlias}', '${table.sqlName}_list', null,'/${classNameLowerCase}/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('${table.sqlName}_upload','导入${table.tableAlias}', '${table.sqlName}_list', null,'/${classNameLowerCase}/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('${table.sqlName}_list_admin', 'admin', '${table.sqlName}_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('${table.sqlName}_update_admin', 'admin', '${table.sqlName}_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('${table.sqlName}_look_admin', 'admin', '${table.sqlName}_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('${table.sqlName}_export_admin', 'admin', '${table.sqlName}_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('${table.sqlName}_delete_admin', 'admin', '${table.sqlName}_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('${table.sqlName}_deleteMore_admin', 'admin', '${table.sqlName}_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('${table.sqlName}_upload_admin', 'admin', '${table.sqlName}_upload');


