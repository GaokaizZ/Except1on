-- Create table
create table T_AUDITLOG_HISTORY_2015
(
  ID                 VARCHAR2(64) not null,
  OPERATION_TYPE     VARCHAR2(64),
  OPERATOR_NAME      VARCHAR2(64),
  PRE_VALUE          VARCHAR2(4000),
  CUR_VALUE          VARCHAR2(4000),
  OPERATION_TIME     DATE,
  OPERATION_CLASS    VARCHAR2(512),
  OPERATION_CLASS_ID VARCHAR2(64)
);
-- Add comments to the table 
comment on table T_AUDITLOG_HISTORY_2015
  is '操作日志';
-- Add comments to the columns 
comment on column T_AUDITLOG_HISTORY_2015.ID
  is 'ID';
comment on column T_AUDITLOG_HISTORY_2015.OPERATION_TYPE
  is '操作类型';
comment on column T_AUDITLOG_HISTORY_2015.OPERATOR_NAME
  is '操作人姓名';
comment on column T_AUDITLOG_HISTORY_2015.PRE_VALUE
  is '旧值';
comment on column T_AUDITLOG_HISTORY_2015.CUR_VALUE
  is '新值';
comment on column T_AUDITLOG_HISTORY_2015.OPERATION_TIME
  is '操作时间';
comment on column T_AUDITLOG_HISTORY_2015.OPERATION_CLASS
  is '操作类';
comment on column T_AUDITLOG_HISTORY_2015.OPERATION_CLASS_ID
  is '记录ID';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_AUDITLOG_HISTORY_2015 add constraint PK_t_auditlog_history_2014 primary key (ID);


-- Create table
create table T_FWLOG_HISTORY_2015
(
  ID         VARCHAR2(64) not null,
  START_DATE DATE,
  STR_DATE   VARCHAR2(128),
  TOMCAT     VARCHAR2(64),
  USER_CODE  VARCHAR2(64),
  USER_NAME  VARCHAR2(256),
  SESSION_ID VARCHAR2(128),
  IP         VARCHAR2(128),
  FW_URL     VARCHAR2(2048),
  MENU_NAME  VARCHAR2(512),
  ISQX       VARCHAR2(2)
);
-- Add comments to the table 
comment on table T_FWLOG_HISTORY_2015
  is '访问日志';
-- Add comments to the columns 
comment on column T_FWLOG_HISTORY_2015.ID
  is 'ID';
comment on column T_FWLOG_HISTORY_2015.START_DATE
  is '访问时间';
comment on column T_FWLOG_HISTORY_2015.STR_DATE
  is '访问时间(毫秒)';
comment on column T_FWLOG_HISTORY_2015.TOMCAT
  is 'Tomcat';
comment on column T_FWLOG_HISTORY_2015.USER_CODE
  is '登录账号';
comment on column T_FWLOG_HISTORY_2015.USER_NAME
  is '用户名';
comment on column T_FWLOG_HISTORY_2015.SESSION_ID
  is 'sessionId';
comment on column T_FWLOG_HISTORY_2015.IP
  is 'IP';
comment on column T_FWLOG_HISTORY_2015.FW_URL
  is '访问菜单';
comment on column T_FWLOG_HISTORY_2015.MENU_NAME
  is '菜单名称';
comment on column T_FWLOG_HISTORY_2015.ISQX
  is '是否有权限访问';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_FWLOG_HISTORY_2015 add constraint PK_t_fwlog_history_2014 primary key (ID);


-- Create table
create table T_DIC_DATA
(
  ID          VARCHAR2(64) not null,
  NAME        VARCHAR2(256),
  CODE        VARCHAR2(64),
  PID         VARCHAR2(64),
  SORT        NUMBER(11),
  REMARK      VARCHAR2(1024),
  STATE       VARCHAR2(2),
  TYPEKEY     VARCHAR2(32),
  CREATE_USER VARCHAR2(64),
  CREATE_DATE DATE,
  UPDATE_USER VARCHAR2(64),
  UPDATE_DATE DATE
);
-- Add comments to the table 
comment on table T_DIC_DATA
  is '数据字典';
