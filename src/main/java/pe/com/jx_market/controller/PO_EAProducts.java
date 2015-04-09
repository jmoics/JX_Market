/**
 *
 */
package pe.com.jx_market.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
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
    extends SecuredComposer<Window>
{
    private static final long serialVersionUID = -1744564334999015662L;

    static Log logger = LogFactory.getLog(PO_EAProducts.class);
    private final NumberFormat formateador = NumberFormat.getNumberInstance(Locale.ENGLISH);
    @Wire
    private Textbox txtProdName;
    @Wire
    private Combobox cmbEstad;
    @Wire
    private Chosenbox chbCat;
    @Wire
    private Listbox lstProd;
    @WireVariable
    private BusinessService<DTO_Articulo> productService;
    @WireVariable
    private BusinessService<DTO_Categoria> categoryService;
    private DTO_Empresa empresa;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Window wEACP;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        empresa = (DTO_Empresa) _comp.getDesktop().getSession().getAttribute("empresa");

        listCategories();

        buildActiveCombo(cmbEstad);
    }

    private void listCategories()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final ServiceInput<DTO_Categoria> input = new ServiceInput<DTO_Categoria>(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Categoria> output = categoryService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo(logger, "", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            final CategoryTreeNode categoryTreeNode = buildCategoryTree(lstCat);

            final List<DTO_Categoria> lstCat2 = new LinkedList<DTO_Categoria>();
            buildChosenBox(categoryTreeNode, lstCat2, Constantes.EMPTY_STRING);
            final ListModelList<DTO_Categoria> modelCat = new ListModelList<DTO_Categoria>(lstCat2);
            //chbCat.setModel(ListModels.toListSubModel(modelCat));
            chbCat.setModel(modelCat);
        } else {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.Error.Label"), "Error cargando categorias", null);
        }
    }

    private CategoryTreeNode buildCategoryTree(final List<DTO_Categoria> _categorias)
    {
        final Map<Integer, DTO_Categoria> mapCateg = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> roots = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> childs = new TreeMap<Integer, DTO_Categoria>();
        final Set<Integer> setPadres = new HashSet<Integer>();
        for (final DTO_Categoria cat : _categorias) {
            mapCateg.put(cat.getId(), cat);
            setPadres.add(cat.getCategoryParentId());
            if (cat.getCategoryParentId() == null) {
                roots.put(cat.getId(), cat);
            } else {
                childs.put(cat.getId(), cat);
            }
        }

        final CategoryTreeNode categoryTreeNode = new CategoryTreeNode(null,
                        new CategoryTreeNodeCollection()
        {
            private static final long serialVersionUID = -8249078122595873454L;
            {
                for (final DTO_Categoria root : roots.values()) {
                    if (!setPadres.contains(root.getId())) {
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
            if (categ.getCategoryParentId() != null) {
                categ.setCategoryName(_space.concat(categ.getCategoryName()));
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

    @Listen("onClick = #btnSearch")
    public void buscarProductos()
    {
        lstProd.getItems().clear();
        final ArrayList<Integer> listCat = new ArrayList<Integer>();
        final ServiceInput<DTO_Articulo> input = new ServiceInput<DTO_Articulo>();
        if (chbCat.getSelectedObjects() != null && !chbCat.getSelectedObjects().isEmpty()) {
            final Set<DTO_Categoria> setCateg = chbCat.getSelectedObjects();
            for (final DTO_Categoria categ : setCateg) {
                listCat.add(categ.getId());
            }
            input.addMapPair("lstCategory", listCat);
        }
        if (txtProdName.getValue().length() > 0) {
            input.addMapPair("nombre", txtProdName.getValue());
        }
        if (cmbEstad.getSelectedItem() != null) {
            input.addMapPair("activo", cmbEstad.getSelectedItem().getValue());
        }
        input.addMapPair("empresa", empresa.getCodigo());
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Articulo> output = productService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Articulo> lst = output.getLista();
            for (final DTO_Articulo art : lst) {
                final Listitem item = new Listitem();
                final List<DTO_Categoria> categs = art.getCategories();
                Listcell cell = new Listcell();
                int cont = 1;
                final StringBuilder strB = new StringBuilder();
                for (final DTO_Categoria cat : categs) {
                    strB.append(cat.getCategoryName());
                    if (cont < categs.size()) {
                        strB.append(", ");
                    }
                    cont++;
                }
                cell.setLabel(strB.toString());
                item.appendChild(cell);
                cell = new Listcell(art.getProductName());
                item.appendChild(cell);
                cell = new Listcell(art.getProductDescription());
                item.appendChild(cell);
                /*cell = new Listcell(formateador.format(art.getPrecio()));
                item.appendChild(cell);*/
                if (art.getActivo()) {
                    cell = new Listcell(Labels.getLabel("pe.com.jx_market.Active.TRUE"));
                } else {
                    cell = new Listcell(Labels.getLabel("pe.com.jx_market.Active.FALSE"));
                }
                item.appendChild(cell);
                item.setAttribute(Constantes.ATTRIBUTE_PRODUCT, art);
                item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event e)
                        throws UiException
                    {
                        runWindowEdit((MouseEvent) e);
                        //incluir("eAEditaProducto.zul");
                    }
                });
                lstProd.appendChild(item);
            }
        }
        else {
        }
    }

    public void limpiarConsulta()
    {
        txtProdName.setValue("");
        chbCat.clearSelection();
        cmbEstad.setValue("");
        cmbEstad.setSelectedItem(null);
    }

    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent event) {
        if (lstProd.getSelectedItem() != null) {
            desktop.getSession().setAttribute(Constantes.ATTRIBUTE_PRODUCT,
                            lstProd.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_PRODUCT));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            final Window w = (Window) Executions.createComponents(Constantes.Form.PRODUCTS_EDIT_FORM.getForm(), null, dataArgs);
            w.setPage(wEACP.getPage());
            //w.setParent(wEACC);
            //w.doOverlapped();
            w.doHighlighted();
            //w.doEmbedded();
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProducts.runWindowEdit.Info.Label"),
                                "No se selecciono un registro a editar", null);
        }
    }

    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent event) {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        final Window w = (Window) Executions.createComponents(Constantes.Form.PRODUCTS_CREATE_FORM.getForm(), null, dataArgs);
        w.setPage(wEACP.getPage());
        //w.setParent(wEACC);
        //w.doOverlapped();
        w.doModal();
        //w.doEmbedded();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @Override String[] requiredResources() { return new String[] {
     * Constantes.MODULO_PROD_PRODUCT }; }
     */
}
