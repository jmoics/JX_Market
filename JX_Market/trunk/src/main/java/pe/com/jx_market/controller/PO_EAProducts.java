/**
 *
 */
package pe.com.jx_market.controller;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Articulo;
import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.CategoryTreeNode;
import pe.com.jx_market.utilities.CategoryTreeNodeCollection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@VariableResolver(DelegatingVariableResolver.class)
public class PO_EAProducts
    extends SelectorComposer<Window>
{

    static Log logger = LogFactory.getLog(PO_EAProducts.class);
    private final NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    @Wire
    private Textbox txtProdName;
    @Wire
    private Combobox cmbEstad;
    @Wire
    private Chosenbox chbCat;
    @Wire
    private Groupbox grpCons, grpBusq;
    @Wire
    private Listbox lstProd;
    @WireVariable
    private BusinessService<DTO_Articulo> articuloService;
    @WireVariable
    private BusinessService<DTO_Categoria> categoryService;
    private DTO_Empresa empresa;
    @WireVariable
    private Desktop desktop;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        empresa = (DTO_Empresa) _comp.getDesktop().getSession().getAttribute("empresa");

        listCategories();

        listarEstados();
    }

    private void listCategories()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final ServiceInput<DTO_Categoria> input = new ServiceInput<DTO_Categoria>(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Categoria> output = categoryService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo("", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            final CategoryTreeNode categoryTreeNode = buildCategoryTree(lstCat);

            final List<DTO_Categoria> lstCat2 = new LinkedList<DTO_Categoria>();
            buildChosenBox(categoryTreeNode, lstCat2, Constantes.EMPTY_STRING);
            final ListModelList<DTO_Categoria> modelCat = new ListModelList<DTO_Categoria>(lstCat2);
            //chbCat.setModel(ListModels.toListSubModel(modelCat));
            chbCat.setModel(modelCat);
        } else {
            alertaError("Error inesperado, por favor contacte al administrador", "Error cargando categorias", null);
        }
    }

    private CategoryTreeNode buildCategoryTree(final List<DTO_Categoria> _categorias)
    {
        final Map<Integer, DTO_Categoria> mapCateg = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> roots = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> childs = new TreeMap<Integer, DTO_Categoria>();
        final Set<Integer> setPadres = new HashSet<Integer>();
        for (final DTO_Categoria cat : _categorias) {
            mapCateg.put(cat.getCodigo(), cat);
            setPadres.add(cat.getCodigoPadre());
            if (cat.getCodigoPadre() == null) {
                roots.put(cat.getCodigo(), cat);
            } else {
                childs.put(cat.getCodigo(), cat);
            }
        }

        final CategoryTreeNode categoryTreeNode = new CategoryTreeNode(null,
                        new CategoryTreeNodeCollection()
        {

            private static final long serialVersionUID = -8249078122595873454L;
            {
                for (final DTO_Categoria root : roots.values()) {
                    if (!setPadres.contains(root.getCodigo())) {
                        add(new CategoryTreeNode(root));
                    } else {
                        add(new CategoryTreeNode(root,
                                        new CategoryTreeNodeCollection()
                        {
                            private static final long serialVersionUID = -5643408533240445491L;
                            {
                                construirJerarquia(childs.values(), root, setPadres, false);
                            }
                        }, true));
                    }
                }
            }
        },
        true);
        return categoryTreeNode;
    }

    private void buildChosenBox(final CategoryTreeNode _nodo,
                                final List<DTO_Categoria> _lstCat,
                                final String _space) {
        final DTO_Categoria categ = _nodo.getData();
        if (categ != null) {
            if (categ.getCodigoPadre() != null) {
                categ.setNombre(_space.concat(categ.getNombre()));
            }
            _lstCat.add(categ);
        }
        if (_nodo.getChildren() != null && !_nodo.getChildren().isEmpty()) {
            String space = _space;
            if (categ != null) {
                space = _space.concat(Constantes.SEPARATOR_STRING);
            }
            final List<TreeNode<DTO_Categoria>> hijos = _nodo.getChildren();
            for (int i = 0; i < hijos.size(); i++) {
                final CategoryTreeNode hijo = (CategoryTreeNode) hijos.get(i);
                buildChosenBox(hijo, _lstCat, space);
            }
        }
    }

    private void listarEstados()
    {
        Comboitem item = new Comboitem();
        item.setLabel(Constantes.STATUS_ACTIVO);
        item.setValue(Constantes.ST_ACTIVO);
        cmbEstad.appendChild(item);
        item = new Comboitem();
        item.setLabel(Constantes.STATUS_INACTIVO);
        item.setValue(Constantes.ST_INACTIVO);
        cmbEstad.appendChild(item);
    }

    public void buscarProductos()
    {
        /*final DTO_Articulo articulo = new DTO_Articulo();
        articulo.setEmpresa(empresa.getCodigo());
        if (cmbCat.getSelectedItem() != null) {
            articulo.setCategoria(((DTO_Categoria)
                            cmbCat.getSelectedItem().getAttribute("categoria")).getCodigo());
        }
        if (txtProdName.getValue().length() > 0) {
            articulo.setNombre(txtProdName.getValue());
        }
        if (txtMarc.getValue().length() > 0) {
            articulo.setMarca(txtMarc.getValue());
        }
        if (cmbEstad.getSelectedItem() != null) {
            articulo.setActivo((Integer)
                            cmbEstad.getSelectedItem().getValue());
        }
        final ServiceInput input =
                        new ServiceInput(articulo);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput output = articuloService.execute(input);
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
                item.addEventListener("onClick", new
                                org.zkoss.zk.ui.event.EventListener()
                                {

                                    @Override
                                    public void onEvent(final Event e)
                                        throws UiException
                                    {
                                        desktop.getSession().setAttribute("producto",
                                                        e.getTarget().getAttribute("producto"));
                                        incluir("eAEditaProducto.zul");
                                    }
                                });
                lstProd.appendChild(item);
            }
        }
        else {
        }
        grpCons.setVisible(true);
        grpBusq.setVisible(false);*/
    }

    public void incluir(final String txt)
    {
        // getDesktop().getSession().setAttribute("paginaActual", txt);
        desktop.getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.saltar(desktop, txt);
    }

    public void limpiarConsulta()
    {
        txtProdName.setValue("");
        //chbCat.setValue("");
        //chbCat.setSelectedItem(null);
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

    /*
     * @Override String[] requiredResources() { return new String[] {
     * Constantes.MODULO_PROD_PRODUCT }; }
     */
}
