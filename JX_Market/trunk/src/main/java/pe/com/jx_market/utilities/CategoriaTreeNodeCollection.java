package pe.com.jx_market.utilities;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import pe.com.jx_market.domain.DTO_Categoria;

public class CategoriaTreeNodeCollection
    extends LinkedList<CategoriaTreeNode>
{
    private static final long serialVersionUID = 5118244589333582131L;

    protected void construirJerarquia(final Collection<DTO_Categoria> childs,
                                      final DTO_Categoria root,
                                      final Set<Integer> setPadres)
    {
        for (final DTO_Categoria child : childs) {
            if ((child).getCodigoPadre().equals((root).getCodigo())) {
                if (setPadres.contains(child.getCodigo())) {
                    add(new CategoriaTreeNode(child,
                                    new CategoriaTreeNodeCollection()
                                    {
                                        private static final long serialVersionUID = -525877166192606505L;
                                        {
                                            construirJerarquia(childs, child, setPadres);
                                        }
                                    }));
                } else {
                    add(new CategoriaTreeNode(child, new CategoriaTreeNodeCollection()));
                }
            }
        }
    }
}
