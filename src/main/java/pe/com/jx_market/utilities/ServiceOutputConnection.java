package pe.com.jx_market.utilities;

import java.util.List;
import java.util.Map;

public class ServiceOutputConnection<E, F, T>
    extends ServiceInputConnection<E, F, T>
{

    private static final long serialVersionUID = -4570218338889339092L;

    private int errorCode;
    private String errorMsg;

    public ServiceOutputConnection()
    {
        this.errorMsg = "Motivo de error no especificado";
    }

    public ServiceOutputConnection(final E obj)
    {
        super(obj);
    }

    public ServiceOutputConnection(final Map<Object, Object> m)
    {
        super(m);
    }

    public ServiceOutputConnection(final List<E> l)
    {
        super(l);
    }

    public int getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode(final int errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMsg()
    {
        return this.errorMsg;
    }

    public void setErrorMsg(final String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public void setError(final int errorCode,
                         final String msg)
    {
        this.errorCode = errorCode;
        this.errorMsg = msg;
    }
}
