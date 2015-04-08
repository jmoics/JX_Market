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
import pe.com.jx_market.persistence.ProductMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@Service
public class ProductService implements BusinessService<DTO_Articulo>
{
    static Log logger = LogFactory.getLog(ProductService.class);
    @Autowired
    private ProductMapper articuloMapper;

    @Override
    @Transactional
    public ServiceOutput<DTO_Articulo> execute(final ServiceInput<DTO_Articulo> input)
    {
        final ServiceOutput<DTO_Articulo> output = new ServiceOutput<DTO_Articulo>();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            output.setLista(articuloMapper.getArticulos(input.getMapa()));
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            final DTO_Articulo arti = input.getObject();
            if(arti.getNomimg() == null) {
                arti.setNomimg(arti.getEmpresa() + "." + arti.getProductName().trim() + "." + generarNombreAleatorio());
                savePhoto(arti);
            }
            final DTO_Articulo artiTmp = articuloMapper.getArticuloXCodigo(arti);
            if (artiTmp == null) {
                articuloMapper.insertArticulo(arti);
            } else {
                articuloMapper.updateArticulo(arti);
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            final Map<?, ?> map = input.getMapa();
            final DTO_Articulo art = articuloMapper.getArticuloXCodigo(input.getObject());
            if (map == null) {
                loadPhoto(art);
            }
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_USTOCK.equals(input.getAccion())) {
            articuloMapper.updateStock(input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        }else if (Constantes.V_GETIMG.equals(input.getAccion())) {
            final DTO_Articulo art = input.getObject();
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

    public ProductMapper getDao () {
        return articuloMapper;
    }

    public void setDao (final ProductMapper articuloMapper) {
        this.articuloMapper = articuloMapper;
    }

}
