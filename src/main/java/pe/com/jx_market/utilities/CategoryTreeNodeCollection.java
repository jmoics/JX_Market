package pe.com.jx_market.utilities;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import pe.com.jx_market.domain.DTO_Category;

public class CategoryTreeNodeCollection
    extends LinkedList<CategoryTreeNode>
{
    private static final long serialVersionUID = 5118244589333582131L;

    protected void construirJerarquia(final Collection<DTO_Category> childs,
                                      final DTO_Category root,
                                      final Set<Integer> setPadres,
                                      final Boolean _editable)
    {
        for (final DTO_Category child : childs) {
            if ((child).getCategoryParentId().equals((root).getId())) {
                if (setPadres.contains(child.getId())) {
                    add(new CategoryTreeNode(child,
                                    new CategoryTreeNodeCollection()
                                    {
                                        private static final long serialVersionUID = -525877166192606505L;
                                        {
                                            construirJerarquia(childs, child, setPadres, _editable);
                                        }
                                    }, true));
                } else {
                    if (_editable) {
                        add(new CategoryTreeNode(child, new CategoryTreeNodeCollection(), true));
                    } else {
                        add(new CategoryTreeNode(child));
                    }
                }
            }
        }
    }
}
