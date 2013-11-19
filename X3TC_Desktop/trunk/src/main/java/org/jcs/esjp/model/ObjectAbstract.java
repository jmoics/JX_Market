package org.jcs.esjp.model;


public abstract class ObjectAbstract
{
    private String name;
    private Integer price;
    private Integer quantity;
    private Integer freeSpace;
    private String iconPath;
    private Integer orderType;

    public String getIconPath()
    {
        return iconPath;
    }
    public void setIconPath(final String iconPath)
    {
        this.iconPath = iconPath;
    }
    public String getName()
    {
        return name;
    }
    public void setName(final String name)
    {
        this.name = name;
    }
    public Integer getPrice()
    {
        return price;
    }
    public void setPrice(final Integer price)
    {
        this.price = price;
    }
    public Integer getQuantity()
    {
        return quantity;
    }
    public void setQuantity(final Integer quantity)
    {
        this.quantity = quantity;
    }
    public Integer getFreeSpace()
    {
        return freeSpace;
    }
    public void setFreeSpace(final Integer freeSpace)
    {
        this.freeSpace = freeSpace;
    }
    public Integer getOrderType()
    {
        return orderType;
    }
    public void setOrderType(final Integer orderType)
    {
        this.orderType = orderType;
    }
    @Override
    public String toString() {
        return this.name;
    };
}
