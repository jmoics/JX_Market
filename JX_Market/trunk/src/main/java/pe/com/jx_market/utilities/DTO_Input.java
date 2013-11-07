package pe.com.jx_market.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTO_Input implements Serializable {
    private Serializable object;
    private List lista;
    private Map mapa;
    private String verbo;

    public DTO_Input () {
    }

    public DTO_Input (Serializable obj) {
        this.object = obj;
    }

    public DTO_Input (Serializable obj, String verbo) {
        this.object = obj;
        this.verbo = verbo;
    }

    public DTO_Input (Map m) {
        this.mapa = m;
    }

    public DTO_Input (List l) {
        this.lista = l;
    }

    public void addMapPair (Serializable k, Serializable v) {
        if (this.mapa == null) {
            this.mapa = new HashMap();
        }
        this.mapa.put(k, v);
    }

    public void addListEntry (Serializable v) {
        if (this.lista == null) {
            this.lista = new ArrayList();
        }
        this.lista.add(v);
    }

    public Serializable getObject () {
        return this.object;
    }

    public void setObject (Serializable object) {
        this.object = object;
    }

    public List getLista () {
        return this.lista;
    }

    public void setLista (List lista) {
        this.lista = lista;
    }

    public Map getMapa () {
        return this.mapa;
    }

    public void setMapa (Map mapa) {
        this.mapa = mapa;
    }

    public String getVerbo () {
        return this.verbo;
    }

    public void setVerbo (String verbo) {
        this.verbo = verbo;
    }
}
