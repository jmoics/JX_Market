package pe.com.jx_market.utilities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ServiceOutput<E>
    extends ServiceInput<E>
    implements Serializable
{

    private static final long serialVersionUID = -1584530075331064907L;
    private int errorCode;
    private String errorMsg;

    public ServiceOutput()
    {
        this.errorMsg = "Motivo de error no especificado";
    }

    public ServiceOutput(final E obj)
    {
        super(obj);
    }

    public ServiceOutput(final Map<Serializable, Serializable> m)
    {
        super(m);
    }

    public ServiceOutput(final List<E> l)
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
