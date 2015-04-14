--
-- Table structure for table T_EMP_EMPRESA
--

CREATE TABLE T_EMP_EMPRESA (
  EMPRESA_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESA_V_RAZONSOCIAL VARCHAR(200) DEFAULT NULL,
  EMPRESA_N_ACTIVO INTEGER DEFAULT NULL,
  EMPRESA_V_RUC VARCHAR(11) DEFAULT NULL,
  EMPRESA_V_DOMINIO VARCHAR(10) DEFAULT NULL,
  PRIMARY KEY (EMPRESA_N_CODIGO)
);


CREATE UNIQUE INDEX EMPRESA_V_DOMINIO_UNIQUE ON T_EMP_EMPRESA(EMPRESA_V_DOMINIO);

--
-- Table structure for table T_EMP_AREA
--

CREATE TABLE T_EMP_AREA (
  AREA_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESA_N_CODIGO INTEGER NOT NULL,
  AREA_V_NOMBRE VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (AREA_N_CODIGO),
  CONSTRAINT FK_REL_EMPRESA_AREA FOREIGN KEY (EMPRESA_N_CODIGO) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO)
);


--
-- Table structure for table T_SEG_PERFIL
--

CREATE TABLE T_SEG_PERFIL (
  PERFIL_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESA_N_CODIGO INTEGER NOT NULL,
  AREA_N_CODIGO INTEGER NOT NULL,
  PERFIL_V_FUNCION VARCHAR(50) DEFAULT NULL,
  PERFIL_V_DESCRIPCION VARCHAR(300) DEFAULT NULL,
  PRIMARY KEY (PERFIL_N_CODIGO),
  CONSTRAINT FK_REL_EMPRESA_PERFIL FOREIGN KEY (EMPRESA_N_CODIGO) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO),
  CONSTRAINT FK_REL_PERFIL_AREA FOREIGN KEY (AREA_N_CODIGO) REFERENCES T_EMP_AREA (AREA_N_CODIGO)
);


--
-- Table structure for table T_SEG_USUARIO
--

CREATE TABLE T_SEG_USUARIO (
  USUARIO_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESA_N_CODIGO INTEGER NOT NULL,
  USUARIO_V_CONTRASENA VARCHAR(150) DEFAULT NULL,
  USUARIO_V_USERNAME VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (USUARIO_N_CODIGO),
  CONSTRAINT FK_REL_EMPRESA_USUARIO FOREIGN KEY (EMPRESA_N_CODIGO) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO)
);


--
-- Table structure for table T_SEG_MODULO
--

CREATE TABLE T_SEG_MODULO (
  MODULO_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESA_N_CODIGO INTEGER NOT NULL,
  MODULO_V_DESC VARCHAR(50) DEFAULT NULL,
  MODULO_V_RECURSO VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (MODULO_N_CODIGO),
  CONSTRAINT FK_REL_EMPRESA_MODULO FOREIGN KEY (EMPRESA_N_CODIGO) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO)
);


--
-- Table structure for table T_SEG_MOD_DET
--

CREATE TABLE T_SEG_MOD_DET (
  PERFIL_N_CODIGO INTEGER NOT NULL,
  MODULO_N_CODIGO INTEGER NOT NULL,
  MODDET_N_HABILITADO INTEGER DEFAULT NULL,
  PRIMARY KEY (PERFIL_N_CODIGO,MODULO_N_CODIGO),
  CONSTRAINT FK_T_SEG_MOD_DET FOREIGN KEY (PERFIL_N_CODIGO) REFERENCES T_SEG_PERFIL (PERFIL_N_CODIGO),
  CONSTRAINT FK_T_SEG_MOD_DET_1 FOREIGN KEY (MODULO_N_CODIGO) REFERENCES T_SEG_MODULO (MODULO_N_CODIGO) ON DELETE NO ACTION ON UPDATE NO ACTION
);


--
-- Table structure for table T_EMP_EMPLEADO
--

