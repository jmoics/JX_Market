/**
 *
 */
package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.CurrencyRate;
import pe.com.jx_market.persistence.CurrencyMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@Service
public class CurrencyRateService
    implements BusinessService<CurrencyRate>
{
    /**
     *
     */
    @Autowired
    private CurrencyMapper currencyMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    @Transactional
    public ServiceOutput<CurrencyRate> execute(final ServiceInput<CurrencyRate> _input)
    {
        final ServiceOutput<CurrencyRate> output = new ServiceOutput<CurrencyRate>();
        if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final CurrencyRate curTmp = this.currencyMapper.getCurrencyRate4Id(_input.getObject());
            if (curTmp == null) {
                this.currencyMapper.insertCurrencyRate(_input.getObject());
            } else {
                this.currencyMapper.updateCurrencyRate(_input.getObject());
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final CurrencyRate art = this.currencyMapper.getCurrencyRate4Id(_input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
