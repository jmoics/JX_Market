
/*==============================================================*/
/* Table: T_EMP_AREA                                            */
/*==============================================================*/
create table T_EMP_AREA
(
   AREA_N_CODIGO        int not null,
   EMPRESA_N_CODIGO     int not null,
   AREA_V_NOMBRE        varchar(50),
   primary key (AREA_N_CODIGO)
);

/*==============================================================*/
/* Table: T_EMP_EMPLEADO                                        */
/*==============================================================*/
create table T_EMP_EMPLEADO
(
   EMPLEADO_N_CODIGO    int not null,
   PERFIL_N_CODIGO      int not null,
   USUARIO_N_CODIGO     int not null,
   EMPRESA_N_CODIGO     int not null,
   EMPLEADO_V_NOMBRE    varchar(50),
   EMPLEADO_V_APELLIDO  varchar(50),
   EMPLEADO_V_DNI       varchar(15),
   EMPLEADO_V_DIRECCION varchar(70),
   EMPLEADO_V_TELEFONO  varchar(15),
   EMPLEADO_V_CELULAR   varchar(20),
   EMPLEADO_V_EMAIL     varchar(30),
   EMPLEADO_V_CIUDAD    varchar(20),
   EMPLEADO_V_REGION    varchar(20),
   EMPLEADO_D_FECHNAC   date,
   EMPLEADO_N_ACTIVO    int,
   primary key (EMPLEADO_N_CODIGO)
);

/*==============================================================*/
/* Table: T_EMP_EMPRESA                                         */
/*==============================================================*/
create table T_EMP_EMPRESA
(
   EMPRESA_N_CODIGO     int not null,
   EMPRESA_V_RAZONSOCIAL varchar(200),
   EMPRESA_N_ACTIVO     int,
   EMPRESA_V_RUC        varchar(11),
   primary key (EMPRESA_N_CODIGO)
);

/*==============================================================*/
/* Table: T_NEG_ARTICULO                                        */
/*==============================================================*/
create table T_NEG_ARTICULO
(
   ARTICULO_N_CODIGO    int not null,
   EMPRESA_N_CODIGO     int not null,
   CATEGORIA_N_CODIGO   int not null,
   ARTICULO_V_NOMBRE    varchar(70),
   ARTICULO_V_MARCA     varchar(30),
   ARTICULO_V_DESCRIPCION varchar(4000),
   ARTICULO_N_PRECIO    float(8,2),
   ARTICULO_N_STOCK     int,
   ARTICULO_N_ACTIVO    int,
   primary key (ARTICULO_N_CODIGO)
);

/*==============================================================*/
/* Table: T_NEG_CATEGORIA                                       */
/*==============================================================*/
create table T_NEG_CATEGORIA
(
   CATEGORIA_N_CODIGO   int not null,
   EMPRESA_N_CODIGO     int not null,
   CATEGORIA_N_NOMBRE   varchar(60),
   primary key (CATEGORIA_N_CODIGO)
);

/*==============================================================*/
/* Table: T_NEG_CLIENTE                                         */
/*==============================================================*/
create table T_NEG_CLIENTE
(
   CLIENTE_N_CODIGO     int not null,
   EMPRESA_N_CODIGO     int not null,
   USUARIO_N_CODIGO     int not null,
   CLIENTE_V_NOMBRE     varchar(60),
   CLIENTE_V_APELLIDO   varchar(60),
   CLIENTE_D_FECHANAC   date,
   CLIENTE_N_DNI        int,
   CLIENTE_V_DIRECCION  varchar(80),
   CLIENTE_V_TELEFONO   smallint,
   CLIENTE_V_CELULAR    smallint,
   CLIENTE_V_EMAIL      varchar(30),
   CLIENTE_V_CIUDAD     varchar(20),
   CLIENTE_V_REGION     varchar(20),
   CLIENTE_V_CODPOSTAL  smallint,
   CLIENTE_N_ACTIVO     bool,
   primary key (CLIENTE_N_CODIGO)
);

/*==============================================================*/
/* Table: T_NEG_PEDIDO                                          */
/*==============================================================*/
create table T_NEG_PEDIDO
(
   PEDIDO_N_CODIGO      int not null,
   EMPRESA_N_CODIGO     int not null,
   CLIENTE_N_CODIGO     int not null,
   TIPOPED_N_CODIGO     int not null,
   PEDIDO_D_FECHA       date,
   PEDIDO_N_IGV         int,
   PEDIDO_N_TOTAL       float(8,2),
   primary key (PEDIDO_N_CODIGO)
);

