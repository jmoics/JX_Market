-- MySQL dump 10.10
--
-- Host: localhost    Database: jxmarket
-- ------------------------------------------------------
-- Server version	5.0.16-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_emp_area`
--

DROP TABLE IF EXISTS `t_emp_area`;
CREATE TABLE `t_emp_area` (
  `AREA_N_CODIGO` int(11) NOT NULL auto_increment,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `AREA_V_NOMBRE` varchar(50) default NULL,
  PRIMARY KEY  (`AREA_N_CODIGO`),
  KEY `FK_REL_EMPRESA_AREA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_AREA` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_emp_area`
--


/*!40000 ALTER TABLE `t_emp_area` DISABLE KEYS */;
LOCK TABLES `t_emp_area` WRITE;
INSERT INTO `t_emp_area` VALUES (1,2,'Gerenciaa'),(2,2,'Ventaas'),(3,2,'Productos'),(4,2,'Recursos Humanos');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_emp_area` ENABLE KEYS */;

--
-- Table structure for table `t_emp_empleado`
--

DROP TABLE IF EXISTS `t_emp_empleado`;
CREATE TABLE `t_emp_empleado` (
  `EMPLEADO_N_CODIGO` int(11) NOT NULL auto_increment,
  `PERFIL_N_CODIGO` int(11) NOT NULL,
  `USUARIO_N_CODIGO` int(11) NOT NULL,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `EMPLEADO_V_NOMBRE` varchar(50) default NULL,
  `EMPLEADO_V_APELLIDO` varchar(50) default NULL,
  `EMPLEADO_V_DNI` varchar(15) default NULL,
  `EMPLEADO_V_DIRECCION` varchar(70) default NULL,
  `EMPLEADO_V_TELEFONO` varchar(15) default NULL,
  `EMPLEADO_V_CELULAR` varchar(20) default NULL,
  `EMPLEADO_V_EMAIL` varchar(30) default NULL,
  `EMPLEADO_V_CIUDAD` varchar(20) default NULL,
  `EMPLEADO_V_REGION` varchar(20) default NULL,
  `EMPLEADO_D_FECHNAC` date default NULL,
  `EMPLEADO_N_ACTIVO` int(11) default NULL,
  PRIMARY KEY  (`EMPLEADO_N_CODIGO`),
  KEY `FK_REL_EMPLEADO_PERFIL` (`PERFIL_N_CODIGO`),
  KEY `FK_REL_EMPLEADO_USUARIO` (`USUARIO_N_CODIGO`),
  KEY `FK_REL_EMPRESA_EMPLEADO` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPLEADO_PERFIL` FOREIGN KEY (`PERFIL_N_CODIGO`) REFERENCES `t_seg_perfil` (`PERFIL_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPLEADO_USUARIO` FOREIGN KEY (`USUARIO_N_CODIGO`) REFERENCES `t_seg_usuario` (`USUARIO_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_EMPLEADO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_emp_empleado`
--


/*!40000 ALTER TABLE `t_emp_empleado` DISABLE KEYS */;
LOCK TABLES `t_emp_empleado` WRITE;
INSERT INTO `t_emp_empleado` VALUES (1,1,1,2,'Jorge','Cueva','44128463','Mi casa','4823262','945190242','jmoics@gmail.com','Lima','Lima','1987-12-01',1),(2,3,16,2,'jorge','cueva samames','44128463','mi house','4563423','987755443','','lima','lima',NULL,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_emp_empleado` ENABLE KEYS */;

--
-- Table structure for table `t_emp_empresa`
--

DROP TABLE IF EXISTS `t_emp_empresa`;
CREATE TABLE `t_emp_empresa` (
  `EMPRESA_N_CODIGO` int(11) NOT NULL auto_increment,
  `EMPRESA_V_RAZONSOCIAL` varchar(200) default NULL,
  `EMPRESA_N_ACTIVO` int(11) default NULL,
  `EMPRESA_V_RUC` varchar(11) default NULL,
  `EMPRESA_V_DOMINIO` varchar(10) default NULL,
  PRIMARY KEY  (`EMPRESA_N_CODIGO`),
  UNIQUE KEY `EMPRESA_V_DOMINIO_UNIQUE` (`EMPRESA_V_DOMINIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_emp_empresa`
--


/*!40000 ALTER TABLE `t_emp_empresa` DISABLE KEYS */;
LOCK TABLES `t_emp_empresa` WRITE;
INSERT INTO `t_emp_empresa` VALUES (1,'JX_Market',1,'44563458976','jxmarket'),(2,'Metro',1,'35346576899','metro'),(3,'moxter.net',1,'56452345346','moxternet');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_emp_empresa` ENABLE KEYS */;

--
-- Table structure for table `t_neg_articulo`
--

DROP TABLE IF EXISTS `t_neg_articulo`;
CREATE TABLE `t_neg_articulo` (
  `ARTICULO_N_CODIGO` int(11) NOT NULL auto_increment,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `CATEGORIA_N_CODIGO` int(11) NOT NULL,
  `ARTICULO_V_NOMBRE` varchar(70) default NULL,
  `ARTICULO_V_MARCA` varchar(30) default NULL,
  `ARTICULO_V_DESCRIPCION` varchar(4000) default NULL,
  `ARTICULO_N_PRECIO` float(8,2) default NULL,
  `ARTICULO_N_STOCK` int(11) default '0',
  `ARTICULO_N_ACTIVO` int(11) default NULL,
  `ARTICULO_V_IMAGEN` varchar(45) default NULL,
  PRIMARY KEY  (`ARTICULO_N_CODIGO`),
  KEY `FK_REL_ARTICULO_TIPO` (`CATEGORIA_N_CODIGO`),
  KEY `FK_REL_EMPRESA_ARTICULO` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_ARTICULO_TIPO` FOREIGN KEY (`CATEGORIA_N_CODIGO`) REFERENCES `t_neg_categoria` (`CATEGORIA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_ARTICULO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_neg_articulo`
--


/*!40000 ALTER TABLE `t_neg_articulo` DISABLE KEYS */;
LOCK TABLES `t_neg_articulo` WRITE;
INSERT INTO `t_neg_articulo` VALUES (1,2,1,'Ring Action','Capcom','juego de video de accion basado en la trilogia del senior de los anillos',24.50,20,1,NULL),(2,2,1,'Final Fantasy X','Sony','Juego de ROL, para consola Play Station 2',25.90,NULL,1,NULL),(11,2,3,'PS2','Sony','play station 2 color azul con un mando y una memory card',550.00,NULL,1,'2.1.PS2.22589'),(12,2,3,'PS2','Sony','play station 2 azul y negro mas un mando',540.00,NULL,1,'2.1.PS2.938193'),(13,3,1,'Final fantasy x-2','Eydos','Juego rpg final fantasy x-2 original en caja',35.00,NULL,1,'3.1.Final fantasy x-2.950467'),(14,3,1,'Final Fantasy XII','Eydos','Juego Final Fantasy XII original algo rayadito XD',40.00,NULL,1,'3.1.Final Fantasy XII.180339'),(15,3,3,'PS2','Sony','consola ps2',500.00,NULL,1,'3.3.PS2.918564');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_neg_articulo` ENABLE KEYS */;

--
-- Table structure for table `t_neg_categoria`
--

DROP TABLE IF EXISTS `t_neg_categoria`;
CREATE TABLE `t_neg_categoria` (
  `CATEGORIA_N_CODIGO` int(11) NOT NULL auto_increment,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `CATEGORIA_V_NOMBRE` varchar(60) default NULL,
  PRIMARY KEY  (`CATEGORIA_N_CODIGO`),
  KEY `FK_REL_EMPRESA_CATEGORIA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_CATEGORIA` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_neg_categoria`
--


/*!40000 ALTER TABLE `t_neg_categoria` DISABLE KEYS */;
LOCK TABLES `t_neg_categoria` WRITE;
INSERT INTO `t_neg_categoria` VALUES (1,1,'Juegos'),(2,1,'Libros'),(3,1,'Consola');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_neg_categoria` ENABLE KEYS */;

--
-- Table structure for table `t_neg_cliente`
--

DROP TABLE IF EXISTS `t_neg_cliente`;
CREATE TABLE `t_neg_cliente` (
  `CLIENTE_N_CODIGO` int(11) NOT NULL auto_increment,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `USUARIO_N_CODIGO` int(11) NOT NULL,
  `CLIENTE_V_NOMBRE` varchar(60) default NULL,
  `CLIENTE_V_APELLIDO` varchar(60) default NULL,
  `CLIENTE_D_FECHANAC` date default NULL,
  `CLIENTE_N_DNI` int(11) default NULL,
  `CLIENTE_V_DIRECCION` varchar(80) default NULL,
  `CLIENTE_V_TELEFONO` varchar(10) default NULL,
  `CLIENTE_V_CELULAR` varchar(10) default NULL,
  `CLIENTE_V_EMAIL` varchar(30) default NULL,
  `CLIENTE_V_CIUDAD` varchar(20) default NULL,
  `CLIENTE_V_REGION` varchar(20) default NULL,
  `CLIENTE_V_CODPOSTAL` smallint(6) default NULL,
  `CLIENTE_N_ACTIVO` tinyint(1) default NULL,
  PRIMARY KEY  (`CLIENTE_N_CODIGO`),
  KEY `FK_REL_CLIENTE_USUARIO` (`USUARIO_N_CODIGO`),
  KEY `FK_REL_EMPRESA_CLIENTE` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_CLIENTE_USUARIO` FOREIGN KEY (`USUARIO_N_CODIGO`) REFERENCES `t_seg_usuario` (`USUARIO_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_CLIENTE` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_neg_cliente`
--


/*!40000 ALTER TABLE `t_neg_cliente` DISABLE KEYS */;
LOCK TABLES `t_neg_cliente` WRITE;
INSERT INTO `t_neg_cliente` VALUES (1,1,20,'Jorge','Cueva Samames','1987-02-12',45645645,'mi casa','4564564','987987987','jmoics@gmail.com','Lima',NULL,NULL,NULL),(2,1,21,'Jackeline','Rios Ramos','1989-08-20',45645645,'su casa','4564564','987987987','jacky@gmail.com','Lima','Lima',NULL,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_neg_cliente` ENABLE KEYS */;

--
-- Table structure for table `t_neg_pedido`
--

DROP TABLE IF EXISTS `t_neg_pedido`;
CREATE TABLE `t_neg_pedido` (
  `PEDIDO_N_CODIGO` int(11) NOT NULL auto_increment,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `CLIENTE_N_CODIGO` int(11) NOT NULL,
  `TIPOPED_N_CODIGO` int(11) NOT NULL,
  `PEDIDO_D_FECHA` date default NULL,
  `PEDIDO_N_IGV` int(11) default NULL,
  `PEDIDO_N_TOTAL` float(8,2) default NULL,
  PRIMARY KEY  (`PEDIDO_N_CODIGO`),
  KEY `FK_REL_EMPRESA_PEDIDO` (`EMPRESA_N_CODIGO`),
  KEY `FK_REL_PEDIDO_CLIENTE` (`CLIENTE_N_CODIGO`),
  KEY `FK_REL_PEDIDO_TIPO` (`TIPOPED_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_PEDIDO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_PEDIDO_CLIENTE` FOREIGN KEY (`CLIENTE_N_CODIGO`) REFERENCES `t_neg_cliente` (`CLIENTE_N_CODIGO`),
  CONSTRAINT `FK_REL_PEDIDO_TIPO` FOREIGN KEY (`TIPOPED_N_CODIGO`) REFERENCES `t_neg_pedido_tipo` (`TIPOPED_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_neg_pedido`
--


/*!40000 ALTER TABLE `t_neg_pedido` DISABLE KEYS */;
LOCK TABLES `t_neg_pedido` WRITE;
INSERT INTO `t_neg_pedido` VALUES (26,1,1,1,'2011-01-05',0,6300.00),(27,2,1,1,'2011-01-05',0,4900.00),(28,3,1,1,'2011-01-05',0,1400.00);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_neg_pedido` ENABLE KEYS */;

--
-- Table structure for table `t_neg_pedido2pedido`
--

DROP TABLE IF EXISTS `t_neg_pedido2pedido`;
CREATE TABLE `t_neg_pedido2pedido` (
  `PEDIDO_N_CODIGO_FROM` int(11) NOT NULL,
  `PEDIDO_N_CODIGO_TO` int(11) NOT NULL,
  PRIMARY KEY  (`PEDIDO_N_CODIGO_FROM`,`PEDIDO_N_CODIGO_TO`),
  KEY `fk_T_NEG_PEDIDO2PEDIDO_1` (`PEDIDO_N_CODIGO_FROM`),
  KEY `fk_T_NEG_PEDIDO2PEDIDO_2` (`PEDIDO_N_CODIGO_TO`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_neg_pedido2pedido`
--


/*!40000 ALTER TABLE `t_neg_pedido2pedido` DISABLE KEYS */;
LOCK TABLES `t_neg_pedido2pedido` WRITE;
INSERT INTO `t_neg_pedido2pedido` VALUES (26,27),(26,28);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_neg_pedido2pedido` ENABLE KEYS */;

--
-- Table structure for table `t_neg_pedido_det`
--

DROP TABLE IF EXISTS `t_neg_pedido_det`;
CREATE TABLE `t_neg_pedido_det` (
  `PEDIDO_N_CODIGO` int(11) NOT NULL,
  `ARTICULO_N_CODIGO` int(11) NOT NULL,
  `DETPED_N_CANTIDAD` int(11) default NULL,
  PRIMARY KEY  (`PEDIDO_N_CODIGO`,`ARTICULO_N_CODIGO`),
  KEY `FK_T_NEG_PEDIDO_DET2` (`ARTICULO_N_CODIGO`),
  CONSTRAINT `FK_T_NEG_PEDIDO_DET` FOREIGN KEY (`PEDIDO_N_CODIGO`) REFERENCES `t_neg_pedido` (`PEDIDO_N_CODIGO`),
  CONSTRAINT `FK_T_NEG_PEDIDO_DET2` FOREIGN KEY (`ARTICULO_N_CODIGO`) REFERENCES `t_neg_articulo` (`ARTICULO_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_neg_pedido_det`
--


/*!40000 ALTER TABLE `t_neg_pedido_det` DISABLE KEYS */;
LOCK TABLES `t_neg_pedido_det` WRITE;
INSERT INTO `t_neg_pedido_det` VALUES (27,11,4),(27,12,5),(28,14,35);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_neg_pedido_det` ENABLE KEYS */;

--
-- Table structure for table `t_neg_pedido_tipo`
--

DROP TABLE IF EXISTS `t_neg_pedido_tipo`;
CREATE TABLE `t_neg_pedido_tipo` (
  `TIPOPED_N_CODIGO` int(11) NOT NULL auto_increment,
  `TIPOPED_V_DESCRIPCION` varchar(50) default NULL,
  PRIMARY KEY  (`TIPOPED_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_neg_pedido_tipo`
--


/*!40000 ALTER TABLE `t_neg_pedido_tipo` DISABLE KEYS */;
LOCK TABLES `t_neg_pedido_tipo` WRITE;
INSERT INTO `t_neg_pedido_tipo` VALUES (1,'Entrega a domicilio'),(2,'Recoger el producto');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_neg_pedido_tipo` ENABLE KEYS */;

--
-- Table structure for table `t_neg_solicitud`
--

DROP TABLE IF EXISTS `t_neg_solicitud`;
CREATE TABLE `t_neg_solicitud` (
  `SOLICITUD_N_CODIGO` int(11) unsigned NOT NULL auto_increment,
  `SOLICITUD_V_RAZON` varchar(100) default NULL,
  `SOLICITUD_N_RUC` int(11) default NULL,
  `SOLICITUD_D_FECHA` date default NULL,
  `SOLICITUD_N_ESTADO` int(1) unsigned default NULL,
  `SOLICITUD_V_EMAIL` varchar(50) default NULL,
  PRIMARY KEY  USING BTREE (`SOLICITUD_N_CODIGO`),
  KEY `FK_REL_EMPRESA_VENTA` USING BTREE (`SOLICITUD_V_RAZON`),
  KEY `FK_REL_VENTA_PEDIDO` USING BTREE (`SOLICITUD_D_FECHA`),
  KEY `FK_REL_VENTA_TIPO` USING BTREE (`SOLICITUD_N_RUC`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_neg_solicitud`
--


/*!40000 ALTER TABLE `t_neg_solicitud` DISABLE KEYS */;
LOCK TABLES `t_neg_solicitud` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_neg_solicitud` ENABLE KEYS */;

--
-- Table structure for table `t_neg_venta_tipo`
--

DROP TABLE IF EXISTS `t_neg_venta_tipo`;
CREATE TABLE `t_neg_venta_tipo` (
  `TIPOVEN_N_CODIGO` int(11) NOT NULL,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `TIPOVEN_V_DESCRIPCION` varchar(50) default NULL,
  PRIMARY KEY  (`TIPOVEN_N_CODIGO`),
  KEY `FK_REL_EMPRESA_TIPOVENTA` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_TIPOVENTA` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_neg_venta_tipo`
--


/*!40000 ALTER TABLE `t_neg_venta_tipo` DISABLE KEYS */;
LOCK TABLES `t_neg_venta_tipo` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_neg_venta_tipo` ENABLE KEYS */;

--
-- Table structure for table `t_seg_mod_det`
--

DROP TABLE IF EXISTS `t_seg_mod_det`;
CREATE TABLE `t_seg_mod_det` (
  `PERFIL_N_CODIGO` int(11) NOT NULL,
  `MODULO_N_CODIGO` int(11) NOT NULL,
  `MODDET_N_HABILITADO` int(11) default NULL,
  PRIMARY KEY  (`PERFIL_N_CODIGO`,`MODULO_N_CODIGO`),
  KEY `MODULO_N_CODIGO` (`MODULO_N_CODIGO`),
  KEY `FK_T_SEG_MOD_DET_1` (`MODULO_N_CODIGO`),
  CONSTRAINT `FK_T_SEG_MOD_DET` FOREIGN KEY (`PERFIL_N_CODIGO`) REFERENCES `t_seg_perfil` (`PERFIL_N_CODIGO`),
  CONSTRAINT `FK_T_SEG_MOD_DET_1` FOREIGN KEY (`MODULO_N_CODIGO`) REFERENCES `t_seg_modulo` (`MODULO_N_CODIGO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_seg_mod_det`
--


/*!40000 ALTER TABLE `t_seg_mod_det` DISABLE KEYS */;
LOCK TABLES `t_seg_mod_det` WRITE;
INSERT INTO `t_seg_mod_det` VALUES (1,1,NULL),(1,3,NULL),(1,4,NULL),(1,5,NULL),(1,6,NULL),(1,7,NULL),(1,8,NULL),(1,9,NULL),(1,10,NULL),(1,11,NULL),(1,12,NULL),(2,3,NULL),(2,5,NULL),(3,3,NULL),(3,4,NULL),(3,5,NULL),(3,6,NULL),(3,12,NULL),(4,1,NULL),(4,7,NULL),(4,8,NULL),(4,12,NULL),(5,3,NULL),(5,5,NULL),(5,6,NULL),(5,7,NULL),(5,8,NULL),(5,11,NULL),(5,12,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_seg_mod_det` ENABLE KEYS */;

--
-- Table structure for table `t_seg_modulo`
--

DROP TABLE IF EXISTS `t_seg_modulo`;
CREATE TABLE `t_seg_modulo` (
  `MODULO_N_CODIGO` int(11) NOT NULL auto_increment,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `MODULO_V_DESC` varchar(50) default NULL,
  `MODULO_V_RECURSO` varchar(45) default NULL,
  PRIMARY KEY  (`MODULO_N_CODIGO`),
  KEY `FK_REL_EMPRESA_MODULO` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_MODULO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_seg_modulo`
--


/*!40000 ALTER TABLE `t_seg_modulo` DISABLE KEYS */;
LOCK TABLES `t_seg_modulo` WRITE;
INSERT INTO `t_seg_modulo` VALUES (1,2,'Modulo para administracion de empleados','MODULO_ADM_EMPLEADO'),(3,2,'Modulo para productos','MODULO_PRODUCTOS'),(4,2,'Modulo para ingreso de productos','MODULO_PROD_INGRESO'),(5,2,'Modulo para consulta de productos','MODULO_PROD_CONSULTA'),(6,2,'Modulo para edicion de productos','MODULO_PROD_EDICION'),(7,2,'Modulo para administracion','MODULO_ADMINISTRACION'),(8,2,'Modulo para administracion de perfiles','MODULO_ADM_PERFIL'),(9,2,'modulo para administracion de areas','MODULO_ADM_AREA'),(10,2,'Modulo para administracion de modulos','MODULO_ADM_MODULO'),(11,2,'Modulo para asignacion de modulos a perfiles','MODULO_ADM_PERFILMODULO'),(12,2,'Modulo para administrar la contrasena','MODULO_CHANGE_PASS');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_seg_modulo` ENABLE KEYS */;

--
-- Table structure for table `t_seg_perfil`
--

DROP TABLE IF EXISTS `t_seg_perfil`;
CREATE TABLE `t_seg_perfil` (
  `PERFIL_N_CODIGO` int(11) NOT NULL auto_increment,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `AREA_N_CODIGO` int(11) NOT NULL,
  `PERFIL_V_FUNCION` varchar(50) default NULL,
  `PERFIL_V_DESCRIPCION` varchar(300) default NULL,
  PRIMARY KEY  (`PERFIL_N_CODIGO`),
  KEY `FK_REL_EMPRESA_PERFIL` (`EMPRESA_N_CODIGO`),
  KEY `FK_REL_PERFIL_AREA` (`AREA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_PERFIL` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_PERFIL_AREA` FOREIGN KEY (`AREA_N_CODIGO`) REFERENCES `t_emp_area` (`AREA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_seg_perfil`
--


/*!40000 ALTER TABLE `t_seg_perfil` DISABLE KEYS */;
LOCK TABLES `t_seg_perfil` WRITE;
INSERT INTO `t_seg_perfil` VALUES (1,2,1,'Gerente','Administracion Generaal'),(2,2,2,'Vendedor','Encargador de realizar las ventas'),(3,2,3,'Almacenero','Encargado de la logistica'),(4,2,4,'Gerente','Encargado del area'),(5,2,1,'Subgerente','Subgerencia general');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_seg_perfil` ENABLE KEYS */;

--
-- Table structure for table `t_seg_usuario`
--

DROP TABLE IF EXISTS `t_seg_usuario`;
CREATE TABLE `t_seg_usuario` (
  `USUARIO_N_CODIGO` int(11) NOT NULL auto_increment,
  `EMPRESA_N_CODIGO` int(11) NOT NULL,
  `USUARIO_V_CONTRASENA` varchar(150) default NULL,
  `USUARIO_V_USERNAME` varchar(45) default NULL,
  PRIMARY KEY  (`USUARIO_N_CODIGO`),
  KEY `FK_REL_EMPRESA_USUARIO` (`EMPRESA_N_CODIGO`),
  CONSTRAINT `FK_REL_EMPRESA_USUARIO` FOREIGN KEY (`EMPRESA_N_CODIGO`) REFERENCES `t_emp_empresa` (`EMPRESA_N_CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `t_seg_usuario`
--


/*!40000 ALTER TABLE `t_seg_usuario` DISABLE KEYS */;
LOCK TABLES `t_seg_usuario` WRITE;
INSERT INTO `t_seg_usuario` VALUES (1,2,'8679b5756696bc0e69308525b298f27b','jmoics'),(2,3,'552fea927d2ca4f8736fd19f5e50eee4','jcueva'),(16,2,'7adfca2f2aba4cd301a02b9f33ca9037','jcueva'),(17,2,'aae9e370356842606fd0276c415219e2','aencalada'),(20,1,'62a90ccff3fd73694bf6281bb234b09a','jmoics@gmail.com'),(21,1,'5f4dcc3b5aa765d61d8327deb882cf99','jacky@gmail.com');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_seg_usuario` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

