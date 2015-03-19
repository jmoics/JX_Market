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
import org.zkoss.zk.ui.event.DropEvent;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.AdvancedTreeModel;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.CategoriaTreeNode;
import pe.com.jx_market.utilities.CategoriaTreeNodeCollection;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;


public class PO_EAEditaCategoria
    extends SelectorComposer<Window>
{
    private static final long serialVersionUID = 6046074628719265175L;

    static Log logger = LogFactory.getLog(PO_EAEditaCategoria.class);
    @Autowired
    private BusinessService<DTO_Categoria> categoriaService;
    private DTO_Empresa empresa;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Tree tree;
    @Wire
    Window wEAEC;
    private CategoriaTreeNode categoriaTreeNode;
    private AdvancedTreeModel categoriaTreeModel;

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(final Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);

        categoriaService = (BusinessService<DTO_Categoria>) Utility.getService(comp, "categoriaService");

        empresa = (DTO_Empresa) desktop.getSession().getAttribute("empresa");

        categoriaTreeNode = listarCategorias();
        categoriaTreeModel = new AdvancedTreeModel(categoriaTreeNode);
        tree.setItemRenderer(new CategoriaTreeRenderer());
        tree.setModel(categoriaTreeModel);
        tree.getRoot().setVisible(true);
    }

    public CategoriaTreeNode listarCategorias()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final DTO_Input<DTO_Categoria> input = new DTO_Input<DTO_Categoria>(cat);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output<DTO_Categoria> output = categoriaService.execute(input);
        CategoriaTreeNode categoriaTreeNode = null;
        if (output.getErrorCode() == Constantes.OK) {
            // alertaInfo("", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            categoriaTreeNode = construirArbolCategorias(lstCat);
        } else {
            // alertaError("Error inesperado, por favor catege al administrador",
            // "Error cargando categorias", null);
        }
        return categoriaTreeNode;
    }

    private CategoriaTreeNode construirArbolCategorias(final List<DTO_Categoria> _categorias)
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

        final CategoriaTreeNode categoriaTreeNode = new CategoriaTreeNode(null,
                        new CategoriaTreeNodeCollection()
        {
            private static final long serialVersionUID = -8249078122595873454L;
            {
                // Agregamos esta Raiz ficticia para poder convertir un nodo hijo en Raiz de categorias
                add(new CategoriaTreeNode(new DTO_Categoria(Constantes.TREE_EDITABLE_RAIZ), new CategoriaTreeNodeCollection()
                {
                    private static final long serialVersionUID = 3800210198277431722L;
                    {
                        for (final DTO_Categoria root : roots.values()) {
                            if (!setPadres.contains(root.getCodigo())) {
                                add(new CategoriaTreeNode(root, new CategoriaTreeNodeCollection(), true));
                            } else {
                                add(new CategoriaTreeNode(root,
                                                new CategoriaTreeNodeCollection()
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
        return categoriaTreeNode;
    }

    private final class CategoriaTreeRenderer
        implements TreeitemRenderer<CategoriaTreeNode>
    {
        @Override
        public void render(final Treeitem treeItem,
                           final CategoriaTreeNode treeNode,
                           final int index)
            throws Exception
        {
            final CategoriaTreeNode ctn = treeNode;
            final DTO_Categoria categ = ctn.getData();
            final Treerow dataRow = new Treerow();
            dataRow.setParent(treeItem);
            treeItem.setValue(ctn);
            treeItem.setOpen(ctn.isOpen());

            final Hlayout hl = new Hlayout();
            hl.appendChild(new Image("/widgets/tree/dynamic_tree/img/" + categ.getImagen()));
            final Textbox textBoxCat = new Textbox(categ.getNombre());
            textBoxCat.setId(Constantes.TREE_EDITABLE_TEXTBOX + categ.getCodigo());
            hl.appendChild(textBoxCat);
            hl.setSclass("h-inline-block");
            final Treecell treeCell = new Treecell();
            treeCell.appendChild(hl);
            dataRow.setDraggable("true");
            dataRow.appendChild(treeCell);
            dataRow.setDroppable("true");
            dataRow.addEventListener(Events.ON_DROP, new EventListener<Event>() {
                @Override
                public void onEvent(final Event event) throws Exception {
                    // The dragged target is a TreeRow belongs to an
                    // Treechildren of TreeItem.
                    final Treeitem draggedItem = (Treeitem) ((DropEvent) event).getDragged().getParent();
                    final CategoriaTreeNode draggedValue = (CategoriaTreeNode) draggedItem.getValue();
                    if (!isAncestor(draggedValue, ctn)) {
                        categoriaTreeModel.remove(draggedValue);
                            categoriaTreeModel.add((CategoriaTreeNode)treeItem.getValue(),
                                    new CategoriaTreeNodeCollection() {
                                        private static final long serialVersionUID = -4941224185260321214L;
                                    {add(draggedValue);} });
                        draggedValue.getData().setCodigoPadre(categ.getCodigo());
                    } else {
                        logger.info("No puede ingresar un padre dentro de su hijo");
                        Messagebox.show("No puede ingresar un padre dentro de su hijo", "JX_Market",
                                        Messagebox.OK, Messagebox.ERROR);
                    }
                }
            });
        }

        private boolean isAncestor(final CategoriaTreeNode p, CategoriaTreeNode c) {
            do {
                if (p == c)
                    return true;
            } while ((c = (CategoriaTreeNode) c.getParent()) != null);
            return false;
        }
    }

    @Listen("onClick = #btnCerrar")
    public void accionCerrar(final Event e) {
        wEAEC.detach();
    }

    @Listen("onClick = #btnGrabar")
    public void accionGrabar(final MouseEvent e) {
        final CategoriaTreeNode raiz = categoriaTreeNode;
        grabarData(raiz);

        desktop.getSession().setAttribute("actualizar", "actualizar");
        Utility.recargar(desktop, "eAConsultaCategoria.zul");

        wEAEC.detach();
    }

    private void grabarData(final CategoriaTreeNode _nodo)
    {
        final DTO_Categoria categ = _nodo.getData();
        final DTO_Input<DTO_Categoria> input = new DTO_Input<DTO_Categoria>();
        input.setVerbo(Constantes.V_REGISTER);
        input.setObject(categ);
        DTO_Output<DTO_Categoria> output = null;
        if (categ != null && !Constantes.TREE_EDITABLE_RAIZ.equals(categ.getNombre())) {
            final String strIdTxtCateg = new StringBuilder()
                .append(Constantes.TREE_EDITABLE_TEXTBOX).append(categ.getCodigo()).toString();
            final Textbox txtCategName = ((Textbox) wEAEC.getFellow(strIdTxtCateg));
            categ.setNombre(txtCategName.getValue());
            output = categoriaService.execute(input);
            if (Constantes.OK == output.getErrorCode()) {
                logger.debug("Categoria '" + categ.getNombre() + "' actualizada");
            }
        }

        if (_nodo.getChildren() != null && !_nodo.getChildren().isEmpty()) {
            final List<TreeNode<DTO_Categoria>> hijos = _nodo.getChildren();
            for (int i = 0; i < hijos.size(); i++) {
                final CategoriaTreeNode hijo = (CategoriaTreeNode) hijos.get(i);
                grabarData(hijo);
            }
        }
    }
}