-- Add comments to the columns 
comment on column T_DIC_DATA.ID
  is '主键ID';
comment on column T_DIC_DATA.NAME
  is '名称';
comment on column T_DIC_DATA.CODE
  is '编码';
comment on column T_DIC_DATA.PID
  is '父ID';
comment on column T_DIC_DATA.SORT
  is '排序';
comment on column T_DIC_DATA.REMARK
  is '描述';
comment on column T_DIC_DATA.STATE
  is '是否有效（0否/1是）';
comment on column T_DIC_DATA.TYPEKEY
  is '类型';
comment on column T_DIC_DATA.CREATE_USER
  is '创建人账号';
comment on column T_DIC_DATA.CREATE_DATE
  is '创建时间';
comment on column T_DIC_DATA.UPDATE_USER
  is '修改人账号';
comment on column T_DIC_DATA.UPDATE_DATE
  is '修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_DIC_DATA add constraint PK_t_dic_data primary key (ID);


-- Create table
create table T_MENU
(
  ID          VARCHAR2(64) not null,
  NAME        VARCHAR2(256),
  PID         VARCHAR2(64),
  DESCRIPTION VARCHAR2(1024),
  PAGEURL     VARCHAR2(2048),
  TYPE        NUMBER(11),
  STATE       VARCHAR2(2),
  SORT        NUMBER(11),
  ICON        VARCHAR2(256),
  REMARK      VARCHAR2(1024),
  CREATE_USER VARCHAR2(64),
  CREATE_DATE DATE,
  UPDATE_USER VARCHAR2(64),
  UPDATE_DATE DATE
);
-- Add comments to the table 
comment on table T_MENU
  is '菜单';
-- Add comments to the columns 
comment on column T_MENU.ID
  is '主键ID';
comment on column T_MENU.NAME
  is '名称';
comment on column T_MENU.PID
  is '父ID';
comment on column T_MENU.DESCRIPTION
  is '描述';
comment on column T_MENU.PAGEURL
  is 'URL地址';
comment on column T_MENU.TYPE
  is '0.功能按钮,1.导航菜单';
comment on column T_MENU.STATE
  is '是否有效（0否/1是）';
comment on column T_MENU.SORT
  is '排序';
comment on column T_MENU.ICON
  is '图标';
comment on column T_MENU.REMARK
  is '备注';
comment on column T_MENU.CREATE_USER
  is '创建人账号';
comment on column T_MENU.CREATE_DATE
  is '创建时间';
comment on column T_MENU.UPDATE_USER
  is '修改人账号';
comment on column T_MENU.UPDATE_DATE
  is '修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MENU add constraint PK_t_menu primary key (ID);


-- Create table
create table T_ORG
(
  ID          VARCHAR2(64) not null,
  NAME        VARCHAR2(256),
  COMCODE     VARCHAR2(64),
  PID         VARCHAR2(64),
  SYSID       VARCHAR2(128),
  TYPE        NUMBER(11),
  LEAF        NUMBER(11),
  SORTNO      NUMBER(11),
  DESCRIPTION VARCHAR2(1024),
  STATE       VARCHAR2(2),
  ORG_GRADE   NUMBER(11),
  CREATE_USER VARCHAR2(64),
  CREATE_DATE DATE,
  UPDATE_USER VARCHAR2(64),
  UPDATE_DATE DATE
);
-- Add comments to the table 
comment on table T_ORG
  is '组织';
-- Add comments to the columns 
comment on column T_ORG.ID
  is '主键ID';
comment on column T_ORG.NAME
  is '组织名称';
comment on column T_ORG.COMCODE
  is '组织代码';
comment on column T_ORG.PID
  is '上级组织ID';
