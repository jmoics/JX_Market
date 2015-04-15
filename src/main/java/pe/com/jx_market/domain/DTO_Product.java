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
public class DTO_Product implements Serializable
{
    private Integer id;
    private Integer company;
    private List<DTO_Category> categories;
    private String productName;
    private String productDescription;
    private DTO_TradeMark tradeMark;
    //private BigDecimal precio;
    //private Integer stock;
    private Boolean active;
    private List<DTO_ProductImage> images;

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
    public List<DTO_Category> getCategories()
    {
        return categories;
    }
    public void setCategories(final List<DTO_Category> categories)
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
    /*public String getTradeMark()
    {
        return tradeMark;
    }
    public void setTradeMark(String tradeMark)
    {
        this.tradeMark = tradeMark;
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
    public Boolean isActive()
    {
        return active;
    }
    public void setActive(final Boolean active)
    {
        this.active = active;
    }
    public DTO_TradeMark getTradeMark()
    {
        return tradeMark;
    }
    public void setTradeMark(final DTO_TradeMark tradeMark)
    {
        this.tradeMark = tradeMark;
    }

    public List<DTO_ProductImage> getImages()
    {
        return images;
    }
    public void setImages(final List<DTO_ProductImage> images)
    {
        this.images = images;
    }
}
