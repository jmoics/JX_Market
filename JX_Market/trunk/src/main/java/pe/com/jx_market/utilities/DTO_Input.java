package pe.com.jx_market.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTO_Input<E>
    implements Serializable
{
    private static final long serialVersionUID = 8163067441458677824L;

    private E object;
    private List<E> lista;
    private Map<Serializable, E> mapa;
    private String verbo;

    public DTO_Input()
    {
    }

    public DTO_Input(final E obj)
    {
        this.object = obj;
    }

    public DTO_Input(final E obj,
                     final String verbo)
    {
        this.object = obj;
        this.verbo = verbo;
    }

    public DTO_Input(final Map<Serializable, E> m)
    {
        this.mapa = m;
    }

    public DTO_Input(final List<E> l)
    {
        this.lista = l;
    }

    public void addMapPair(final Serializable k,
                           final E v)
    {
        if (this.mapa == null) {
            this.mapa = new HashMap<Serializable, E>();
        }
        this.mapa.put(k, v);
    }

    public void addListEntry(final E v)
    {
        if (this.lista == null) {
            this.lista = new ArrayList<E>();
        }
        this.lista.add(v);
        ;
    }

    public E getObject()
    {
        return this.object;
    }

    public void setObject(final E object)
    {
        this.object = object;
    }

    public List<E> getLista()
    {
        return this.lista;
    }

    public void setLista(final List<E> lista)
    {
        this.lista = lista;
    }

    public Map<Serializable, E> getMapa()
    {
        return this.mapa;
    }

    public void setMapa(final Map<Serializable, E> mapa)
    {
        this.mapa = mapa;
    }

    public String getVerbo()
    {
        return this.verbo;
    }

    public void setVerbo(final String verbo)
    {
        this.verbo = verbo;
    }
}
