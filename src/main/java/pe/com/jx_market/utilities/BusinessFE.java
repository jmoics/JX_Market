package pe.com.jx_market.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jcuevas
 *
 * @param <T>
 */
public class BusinessFE<T>
    implements BusinessService<T>
{

    /**
     *
     */
    private BusinessService<T> service;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private boolean reportExceptions;
    /**
     *
     */
    private final Log logger = LogFactory.getLog(BusinessFE.class);

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    public ServiceOutput<T> execute(final ServiceInput<T> _e)
    {
        ServiceOutput<T> output;
        try {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Inicio ejecucion bean " + this.name);
            }
            output = this.service.execute(_e);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Fin de ejecucion bean " + this.name);
            }
        } catch (final RuntimeException ex) {
            this.logger.warn("Excepcion generada por bean " + this.name, ex);
            output = new ServiceOutput<T>();
            if (this.reportExceptions) {
                output.setError(-1, ex.getMessage());
            } else {
                output.setError(-1, "Hubo un problema en la aplicacion.");
            }
        }
        return output;
    }

    /**
     * @return service.
     */
    public BusinessService<T> getService()
    {
        return this.service;
    }

    /**
     * @param _service set Service
     */
    public void setService(final BusinessService<T> _service)
    {
        this.service = _service;
    }

    /**
     * @return name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param _name Name.
     */
    public void setName(final String _name)
    {
        this.name = _name;
    }

    /**
     * @return reportExceptions.
     */
    public boolean isReportExceptions()
    {
        return this.reportExceptions;
    }

    /**
     * @param _reportExceptions reportExceptions
     */
    public void setReportExceptions(final boolean _reportExceptions)
    {
        this.reportExceptions = _reportExceptions;
    }
}
