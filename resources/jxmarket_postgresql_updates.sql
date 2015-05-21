CREATE SEQUENCE T_COM_AREA_ID_SEQ;
ALTER TABLE T_COM_AREA ALTER COLUMN ID SET DEFAULT nextval('T_COM_AREA_ID_SEQ');
ALTER SEQUENCE T_COM_AREA_ID_SEQ OWNED BY T_COM_AREA.ID;

CREATE SEQUENCE T_BUS_PARAMTYPE_ID_SEQ;
ALTER TABLE T_BUS_PARAMTYPE ALTER COLUMN ID SET DEFAULT nextval('T_BUS_PARAMTYPE_ID_SEQ');
ALTER SEQUENCE T_BUS_PARAMTYPE_ID_SEQ OWNED BY T_BUS_PARAMTYPE.ID;

CREATE SEQUENCE T_BUS_PARAMETER_ID_SEQ;
ALTER TABLE T_BUS_PARAMETER ALTER COLUMN ID SET DEFAULT nextval('T_BUS_PARAMETER_ID_SEQ');
ALTER SEQUENCE T_BUS_PARAMETER_ID_SEQ OWNED BY T_BUS_PARAMETER.ID;

CREATE SEQUENCE T_BUS_EMPLOYEE_ID_SEQ;
ALTER TABLE T_BUS_EMPLOYEE ALTER COLUMN ID SET DEFAULT nextval('T_BUS_EMPLOYEE_ID_SEQ');
ALTER SEQUENCE T_BUS_EMPLOYEE_ID_SEQ OWNED BY T_BUS_EMPLOYEE.ID;

CREATE SEQUENCE T_COM_COMPANY_ID_SEQ;
ALTER TABLE T_COM_COMPANY ALTER COLUMN ID SET DEFAULT nextval('T_COM_COMPANY_ID_SEQ');
ALTER SEQUENCE T_COM_COMPANY_ID_SEQ OWNED BY T_COM_COMPANY.ID;

CREATE SEQUENCE T_BUS_TRADEMARK_ID_SEQ;
ALTER TABLE T_BUS_TRADEMARK ALTER COLUMN ID SET DEFAULT nextval('T_BUS_TRADEMARK_ID_SEQ');
ALTER SEQUENCE T_BUS_TRADEMARK_ID_SEQ OWNED BY T_BUS_TRADEMARK.ID;

CREATE SEQUENCE T_BUS_PRODUCTIMAGE_ID_SEQ;
ALTER TABLE T_BUS_PRODUCTIMAGE ALTER COLUMN ID SET DEFAULT nextval('T_BUS_PRODUCTIMAGE_ID_SEQ');
ALTER SEQUENCE T_BUS_PRODUCTIMAGE_ID_SEQ OWNED BY T_BUS_PRODUCTIMAGE.ID;

CREATE SEQUENCE T_BUS_PRODUCT_ID_SEQ;
ALTER TABLE T_BUS_PRODUCT ALTER COLUMN ID SET DEFAULT nextval('T_BUS_PRODUCT_ID_SEQ');
ALTER SEQUENCE T_BUS_PRODUCT_ID_SEQ OWNED BY T_BUS_PRODUCT.ID;

CREATE SEQUENCE T_BUS_ARTIC2ATRIB_ID_SEQ;
ALTER TABLE T_BUS_ARTIC2ATRIB ALTER COLUMN ID SET DEFAULT nextval('T_BUS_ARTIC2ATRIB_ID_SEQ');
ALTER SEQUENCE T_BUS_ARTIC2ATRIB_ID_SEQ OWNED BY T_BUS_ARTIC2ATRIB.ID;

CREATE SEQUENCE T_BUS_CATEGORY_ID_SEQ;
ALTER TABLE T_BUS_CATEGORY ALTER COLUMN ID SET DEFAULT nextval('T_BUS_CATEGORY_ID_SEQ');
ALTER SEQUENCE T_BUS_CATEGORY_ID_SEQ OWNED BY T_BUS_CATEGORY.ID;

CREATE SEQUENCE T_BUS_CATEG2ARTIC_ID_SEQ;
ALTER TABLE T_BUS_CATEG2ARTIC ALTER COLUMN ID SET DEFAULT nextval('T_BUS_CATEG2ARTIC_ID_SEQ');
ALTER SEQUENCE T_BUS_CATEG2ARTIC_ID_SEQ OWNED BY T_BUS_CATEG2ARTIC.ID;

CREATE SEQUENCE T_BUS_ATRIBUTO_ID_SEQ;
ALTER TABLE T_BUS_ATRIBUTO ALTER COLUMN ID SET DEFAULT nextval('T_BUS_ATRIBUTO_ID_SEQ');
ALTER SEQUENCE T_BUS_ATRIBUTO_ID_SEQ OWNED BY T_BUS_ATRIBUTO.ID;

