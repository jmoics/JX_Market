package pe.com.jx_market.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;
import pe.com.jx_market.validacion.Validador;

@Service
public class ValidationService
    implements BusinessService
{
    static Log logger = LogFactory.getLog(ValidationService.class);
    private Validador registraCliente, registraSolicitud;

    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input)
    {
        final ServiceOutput output = new ServiceOutput();
        Validador val = null;
        if (input.getAccion().equals("registraCliente")) {
            val = getRegistraCliente();
        } else if (input.getAccion().equals("registraSolicitud")) {
            val = getRegistraSolicitud();
        }

        // procesar...
        if (val == null || input.getObject() == null) {
            output.setErrorCode(Constantes.INVALID_PARAM);
            return output;
        }
        logger.info("Validando: " + input.getAccion());
        final List<String> ans = val.valida(input.getObject());
        if (ans.size() == 0) {
            logger.info("Validacion " + input.getAccion() + " exitosa");
            output.setErrorCode(Constantes.OK);
            return output;
        }
        logger.info("Error de validacion " + input.getAccion());
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
