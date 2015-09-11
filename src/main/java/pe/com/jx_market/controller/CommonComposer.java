package pe.com.jx_market.controller;

import org.apache.commons.logging.Log;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;

import pe.com.jx_market.domain.DTO_Company;
import pe.com.jx_market.utilities.BusinessService;
import pe.com.jx_market.utilities.Constantes;
import pe.com.jx_market.utilities.JXMarketException;
import pe.com.jx_market.utilities.ServiceInput;
import pe.com.jx_market.utilities.ServiceOutput;

/**
 * TODO comment!
 *
 * @author jcuevas
 * @version $Id$
 */
@VariableResolver(DelegatingVariableResolver.class)
public abstract class CommonComposer<T extends Component>
    extends SelectorComposer<T>
{

    private static final long serialVersionUID = 3258515575680520815L;
    private DTO_Company company;
    @WireVariable
    private Desktop desktop;
    @WireVariable
    private BusinessService<String> passwordHashService;

    @Override
    public void doAfterCompose(final T _comp)
        throws Exception
    {
        super.doAfterCompose(_comp);
    }

    /**
     * @param _logger
     * @param _txt
     * @param _txt2
     * @param _t
     * @return
     */
    public int alertaInfo(final Log _logger,
                          final String _txt,
                          final String _txt2,
                          final Throwable _t)
    {
        int ret = 0;
        if (_txt.length() > 0) {
            ret = Messagebox.show(_txt, this.company.getBusinessName(), Messagebox.OK, Messagebox.INFORMATION);
        }
        if (_t != null) {
            _logger.info(_txt2, _t);
        } else {
            _logger.info(_txt2);
        }
        return ret;
    }

    /**
     * @param _logger
     * @param _txt
     * @param _txt2
     * @param _t
     */
    public void alertaError(final Log _logger,
                            final String _txt,
                            final String _txt2,
                            final Throwable _t)
    {
        if (_txt.length() > 0) {
            Messagebox.show(_txt, this.company.getBusinessName(), Messagebox.OK, Messagebox.EXCLAMATION);
        }
        if (_t != null) {
            _logger.error(_txt2, _t);
        } else {
            _logger.error(_txt2);
        }
    }

    /**
     * @param _combo Combobox to add items.
     */
    public void buildActiveCombo(final Combobox _combo)
    {
        Comboitem item = new Comboitem();
        item.setLabel(Labels.getLabel("pe.com.jx_market.Active.TRUE"));
        item.setValue(Constantes.STB_ACTIVO);
        _combo.appendChild(item);
        item = new Comboitem();
        item.setLabel(Labels.getLabel("pe.com.jx_market.Active.FALSE"));
        item.setValue(Constantes.STB_INACTIVO);
        _combo.appendChild(item);
    }

    /**
     * @param _value Value to encrypt
     * @return String with encrypted value.
     * @throws JXMarketException on error.
     */
    protected String encryption(final String _value)
        throws JXMarketException
    {
        String ret = "";
        final ServiceOutput<String> output = this.passwordHashService.execute(new ServiceInput<String>(_value));
        if (output.getErrorCode() == Constantes.OK) {
            ret = output.getObject();
        } else {
            throw new JXMarketException(CommonComposer.class, "Error en encriptacion hash de password");
        }
        return ret;
    }
}
