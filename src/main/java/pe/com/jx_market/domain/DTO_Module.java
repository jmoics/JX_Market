package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jcuevas
 *
 */
public class DTO_Module
    implements Serializable
{
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String moduleDescription;
    /**
     *
     */
    private String moduleResource;
    /**
     *
     */
    private Boolean activeConnection;
    /**
     *
     */
    private List<ModuleAccessUI> accessUis;
    /**
     *
     */
    private Map<String, ModuleAccessUI> accessUisMap;

    /**
     * @return the id
     */
    public final Integer getId()
    {
        return this.id;
    }

    /**
     * @param _id the id to set
     */
    public final void setId(final Integer _id)
    {
        this.id = _id;
    }

    /**
     * @return the companyId
     */
    public final Integer getCompanyId()
    {
        return this.companyId;
    }

    /**
     * @param _companyId the companyId to set
     */
    public final void setCompanyId(final Integer _companyId)
    {
        this.companyId = _companyId;
    }

    /**
     * @return the moduleDescription
     */
    public final String getModuleDescription()
    {
        return this.moduleDescription;
    }

    /**
     * @param _moduleDescription the moduleDescription to set
     */
    public final void setModuleDescription(final String _moduleDescription)
    {
        this.moduleDescription = _moduleDescription;
    }

    /**
     * @return the moduleResource
     */
    public final String getModuleResource()
    {
        return this.moduleResource;
    }

    /**
     * @param _moduleResource the moduleResource to set
     */
    public final void setModuleResource(final String _moduleResource)
    {
        this.moduleResource = _moduleResource;
    }

    /**
     * @return the activeConnection
     */
    public final Boolean getActiveConnection()
    {
        return this.activeConnection;
    }

    /**
     * @param _activeConnection the activeConnection to set
     */
    public final void setActiveConnection(final Boolean _activeConnection)
    {
        this.activeConnection = _activeConnection;
    }


    /**
     * Getter method for the variable {@link #accessUis}.
     *
     * @return value of variable {@link #accessUis}
     */
    public final List<ModuleAccessUI> getAccessUis()
    {
        return this.accessUis;
    }


    /**
     * Setter method for variable {@link #accessUis}.
     *
     * @param _accessUis value for variable {@link #accessUis}
     */
    public final void setAccessUis(final List<ModuleAccessUI> _accessUis)
    {
        this.accessUis = _accessUis;
    }

    /**
     * Getter method for the variable {@link #accessUisMap}.
     *
     * @return value of variable {@link #accessUisMap}
     */
    public final Map<String, ModuleAccessUI> getAccessUisMap()
    {
        return this.accessUisMap;
    }

    /**
     * Setter method for variable {@link #accessUisMap}.
     *
     */
    public final void setAccessUisMap()
    {
        this.accessUisMap = new HashMap<String, ModuleAccessUI>();
        for (final ModuleAccessUI wu : this.accessUis) {
            this.accessUisMap.put(wu.getNameUi(), wu);
            wu.setWidgetsUisMap();
        }
    }

}
