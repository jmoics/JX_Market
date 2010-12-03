package pe.com.jx_market.service;

import pe.com.jx_market.dao.ModuloDAO;
import pe.com.jx_market.domain.DTO_Modulo;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class ModuloService
    implements BusinessService
{
    private ModuloDAO dao;

    @Override
    public DTO_Output execute(final DTO_Input input)
    {
        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(dao.getModulos((DTO_Modulo) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            dao.insertModulo((DTO_Modulo) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            final DTO_Modulo mod = dao.getModuloXCodigo((DTO_Modulo) input.getObject());
            output.setObject(mod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            dao.deleteModulo((DTO_Modulo) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public ModuloDAO getDao()
    {
        return dao;
    }

    public void setDao(final ModuloDAO dao)
    {
        this.dao = dao;
    }

}