CREATE TABLE T_EMP_EMPLEADO (
  EMPLEADO_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  PERFIL_N_CODIGO INTEGER NOT NULL,
  USUARIO_N_CODIGO INTEGER NOT NULL,
  EMPRESA_N_CODIGO INTEGER NOT NULL,
  EMPLEADO_V_NOMBRE VARCHAR(50) DEFAULT NULL,
  EMPLEADO_V_APELLIDO VARCHAR(50) DEFAULT NULL,
  EMPLEADO_V_DNI VARCHAR(15) DEFAULT NULL,
  EMPLEADO_V_DIRECCION VARCHAR(70) DEFAULT NULL,
  EMPLEADO_V_TELEFONO VARCHAR(15) DEFAULT NULL,
  EMPLEADO_V_CELULAR VARCHAR(20) DEFAULT NULL,
  EMPLEADO_V_EMAIL VARCHAR(30) DEFAULT NULL,
  EMPLEADO_V_CIUDAD VARCHAR(20) DEFAULT NULL,
  EMPLEADO_V_REGION VARCHAR(20) DEFAULT NULL,
  EMPLEADO_D_FECHNAC date DEFAULT NULL,
  EMPLEADO_N_ACTIVO INTEGER DEFAULT NULL,
  PRIMARY KEY (EMPLEADO_N_CODIGO),
  CONSTRAINT FK_REL_EMPLEADO_PERFIL FOREIGN KEY (PERFIL_N_CODIGO) REFERENCES T_SEG_PERFIL (PERFIL_N_CODIGO),
  CONSTRAINT FK_REL_EMPLEADO_USUARIO FOREIGN KEY (USUARIO_N_CODIGO) REFERENCES T_SEG_USUARIO (USUARIO_N_CODIGO),
  CONSTRAINT FK_REL_EMPRESA_EMPLEADO FOREIGN KEY (EMPRESA_N_CODIGO) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO)
) ;


--
-- Table structure for table T_NEG_CATEGORIA
--

CREATE TABLE T_NEG_CATEGORIA (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESAID INTEGER NOT NULL,
  NOMBRE VARCHAR(60) DEFAULT NULL,
  ESTADO BOOLEAN DEFAULT TRUE NOT NULL,
  PARENTID INTEGER,
  IMAGEN VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_CATEGORIA_EMPRESA FOREIGN KEY (EMPRESAID) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO),
  CONSTRAINT FK_REL_CATEGORIA_CATEGORIA FOREIGN KEY (PARENTID) REFERENCES T_NEG_CATEGORIA (ID)
);

CREATE TABLE T_NEG_ATRIBUTO (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESAID INTEGER NOT NULL,
  NOMBRE VARCHAR(20) NOT NULL,
  DESCR VARCHAR(80),
  ACTIVO INTEGER DEFAULT 1,
  FILTRABLE INTEGER DEFAULT 0,
  --PREDET INTEGER DEFAULT NULL,    --Para el precio por ejemplo, que debe existir si o si.
  CATEGORYID INTEGER,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_ATRIBUTO_CATEGORIA FOREIGN KEY (CATEGORYID) REFERENCES T_NEG_CATEGORIA (ID)
);

--
-- Table structure for table T_NEG_MARCA
--

CREATE TABLE T_NEG_MARCA (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESAID INTEGER NOT NULL,
  NOMBRE VARCHAR(70) NOT NULL,
  ACTIVO BOOLEAN DEFAULT TRUE NOT NULL,
  IMAGEN VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_MARCA_EMPRESA FOREIGN KEY (EMPRESAID) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO)
);

CREATE TABLE T_NEG_ARTICULO (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESAID INTEGER NOT NULL,
  NOMBRE VARCHAR(70) DEFAULT NULL,
  MARCAID INTEGER NOT NULL,
  DESCRIPCION VARCHAR(4000) DEFAULT NULL,
  --ARTICULO_N_PRECIO DOUBLE PRECISION DEFAULT NULL,
  --ARTICULO_N_STOCK INTEGER DEFAULT 0,
  ACTIVO BOOLEAN DEFAULT TRUE NOT NULL,
  IMAGEN VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_ARTICULO_EMPRESA FOREIGN KEY (EMPRESAID) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO),
  CONSTRAINT FK_REL_ARTICULO_MARCA FOREIGN KEY (MARCAID) REFERENCES T_NEG_MARCA (ID)
);

