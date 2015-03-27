/**
 *
 */
package pe.com.jx_market.domain;

import java.io.Serializable;

/**
 * @author George
 *
 */
public class DTO_Categoria implements Serializable
{
    private static final long serialVersionUID = 7736453145474958927L;

    private Integer codigo;
    private Integer empresa;
    private String nombre;
    private Integer codigoPadre;
    private Boolean estado;
    private byte[] imagen;

    public DTO_Categoria() {
    }

    /**
     * Constructor solo utilizado para utilizar como raíz del arbol al editar.
     *
     * @param _nombre
     */
    public DTO_Categoria(final String _nombre) {
        this.nombre = _nombre;
    }

    public Integer getCodigo()
    {
        return codigo;
    }
    public void setCodigo(final Integer codigo)
    {
        this.codigo = codigo;
    }
    public Integer getEmpresa()
    {
        return empresa;
    }
    public void setEmpresa(final Integer empresa)
    {
        this.empresa = empresa;
    }
    public String getNombre()
    {
        return nombre;
    }
    public void setNombre(final String nombre)
    {
        this.nombre = nombre;
    }

    public Integer getCodigoPadre()
    {
        return codigoPadre;
    }

    public void setCodigoPadre(final Integer codigoPadre)
    {
        this.codigoPadre = codigoPadre;
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

    @Override
    public String toString()
    {
        return this.nombre;
    }
}
