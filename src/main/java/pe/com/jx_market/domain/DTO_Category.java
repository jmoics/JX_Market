/**
 *
 */
package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author George
 *
 */
public class DTO_Category implements Serializable
{
    private static final long serialVersionUID = 7736453145474958927L;

    private Integer id;
    private Integer company;
    private String categoryName;
    private Integer categoryParentId;
    private Boolean active;
    private byte[] imagen;
    private List<DTO_Product> products;

    public DTO_Category() {
    }

    /**
     * Constructor solo utilizado para utilizar como raíz del arbol al editar.
     *
     * @param _nombre
     */
    public DTO_Category(final String _categoryName) {
        this.categoryName = _categoryName;
    }

    public Integer getId()
    {
        return id;
    }
    public void setId(final Integer id)
    {
        this.id = id;
    }
    public Integer getEmpresa()
    {
        return company;
    }
    public void setEmpresa(final Integer company)
    {
        this.company = company;
    }
    public String getCategoryName()
    {
        return categoryName;
    }
    public void setCategoryName(final String categoryName)
    {
        this.categoryName = categoryName;
    }

    public Integer getCategoryParentId()
    {
        return categoryParentId;
    }

    public void setCategoryParentId(final Integer categoryParentId)
    {
        this.categoryParentId = categoryParentId;
    }

    public byte[] getImagen()
    {
        return imagen;
    }

    public void setImagen(final byte[] imagen)
    {
        this.imagen = imagen;
    }

    public Boolean isActive()
    {
        return active;
    }

    public void setActive(final Boolean active)
    {
        this.active = active;
    }
    public List<DTO_Product> getProducts()
    {
        return products;
    }
    public void setProducts(final List<DTO_Product> products)
    {
        this.products = products;
    }

    @Override
    public String toString()
    {
        return this.categoryName;
    }
}
