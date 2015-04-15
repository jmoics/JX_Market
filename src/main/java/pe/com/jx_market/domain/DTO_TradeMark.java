package pe.com.jx_market.domain;

import java.io.Serializable;


public class DTO_TradeMark
    implements Serializable
{
    private Integer id;
    private Integer empresa;
    private String marcaName;
    private Boolean active;
    private byte[] imagen;
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
        return empresa;
    }

    public void setEmpresa(final Integer empresa)
    {
        this.empresa = empresa;
    }

    public String getMarcaName()
    {
        return marcaName;
    }

    public void setMarcaName(final String marcaName)
    {
        this.marcaName = marcaName;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(final Boolean active)
    {
        this.active = active;
    }

    public byte[] getImagen()
    {
        return imagen;
    }

    public void setImagen(final byte[] imagen)
    {
        this.imagen = imagen;
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
