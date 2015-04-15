--
-- Table structure for table T_COM_COMPANY
--

DROP TABLE IF EXISTS T_COM_COMPANY;
CREATE TABLE T_COM_COMPANY (
  COMPANYID BIGINT NOT NULL,
  BUSINESSNAME varchar(200) DEFAULT NULL,
  ACTIVE BIGINT DEFAULT NULL,
  DOCNUMBER varchar(11) DEFAULT NULL,
  DOMAIN varchar(10) DEFAULT NULL,
  PRIMARY KEY (COMPANYID)
);


CREATE UNIQUE INDEX T_COM_COMPANY_DOMAIN_UNIQUE ON T_COM_COMPANY(DOMAIN);

--
-- Table structure for table T_EMP_AREA
--

DROP TABLE IF EXISTS T_EMP_AREA;
CREATE TABLE T_EMP_AREA (
  AREA_N_CODIGO BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  AREA_V_NOMBRE varchar(50) DEFAULT NULL,
  PRIMARY KEY (AREA_N_CODIGO),
  CONSTRAINT FK_REL_COMPANY_AREA FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID)
);


--
-- Table structure for table T_SEG_PERFIL
--

DROP TABLE IF EXISTS T_SEG_PERFIL;
CREATE TABLE T_SEG_PERFIL (
  PERFIL_N_CODIGO BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  AREA_N_CODIGO BIGINT NOT NULL,
  PERFIL_V_FUNCION varchar(50) DEFAULT NULL,
  PERFIL_V_DESCRIPCION varchar(300) DEFAULT NULL,
  PRIMARY KEY (PERFIL_N_CODIGO),
  CONSTRAINT FK_REL_COMPANY_PERFIL FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID),
  CONSTRAINT FK_REL_PERFIL_AREA FOREIGN KEY (AREA_N_CODIGO) REFERENCES T_EMP_AREA (AREA_N_CODIGO)
);


--
-- Table structure for table T_SEG_USUARIO
--

DROP TABLE IF EXISTS T_SEG_USUARIO;
CREATE TABLE T_SEG_USUARIO (
  USUARIO_N_CODIGO BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  USUARIO_V_CONTRASENA varchar(150) DEFAULT NULL,
  USUARIO_V_USERNAME varchar(45) DEFAULT NULL,
  PRIMARY KEY (USUARIO_N_CODIGO),
  CONSTRAINT FK_REL_COMPANY_USUARIO FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID)
);


--
-- Table structure for table T_SEG_MODULO
--

DROP TABLE IF EXISTS T_SEG_MODULO;
CREATE TABLE T_SEG_MODULO (
  MODULO_N_CODIGO BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  MODULO_V_DESC varchar(50) DEFAULT NULL,
  MODULO_V_RECURSO varchar(45) DEFAULT NULL,
  PRIMARY KEY (MODULO_N_CODIGO),
  CONSTRAINT FK_REL_COMPANY_MODULO FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID)
);


--
-- Table structure for table T_SEG_MOD_DET
--

DROP TABLE IF EXISTS T_SEG_MOD_DET;
CREATE TABLE T_SEG_MOD_DET (
  PERFIL_N_CODIGO BIGINT NOT NULL,
  MODULO_N_CODIGO BIGINT NOT NULL,
  MODDET_N_HABILITADO BIGINT DEFAULT NULL,
  PRIMARY KEY (PERFIL_N_CODIGO,MODULO_N_CODIGO),
  CONSTRAINT FK_T_SEG_MOD_DET FOREIGN KEY (PERFIL_N_CODIGO) REFERENCES T_SEG_PERFIL (PERFIL_N_CODIGO),
  CONSTRAINT FK_T_SEG_MOD_DET_1 FOREIGN KEY (MODULO_N_CODIGO) REFERENCES T_SEG_MODULO (MODULO_N_CODIGO) ON DELETE NO ACTION ON UPDATE NO ACTION
);


--
-- Table structure for table T_EMP_EMPLEADO
--

