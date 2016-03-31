package pe.com.jx_market.service;

import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
public interface IBusinessService<E>
{
    public ServiceOutput<E> select(ServiceInput<E> _input);

    public ServiceOutput<E> list(ServiceInput<E> _input);

    public ServiceOutput<E> insert(ServiceInput<E> _input);

    public ServiceOutput<E> update(ServiceInput<E> _input);

    public ServiceOutput<E> delete(ServiceInput<E> _input);
}
