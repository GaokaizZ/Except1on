
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_auditlog_history_2015
-- ----------------------------
DROP TABLE IF EXISTS `T_AUDITLOG_HISTORY_2015`;
CREATE TABLE `T_AUDITLOG_HISTORY_2015` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `OPERATION_TYPE` varchar(64) DEFAULT NULL COMMENT '操作类型',
  `OPERATOR_NAME` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `PRE_VALUE` text COMMENT '旧值',
  `CUR_VALUE` text COMMENT '新值',
  `OPERATION_TIME` datetime DEFAULT NULL COMMENT '操作时间',
  `OPERATION_CLASS` varchar(512) DEFAULT NULL COMMENT '操作类',
  `OPERATION_CLASS_ID` varchar(64) DEFAULT NULL COMMENT '记录ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Table structure for t_fwlog_history_2015
-- ----------------------------
DROP TABLE IF EXISTS `T_FWLOG_HISTORY_2015`;
CREATE TABLE `T_FWLOG_HISTORY_2015` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `START_DATE` datetime DEFAULT NULL COMMENT '访问时间',
  `STR_DATE` varchar(128) DEFAULT NULL COMMENT '访问时间(毫秒)',
  `TOMCAT` varchar(64) DEFAULT NULL COMMENT 'Tomcat',
  `USER_CODE` varchar(64) DEFAULT NULL COMMENT '登录账号',
  `USER_NAME` varchar(256) DEFAULT NULL COMMENT '用户名',
  `SESSION_ID` varchar(128) DEFAULT NULL COMMENT 'sessionId',
  `IP` varchar(128) DEFAULT NULL COMMENT 'IP',
  `FW_URL` varchar(2048) DEFAULT NULL COMMENT '访问菜单',
  `MENU_NAME` varchar(512) DEFAULT NULL COMMENT '菜单名称',
  `ISQX` varchar(2) DEFAULT NULL COMMENT '是否有权限访问',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访问日志';

