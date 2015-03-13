package pe.com.jx_market.controller;

import java.util.ArrayList;
import java.util.Collection;
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
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import pe.com.jx_market.utilities.AdvancedTreeModel;
import pe.com.jx_market.domain.DTO_Categoria;
import pe.com.jx_market.domain.DTO_Empresa;
import pe.com.jx_market.service.Constantes;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.CategoriaTreeNode;
import pe.com.jx_market.utilities.CategoriaTreeNodeCollection;
import pe.com.jx_market.utilities.DTO_Input;
import pe.com.jx_market.utilities.DTO_Output;


public class PO_EAConsultaCategoria
    extends SelectorComposer<Window>
{
    private static final long serialVersionUID = -1481359887612974294L;
    
    static Log logger = LogFactory.getLog(PO_EAConsultaCategoria.class);
    @Autowired
    private BusinessService articuloService, categoriaService;
    private DTO_Empresa empresa;
    @WireVariable
    private Desktop desktop;
    @Wire
    private Tree tree;
    private CategoriaTreeNode<DTO_Categoria> categoriaTreeNode;
    private AdvancedTreeModel categoriaTreeModel;
    
    @Override
    public void doAfterCompose(Window comp)
        throws Exception
    {
        super.doAfterCompose(comp);
        
        articuloService = Utility.getService(comp, "articuloService");
        categoriaService = Utility.getService(comp, "categoriaService");

        empresa = (DTO_Empresa) desktop.getSession().getAttribute("empresa");

        listarCategorias();
        categoriaTreeModel = new AdvancedTreeModel(categoriaTreeNode);
        tree.setModel(categoriaTreeModel);
    }
    
    public void listarCategorias()
    {
        final DTO_Categoria cat = new DTO_Categoria();
        cat.setEmpresa(empresa.getCodigo());
        final DTO_Input input = new DTO_Input(cat);
        input.setVerbo(Constantes.V_LIST);
        final DTO_Output output = categoriaService.execute(input);
        if (output.getErrorCode() == Constantes.OK) {
            //alertaInfo("", "Exito al cargar categorias", null);
            final List<DTO_Categoria> lstCat = output.getLista();
            construirArbolCategorias(lstCat);
        } else {
            //alertaError("Error inesperado, por favor catege al administrador", "Error cargando categorias", null);
        }
    }

    private void construirArbolCategorias(List<DTO_Categoria> _categorias)
    {
        Map<Integer, DTO_Categoria> mapCateg = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> roots = new TreeMap<Integer, DTO_Categoria>();
        final Map<Integer, DTO_Categoria> childs = new TreeMap<Integer, DTO_Categoria>();
        final Set<Integer> setPadres = new HashSet<Integer>();
        for (DTO_Categoria cat : _categorias) {
            mapCateg.put(cat.getCodigo(), cat);
            setPadres.add(cat.getCodigoPadre());
            if (cat.getCodigoPadre() == null) {
                roots.put(cat.getCodigo(), cat);
            } else {
                childs.put(cat.getCodigo(), cat);
            }
        }
        
        categoriaTreeNode = new CategoriaTreeNode<DTO_Categoria>(null, 
                new CategoriaTreeNodeCollection<DTO_Categoria>() {
                    private static final long serialVersionUID = -8249078122595873454L;
                    {
                        for (final DTO_Categoria root : roots.values()) {
                            if (!setPadres.contains(root.getCodigo())) {
                                add(new CategoriaTreeNode<DTO_Categoria>(root));
                            } else {
                                add(new CategoriaTreeNode<DTO_Categoria>(root, 
                                            new CategoriaTreeNodeCollection<DTO_Categoria>(){
                                                private static final long serialVersionUID = -5643408533240445491L;
                                                {
                                                    construirJerarquia(childs.values(), root, setPadres);
                                                }
                                            }));
                            }
                        }
                    }
                }, 
                true);
    }
}
