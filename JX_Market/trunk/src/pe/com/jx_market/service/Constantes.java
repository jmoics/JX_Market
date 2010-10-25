package pe.com.jx_market.service;

/**
 * Constantes a utilizar
 *
 */
public class Constantes {

	/*
	 *
	 * CONSTANTES RETORNADAS POR LOS SERVICIOS: ERROR_CODES
	 * 
	 */
	
    /** Exito en la ejecucion del servicio */
	public static final int OK = 17;
    /** Problema interno: no se definio valor de retorno. Avisar al desarrollador. */
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
     *  Este valor se asume que siempre esta en la base de datos del SICF
     */
    public static final int INSTITUCION_JX_MARKET = 99;
    
    /*
     * Constantes arbitrarias para estados del reclamo y transacciones
     */
    
    /** Estado de Reclamo: ABIERTO */
    public static final String ABIERTO = "A";
    /** Estado de Reclamo: CERRADO */
    public static final String CERRADO = "C";
     
    
    
    /** Tipo de autenticacion local */
    public static final String AUTH_LOCAL = "L";
    /** Tipo de autenticacion corporativa */
    public static final String AUTH_CORPORATIVA = "C";

    /** Perfil contacto en banco
     */
    public static final String PERFIL_CONTACTO_BANCO = "CB";
    /** Perfil supervisor de reclamos
     */
    public static final String PERFIL_SUPERVISOR_RECLAMOS = "OR";

    
    /*
     * Constantes para Asignacion de reclamos a un contacto
     */
    
    /** Se puede seleccionar solo una transaccion a la vez" */
    public static final String CONTACTO_RECL_ASSIGN="S";
    /** Se pueden seleccionar mas una transaccion */
    public static final String CONTACTO_NO_RECL_ASSIGN="N";
    
    /**
     * Identificador de accion estandar sin comportamiento programatico adicional
     */
    public static final String IDENTIFICADOR_ACCION_ESTANDAR = "STD";

    /**
     * Identificador de accion de tipo cierre automatico (background)
     */
    public static final String IDENTIFICADOR_ACCION_CIERRE_AUTOMATICO = "CIE_AUT";
    
    /**
     * Identificador de accion de tipo asociacion de transaccion a reclamo
     */
    public static final String IDENTIFICADOR_ACCION_REGISTRO_TX = "REG_TX";

    /**
     * Identificador de accion de tipo cierre manual
     */
    public static final String IDENTIFICADOR_ACCION_CIERRE_MANUAL = "CIE_MAN";

    /**
     * Identificador de accion contracargo incoming visa
     */
    public static final String IDENTIFICADOR_ACCION_CONT_INC_VISA = "CIV";

    /**
     * Identificador de accion representacion incoming visa
     */
    public static final String IDENTIFICADOR_ACCION_REP_INC_VISA = "RIV";
    
    
    /**
     * Identificador de accion contracargo outgoing visa
     */
    public static final String IDENTIFICADOR_ACCION_CONT_OUT_VISA = "COV";
    
    /**
     * Identificador de accion representacion outgoing visa
     */
    public static final String IDENTIFICADOR_ACCION_REP_OUT_VISA = "ROV";

    /**
     * Identificador de accion pre cierre automatico
     */
    public static final String IDENTIFICADOR_ACCION_PRE_CIERRE_AUTOMATICO = "PCA";


    /*
     * Temas configurables 
     */
    
    public static final String TEMA_ROJO="rojo";
    public static final String TEMA_PLOMO="plomo";
    public static final String TEMA_VERDE="verde";
    public static final String TEMA_AZUL="azul";
    public static final String TEMA_NARANJA="naranja";
    public static final String TEMA_LILA="lila";
    public static final String TEMA_FUCSIA="fucsia";
    public static final String TEMA_AMARILLO="amarillo";
    public static final String TEMA_CELESTE="celeste";
    public static final String TEMA_NEGRO="negro";
    
}
