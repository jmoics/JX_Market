/**
 *
 */
package pe.com.jx_market.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Category;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.domain.DTO_Product;
import pe.com.jx_market.domain.DTO_TradeMark;
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
    private Combobox cmbEstad, cmbTradeMark;
    @Wire
    private Chosenbox chbCat;
    @Wire
    private Listbox lstProd;
    @Wire
    private Listheader headNumber;
    @WireVariable
    private BusinessService<DTO_Product> productService;
    @WireVariable
    private BusinessService<DTO_Category> categoryService;
    @WireVariable
    private BusinessService<DTO_TradeMark> tradeMarkService;
    private DTO_Company company;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Window wEAP;

    @Override
    public void doAfterCompose(final Window _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);

        this.company = (DTO_Company) _comp.getDesktop().getSession().getAttribute(Constantes.ATTRIBUTE_COMPANY);

        listCategories();

        buildActiveCombo(this.cmbEstad);
        buildTradeMarkCombo();
        buildOrderComparator();

        if (this.desktop.getAttribute(Constantes.ATTRIBUTE_RELOAD) != null
                        && (Boolean) this.desktop.getAttribute(Constantes.ATTRIBUTE_RELOAD)) {
            this.desktop.setAttribute(Constantes.ATTRIBUTE_RELOAD, false);
            searchProducts();
        }
    }

    /**
     * Configuration for the sort type for number column.
     */
    private void buildOrderComparator()
    {
        this.headNumber.setSortAscending(new Comparator<Listitem>()
        {

            @Override
            public int compare(final Listitem _o1,
                               final Listitem _o2)
            {
                final Integer c1 = Integer.parseInt(((Listcell) _o1.getChildren().get(0)).getLabel());
                final Integer c2 = Integer.parseInt(((Listcell) _o2.getChildren().get(0)).getLabel());
                return c1.compareTo(c2);
            }

        });
        this.headNumber.setSortDescending(new Comparator<Listitem>()
        {

            @Override
            public int compare(final Listitem _o1,
                               final Listitem _o2)
            {
                final Integer c1 = Integer.parseInt(((Listcell) _o1.getChildren().get(0)).getLabel());
                final Integer c2 = Integer.parseInt(((Listcell) _o2.getChildren().get(0)).getLabel());
                return c2.compareTo(c1);
            }

        });
    }

    /**
     * Get the trade marks in a combo.
     */
    private void buildTradeMarkCombo()
    {
        final DTO_TradeMark marFi = new DTO_TradeMark();
        marFi.setCompanyId(this.company.getId());
        final ServiceInput<DTO_TradeMark> input = new ServiceInput<DTO_TradeMark>(marFi);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_TradeMark> output = this.tradeMarkService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_TradeMark> lstMar = output.getLista();
            for (final DTO_TradeMark marIt : lstMar) {
                final Comboitem item = new Comboitem();
                item.setLabel(marIt.getTradeMarkName());
                item.setValue(marIt);
                this.cmbTradeMark.appendChild(item);
            }
        } else {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.Error.Label"), "Error cargando marcas", null);
        }
    }

    /**
     * Get the categories for the search of products.
     */
    private void listCategories()
    {
        final DTO_Category cat = new DTO_Category();
        cat.setCompanyId(this.company.getId());
        final ServiceInput<DTO_Category> input = new ServiceInput<DTO_Category>(cat);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Category> output = this.categoryService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            alertaInfo(logger, "", "Exito al cargar categories", null);
            final List<DTO_Category> lstCat = output.getLista();
            final CategoryTreeNode categoryTreeNode = buildCategoryTree(lstCat);

            final List<DTO_Category> lstCat2 = new LinkedList<DTO_Category>();
            buildChosenBox(categoryTreeNode, lstCat2, Constantes.EMPTY_STRING);
            final ListModelList<DTO_Category> modelCat = new ListModelList<DTO_Category>(lstCat2);
            //chbCat.setModel(ListModels.toListSubModel(modelCat));
            this.chbCat.setModel(modelCat);
        } else {
            alertaError(logger, Labels.getLabel("pe.com.jx_market.Error.Label"), "Error cargando categories", null);
        }
    }

    /**
     * Build the tree of categories to choose those and add to the products search.
     *
     * @param _categories List with the categories.
     * @return CategoryTreeNode to build the choosen box.
     */
    private CategoryTreeNode buildCategoryTree(final List<DTO_Category> _categories)
    {
        final Map<Integer, DTO_Category> mapCateg = new TreeMap<Integer, DTO_Category>();
        final Map<Integer, DTO_Category> roots = new TreeMap<Integer, DTO_Category>();
        final Map<Integer, DTO_Category> childs = new TreeMap<Integer, DTO_Category>();
        final Set<Integer> setPadres = new HashSet<Integer>();
        for (final DTO_Category cat : _categories) {
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
                    for (final DTO_Category root : roots.values()) {
                        if (!setPadres.contains(root.getId())) {
                            add(new CategoryTreeNode(root));
                        } else {
                            add(new CategoryTreeNode(root,
                                new CategoryTreeNodeCollection()
                                {
                                    private static final long serialVersionUID = -5643408533240445491L;
                                    {
                                        construirJerarquia(childs.values(), root, setPadres,
                                                        false);
                                    }
                                }, true));
                        }
                    }
                }
            },
        true);
        return categoryTreeNode;
    }

    /**
     * @param _nodo CategoryTreeNode with the categories
     * @param _lstCat List of categories to show in the chosen box.
     * @param _space
     */
    private void buildChosenBox(final CategoryTreeNode _nodo,
                                final List<DTO_Category> _lstCat,
                                final String _space)
    {
        final DTO_Category categ = _nodo.getData();
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
            final List<TreeNode<DTO_Category>> hijos = _nodo.getChildren();
            for (int i = 0; i < hijos.size(); i++) {
                final CategoryTreeNode hijo = (CategoryTreeNode) hijos.get(i);
                buildChosenBox(hijo, _lstCat, space);
            }
        }
    }

    /**
     * Method to search products.
     */
    @Listen("onClick = #btnSearch")
    public void searchProducts()
    {
        this.lstProd.getItems().clear();
        final ArrayList<Integer> listCat = new ArrayList<Integer>();
        final ServiceInput<DTO_Product> input = new ServiceInput<DTO_Product>();
        if (this.chbCat.getSelectedObjects() != null && !this.chbCat.getSelectedObjects().isEmpty()) {
            final Set<DTO_Category> setCateg = this.chbCat.getSelectedObjects();
            for (final DTO_Category categ : setCateg) {
                listCat.add(categ.getId());
            }
            input.addMapPair("lstCategory", listCat);
        }
        if (this.txtProdName.getValue().length() > 0) {
            input.addMapPair("productName", this.txtProdName.getValue());
        }
        if (this.cmbEstad.getSelectedItem() != null) {
            input.addMapPair("active", this.cmbEstad.getSelectedItem().getValue());
        }
        input.addMapPair("company", this.company.getId());
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Product> output = this.productService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            final List<DTO_Product> lst = output.getLista();
            int columnNumber = 1;
            for (final DTO_Product art : lst) {
                final Listitem item = new Listitem();
                final List<DTO_Category> categs = art.getCategories();
                Listcell cell = new Listcell("" + columnNumber);
                item.appendChild(cell);
                int cont = 1;
                final StringBuilder strB = new StringBuilder();
                for (final DTO_Category cat : categs) {
                    strB.append(cat.getCategoryName());
                    if (cont < categs.size()) {
                        strB.append(", ");
                    }
                    cont++;
                }
                cell = new Listcell(strB.toString());
                item.appendChild(cell);
                cell = new Listcell(art.getTradeMark().getTradeMarkName());
                item.appendChild(cell);
                cell = new Listcell(art.getProductName());
                item.appendChild(cell);
                cell = new Listcell(art.getProductDescription());
                item.appendChild(cell);
                if (art.isActive()) {
                    cell = new Listcell(Labels.getLabel("pe.com.jx_market.Active.TRUE"));
                } else {
                    cell = new Listcell(Labels.getLabel("pe.com.jx_market.Active.FALSE"));
                }
                item.appendChild(cell);
                item.setAttribute(Constantes.ATTRIBUTE_PRODUCT, art);
                item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent(final Event _e)
                        throws UiException
                    {
                        runWindowEdit((MouseEvent) _e);
                        //incluir("eAEditaProducto.zul");
                    }
                });
                this.lstProd.appendChild(item);
                columnNumber++;
            }
        } else {
        }
    }

    /**
     *
     */
    public void limpiarConsulta()
    {
        this.txtProdName.setValue("");
        this.chbCat.clearSelection();
        this.cmbEstad.setValue("");
        this.cmbEstad.setSelectedItem(null);
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnEdit")
    public void runWindowEdit(final MouseEvent _event) {
        if (this.lstProd.getSelectedItem() != null) {
            this.desktop.getSession().setAttribute(Constantes.ATTRIBUTE_PRODUCT,
                            this.lstProd.getSelectedItem().getAttribute(Constantes.ATTRIBUTE_PRODUCT));
            final Map<String, Object> dataArgs = new HashMap<String, Object>();
            dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
            final Window w = (Window) Executions.createComponents(Constantes.Form.PRODUCTS_EDIT_FORM.getForm(),
                            null, dataArgs);
            w.setPage(this.wEAP.getPage());
            //w.setParent(wEACC);
            //w.doOverlapped();
            w.doHighlighted();
            //w.doEmbedded();
        } else {
            alertaInfo(logger, Labels.getLabel("pe.com.jx_market.PO_EAProducts.runWindowEdit.Info.Label"),
                                "No se selecciono un registro a editar", null);
        }
    }

    /**
     * @param _event
     */
    @Listen("onClick = #btnCreate")
    public void runWindowCreate(final MouseEvent _event)
    {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        dataArgs.put(Constantes.ATTRIBUTE_PARENTFORM, this);
        final Window w = (Window) Executions.createComponents(Constantes.Form.PRODUCTS_CREATE_FORM.getForm(),
                        null, dataArgs);
        w.setPage(this.wEAP.getPage());
        // w.setParent(wEACC);
        // w.doOverlapped();
        w.doModal();
        // w.doEmbedded();
    }

    @Override
    String[] requiredResources()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @Override String[] requiredResources() { return new String[] {
     * Constantes.MODULE_PROD_PRODUCT }; }
     */
}
