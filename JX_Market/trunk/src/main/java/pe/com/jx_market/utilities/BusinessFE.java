package pe.com.jx_market.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BusinessFE<T> implements BusinessService<T> {
    private BusinessService<T> service;
    private String name;
    private boolean reportExceptions;
    static Log logger = LogFactory.getLog(BusinessFE.class);

    @Override
    public ServiceOutput<T> execute (final ServiceInput<T> e) {
        ServiceOutput<T> output;
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
            output = new ServiceOutput<T>();
            if (this.reportExceptions) {
                output.setError(-1, ex.getMessage());
            } else {
                output.setError(-1, "Hubo un problema en la aplicacion.");
            }
        }
        return output;
    }

    public BusinessService<T> getService () {
        return this.service;
    }

    public void setService (final BusinessService<T> service) {
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