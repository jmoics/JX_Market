package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public class ModuleAccessUI
    implements Serializable
{

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer moduleId;
    /**
     *
     */
    private String nameUi;
    /**
     *
     */
    private List<ModuleWidgetUI> widgetsUis;

    /**
     *
     */
    private Map<String, ModuleWidgetUI> widgetsUisMap;

    /**
     * Getter method for the variable {@link #id}.
     *
     * @return value of variable {@link #id}
     */
    public final Integer getId()
    {
        return this.id;
    }

    /**
     * Setter method for variable {@link #id}.
     *
     * @param _id value for variable {@link #id}
     */
    public final void setId(final Integer _id)
    {
        this.id = _id;
    }

    /**
     * Getter method for the variable {@link #moduleId}.
     *
     * @return value of variable {@link #moduleId}
     */
    public final Integer getModuleId()
    {
        return this.moduleId;
    }

    /**
     * Setter method for variable {@link #moduleId}.
     *
     * @param _moduleId value for variable {@link #moduleId}
     */
    public final void setModuleId(final Integer _moduleId)
    {
        this.moduleId = _moduleId;
    }

    /**
     * Getter method for the variable {@link #nameUi}.
     *
     * @return value of variable {@link #nameUi}
     */
    public final String getNameUi()
    {
        return this.nameUi;
    }

    /**
     * Setter method for variable {@link #nameUi}.
     *
     * @param _nameUi value for variable {@link #nameUi}
     */
    public final void setNameUi(final String _nameUi)
    {
        this.nameUi = _nameUi;
    }

    /**
     * Getter method for the variable {@link #widgetsUis}.
     *
     * @return value of variable {@link #widgetsUis}
     */
    public final List<ModuleWidgetUI> getWidgetsUis()
    {
        return this.widgetsUis;
    }

    /**
     * Setter method for variable {@link #widgetsUis}.
     *
     * @param _widgetsUis value for variable {@link #widgetsUis}
     */
    public final void setWidgetsUis(final List<ModuleWidgetUI> _widgetsUis)
    {
        this.widgetsUis = _widgetsUis;
    }

    /**
     * Getter method for the variable {@link #widgetsUisSet}.
     *
     * @return value of variable {@link #widgetsUisSet}
     */
    public final Map<String, ModuleWidgetUI> getWidgetsUisMap()
    {
        return this.widgetsUisMap;
    }

    /**
     * Setter method for variable {@link #widgetsUisMap}.
     *
     */
    public final void setWidgetsUisMap()
    {
        this.widgetsUisMap = new HashMap<String, ModuleWidgetUI>();
        for (final ModuleWidgetUI wu : this.widgetsUis) {
            this.widgetsUisMap.put(wu.getNameUi(), wu);
        }
    }
}
