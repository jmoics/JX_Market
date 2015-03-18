package pe.com.jx_market.utilities;

public abstract interface BusinessService<E> {
    public abstract DTO_Output<E> execute (DTO_Input<E> paramDTO_Input);
}