DROP TABLE IF EXISTS T_EMP_EMPLEADO;
CREATE TABLE T_EMP_EMPLEADO (
  EMPLEADO_N_CODIGO BIGINT NOT NULL,
  PERFIL_N_CODIGO BIGINT NOT NULL,
  USUARIO_N_CODIGO BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  EMPLEADO_V_NOMBRE varchar(50) DEFAULT NULL,
  EMPLEADO_V_APELLIDO varchar(50) DEFAULT NULL,
  EMPLEADO_V_DNI varchar(15) DEFAULT NULL,
  EMPLEADO_V_DIRECCION varchar(70) DEFAULT NULL,
  EMPLEADO_V_TELEFONO varchar(15) DEFAULT NULL,
  EMPLEADO_V_CELULAR varchar(20) DEFAULT NULL,
  EMPLEADO_V_EMAIL varchar(30) DEFAULT NULL,
  EMPLEADO_V_CIUDAD varchar(20) DEFAULT NULL,
  EMPLEADO_V_REGION varchar(20) DEFAULT NULL,
  EMPLEADO_D_FECHNAC date DEFAULT NULL,
  EMPLEADO_N_ACTIVO BIGINT DEFAULT NULL,
  PRIMARY KEY (EMPLEADO_N_CODIGO),
  CONSTRAINT FK_REL_EMPLEADO_PERFIL FOREIGN KEY (PERFIL_N_CODIGO) REFERENCES T_SEG_PERFIL (PERFIL_N_CODIGO),
  CONSTRAINT FK_REL_EMPLEADO_USUARIO FOREIGN KEY (USUARIO_N_CODIGO) REFERENCES T_SEG_USUARIO (USUARIO_N_CODIGO),
  CONSTRAINT FK_REL_COMPANY_EMPLEADO FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID)
) ;


--
-- Table structure for table T_BUS_CATEGORY
--

DROP TABLE IF EXISTS T_BUS_CATEGORY;
CREATE TABLE T_BUS_CATEGORY (
  ID BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  NAME varchar(60) DEFAULT NULL,
  ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,
  PARENTID BIGINT,
  IMAGE varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_COMPANY_CATEGORY FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID),
  CONSTRAINT FK_REL_CATEGORY_CATEGORY FOREIGN KEY (PARENTID) REFERENCES T_BUS_CATEGORY (ID)
);

DROP TABLE IF EXISTS T_BUS_TRADEMARK;
CREATE TABLE T_BUS_TRADEMARK (
  ID BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  NAME VARCHAR(70) NOT NULL,
  ACTIVE BOOLEAN DEFAULT TRUE NOT NULL,
  IMAGE VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_TRADEMARK_COMPANY FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID)
);

--
-- Table structure for table T_BUS_CATEGORY
--

DROP TABLE IF EXISTS T_BUS_ATRIBUTO;
CREATE TABLE T_BUS_ATRIBUTO (
  ID BIGINT NOT NULL,
  NOMBRE VARCHAR(20) NOT NULL,
  DESCR VARCHAR(80),
  ACTIVO INTEGER DEFAULT 1,
  FILTRABLE INTEGER DEFAULT 0,
  --ATR_N_PREDET BIGINT DEFAULT NULL,    --Para el precio por ejemplo, que debe existir si o si.
  CATEGORYID BIGINT,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_ATRIBUTO_CATEGORY FOREIGN KEY (CATEGORYID) REFERENCES T_BUS_CATEGORY (ID)
);

--
-- Table structure for table T_BUS_PRODUCT
--

DROP TABLE IF EXISTS T_BUS_PRODUCT;
CREATE TABLE T_BUS_PRODUCT (
  ID BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  NAME varchar(70) DEFAULT NULL,
  TRADEMARKID BIGINT NOT NULL,
  DESCRIPTION varchar(4000) DEFAULT NULL,
  --ARTICULO_N_PRECIO DECIMAL(8,2) DEFAULT NULL,
  --ARTICULO_N_STOCK BIGINT DEFAULT '0',
  ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,
  --IMAGE varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_ARTICULO_COMPANY FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID),
  CONSTRAINT FK_REL_ARTICULO_TRADEMARK FOREIGN KEY (TRADEMARKID) REFERENCES T_BUS_TRADEMARK (ID)
);

DROP TABLE IF EXISTS T_BUS_PRODUCTIMAGE;
CREATE TABLE T_BUS_PRODUCTIMAGE (
  ID BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  NOMBRE VARCHAR(70) NOT NULL,
  PRODUCTID BIGINT NOT NULL,
  IMAGDEFAULT BOOLEAN DEFAULT FALSE NOT NULL,
  ACTIVE BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_PRODUCTIMAGE_COMPANY FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID),
  CONSTRAINT FK_REL_PRODUCTIMAGE_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_BUS_PRODUCT (ID)
);


