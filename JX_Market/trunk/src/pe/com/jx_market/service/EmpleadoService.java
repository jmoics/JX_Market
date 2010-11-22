package pe.com.jx_market.service;

import java.util.HashSet;

import pe.com.jx_market.dao.EmpleadoDAO;
import pe.com.jx_market.domain.DTO_Cliente;
import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Usuario;

import pe.com.jx_market.utilities.*;

/**
 * Servicio de Administracion de Contactos
 * 
 * @author jorge
 * 
 */

public class EmpleadoService implements BusinessService {

    private EmpleadoDAO dao;

    /**
     * El DTO_Input contendrá como verbo un String: para realizar una consulta
     * se usará el verbo "list" y un string con el codigo de la institucion a
     * la que pertenece el contacto, para ingresar o actualizar datos se usará
     * el verbo "register" conteniendo además un objeto DTO_Contacto con los
     * datos nuevos a ingresar, y para eliminar datos se usará el verbo
     * "delete" adeḿas de contener un string con el codigo del contacto a
     * eliminar. El DTO_Output tiene codigo de error OK; y si el verbo es "list"
     * contendra una lista de objetos DTO_Contacto con todos los campos leidos
     * de la Base de Datos.
     * 
     * @param Objeto
     *            estandar de entrada
     * @return Objeto estandar de salida
     */
    public DTO_Output execute (DTO_Input input) {

        DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            DTO_Empleado empleado = (DTO_Empleado) input.getObject();
            output.setLista(dao.getEmpleados(empleado));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            DTO_Empleado empleado = (DTO_Empleado) input.getObject();
            output.setObject(dao.leeEmpleado(empleado));
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            dao.registraEmpleado((DTO_Empleado) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            dao.eliminaEmpleado((DTO_Empleado) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public EmpleadoDAO getDao () {
        return dao;
    }

    public void setDao (EmpleadoDAO dao) {
        this.dao = dao;
    }

}
