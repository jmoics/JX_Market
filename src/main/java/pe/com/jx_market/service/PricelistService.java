package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.jx_market.domain.AbstractPricelist;
import pe.com.jx_market.domain.PricelistCost;
import pe.com.jx_market.domain.PricelistRetail;
import pe.com.jx_market.persistence.PricelistMapper;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;


/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
@Service
public class PricelistService<E>
    implements IBusinessService<E>
{
    /**
    *
    */
    @Autowired
    private PricelistMapper<E> pricelistMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.service.IBusinessService#select(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    public ServiceOutput<E> select(final ServiceInput<E> _input)
    {
        final ServiceOutput<E> output = new ServiceOutput<E>();
        final E pl = this.pricelistMapper.getPricelist4Id(_input.getObject());
        output.setObject(pl);
        output.setErrorCode(Constantes.OK);
        return output;
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.service.IBusinessService#list(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    public ServiceOutput<E> list(final ServiceInput<E> _input)
    {
        final ServiceOutput<E> output = new ServiceOutput<E>();
        if (_input.getObject() instanceof PricelistRetail) {
            output.setSecList(this.pricelistMapper.getPricelistRetail(_input.getObject()));
        } else if (_input.getObject() instanceof PricelistCost) {
            output.setSecList(this.pricelistMapper.getPricelistCost(_input.getObject()));
        }
        output.setErrorCode(Constantes.OK);
        return output;
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.service.IBusinessService#insert(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    public ServiceOutput<E> insert(final ServiceInput<E> _input)
    {
        final ServiceOutput<E> output = new ServiceOutput<E>();
        final E plTmp = this.pricelistMapper.getPricelist4Id(_input.getObject());
        if (plTmp == null) {
            this.pricelistMapper.insertPricelist(_input.getObject());
            if (_input.getObject() instanceof PricelistRetail) {
                this.pricelistMapper.insertPricelistRetail(new PricelistRetail(((AbstractPricelist) _input.getObject())
                                .getId()));
            } else if (_input.getObject() instanceof PricelistCost) {
                this.pricelistMapper.insertPricelistCost(new PricelistCost(((AbstractPricelist) _input.getObject())
                                .getId()));
            }
        } else {
            this.pricelistMapper.updatePricelist(_input.getObject());
        }
        output.setErrorCode(Constantes.OK);
        return output;
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.service.IBusinessService#update(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    public ServiceOutput<E> update(final ServiceInput<E> _input)
    {
        // TODO Auto-generated method stub
                return null;
    }

    /* (non-Javadoc)
     * @see pe.com.jx_market.service.IBusinessService#delete(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    public ServiceOutput<E> delete(final ServiceInput<E> _input)
    {
        // TODO Auto-generated method stub
                return null;
    }

}
