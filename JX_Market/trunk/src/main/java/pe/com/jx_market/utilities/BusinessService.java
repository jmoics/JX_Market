package pe.com.jx_market.utilities;

public interface BusinessService<E> {
    public ServiceOutput<E> execute (ServiceInput<E> paramDTO_Input);
}
