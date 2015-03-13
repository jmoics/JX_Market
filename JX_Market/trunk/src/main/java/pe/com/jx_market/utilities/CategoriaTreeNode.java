package pe.com.jx_market.utilities;

import org.zkoss.zul.DefaultTreeNode;

import pe.com.jx_market.domain.DTO_Categoria;


@SuppressWarnings("hiding")
public class CategoriaTreeNode<T> extends DefaultTreeNode<T> {
    private static final long serialVersionUID = -8085873079938209759L;
     
    // Node Control the default open
    private boolean open = false;
 
    public CategoriaTreeNode(T data, CategoriaTreeNodeCollection<T> children, boolean open) {
        super(data, children);
        this.setOpen(open);
    }
 
    public CategoriaTreeNode(T data, CategoriaTreeNodeCollection<T> children) {
        super(data, children);
    }
 
    public CategoriaTreeNode(T data) {
        super(data);
    }
 
    public boolean isOpen() {
        return open;
    }
 
    public void setOpen(boolean open) {
        this.open = open;
    }
}
