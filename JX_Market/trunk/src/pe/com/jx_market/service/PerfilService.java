package pe.com.jx_market.service;

import pe.com.jx_market.dao.PerfilDAO;
import pe.com.jx_market.domain.DTO_Perfil;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

public class PerfilService
    implements BusinessService
{
    private PerfilDAO dao;

    @Override
    public DTO_Output execute(final DTO_Input input)
    {
        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(dao.getPerfiles((DTO_Perfil) input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            dao.insertPerfil((DTO_Perfil) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            final DTO_Perfil art = dao.getPerfilXCodigo((DTO_Perfil) input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETE.equals(input.getVerbo())) {
            dao.deletePerfil((DTO_Perfil) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    public PerfilDAO getDao()
    {
        return dao;
    }

    public void setDao(final PerfilDAO dao)
    {
        this.dao = dao;
    }

}
