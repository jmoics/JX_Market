package pe.com.jx_market.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.zkoss.zk.ui.Desktop;
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
public class ContextLoader
    extends ContextLoaderListener
    implements ServletContextListener
{
    public static final String appAttrName = "pe.com.jx-market";
    static Log logger = LogFactory.getLog(ContextLoader.class);

    /**
     * Default constructor.
     */
    public ContextLoader()
    {
    }

    // public static void saltar(Window w, String destino){
    public static void saltar(final Window w,
                              final String destino)
    {
        w.getDesktop().setBookmark(destino);
        ((Include) w.getDesktop().getPage("menup").getFellow("inc"))
                        .setSrc(destino);
    }

    public static void saltar(final Borderlayout w,
                              final String destino)
    {
        w.getDesktop().setBookmark(destino);
        ((Include) w.getDesktop().getPage("menup").getFellow("inc"))
                        .setSrc(destino);
    }

    public static void saltar(final Div w,
                              final String destino)
    {
        w.getDesktop().getPage("menup").getFellows();
        w.getDesktop().setBookmark(destino);
        ((Include) w.getDesktop().getPage("menup").getFellow("inc"))
                        .setSrc(destino);
    }

    public static void saltar(final Desktop w,
                              final String destino)
    {
        w.getPage("menup").getFellows();
        w.setBookmark(destino);
        ((Include) w.getPage("menup").getFellow("inc"))
                        .setSrc(destino);
    }

    public static void recargar(final Desktop w,
                                final String destino)
    {
        w.getPage("menup").getFellows();
        w.setBookmark(destino);
        ((Include) w.getPage("menup").getFellow("inc"))
                        .setSrc(null);
        ((Include) w.getPage("menup").getFellow("inc"))
                        .setSrc(destino);
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent event)
    {
        final WebApplicationContext context = initWebApplicationContext(event.getServletContext());

        final ServiceProvider provider = ServiceProvider.getServiceProvider(context);
        if (provider == null) {
            throw new RuntimeException("Can't start ServiceProvider");
        }
        event.getServletContext().setAttribute(appAttrName, provider);

    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(final ServletContextEvent sce)
    {
        super.contextDestroyed(sce);
    }

    /**
     * Obtiene un servicio de la capa de negocios, si el servicio no se
     * encuentra en el mapa por se la primera vez, busca en los beans dentro del
     * contexto, el cual fue inicializado a partir del
     * applicationContext-jdbc.xml.
     *
     * @param w La ventana actual
     * @param name El nombre del servicio
     * @return El servicio
     */
    public static BusinessService<?> getService(final Window w,
                                                final String name)
    {
        final ServiceProvider p = (ServiceProvider) w.getDesktop().getWebApp()
                        .getAttribute(appAttrName);
        return p.getService(name);
    }

    public static BusinessService<?> getService(final Borderlayout w,
                                                final String name)
    {
        final ServiceProvider p = (ServiceProvider) w.getDesktop().getWebApp()
                        .getAttribute(appAttrName);
        return p.getService(name);
    }

    public static BusinessService<?> getService(final Div w,
                                                final String name)
    {
        final ServiceProvider p = (ServiceProvider) w.getDesktop().getWebApp()
                        .getAttribute(appAttrName);
        return p.getService(name);
    }

    public static BusinessService<?> getService(final Desktop w,
                                                final String name)
    {
        final ServiceProvider p = (ServiceProvider) w.getWebApp()
                        .getAttribute(appAttrName);
        return p.getService(name);
    }

    public static BusinessService<?> getService(final javax.servlet.ServletContext context,
                                                final String name)
    {
        final ServiceProvider p = (ServiceProvider) context.getAttribute(appAttrName);
        return p.getService(name);
    }
}
