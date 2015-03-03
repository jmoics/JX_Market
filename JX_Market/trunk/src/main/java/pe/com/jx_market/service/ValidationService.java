package pe.com.jx_market.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;
import pe.com.jx_market.validacion.Validador;

@Service
public class ValidationService
    implements BusinessService
{
    static Log logger = LogFactory.getLog(ValidationService.class);
    private Validador registraCliente, registraSolicitud;

    @Override
    @Transactional
    public DTO_Output execute(final DTO_Input input)
    {
        final DTO_Output output = new DTO_Output();
        Validador val = null;
        if (input.getVerbo().equals("registraCliente")) {
            val = getRegistraCliente();
        } else if (input.getVerbo().equals("registraSolicitud")) {
            val = getRegistraSolicitud();
        }

        // procesar...
        if (val == null || input.getObject() == null) {
            output.setErrorCode(Constantes.INVALID_PARAM);
            return output;
        }
        logger.info("Validando: " + input.getVerbo());
        final List<String> ans = val.valida(input.getObject());
        if (ans.size() == 0) {
            logger.info("Validacion " + input.getVerbo() + " exitosa");
            output.setErrorCode(Constantes.OK);
            return output;
        }
        logger.info("Error de validacion " + input.getVerbo());
        output.setErrorCode(Constantes.VALIDATION_ERROR);
        output.setLista(ans);
        return output;
    }

    public Validador getRegistraCliente()
    {
        return registraCliente;
    }

    public void setRegistraCliente(final Validador registraCliente)
    {
        this.registraCliente = registraCliente;
    }

    public Validador getRegistraSolicitud()
    {
        return registraSolicitud;
    }

    public void setRegistraSolicitud(final Validador registraSolicitud)
    {
        this.registraSolicitud = registraSolicitud;
    }

}
