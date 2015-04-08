package pe.com.jx_market.utilities;

import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class ServiceProvider
{

    static Log logger = LogFactory.getLog(ServiceProvider.class);
    private static ServiceProvider provider;
    private static int configurationMode = 0;
    private static Properties properties = null;
    private final HashMap<String, BusinessService> serviceMap;
    private final ApplicationContext context;

    /**
     * Constructor donde se inicializa el contexto de spring, en el cual
     * ingresamos los distintos beans a partir del xml applicationContext.
     * Además inicializamos la instancia del mapa de servicios que se irá
     * llenando mientras los servicios sean convocados.
     */
    private ServiceProvider(final WebApplicationContext _context)
    {
        logger.info("ContextLoader - Starting Spring");
        this.serviceMap = new HashMap<String, BusinessService>();
        /*
         * if (properties != null) {
         * PropertiesFactoryBean.setProperties(properties); context = new
         * ClassPathXmlApplicationContext( "applicationContext-jdbc-prop.xml");
         * } else {
         */
        context = _context;
        /*
         * System.out.println("test"); }
         */
    }

    private ServiceProvider()
    {
        logger.info("ContextLoader - Starting Spring");
        this.serviceMap = new HashMap<String, BusinessService>();
        if (properties != null) {
            PropertiesFactoryBean.setProperties(properties);
            context = new ClassPathXmlApplicationContext(
                            "applicationContext-jdbc-prop.xml");
        } else {
            context = new ClassPathXmlApplicationContext("applicationContext-jdbc.xml");
            System.out.println("test");
        }
    }

    /**
     * Método para obtener un servicio a partir de su nombre (id del bean), el
     * cual si no se encuentra dentro del mapa de servicios de la clase, éste
     * será buscado dentro de los beans del contexto de spring.
     *
     * @param name id del bean en los beans de servicios.
     * @return frontEnd el objeto que contiene el servicio.
     */
    public BusinessService getService(final String name)
    {
        logger.debug("Retrieving service " + name + " via Spring");
        BusinessService srv = this.serviceMap.get(name);
        if (srv == null) {
            srv = (BusinessService) this.context.getBean(name);
            if (srv == null) {
                throw new NullPointerException("No existe bean " + name);
            }
            final BusinessFE frontEnd = new BusinessFE();
            frontEnd.setService(srv);
            frontEnd.setName(name);
            frontEnd.setReportExceptions(false);
            this.serviceMap.put(name, frontEnd);
            return frontEnd;
        }
        return srv;
    }

    /**
     * Método que inicializa el proovedor de servicios .
     *
     * @return provider, proveedor de servicios
     */
    public static ServiceProvider getServiceProvider(final WebApplicationContext _context)
    {
        if (provider == null) {
            provider = new ServiceProvider(_context);
        }
        return provider;
    }

    public static ServiceProvider getServiceProvider()
    {
        if (provider == null) {
            provider = new ServiceProvider();
        }
        return provider;
    }

    public static int getConfigurationMode()
    {
        return configurationMode;
    }

    public static void setConfigurationMode(final int aTestMode)
    {
        configurationMode = aTestMode;
    }

    public static Properties getProperties()
    {
        return properties;
    }

    public static void setProperties(final Properties aProperties)
    {
        properties = aProperties;
    }
}
