package pe.com.jx_market.domain;

import java.io.Serializable;

/**
  * TODO comment!
  *
  * @author jcuevas
  * @version $Id$
  */
public class DTO_ProductImage
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
    private String imageName;
    /**
     *
     */
    private Integer productId;
    /**
     *
     */
    private boolean defaul;
    /**
     *
     */
    private boolean active;
    /**
     *
     */
    private byte[] image;

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
     * Getter method for the variable {@link #imageName}.
     *
     * @return value of variable {@link #imageName}
     */
    public final String getImageName()
    {
        return this.imageName;
    }

    /**
     * Setter method for variable {@link #imageName}.
     *
     * @param _imageName value for variable {@link #imageName}
     */

    public final void setImageName(final String _imageName)
    {
        this.imageName = _imageName;
    }

    /**
     * Getter method for the variable {@link #productId}.
     *
     * @return value of variable {@link #productId}
     */
    public final Integer getProductId()
    {
        return this.productId;
    }

    /**
     * Setter method for variable {@link #productId}.
     *
     * @param _productId value for variable {@link #productId}
     */

    public final void setProductId(final Integer _productId)
    {
        this.productId = _productId;
    }

    /**
     * Getter method for the variable {@link #defaul}.
     *
     * @return value of variable {@link #defaul}
     */
    public final boolean isDefaul()
    {
        return this.defaul;
    }

    /**
     * Setter method for variable {@link #defaul}.
     *
     * @param _defaul value for variable {@link #defaul}
     */

    public final void setDefaul(final boolean _defaul)
    {
        this.defaul = _defaul;
    }

    /**
     * Getter method for the variable {@link #active}.
     *
     * @return value of variable {@link #active}
     */
    public final boolean isActive()
    {
        return this.active;
    }

    /**
     * Setter method for variable {@link #active}.
     *
     * @param _active value for variable {@link #active}
     */

    public final void setActive(final boolean _active)
    {
        this.active = _active;
    }

    /**
     * Getter method for the variable {@link #image}.
     *
     * @return value of variable {@link #image}
     */
    public final byte[] getImage()
    {
        return this.image;
    }

    /**
     * Setter method for variable {@link #image}.
     *
     * @param _image value for variable {@link #image}
     */

    public final void setImage(final byte[] _image)
    {
        this.image = _image;
    }


}
