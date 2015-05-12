package pe.com.jx_market.domain;


/**
 * @author jcuevas
 *
 */
public class DTO_Atributo2Product
{
    /**
     *
     */
    private Integer atributeId;
    /**
     *
     */
    private Integer productId;
    /**
     *
     */
    private String name;

    /**
     * @return Attribute Id.
     */
    public Integer getAtributeId()
    {
        return this.atributeId;
    }

    /**
     * @param _atributeId Attribute Id.
     */
    public void setAtributeId(final Integer _atributeId)
    {
        this.atributeId = _atributeId;
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
     * @return Name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param _name Name.
     */
    public void setName(final String _name)
    {
        this.name = _name;
    }
}