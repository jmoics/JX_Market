package pe.com.jx_market.utilities;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import org.zkoss.zul.DefaultTreeNode;

import pe.com.jx_market.domain.DTO_Categoria;


@SuppressWarnings("hiding")
public class CategoriaTreeNodeCollection<T>
    extends LinkedList<DefaultTreeNode<T>>
{
    private static final long serialVersionUID = 5118244589333582131L;

    protected void construirJerarquia(final Collection<DTO_Categoria> childs, 
                                      final DTO_Categoria root,
                                      final Set<Integer> setPadres) {
        for (final DTO_Categoria child : childs) {
            if (child.getCodigoPadre().equals(root.getCodigo())) {
                if (setPadres.contains(child.getCodigo())) {
                    add((DefaultTreeNode<T>) new CategoriaTreeNode<DTO_Categoria>(child, 
                                new CategoriaTreeNodeCollection<DTO_Categoria>(){
                                    private static final long serialVersionUID = -525877166192606505L;
                                    {
                                        construirJerarquia(childs, child, setPadres);
                                    }
                                }));
                } else {
                    add((DefaultTreeNode<T>) new CategoriaTreeNode<DTO_Categoria>(child));
                }
            }
        }
    }
}
