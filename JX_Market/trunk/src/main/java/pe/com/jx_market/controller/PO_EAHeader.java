
package pe.com.jx_market.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Empleado;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.utilities.Constantes;

/**
 * @author <._.>
 *
 */
public class PO_EAHeader extends SelectorComposer<Window>
{
    static Log logger = LogFactory.getLog(PO_EAHeader.class);
    @WireVariable
    private Desktop desktop;
    @Wire
    private Label userdata;
    @Wire
    private Image imaLogo;
    private byte[] imgLogoByte;
    private DTO_Empresa empresa;

    @Override
    public void doAfterCompose(final Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);
        empresa = (DTO_Empresa) comp.getDesktop().getSession().getAttribute("empresa");
        loadPhoto(empresa.getDominio());
        setGraficoFoto();

        final DTO_Empleado empleado = (DTO_Empleado) desktop.getSession().getAttribute("empleado");
        if (empleado == null) {
            throw new RuntimeException("La sesion se perdio, vuelva a ingresar por favor");
        }
        userdata.setValue(empleado.getApellido() + " " + empleado.getNombre());
    }

    private void setGraficoFoto()
    {
        if (imgLogoByte != null) {
            try {
                imaLogo.setContent(new AImage("foto", imgLogoByte));
                return;
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        imaLogo.setSrc("/media/imagProd.gif");
    }

    private void loadPhoto(final String name) {
        final File photo = getPhotoFile(name);
        if(!photo.exists()) {
            if(logger.isDebugEnabled()) {
                logger.debug("No existe archivo de foto " + photo.getName());
            }
            imgLogoByte = null;
            return;
        }
        if(logger.isDebugEnabled()) {
            logger.debug("Existe archivo de foto " + photo.getName());
        }
        try {
            final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(photo));
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int n;
            while((n = bis.read()) != -1) {
                baos.write(n);
            }
            bis.close();
            baos.close();
            imgLogoByte = baos.toByteArray();
            if(logger.isDebugEnabled()) {
                logger.debug("Cargamos bytes en foto "+ imgLogoByte.length);
            }
        } catch(final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private File getPhotoFile(final String name) {
        String ruta;
        if(System.getProperty("os.name").contains("Windows")){
            ruta = Constantes.RUTA_IMAGENES_WINDOWS + File.separator + name;
        } else{
            ruta = Constantes.RUTA_IMAGENES + File.separator + name;
        }
        return new File(ruta);
    }

    @Listen("onClick=#btnSalir")
    public void salir()
    {
        desktop.getSession().removeAttribute("login");
        desktop.getSession().removeAttribute("actualizar");
        desktop.getSession().removeAttribute("empresa");
        desktop.getSession().removeAttribute("empleado");
        // getDesktop().getSession().removeAttribute("paginaActual");
        Executions.sendRedirect("eALogin.zul");
    }
}
