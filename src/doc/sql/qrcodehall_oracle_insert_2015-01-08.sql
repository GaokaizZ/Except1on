-- -----------------------------------
-- T_ACCEPT_RECORD
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_ACCEPT_RECORD_list','受理记录表管理', 'business_manager', null,'/acceptrecord/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_ACCEPT_RECORD_update','修改受理记录表', 'T_ACCEPT_RECORD_list', null,'/acceptrecord/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_ACCEPT_RECORD_look','查看受理记录表', 'T_ACCEPT_RECORD_list', null,'/acceptrecord/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_ACCEPT_RECORD_export','导出受理记录表', 'T_ACCEPT_RECORD_list', null,'/acceptrecord/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_ACCEPT_RECORD_delete','删除受理记录表', 'T_ACCEPT_RECORD_list', null,'/acceptrecord/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_ACCEPT_RECORD_deleteMore','批量删除受理记录表', 'T_ACCEPT_RECORD_list', null,'/acceptrecord/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_ACCEPT_RECORD_upload','导入受理记录表', 'T_ACCEPT_RECORD_list', null,'/acceptrecord/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_ACCEPT_RECORD_list_admin', 'admin', 'T_ACCEPT_RECORD_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_ACCEPT_RECORD_update_admin', 'admin', 'T_ACCEPT_RECORD_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_ACCEPT_RECORD_look_admin', 'admin', 'T_ACCEPT_RECORD_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_ACCEPT_RECORD_export_admin', 'admin', 'T_ACCEPT_RECORD_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_ACCEPT_RECORD_delete_admin', 'admin', 'T_ACCEPT_RECORD_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_ACCEPT_RECORD_deleteMore_admin', 'admin', 'T_ACCEPT_RECORD_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_ACCEPT_RECORD_upload_admin', 'admin', 'T_ACCEPT_RECORD_upload');


-- -----------------------------------
-- T_BRAND
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BRAND_list','品牌表管理', 'business_manager', null,'/brand/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BRAND_update','修改品牌表', 'T_BRAND_list', null,'/brand/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BRAND_look','查看品牌表', 'T_BRAND_list', null,'/brand/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BRAND_export','导出品牌表', 'T_BRAND_list', null,'/brand/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BRAND_delete','删除品牌表', 'T_BRAND_list', null,'/brand/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BRAND_deleteMore','批量删除品牌表', 'T_BRAND_list', null,'/brand/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BRAND_upload','导入品牌表', 'T_BRAND_list', null,'/brand/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BRAND_list_admin', 'admin', 'T_BRAND_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BRAND_update_admin', 'admin', 'T_BRAND_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BRAND_look_admin', 'admin', 'T_BRAND_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BRAND_export_admin', 'admin', 'T_BRAND_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BRAND_delete_admin', 'admin', 'T_BRAND_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BRAND_deleteMore_admin', 'admin', 'T_BRAND_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BRAND_upload_admin', 'admin', 'T_BRAND_upload');



-- -----------------------------------
-- T_BUSINESS
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BUSINESS_list','业务表管理', 'business_manager', null,'/business/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BUSINESS_update','修改业务表', 'T_BUSINESS_list', null,'/business/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BUSINESS_look','查看业务表', 'T_BUSINESS_list', null,'/business/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BUSINESS_export','导出业务表', 'T_BUSINESS_list', null,'/business/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BUSINESS_delete','删除业务表', 'T_BUSINESS_list', null,'/business/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BUSINESS_deleteMore','批量删除业务表', 'T_BUSINESS_list', null,'/business/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_BUSINESS_upload','导入业务表', 'T_BUSINESS_list', null,'/business/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BUSINESS_list_admin', 'admin', 'T_BUSINESS_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BUSINESS_update_admin', 'admin', 'T_BUSINESS_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BUSINESS_look_admin', 'admin', 'T_BUSINESS_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BUSINESS_export_admin', 'admin', 'T_BUSINESS_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BUSINESS_delete_admin', 'admin', 'T_BUSINESS_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BUSINESS_deleteMore_admin', 'admin', 'T_BUSINESS_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_BUSINESS_upload_admin', 'admin', 'T_BUSINESS_upload');



