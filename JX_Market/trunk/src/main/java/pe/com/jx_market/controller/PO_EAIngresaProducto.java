/**
 *
 */
package pe.com.jx_market.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

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
public class PO_EAIngresaProducto
    extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EAIngresaProducto.class);
    private Combobox cmbCateg;
    private Textbox txtNombre, txtDesc, txtMarca;
    private Decimalbox decPrec;
    private Image imgFoto;
    private BusinessService articuloService, categoriaService;
    private DTO_Empresa empresa;
    private byte[] imgProducto;

    @Override
    public void realOnCreate()
    {
        imgFoto = (Image) getFellow("imgFoto");
        cmbCateg = (Combobox) getFellow("cmbCateg");
        txtNombre = (Textbox) getFellow("txtNombre");
        txtDesc = (Textbox) getFellow("txtDesc");
        txtMarca = (Textbox) getFellow("txtMarca");
        decPrec = (Decimalbox) getFellow("decPrec");
        decPrec.setValue(BigDecimal.ZERO);

        articuloService = ContextLoader.getService(this, "articuloService");
        categoriaService = ContextLoader.getService(this, "categoriaService");

        empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");

        listarCategorias();
    }

    public void crearProducto()
    {
        if (cmbCateg.getSelectedItem() != null && !txtNombre.getValue().equals("")
                        && !txtDesc.getValue().equals("") && !txtMarca.getValue().equals("")
                        && decPrec.getValue() != null && decPrec.getValue() != BigDecimal.ZERO) {
            final DTO_Articulo articulo = new DTO_Articulo();
            //articulo.setCategoria(((DTO_Categoria) cmbCateg.getSelectedItem().getAttribute("categoria")).getCodigo());
            articulo.setActivo(Constantes.ST_ACTIVO);
            articulo.setDescripcion(txtDesc.getValue());
            articulo.setEmpresa(empresa.getCodigo());
            //articulo.setMarca(txtMarca.getValue());
            articulo.setNombre(txtNombre.getValue());
            //articulo.setPrecio(decPrec.getValue());
            if (imgProducto != null) {
                articulo.setImagen(imgProducto);
            }
            final ServiceInput input = new ServiceInput(articulo);
            input.setAccion(Constantes.V_REGISTER);
            final ServiceOutput output = articuloService.execute(input);
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
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final ServiceInput input = new ServiceInput(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = categoriaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            for (final DTO_Categoria categ : lstCat) {
                final Comboitem item = new Comboitem();
                item.setLabel(categ.getNombre());
                item.setAttribute("categoria", categ);
                cmbCateg.appendChild(item);
            }
        } else {
            alertaError("Error inesperado, por favor contacte al administrador", "Error cargando categorias", null);
        }
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
        imgFoto.setSrc("/media/silueta.gif");
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
        cmbCateg.setSelectedItem(null);
        cmbCateg.setValue("");
        txtNombre.setValue("");
        txtDesc.setValue("");
        txtMarca.setValue("");
        decPrec.setValue(BigDecimal.ZERO);
        imgProducto = null;
        setGraficoFoto();
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.INFORMATION);
        }
        if (t != null) {
            logger.info(txt2, t);
        } else {
            logger.info(txt2);
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        if (txt.length() > 0) {
            Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.EXCLAMATION);
        }
        if (t != null) {
            logger.error(txt2, t);
        } else {
            logger.error(txt2);
        }

    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULO_PROD_PRODUCT };
    }
}
