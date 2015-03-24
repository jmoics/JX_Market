package pe.com.jx_market.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceInput<E>
    implements Serializable
{

    private static final long serialVersionUID = 8163067441458677824L;

    private E object;
    private List<E> lista;
    private Map<Serializable, Serializable> mapa;
    private String accion;

    public ServiceInput()
    {
    }

    public ServiceInput(final E obj)
    {
        this.object = obj;
    }

    public ServiceInput(final E obj,
                        final String accion)
    {
        this.object = obj;
        this.accion = accion;
    }

    public ServiceInput(final Map<Serializable, Serializable> m)
    {
        this.mapa = m;
    }

    public ServiceInput(final List<E> l)
    {
        this.lista = l;
    }

    public void addMapPair(final Serializable k,
                           final Serializable v)
    {
        if (this.mapa == null) {
            this.mapa = new HashMap<Serializable, Serializable>();
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

    public Map<Serializable, Serializable> getMapa()
    {
        return this.mapa;
    }

    public void setMapa(final Map<Serializable, Serializable> mapa)
    {
        this.mapa = mapa;
    }

    public String getAccion()
    {
        return this.accion;
    }

    public void setAccion(final String _accion)
    {
        this.accion = _accion;
    }
}