CREATE TABLE T_NEG_IMAGE4PRODUCT (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESAID INTEGER NOT NULL,
  NOMBRE VARCHAR(70) NOT NULL,
  PRODUCTID INTEGER NOT NULL,
  IMAGDEFAULT BOOLEAN DEFAULT FALSE NOT NULL,
  ACTIVE BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_IMAGE4PRODUCT_EMPRESA FOREIGN KEY (EMPRESAID) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO),
  CONSTRAINT FK_REL_IMAGE4PRODUCT_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_NEG_ARTICULO (ID)
);

CREATE TABLE T_NEG_ARTIC2ATRIB (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESAID INTEGER NOT NULL,
  PRODUCTID INTEGER NOT NULL,
  ATTRIBUTEID INTEGER NOT NULL,
  NOMBRE VARCHAR(20) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_DESDE_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_NEG_ARTICULO (ID),
  CONSTRAINT FK_REL_HASTA_ATRIBUTO FOREIGN KEY (ATTRIBUTEID) REFERENCES T_NEG_ATRIBUTO (ID)
);

CREATE TABLE T_NEG_INVENTARIO (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESAID INTEGER NOT NULL,
  PRODUCTID INTEGER NOT NULL,
  STOCK DOUBLE,
  RESERVA DOUBLE,
  FECHA DATE,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_INVENTARIO_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_NEG_ARTICULO (ID)
);

CREATE TABLE T_NEG_PRECIO (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  PRODUCTID INTEGER NOT NULL,
  DESDE DATE,
  HASTA DATE,
  MONTO DOUBLE,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_PRECIO_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_NEG_ARTICULO (ID)
);

CREATE TABLE T_NEG_COSTO (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  PRODUCTID INTEGER NOT NULL,
  DESDE DATE,
  HASTA DATE,
  MONTO DOUBLE,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_COSTO_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_NEG_ARTICULO (ID)
);

CREATE TABLE T_NEG_OFERTA (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  PRODUCTID INTEGER NOT NULL,
  DESDE DATE,
  HASTA DATE,
  MONTO DOUBLE,
  PRIMARY KEY (ID),
  CONSTRAINT FK_REL_OFERTA_ARTICULO FOREIGN KEY (PRODUCTID) REFERENCES T_NEG_ARTICULO (ID)
);

CREATE TABLE T_NEG_CATEG2ARTIC (
  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  CATEGORYID INTEGER NOT NULL,
  PRODUCTID INTEGER NOT NULL,
  CONSTRAINT FK_REL_CATEGORIA_FROM FOREIGN KEY (CATEGORYID) REFERENCES T_NEG_CATEGORIA (ID),
  CONSTRAINT FK_REL_ARTICULO_TO FOREIGN KEY (PRODUCTID) REFERENCES T_NEG_ARTICULO (ID)
);

--
-- Table structure for table T_NEG_CLIENTE
--

CREATE TABLE T_NEG_CLIENTE (
  CLIENTE_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESA_N_CODIGO INTEGER NOT NULL,
  USUARIO_N_CODIGO INTEGER NOT NULL,
  CLIENTE_V_NOMBRE VARCHAR(60) DEFAULT NULL,
  CLIENTE_V_APELLIDO VARCHAR(60) DEFAULT NULL,
  CLIENTE_D_FECHANAC date DEFAULT NULL,
  CLIENTE_N_DNI INTEGER DEFAULT NULL,
  CLIENTE_V_DIRECCION VARCHAR(80) DEFAULT NULL,
  CLIENTE_V_TELEFONO VARCHAR(10) DEFAULT NULL,
  CLIENTE_V_CELULAR VARCHAR(10) DEFAULT NULL,
  CLIENTE_V_EMAIL VARCHAR(30) DEFAULT NULL,
  CLIENTE_V_CIUDAD VARCHAR(20) DEFAULT NULL,
  CLIENTE_V_REGION VARCHAR(20) DEFAULT NULL,
  CLIENTE_V_CODPOSTAL INTEGER DEFAULT NULL,
  CLIENTE_N_ACTIVO INTEGER DEFAULT NULL,
  PRIMARY KEY (CLIENTE_N_CODIGO),
  CONSTRAINT FK_REL_CLIENTE_USUARIO FOREIGN KEY (USUARIO_N_CODIGO) REFERENCES T_SEG_USUARIO (USUARIO_N_CODIGO),
  CONSTRAINT FK_REL_EMPRESA_CLIENTE FOREIGN KEY (EMPRESA_N_CODIGO) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO)
);

