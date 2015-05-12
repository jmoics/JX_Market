package pe.com.jx_market.domain;

import java.io.Serializable;

/**
 * @author jcuevas
 *
 */
public class DTO_Atributo
    implements Serializable
{
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String descr;
    /**
     *
     */
    private Boolean active;
    /**
     *
     */
    private Boolean filtrable;
    /**
     *
     */
    private Integer categoryId;

    /**
     * @return Table Id.
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * @param _id Table Id.
     */
    public void setId(final Integer _id)
    {
        this.id = _id;
    }

    /**
     * @return Attribute Name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return Attribute Description.
     */
    public String getDescr()
    {
        return this.descr;
    }

    /**
     * @param _descr Attribute Description.
     */
    public void setDescr(final String _descr)
    {
        this.descr = _descr;
    }

    /**
     * @param _name Attribute Name.
     */
    public void setName(final String _name)
    {
        this.name = _name;
    }

    /**
     * @return Status.
     */
    public Boolean getActive()
    {
        return this.active;
    }

    /**
     * @param _active Status.
     */
    public void setActive(final Boolean _active)
    {
        this.active = _active;
    }

    /**
     * @return If will be filterable.
     */
    public Boolean getFiltrable()
    {
        return this.filtrable;
    }

    /**
     * @param _filtrable If will be filterable.
     */
    public void setFiltrable(final Boolean _filtrable)
    {
        this.filtrable = _filtrable;
    }


    /**
     * @return Category Id.
     */
    public Integer getCategoryId()
    {
        return this.categoryId;
    }


    /**
     * @param _categoryId Category Id.
     */
    public void setCategoryId(final Integer _categoryId)
    {
        this.categoryId = _categoryId;
    }
}
