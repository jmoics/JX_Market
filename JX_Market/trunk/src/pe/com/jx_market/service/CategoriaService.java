/**
 * 
 */
package pe.com.jx_market.service;

import pe.com.jx_market.dao.CategoriaDAO;
import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 *
 */
public class CategoriaService implements BusinessService
{
    private CategoriaDAO dao;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.DTO_Input)
     */
    @Override
    public DTO_Output execute(DTO_Input input)
    {
        DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(dao.getCategorias((DTO_Categoria)input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            dao.insertCategoria((DTO_Categoria) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            DTO_Categoria art = dao.getCategoriaXCodigo((DTO_Categoria) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            dao.deleteCategoria((DTO_Categoria) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }
    
    public CategoriaDAO getDao()
    {
        return dao;
    }

    public void setDao(CategoriaDAO dao)
    {
        this.dao = dao;
    }
}
