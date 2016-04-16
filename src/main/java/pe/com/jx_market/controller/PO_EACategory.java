package pe.com.jx_market.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Category;
import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.AdvancedTreeModel;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.CategoryTreeNode;
import pe.com.jx_market.utilities.CategoryTreeNodeCollection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

public class PO_EACategory
extends SelectorComposer<Window>
{

    private static final long serialVersionUID = -1481359887612974294L;

    static Log logger = LogFactory.getLog(PO_EACategory.class);
    @Wire
    private Window wEACC;
    @Autowired
    private BusinessService<DTO_Category> categoryService;
    private DTO_Company company;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Tree tree;
    private CategoryTreeNode categoryTreeNode;
    private AdvancedTreeModel categoryTreeModel;

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(final Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);

        categoryService = (BusinessService<DTO_Category>) ContextLoader.getService(comp, "categoryService");

        company = (DTO_Company) desktop.getSession().getAttribute("company");

        listarCategories();
        categoryTreeModel = new AdvancedTreeModel(categoryTreeNode);
        tree.setItemRenderer(new CategoryTreeRenderer());
        tree.setModel(categoryTreeModel);
    }

    public void listarCategories()
    {
        final DTO_Category cat = new DTO_Category();
        cat.setCompanyId(company.getId());
        final ServiceInput<DTO_Category> input = new ServiceInput<DTO_Category>(cat);
        input.setAction(Constantes.V_LIST);
        final ServiceOutput<DTO_Category> output = categoryService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            // alertaInfo("", "Exito al cargar categories", null);
            final List<DTO_Category> lstCat = output.getLista();
            construirArbolCategories(lstCat);
        } else {
            // alertaError("Error inesperado, por favor catege al administrador",
            // "Error cargando categories", null);
        }
    }

    private void construirArbolCategories(final List<DTO_Category> _categories)
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

        categoryTreeNode = new CategoryTreeNode(null,
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
                                construirJerarquia(childs.values(), root, setPadres, false);
                            }
                        }, true));
                    }
                }
            }
        },
        true);
    }

    @Listen("onClick = #btnEdit")
    public void lanzarWindowEditar(final MouseEvent event)
    {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        final Window w = (Window) Executions.createComponents(Constantes.Form.CATEGORY_EDIT_FORM.getForm(), null,
                        dataArgs);
        w.setPage(this.wEACC.getPage());
        // w.setParent(wEACC);
        // w.doOverlapped();
        w.doHighlighted();
        // w.doEmbedded();
    }

    @Listen("onClick = #btnCreate")
    public void lanzarWindowNuevo(final MouseEvent event)
    {
        final Map<String, Object> dataArgs = new HashMap<String, Object>();
        final Window w = (Window) Executions.createComponents(Constantes.Form.CATEGORY_CREATE_FORM.getForm(), null,
                        dataArgs);
        w.setPage(this.wEACC.getPage());
        // w.setParent(wEACC);
        // w.doOverlapped();
        w.doModal();
        // w.doEmbedded();
    }

    private final class CategoryTreeRenderer
        implements TreeitemRenderer<CategoryTreeNode>
    {
        @Override
        public void render(final Treeitem treeItem,
                           final CategoryTreeNode treeNode,
                           final int index)
            throws Exception
        {
            final CategoryTreeNode ctn = treeNode;
            final DTO_Category categ = ctn.getData();
            final Treerow dataRow = new Treerow();
            dataRow.setParent(treeItem);
            treeItem.setValue(ctn);
            treeItem.setOpen(ctn.isOpen());

            final Hlayout hl = new Hlayout();
            hl.appendChild(new Image("/widgets/tree/dynamic_tree/img/" + categ.getImagen()));
            hl.appendChild(new Label(categ.getCategoryName()));
            hl.setSclass("h-inline-block");
            final Treecell treeCell = new Treecell();
            treeCell.appendChild(hl);
            dataRow.appendChild(treeCell);
            final Treecell treeCell2 = new Treecell();
            final Hlayout h2 = new Hlayout();
            h2.appendChild(new Label(categ.isActive() ? Constantes.STATUS_ACTIVO : Constantes.STATUS_INACTIVO));
            treeCell2.appendChild(h2);
            dataRow.appendChild(treeCell2);
        }
    }
}
