package pe.com.jx_market.utilities;

public abstract interface BusinessService<E> {
    public abstract ServiceOutput<E> execute (ServiceInput<E> paramDTO_Input);
}
