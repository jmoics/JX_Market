package pe.com.jx_market.controller;

import org.zkoss.zul.Listbox;

import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.ServiceProvider;

public class PO_EESolicitudesPendientes extends SecuredWindow 
{
	private BusinessService solicitudService;
	private Listbox lstSolicitud;

	@Override
	void realOnCreate() 
	{		
		lstSolicitud = (Listbox) getFellow("lstSolicitud");
		solicitudService = ServiceProvider.getServiceProvider().getService("solicitudService");
		busquedaSolicitudes();
	}
	
	public void onLimpiar()
	{
		lstSolicitud.setVisible(false);
		lstSolicitud.getItems().clear();		
	}
	
	public void busquedaSolicitudes()
	{
		lstSolicitud.getItems().clear();
		DTO_Input input = new DTO_Input();
		
		
		
	}
	

	@Override
	String[] requiredResources() {
		// TODO Auto-generated method stub
		return null;
	}

}