DROP TABLE IF EXISTS T_BUS_ARTIC2ATRIB;
CREATE TABLE T_BUS_ARTIC2ATRIB (
  ID BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  PRODUCTID BIGINT NOT NULL,
  ATTRIBUTEID BIGINT NOT NULL,
  NOMBRE VARCHAR(20) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_DESDE_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_BUS_PRODUCT (ID),
  CONSTRAINT FK_REL_HASTA_ATRIBUTO FOREIGN KEY (ATTRIBUTEID) REFERENCES T_BUS_ATRIBUTO (ID)
);


DROP TABLE IF EXISTS T_BUS_INVENTARIO;
CREATE TABLE T_BUS_INVENTARIO (
  ID BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  PRODUCTID BIGINT NOT NULL,
  STOCK DECIMAL(8,2),
  RESERVA DECIMAL(8,2),
  FECHA DATE,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_INVENTARIO_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_BUS_PRODUCT (ID),
  CONSTRAINT FK_REL_INVENTARIO_COMPANY FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID)
);


DROP TABLE IF EXISTS T_BUS_PRECIO;
CREATE TABLE T_BUS_PRECIO (
  ID BIGINT NOT NULL,
  PRODUCTID BIGINT NOT NULL,
  DESDE DATE,
  HASTA DATE,
  MONTO DECIMAL(14,4),
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_PRECIO_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_BUS_PRODUCT (ID)
);


DROP TABLE IF EXISTS T_BUS_COSTO;
CREATE TABLE T_BUS_COSTO (
  ID BIGINT NOT NULL,
  PRODUCTID BIGINT NOT NULL,
  DESDE DATE,
  HASTA DATE,
  MONTO DECIMAL(14,4),
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_COSTO_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_BUS_PRODUCT (ID)
);


DROP TABLE IF EXISTS T_BUS_OFERTA;
CREATE TABLE T_BUS_OFERTA (
  ID BIGINT NOT NULL,
  PRODUCTID BIGINT NOT NULL,
  DESDE DATE,
  HASTA DATE,
  MONTO DECIMAL(14,4),
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_OFERTA_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_BUS_PRODUCT (ID)
);


DROP TABLE IF EXISTS T_BUS_CATEG2ARTIC;
CREATE TABLE T_BUS_CATEG2ARTIC (
  ID BIGINT NOT NULL,
  CATEGORYID BIGINT NOT NULL,
  PRODUCTID BIGINT NOT NULL,
  CONSTRAINT FK_REL_CATEGORY_FROM FOREIGN KEY (CATEGORYID) REFERENCES T_BUS_CATEGORY (ID),
  CONSTRAINT FK_REL_ARTICULO_TO FOREIGN KEY (PRODUCTID) REFERENCES T_BUS_PRODUCT (ID)
);


--
-- Table structure for table T_BUS_CLIENTE
--

DROP TABLE IF EXISTS T_BUS_CLIENTE;
CREATE TABLE T_BUS_CLIENTE (
  CLIENTE_N_CODIGO BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  USUARIO_N_CODIGO BIGINT NOT NULL,
  CLIENTE_V_NOMBRE varchar(60) DEFAULT NULL,
  CLIENTE_V_APELLIDO varchar(60) DEFAULT NULL,
  CLIENTE_D_FECHANAC date DEFAULT NULL,
  CLIENTE_N_DNI BIGINT DEFAULT NULL,
  CLIENTE_V_DIRECCION varchar(80) DEFAULT NULL,
  CLIENTE_V_TELEFONO varchar(10) DEFAULT NULL,
  CLIENTE_V_CELULAR varchar(10) DEFAULT NULL,
  CLIENTE_V_EMAIL varchar(30) DEFAULT NULL,
  CLIENTE_V_CIUDAD varchar(20) DEFAULT NULL,
  CLIENTE_V_REGION varchar(20) DEFAULT NULL,
  CLIENTE_V_CODPOSTAL BIGINT DEFAULT NULL,
  CLIENTE_N_ACTIVO INTEGER DEFAULT NULL,
  PRIMARY KEY (CLIENTE_N_CODIGO),
  CONSTRAINT FK_REL_CLIENTE_USUARIO FOREIGN KEY (USUARIO_N_CODIGO) REFERENCES T_SEG_USUARIO (USUARIO_N_CODIGO),
  CONSTRAINT FK_REL_COMPANY_CLIENTE FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID)
);

--
-- Table structure for table T_BUS_PEDIDO_TIPO
--

