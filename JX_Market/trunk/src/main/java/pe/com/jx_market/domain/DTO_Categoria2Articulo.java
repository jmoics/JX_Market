package pe.com.jx_market.domain;

import java.io.Serializable;
import java.util.List;

public class DTO_Categoria2Articulo
    implements Serializable
{
    private Integer id;
    private Integer categoryId;
    private Integer productId;
    private List<DTO_Categoria> categories;

    public Integer getCategoryId()
    {
        return categoryId;
    }
    public void setCategoryId(final Integer categoryId)
    {
        this.categoryId = categoryId;
    }
    public Integer getProductId()
    {
        return productId;
    }
    public void setProductId(final Integer productId)
    {
        this.productId = productId;
    }
    public List<DTO_Categoria> getCategories()
    {
        return categories;
    }
    public void setCategories(final List<DTO_Categoria> categories)
    {
        this.categories = categories;
    }
    public Integer getId()
    {
        return id;
    }
    public void setId(final Integer id)
    {
        this.id = id;
    }
}
