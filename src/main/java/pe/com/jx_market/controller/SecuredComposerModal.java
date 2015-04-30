package pe.com.jx_market.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

@VariableResolver(DelegatingVariableResolver.class)
public abstract class SecuredComposerModal<T extends Component>
    extends SecuredComposer<T>
{

    private static final long serialVersionUID = 2403353020943263536L;
    private Component parent;
    /**
     * To use in a future to check access.
     */
    private String accessType;

    public Component getParent()
    {
        return parent;
    }

    public void setParent(final Component parent)
    {
        this.parent = parent;
    }

    public String getAccessType()
    {
        return accessType;
    }

    public void setAccessType(final String accessType)
    {
        this.accessType = accessType;
    }



}
