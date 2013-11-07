/**
 *
 */
package pe.com.jx_market.controller;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

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
public class PO_EAConsultaProducto
    extends SecuredWindow
{
    static Log logger = LogFactory.getLog(PO_EAConsultaProducto.class);
    private NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private Textbox txtNomProd, txtMarc;
    private Combobox cmbCat, cmbEstad;
    private Groupbox grpCons, grpBusq;
    private Listbox lstProd;
    private BusinessService articuloService, categoriaService;
    private DTO_Empresa empresa;

    @Override
    public void realOnCreate()
    {
        txtNomProd = (Textbox) getFellow("txtNomProd");
        txtMarc = (Textbox) getFellow("txtMarc");
        cmbCat = (Combobox) getFellow("cmbCat");
        cmbEstad = (Combobox) getFellow("cmbEstad");
        grpCons = (Groupbox) getFellow("grpCons");
        grpBusq = (Groupbox) getFellow("grpBusq");
        lstProd = (Listbox) getFellow("lstProd");

        articuloService = Utility.getService(this, "articuloService");
        categoriaService = Utility.getService(this, "categoriaService");

        empresa = (DTO_Empresa) getDesktop().getSession().getAttribute("empresa");

        listarCategorias();
        listarEstados();
    }

    public void listarCategorias()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final DTO_Input input = new DTO_Input(cat);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output output = categoriaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            for (final DTO_Categoria categ : lstCat) {
                final Comboitem item = new Comboitem();
                item.setLabel(categ.getNombre());
                item.setAttribute("categoria", categ);
                cmbCat.appendChild(item);
            }
        } else {
            alertaError("Error inesperado, por favor contacte al administrador", "Error cargando categorias", null);
        }
    }

    private void listarEstados()
    {
        Comboitem item = new Comboitem();
        item.setLabel("Activo");
        item.setValue(Constantes.ST_ACTIVO);
        cmbEstad.appendChild(item);
        item = new Comboitem();
        item.setLabel("Inactivo");
        item.setValue(Constantes.ST_INACTIVO);
        cmbEstad.appendChild(item);
    }

    public void buscarProductos()
    {
        final DTO_Articulo articulo = new DTO_Articulo();
        articulo.setEmpresa(empresa.getCodigo());
        if (cmbCat.getSelectedItem() != null) {
            articulo.setCategoria(((DTO_Categoria) cmbCat.getSelectedItem().getAttribute("categoria")).getCodigo());
        }
        if (txtNomProd.getValue().length() > 0) {
            articulo.setNombre(txtNomProd.getValue());
        }
        if (txtMarc.getValue().length() > 0) {
            articulo.setMarca(txtMarc.getValue());
        }
        if (cmbEstad.getSelectedItem() != null) {
            articulo.setActivo((Integer) cmbEstad.getSelectedItem().getValue());
        }
        final DTO_Input input = new DTO_Input(articulo);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output output = articuloService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Articulo> lst = output.getLista();
            for (final DTO_Articulo art : lst) {
                final Listitem item = new Listitem();
                Listcell cell = new Listcell(getCategoria(art.getCategoria()));
                item.appendChild(cell);
                cell = new Listcell(art.getNombre());
                item.appendChild(cell);
                cell = new Listcell(art.getMarca());
                item.appendChild(cell);
                cell = new Listcell(formateador.format(art.getPrecio()));
                item.appendChild(cell);
                if (art.getActivo().equals(Constantes.ST_ACTIVO)) {
                    cell = new Listcell("Activo");
                } else {
                    cell = new Listcell("Inactivo");
                }
                item.appendChild(cell);
                item.setAttribute("producto", art);
                item.addEventListener("onClick",
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                                    {
                                        getDesktop().getSession().setAttribute("producto", e.getTarget().getAttribute("producto"));
                                        incluir("eAEditaProducto.zul");
                                    }
                                });
                lstProd.appendChild(item);
            }
        } else {

        }
        grpCons.setVisible(true);
        grpBusq.setVisible(false);
    }

    private String getCategoria(final Integer codCat)
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setCodigo(codCat);
        final DTO_Input input = new DTO_Input(cat);
        input.setVerbo(Constantes.V_GET);
        final DTO_Output output = categoriaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            return ((DTO_Categoria) output.getObject()).getNombre();
        } else {
            return null;
        }
    }

    public void incluir(final String txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        getDesktop().getSession().setAttribute("actualizar", "actualizar");
        Utility.saltar(this, txt);
    }

    public void limpiarConsulta()
    {
        txtNomProd.setValue("");
        txtMarc.setValue("");
        cmbCat.setValue("");
        cmbCat.setSelectedItem(null);
        cmbEstad.setValue("");
        cmbEstad.setSelectedItem(null);
        grpCons.setVisible(false);
        grpBusq.setVisible(true);
    }

    public void cancelarBusqueda()
    {
        lstProd.getItems().clear();
        limpiarConsulta();
    }

    public void alertaInfo(final String txt,
                           final String txt2,
                           final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.INFORMATION);
            if (t != null) {
                logger.info(txt2, t);
            } else {
                logger.info(txt2);
            }
        } catch (final InterruptedException ex) {
        }
    }

    public void alertaError(final String txt,
                            final String txt2,
                            final Throwable t)
    {
        try {
            if (txt.length() > 0)
                Messagebox.show(txt, empresa.getRazonsocial(), 1, Messagebox.EXCLAMATION);
            if (t != null) {
                logger.error(txt2, t);
            } else {
                logger.error(txt2);
            }
        } catch (final InterruptedException ex) {
        }
    }

    @Override
    String[] requiredResources()
    {
        return new String[] { Constantes.MODULO_PROD_CONSULTA };
    }
}