/*==============================================================*/
/* Table: T_NEG_PEDIDO_DET                                      */
/*==============================================================*/
create table T_NEG_PEDIDO_DET
(
   PEDIDO_N_CODIGO      int not null,
   ARTICULO_N_CODIGO    int not null,
   DETPED_N_CANTIDAD    int,
   primary key (PEDIDO_N_CODIGO, ARTICULO_N_CODIGO)
);

/*==============================================================*/
/* Table: T_NEG_PEDIDO_TIPO                                     */
/*==============================================================*/
create table T_NEG_PEDIDO_TIPO
(
   TIPOPED_N_CODIGO     int not null,
   TIPOPED_V_DESCRIPCION varchar(50),
   primary key (TIPOPED_N_CODIGO)
);

/*==============================================================*/
/* Table: T_NEG_VENTA                                           */
/*==============================================================*/
create table T_NEG_VENTA
(
   VENTA_N_CODIGO       int not null,
   EMPRESA_N_CODIGO     int not null,
   TIPOVEN_N_CODIGO     int not null,
   PEDIDO_N_CODIGO      int not null,
   VENTA_D_FECHA        date,
   VENTA_N_IGV          int,
   VENTA_VENTA_N_TOTAL  float(8,2),
   primary key (VENTA_N_CODIGO)
);

/*==============================================================*/
/* Table: T_NEG_VENTA_TIPO                                      */
/*==============================================================*/
create table T_NEG_VENTA_TIPO
(
   TIPOVEN_N_CODIGO     int not null,
   EMPRESA_N_CODIGO     int not null,
   TIPOVEN_V_DESCRIPCION varchar(50),
   primary key (TIPOVEN_N_CODIGO)
);

/*==============================================================*/
/* Table: T_SEG_MODULO                                          */
/*==============================================================*/
create table T_SEG_MODULO
(
   MODULO_ID            float not null,
   EMPRESA_N_CODIGO     int not null,
   T_S_MODULO_ID        float not null,
   MODULO_DESC          varchar(50),
   primary key (MODULO_ID)
);

/*==============================================================*/
/* Table: T_SEG_MOD_DET                                         */
/*==============================================================*/
create table T_SEG_MOD_DET
(
   PERFIL_N_CODIGO      int not null,
   MODULO_ID            float not null,
   ROL_N_ID             int not null,
   MODDET_N_HABILITADO  int,
   primary key (PERFIL_N_CODIGO, MODULO_ID, ROL_N_ID)
);

/*==============================================================*/
/* Table: T_SEG_PERFIL                                          */
/*==============================================================*/
create table T_SEG_PERFIL
(
   PERFIL_N_CODIGO      int not null,
   EMPRESA_N_CODIGO     int not null,
   AREA_N_CODIGO        int not null,
   PERFIL_V_FUNCION     varchar(50),
   PERFIL_V_DESCRIPCION varchar(300),
   primary key (PERFIL_N_CODIGO)
);

/*==============================================================*/
/* Table: T_SEG_ROL                                             */
/*==============================================================*/
create table T_SEG_ROL
(
   ROL_N_ID             int not null,
   EMPRESA_N_CODIGO     int not null,
   ROL_V_DESCRIPCION    varchar(100),
   primary key (ROL_N_ID)
);

/*==============================================================*/
/* Table: T_SEG_ROL_DET                                         */
/*==============================================================*/
create table T_SEG_ROL_DET
(
   ROL_N_ID             int not null,
   USUARIO_N_CODIGO     int not null,
   primary key (ROL_N_ID, USUARIO_N_CODIGO)
);

/*==============================================================*/
/* Table: T_SEG_USUARIO                                         */
/*==============================================================*/
create table T_SEG_USUARIO
(
   USUARIO_N_CODIGO     int not null,
   EMPRESA_N_CODIGO     int not null,
   USUARIO_V_CONTRASENA varchar(150),
   USUARIO_V_USERNAME   varchar(45),
   primary key (USUARIO_N_CODIGO)
);

alter table T_EMP_AREA add constraint FK_REL_EMPRESA_AREA foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_EMP_EMPLEADO add constraint FK_REL_EMPLEADO_PERFIL foreign key (PERFIL_N_CODIGO)
      references T_SEG_PERFIL (PERFIL_N_CODIGO) on delete restrict on update restrict;

