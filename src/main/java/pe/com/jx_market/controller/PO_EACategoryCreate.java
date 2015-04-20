package pe.com.jx_market.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
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


public class PO_EACategoryCreate
    extends SelectorComposer<Window>
{
    private static final long serialVersionUID = 6046074628719265175L;

    static Log logger = LogFactory.getLog(PO_EACategoryCreate.class);
    @Autowired
    private BusinessService<DTO_Category> categoryService;
    private DTO_Company company;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Tree tree;
    @Wire
    private Window wEAIC;
    private CategoryTreeNode categoryTreeNode;
    private AdvancedTreeModel categoryTreeModel;
    private int seqTxtId;

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(final Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);

        categoryService = (BusinessService<DTO_Category>) ContextLoader.getService(comp, "categoryService");

        company = (DTO_Company) desktop.getSession().getAttribute("company");

        categoryTreeNode = listarCategories();
        categoryTreeModel = new AdvancedTreeModel(categoryTreeNode);
        tree.setItemRenderer(new CategoryTreeRenderer());
        tree.setModel(categoryTreeModel);
        tree.getRoot().setVisible(true);
        seqTxtId = 1;
    }

    public CategoryTreeNode listarCategories()
    {
        final DTO_Category cat = new DTO_Category();
        cat.setCompanyId(company.getId());
        final ServiceInput<DTO_Category> input = new ServiceInput<DTO_Category>(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Category> output = categoryService.execute(input);
        CategoryTreeNode categoryTreeNode = null;
        if (output.getErrorCode() == Constantes.OK) {
            // alertaInfo("", "Exito al cargar categories", null);
            final List<DTO_Category> lstCat = output.getLista();
            categoryTreeNode = construirArbolCategories(lstCat);
        } else {
            // alertaError("Error inesperado, por favor catege al administrador",
            // "Error cargando categories", null);
        }
        return categoryTreeNode;
    }

    private CategoryTreeNode construirArbolCategories(final List<DTO_Category> _categories)
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
                // Agregamos esta Raiz ficticia para poder convertir un nodo hijo en Raiz de categories
                add(new CategoryTreeNode(new DTO_Category(Constantes.TREE_EDITABLE_RAIZ), new CategoryTreeNodeCollection()
                {
                    private static final long serialVersionUID = 3800210198277431722L;
                    {
                        for (final DTO_Category root : roots.values()) {
                            if (!setPadres.contains(root.getId())) {
                                add(new CategoryTreeNode(root, new CategoryTreeNodeCollection(), true));
                            } else {
                                add(new CategoryTreeNode(root,
                                                new CategoryTreeNodeCollection()
                                {
                                    private static final long serialVersionUID = -5643408533240445491L;
                                    {
                                        construirJerarquia(childs.values(), root, setPadres, true);
                                    }
                                }, true));
                            }
                        }
                    }
                }, true));
            }
        },
        true);
        return categoryTreeNode;
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
            hl.setSclass("h-inline-block");
            hl.appendChild(new Image("/widgets/tree/dynamic_tree/img/" + categ.getImagen()));
            final Treecell treeCell = new Treecell();
            treeCell.appendChild(hl);
            dataRow.appendChild(treeCell);

            if ((categ.getId() != null && categ.getId() > 0 )
                            || Constantes.TREE_EDITABLE_RAIZ.equals(categ.getCategoryName())) {
                hl.appendChild(new Label(categ.getCategoryName()));

                final Treecell treeCell2 = new Treecell();
                if (!Constantes.TREE_EDITABLE_RAIZ.equals(categ.getCategoryName())) {
                    final Hlayout h2 = new Hlayout();
                    h2.appendChild(new Label(categ.isActive() ? Constantes.STATUS_ACTIVO : Constantes.STATUS_INACTIVO));
                    treeCell2.appendChild(h2);
                }
                dataRow.appendChild(treeCell2);

                if (categ.isActive() != null && categ.isActive()
                                || Constantes.TREE_EDITABLE_RAIZ.equals(categ.getCategoryName())) {
                    final Hlayout h3 = new Hlayout();
                    h3.appendChild(new Image("/media/add.png"));
                    final Treecell treeCell3 = new Treecell();
                    treeCell3.appendChild(h3);
                    dataRow.appendChild(treeCell3);

                    h3.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                        @Override
                        public void onEvent(final Event event)
                            throws Exception
                        {
                            final DTO_Category newCateg = new DTO_Category();
                            categoryTreeModel.add((CategoryTreeNode) treeItem.getValue(),
                                    new CategoryTreeNodeCollection()
                                    {
                                        private static final long serialVersionUID = -4941224185260321214L;
                                        {
                                            add(new CategoryTreeNode(newCateg, new CategoryTreeNodeCollection(), true));
                                        }
                                    });
                            newCateg.setCategoryParentId(categ.getId());
                        }
                    });
                }
            } else {
                final Textbox textBoxCat = new Textbox();
                textBoxCat.setId(new StringBuilder(Constantes.TREE_EDITABLE_TEXTBOX)
                                    .append(-seqTxtId).toString());
                hl.appendChild(textBoxCat);
                categ.setId(-seqTxtId); //codigo temporal para marcar que es nueva category
                seqTxtId++;
            }
        }
    }

    @Listen("onClick = #btnClose")
    public void accionCerrar(final Event e) {
        wEAIC.detach();
    }

    @Listen("onClick = #btnSave")
    public void accionGrabar(final MouseEvent e) {
        final CategoryTreeNode raiz = categoryTreeNode;
        grabarData(raiz);

        desktop.getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.recargar(desktop, "eACategory.zul");

        wEAIC.detach();
    }

    private void grabarData(final CategoryTreeNode _nodo)
    {
        final DTO_Category categ = _nodo.getData();
        final ServiceInput<DTO_Category> input = new ServiceInput<DTO_Category>();
        input.setAccion(Constantes.V_REGISTER);
        input.setObject(categ);
        ServiceOutput<DTO_Category> output = null;
        if (categ != null && categ.getId() != null && categ.getId() < 0) {
            final String strIdTxtCateg = new StringBuilder(Constantes.TREE_EDITABLE_TEXTBOX)
                .append(categ.getId()).toString();
            final Textbox txtCategName = ((Textbox) wEAIC.getFellow(strIdTxtCateg));
            categ.setCategoryName(txtCategName.getValue());
            categ.setCompanyId(company.getId());
            output = categoryService.execute(input);
            if (Constantes.OK == output.getErrorCode()) {
                logger.debug("Category '" + categ.getCategoryName() + "' actualizada");
            }
        }
        if (_nodo.getChildren() != null && !_nodo.getChildren().isEmpty()) {
            final List<TreeNode<DTO_Category>> hijos = _nodo.getChildren();
            for (int i = 0; i < hijos.size(); i++) {
                final CategoryTreeNode hijo = (CategoryTreeNode) hijos.get(i);
                grabarData(hijo);
            }
        }
    }
}
