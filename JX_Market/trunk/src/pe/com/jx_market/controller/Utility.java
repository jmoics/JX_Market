package pe.com.jx_market.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.ServiceProvider;

/**
 * Application Lifecycle Listener implementation class UtilityStarter
 *
 */
public class Utility implements ServletContextListener {

    public static final String appAttrName = "pe.com.jx-market";
    static Log logger = LogFactory.getLog(Utility.class);

    /**
     * Default constructor.
     */
    public Utility () {
    }

    // public static void saltar(Window w, String destino){
    public static void saltar (final Window w, final String destino) {
        w.getDesktop().setBookmark(destino);
        ((Include) w.getDesktop().getPage("menup").getFellow("inc"))
                .setSrc(destino);
    }

    public static void saltar (final Borderlayout w, final String destino) {
        w.getDesktop().setBookmark(destino);
        ((Include) w.getDesktop().getPage("menup").getFellow("inc"))
                .setSrc(destino);
    }

    public static void saltar (final Div w, final String destino) {
        w.getDesktop().getPage("menup").getFellows();
        w.getDesktop().setBookmark(destino);
        ((Include) w.getDesktop().getPage("menup").getFellow("inc"))
                .setSrc(destino);
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized (final ServletContextEvent sce) {
        ServiceProvider.setConfigurationMode(ServiceProvider.MODE_JDBC);
        final ServiceProvider provider = ServiceProvider.getServiceProvider();
        if (provider == null) {
            throw new RuntimeException("Can't start ServiceProvider");
        }
        sce.getServletContext().setAttribute(appAttrName, provider);

    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed (final ServletContextEvent sce) {

    }

    /**
     * Obtiene un servicio de la capa de negocios
     *
     * @param w
     *            La ventana actual
     * @param name
     *            El nombre del servicio
     * @return El servicio
     */
    public static BusinessService getService (final Window w, final String name) {
        final ServiceProvider p = (ServiceProvider) w.getDesktop().getWebApp()
                .getAttribute(appAttrName);
        return p.getService(name);
    }

    public static BusinessService getService (final Borderlayout w, final String name) {
        final ServiceProvider p = (ServiceProvider) w.getDesktop().getWebApp()
                .getAttribute(appAttrName);
        return p.getService(name);
    }

    public static BusinessService getService (final Div w, final String name) {
        final ServiceProvider p = (ServiceProvider) w.getDesktop().getWebApp()
                .getAttribute(appAttrName);
        return p.getService(name);
    }

    public static BusinessService getService (
            final javax.servlet.ServletContext context, final String name) {
        final ServiceProvider p = (ServiceProvider) context.getAttribute(appAttrName);
        return p.getService(name);
    }

}
