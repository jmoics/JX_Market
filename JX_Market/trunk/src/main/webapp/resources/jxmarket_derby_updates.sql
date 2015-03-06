--
-- Dumping data for table T_EMP_AREA
--

INSERT INTO T_EMP_EMPRESA (EMPRESA_V_RAZONSOCIAL, EMPRESA_N_ACTIVO, EMPRESA_V_RUC, EMPRESA_V_DOMINIO) 
	VALUES ('JX_Market',1,'44563458976','jxmarket'),('Metro',1,'35346576899','metro'),('moxter.net',1,'56452345346','moxternet');

INSERT INTO T_EMP_AREA (EMPRESA_N_CODIGO, AREA_V_NOMBRE) VALUES (2,'Gerenciaa'),(2,'Ventaas'),(2,'Productos'),(2,'Recursos Humanos'),(1,'Administration');

INSERT INTO T_SEG_PERFIL (EMPRESA_N_CODIGO, AREA_N_CODIGO, PERFIL_V_FUNCION, PERFIL_V_DESCRIPCION) VALUES (2,1,'Gerente','Administracion Generaal'),
				(2,2,'Vendedor','Encargador de realizar las ventas'),
				(2,3,'Almacenero','Encargado de la logistica'),
				(2,4,'Gerente','Encargado del area'),
				(2,1,'Subgerente','Subgerencia general');

INSERT INTO T_SEG_USUARIO (EMPRESA_N_CODIGO, USUARIO_V_CONTRASENA, USUARIO_V_USERNAME) VALUES (1,'62a90ccff3fd73694bf6281bb234b09a',NULL),
				 (2,'e10adc3949ba59abbe56e057f20f883e','jmoics'),
				 (3,'e10adc3949ba59abbe56e057f20f883e','jcueva'),
				 (2,'e10adc3949ba59abbe56e057f20f883e','jcueva'),
				 (2,'e10adc3949ba59abbe56e057f20f883e','aencalada'),
				 (1,'e10adc3949ba59abbe56e057f20f883e','jmoics@gmail.com'),
				 (1,'e10adc3949ba59abbe56e057f20f883e','jacky@gmail.com');

INSERT INTO T_SEG_MODULO (EMPRESA_N_CODIGO, MODULO_V_DESC, MODULO_V_RECURSO) VALUES (2,'Modulo para administracion de empleados','MODULO_ADM_EMPLEADO'),
				(2,'Modulo para productos','MODULO_PRODUCTOS'),
				(2,'Modulo para ingreso de productos','MODULO_PROD_INGRESO'),
				(2,'Modulo para consulta de productos','MODULO_PROD_CONSULTA'),
				(2,'Modulo para edicion de productos','MODULO_PROD_EDICION'),
				(2,'Modulo para administracion','MODULO_ADMINISTRACION'),
				(2,'Modulo para administracion de perfiles','MODULO_ADM_PERFIL'),
				(2,'modulo para administracion de areas','MODULO_ADM_AREA'),
				(2,'Modulo para administracion de modulos','MODULO_ADM_MODULO'),
				(2,'Modulo para asignacion de modulos a perfiles','MODULO_ADM_PERFILMODULO'),
				(2,'Modulo para administrar la contrasena','MODULO_CHANGE_PASS');

INSERT INTO T_SEG_MOD_DET (PERFIL_N_CODIGO, MODULO_N_CODIGO, MODDET_N_HABILITADO) VALUES (1,1,NULL),(1,2,NULL),(1,3,NULL),(1,4,NULL),(1,5,NULL),(1,6,NULL),(1,7,NULL),(1,8,NULL),(1,9,NULL),(1,10,NULL),(1,11,NULL),
				(2,2,NULL),(2,4,NULL),(3,2,NULL),(3,3,NULL),(3,4,NULL),(3,5,NULL),(3,11,NULL),(4,1,NULL),(4,6,NULL),(4,7,NULL),(4,11,NULL),
				(5,2,NULL),(5,4,NULL),(5,5,NULL),(5,6,NULL),(5,7,NULL),(5,10,NULL),(5,11,NULL);