-- -----------------------------------
-- T_COUNTY
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_COUNTY_list','区县表管理', 'business_manager', null,'/county/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_COUNTY_update','修改区县表', 'T_COUNTY_list', null,'/county/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_COUNTY_look','查看区县表', 'T_COUNTY_list', null,'/county/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_COUNTY_export','导出区县表', 'T_COUNTY_list', null,'/county/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_COUNTY_delete','删除区县表', 'T_COUNTY_list', null,'/county/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_COUNTY_deleteMore','批量删除区县表', 'T_COUNTY_list', null,'/county/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_COUNTY_upload','导入区县表', 'T_COUNTY_list', null,'/county/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_COUNTY_list_admin', 'admin', 'T_COUNTY_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_COUNTY_update_admin', 'admin', 'T_COUNTY_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_COUNTY_look_admin', 'admin', 'T_COUNTY_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_COUNTY_export_admin', 'admin', 'T_COUNTY_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_COUNTY_delete_admin', 'admin', 'T_COUNTY_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_COUNTY_deleteMore_admin', 'admin', 'T_COUNTY_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_COUNTY_upload_admin', 'admin', 'T_COUNTY_upload');



-- -----------------------------------
-- T_EXECUTE_TIME
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_EXECUTE_TIME_list','接口执行时间统计管理', 'business_manager', null,'/executetime/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_EXECUTE_TIME_update','修改接口执行时间统计', 'T_EXECUTE_TIME_list', null,'/executetime/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_EXECUTE_TIME_look','查看接口执行时间统计', 'T_EXECUTE_TIME_list', null,'/executetime/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_EXECUTE_TIME_export','导出接口执行时间统计', 'T_EXECUTE_TIME_list', null,'/executetime/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_EXECUTE_TIME_delete','删除接口执行时间统计', 'T_EXECUTE_TIME_list', null,'/executetime/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_EXECUTE_TIME_deleteMore','批量删除接口执行时间统计', 'T_EXECUTE_TIME_list', null,'/executetime/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_EXECUTE_TIME_upload','导入接口执行时间统计', 'T_EXECUTE_TIME_list', null,'/executetime/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_EXECUTE_TIME_list_admin', 'admin', 'T_EXECUTE_TIME_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_EXECUTE_TIME_update_admin', 'admin', 'T_EXECUTE_TIME_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_EXECUTE_TIME_look_admin', 'admin', 'T_EXECUTE_TIME_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_EXECUTE_TIME_export_admin', 'admin', 'T_EXECUTE_TIME_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_EXECUTE_TIME_delete_admin', 'admin', 'T_EXECUTE_TIME_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_EXECUTE_TIME_deleteMore_admin', 'admin', 'T_EXECUTE_TIME_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_EXECUTE_TIME_upload_admin', 'admin', 'T_EXECUTE_TIME_upload');



-- -----------------------------------
-- T_GOODS
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_GOODS_list','商品表管理', 'business_manager', null,'/goods/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_GOODS_update','修改商品表', 'T_GOODS_list', null,'/goods/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_GOODS_look','查看商品表', 'T_GOODS_list', null,'/goods/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_GOODS_export','导出商品表', 'T_GOODS_list', null,'/goods/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_GOODS_delete','删除商品表', 'T_GOODS_list', null,'/goods/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_GOODS_deleteMore','批量删除商品表', 'T_GOODS_list', null,'/goods/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_GOODS_upload','导入商品表', 'T_GOODS_list', null,'/goods/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_GOODS_list_admin', 'admin', 'T_GOODS_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_GOODS_update_admin', 'admin', 'T_GOODS_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_GOODS_look_admin', 'admin', 'T_GOODS_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_GOODS_export_admin', 'admin', 'T_GOODS_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_GOODS_delete_admin', 'admin', 'T_GOODS_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_GOODS_deleteMore_admin', 'admin', 'T_GOODS_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_GOODS_upload_admin', 'admin', 'T_GOODS_upload');



-- -----------------------------------
-- T_OFFICE
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_OFFICE_list','营业厅表管理', 'business_manager', null,'/office/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_OFFICE_update','修改营业厅表', 'T_OFFICE_list', null,'/office/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_OFFICE_look','查看营业厅表', 'T_OFFICE_list', null,'/office/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_OFFICE_export','导出营业厅表', 'T_OFFICE_list', null,'/office/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_OFFICE_delete','删除营业厅表', 'T_OFFICE_list', null,'/office/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_OFFICE_deleteMore','批量删除营业厅表', 'T_OFFICE_list', null,'/office/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_OFFICE_upload','导入营业厅表', 'T_OFFICE_list', null,'/office/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_OFFICE_list_admin', 'admin', 'T_OFFICE_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_OFFICE_update_admin', 'admin', 'T_OFFICE_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_OFFICE_look_admin', 'admin', 'T_OFFICE_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_OFFICE_export_admin', 'admin', 'T_OFFICE_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_OFFICE_delete_admin', 'admin', 'T_OFFICE_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_OFFICE_deleteMore_admin', 'admin', 'T_OFFICE_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_OFFICE_upload_admin', 'admin', 'T_OFFICE_upload');



