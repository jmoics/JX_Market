package pe.com.jx_market.utilities;


/**
 * Constantes a utilizar
 *
 */
public class Constantes {

    /*
     *
     * CONSTANTES RETORNADAS POR LOS SERVICIOS: ERROR_CODES
     */
    /** Exito en la ejecucion del servicio */
    public static final int OK = 17;
    /**
     * Problema interno: no se definio valor de retorno. Avisar al
     * desarrollador.
     */
    public static final int UNDEFINED = 0;
    /** Error de autenticación */
    public static final int AUTH_ERROR = 100;
    /** Parametro suministrado es invalido o nulo */
    public static final int INVALID_PARAM = 101;
    /** No se encontro lo que se solicita */
    public static final int NOT_FOUND = 102;
    /** Error descriptivo */
    public static final int ERROR_MSG = 103;
    /** Error de codigo de respuesta de transaccion */
    public static final int ERROR_RC = 103;
    /** Error por excepcion */
    public static final int ERROR_EXCEPTION = 104;
    /** Password muy debil */
    public static final int BAD_PASS = 105;
    /** Switch desconectado */
    public static final int CONN_ERROR = 106;
    /** Registro ya inscrito */
    public static final int ALREADY_USED = 107;
    /** Error de validacion */
    public static final int VALIDATION_ERROR = 108;

    /**
     * Institucion Jx-Market
     *
     * Este valor se asume que siempre esta en la base de datos del SICF
     */
    public static final int INSTITUCION_JX_MARKET = 1;

    /*
     * Constantes para acciones
     */
    public static final String V_REGISTER = "register";
    public static final String V_REGISTERCAT4PROD = "registerCat4Prod";
    public static final String V_REGISTERIMG4PROD = "registerImg4Prod";
    public static final String V_REGISTERPM = "registerPM";
    public static final String V_GET = "get";
    public static final String V_LIST = "list";
    public static final String V_LISTEMP = "listEmp";
    public static final String V_DELETE = "delete";
    public static final String V_DELETECAT4PROD = "deleteCat4Prod";
    public static final String V_USTOCK = "upStock";
    public static final String V_GETIMG = "getimg";
    public static final String V_UPDATE = "update";

    /*
     * Constantes para estados
     */
    public static final Integer ST_ACTIVO = 1;
    public static final Integer ST_INACTIVO = 2;
    public static final Integer ST_CANCELADO = 3;
    public static final Integer ST_PENDIENTE = 4;

    public static final Boolean STB_ACTIVO = true;
    public static final Boolean STB_INACTIVO = false;

    /*
     * Strings para estados
     */
    public static final String STATUS_ACTIVO = "Activo";
    public static final String STATUS_INACTIVO = "Inactivo";
    public static final String STATUS_CANCELADO = "Cancelado";
    public static final String STATUS_PENDIENTE = "Pendiente";

    /*
     * Temas configurables
     */

    public static final String TEMA_ROJO = "rojo";
    public static final String TEMA_PLOMO = "plomo";
    public static final String TEMA_VERDE = "verde";
    public static final String TEMA_AZUL = "azul";
    public static final String TEMA_NARANJA = "naranja";
    public static final String TEMA_LILA = "lila";
    public static final String TEMA_FUCSIA = "fucsia";
    public static final String TEMA_AMARILLO = "amarillo";
    public static final String TEMA_CELESTE = "celeste";
    public static final String TEMA_NEGRO = "negro";

    /*
     * imagenes
     */
    public static final String RUTA_IMAGENES = "/home/imagenesJXMarket";
    public static final String RUTA_IMAGENES_WINDOWS = "C:/imagenesJXMarket";

    /*
     * Modules para accesos
     */
    public static final String MODULE_PRODUCTS = "MODULE_PRODUCTS";
    public static final String MODULE_PROD_CATEGORY = "MODULE_PROD_CATEGORY";
    public static final String MODULE_PROD_CATEGORY_EDIT = "MODULE_PROD_CATEGORY_EDIT";
    public static final String MODULE_PROD_CATEGORY_CREATE = "MODULE_PROD_CATEGORY_CREATE";
    public static final String MODULE_PROD_TRADEMARK = "MODULE_PROD_TRADEMARK";
    public static final String MODULE_PROD_TRADEMARK_EDIT = "MODULE_PROD_TRADEMARK_EDIT";
    public static final String MODULE_PROD_TRADEMARK_CREATE = "MODULE_PROD_TRADEMARK_CREATE";
    public static final String MODULE_PROD_PRODUCT = "MODULE_PROD_PRODUCT";
    public static final String MODULE_PROD_PRODUCT_EDIT = "MODULE_PROD_PRODUCT_EDIT";
    public static final String MODULE_PROD_PRODUCT_CREATE = "MODULE_PROD_PRODUCT_CREATE";
    public static final String MODULE_PROD_AMOUNT = "MODULE_PROD_AMOUNUT";
    public static final String MODULE_PROD_INVENTORY = "MODULE_PROD_INVENTORY";

