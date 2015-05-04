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
    private Validador registraClient, registraSolicitud;

    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput input)
    {
        final ServiceOutput output = new ServiceOutput();
        Validador val = null;
        if (input.getAction().equals("registraClient")) {
            val = getRegistraClient();
        } else if (input.getAction().equals("registraSolicitud")) {
            val = getRegistraSolicitud();
        }

        // procesar...
        if (val == null || input.getObject() == null) {
            output.setErrorCode(Constantes.INVALID_PARAM);
            return output;
        }
        logger.info("Validando: " + input.getAction());
        final List<String> ans = val.valida(input.getObject());
        if (ans.size() == 0) {
            logger.info("Validacion " + input.getAction() + " exitosa");
            output.setErrorCode(Constantes.OK);
            return output;
        }
        logger.info("Error de validacion " + input.getAction());
        output.setErrorCode(Constantes.VALIDATION_ERROR);
        output.setLista(ans);
        return output;
    }

    public Validador getRegistraClient()
    {
        return registraClient;
    }

    public void setRegistraClient(final Validador registraClient)
    {
        this.registraClient = registraClient;
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
