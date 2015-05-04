package pe.com.jx_market.utilities;

import java.util.List;
import java.util.Map;


public class ServiceInputConnection<E, F, T>
    extends ServiceInput<E>
{

    private static final long serialVersionUID = -7531435153304653110L;

    private F objectFrom;
    private T objectTo;
    private List<F> resultListFrom;
    private List<T> resultListTo;

    public ServiceInputConnection() {

    }

    public ServiceInputConnection(final E obj)
    {
        super(obj);
    }

    public ServiceInputConnection(final Map<Object, Object> m)
    {
        super(m);
    }

    public ServiceInputConnection(final List<E> l)
    {
        super(l);
    }

    public F getObjectFrom()
    {
        return objectFrom;
    }

    public void setObjectFrom(final F objectFrom)
    {
        this.objectFrom = objectFrom;
    }

    public T getObjectTo()
    {
        return objectTo;
    }

    public void setObjectTo(final T objectTo)
    {
        this.objectTo = objectTo;
    }

    public List<F> getResultListFrom()
    {
        return resultListFrom;
    }

    public void setResultListFrom(final List<F> resultListFrom)
    {
        this.resultListFrom = resultListFrom;
    }

    public List<T> getResultListTo()
    {
        return resultListTo;
    }

    public void setResultListTo(final List<T> resultListTo)
    {
        this.resultListTo = resultListTo;
    }


}
