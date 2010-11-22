/**
 * 
 */
package pe.com.jx_market.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.jx_market.dao.ArticuloDAO;
import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 *
 */
public class ArticuloService implements BusinessService
{
    static Log logger = LogFactory.getLog(ArticuloService.class);
    private ArticuloDAO dao;
    @Override
    public DTO_Output execute(DTO_Input input)
    {
        DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(dao.getArticulos((DTO_Articulo)input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            DTO_Articulo arti = (DTO_Articulo) input.getObject();
            if(arti.getNomimg() == null) {
                arti.setNomimg(arti.getEmpresa() + "." + arti.getCategoria() + "." + 
                                arti.getNombre().trim() + "." + generarNombreAleatorio());
                savePhoto(arti);
            }
            dao.insertArticulo(arti);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            DTO_Articulo art = dao.getArticuloXCodigo((DTO_Articulo) input.getObject());
            loadPhoto(art);
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_USTOCK.equals(input.getVerbo())) {
            dao.insertStock((DTO_Articulo) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_GETIMG.equals(input.getVerbo())) {
            DTO_Articulo art = (DTO_Articulo) input.getObject();
            loadPhoto(art);
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }
    
    private String generarNombreAleatorio() {
        Random rnd = new Random();
        Integer nomImg = ((int)(rnd.nextDouble() * 1000000.0));
        return nomImg.toString();
    }
    
    private File getPhotoFile(DTO_Articulo art) {
        String ruta = Constantes.RUTA_IMAGENES + File.separator + art.getNomimg();
        return new File(ruta);
    }
    
    private void savePhoto(DTO_Articulo art) {
        File photo = getPhotoFile(art);
        if(art.getImagen() == null) {
            if(photo.exists()) {
                photo.delete();
            }
        } else {
            try {
                BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(photo));
                bof.write(art.getImagen());
                bof.close();
            } catch(IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    private void loadPhoto(DTO_Articulo art) {
        File photo = getPhotoFile(art);
        if(!photo.exists()) {
            if(logger.isDebugEnabled()) {
                logger.debug("No existe archivo de foto " + photo.getName());
            }
            art.setImagen(null);
            return;
        }
        if(logger.isDebugEnabled()) {
            logger.debug("Existe archivo de foto " + photo.getName());
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(photo));
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
            int n;
            while((n = bis.read()) != -1) {
                baos.write(n);
            }
            bis.close();
            baos.close();
            art.setImagen(baos.toByteArray());
            if(logger.isDebugEnabled()) {
                logger.debug("Cargamos bytes en foto "+ art.getImagen().length);
            }
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public ArticuloDAO getDao () {
        return dao;
    }

    public void setDao (ArticuloDAO dao) {
        this.dao = dao;
    }

}
