package pe.com.jx_market.utilities;

import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;

import pe.com.jx_market.domain.DTO_Categoria;

public class AdvancedTreeModel
    extends DefaultTreeModel<DTO_Categoria>
{

    private static final long serialVersionUID = -5513180500300189445L;

    DefaultTreeNode<DTO_Categoria> _root;

    public AdvancedTreeModel(CategoriaTreeNode contactTreeNode)
    {
        super(contactTreeNode);
        _root = contactTreeNode;
    }

    /**
     * remove the nodes which parent is <code>parent</code> with indexes
     * <code>indexes</code>
     * 
     * @param parent The parent of nodes are removed
     * @param indexFrom the lower index of the change range
     * @param indexTo the upper index of the change range
     * @throws IndexOutOfBoundsException - indexFrom < 0 or indexTo > number of
     *             parent's children
     */
    public void remove(DefaultTreeNode<DTO_Categoria> parent,
                       int indexFrom,
                       int indexTo)
        throws IndexOutOfBoundsException
    {
        DefaultTreeNode<DTO_Categoria> stn = parent;
        for (int i = indexTo; i >= indexFrom; i--)
            try {
                stn.getChildren().remove(i);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
    }

    public void remove(DefaultTreeNode<DTO_Categoria> target)
        throws IndexOutOfBoundsException
    {
        int index = 0;
        DefaultTreeNode<DTO_Categoria> parent = null;
        // find the parent and index of target
        parent = dfSearchParent(_root, target);
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index).equals(target)) {
                break;
            }
        }
        remove(parent, index, index);
    }

    /**
     * insert new nodes which parent is <code>parent</code> with indexes
     * <code>indexes</code> by new nodes <code>newNodes</code>
     * 
     * @param parent The parent of nodes are inserted
     * @param indexFrom the lower index of the change range
     * @param indexTo the upper index of the change range
     * @param newNodes New nodes which are inserted
     * @throws IndexOutOfBoundsException - indexFrom < 0 or indexTo > number of
     *             parent's children
     */
    public void insert(DefaultTreeNode<DTO_Categoria> parent,
                       int indexFrom,
                       int indexTo,
                       DefaultTreeNode<DTO_Categoria>[] newNodes)
        throws IndexOutOfBoundsException
    {
        DefaultTreeNode<DTO_Categoria> stn = parent;
        for (int i = indexFrom; i <= indexTo; i++) {
            try {
                stn.getChildren().add(i, newNodes[i - indexFrom]);
            } catch (Exception exp) {
                throw new IndexOutOfBoundsException("Out of bound: " + i + " while size=" + stn.getChildren().size());
            }
        }
    }

    /**
     * append new nodes which parent is <code>parent</code> by new nodes
     * <code>newNodes</code>
     * 
     * @param parent The parent of nodes are appended
     * @param newNodes New nodes which are appended
     */
    public void add(DefaultTreeNode<DTO_Categoria> parent,
                    DefaultTreeNode<DTO_Categoria>[] newNodes)
    {
        DefaultTreeNode<DTO_Categoria> stn = (DefaultTreeNode<DTO_Categoria>) parent;

        for (int i = 0; i < newNodes.length; i++)
            stn.getChildren().add(newNodes[i]);

    }

    private DefaultTreeNode<DTO_Categoria> dfSearchParent(DefaultTreeNode<DTO_Categoria> node,
                                                          DefaultTreeNode<DTO_Categoria> target)
    {
        if (node.getChildren() != null && node.getChildren().contains(target)) {
            return node;
        } else {
            int size = getChildCount(node);
            for (int i = 0; i < size; i++) {
                DefaultTreeNode<DTO_Categoria> parent = dfSearchParent(
                                (DefaultTreeNode<DTO_Categoria>) getChild(node, i), target);
                if (parent != null) {
                    return parent;
                }
            }
        }
        return null;
    }

}
