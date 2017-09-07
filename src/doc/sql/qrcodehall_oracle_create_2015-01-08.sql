
create table T_ACCEPT_RECORD
(
  ID           VARCHAR2(64) not null,
  ACCOUNT      VARCHAR2(64),
  MOBILE       VARCHAR2(16),
  REWARD       NUMBER(11,2),
  IP           VARCHAR2(32),
  OPERATE_TYPE VARCHAR2(32),
  CHANNEL_CODE VARCHAR2(32),
  ORDER_ID     VARCHAR2(256),
  HINT         VARCHAR2(1024),
  BUS_ID       VARCHAR2(1024),
  FEE_CODE     VARCHAR2(2048),
  DATETIME     DATE,
  REMARK       VARCHAR2(2048)
);
comment on table T_ACCEPT_RECORD
  is '受理记录表';
comment on column T_ACCEPT_RECORD.ID
  is '主键';
comment on column T_ACCEPT_RECORD.ACCOUNT
  is '用户账号';
comment on column T_ACCEPT_RECORD.MOBILE
  is '客户手机号';
comment on column T_ACCEPT_RECORD.REWARD
  is '酬金';
comment on column T_ACCEPT_RECORD.IP
  is 'IP地址';
comment on column T_ACCEPT_RECORD.OPERATE_TYPE
  is '操作类型';
comment on column T_ACCEPT_RECORD.CHANNEL_CODE
  is '渠道编号';
comment on column T_ACCEPT_RECORD.ORDER_ID
  is 'BOSS工单号';
comment on column T_ACCEPT_RECORD.HINT
  is '提示信息';
comment on column T_ACCEPT_RECORD.BUS_ID
  is '业务ID';
comment on column T_ACCEPT_RECORD.FEE_CODE
  is '资费代码';
comment on column T_ACCEPT_RECORD.DATETIME
  is '受理时间';
comment on column T_ACCEPT_RECORD.REMARK
  is '备注信息';
alter table T_ACCEPT_RECORD
  add constraint PK_T_ACCEPT_RECORD primary key (ID);



create table T_BRAND
(
  ID         VARCHAR2(64) not null,
  BRAND_CODE VARCHAR2(64),
  BRAND_NAME VARCHAR2(128),
  STATE      VARCHAR2(2)
);
comment on table T_BRAND
  is '品牌表';
comment on column T_BRAND.ID
  is '主键';
comment on column T_BRAND.BRAND_CODE
  is '品牌编号';
comment on column T_BRAND.BRAND_NAME
  is '品牌名称';
comment on column T_BRAND.STATE
  is '是否有效（0否/1是）';
alter table T_BRAND
  add constraint PK_T_BRAND primary key (ID);



create table T_BUSINESS
(
  ID             VARCHAR2(64) not null,
  BUS_CODE       VARCHAR2(64),
  BUS_NAME       VARCHAR2(256),
  DEFAULT_REWARD NUMBER(11,2),
  REMARK         VARCHAR2(1024),
  SORT           NUMBER(11),
  STATE          VARCHAR2(2),
  UP_DOWN        VARCHAR2(2),
  BUS_TYPE       VARCHAR2(64),
  BUS_URL        VARCHAR2(1024)
);
comment on table T_BUSINESS
  is '业务表';
comment on column T_BUSINESS.ID
  is '主键';
comment on column T_BUSINESS.BUS_CODE
  is '业务编号';
comment on column T_BUSINESS.BUS_NAME
  is '业务名称';
comment on column T_BUSINESS.DEFAULT_REWARD
  is '默认酬金';
comment on column T_BUSINESS.REMARK
  is '备注';
comment on column T_BUSINESS.SORT
  is '排序';
comment on column T_BUSINESS.STATE
  is '是否有效，0否/1是';
comment on column T_BUSINESS.UP_DOWN
  is '上下架，0下架/1上架';
comment on column T_BUSINESS.BUS_TYPE
  is '业务类型';
comment on column T_BUSINESS.BUS_URL
  is '业务URL';
alter table T_BUSINESS
  add constraint PK_T_BUSINESS primary key (ID);



create table T_COUNTY
(
  ID          VARCHAR2(64) not null,
  REGION_CODE VARCHAR2(64),
  COUNTY_CODE VARCHAR2(64),
  COUNTY_NAME VARCHAR2(128),
  STATE       VARCHAR2(2)
);
comment on table T_COUNTY
  is '区县表';
comment on column T_COUNTY.ID
  is '主键';
comment on column T_COUNTY.REGION_CODE
  is '地市编号';
comment on column T_COUNTY.COUNTY_CODE
  is '区县编号';
comment on column T_COUNTY.COUNTY_NAME
  is '区县名称';
comment on column T_COUNTY.STATE
  is '是否有效（1有效、0无效）';
alter table T_COUNTY
  add constraint PK_T_COUNTY primary key (ID);


  
