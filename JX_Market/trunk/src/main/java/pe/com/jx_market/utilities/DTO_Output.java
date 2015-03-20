package pe.com.jx_market.utilities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DTO_Output<E>
    extends DTO_Input<E>
    implements Serializable
{

    private static final long serialVersionUID = -1584530075331064907L;
    private int errorCode;
    private String errorMsg;

    public DTO_Output()
    {
        this.errorMsg = "Motivo de error no especificado";
    }

    public DTO_Output(final E obj)
    {
        super(obj);
    }

    public DTO_Output(final Map<Serializable, Serializable> m)
    {
        super(m);
    }

    public DTO_Output(final List<E> l)
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
