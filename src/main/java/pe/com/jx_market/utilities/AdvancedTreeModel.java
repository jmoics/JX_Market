package pe.com.jx_market.utilities;

import org.zkoss.zul.DefaultTreeModel;

import pe.com.jx_market.domain.DTO_Categoria;

public class AdvancedTreeModel
    extends DefaultTreeModel<DTO_Categoria>
{

    private static final long serialVersionUID = -5513180500300189445L;

    CategoryTreeNode _root;

    public AdvancedTreeModel(final CategoryTreeNode contactTreeNode)
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
    public void remove(final CategoryTreeNode parent,
                       final int indexFrom,
                       final int indexTo)
        throws IndexOutOfBoundsException
    {
        final CategoryTreeNode stn = parent;
        for (int i = indexTo; i >= indexFrom; i--)
            try {
                stn.getChildren().remove(i);
            } catch (final Exception exp) {
                exp.printStackTrace();
            }
    }

    public void remove(final CategoryTreeNode target)
        throws IndexOutOfBoundsException
    {
        int index = 0;
        CategoryTreeNode parent = null;
        // find the parent and index of target
        parent = dfSearchParent(_root, target);
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index).equals(target)) {
                break;
            }
        }
        remove(parent, index, index);
        // Remover el contenedor de childs (children) si no existen más una vez removido.
        /*if (parent.getChildren().isEmpty()) {
            final CategoryTreeNode newParent = new CategoryTreeNode(parent.getData());
            final CategoryTreeNode grandParent = dfSearchParent(_root, parent);;
            for (index = 0; index < grandParent.getChildCount(); index++) {
                if (grandParent.getChildAt(index).equals(parent)) {
                    break;
                }
            }
            remove(grandParent, index, index);
            grandParent.getChildren().add(index, newParent);
        }*/
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
    public void insert(final CategoryTreeNode parent,
                       final int indexFrom,
                       final int indexTo,
                       final CategoryTreeNodeCollection newNodes)
        throws IndexOutOfBoundsException
    {
        final CategoryTreeNode stn = parent;
        for (int i = indexFrom; i <= indexTo; i++) {
            try {
                stn.getChildren().add(i, newNodes.get(i - indexFrom));
            } catch (final Exception exp) {
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
    public void add(final CategoryTreeNode parent,
                    final CategoryTreeNodeCollection newNodes)
    {
        final CategoryTreeNode stn = parent;

        for (int i = 0; i < newNodes.size(); i++) {
            if (stn.getChildren() != null) {
                stn.getChildren().add(newNodes.get(i));
            } else {
                /*final CategoryTreeNode newNode = new CategoryTreeNode(stn.getData(), newNodes);
                final CategoryTreeNode grandParent = (CategoryTreeNode) stn.getParent();
                int index = 0;
                for (index = 0; index < grandParent.getChildCount(); index++) {
                    if (grandParent.getChildAt(index).equals(stn)) {
                        break;
                    }
                }
                remove(stn);
                grandParent.getChildren().add(index, newNode);*/
            }
        }

    }

    private CategoryTreeNode dfSearchParent(final CategoryTreeNode node,
                                             final CategoryTreeNode target)
    {
        if (node.getChildren() != null && node.getChildren().contains(target)) {
            return node;
        } else {
            final int size = getChildCount(node);
            for (int i = 0; i < size; i++) {
                final CategoryTreeNode parent = dfSearchParent(
                                (CategoryTreeNode) getChild(node, i), target);
                if (parent != null) {
                    return parent;
                }
            }
        }
        return null;
    }

}