-- -----------------------------------
-- T_QRCODE
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_list','二维码表管理', 'business_manager', null,'/qrcode/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_update','修改二维码表', 'T_QRCODE_list', null,'/qrcode/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_look','查看二维码表', 'T_QRCODE_list', null,'/qrcode/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_export','导出二维码表', 'T_QRCODE_list', null,'/qrcode/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_delete','删除二维码表', 'T_QRCODE_list', null,'/qrcode/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_deleteMore','批量删除二维码表', 'T_QRCODE_list', null,'/qrcode/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_upload','导入二维码表', 'T_QRCODE_list', null,'/qrcode/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_list_admin', 'admin', 'T_QRCODE_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_update_admin', 'admin', 'T_QRCODE_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_look_admin', 'admin', 'T_QRCODE_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_export_admin', 'admin', 'T_QRCODE_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_delete_admin', 'admin', 'T_QRCODE_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_deleteMore_admin', 'admin', 'T_QRCODE_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_upload_admin', 'admin', 'T_QRCODE_upload');



-- -----------------------------------
-- T_QRCODE_BUSINESS
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_BUSINESS_list','二维码与业务关系管理', 'business_manager', null,'/qrcodebusiness/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_BUSINESS_update','修改二维码与业务关系', 'T_QRCODE_BUSINESS_list', null,'/qrcodebusiness/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_BUSINESS_look','查看二维码与业务关系', 'T_QRCODE_BUSINESS_list', null,'/qrcodebusiness/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_BUSINESS_export','导出二维码与业务关系', 'T_QRCODE_BUSINESS_list', null,'/qrcodebusiness/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_BUSINESS_delete','删除二维码与业务关系', 'T_QRCODE_BUSINESS_list', null,'/qrcodebusiness/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_BUSINESS_deleteMore','批量删除二维码与业务关系', 'T_QRCODE_BUSINESS_list', null,'/qrcodebusiness/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_QRCODE_BUSINESS_upload','导入二维码与业务关系', 'T_QRCODE_BUSINESS_list', null,'/qrcodebusiness/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_BUSINESS_list_admin', 'admin', 'T_QRCODE_BUSINESS_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_BUSINESS_update_admin', 'admin', 'T_QRCODE_BUSINESS_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_BUSINESS_look_admin', 'admin', 'T_QRCODE_BUSINESS_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_BUSINESS_export_admin', 'admin', 'T_QRCODE_BUSINESS_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_BUSINESS_delete_admin', 'admin', 'T_QRCODE_BUSINESS_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_BUSINESS_deleteMore_admin', 'admin', 'T_QRCODE_BUSINESS_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_QRCODE_BUSINESS_upload_admin', 'admin', 'T_QRCODE_BUSINESS_upload');



-- -----------------------------------
-- T_REGION
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_list','地市表管理', 'business_manager', null,'/region/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_update','修改地市表', 'T_REGION_list', null,'/region/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_look','查看地市表', 'T_REGION_list', null,'/region/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_export','导出地市表', 'T_REGION_list', null,'/region/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_delete','删除地市表', 'T_REGION_list', null,'/region/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_deleteMore','批量删除地市表', 'T_REGION_list', null,'/region/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_upload','导入地市表', 'T_REGION_list', null,'/region/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_list_admin', 'admin', 'T_REGION_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_update_admin', 'admin', 'T_REGION_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_look_admin', 'admin', 'T_REGION_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_export_admin', 'admin', 'T_REGION_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_delete_admin', 'admin', 'T_REGION_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_deleteMore_admin', 'admin', 'T_REGION_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_upload_admin', 'admin', 'T_REGION_upload');



