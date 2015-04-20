/**
 *
 */
package pe.com.jx_market.testing;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;
import pe.com.jx_market.utilities.ServiceProvider;


/**
 * @author George
 *
 */
public class ProductServiceTest
{
    public static BusinessService productService;

    @BeforeClass
    public static void setUpBeforeClass () {
        productService = ServiceProvider.getServiceProvider().getService("productService");
    }

    @Test
    public void registraProduct()
    {
        final DTO_Product art = new DTO_Product();
        //art.setCategory(1);
        art.setActive(Constantes.STB_ACTIVO);
        art.setProductDescription("juego de video de accion basado en la trilogia del senior de los anillos");
        art.setProductName("Ring Action");
        art.setCompanyId(2);
        //art.setTradeMark("Capcom");
        //art.setPrecio(new BigDecimal(24.5));
        //art.setStock(20);

        final ServiceInput input = new ServiceInput(art);
        input.setAccion(Constantes.V_REGISTER);
        final ServiceOutput output = productService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());
    }

    @Test
    public void getProduct() {
        DTO_Product art = new DTO_Product();
        art.setId(1);
        art.setCompanyId(2);
        final ServiceInput input = new ServiceInput(art);
        input.setAccion(Constantes.V_GET);
        final ServiceOutput output = productService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());

        art = (DTO_Product)output.getObject();
        //assertEquals(new BigDecimal(24.50), art.getPrecio());
        assertEquals("Ring Action", art.getProductName());
    }
}