alter table T_EMP_EMPLEADO add constraint FK_REL_EMPLEADO_USUARIO foreign key (USUARIO_N_CODIGO)
      references T_SEG_USUARIO (USUARIO_N_CODIGO) on delete restrict on update restrict;

alter table T_EMP_EMPLEADO add constraint FK_REL_EMPRESA_EMPLEADO foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_ARTICULO add constraint FK_REL_ARTICULO_TIPO foreign key (CATEGORIA_N_CODIGO)
      references T_NEG_CATEGORIA (CATEGORIA_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_ARTICULO add constraint FK_REL_EMPRESA_ARTICULO foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_CATEGORIA add constraint FK_REL_EMPRESA_CATEGORIA foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_CLIENTE add constraint FK_REL_CLIENTE_USUARIO foreign key (USUARIO_N_CODIGO)
      references T_SEG_USUARIO (USUARIO_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_CLIENTE add constraint FK_REL_EMPRESA_CLIENTE foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_PEDIDO add constraint FK_REL_EMPRESA_PEDIDO foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_PEDIDO add constraint FK_REL_PEDIDO_CLIENTE foreign key (CLIENTE_N_CODIGO)
      references T_NEG_CLIENTE (CLIENTE_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_PEDIDO add constraint FK_REL_PEDIDO_TIPO foreign key (TIPOPED_N_CODIGO)
      references T_NEG_PEDIDO_TIPO (TIPOPED_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_PEDIDO_DET add constraint FK_T_NEG_PEDIDO_DET foreign key (PEDIDO_N_CODIGO)
      references T_NEG_PEDIDO (PEDIDO_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_PEDIDO_DET add constraint FK_T_NEG_PEDIDO_DET2 foreign key (ARTICULO_N_CODIGO)
      references T_NEG_ARTICULO (ARTICULO_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_VENTA add constraint FK_REL_EMPRESA_VENTA foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_VENTA add constraint FK_REL_VENTA_PEDIDO foreign key (PEDIDO_N_CODIGO)
      references T_NEG_PEDIDO (PEDIDO_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_VENTA add constraint FK_REL_VENTA_TIPO foreign key (TIPOVEN_N_CODIGO)
      references T_NEG_VENTA_TIPO (TIPOVEN_N_CODIGO) on delete restrict on update restrict;

alter table T_NEG_VENTA_TIPO add constraint FK_REL_EMPRESA_TIPOVENTA foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_SEG_MODULO add constraint FK_REL_EMPRESA_MODULO foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_SEG_MODULO add constraint FK_REL_MODULO_MODULO foreign key (T_S_MODULO_ID)
      references T_SEG_MODULO (MODULO_ID) on delete restrict on update restrict;

alter table T_SEG_MOD_DET add constraint FK_T_SEG_MOD_DET foreign key (PERFIL_N_CODIGO)
      references T_SEG_PERFIL (PERFIL_N_CODIGO) on delete restrict on update restrict;

alter table T_SEG_MOD_DET add constraint FK_T_SEG_MOD_DET2 foreign key (MODULO_ID)
      references T_SEG_MODULO (MODULO_ID) on delete restrict on update restrict;

alter table T_SEG_MOD_DET add constraint FK_T_SEG_MOD_DET3 foreign key (ROL_N_ID)
      references T_SEG_ROL (ROL_N_ID) on delete restrict on update restrict;

alter table T_SEG_PERFIL add constraint FK_REL_EMPRESA_PERFIL foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_SEG_PERFIL add constraint FK_REL_PERFIL_AREA foreign key (AREA_N_CODIGO)
      references T_EMP_AREA (AREA_N_CODIGO) on delete restrict on update restrict;

alter table T_SEG_ROL add constraint FK_REL_EMPRESA_ROL foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;

alter table T_SEG_ROL_DET add constraint FK_T_SEG_ROL_DET foreign key (ROL_N_ID)
      references T_SEG_ROL (ROL_N_ID) on delete restrict on update restrict;

alter table T_SEG_ROL_DET add constraint FK_T_SEG_ROL_DET2 foreign key (USUARIO_N_CODIGO)
      references T_SEG_USUARIO (USUARIO_N_CODIGO) on delete restrict on update restrict;

alter table T_SEG_USUARIO add constraint FK_REL_EMPRESA_USUARIO foreign key (EMPRESA_N_CODIGO)
      references T_EMP_EMPRESA (EMPRESA_N_CODIGO) on delete restrict on update restrict;
