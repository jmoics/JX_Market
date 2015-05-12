package pe.com.jx_market.domain;

import java.io.Serializable;

/**
 * @author jcuevas
 *
 */
public class DTO_Category2Product
    implements Serializable
{
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer categoryId;
    /**
     *
     */
    private Integer productId;

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
    /**
     * @return Product Id.
     */
    public Integer getProductId()
    {
        return this.productId;
    }
    /**
     * @param _productId Product Id.
     */
    public void setProductId(final Integer _productId)
    {
        this.productId = _productId;
    }
    /**
     * @return Product Id.
     */
    public Integer getId()
    {
        return this.id;
    }
    /**
     * @param _id Product Id.
     */
    public void setId(final Integer _id)
    {
        this.id = _id;
    }
}