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

/**
 * @author jcuevas
 *
 */
@Service
public class ValidationService
    implements BusinessService
{
    /**
     *
     */
    private final Log logger = LogFactory.getLog(ValidationService.class);
    /**
     *
     */
    private Validador registraClient;
    /**
     *
     */
    private Validador registraSolicitud;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    @Transactional
    public ServiceOutput execute(final ServiceInput _input)
    {
        final ServiceOutput output = new ServiceOutput();
        Validador val = null;
        if (_input.getAction().equals("registraClient")) {
            val = getRegistraClient();
        } else if (_input.getAction().equals("registraSolicitud")) {
            val = getRegistraSolicitud();
        }

        // procesar...
        if (val == null || _input.getObject() == null) {
            output.setErrorCode(Constantes.INVALID_PARAM);
        } else {
            this.logger.info("Validando: " + _input.getAction());
            final List<String> ans = val.valida(_input.getObject());
            if (ans.size() == 0) {
                this.logger.info("Validacion " + _input.getAction() + " exitosa");
                output.setErrorCode(Constantes.OK);
            } else {
                this.logger.info("Error de validacion " + _input.getAction());
                output.setErrorCode(Constantes.VALIDATION_ERROR);
                output.setLista(ans);
            }
        }
        return output;
    }

    /**
     * @return registraClient
     */
    public Validador getRegistraClient()
    {
        return this.registraClient;
    }

    /**
     * @param _registraClient client.
     */
    public void setRegistraClient(final Validador _registraClient)
    {
        this.registraClient = _registraClient;
    }

    /**
     * @return registraClient
     */
    public Validador getRegistraSolicitud()
    {
        return this.registraSolicitud;
    }

    /**
     * @param _registraSolicitud client.
     */
    public void setRegistraSolicitud(final Validador _registraSolicitud)
    {
        this.registraSolicitud = _registraSolicitud;
    }

}
