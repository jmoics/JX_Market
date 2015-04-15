package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_Category2Product
    implements Serializable
{
    private Integer id;
    private Integer categoryId;
    private Integer productId;

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
    public Integer getId()
    {
        return id;
    }
    public void setId(final Integer id)
    {
        this.id = id;
    }
}
