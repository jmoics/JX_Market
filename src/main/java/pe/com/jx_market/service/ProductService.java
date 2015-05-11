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

import pe.com.jx_market.domain.DTO_Category;
import pe.com.jx_market.domain.DTO_Category2Product;
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

    /**
     *
     */
    private final Log logger = LogFactory.getLog(ProductService.class);
    /**
     *
     */
    @Autowired
    private ProductMapper productMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    @Transactional
    public ServiceOutput<DTO_Product> execute(final ServiceInput<DTO_Product> _input)
    {
        final ServiceOutput<DTO_Product> output = new ServiceOutput<DTO_Product>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            final List<DTO_Product> lstProds = this.productMapper.getProducts(_input.getMapa());
            for (final DTO_Product prod : lstProds) {
                for (final DTO_ProductImage img : prod.getImages()) {
                    loadPhoto(img);
                }
            }
            output.setLista(lstProds);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final DTO_Product prod = _input.getObject();
            final DTO_Product prodTmp = this.productMapper.getProduct4Id(prod);
            if (prodTmp == null) {
                this.productMapper.insertProduct(prod);
            } else {
                this.productMapper.updateProduct(prod);
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final Map<?, ?> map = _input.getMapa();
            final DTO_Product prod = this.productMapper.getProduct4Id(_input.getObject());
            if (map == null) {
                for (final DTO_ProductImage img : prod.getImages()) {
                    loadPhoto(img);
                }
            }
            output.setObject(prod);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_USTOCK.equals(_input.getAction())) {
            this.productMapper.updateStock(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GETIMG.equals(_input.getAction())) {
            final DTO_Product prod = _input.getObject();
            // loadPhoto(art);
            output.setObject(prod);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTERCAT4PROD.equals(_input.getAction())) {
            final DTO_Product prod = _input.getObject();
            for (final DTO_Category categ : prod.getCategories()) {
                final DTO_Category2Product cat2Prod = new DTO_Category2Product();
                cat2Prod.setProductId(prod.getId());
                cat2Prod.setCategoryId(categ.getId());
                final int count = this.productMapper.getCategories4Product(cat2Prod);
                if (count == 0) {
                    this.productMapper.insertCategory4Product(cat2Prod);
                }
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_DELETECAT4PROD.equals(_input.getAction())) {
            final DTO_Product prod = _input.getObject();
            for (final DTO_Category categ : prod.getCategories()) {
                final DTO_Category2Product cat2Prod = new DTO_Category2Product();
                cat2Prod.setProductId(prod.getId());
                cat2Prod.setCategoryId(categ.getId());
                final int count = this.productMapper.getCategories4Product(cat2Prod);
                if (count > 0) {
                    this.productMapper.deleteCategory4Product(cat2Prod);
                }
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTERIMG4PROD.equals(_input.getAction())) {
            final DTO_Product prod = _input.getObject();
            for (final DTO_ProductImage img : prod.getImages()) {
                // if(img.getImageName() == null) {
                img.setImageName(prod.getCompanyId() + "." + prod.getId() + "." + generarNombreAleatorio());
                savePhoto(img);
                // }
                if (img.getId() == null) {
                    this.productMapper.insertImage4Product(img);
                } else {
                    this.productMapper.updateImage4Product(img);
                }
            }
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }

    /**
     * @return random name.
     */
    private String generarNombreAleatorio()
    {
        final Random rnd = new Random();
        final Integer nomImg = (int) (rnd.nextDouble() * 1000000.0);
        return nomImg.toString();
    }

    /**
     * @param _image entity for images.
     * @return File with path.
     */
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

    /**
     * @param _image entity for images.
     */
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

    /**
     * @param _image entity for images.
     */
    private void loadPhoto(final DTO_ProductImage _image)
    {
        final File photo = getPhotoFile(_image);
        if (!photo.exists()) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("No existe archivo de foto " + photo.getName());
            }
            _image.setImage(null);
            return;
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Existe archivo de foto " + photo.getName());
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
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Cargamos bytes en foto " + _image.getImage().length);
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
