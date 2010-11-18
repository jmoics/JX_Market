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
    private Integer codigo;
    private Integer empresa;
    private String nombre;
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
    public String getNombre()
    {
        return nombre;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    
}