    public static final String MODULE_ADMINISTRACION = "MODULE_ADMINISTRACION";
    public static final String MODULE_ADM_ROLE = "MODULE_ADM_ROLE";
    public static final String MODULE_ADM_ROLE_EDIT = "MODULE_ADM_ROLE_EDIT";
    public static final String MODULE_ADM_ROLE_CREATE = "MODULE_ADM_ROLE_CREATE";
    public static final String MODULE_ADM_AREA = "MODULE_ADM_AREA";
    public static final String MODULE_ADM_AREA_EDIT = "MODULE_ADM_AREA_EDIT";
    public static final String MODULE_ADM_AREA_CREATE = "MODULE_ADM_AREA_CREATE";
    public static final String MODULE_ADM_EMPLOYEE = "MODULE_ADM_EMPLOYEE";
    public static final String MODULE_ADM_MODULE = "MODULE_ADM_MODULE";
    public static final String MODULE_ADM_MODULE_EDIT = "MODULE_ADM_MODULE_EDIT";
    public static final String MODULE_ADM_MODULE_CREATE = "MODULE_ADM_MODULE_CREATE";
    public static final String MODULE_ADM_ROLEMODULE = "MODULE_ADM_ROLEMODULE";
    public static final String MODULE_ADM_ROLEMODULE_EDIT = "MODULE_ADM_ROLEMODULE_EDIT";
    public static final String MODULE_CHANGE_PASS = "MODULE_CHANGE_PASS";

    /*VALIDACION DE CARACTERES*/
    public static final String VALID_TELEFONO = "^[0-9]*$";

    /*
     * Tipos de Pedidos
     */
    public static final String TIPO_PEDIDO_ENTREGA = "Entrega a Domicilio";
    public static final String TIPO_PEDIDO_RECOGE = "Recoger Producto";

    public static final String TREE_EDITABLE_RAIZ = "Raiz";
    public static final String TREE_EDITABLE_TEXTBOX = "textBox";

    public static final String EMPTY_STRING = "";
    public static final String SEPARATOR_STRING = "..";

    public enum Form {
        EMPTY_FORM("eAFondo.zul"),
        PRODUCTS_FORM("eAProducts.zul"),
        PRODUCTS_EDIT_FORM("eAProductsEdit.zul"),
        PRODUCTS_CREATE_FORM("eAProductsCreate.zul"),
        TRADEMARK_FORM("eATradeMark.zul"),
        TRADEMARK_EDIT_FORM("eATradeMarkEdit.zul"),
        TRADEMARK_CREATE_FORM("eATradeMarkCreate.zul"),
        AREA_FORM("eAAdministrateArea.zul"),
        AREA_EDIT_FORM("eAAdministrateAreaEdit.zul"),
        AREA_CREATE_FORM("eAAdministrateAreaCreate.zul"),
        ROLE_FORM("eAAdministrateRole.zul"),
        ROLE_EDIT_FORM("eAAdministrateRoleEdit.zul"),
        ROLE_CREATE_FORM("eAAdministrateRoleCreate.zul"),
        MODULE_FORM("eAAdministrateModule.zul"),
        MODULE_EDIT_FORM("eAAdministrateModuleEdit.zul"),
        MODULE_CREATE_FORM("eAAdministrateModuleCreate.zul"),
        ROLEMODULE_FORM("eAAdministrateRoleModule.zul"),
        ROLEMODULE_EDIT_FORM("eAAdministrateRoleModuleEdit.zul");

        private final String form;

        private Form(final String _form) {
            this.form = _form;
        }

        public String getForm() {
            return this.form;
        }
    }

    public static final String ATTRIBUTE_RELOAD = "reloadPage";
    public static final String ATTRIBUTE_PARENTFORM = "parentForm";
    public static final String ATTRIBUTE_COMPANY = "company";
    public static final String ATTRIBUTE_PRODUCT = "product";
    public static final String ATTRIBUTE_PRODUCT_EDITIMAGE = "productEditImage";
    public static final String ATTRIBUTE_TRADEMARK = "tradeMark";
    public static final String ATTRIBUTE_AREA = "area";
    public static final String ATTRIBUTE_ROLE = "role";
    public static final String ATTRIBUTE_MODULE = "module";
    public static final String ATTRIBUTE_MODULES = "module-array";
    public static final String ATTRIBUTE_ROLEMODULE = "roleModule";
    public static final String ATTRIBUTE_EMPLOYEE = "employee";
    public static final String ATTRIBUTE_USER = "login";


    /**
     * To use in future to check access.
     * @author jcuevas
     *
     */
    public enum AccessType {
        CREATE("create"),
        EDIT("edit"),
        VIEW("view"),
        DELETE("delete");

        private final String accessType;

        private AccessType(final String _accessType) {
            this.accessType = _accessType;
        }

        public String getAccessType() {
            return this.accessType;
        }
    }
}
