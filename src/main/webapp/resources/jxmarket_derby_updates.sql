--
-- Dumping data for table T_EMP_AREA
--

INSERT INTO T_COM_COMPANY (BUSINESSNAME, ACTIVE, DOCNUMBER, DOMAIN)
    VALUES ('JX_Market',1,'44563458976','jxmarket'),('Metro',1,'35346576899','metro'),('moxter.net',1,'56452345346','moxternet');

INSERT INTO T_COM_AREA (COMPANYID, NAME) VALUES (2,'Gerenciaa'),(2,'Ventaas'),(2,'Productos'),(2,'Recursos Humanos'),(1,'Administration');

INSERT INTO T_SEG_ROLE (COMPANYID, AREAID, NAME, DESCRIPTION)
         VALUES (2,1,'Gerente','Administracion Generaal'),
                (2,2,'Vendedor','Encargador de realizar las ventas'),
                (2,3,'Almacenero','Encargado de la logistica'),
                (2,4,'Gerente','Encargado del area'),
                (2,1,'Subgerente','Subgerencia general');

INSERT INTO T_SEG_USUARIO (COMPANYID, USUARIO_V_CONTRASENA, USUARIO_V_USERNAME)
          VALUES (1,'62a90ccff3fd73694bf6281bb234b09a',NULL),
                 (2,'e10adc3949ba59abbe56e057f20f883e','jmoics'),
                 (3,'e10adc3949ba59abbe56e057f20f883e','jcueva'),
                 (2,'e10adc3949ba59abbe56e057f20f883e','jcueva'),
                 (2,'e10adc3949ba59abbe56e057f20f883e','aencalada'),
                 (1,'e10adc3949ba59abbe56e057f20f883e','jmoics@gmail.com'),
                 (1,'e10adc3949ba59abbe56e057f20f883e','jacky@gmail.com');

INSERT INTO T_SEG_MODULE (COMPANYID, DESCRIPTION, RESOURCE) VALUES (2,'Module para administracion de empleados','MODULE_ADM_EMPLEADO'),
                (2,'Modulo para productos','MODULE_PRODUCTS'),
                (2,'Modulo para gestion de productos','MODULE_PROD_PRODUCT'),
                (2,'Modulo para gestion de categories','MODULE_PROD_CATEGORY'),
                (2,'Modulo para gestion de inventario','MODULE_PROD_INVENTORY'),
                (2,'Modulo para gestion de marcas','MODULE_PROD_TRADEMARK'),
                (2,'Modulo para gestion de montos','MODULE_PROD_AMOUNT'),
                (2,'Modulo para administracion','MODULE_ADMINISTRACION'),
                (2,'Modulo para administracion de roles','MODULE_ADM_ROLE'),
                (2,'Modulo para administracion de areas','MODULE_ADM_AREA'),
                (2,'Modulo para administracion de modules','MODULE_ADM_MODULE'),
                (2,'Modulo para asignacion de modules a roles','MODULE_ADM_ROLEMODULE'),
                (2,'Modulo para administrar la contrasena','MODULE_CHANGE_PASS');

INSERT INTO T_SEG_ROLE2MODULE (ROLEID, MODULEID, ACTIVE)
         VALUES (1,1,true),(1,2,true),(1,3,true),(1,4,true),(1,5,true),(1,6,true),(1,7,true),(1,8,true),(1,9,true),(1,10,true),(1,11,true),
                (2,2,true),(2,4,true),(3,2,true),(3,3,true),(3,4,true),(3,5,true),(3,11,true),(4,1,true),(4,6,true),(4,7,true),(4,11,true),
                (5,2,true),(5,4,true),(5,5,true),(5,6,true),(5,7,true),(5,10,true),(5,11,true);

INSERT INTO T_EMP_EMPLEADO (ROLEID, USUARIO_N_CODIGO, COMPANYID, EMPLEADO_V_NOMBRE, EMPLEADO_V_APELLIDO,  EMPLEADO_V_DNI, EMPLEADO_V_DIRECCION, EMPLEADO_V_TELEFONO, EMPLEADO_V_CELULAR, EMPLEADO_V_EMAIL, EMPLEADO_V_CIUDAD, EMPLEADO_V_REGION, EMPLEADO_D_FECHNAC, EMPLEADO_N_ACTIVO)
           VALUES (1,4,2,'Jorge','Cueva','44128463','Mi casa','4823262','945190242','jmoics@gmail.com','Lima','Lima','1987-12-01',1),
                  (3,2,2,'jorge','cueva samames','44128463','mi house','4563423','987755443','','lima','lima',NULL,1);

