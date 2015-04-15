/**
 *
 */
package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Category;
import pe.com.jx_market.persistence.CategoryMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@Service
public class CategoryService
    implements BusinessService<DTO_Category>
{
    @Autowired
    private CategoryMapper categoriaMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    @Transactional
    public ServiceOutput<DTO_Category> execute(final ServiceInput<DTO_Category> input)
    {
        final ServiceOutput<DTO_Category> output = new ServiceOutput<DTO_Category>();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            output.setLista(categoriaMapper.getCategorias(input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            final DTO_Category categTmp = categoriaMapper.getCategoriaXCodigo(input.getObject());
            if (categTmp == null) {
                categoriaMapper.insertCategoria(input.getObject());
            } else {
                categoriaMapper.updateCategoria(input.getObject());
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            final DTO_Category art = categoriaMapper.getCategoriaXCodigo(input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_DELETE.equals(input.getAccion())) {
            categoriaMapper.deleteCategoria(input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public CategoryMapper getDao()
    {
        return categoriaMapper;
    }

    public void setDao(final CategoryMapper categoriaMapper)
    {
        this.categoriaMapper = categoriaMapper;
    }
}