DROP TABLE IF EXISTS T_BUS_PEDIDO_TIPO;
CREATE TABLE T_BUS_PEDIDO_TIPO (
  TIPOPED_N_CODIGO BIGINT NOT NULL,
  TIPOPED_V_DESCRIPCION varchar(50) DEFAULT NULL,
  PRIMARY KEY (TIPOPED_N_CODIGO)
);

--
-- Table structure for table T_BUS_PEDIDO
--

DROP TABLE IF EXISTS T_BUS_PEDIDO;
CREATE TABLE T_BUS_PEDIDO (
  PEDIDO_N_CODIGO BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  CLIENTE_N_CODIGO BIGINT NOT NULL,
  TIPOPED_N_CODIGO BIGINT NOT NULL,
  PEDIDO_D_FECHA date DEFAULT NULL,
  PEDIDO_N_IGV BIGINT DEFAULT NULL,
  PEDIDO_N_TOTAL DECIMAL(8,2) DEFAULT NULL,
  PRIMARY KEY (PEDIDO_N_CODIGO),
  CONSTRAINT FK_REL_COMPANY_PEDIDO FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID),
  CONSTRAINT FK_REL_PEDIDO_CLIENTE FOREIGN KEY (CLIENTE_N_CODIGO) REFERENCES T_BUS_CLIENTE (CLIENTE_N_CODIGO),
  CONSTRAINT FK_REL_PEDIDO_TIPO FOREIGN KEY (TIPOPED_N_CODIGO) REFERENCES T_BUS_PEDIDO_TIPO (TIPOPED_N_CODIGO)
);


--
-- Table structure for table T_BUS_PEDIDO2PEDIDO
--

DROP TABLE IF EXISTS T_BUS_PEDIDO2PEDIDO;
CREATE TABLE T_BUS_PEDIDO2PEDIDO (
  PEDIDO_N_CODIGO_FROM BIGINT NOT NULL,
  PEDIDO_N_CODIGO_TO BIGINT NOT NULL,
  PRIMARY KEY (PEDIDO_N_CODIGO_FROM,PEDIDO_N_CODIGO_TO)
);


--
-- Table structure for table T_BUS_PEDIDO_DET
--

DROP TABLE IF EXISTS T_BUS_PEDIDO_DET;
CREATE TABLE T_BUS_PEDIDO_DET (
  PEDIDO_N_CODIGO BIGINT NOT NULL,
  ARTICULO_N_CODIGO BIGINT NOT NULL,
  DETPED_N_CANTIDAD BIGINT DEFAULT NULL,
  PRIMARY KEY (PEDIDO_N_CODIGO,ARTICULO_N_CODIGO),
  CONSTRAINT FK_T_BUS_PEDIDO_DET FOREIGN KEY (PEDIDO_N_CODIGO) REFERENCES T_BUS_PEDIDO (PEDIDO_N_CODIGO),
  CONSTRAINT FK_T_BUS_PEDIDO_DET2 FOREIGN KEY (ARTICULO_N_CODIGO) REFERENCES T_BUS_PRODUCT (ID)
);


--
-- Table structure for table T_BUS_SOLICITUD
--

DROP TABLE IF EXISTS T_BUS_SOLICITUD;
CREATE TABLE T_BUS_SOLICITUD (
  SOLICITUD_N_CODIGO BIGINT NOT NULL,
  SOLICITUD_V_RAZON varchar(100) DEFAULT NULL,
  SOLICITUD_N_RUC varchar(11) DEFAULT NULL,
  SOLICITUD_D_FECHA date DEFAULT NULL,
  SOLICITUD_N_ESTADO INTEGER DEFAULT NULL,
  SOLICITUD_V_EMAIL varchar(50) DEFAULT NULL,
  PRIMARY KEY (SOLICITUD_N_CODIGO)
);


--
-- Table structure for table T_BUS_VENTA_TIPO
--

DROP TABLE IF EXISTS T_BUS_VENTA_TIPO;
CREATE TABLE T_BUS_VENTA_TIPO (
  TIPOVEN_N_CODIGO BIGINT NOT NULL,
  COMPANYID BIGINT NOT NULL,
  TIPOVEN_V_DESCRIPCION varchar(50) DEFAULT NULL,
  PRIMARY KEY (TIPOVEN_N_CODIGO),
  CONSTRAINT FK_REL_COMPANY_TIPOVENTA FOREIGN KEY (COMPANYID) REFERENCES T_COM_COMPANY (ID)
);



-- Dump completed on 2011-01-06  5:11:20
