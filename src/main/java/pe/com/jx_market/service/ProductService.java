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
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Categoria2Articulo;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.domain.DTO_ProductImage;
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
public class ProductService
    implements BusinessService<DTO_Product>
{

    static Log logger = LogFactory.getLog(ProductService.class);
    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public ServiceOutput<DTO_Product> execute(final ServiceInput<DTO_Product> input)
    {
        final ServiceOutput<DTO_Product> output = new ServiceOutput<DTO_Product>();
        if (Constantes.V_LIST.equals(input.getAccion())) {
            final List<DTO_Product> lstProds = productMapper.getArticulos(input.getMapa());
            for (final DTO_Product prod : lstProds) {
                for (final DTO_ProductImage img : prod.getImages()) {
                    loadPhoto(img);
                }
            }
            output.setLista(lstProds);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTER.equals(input.getAccion())) {
            final DTO_Product prod = input.getObject();
            final DTO_Product prodTmp = productMapper.getArticuloXCodigo(prod);
            if (prodTmp == null) {
                productMapper.insertArticulo(prod);
            } else {
                productMapper.updateArticulo(prod);
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GET.equals(input.getAccion())) {
            final Map<?, ?> map = input.getMapa();
            final DTO_Product prod = productMapper.getArticuloXCodigo(input.getObject());
            if (map == null) {
                for (final DTO_ProductImage img : prod.getImages()) {
                    loadPhoto(img);
                }
            }
            output.setObject(prod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_USTOCK.equals(input.getAccion())) {
            productMapper.updateStock(input.getObject());
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_GETIMG.equals(input.getAccion())) {
            final DTO_Product prod = input.getObject();
            // loadPhoto(art);
            output.setObject(prod);
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTERCAT4PROD.equals(input.getAccion())) {
            final DTO_Product prod = input.getObject();
            for (final DTO_Categoria categ : prod.getCategories()) {
                final DTO_Categoria2Articulo cat2Prod = new DTO_Categoria2Articulo();
                cat2Prod.setProductId(prod.getId());
                cat2Prod.setCategoryId(categ.getId());
                final int count = productMapper.getCategories4Product(cat2Prod);
                if (count == 0) {
                    productMapper.insertCategory4Product(cat2Prod);
                }
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_DELETECAT4PROD.equals(input.getAccion())) {
            final DTO_Product arti = input.getObject();
            for (final DTO_Categoria categ : arti.getCategories()) {
                final DTO_Categoria2Articulo cat2Prod = new DTO_Categoria2Articulo();
                cat2Prod.setProductId(arti.getId());
                cat2Prod.setCategoryId(categ.getId());
                final int count = productMapper.getCategories4Product(cat2Prod);
                if (count > 0) {
                    productMapper.deleteCategory4Product(cat2Prod);
                }
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else if (Constantes.V_REGISTERIMG4PROD.equals(input.getAccion())) {
            final DTO_Product arti = input.getObject();
            for (final DTO_ProductImage img : arti.getImages()) {
                // if(img.getImageName() == null) {
                img.setImageName(arti.getEmpresa() + "." + arti.getId() + "." + generarNombreAleatorio());
                savePhoto(img);
                // }
                if (img.getId() == null) {
                    productMapper.insertImage4Product(img);
                } else {
                    productMapper.updateImage4Product(img);
                }
            }
            output.setErrorCode(Constantes.OK);
            return output;
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
    }

    private String generarNombreAleatorio()
    {
        final Random rnd = new Random();
        final Integer nomImg = ((int) (rnd.nextDouble() * 1000000.0));
        return nomImg.toString();
    }

    private File getPhotoFile(final DTO_ProductImage _image)
    {
        String ruta;
        if (System.getProperty("os.name").contains("Windows")) {
            ruta = Constantes.RUTA_IMAGENES_WINDOWS + File.separator + _image.getImageName();
        } else {
            ruta = Constantes.RUTA_IMAGENES + File.separator + _image.getImageName();
        }
        return new File(ruta);
    }

    private void savePhoto(final DTO_ProductImage _image)
    {
        final File photo = getPhotoFile(_image);
        if (_image.getImage() == null) {
            if (photo.exists()) {
                photo.delete();
            }
        } else {
            try {
                final BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(photo));
                bof.write(_image.getImage());
                bof.close();
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void loadPhoto(final DTO_ProductImage _image)
    {
        final File photo = getPhotoFile(_image);
        if (!photo.exists()) {
            if (logger.isDebugEnabled()) {
                logger.debug("No existe archivo de foto " + photo.getName());
            }
            _image.setImage(null);
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Existe archivo de foto " + photo.getName());
        }
        try {
            final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(photo));
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int n;
            while ((n = bis.read()) != -1) {
                baos.write(n);
            }
            bis.close();
            baos.close();
            _image.setImage(baos.toByteArray());
            if (logger.isDebugEnabled()) {
                logger.debug("Cargamos bytes en foto " + _image.getImage().length);
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ProductMapper getDao()
    {
        return productMapper;
    }

    public void setDao(final ProductMapper _productMapper)
    {
        this.productMapper = _productMapper;
    }

}
