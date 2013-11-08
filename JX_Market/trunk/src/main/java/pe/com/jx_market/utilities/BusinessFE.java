package pe.com.jx_market.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BusinessFE implements BusinessService {
    private BusinessService service;
    private String name;
    private boolean reportExceptions;
    static Log logger = LogFactory.getLog(BusinessFE.class);

    @Override
    public DTO_Output execute (final DTO_Input e) {
        DTO_Output output;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Inicio ejecucion bean " + this.name);
            }
            output = this.service.execute(e);
            if (logger.isDebugEnabled()) {
                logger.debug("Fin de ejecucion bean " + this.name);
            }
            return output;
        } catch (final RuntimeException ex) {
            logger.warn("Excepcion generada por bean " + this.name, ex);
            output = new DTO_Output();
            if (this.reportExceptions) {
                output.setError(-1, ex.getMessage());
            } else {
                output.setError(-1, "Hubo un problema en la aplicacion.");
            }
        }
        return output;
    }

    public BusinessService getService () {
        return this.service;
    }

    public void setService (final BusinessService service) {
        this.service = service;
    }

    public String getName () {
        return this.name;
    }

    public void setName (final String name) {
        this.name = name;
    }

    public boolean isReportExceptions () {
        return this.reportExceptions;
    }

    public void setReportExceptions (final boolean reportExceptions) {
        this.reportExceptions = reportExceptions;
    }
}