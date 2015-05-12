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
public class DTO_Product
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
    private List<DTO_Category> categories;
    /**
     *
     */
    private String productName;
    /**
     *
     */
    private String productDescription;
    /**
     *
     */
    private DTO_TradeMark tradeMark;
    /**
     *
     */
    private Boolean active;
    /**
     *
     */
    private List<DTO_ProductImage> images;

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
     * Getter method for the variable {@link #companyId}.
     *
     * @return value of variable {@link #companyId}
     */
    public final Integer getCompanyId()
    {
        return this.companyId;
    }

    /**
     * Setter method for variable {@link #companyId}.
     *
     * @param _companyId value for variable {@link #companyId}
     */

    public final void setCompanyId(final Integer _companyId)
    {
        this.companyId = _companyId;
    }

    /**
     * Getter method for the variable {@link #categories}.
     *
     * @return value of variable {@link #categories}
     */
    public final List<DTO_Category> getCategories()
    {
        return this.categories;
    }

    /**
     * Setter method for variable {@link #categories}.
     *
     * @param _categories value for variable {@link #categories}
     */

    public final void setCategories(final List<DTO_Category> _categories)
    {
        this.categories = _categories;
    }

    /**
     * Getter method for the variable {@link #productName}.
     *
     * @return value of variable {@link #productName}
     */
    public final String getProductName()
    {
        return this.productName;
    }

    /**
     * Setter method for variable {@link #productName}.
     *
     * @param _productName value for variable {@link #productName}
     */

    public final void setProductName(final String _productName)
    {
        this.productName = _productName;
    }

    /**
     * Getter method for the variable {@link #productDescription}.
     *
     * @return value of variable {@link #productDescription}
     */
    public final String getProductDescription()
    {
        return this.productDescription;
    }

    /**
     * Setter method for variable {@link #productDescription}.
     *
     * @param _productDescription value for variable {@link #productDescription}
     */

    public final void setProductDescription(final String _productDescription)
    {
        this.productDescription = _productDescription;
    }

    /**
     * Getter method for the variable {@link #tradeMark}.
     *
     * @return value of variable {@link #tradeMark}
     */
    public final DTO_TradeMark getTradeMark()
    {
        return this.tradeMark;
    }

    /**
     * Setter method for variable {@link #tradeMark}.
     *
     * @param _tradeMark value for variable {@link #tradeMark}
     */

    public final void setTradeMark(final DTO_TradeMark _tradeMark)
    {
        this.tradeMark = _tradeMark;
    }

    /**
     * Getter method for the variable {@link #active}.
     *
     * @return value of variable {@link #active}
     */
    public final Boolean getActive()
    {
        return this.active;
    }

    /**
     * Setter method for variable {@link #active}.
     *
     * @param _active value for variable {@link #active}
     */

    public final void setActive(final Boolean _active)
    {
        this.active = _active;
    }

    /**
     * Getter method for the variable {@link #images}.
     *
     * @return value of variable {@link #images}
     */
    public final List<DTO_ProductImage> getImages()
    {
        return this.images;
    }

    /**
     * Setter method for variable {@link #images}.
     *
     * @param _images value for variable {@link #images}
     */

    public final void setImages(final List<DTO_ProductImage> _images)
    {
        this.images = _images;
    }
}
