/**
 *
 */
package pe.com.jx_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.jx_market.domain.DTO_TradeMark;
import pe.com.jx_market.persistence.TradeMarkMapper;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * @author George
 *
 */
@Service
public class TradeMarkService
    implements BusinessService<DTO_TradeMark>
{
    /**
     *
     */
    @Autowired
    private TradeMarkMapper tradeMarkMapper;

    /* (non-Javadoc)
     * @see pe.com.jx_market.utilities.BusinessService#execute(pe.com.jx_market.utilities.ServiceInput)
     */
    @Override
    @Transactional
    public ServiceOutput<DTO_TradeMark> execute(final ServiceInput<DTO_TradeMark> _input)
    {
        final ServiceOutput<DTO_TradeMark> output = new ServiceOutput<DTO_TradeMark>();
        if (Constantes.V_LIST.equals(_input.getAction())) {
            output.setLista(this.tradeMarkMapper.getTradeMarks(_input.getObject()));
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_REGISTER.equals(_input.getAction())) {
            final DTO_TradeMark categTmp = this.tradeMarkMapper.getTradeMark4Id(_input.getObject());
            if (categTmp == null) {
                this.tradeMarkMapper.insertTradeMark(_input.getObject());
            } else {
                this.tradeMarkMapper.updateTradeMark(_input.getObject());
            }
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_GET.equals(_input.getAction())) {
            final DTO_TradeMark art = this.tradeMarkMapper.getTradeMark4Id(_input.getObject());
            output.setObject(art);
            output.setErrorCode(Constantes.OK);
        } else if (Constantes.V_DELETE.equals(_input.getAction())) {
            this.tradeMarkMapper.deleteTradeMark(_input.getObject());
            output.setErrorCode(Constantes.OK);
        } else {
            throw new RuntimeException("No se especifico verbo adecuado");
        }
        return output;
    }
}
