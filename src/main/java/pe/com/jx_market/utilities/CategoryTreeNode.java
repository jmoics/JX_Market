package pe.com.jx_market.utilities;

import org.zkoss.zul.DefaultTreeNode;

import pe.com.jx_market.domain.DTO_Category;

public class CategoryTreeNode
    extends DefaultTreeNode<DTO_Category>
{

    private static final long serialVersionUID = -8085873079938209759L;

    // Node Control the default open
    private boolean open = false;

    public CategoryTreeNode(final DTO_Category data,
                             final CategoryTreeNodeCollection children,
                             final boolean open)
    {
        super(data, children);
        this.setOpen(open);
    }

    public CategoryTreeNode(final DTO_Category data,
                             final CategoryTreeNodeCollection children)
    {
        super(data, children);
    }

    public CategoryTreeNode(final DTO_Category data)
    {
        super(data);
    }

    public boolean isOpen()
    {
        return open;
    }

    public void setOpen(final boolean open)
    {
        this.open = open;
    }

}
