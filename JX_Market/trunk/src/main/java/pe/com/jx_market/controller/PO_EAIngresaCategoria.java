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

import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.utilities.AdvancedTreeModel;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.CategoriaTreeNode;
import pe.com.jx_market.utilities.CategoriaTreeNodeCollection;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


public class PO_EAIngresaCategoria
    extends SelectorComposer<Window>
{
    private static final long serialVersionUID = 6046074628719265175L;

    static Log logger = LogFactory.getLog(PO_EAIngresaCategoria.class);
    @Autowired
    private BusinessService<DTO_Categoria> categoriaService;
    private DTO_Empresa empresa;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Tree tree;
    @Wire
    private Window wEAIC;
    private CategoriaTreeNode categoriaTreeNode;
    private AdvancedTreeModel categoriaTreeModel;
    private int seqTxtId;

    @SuppressWarnings("unchecked")
    @Override
    public void doAfterCompose(final Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);

        categoriaService = (BusinessService<DTO_Categoria>) ContextLoader.getService(comp, "categoriaService");

        empresa = (DTO_Empresa) desktop.getSession().getAttribute("empresa");

        categoriaTreeNode = listarCategorias();
        categoriaTreeModel = new AdvancedTreeModel(categoriaTreeNode);
        tree.setItemRenderer(new CategoriaTreeRenderer());
        tree.setModel(categoriaTreeModel);
        tree.getRoot().setVisible(true);
        seqTxtId = 1;
    }

    public CategoriaTreeNode listarCategorias()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final ServiceInput<DTO_Categoria> input = new ServiceInput<DTO_Categoria>(cat);
        input.setAccion(Constantes.V_LIST);
        final ServiceOutput<DTO_Categoria> output = categoriaService.execute(input);
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
            hl.setSclass("h-inline-block");
            hl.appendChild(new Image("/widgets/tree/dynamic_tree/img/" + categ.getImagen()));
            final Treecell treeCell = new Treecell();
            treeCell.appendChild(hl);
            dataRow.appendChild(treeCell);

            if ((categ.getCodigo() != null && categ.getCodigo() > 0 )
                            || Constantes.TREE_EDITABLE_RAIZ.equals(categ.getNombre())) {
                hl.appendChild(new Label(categ.getNombre()));

                final Treecell treeCell2 = new Treecell();
                if (!Constantes.TREE_EDITABLE_RAIZ.equals(categ.getNombre())) {
                    final Hlayout h2 = new Hlayout();
                    h2.appendChild(new Label(categ.getEstado() ? Constantes.STATUS_ACTIVO : Constantes.STATUS_INACTIVO));
                    treeCell2.appendChild(h2);
                }
                dataRow.appendChild(treeCell2);

                if (categ.getEstado() != null && categ.getEstado()
                                || Constantes.TREE_EDITABLE_RAIZ.equals(categ.getNombre())) {
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
                            final DTO_Categoria newCateg = new DTO_Categoria();
                            categoriaTreeModel.add((CategoriaTreeNode) treeItem.getValue(),
                                    new CategoriaTreeNodeCollection()
                                    {
                                        private static final long serialVersionUID = -4941224185260321214L;
                                        {
                                            add(new CategoriaTreeNode(newCateg, new CategoriaTreeNodeCollection(), true));
                                        }
                                    });
                            newCateg.setCodigoPadre(categ.getCodigo());
                        }
                    });
                }
            } else {
                final Textbox textBoxCat = new Textbox();
                textBoxCat.setId(new StringBuilder(Constantes.TREE_EDITABLE_TEXTBOX)
                                    .append(-seqTxtId).toString());
                hl.appendChild(textBoxCat);
                categ.setCodigo(-seqTxtId); //codigo temporal para marcar que es nueva categoria
                seqTxtId++;
            }
        }
    }

    @Listen("onClick = #btnCerrar")
    public void accionCerrar(final Event e) {
        wEAIC.detach();
    }

    @Listen("onClick = #btnGrabar")
    public void accionGrabar(final MouseEvent e) {
        final CategoriaTreeNode raiz = categoriaTreeNode;
        grabarData(raiz);

        desktop.getSession().setAttribute("actualizar", "actualizar");
        ContextLoader.recargar(desktop, "eAConsultaCategoria.zul");

        wEAIC.detach();
    }

    private void grabarData(final CategoriaTreeNode _nodo)
    {
        final DTO_Categoria categ = _nodo.getData();
        final ServiceInput<DTO_Categoria> input = new ServiceInput<DTO_Categoria>();
        input.setAccion(Constantes.V_REGISTER);
        input.setObject(categ);
        ServiceOutput<DTO_Categoria> output = null;
        if (categ != null && categ.getCodigo() != null && categ.getCodigo() < 0) {
            final String strIdTxtCateg = new StringBuilder(Constantes.TREE_EDITABLE_TEXTBOX)
                .append(categ.getCodigo()).toString();
            final Textbox txtCategName = ((Textbox) wEAIC.getFellow(strIdTxtCateg));
            categ.setNombre(txtCategName.getValue());
            categ.setEmpresa(empresa.getCodigo());
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
