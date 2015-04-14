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
public class DTO_Categoria implements Serializable
{
    private static final long serialVersionUID = 7736453145474958927L;

    private Integer id;
    private Integer empresa;
    private String categoryName;
    private Integer categoryParentId;
    private Boolean estado;
    private byte[] imagen;
    private List<DTO_Product> articulos;

    public DTO_Categoria() {
    }

    /**
     * Constructor solo utilizado para utilizar como raíz del arbol al editar.
     *
     * @param _nombre
     */
    public DTO_Categoria(final String _categoryName) {
        this.categoryName = _categoryName;
    }

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
    public String getCategoryName()
    {
        return categoryName;
    }
    public void setCategoryName(final String categoryName)
    {
        this.categoryName = categoryName;
    }

    public Integer getCategoryParentId()
    {
        return categoryParentId;
    }

    public void setCategoryParentId(final Integer categoryParentId)
    {
        this.categoryParentId = categoryParentId;
    }

    public byte[] getImagen()
    {
        return imagen;
    }

    public void setImagen(final byte[] imagen)
    {
        this.imagen = imagen;
    }

    public Boolean getEstado()
    {
        return estado;
    }

    public void setEstado(final Boolean estado)
    {
        this.estado = estado;
    }
    public List<DTO_Product> getArticulos()
    {
        return articulos;
    }
    public void setArticulos(final List<DTO_Product> articulos)
    {
        this.articulos = articulos;
    }

    @Override
    public String toString()
    {
        return this.categoryName;
    }
}