INSERT INTO T_EMP_EMPLEADO (PERFIL_N_CODIGO, USUARIO_N_CODIGO, EMPRESA_N_CODIGO, EMPLEADO_V_NOMBRE, EMPLEADO_V_APELLIDO,  EMPLEADO_V_DNI, EMPLEADO_V_DIRECCION, EMPLEADO_V_TELEFONO, EMPLEADO_V_CELULAR, EMPLEADO_V_EMAIL, EMPLEADO_V_CIUDAD, EMPLEADO_V_REGION, EMPLEADO_D_FECHNAC, EMPLEADO_N_ACTIVO) 
           VALUES (1,1,2,'Jorge','Cueva','44128463','Mi casa','4823262','945190242','jmoics@gmail.com','Lima','Lima','1987-12-01',1),
				  (3,2,2,'jorge','cueva samames','44128463','mi house','4563423','987755443','','lima','lima',NULL,1);

INSERT INTO T_NEG_CATEGORIA (EMPRESA_N_CODIGO, CATEGORIA_V_NOMBRE) 
           VALUES (1,'Juegos'),(1,'Libros'),(1,'Consola');

INSERT INTO T_NEG_ARTICULO (EMPRESA_N_CODIGO, CATEGORIA_N_CODIGO, ARTICULO_V_NOMBRE, ARTICULO_V_MARCA, ARTICULO_V_DESCRIPCION, ARTICULO_N_PRECIO, ARTICULO_N_STOCK, ARTICULO_N_ACTIVO, ARTICULO_V_IMAGEN) VALUES (2,1,'Ring Action','Capcom','juego de video de accion basado en la trilogia del senior de los anillos',24.50,20,1,NULL),
				  (2,1,'Final Fantasy X','Sony','Juego de ROL, para consola Play Station 2',25.90,NULL,1,NULL),
				  (2,3,'PS2','Sony','play station 2 color azul con un mando y una memory card',550.00,NULL,1,'2.1.PS2.22589'),
				  (2,3,'PS2','Sony','play station 2 azul y negro mas un mando',540.00,NULL,1,'2.1.PS2.938193'),
				  (3,1,'Final fantasy x-2','Eydos','Juego rpg final fantasy x-2 original en caja',35.00,NULL,1,'3.1.Final fantasy x-2.950467'),
				  (3,1,'Final Fantasy XII','Eydos','Juego Final Fantasy XII original algo rayadito XD',40.00,NULL,1,'3.1.Final Fantasy XII.180339'),
				  (3,3,'PS2','Sony','consola ps2',500.00,NULL,1,'3.3.PS2.918564');

INSERT INTO T_NEG_CLIENTE (EMPRESA_N_CODIGO, USUARIO_N_CODIGO, CLIENTE_V_NOMBRE, CLIENTE_V_APELLIDO, CLIENTE_D_FECHANAC, CLIENTE_N_DNI, CLIENTE_V_DIRECCION, CLIENTE_V_TELEFONO, CLIENTE_V_CELULAR, CLIENTE_V_EMAIL, CLIENTE_V_CIUDAD, CLIENTE_V_REGION, CLIENTE_V_CODPOSTAL, CLIENTE_N_ACTIVO) VALUES (1,2,'Jorge','Cueva Samames','1987-02-12',45645645,'mi casa','4564564','987987987','jmoics@gmail.com','Lima',NULL,NULL,NULL),
				 (1,7,'Jackeline','Rios Ramos','1989-08-20',45645645,'su casa','4564564','987987987','jacky@gmail.com','Lima','Lima',NULL,1);

INSERT INTO T_NEG_PEDIDO_TIPO (TIPOPED_V_DESCRIPCION) VALUES ('Entrega a domicilio'),('Recoger el producto');

INSERT INTO T_NEG_PEDIDO (EMPRESA_N_CODIGO,CLIENTE_N_CODIGO,TIPOPED_N_CODIGO,PEDIDO_D_FECHA,PEDIDO_N_IGV,PEDIDO_N_TOTAL) VALUES (1,1,1,'2011-01-05',0,6300.00),(2,1,1,'2011-01-05',0,4900.00),(3,1,1,'2011-01-05',0,1400.00);

INSERT INTO T_NEG_PEDIDO2PEDIDO VALUES (1,2),(1,3);

INSERT INTO T_NEG_PEDIDO_DET VALUES (2,4,4),(2,5,5),(3,7,35);

INSERT INTO T_NEG_SOLICITUD (SOLICITUD_V_RAZON,SOLICITUD_N_RUC,SOLICITUD_D_FECHA,SOLICITUD_N_ESTADO,SOLICITUD_V_EMAIL) VALUES ('Bioquimica','96495623526','2011-01-06',4,'io@gmail.com');