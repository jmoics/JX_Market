/**
 * 
 */
package pe.com.jx_market.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;

/**
 * @author George
 * 
 */
public class PO_EAIngresaProducto
    extends Window
{
    static Log logger = LogFactory.getLog(PO_EAIngresaProducto.class);
    private Combobox cmbCateg;
    private Textbox txtNombre, txtDesc, txtMarca;
    private Decimalbox decPrec;
    private BusinessService articuloService, categoriaService;
    private DTO_Empresa empresa;

    public void onCreate()
    {
        cmbCateg = (Combobox) getFellow("cmbCateg");
        txtNombre = (Textbox) getFellow("txtNombre");
        txtDesc = (Textbox) getFellow("txtDesc");
        txtMarca = (Textbox) getFellow("txtMarca");
        decPrec = (Decimalbox) getFellow("decPrec");
        decPrec.setValue(BigDecimal.ZERO);

        articuloService = Utility.getService(this, "articuloService");
        categoriaService = Utility.getService(this, "categoriaService");

        empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");

        listarCategorias();
    }

    public void crearProducto()
    {
        if (cmbCateg.getSelectedItem() != null && !txtNombre.getValue().equals("")
                        && !txtDesc.getValue().equals("") && !txtMarca.getValue().equals("")
                        && decPrec.getValue() != null && decPrec.getValue() != BigDecimal.ZERO) {
            DTO_Articulo articulo = new DTO_Articulo();
            articulo.setCategoria(((DTO_Categoria) cmbCateg.getSelectedItem().getAttribute("categoria")).getCodigo());
            articulo.setActivo(Constantes.ST_ACTIVO);
            articulo.setDescripcion(txtDesc.getValue());
            articulo.setEmpresa(empresa.getCodigo());
            articulo.setMarca(txtMarca.getValue());
            articulo.setNombre(txtNombre.getValue());
            articulo.setPrecio(decPrec.getValue());
            DTO_Input input = new DTO_Input(articulo);
            input.setVerbo(Constantes.V_REGISTER);
            DTO_Output output = articuloService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                alertaInfo("Los datos del nuevo producto fueron guardados correctamente",
                                "Los datos del nuevo producto fueron guardados correctamente", null);
                limpiarCampos();
            } else {
                alertaError("Ocurrio un error inesperado al guardar el producto, consulte al Administrador",
                                "Ocurrio un error inesperado al guardar el producto, consulte al Administrador", null);
            }
        } else {
            alertaInfo("Debe ingresar datos en todos los campos", "No se ingreso data en todos los campos", null);
        }
    }

    public void listarCategorias()
    {
        DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        DTO_Input input = new DTO_Input(cat);
        input.setVerbo(Constantes.V_LIST);
        DTO_Output output = categoriaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "Exito al cargar categorias", null);
            List<DTO_Categoria> lstCat = (List<DTO_Categoria>) output.getLista();
            for (DTO_Categoria categ : lstCat) {
                Comboitem item = new Comboitem();
                item.setLabel(categ.getNombre());
                item.setAttribute("categoria", categ);
                cmbCateg.appendChild(item);
            }
        } else {
            alertaError("Error inesperado, por favor contacte al administrador", "Error cargando categorias", null);
        }
    }

    public void limpiarCampos()
    {
        cmbCateg.setSelectedItem(null);
        cmbCateg.setValue("");
        txtNombre.setValue("");
        txtDesc.setValue("");
        txtMarca.setValue("");
        decPrec.setValue(BigDecimal.ZERO);
    }

    public void alertaInfo(String txt,
                           String txt2,
                           Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.INFORMATION);
            if (t != null) {
                logger.info(txt2, t);
            } else {
                logger.info(txt2);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void alertaError(String txt,
                            String txt2,
                            Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.EXCLAMATION);
            if (t != null) {
                logger.error(txt2, t);
            } else {
                logger.error(txt2);
            }
        } catch (InterruptedException ex) {
        }

    }
}
