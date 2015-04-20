package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_ProductImage
    implements Serializable
{

    private Integer id;
    private Integer companyId;
    private String imageName;
    private Integer productId;
    private boolean defaul;
    private boolean active;
    private byte[] image;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(final Integer _companyId)
    {
        this.companyId = _companyId;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setImageName(final String imageName)
    {
        this.imageName = imageName;
    }

    public boolean isDefaul()
    {
        return defaul;
    }

    public void setDefaul(final boolean defaul)
    {
        this.defaul = defaul;
    }

    public Integer getProductId()
    {
        return productId;
    }

    public void setProductId(final Integer productId)
    {
        this.productId = productId;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(final byte[] image)
    {
        this.image = image;
    }


    public boolean isActive()
    {
        return active;
    }

    public void setActive(final boolean active)
    {
        this.active = active;
    }

}
