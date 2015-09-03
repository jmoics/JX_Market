/**
 *
 */
package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.Currency;
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
public class CurrencyService
    implements BusinessService<Currency>
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
    public ServiceOutput<Currency> execute(final ServiceInput<Currency> _input)
    {
        final ServiceOutput<Currency> output = new ServiceOutput<Currency>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista(this.currencyMapper.getCurrencies(_input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final Currency curTmp = this.currencyMapper.getCurrency4Id(_input.getObject());
            if (curTmp == null) {
                this.currencyMapper.insertCurrency(_input.getObject());
            } else {
                this.currencyMapper.updateCurrency(_input.getObject());
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final Currency art = this.currencyMapper.getCurrency4Id(_input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.currencyMapper.deleteCurrency(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
