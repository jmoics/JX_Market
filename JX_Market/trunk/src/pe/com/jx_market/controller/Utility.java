package pe.com.jx_market.controller;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;
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
    public Utility() {
    }
	
	//public static void saltar(Window w, String destino){
	public static void saltar(Window w, String destino){
		w.getDesktop().setBookmark(destino);
		((Include)w.getDesktop().getPage("menup").getFellow("inc")).setSrc(destino);
	}

	public static void saltar(Borderlayout w, String destino) {
		w.getDesktop().setBookmark(destino);
		((Include)w.getDesktop().getPage("menup").getFellow("inc")).setSrc(destino);
	}
	
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	ServiceProvider.setConfigurationMode(ServiceProvider.MODE_JDBC);
        ServiceProvider provider = ServiceProvider.getServiceProvider();
        if(provider == null) {
            throw new RuntimeException("Can't start ServiceProvider");
        }
        sce.getServletContext().setAttribute(appAttrName, provider);
        
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {

    }
    
    /**
     * Obtiene un servicio de la capa de negocios 
     * @param w La ventana actual
     * @param name El nombre del servicio
     * @return El servicio
     */
	public static BusinessService getService(Window w, String name) {
    	ServiceProvider p = (ServiceProvider)w.getDesktop().getWebApp().getAttribute(appAttrName);
    	return p.getService(name);
	}
	
	public static BusinessService getService(Borderlayout w, String name) {
    	ServiceProvider p = (ServiceProvider)w.getDesktop().getWebApp().getAttribute(appAttrName);
    	return p.getService(name);
	}    
    public static BusinessService getService(
            javax.servlet.ServletContext context, String name) {
    	ServiceProvider p = (ServiceProvider) context.getAttribute(appAttrName);
    	return p.getService(name);
    }

}