create table T_EXECUTE_TIME
(
  ID           VARCHAR2(64) not null,
  CLASS_NAME   VARCHAR2(256),
  METHOD_NAME  VARCHAR2(64),
  INPUT_PARAM  VARCHAR2(4000),
  OUTPUT_PARAM VARCHAR2(4000),
  TIME_CONSUME VARCHAR2(16),
  CALL_TIME    VARCHAR2(32),
  REMARK       VARCHAR2(2048),
  MOBILE       VARCHAR2(16),
  ACCOUNT      VARCHAR2(64)
);
comment on table T_EXECUTE_TIME
  is '接口执行时间统计';
comment on column T_EXECUTE_TIME.ID
  is '主键';
comment on column T_EXECUTE_TIME.CLASS_NAME
  is '类名';
comment on column T_EXECUTE_TIME.METHOD_NAME
  is '方法名';
comment on column T_EXECUTE_TIME.INPUT_PARAM
  is '入参串';
comment on column T_EXECUTE_TIME.OUTPUT_PARAM
  is '出参串';
comment on column T_EXECUTE_TIME.TIME_CONSUME
  is '耗时';
comment on column T_EXECUTE_TIME.CALL_TIME
  is '调用时间';
comment on column T_EXECUTE_TIME.REMARK
  is '备注';
comment on column T_EXECUTE_TIME.MOBILE
  is '手机号';
comment on column T_EXECUTE_TIME.ACCOUNT
  is '用户账号';
alter table T_EXECUTE_TIME
  add constraint PK_T_EXECUTE_TIME primary key (ID);


  

create table T_GOODS
(
  ID              VARCHAR2(64) not null,
  GOODS_NAME      VARCHAR2(256),
  FEE_CODE        VARCHAR2(64),
  GOODS_DESC      VARCHAR2(512),
  FEE_STANDARD    VARCHAR2(512),
  DETAIL_DESC     VARCHAR2(2048),
  EFFECT_WAY      VARCHAR2(64),
  TRANSACT_WAY    VARCHAR2(64),
  KEY_CHARSET     VARCHAR2(1024),
  FIRST_CATEGORY  VARCHAR2(128),
  SECOND_CATEGORY VARCHAR2(128),
  THIRD_CATEGORY  VARCHAR2(128),
  BUS_ID          VARCHAR2(64),
  SORT            NUMBER(11),
  STATE           VARCHAR2(2),
  UP_DOWN        VARCHAR2(2)
);
comment on table T_GOODS
  is '商品表';
comment on column T_GOODS.ID
  is '主键';
comment on column T_GOODS.GOODS_NAME
  is '商品名称';
comment on column T_GOODS.FEE_CODE
  is '资费代码';
comment on column T_GOODS.GOODS_DESC
  is '商品简介（50）';
comment on column T_GOODS.FEE_STANDARD
  is '资费标准（20）';
comment on column T_GOODS.DETAIL_DESC
  is '详细描述（300）';
comment on column T_GOODS.EFFECT_WAY
  is '生效方式';
comment on column T_GOODS.TRANSACT_WAY
  is '办理方式';
comment on column T_GOODS.KEY_CHARSET
  is '智能搜索关键字';
comment on column T_GOODS.FIRST_CATEGORY
  is '一级分类';
comment on column T_GOODS.SECOND_CATEGORY
  is '二级分类';
comment on column T_GOODS.THIRD_CATEGORY
  is '三级分类';
comment on column T_GOODS.BUS_ID
  is '业务ID';
comment on column T_GOODS.SORT
  is '排序';
comment on column T_GOODS.STATE
  is '是否有效（0否/1是）';
comment on column T_GOODS.UP_DOWN
  is '上下架，0下架/1上架';
alter table T_GOODS
  add constraint PK_T_GOODS primary key (ID);


  

create table T_OFFICE
(
  ID          VARCHAR2(64) not null,
  REGION_CODE VARCHAR2(64),
  COUNTY_CODE VARCHAR2(64),
  OFFICE_CODE VARCHAR2(64),
  OFFICE_NAME VARCHAR2(256),
  ADDRESS     VARCHAR2(1024),
  STATE       VARCHAR2(2)
);
comment on table T_OFFICE
  is '营业厅表';
comment on column T_OFFICE.ID
  is '主键';
comment on column T_OFFICE.REGION_CODE
  is '地市编号';
comment on column T_OFFICE.COUNTY_CODE
  is '区县编号';
comment on column T_OFFICE.OFFICE_CODE
  is '营业厅编号';
comment on column T_OFFICE.OFFICE_NAME
  is '营业厅名称';
comment on column T_OFFICE.ADDRESS
  is '营业厅地址';
comment on column T_OFFICE.STATE
  is '是否有效（1有效、0无效）';
alter table T_OFFICE
  add constraint PK_T_OFFICE primary key (ID);


  

