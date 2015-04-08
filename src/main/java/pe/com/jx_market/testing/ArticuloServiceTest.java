/**
 *
 */
package pe.com.jx_market.testing;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;
import pe.com.jx_market.utilities.ServiceProvider;


/**
 * @author George
 *
 */
public class ArticuloServiceTest
{
    public static BusinessService articuloService;

    @BeforeClass
    public static void setUpBeforeClass () {
        articuloService = ServiceProvider.getServiceProvider().getService("articuloService");
    }

    @Test
    public void registraArticulo()
    {
        final DTO_Articulo art = new DTO_Articulo();
        //art.setCategoria(1);
        art.setActivo(Constantes.STB_ACTIVO);
        art.setProductDescription("juego de video de accion basado en la trilogia del senior de los anillos");
        art.setProductName("Ring Action");
        art.setEmpresa(2);
        //art.setMarca("Capcom");
        //art.setPrecio(new BigDecimal(24.5));
        //art.setStock(20);

        final ServiceInput input = new ServiceInput(art);
        input.setAccion(Constantes.V_REGISTER);
        final ServiceOutput output = articuloService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());
    }

    @Test
    public void getArticulo() {
        DTO_Articulo art = new DTO_Articulo();
        art.setId(1);
        art.setEmpresa(2);
        final ServiceInput input = new ServiceInput(art);
        input.setAccion(Constantes.V_GET);
        final ServiceOutput output = articuloService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());

        art = (DTO_Articulo)output.getObject();
        //assertEquals(new BigDecimal(24.50), art.getPrecio());
        assertEquals("Ring Action", art.getProductName());
    }
}
