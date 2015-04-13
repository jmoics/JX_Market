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
public class DTO_Articulo implements Serializable
{
    private Integer id;
    private Integer empresa;
    private List<DTO_Categoria> categories;
    private String productName;
    private String productDescription;
    private DTO_Marca marca;
    //private BigDecimal precio;
    //private Integer stock;
    private Boolean activo;
    private List<DTO_ArticuloImage> images;

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
    public List<DTO_Categoria> getCategories()
    {
        return categories;
    }
    public void setCategories(final List<DTO_Categoria> categories)
    {
        this.categories = categories;
    }
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(final String productName)
    {
        this.productName = productName;
    }
    public String getProductDescription()
    {
        return productDescription;
    }
    public void setProductDescription(final String productDescription)
    {
        this.productDescription = productDescription;
    }
    /*public String getMarca()
    {
        return marca;
    }
    public void setMarca(String marca)
    {
        this.marca = marca;
    }
    public BigDecimal getPrecio()
    {
        return precio;
    }
    public void setPrecio(BigDecimal precio)
    {
        this.precio = precio;
    }
    public Integer getStock()
    {
        return stock;
    }
    public void setStock(Integer stock)
    {
        this.stock = stock;
    }*/
    public Boolean getActivo()
    {
        return activo;
    }
    public void setActivo(final Boolean activo)
    {
        this.activo = activo;
    }
    public DTO_Marca getMarca()
    {
        return marca;
    }
    public void setMarca(final DTO_Marca marca)
    {
        this.marca = marca;
    }

    public List<DTO_ArticuloImage> getImages()
    {
        return images;
    }
    public void setImages(final List<DTO_ArticuloImage> images)
    {
        this.images = images;
    }
}
