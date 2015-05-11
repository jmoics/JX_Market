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
    /**
     *
     */
    @Autowired
    private CategoryMapper categoryMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    @Transactional
    public ServiceOutput<DTO_Category> execute(final ServiceInput<DTO_Category> _input)
    {
        final ServiceOutput<DTO_Category> output = new ServiceOutput<DTO_Category>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista(this.categoryMapper.getCategories(_input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final DTO_Category categTmp = this.categoryMapper.getCategory4Id(_input.getObject());
            if (categTmp == null) {
                this.categoryMapper.insertCategory(_input.getObject());
            } else {
                this.categoryMapper.updateCategory(_input.getObject());
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final DTO_Category art = this.categoryMapper.getCategory4Id(_input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.categoryMapper.deleteCategory(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
