package pe.com.jx_market.domain;

import java.io.Serializable;


public class DTO_TradeMark
    implements Serializable
{
    private Integer id;
    private Integer company;
    private String tradeMarkName;
    private Boolean active;
    private byte[] image;
    private String imageName;

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

    public String getTradeMarkName()
    {
        return tradeMarkName;
    }

    public void setTradeMarkName(final String tradeMarkName)
    {
        this.tradeMarkName = tradeMarkName;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(final Boolean active)
    {
        this.active = active;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(final byte[] image)
    {
        this.image = image;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setImageName(final String imageName)
    {
        this.imageName = imageName;
    }

}
