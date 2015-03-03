/**
 * 
 */
package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.persistence.CategoriaMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 *
 */
@Service
public class CategoriaService 
    implements BusinessService
{
    @Autowired
    private CategoriaMapper categoriaMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.DTO_Input)
     */
    @Override
    @Transactional
    public DTO_Output execute(DTO_Input input)
    {
        DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(categoriaMapper.getCategorias((DTO_Categoria)input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            DTO_Categoria categTmp = categoriaMapper.getCategoriaXCodigo((DTO_Categoria) input.getObject());
            if (categTmp == null) {
                categoriaMapper.insertCategoria((DTO_Categoria) input.getObject());
            } else {
                categoriaMapper.updateCategoria((DTO_Categoria) input.getObject());
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            DTO_Categoria art = categoriaMapper.getCategoriaXCodigo((DTO_Categoria) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            categoriaMapper.deleteCategoria((DTO_Categoria) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }
    
    public CategoriaMapper getDao()
    {
        return categoriaMapper;
    }

    public void setDao(CategoriaMapper categoriaMapper)
    {
        this.categoriaMapper = categoriaMapper;
    }
}