CREATE SEQUENCE T_BUS_INVENTARIO_ID_SEQ;
ALTER TABLE T_BUS_INVENTARIO ALTER COLUMN ID SET DEFAULT nextval('T_BUS_INVENTARIO_ID_SEQ');
ALTER SEQUENCE T_BUS_INVENTARIO_ID_SEQ OWNED BY T_BUS_INVENTARIO.ID;

CREATE SEQUENCE T_BUS_PRECIO_ID_SEQ;
ALTER TABLE T_BUS_PRECIO ALTER COLUMN ID SET DEFAULT nextval('T_BUS_PRECIO_ID_SEQ');
ALTER SEQUENCE T_BUS_PRECIO_ID_SEQ OWNED BY T_BUS_PRECIO.ID;

CREATE SEQUENCE T_BUS_COSTO_ID_SEQ;
ALTER TABLE T_BUS_COSTO ALTER COLUMN ID SET DEFAULT nextval('T_BUS_COSTO_ID_SEQ');
ALTER SEQUENCE T_BUS_COSTO_ID_SEQ OWNED BY T_BUS_COSTO.ID;

CREATE SEQUENCE T_BUS_OFERTA_ID_SEQ;
ALTER TABLE T_BUS_OFERTA ALTER COLUMN ID SET DEFAULT nextval('T_BUS_OFERTA_ID_SEQ');
ALTER SEQUENCE T_BUS_OFERTA_ID_SEQ OWNED BY T_BUS_OFERTA.ID;

CREATE SEQUENCE T_BUS_CLIENT_ID_SEQ;
ALTER TABLE T_BUS_CLIENT ALTER COLUMN ID SET DEFAULT nextval('T_BUS_CLIENT_ID_SEQ');
ALTER SEQUENCE T_BUS_CLIENT_ID_SEQ OWNED BY T_BUS_CLIENT.ID;

CREATE SEQUENCE T_BUS_PEDIDO_ID_SEQ;
ALTER TABLE T_BUS_PEDIDO ALTER COLUMN PEDIDO_N_CODIGO SET DEFAULT nextval('T_BUS_PEDIDO_ID_SEQ');
ALTER SEQUENCE T_BUS_PEDIDO_ID_SEQ OWNED BY T_BUS_PEDIDO.PEDIDO_N_CODIGO;

CREATE SEQUENCE T_BUS_PEDIDO_TIPO_ID_SEQ;
ALTER TABLE T_BUS_PEDIDO_TIPO ALTER COLUMN TIPOPED_N_CODIGO SET DEFAULT nextval('T_BUS_PEDIDO_TIPO_ID_SEQ');
ALTER SEQUENCE T_BUS_PEDIDO_TIPO_ID_SEQ OWNED BY T_BUS_PEDIDO_TIPO.TIPOPED_N_CODIGO;

CREATE SEQUENCE T_BUS_SOLICITUD_ID_SEQ;
ALTER TABLE T_BUS_SOLICITUD ALTER COLUMN SOLICITUD_N_CODIGO SET DEFAULT nextval('T_BUS_SOLICITUD_ID_SEQ');
ALTER SEQUENCE T_BUS_SOLICITUD_ID_SEQ OWNED BY T_BUS_SOLICITUD.SOLICITUD_N_CODIGO;

CREATE SEQUENCE T_SEG_MODULE_ID_SEQ;
ALTER TABLE T_SEG_MODULE ALTER COLUMN ID SET DEFAULT nextval('T_SEG_MODULE_ID_SEQ');
ALTER SEQUENCE T_SEG_MODULE_ID_SEQ OWNED BY T_SEG_MODULE.ID;

CREATE SEQUENCE T_SEG_ROLE_ID_SEQ;
ALTER TABLE T_SEG_ROLE ALTER COLUMN ID SET DEFAULT nextval('T_SEG_ROLE_ID_SEQ');
ALTER SEQUENCE T_SEG_ROLE_ID_SEQ OWNED BY T_SEG_ROLE.ID;

CREATE SEQUENCE T_SEG_ROLE2MODULE_ID_SEQ;
ALTER TABLE T_SEG_ROLE2MODULE ALTER COLUMN ID SET DEFAULT nextval('T_SEG_ROLE2MODULE_ID_SEQ');
ALTER SEQUENCE T_SEG_ROLE2MODULE_ID_SEQ OWNED BY T_SEG_ROLE2MODULE.ID;

