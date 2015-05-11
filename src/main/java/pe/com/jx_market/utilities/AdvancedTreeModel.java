package pe.com.jx_market.utilities;

import org.zkoss.zul.DefaultTreeModel;

import pe.com.jx_market.domain.DTO_Category;

public class AdvancedTreeModel
    extends DefaultTreeModel<DTO_Category>
{

    /**
     *
     */
    private static final long serialVersionUID = -5513180500300189445L;

    /**
     *
     */
    private final CategoryTreeNode root;

    /**
     * @param _contactTreeNode Tree Node.
     */
    public AdvancedTreeModel(final CategoryTreeNode _contactTreeNode)
    {
        super(_contactTreeNode);
        this.root = _contactTreeNode;
    }

    /**
     * remove the nodes which parent is <code>parent</code> with indexes
     * <code>indexes</code>.
     *
     * @param _parent The parent of nodes are removed
     * @param _indexFrom the lower index of the change range
     * @param _indexTo the upper index of the change range
     * @throws IndexOutOfBoundsException - indexFrom < 0 or indexTo > number of
     *             parent's children
     */
    public void remove(final CategoryTreeNode _parent,
                       final int _indexFrom,
                       final int _indexTo)
        throws IndexOutOfBoundsException
    {
        final CategoryTreeNode stn = _parent;
        for (int i = _indexTo; i >= _indexFrom; i--) {
            try {
                stn.getChildren().remove(i);
            } catch (final IndexOutOfBoundsException exp) {
                exp.printStackTrace();
            }
        }
    }

    /**
     * @param _target Element to delete.
     * @throws IndexOutOfBoundsException Exception
     */
    public void remove(final CategoryTreeNode _target)
        throws IndexOutOfBoundsException
    {
        int index = 0;
        CategoryTreeNode parent = null;
        // find the parent and index of target
        parent = dfSearchParent(this.root, _target);
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index).equals(_target)) {
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
     * <code>indexes</code> by new nodes <code>newNodes</code>.
     *
     * @param _parent The parent of nodes are inserted
     * @param _indexFrom the lower index of the change range
     * @param _indexTo the upper index of the change range
     * @param _newNodes New nodes which are inserted
     * @throws IndexOutOfBoundsException - indexFrom < 0 or indexTo > number of
     *             parent's children
     */
    public void insert(final CategoryTreeNode _parent,
                       final int _indexFrom,
                       final int _indexTo,
                       final CategoryTreeNodeCollection _newNodes)
        throws IndexOutOfBoundsException
    {
        final CategoryTreeNode stn = _parent;
        for (int i = _indexFrom; i <= _indexTo; i++) {
            try {
                stn.getChildren().add(i, _newNodes.get(i - _indexFrom));
            } catch (final IndexOutOfBoundsException exp) {
                throw new IndexOutOfBoundsException("Out of bound: " + i + " while size=" + stn.getChildren().size());
            }
        }
    }

    /**
     * append new nodes which parent is <code>parent</code> by new nodes
     * <code>newNodes</code>.
     *
     * @param _parent The parent of nodes are appended
     * @param _newNodes New nodes which are appended
     */
    public void add(final CategoryTreeNode _parent,
                    final CategoryTreeNodeCollection _newNodes)
    {
        final CategoryTreeNode stn = _parent;

        for (int i = 0; i < _newNodes.size(); i++) {
            if (stn.getChildren() != null) {
                stn.getChildren().add(_newNodes.get(i));
            } /*else {
                final CategoryTreeNode newNode = new CategoryTreeNode(stn.getData(), newNodes);
                final CategoryTreeNode grandParent = (CategoryTreeNode) stn.getParent();
                int index = 0;
                for (index = 0; index < grandParent.getChildCount(); index++) {
                    if (grandParent.getChildAt(index).equals(stn)) {
                        break;
                    }
                }
                remove(stn);
                grandParent.getChildren().add(index, newNode);
            }*/
        }

    }

    /**
     * @param _node Node.
     * @param _target Target.
     * @return Parent searched.
     */
    private CategoryTreeNode dfSearchParent(final CategoryTreeNode _node,
                                             final CategoryTreeNode _target)
    {
        CategoryTreeNode ret = null;
        if (_node.getChildren() != null && _node.getChildren().contains(_target)) {
            ret = _node;
        } else {
            final int size = getChildCount(_node);
            for (int i = 0; i < size; i++) {
                final CategoryTreeNode parent = dfSearchParent(
                                (CategoryTreeNode) getChild(_node, i), _target);
                if (parent != null) {
                    ret = parent;
                }
            }
        }
        return ret;
    }

}
