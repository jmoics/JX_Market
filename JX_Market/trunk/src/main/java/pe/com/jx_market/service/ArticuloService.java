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
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.persistence.ArticuloMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 *
 */
@Service
public class ArticuloService implements BusinessService
{
    static Log logger = LogFactory.getLog(ArticuloService.class);
    @Autowired
    private ArticuloMapper articuloMapper;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public DTO_Output execute(final DTO_Input input)
    {
        final DTO_Output output = new DTO_Output();
        if (Constantes.V_LIST.equals(input.getVerbo())) {
            output.setLista(articuloMapper.getArticulos((DTO_Articulo)input.getObject()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getVerbo())) {
            final DTO_Articulo arti = (DTO_Articulo) input.getObject();
            if(arti.getNomimg() == null) {
                /*arti.setNomimg(arti.getEmpresa() + "." + arti.getCategoria() + "." +
                                arti.getNombre().trim() + "." + generarNombreAleatorio());*/
                savePhoto(arti);
            }
            DTO_Articulo artiTmp = articuloMapper.getArticuloXCodigo(arti);
            if (artiTmp == null) {
                articuloMapper.insertArticulo(arti);
            } else {
                articuloMapper.updateArticulo(arti);
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getVerbo())) {
            final Map<String, String> map = input.getMapa();
            final DTO_Articulo art = articuloMapper.getArticuloXCodigo((DTO_Articulo) input.getObject());
            if (map == null) {
                loadPhoto(art);
            }
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_USTOCK.equals(input.getVerbo())) {
            articuloMapper.updateStock((DTO_Articulo) input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_GETIMG.equals(input.getVerbo())) {
            final DTO_Articulo art = (DTO_Articulo) input.getObject();
            loadPhoto(art);
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    private String generarNombreAleatorio() {
        final Random rnd = new Random();
        final Integer nomImg = ((int)(rnd.nextDouble() * 1000000.0));
        return nomImg.toString();
    }

    private File getPhotoFile(final DTO_Articulo art) {
    	String ruta;
    	if(System.getProperty("os.name").contains("Windows")){
    		ruta = Constantes.RUTA_IMAGENES_WINDOWS + File.separator + art.getNomimg();
    	} else{
    		ruta = Constantes.RUTA_IMAGENES + File.separator + art.getNomimg();
    	}
        return new File(ruta);
    }

    private void savePhoto(final DTO_Articulo art) {
        final File photo = getPhotoFile(art);
        if(art.getImagen() == null) {
            if(photo.exists()) {
                photo.delete();
            }
        } else {
            try {
                final BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(photo));
                bof.write(art.getImagen());
                bof.close();
            } catch(final IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void loadPhoto(final DTO_Articulo art) {
        final File photo = getPhotoFile(art);
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
            final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(photo));
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
        } catch(final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArticuloMapper getDao () {
        return articuloMapper;
    }

    public void setDao (final ArticuloMapper articuloMapper) {
        this.articuloMapper = articuloMapper;
    }

}