-- -----------------------------------
-- T_REGION_BUSINESS
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_BUSINESS_list','地市与业务关系管理', 'business_manager', null,'/regionbusiness/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_BUSINESS_update','修改地市与业务关系', 'T_REGION_BUSINESS_list', null,'/regionbusiness/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_BUSINESS_look','查看地市与业务关系', 'T_REGION_BUSINESS_list', null,'/regionbusiness/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_BUSINESS_export','导出地市与业务关系', 'T_REGION_BUSINESS_list', null,'/regionbusiness/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_BUSINESS_delete','删除地市与业务关系', 'T_REGION_BUSINESS_list', null,'/regionbusiness/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_BUSINESS_deleteMore','批量删除地市与业务关系', 'T_REGION_BUSINESS_list', null,'/regionbusiness/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_REGION_BUSINESS_upload','导入地市与业务关系', 'T_REGION_BUSINESS_list', null,'/regionbusiness/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_BUSINESS_list_admin', 'admin', 'T_REGION_BUSINESS_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_BUSINESS_update_admin', 'admin', 'T_REGION_BUSINESS_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_BUSINESS_look_admin', 'admin', 'T_REGION_BUSINESS_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_BUSINESS_export_admin', 'admin', 'T_REGION_BUSINESS_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_BUSINESS_delete_admin', 'admin', 'T_REGION_BUSINESS_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_BUSINESS_deleteMore_admin', 'admin', 'T_REGION_BUSINESS_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_REGION_BUSINESS_upload_admin', 'admin', 'T_REGION_BUSINESS_upload');



-- -----------------------------------
-- T_USER_BUSINESS
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_BUSINESS_list','用户与业务关系管理', 'business_manager', null,'/userbusiness/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_BUSINESS_update','修改用户与业务关系', 'T_USER_BUSINESS_list', null,'/userbusiness/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_BUSINESS_look','查看用户与业务关系', 'T_USER_BUSINESS_list', null,'/userbusiness/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_BUSINESS_export','导出用户与业务关系', 'T_USER_BUSINESS_list', null,'/userbusiness/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_BUSINESS_delete','删除用户与业务关系', 'T_USER_BUSINESS_list', null,'/userbusiness/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_BUSINESS_deleteMore','批量删除用户与业务关系', 'T_USER_BUSINESS_list', null,'/userbusiness/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_BUSINESS_upload','导入用户与业务关系', 'T_USER_BUSINESS_list', null,'/userbusiness/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_BUSINESS_list_admin', 'admin', 'T_USER_BUSINESS_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_BUSINESS_update_admin', 'admin', 'T_USER_BUSINESS_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_BUSINESS_look_admin', 'admin', 'T_USER_BUSINESS_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_BUSINESS_export_admin', 'admin', 'T_USER_BUSINESS_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_BUSINESS_delete_admin', 'admin', 'T_USER_BUSINESS_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_BUSINESS_deleteMore_admin', 'admin', 'T_USER_BUSINESS_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_BUSINESS_upload_admin', 'admin', 'T_USER_BUSINESS_upload');



-- -----------------------------------
-- T_USER_OFFICE
-- -----------------------------------
-- Records of t_menu
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_OFFICE_list','用户与营业厅关系管理', 'business_manager', null,'/useroffice/list','1','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_OFFICE_update','修改用户与营业厅关系', 'T_USER_OFFICE_list', null,'/useroffice/update','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_OFFICE_look','查看用户与营业厅关系', 'T_USER_OFFICE_list', null,'/useroffice/look','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_OFFICE_export','导出用户与营业厅关系', 'T_USER_OFFICE_list', null,'/useroffice/list/export','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_OFFICE_delete','删除用户与营业厅关系', 'T_USER_OFFICE_list', null,'/useroffice/delete','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_OFFICE_deleteMore','批量删除用户与营业厅关系', 'T_USER_OFFICE_list', null,'/useroffice/delete/more','0','1');
INSERT INTO t_menu (id, name, pid, description, pageurl, type, state) VALUES('T_USER_OFFICE_upload','导入用户与营业厅关系', 'T_USER_OFFICE_list', null,'/useroffice/upload','0','1');
-- Records of t_role_menu
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_OFFICE_list_admin', 'admin', 'T_USER_OFFICE_list');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_OFFICE_update_admin', 'admin', 'T_USER_OFFICE_update');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_OFFICE_look_admin', 'admin', 'T_USER_OFFICE_look');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_OFFICE_export_admin', 'admin', 'T_USER_OFFICE_export');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_OFFICE_delete_admin', 'admin', 'T_USER_OFFICE_delete');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_OFFICE_deleteMore_admin', 'admin', 'T_USER_OFFICE_deleteMore');
INSERT INTO t_role_menu (id, role_id, menu_id) VALUES ('T_USER_OFFICE_upload_admin', 'admin', 'T_USER_OFFICE_upload');