--
-- Table structure for table T_NEG_PEDIDO_TIPO
--

CREATE TABLE T_NEG_PEDIDO_TIPO (
  TIPOPED_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  TIPOPED_V_DESCRIPCION VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (TIPOPED_N_CODIGO)
);

--
-- Table structure for table T_NEG_PEDIDO
--

CREATE TABLE T_NEG_PEDIDO (
  PEDIDO_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESA_N_CODIGO INTEGER NOT NULL,
  CLIENTE_N_CODIGO INTEGER NOT NULL,
  TIPOPED_N_CODIGO INTEGER NOT NULL,
  PEDIDO_D_FECHA date DEFAULT NULL,
  PEDIDO_N_IGV INTEGER DEFAULT NULL,
  PEDIDO_N_TOTAL DOUBLE PRECISION DEFAULT NULL,
  PRIMARY KEY (PEDIDO_N_CODIGO),
  CONSTRAINT FK_REL_EMPRESA_PEDIDO FOREIGN KEY (EMPRESA_N_CODIGO) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO),
  CONSTRAINT FK_REL_PEDIDO_CLIENTE FOREIGN KEY (CLIENTE_N_CODIGO) REFERENCES T_NEG_CLIENTE (CLIENTE_N_CODIGO),
  CONSTRAINT FK_REL_PEDIDO_TIPO FOREIGN KEY (TIPOPED_N_CODIGO) REFERENCES T_NEG_PEDIDO_TIPO (TIPOPED_N_CODIGO)
);


--
-- Table structure for table T_NEG_PEDIDO2PEDIDO
--

CREATE TABLE T_NEG_PEDIDO2PEDIDO (
  PEDIDO_N_CODIGO_FROM INTEGER NOT NULL,
  PEDIDO_N_CODIGO_TO INTEGER NOT NULL,
  PRIMARY KEY (PEDIDO_N_CODIGO_FROM,PEDIDO_N_CODIGO_TO)
);


--
-- Table structure for table T_NEG_PEDIDO_DET
--

CREATE TABLE T_NEG_PEDIDO_DET (
  PEDIDO_N_CODIGO INTEGER NOT NULL,
  ARTICULO_N_CODIGO INTEGER NOT NULL,
  DETPED_N_CANTIDAD INTEGER DEFAULT NULL,
  PRIMARY KEY (PEDIDO_N_CODIGO,ARTICULO_N_CODIGO),
  CONSTRAINT FK_T_NEG_PEDIDO_DET FOREIGN KEY (PEDIDO_N_CODIGO) REFERENCES T_NEG_PEDIDO (PEDIDO_N_CODIGO),
  CONSTRAINT FK_T_NEG_PEDIDO_DET2 FOREIGN KEY (ARTICULO_N_CODIGO) REFERENCES T_NEG_ARTICULO (ID)
);


--
-- Table structure for table T_NEG_SOLICITUD
--

CREATE TABLE T_NEG_SOLICITUD (
  SOLICITUD_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  SOLICITUD_V_RAZON VARCHAR(100) DEFAULT NULL,
  SOLICITUD_N_RUC VARCHAR(11) DEFAULT NULL,
  SOLICITUD_D_FECHA date DEFAULT NULL,
  SOLICITUD_N_ESTADO INTEGER DEFAULT NULL,
  SOLICITUD_V_EMAIL VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (SOLICITUD_N_CODIGO)
);


--
-- Table structure for table T_NEG_VENTA_TIPO
--

CREATE TABLE T_NEG_VENTA_TIPO (
  TIPOVEN_N_CODIGO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  EMPRESA_N_CODIGO INTEGER NOT NULL,
  TIPOVEN_V_DESCRIPCION VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (TIPOVEN_N_CODIGO),
  CONSTRAINT FK_REL_EMPRESA_TIPOVENTA FOREIGN KEY (EMPRESA_N_CODIGO) REFERENCES T_EMP_EMPRESA (EMPRESA_N_CODIGO)
);
