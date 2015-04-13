package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_ArticuloImage
    implements Serializable
{

    private Integer id;
    private Integer company;
    private String imageName;
    private Integer productId;
    private boolean imageDefault;
    private byte[] image;

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public Integer getCompany()
    {
        return company;
    }

    public void setCompany(final Integer company)
    {
        this.company = company;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setImageName(final String imageName)
    {
        this.imageName = imageName;
    }

    public boolean isImageDefault()
    {
        return imageDefault;
    }

    public void setImageDefault(final boolean imageDefault)
    {
        this.imageDefault = imageDefault;
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

}
