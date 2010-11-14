/**
 * 
 */
package pe.com.jx_market.service;

import pe.com.jx_market.dao.ArticuloDAO;
import pe.com.jx_market.dao.UsuarioDAO;
import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 *
 */
public class ArticuloService implements BusinessService
{
    private ArticuloDAO dao;
    @Override
    public DTO_Output execute(DTO_Input input)
    {
        DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(dao.getArticulos((DTO_Articulo)input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            dao.insertArticulo((DTO_Articulo) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            DTO_Articulo art = dao.getArticuloXCodigo((DTO_Articulo) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_USTOCK.equals(input.getVerbo())) {
            dao.insertStock((DTO_Articulo) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }
    
    public ArticuloDAO getDao () {
        return dao;
    }

    public void setDao (ArticuloDAO dao) {
        this.dao = dao;
    }

}
