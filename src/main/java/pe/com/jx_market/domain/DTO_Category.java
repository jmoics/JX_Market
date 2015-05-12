/**
 *
 */
package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.List;


/**
 * @author jcuevas
 *
 */
public class DTO_Category
    implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 7736453145474958927L;

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
    private String categoryName;
    /**
     *
     */
    private Integer categoryParentId;
    /**
     *
     */
    private Boolean active;
    /**
     *
     */
    private byte[] imagen;
    /**
     *
     */
    private List<DTO_Product> products;

    /**
     *
     */
    public DTO_Category()
    {
    }

    /**
     * Constructor solo para utilizar como raíz del arbol al editar.
     *
     * @param _categoryName Category Name.
     */
    public DTO_Category(final String _categoryName)
    {
        this.categoryName = _categoryName;
    }

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
     * @return Company Id.
     */
    public Integer getCompanyId()
    {
        return this.companyId;
    }

    /**
     * @param _companyId Company Id.
     */
    public void setCompanyId(final Integer _companyId)
    {
        this.companyId = _companyId;
    }

    /**
     * @return Category Name.
     */
    public String getCategoryName()
    {
        return this.categoryName;
    }

    /**
     * @param _categoryName Category Name.
     */
    public void setCategoryName(final String _categoryName)
    {
        this.categoryName = _categoryName;
    }

    /**
     * @return Category parent Id.
     */
    public Integer getCategoryParentId()
    {
        return this.categoryParentId;
    }

    /**
     * @param _categoryParentId Category parent Id.
     */
    public void setCategoryParentId(final Integer _categoryParentId)
    {
        this.categoryParentId = _categoryParentId;
    }

    /**
     * @return Image.
     */
    public byte[] getImagen()
    {
        return this.imagen;
    }

    /**
     * @param _imagen Image.
     */
    public void setImagen(final byte[] _imagen)
    {
        this.imagen = _imagen;
    }

    /**
     * @return Status.
     */
    public Boolean isActive()
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
     * @return Related Products.
     */
    public List<DTO_Product> getProducts()
    {
        return this.products;
    }

    /**
     * @param _products Related Products.
     */
    public void setProducts(final List<DTO_Product> _products)
    {
        this.products = _products;
    }

    @Override
    public String toString()
    {
        return this.categoryName;
    }
}
