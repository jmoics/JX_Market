/**
 *
 */
package pe.com.jx_market.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
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
@VariableResolver(DelegatingVariableResolver.class)
public class PO_EAProductsCreate
    extends SecuredComposer<Window>
{
    private static final long serialVersionUID = -5294436527686469836L;

    static Log logger = LogFactory.getLog(PO_EAProductsCreate.class);
    @Wire
    private Combobox cmbStatus;
    @Wire
    private Textbox txtNombre, txtDesc;
    @Wire
    private Image imgFoto;
    @WireVariable
    private BusinessService<DTO_Articulo> productService;
    @WireVariable
    private BusinessService<DTO_Categoria> categoryService;
    private DTO_Empresa empresa;
    private byte[] imgProducto;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Window wEAPC;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        empresa = (DTO_Empresa) desktop.getSession().getAttribute("empresa");
        buildActiveCombo(cmbStatus);

        // listarCategorias();
    }

    @Listen("onClick = #btnSave")
    public void createProduct()
    {
        if (!txtNombre.getValue().equals("") && !txtDesc.getValue().equals("")) {
            final DTO_Articulo articulo = new DTO_Articulo();
            //articulo.setCategoria(((DTO_Categoria) cmbCateg.getSelectedItem().getAttribute("categoria")).getCodigo());
            articulo.setActivo((Boolean) cmbStatus.getSelectedItem().getValue());
            articulo.setProductDescription(txtDesc.getValue());
            articulo.setEmpresa(empresa.getCodigo());
            //articulo.setMarca(txtMarca.getValue());
            articulo.setProductName(txtNombre.getValue());
            //articulo.setPrecio(decPrec.getValue());
            if (imgProducto != null) {
                articulo.setImagen(imgProducto);
            }
            final ServiceInput<DTO_Articulo> input = new ServiceInput<DTO_Articulo>(articulo);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput<DTO_Articulo> output = productService.execute(input);
            if (output.getErrorCode() == Constantes.OK) {
                alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info.Label"), null);
                limpiarCampos();
            } else {
                alertaError(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Error.Label"),
                                Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Error.Label"), null);
            }
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info2.Label"),
                            Labels.getLabel("pe.com.jx_market.PO_EAProductsCreate.createProduct.Info2.Label"), null);
        }
    }

    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event e) {
        wEAPC.detach();
    }

    /*public void listarCategorias()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final ServiceInput input = new ServiceInput(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = categoryService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo(logger, "", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            for (final DTO_Categoria categ : lstCat) {
                final Comboitem item = new Comboitem();
                item.setLabel(categ.getCategoryName());
                item.setAttribute("categoria", categ);
                cmbCateg.appendChild(item);
            }
        } else {
            alertaError(logger, "Error inesperado, por favor contacte al administrador", "Error cargando categorias", null);
        }
    }*/

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
        imgFoto.setSrc("/media/silueta.gif");
    }

    @Listen("onUpload = #btnUpload")
    public void uploadPhoto(final UploadEvent event)
        throws Exception
    {
        org.zkoss.util.media.Media media;
        try {
            media = event.getMedia();
            if (media == null) {
                return;
            }
        } catch (final Exception ex) {
            Messagebox.show("Hubo un problema con el archivo proporcionado.", empresa.getRazonsocial(), Messagebox.OK, Messagebox.ERROR);
            return;
        }
        System.out.println(media.getName());
        if (media instanceof org.zkoss.image.Image) {
            if (media.getByteData().length > 102400) {
                Messagebox.show("El archivo seleccionado es muy grande. Maximo permitido = 100k", empresa.getRazonsocial(), Messagebox.OK,
                                Messagebox.ERROR);
                return;
            }
            imgProducto = media.getByteData();
            setGraficoFoto();

            // imgFoto.setContent((org.zkoss.image.Image)media);

        } else {
            Messagebox.show("El archivo seleccionado " + media + " no es una imagen", empresa.getRazonsocial(), Messagebox.OK,
                            Messagebox.ERROR);
            return;
        }
    }

    public void limpiarCampos()
    {
        cmbStatus.setSelectedItem(null);
        cmbStatus.setValue(null);
        txtNombre.setValue("");
        txtDesc.setValue("");
        imgProducto = null;
        setGraficoFoto();
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULO_PROD_PRODUCT };
    }
}
