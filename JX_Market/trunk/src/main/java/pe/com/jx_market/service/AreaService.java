package pe.com.jx_market.service;

import pe.com.jx_market.dao.AreaDAO;
import pe.com.jx_market.domain.DTO_Area;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class AreaService
    implements BusinessService
{
    private AreaDAO dao;

    @Override
    public DTO_Output execute(final DTO_Input input)
    {
        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(dao.getAreas((DTO_Area) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            final Integer cod = dao.insertArea((DTO_Area) input.getObject());
            output.setObject(cod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            final DTO_Area art = dao.getAreaXCodigo((DTO_Area) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            dao.deleteArea((DTO_Area) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public AreaDAO getDao()
    {
        return dao;
    }

    public void setDao(final AreaDAO dao)
    {
        this.dao = dao;
    }

}