comment on column T_ORG.SYSID
  is '子系统ID';
comment on column T_ORG.TYPE
  is '0组织、1部门、2岗位';
comment on column T_ORG.LEAF
  is '叶子节点(0:树枝节点;1:叶子节点)';
comment on column T_ORG.SORTNO
  is '排序号';
comment on column T_ORG.DESCRIPTION
  is '描述';
comment on column T_ORG.STATE
  is '是否有效(0否/1是)';
comment on column T_ORG.ORG_GRADE
  is '组织等级';
comment on column T_ORG.CREATE_USER
  is '创建人账号';
comment on column T_ORG.CREATE_DATE
  is '创建时间';
comment on column T_ORG.UPDATE_USER
  is '修改人账号';
comment on column T_ORG.UPDATE_DATE
  is '修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ORG add constraint PK_t_org primary key (ID);
  
  
-- Create table
create table T_ROLE
(
  ID          VARCHAR2(64) not null,
  NAME        VARCHAR2(128),
  CODE        VARCHAR2(64),
  PID         VARCHAR2(64),
  REMARK      VARCHAR2(1024),
  STATE       VARCHAR2(2),
  GRADE       NUMBER(11),
  ROLE_TYPE   VARCHAR2(2),
  CREATE_USER VARCHAR2(64),
  CREATE_DATE DATE,
  UPDATE_USER VARCHAR2(64),
  UPDATE_DATE DATE
);
-- Add comments to the table 
comment on table T_ROLE
  is '角色';
-- Add comments to the columns 
comment on column T_ROLE.ID
  is '角色主键';
comment on column T_ROLE.NAME
  is '角色名称';
comment on column T_ROLE.CODE
  is '角色编号';
comment on column T_ROLE.PID
  is '上级角色主键';
comment on column T_ROLE.REMARK
  is '备注';
comment on column T_ROLE.STATE
  is '是否有效(0否/1是)';
comment on column T_ROLE.GRADE
  is '角色等级';
comment on column T_ROLE.ROLE_TYPE
  is '角色类型';
comment on column T_ROLE.CREATE_USER
  is '创建人账号';
comment on column T_ROLE.CREATE_DATE
  is '创建时间';
comment on column T_ROLE.UPDATE_USER
  is '修改人账号';
comment on column T_ROLE.UPDATE_DATE
  is '修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ROLE add constraint PK_t_role primary key (ID);
  
  
-- Create table
create table T_ROLE_MENU
(
  ID          VARCHAR2(64) not null,
  ROLE_ID     VARCHAR2(64),
  MENU_ID     VARCHAR2(64),
  CREATE_USER VARCHAR2(64),
  CREATE_DATE DATE
);
-- Add comments to the table 
comment on table T_ROLE_MENU
  is '角色菜单关系';
-- Add comments to the columns 
comment on column T_ROLE_MENU.ID
  is '主键ID';
comment on column T_ROLE_MENU.ROLE_ID
  is '角色ID';
comment on column T_ROLE_MENU.MENU_ID
  is '菜单ID';
comment on column T_ROLE_MENU.CREATE_USER
  is '创建人账号';
comment on column T_ROLE_MENU.CREATE_DATE
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ROLE_MENU add constraint PK_t_role_menu primary key (ID);
  
  
-- Create table
create table T_USER
(
  ID          VARCHAR2(64) not null,
  NAME        VARCHAR2(128),
  ACCOUNT     VARCHAR2(64),
  PASSWORD    VARCHAR2(64),
  WORKNO      VARCHAR2(64),
  CARDNO      VARCHAR2(32),
  AGE         NUMBER(11),
  SEX         VARCHAR2(2),
  PHONE       VARCHAR2(16),
  MOBILE      VARCHAR2(16),
  EMAIL       VARCHAR2(64),
  ADDRESS     VARCHAR2(512),
  GRADE_ID    VARCHAR2(64),
  EDU_NAME    VARCHAR2(32),
  FIRE_NAME   VARCHAR2(64),
  FIRE_PHONE  VARCHAR2(32),
  DESCRIPTION VARCHAR2(2048),
  WEIXIN_ID   VARCHAR2(256),
  STATE       VARCHAR2(2),
  POST_CODE   VARCHAR2(16),
  CREATE_USER VARCHAR2(64),
  CREATE_DATE DATE,
  UPDATE_USER VARCHAR2(64),
  UPDATE_DATE DATE
);
-- Add comments to the table 
comment on table T_USER
  is '用户';
