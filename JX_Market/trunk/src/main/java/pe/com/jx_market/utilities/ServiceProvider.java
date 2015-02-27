package pe.com.jx_market.utilities;

import java.util.HashMap;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceProvider {
    public static final int MODE_JNDI = 1;
    public static final int MODE_JDBC = 2;
    static Log logger = LogFactory.getLog(ServiceProvider.class);
    private static ServiceProvider provider;
    private static int configurationMode = 0;
    private static Properties properties = null;
    private HashMap<String, BusinessService> serviceMap;
    private ApplicationContext context;

    /**
     * Constructor donde se inicializa el contexto de spring, en el cual ingresamos
     * los distintos beans a partir del xml applicationContext.
     * Además inicializamos la instancia del mapa de servicios que se irá llenando
     * mientras los servicios sean convocados.
     */
    private ServiceProvider () {
        logger.info("Utility - Starting Spring");
        this.serviceMap = new HashMap();
        if ((configurationMode & 0x1) == 1) {
            this.context = new ClassPathXmlApplicationContext("applicationContext-jndi.xml");
        } else if ((configurationMode & 0x2) == 2) {
            if (properties != null) {
                PropertiesFactoryBean.setProperties(properties);
                this.context = new ClassPathXmlApplicationContext(
                        "applicationContext-jdbc-prop.xml");
            } else {
                this.context = new ClassPathXmlApplicationContext("applicationContext-jdbc.xml");
            }
        } else
            throw new RuntimeException("Bad mode. Please use SetConfigurationMode() before");
    }

    /**
     * Método para obtener un servicio a partir de su nombre (id del bean), el cual
     * si no se encuentra dentro del mapa de servicios de la clase, éste será buscado 
     * dentro de los beans del contexto de spring.
     * 
     * @param name id del bean en los beans de servicios.
     * @return frontEnd el objeto que contiene el servicio.
     */
    public BusinessService getService (String name) {
        logger.debug("Retrieving service " + name + " via Spring");
        BusinessService srv = (BusinessService) this.serviceMap.get(name);
        if (srv == null) {
            srv = (BusinessService) this.context.getBean(name);
            if (srv == null) {
                throw new NullPointerException("No existe bean " + name);
            }
            BusinessFE frontEnd = new BusinessFE();
            frontEnd.setService(srv);
            frontEnd.setName(name);
            frontEnd.setReportExceptions(false);
            this.serviceMap.put(name, frontEnd);
            return frontEnd;
        }
        return srv;
    }

    /**
     * Método que inicializa el proovedor de servicios
     * .
     * @return provider, proveedor de servicios
     */
    public static ServiceProvider getServiceProvider () {
        if (provider == null) {
            provider = new ServiceProvider();
        }
        return provider;
    }

    public static int getConfigurationMode () {
        return configurationMode;
    }

    public static void setConfigurationMode (int aTestMode) {
        configurationMode = aTestMode;
    }

    public static Properties getProperties () {
        return properties;
    }

    public static void setProperties (Properties aProperties) {
        properties = aProperties;
    }
}
