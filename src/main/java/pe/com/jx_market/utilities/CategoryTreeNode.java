package pe.com.jx_market.utilities;

import org.zkoss.zul.DefaultTreeNode;

import pe.com.jx_market.domain.DTO_Categoria;

public class CategoryTreeNode
    extends DefaultTreeNode<DTO_Categoria>
{

    private static final long serialVersionUID = -8085873079938209759L;

    // Node Control the default open
    private boolean open = false;

    public CategoryTreeNode(final DTO_Categoria data,
                             final CategoryTreeNodeCollection children,
                             final boolean open)
    {
        super(data, children);
        this.setOpen(open);
    }

    public CategoryTreeNode(final DTO_Categoria data,
                             final CategoryTreeNodeCollection children)
    {
        super(data, children);
    }

    public CategoryTreeNode(final DTO_Categoria data)
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