-- ----------------------------
-- Table structure for t_dic_data
-- ----------------------------
DROP TABLE IF EXISTS `T_DIC_DATA`;
CREATE TABLE `T_DIC_DATA` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `NAME` varchar(256) NOT NULL COMMENT '名称',
  `CODE` varchar(64) DEFAULT NULL COMMENT '编码',
  `PID` varchar(64) DEFAULT NULL COMMENT '父ID',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  `REMARK` varchar(1024) DEFAULT NULL COMMENT '描述',
  `STATE` varchar(2) DEFAULT '1' COMMENT '是否有效（0否/1是）',
  `TYPEKEY` varchar(32) DEFAULT NULL COMMENT '类型',
  
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(64) DEFAULT NULL COMMENT '修改人账号',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `T_MENU`;
CREATE TABLE `t_menu` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `NAME` varchar(256) DEFAULT NULL COMMENT '名称',
  `PID` varchar(64) DEFAULT NULL COMMENT '父ID',
  `DESCRIPTION` varchar(1024) DEFAULT NULL COMMENT '描述',
  `PAGEURL` varchar(2048) DEFAULT NULL COMMENT 'URL地址',
  `TYPE` int(11) DEFAULT NULL COMMENT '0.功能按钮,1.导航菜单',
  `STATE` varchar(2) DEFAULT '1' COMMENT '是否有效（0否/1是）',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  `ICON` varchar(256) DEFAULT NULL COMMENT '图表',
  
  `REMARK` varchar(1024) DEFAULT NULL COMMENT '备注',
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(64) DEFAULT NULL COMMENT '修改人账号',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Table structure for t_org
-- ----------------------------
DROP TABLE IF EXISTS `T_ORG`;
CREATE TABLE `T_ORG` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `NAME` varchar(256) DEFAULT NULL COMMENT '组织名称',
  `COMCODE` varchar(64) DEFAULT NULL COMMENT '组织代码',
  `PID` varchar(64) DEFAULT NULL COMMENT '上级组织ID',
  `SYSID` varchar(128) DEFAULT NULL COMMENT '子系统ID',
  `TYPE` int(11) DEFAULT NULL COMMENT '0组织、1部门、2岗位',
  `LEAF` int(11) DEFAULT NULL COMMENT '叶子节点(0:树枝节点;1:叶子节点)',
  `SORTNO` int(11) DEFAULT NULL COMMENT '排序号',
  `DESCRIPTION` varchar(1024) DEFAULT NULL COMMENT '描述',
  `STATE` varchar(2) DEFAULT '1' COMMENT '是否有效(0否/1是)',
  
  `ORG_GRADE` int(11) DEFAULT NULL COMMENT '组织等级',
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(64) DEFAULT NULL COMMENT '修改人账号',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织';

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `T_ROLE`;
CREATE TABLE `T_ROLE` (
  `ID` varchar(64) NOT NULL COMMENT '角色主键',
  `NAME` varchar(128) DEFAULT NULL COMMENT '角色名称',
  `CODE` varchar(64) DEFAULT NULL COMMENT '角色编号',
  `PID` varchar(64) DEFAULT NULL COMMENT '上级角色主键',
  `REMARK` varchar(1024) DEFAULT NULL COMMENT '备注',
  `STATE` varchar(2) DEFAULT '1' COMMENT '是否有效(0否/1是)',
  
  `GRADE` int(11) DEFAULT NULL COMMENT '角色等级',
  `ROLE_TYPE` varchar(2) DEFAULT NULL COMMENT '角色类型',
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(64) DEFAULT NULL COMMENT '修改人账号',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `T_ROLE_MENU`;
CREATE TABLE `T_ROLE_MENU` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `ROLE_ID` varchar(64) NOT NULL COMMENT '角色ID',
  `MENU_ID` varchar(64) NOT NULL COMMENT '菜单ID',
  
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关系';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `T_USER`;
CREATE TABLE `T_USER` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `NAME` varchar(128) DEFAULT NULL COMMENT '姓名',
  `ACCOUNT` varchar(64) DEFAULT NULL COMMENT '账号',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT '密码',
  `WORKNO` varchar(64) DEFAULT NULL COMMENT '工号',
  `CARDNO` varchar(32) DEFAULT NULL COMMENT '身份证',
  `AGE` int(11) DEFAULT NULL COMMENT '年龄',
  `SEX` varchar(2) DEFAULT '1' COMMENT '性别(1男/0女)',
  `PHONE` varchar(16) DEFAULT NULL COMMENT '电话号码',
  `MOBILE` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `EMAIL` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `ADDRESS` varchar(512) DEFAULT NULL COMMENT '地址',
  `GRADE_ID` varchar(64) DEFAULT NULL COMMENT '级别',
  `EDU_NAME` varchar(32) DEFAULT NULL COMMENT '学历',
  `FIRE_NAME` varchar(64) DEFAULT NULL COMMENT '紧急联系人',
  `FIRE_PHONE` varchar(32) DEFAULT NULL COMMENT '紧急联系电话',
  `DESCRIPTION` varchar(2048) DEFAULT NULL COMMENT '备注',
  `WEIXIN_ID` varchar(256) DEFAULT NULL COMMENT '微信Id',
  `STATE` varchar(2) DEFAULT '1' COMMENT '是否有效,1是/0否/离职',
  
  `POST_CODE` varchar(16) DEFAULT NULL COMMENT '邮政编码',
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(64) DEFAULT NULL COMMENT '修改人账号',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Table structure for t_user_org
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ORG`;
CREATE TABLE `T_USER_ORG` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `USER_ID` varchar(64) NOT NULL COMMENT '用户ID',
  `ORG_ID` varchar(64) NOT NULL COMMENT '组织ID',
  `MANAGER` varchar(2) DEFAULT '0' COMMENT '是否主管,1是/0否',
  
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组织关系';

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ROLE`;
CREATE TABLE `T_USER_ROLE` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `USER_ID` varchar(64) NOT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(64) NOT NULL COMMENT '角色ID',
  
  `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人账号',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

