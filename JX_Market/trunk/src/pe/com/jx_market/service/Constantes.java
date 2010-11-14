package pe.com.jx_market.service;

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

    /**
     * Institucion Jx-Market
     * 
     * Este valor se asume que siempre esta en la base de datos del SICF
     */
    public static final int INSTITUCION_JX_MARKET = 99;

    /*
     * Constantes para verbos
     */
    public static final String V_REGISTER = "register";
    public static final String V_GET = "get";
    public static final String V_LIST = "list";
    public static final String V_USTOCK = "upStock";

    /*
     * Constantes para estados
     */
    public static final int ST_ACTIVO = 1;
    public static final int ST_INACTIVO = 2;
    public static final int ST_CANCELADO = 3;

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

}