CREATE SEQUENCE T_SEG_USER_ID_SEQ;
ALTER TABLE T_SEG_USER ALTER COLUMN ID SET DEFAULT nextval('T_SEG_USER_ID_SEQ');
ALTER SEQUENCE T_SEG_USER_ID_SEQ OWNED BY T_SEG_USER.ID;



--
-- Dumping data for table T_COM_AREA
--

INSERT INTO T_COM_COMPANY (BUSINESSNAME, ACTIVE, DOCNUMBER, DOMAIN)
    VALUES ('JX_Market',1,'44563458976','jxmarket'),('Metro',1,'35346576899','metro'),('moxter.net',1,'56452345346','moxternet');

INSERT INTO T_COM_AREA (COMPANYID, NAME) VALUES (2,'Gerenciaa'),(2,'Ventaas'),(2,'Productos'),(2,'Recursos Humanos'),(1,'Administration');

INSERT INTO T_SEG_ROLE (COMPANYID, AREAID, NAME, DESCRIPTION) VALUES (2,1,'Gerente','Administracion Generaal'),
                (2,2,'Vendedor','Encargador de realizar las ventas'),
                (2,3,'Almacenero','Encargado de la logistica'),
                (2,4,'Gerente','Encargado del area'),
                (2,1,'Subgerente','Subgerencia general');

INSERT INTO T_SEG_USER (COMPANYID, PASSWORD, USERNAME, ROLEID)
          VALUES (1,'62a90ccff3fd73694bf6281bb234b09a','Administrator',1),
                 (2,'e10adc3949ba59abbe56e057f20f883e','jmoics',3),
                 (3,'e10adc3949ba59abbe56e057f20f883e','jcueva',1),
                 (2,'e10adc3949ba59abbe56e057f20f883e','jcueva',1),
                 (2,'e10adc3949ba59abbe56e057f20f883e','aencalada',2),
                 (1,'e10adc3949ba59abbe56e057f20f883e','jmoics@gmail.com',2),
                 (1,'e10adc3949ba59abbe56e057f20f883e','jacky@gmail.com',2);

INSERT INTO T_SEG_MODULE (COMPANYID, DESCRIPTION, RESOURCE) 
		 VALUES (2,'Módulo para administracion de empleados','MODULE_ADM_EMPLOYEE'),
                (2,'Módulo para productos','MODULE_PRODUCTS'),
                (2,'Módulo para gestion de productos','MODULE_PROD_PRODUCT'),
                (2,'Módulo para edición de productos','MODULE_PROD_PRODUCT_EDIT'),
                (2,'Módulo para creación de productos','MODULE_PROD_PRODUCT_CREATE'),
                (2,'Módulo para gestion de categories','MODULE_PROD_CATEGORY'),
                (2,'Módulo para edición de categories','MODULE_PROD_CATEGORY_EDIT'),
                (2,'Módulo para creación de categories','MODULE_PROD_CATEGORY_CREATE'),
                (2,'Módulo para gestion de inventario','MODULE_PROD_INVENTORY'),
                (2,'Módulo para gestion de marcas','MODULE_PROD_TRADEMARK'),
                (2,'Módulo para edición de marcas','MODULE_PROD_TRADEMARK_EDIT'),
                (2,'Módulo para creación de marcas','MODULE_PROD_TRADEMARK_CREATE'),
                (2,'Módulo para gestion de montos','MODULE_PROD_AMOUNT'),
                (2,'Módulo para administracion','MODULE_ADMINISTRACION'),
                (2,'Módulo para administracion de roles','MODULE_ADM_ROLE'),
                (2,'Módulo para edición de roles','MODULE_ADM_ROLE_EDIT'),
                (2,'Módulo para creación de roles','MODULE_ADM_ROLE_CREATE'),
                (2,'Módulo para administracion de areas','MODULE_ADM_AREA'),
                (2,'Módulo para edición de areas','MODULE_ADM_AREA_EDIT'),
                (2,'Módulo para creación de areas','MODULE_ADM_AREA_CREATE'),
                (2,'Módulo para administracion de modules','MODULE_ADM_MODULE'),
                (2,'Módulo para edición de recursos','MODULE_ADM_MODULE_EDIT'),
                (2,'Módulo para creación de recursos','MODULE_ADM_MODULE_CREATE'),
                (2,'Módulo para asignacion de recursos a roles','MODULE_ADM_ROLEMODULE'),
                (2,'Módulo para editar asignaciones de recursos a roles','MODULE_ADM_ROLEMODULE_EDIT'),
                (2,'Módulo para administracion de empleados','MODULE_ADM_EMPLOYEE'),
                (2,'Módulo para creación de empleados','MODULE_ADM_EMPLOYEE_CREATE'),
                (2,'Módulo para edición de empleados','MODULE_ADM_EMPLOYEE_EDIT'),
                (2,'Módulo para administrar la contrasena','MODULE_CHANGE_PASS');