-- Add comments to the columns 
comment on column T_USER.ID
  is '主键ID';
comment on column T_USER.NAME
  is '姓名';
comment on column T_USER.ACCOUNT
  is '账号';
comment on column T_USER.PASSWORD
  is '密码';
comment on column T_USER.WORKNO
  is '工号';
comment on column T_USER.CARDNO
  is '身份证';
comment on column T_USER.AGE
  is '年龄';
comment on column T_USER.SEX
  is '性别(1男/0女)';
comment on column T_USER.PHONE
  is '电话号码';
comment on column T_USER.MOBILE
  is '手机号码';
comment on column T_USER.EMAIL
  is '邮箱';
comment on column T_USER.ADDRESS
  is '地址';
comment on column T_USER.GRADE_ID
  is '级别';
comment on column T_USER.EDU_NAME
  is '学历';
comment on column T_USER.FIRE_NAME
  is '紧急联系人';
comment on column T_USER.FIRE_PHONE
  is '紧急联系电话';
comment on column T_USER.DESCRIPTION
  is '备注';
comment on column T_USER.WEIXIN_ID
  is '微信Id';
comment on column T_USER.STATE
  is '是否有效,1是/0否/离职';
comment on column T_USER.POST_CODE
  is '邮政编码';
comment on column T_USER.CREATE_USER
  is '创建人账号';
comment on column T_USER.CREATE_DATE
  is '创建时间';
comment on column T_USER.UPDATE_USER
  is '修改人账号';
comment on column T_USER.UPDATE_DATE
  is '修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_USER add constraint PK_t_user primary key (ID);


-- Create table
create table T_USER_ORG
(
  ID          VARCHAR2(64) not null,
  USER_ID     VARCHAR2(64),
  ORG_ID      VARCHAR2(64),
  MANAGER     VARCHAR2(2),
  CREATE_USER VARCHAR2(64),
  CREATE_DATE DATE
);
-- Add comments to the table 
comment on table T_USER_ORG
  is '用户组织关系';
-- Add comments to the columns 
comment on column T_USER_ORG.ID
  is '主键ID';
comment on column T_USER_ORG.USER_ID
  is '用户ID';
comment on column T_USER_ORG.ORG_ID
  is '组织ID';
comment on column T_USER_ORG.MANAGER
  is '是否主管,1是/0否';
comment on column T_USER_ORG.CREATE_USER
  is '创建人账号';
comment on column T_USER_ORG.CREATE_DATE
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_USER_ORG add constraint PK_t_user_org primary key (ID);
  
  
-- Create table
create table T_USER_ROLE
(
  ID          VARCHAR2(64) not null,
  USER_ID     VARCHAR2(64),
  ROLE_ID     VARCHAR2(64),
  CREATE_USER VARCHAR2(64),
  CREATE_DATE DATE
);
-- Add comments to the table 
comment on table T_USER_ROLE
  is '用户角色关系';
-- Add comments to the columns 
comment on column T_USER_ROLE.ID
  is '主键ID';
comment on column T_USER_ROLE.USER_ID
  is '用户ID';
comment on column T_USER_ROLE.ROLE_ID
  is '角色ID';
comment on column T_USER_ROLE.CREATE_USER
  is '创建人账号';
comment on column T_USER_ROLE.CREATE_DATE
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_USER_ROLE add constraint PK_t_user_role primary key (ID);

