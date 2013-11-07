/**
 * 
 */
package pe.com.jx_market.testing;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;
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
        ServiceProvider.setConfigurationMode(ServiceProvider.MODE_JDBC);
        articuloService = ServiceProvider.getServiceProvider().getService("articuloService");
    }
    
    @Test
    public void registraArticulo()
    {
        DTO_Articulo art = new DTO_Articulo();
        art.setCategoria(1);
        art.setActivo(Constantes.ST_ACTIVO);
        art.setDescripcion("juego de video de accion basado en la trilogia del senior de los anillos");
        art.setNombre("Ring Action");
        art.setEmpresa(2);
        art.setMarca("Capcom");
        art.setPrecio(new BigDecimal(24.5));
        art.setStock(20);
        
        DTO_Input input = new DTO_Input(art);
        input.setVerbo(Constantes.V_REGISTER);
        DTO_Output output = articuloService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());
    }
    
    @Test
    public void getArticulo() {
        DTO_Articulo art = new DTO_Articulo();
        art.setCodigo(1);
        art.setEmpresa(2);
        DTO_Input input = new DTO_Input(art);
        input.setVerbo(Constantes.V_GET);
        DTO_Output output = articuloService.execute(input);
        assertEquals(Constantes.OK, output.getErrorCode());
        
        art = (DTO_Articulo)output.getObject();
        assertEquals(new BigDecimal(24.50), art.getPrecio());
        assertEquals("Ring Action", art.getNombre());
    }
}