create table T_QRCODE
(
  ID          VARCHAR2(64) not null,
  QRCODE_NAME VARCHAR2(256),
  DETAIL_DESC VARCHAR2(1024),
  BELONG_USER VARCHAR2(64),
  CREATE_USER VARCHAR2(64),
  CREATE_DATE DATE,
  ICON_NAME   VARCHAR2(128),
  ICON_URL    VARCHAR2(512),
  STATE       VARCHAR2(2),
  FLOW_NO     VARCHAR2(64),
  SIG         VARCHAR2(4000),
  PARAM_STR   VARCHAR2(4000)
);
comment on table T_QRCODE
  is '二维码表';
comment on column T_QRCODE.ID
  is '主键';
comment on column T_QRCODE.QRCODE_NAME
  is '二维码名称';
comment on column T_QRCODE.DETAIL_DESC
  is '描述';
comment on column T_QRCODE.BELONG_USER
  is '归属人';
comment on column T_QRCODE.CREATE_USER
  is '创建人';
comment on column T_QRCODE.CREATE_DATE
  is '创建时间';
comment on column T_QRCODE.ICON_NAME
  is '图片名称';
comment on column T_QRCODE.ICON_URL
  is '图片地址';
comment on column T_QRCODE.STATE
  is '是否有效';
comment on column T_QRCODE.FLOW_NO
  is '流水号';
comment on column T_QRCODE.SIG
  is '签名串';
comment on column T_QRCODE.PARAM_STR
  is '参数串';
alter table T_QRCODE
  add constraint PK_T_QRCODE primary key (ID);


  
  

create table T_QRCODE_BUSINESS
(
  ID        VARCHAR2(64) not null,
  QRCODE_ID VARCHAR2(64),
  BUS_ID    VARCHAR2(64),
  FEE_CODE  VARCHAR2(64)
);
comment on table T_QRCODE_BUSINESS
  is '二维码与业务关系';
comment on column T_QRCODE_BUSINESS.ID
  is '主键';
comment on column T_QRCODE_BUSINESS.QRCODE_ID
  is '二维码ID';
comment on column T_QRCODE_BUSINESS.BUS_ID
  is '业务ID';
comment on column T_QRCODE_BUSINESS.FEE_CODE
  is '资费代码';
alter table T_QRCODE_BUSINESS
  add constraint PK_T_QRCODE_BUSINESS primary key (ID);


  
create table T_REGION
(
  ID          VARCHAR2(64) not null,
  REGION_CODE VARCHAR2(64),
  REGION_NAME VARCHAR2(128),
  STATE       VARCHAR2(2)
);
comment on table T_REGION
  is '地市表';
comment on column T_REGION.ID
  is '主键';
comment on column T_REGION.REGION_CODE
  is '地市编号';
comment on column T_REGION.REGION_NAME
  is '地市名称';
comment on column T_REGION.STATE
  is '是否有效（1有效、0无效）';
alter table T_REGION
  add constraint PK_T_REGION primary key (ID);


  
  
create table T_REGION_BUSINESS
(
  ID        VARCHAR2(64) not null,
  REGION_ID VARCHAR2(64),
  BUS_ID    VARCHAR2(64),
  REWARD    NUMBER(11,2)
);
comment on table T_REGION_BUSINESS
  is '地市与业务关系';
comment on column T_REGION_BUSINESS.ID
  is '主键';
comment on column T_REGION_BUSINESS.REGION_ID
  is '地市ID';
comment on column T_REGION_BUSINESS.BUS_ID
  is '业务ID';
comment on column T_REGION_BUSINESS.REWARD
  is '酬金';
alter table T_REGION_BUSINESS
  add constraint PK_T_REGION_BUSINESS primary key (ID);


  
create table T_USER_BUSINESS
(
  ID      VARCHAR2(64) not null,
  USER_ID VARCHAR2(64),
  BUS_ID  VARCHAR2(64)
);
comment on table T_USER_BUSINESS
  is '用户与业务关系';
comment on column T_USER_BUSINESS.ID
  is '主键';
comment on column T_USER_BUSINESS.USER_ID
  is '用户ID';
comment on column T_USER_BUSINESS.BUS_ID
  is '业务ID';
alter table T_USER_BUSINESS
  add constraint PK_T_USER_BUSINESS primary key (ID);


  

create table T_USER_OFFICE
(
  ID          VARCHAR2(64) not null,
  USER_ID     VARCHAR2(64),
  REGION_CODE VARCHAR2(64),
  COUNTY_CODE VARCHAR2(64),
  OFFICE_CODE VARCHAR2(64),
  POST        VARCHAR2(2048)
);
comment on table T_USER_OFFICE 
  is '用户与营业厅关系';
comment on column T_USER_OFFICE.ID
  is '主键';
comment on column T_USER_OFFICE.USER_ID
  is '用户ID';
comment on column T_USER_OFFICE.REGION_CODE
  is '地市编号';
comment on column T_USER_OFFICE.COUNTY_CODE
  is '区县编号';
comment on column T_USER_OFFICE.OFFICE_CODE
  is '营业厅编号';
comment on column T_USER_OFFICE.POST
  is '用户岗位，多个用逗号分隔';
alter table T_USER_OFFICE
  add constraint PK_T_USER_OFFICE primary key (ID);

  
  