INSERT INTO T_SEG_ROLE2MODULE (ROLEID, MODULEID, ACTIVE)
           VALUES (1,1,true),(1,2,true),(1,3,true),(1,4,true),(1,5,true),(1,6,true),(1,7,true),(1,8,true),(1,9,true),(1,10,true),(1,11,true),(1,12,true),
                  (1,13,true),(1,14,true),(1,15,true),(1,16,true),(1,17,true),(1,18,true),(1,19,true),(1,20,true),(1,21,true),(1,22,true),(1,23,true),(1,24,true),(1,25,true),(1,26,true),
                  (2,2,true),(2,4,true),(3,2,true),(3,3,true),(3,4,true),(3,5,true),(3,11,true),(4,1,true),(4,6,true),(4,7,true),(4,11,true),
                  (5,2,true),(5,4,true),(5,5,true),(5,6,true),(5,7,true),(5,10,true),(5,11,true);

INSERT INTO T_BUS_PARAMTYPE (COMPANYID,NAME,DESCRIPTION)
            VALUES (2,'DOCUMENT_TYPE','Parametros para guardar los tipos de documento'),
                   (2,'SEX_TYPE','Parametro para guardar el género de la persona'),
                   (2,'CIVILSTATE_TYPE','Parametro para guardar el estado civil de la persona');

INSERT INTO T_BUS_PARAMETER (COMPANYID,PARAMTYPEID,NAME,DESCRIPTION)
            VALUES (2,1,'DNI','DNI'),(2,1,'CE','Carnet de Extranjería'),
                   (2,2,'Masculino','Masculino'),(2,2,'Femenino','Femenino'),
                   (2,3,'Soltero','Soltero'),(2,3,'Casado','Casado');

INSERT INTO T_BUS_EMPLOYEE (USERID, COMPANYID, NAME, LASTNAME, LASTNAME2, DOCTYPEID, DOCNUMBER, ADDRESS, PHONE, CELLPHONE, EMAIL, CITY, UBIGEO, BIRTHDAY, SEXID, CIVILSTATEID, ACTIVE)
            VALUES (4,2,'Jorge','Cueva','Samames',1,'44128463','Mi casa','4823262','945190242','jmoics@gmail.com','Lima','150135','1987-12-01',3,5,true),
                   (2,2,'George','Cave',null,1,'44128463','mi house','4563423','987755443','','lima','lima','1987-04-03',3,5,true);
                  
INSERT INTO T_BUS_CATEGORY (COMPANYID, NAME, ACTIVE, PARENTID)
           VALUES (2, 'Cómputo y Videojuegos', true, null),
                  (2, 'Cómputo', true, 1),
                  (2, 'Tablets y Accesorios', true, 1),
                  (2, 'Videojuegos', true, 1),
                  (2, 'PlayStation 4', true, 4),
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

INSERT INTO T_BUS_CLIENT (COMPANYID, USERID, NAME, LASTNAME, LASTNAME2, BIRTHDAY, DOCTYPEID,DOCNUMBER, ADDRESS, PHONE, CELLPHONE, EMAIL, CITY, UBIGEO, POSTCODE, ACTIVE)
          VALUES (1,2,'Jorge','Cueva','Samames','1987-02-12',1,'44128463','mi casa','4564564','987987987','jmoics@gmail.com','Lima','150135',NULL,true),
                 (1,7,'Jackeline','Rios','Ramos','1989-08-20',1,'45645645','su casa','4564564','987987987','jacky@gmail.com','Lima','150135',NULL,true);

INSERT INTO T_BUS_PEDIDO_TIPO (TIPOPED_V_DESCRIPCION) VALUES ('Entrega a domicilio'),('Recoger el producto');

INSERT INTO T_BUS_PEDIDO (COMPANYID,CLIENTID,TIPOPED_N_CODIGO,PEDIDO_D_FECHA,PEDIDO_N_IGV,PEDIDO_N_TOTAL) VALUES (1,1,1,'2011-01-05',0,6300.00),(2,1,1,'2011-01-05',0,4900.00),(3,1,1,'2011-01-05',0,1400.00);

INSERT INTO T_BUS_PEDIDO2PEDIDO VALUES (1,2),(1,3);

INSERT INTO T_BUS_PEDIDO_DET VALUES (2,4,4),(2,5,5),(3,7,35);

INSERT INTO T_BUS_SOLICITUD (SOLICITUD_V_RAZON,SOLICITUD_N_RUC,SOLICITUD_D_FECHA,SOLICITUD_N_ESTADO,SOLICITUD_V_EMAIL) VALUES ('Bioquimica','96495623526','2011-01-06',4,'io@gmail.com');



