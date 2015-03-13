/**
 * 
 */
package pe.com.jx_market.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author George
 * 
 */
public class DTO_Articulo implements Serializable
{
    private Integer codigo;
    private Integer empresa;
    //private Integer categoria;
    private String nombre;
    private String descripcion;
    //private String marca;
    //private BigDecimal precio;
    //private Integer stock;
    private Integer activo;
    private byte[] imagen;
    private String nomimg;
    
    
    public String getNomimg()
    {
        return nomimg;
    }
    public void setNomimg(String nomImg)
    {
        this.nomimg = nomImg;
    }
    public byte[] getImagen()
    {
        return imagen;
    }
    public void setImagen(byte[] imagen)
    {
        this.imagen = imagen;
    }
    public Integer getCodigo()
    {
        return codigo;
    }
    public void setCodigo(Integer codigo)
    {
        this.codigo = codigo;
    }
    public Integer getEmpresa()
    {
        return empresa;
    }
    public void setEmpresa(Integer empresa)
    {
        this.empresa = empresa;
    }
    /*public Integer getCategoria()
    {
        return categoria;
    }
    public void setCategoria(Integer categoria)
    {
        this.categoria = categoria;
    }*/
    public String getNombre()
    {
        return nombre;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    public String getDescripcion()
    {
        return descripcion;
    }
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
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
    public Integer getActivo()
    {
        return activo;
    }
    public void setActivo(Integer activo)
    {
        this.activo = activo;
    }
}
