package pe.com.jx_market.utilities;


public interface BusinessServiceConnection<E, F, T>
    extends BusinessService<E>
{

    public ServiceOutputConnection<E, F, T> execute(ServiceInputConnection<E, F, T> input);
}