INSERT INTO T_BUS_CATEGORY (COMPANYID, NAME, ACTIVE, PARENTID)
           VALUES (2, 'C�mputo y Videojuegos', true, null),
                  (2, 'C�mputo', true, 1),
                  (2, 'Tablets y Accesorios', true, 1),
                  (2, 'Videojuegos', true, 1),
                  (2, 'PlayStation', true, 4),
                  (2, 'Desktop', true, 2),
                  (2, 'TV, Audio y Foto', true, null),
                  (2, 'TV y Video', true, 7);

INSERT INTO T_BUS_TRADEMARK (COMPANYID, NAME, ACTIVE, IMAGE)
           VALUES (2, 'Sony', true, null),
                  (2, 'Toshiba', true, null),
                  (2, 'LG', true, null),
                  (2, 'Samsung', true, null);

INSERT INTO T_BUS_PRODUCT (COMPANYID, TRADEMARKID, NAME, DESCRIPTION, ACTIVE)
           VALUES (2,1,'Ring Action','juego de video de accion basado en la trilogia del senior de los anillos',true),
                  (2,1,'Final Fantasy X','Juego de ROL, para consola Play Station 2',true),
                  (2,1,'PS2','play station 2 color azul con un mando y una memory card',true),
                  (2,1,'PS2','play station 2 azul y negro mas un mando',true),
                  (2,1,'Final fantasy x-2','Juego rpg final fantasy x-2 original en caja',true),
                  (2,1,'Final Fantasy XII','Juego Final Fantasy XII original algo rayadito XD',true),
                  (2,1,'PS2','consola ps2',true);

INSERT INTO T_BUS_CATEG2ARTIC (CATEGORYID, PRODUCTID)
           VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(2,1),(2,2),(4,3),(4,4),(2,5),(2,6),(4,7);

INSERT INTO T_BUS_PRECIO (PRODUCTID, DESDE, HASTA, MONTO)
           VALUES (1,'01/01/2015','31/03/2015',24.50), (2,'01/01/2015','31/03/2015',25.90), (3,'01/01/2015','31/03/2015',550.00),
                  (4,'01/01/2015','31/03/2015',540.00),(5,'01/01/2015','31/03/2015',35.00), (6,'01/01/2015','31/03/2015',40.00),
                  (7,'01/01/2015','31/03/2015',500.00);

INSERT INTO T_BUS_COSTO (PRODUCTID, DESDE, HASTA, MONTO)
           VALUES (1,'01/01/2015','31/03/2015',19.50), (2,'01/01/2015','31/03/2015',18.40), (3,'01/01/2015','31/03/2015',490.00),
                  (4,'01/01/2015','31/03/2015',500.00),(5,'01/01/2015','31/03/2015',28.00), (6,'01/01/2015','31/03/2015',32.00),
                  (7,'01/01/2015','31/03/2015',450.00);

INSERT INTO T_BUS_CLIENTE (COMPANYID, USUARIO_N_CODIGO, CLIENTE_V_NOMBRE, CLIENTE_V_APELLIDO, CLIENTE_D_FECHANAC, CLIENTE_N_DNI, CLIENTE_V_DIRECCION, CLIENTE_V_TELEFONO, CLIENTE_V_CELULAR, CLIENTE_V_EMAIL, CLIENTE_V_CIUDAD, CLIENTE_V_REGION, CLIENTE_V_CODPOSTAL, CLIENTE_N_ACTIVO) VALUES (1,2,'Jorge','Cueva Samames','1987-02-12',45645645,'mi casa','4564564','987987987','jmoics@gmail.com','Lima',NULL,NULL,NULL),
                 (1,7,'Jackeline','Rios Ramos','1989-08-20',45645645,'su casa','4564564','987987987','jacky@gmail.com','Lima','Lima',NULL,1);

INSERT INTO T_BUS_PEDIDO_TIPO (TIPOPED_V_DESCRIPCION) VALUES ('Entrega a domicilio'),('Recoger el producto');

INSERT INTO T_BUS_PEDIDO (COMPANYID,CLIENTE_N_CODIGO,TIPOPED_N_CODIGO,PEDIDO_D_FECHA,PEDIDO_N_IGV,PEDIDO_N_TOTAL) VALUES (1,1,1,'2011-01-05',0,6300.00),(2,1,1,'2011-01-05',0,4900.00),(3,1,1,'2011-01-05',0,1400.00);

INSERT INTO T_BUS_PEDIDO2PEDIDO VALUES (1,2),(1,3);

INSERT INTO T_BUS_PEDIDO_DET VALUES (2,4,4),(2,5,5),(3,7,35);

INSERT INTO T_BUS_SOLICITUD (SOLICITUD_V_RAZON,SOLICITUD_N_RUC,SOLICITUD_D_FECHA,SOLICITUD_N_ESTADO,SOLICITUD_V_EMAIL) VALUES ('Bioquimica','96495623526','2011-01-06',4,'io@gmail.com');