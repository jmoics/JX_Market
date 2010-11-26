package pe.com.jx_market.domain;

import java.io.Serializable;

public class DTO_PerfilModulo
    implements Serializable
{
    private Integer perfil;
    private Integer modulo;
    private Integer estado;

    public Integer getPerfil()
    {
        return perfil;
    }

    public void setPerfil(final Integer perfil)
    {
        this.perfil = perfil;
    }

    public Integer getModulo()
    {
        return modulo;
    }

    public void setModulo(final Integer modulo)
    {
        this.modulo = modulo;
    }

    public Integer getEstado()
    {
        return estado;
    }

    public void setEstado(final Integer estado)
    {
        this.estado = estado;
    }

}
