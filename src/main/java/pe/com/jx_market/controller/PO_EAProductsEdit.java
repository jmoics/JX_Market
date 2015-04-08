/**
 *
 */
package pe.com.jx_market.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
public class PO_EAProductsEdit
    extends SecuredComposer<Window>
{

    static Log logger = LogFactory.getLog(PO_EAProductsEdit.class);
    @Wire
    private Combobox cmbCateg, cmbStatus;
    @Wire
    private Textbox txtNombre, txtDesc, txtMarca;
    @Wire
    private Decimalbox decPrec;
    @Wire
    private Image imgFoto;
    private byte[] imgProducto;
    @WireVariable
    private BusinessService<DTO_Articulo> productService;
    @WireVariable
    private BusinessService<DTO_Categoria> categoryService;
    private DTO_Empresa empresa;
    private DTO_Articulo articulo;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Window wEAEP;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
        //decPrec.setValue(BigDecimal.ZERO);

        empresa = (DTO_Empresa) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);

        articulo = (DTO_Articulo) desktop.getSession().getAttribute(Constantes.ATTRIBUTE_PRODUCT);
        final ServiceInput<DTO_Articulo> input = new ServiceInput<DTO_Articulo>(articulo);
        input.setAccion(Constantes.V_GETIMG);
        final ServiceOutput<DTO_Articulo> output = productService.execute(input);
        if (output.getErrorCode() != Constantes.OK) {
            alertaInfo(logger, "", "El articulo" + articulo.getProductName() + "no posee imagen", null);
        }

        if (articulo == null) {
            alertaInfo(logger, "", "No se encontro producto, retornando a busqueda", null);
        } else {
            desktop.getSession().removeAttribute(Constantes.ATTRIBUTE_PRODUCT);
            cargarDatos();
        }
    }

    @Listen("onClick = #btnSave")
    public void editProduct()
    {
        if (cmbCateg.getSelectedItem() != null && cmbStatus.getSelectedItem() != null
                        && !txtNombre.getValue().equals("")
                        && !txtDesc.getValue().equals("")) {
            // articulo.setCategoria(((DTO_Categoria)
            // cmbCateg.getSelectedItem().getAttribute("categoria")).getCodigo());
            articulo.setActivo((Boolean) cmbStatus.getSelectedItem().getValue());
            articulo.setProductDescription(txtDesc.getValue());
            articulo.setEmpresa(empresa.getCodigo());
            // articulo.setMarca(txtMarca.getValue());
            articulo.setProductName(txtNombre.getValue());
            // articulo.setPrecio(decPrec.getValue());
            if (imgProducto != null && !imgProducto.equals(articulo.getImagen())) {
                articulo.setImagen(imgProducto);
                articulo.setNomimg(null);
            }
            final ServiceInput<DTO_Articulo> input = new ServiceInput<DTO_Articulo>(articulo);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Articulo> output = productService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                alertaInfo(logger, "Los datos del producto se actualizaron correctamente",
                                "Los datos del producto se actualizaron correctamente", null);
            } else {
                alertaError(logger, "Ocurrio un error inesperado al guardar el producto, consulte al Administrador",
                                "Ocurrio un error inesperado al guardar el producto, consulte al Administrador", null);
            }
        } else {
            alertaInfo(logger, "Debe ingresar datos en todos los campos", "No se ingreso data en todos los campos",
                            null);
        }
    }

    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event e) {
        wEAEP.detach();
    }

    private void cargarDatos()
    {
        buildActiveCombo(cmbStatus);
        // listarCategorias();
        txtNombre.setValue(articulo.getProductName());
        // txtMarca.setValue(articulo.getMarca());
        txtDesc.setValue(articulo.getProductDescription());
        // decPrec.setValue(articulo.getPrecio());
        imgProducto = articulo.getImagen();
        setGraficoFoto();
    }

    private void setGraficoFoto()
    {
        if (imgProducto != null) {
            try {
                imgFoto.setContent(new AImage("foto", imgProducto));
                return;
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        imgFoto.setSrc("/media/imagProd.gif");
    }

    public void cargaFoto(final UploadEvent event)
        throws Exception
    {
        org.zkoss.util.media.Media media;
        try {
            media = event.getMedia();
            if (media == null) {
                return;
            }
        } catch (final Exception ex) {
            Messagebox.show("Hubo un problema con el archivo proporcionado.", empresa.getRazonsocial(), Messagebox.OK,
                            Messagebox.ERROR);
            return;
        }
        // System.out.println(media.getName());
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 102400) {
                Messagebox.show("El archivo seleccionado es muy grande. Maximo permitido = 100k",
                                empresa.getRazonsocial(), Messagebox.OK,
                                Messagebox.ERROR);
                return;
            }
            imgProducto = media.getByteData();
            setGraficoFoto();

            // imgFoto.setContent((org.zkoss.image.Image)media);

        } else {
            Messagebox.show("El archivo seleccionado " + media + " no es una imagen", empresa.getRazonsocial(),
                            Messagebox.OK,
                            Messagebox.ERROR);
            return;
        }
    }


    /*private void listarCategorias()
    {
        final DTO_Categoria cat = new
                        DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final ServiceInput input = new ServiceInput(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = categoryService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("",
                            "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat =
                            output.getLista();
            for (final DTO_Categoria categ : lstCat) {
                final Comboitem item = new Comboitem();
                item.setLabel(categ.getCategoryName());
                item.setAttribute("categoria", categ);
                cmbCateg.appendChild(item);
                if (articulo.getCategoria().equals(categ.getCodigo())) {
                    cmbCateg.setSelectedItem(item);
                }
            }
        } else {
            alertaError("Error inesperado, por favor contacte al administrador",
                            "Error cargando categorias", null);
        }
    }*/


    @Override
    public void buildActiveCombo(final Combobox _combo)
    {
        super.buildActiveCombo(cmbStatus);
        for(final Comboitem item : cmbStatus.getItems()) {
            if (articulo.getActivo().equals(item.getValue())){
                cmbStatus.setSelectedItem(item);
            }
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULO_PROD_PRODUCT };
    }
}
